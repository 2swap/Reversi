package com.twoswap.reversi.graphics;

import com.twoswap.reversi.Game;
import com.twoswap.reversi.board.Board;
import com.twoswap.reversi.board.Opponent;

public class Screen {
	public int width;
	public int height;
	public int[] pixels;
	public Game game;
	public int[] cols = { 0x00bf00, 0, 0xffffff, 0xff0000, 0xff00 };

	public Screen(int width, int height, Game game) {
		for (int i = 0; i < Board.boardSize; i++)
			Opponent.emptyVals[i] = 0x00bf00;
		pixels = new int[width * height];
		for (int i = 0; i < width * height; i++)
			pixels[i] = 0;
		this.game = game;
		this.width = width;
		this.height = height;
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0x00bf00;
	}

	public void render() {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y += 32)
				pixels[x + y * width] = 0;
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x += 32)
				pixels[x + y * width] = 0;
		for (int x = 0; x < Board.boardLength; x++)
			for (int y = 0; y < Board.boardLength; y++) {
				int color = cols[Board.board[x + y * Board.boardLength]];
				if (Board.board[x + y * Board.boardLength] == 0)
					color = Opponent.emptyVals[x + y * Board.boardLength];
				if (color == cols[0])
					continue;
				for (int xn = 0; xn < 31; xn++)
					for (int yn = 0; yn < 31; yn++) {
						if ((xn - 16) * (xn - 16) + (yn - 16) * (yn - 16) < 225)
							pixels[x * 32 + xn + (y * 32 + yn) * width] = 0;
						if ((xn - 16) * (xn - 16) + (yn - 16) * (yn - 16) < 169)
							pixels[x * 32 + xn + (y * 32 + yn) * width] = color;
					}
			}
		for (int x = -2; x < 6; x++)
			for (int y = -2; y < 6; y++)
				pixels[(Board.lastMove % Board.boardLength) * 32 + 14 + x + ((Board.lastMove / Board.boardLength) * 32 + 14 + y) * width] = (x + 2 + y + 2) * 0x101010;
	}
}
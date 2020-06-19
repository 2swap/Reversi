package com.twoswap.reversi.graphics;

import com.twoswap.reversi.Game;
import com.twoswap.reversi.board.Board;
import com.twoswap.reversi.board.Seat;

public class Screen {
	public int width;
	public int height;
	public int[] pixels;
	public Game game;
	public int[] cols = { 0x00bf00, 0, 0xffffff, 0xff0000, 0xff00 };

	public Screen(int width, int height, Game game) {
		for (int i = 0; i < Board.BOARDSIZE; i++)
			Seat.emptyVals[i] = 0x00bf00;
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

	public void render(Board b) {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y += 32)
				pixels[x + y * width] = 0;
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x += 32)
				pixels[x + y * width] = 0;
		for (int x = 0; x < Board.BOARDSIZE; x++)
			for (int y = 0; y < Board.BOARDSIZE; y++) {
				int color = cols[b.board[y][x]];
				if (b.board[y][x] == 0)
					color = Seat.emptyVals[x + y * Board.BOARDSIZE];
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
	}
}
package com.twoswap.reversi.graphics;

import com.twoswap.reversi.Game;
import com.twoswap.reversi.board.Board;

public class Screen {
	public int width;
	public int height;
	public int[] pixels;
	public Game game;
	public static int[] cols = { 0x00bf00, 0, 0xffffff};
	public static String[] names = { "Empty", "Black", "White"};

	public Screen(int width, int height, Game game) {
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
		
		//Render lines of grid
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y += 32)
				pixels[x + y * width] = 0;
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x += 32)
				pixels[x + y * width] = 0;
		
		for (int x = 0; x < Board.SIZE; x++)
			for (int y = 0; y < Board.SIZE; y++) {
				int color = cols[b.board[y][x]];
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
		if(b.lastMove != -1)
			for (int x = -2; x < 6; x++)
				for (int y = -2; y < 6; y++)
					pixels[(b.lastMove % Board.SIZE) * 32 + 14 + x + ((b.lastMove / Board.SIZE) * 32 + 14 + y) * width] = (x + 2 + y + 2) * 0x101010;
		
		if(b.whoseTurn == Board.EMPTY)
			for(int i = (int) (width*.25); i < width*.75; i++)
				for(int j = -5; j <= 5; j++)
					pixels[i+j+i*width] = cols[b.getWinner()];
	}
}
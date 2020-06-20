package com.twoswap.reversi.strategy;

import com.twoswap.reversi.board.Board;

public class GreedySafe extends Strategy {
	
	@Override
	public double evaluateAsBlack(Board b) {
		int ct = 0;
		for(int i = 0; i < Board.SIZE; i++)
			for(int j = 0; j < Board.SIZE; j++) {
				if(b.board[i][j] == Board.BLACK) ct++;
				else if(b.board[i][j] == Board.WHITE) ct--;
			}
		return ct + Math.random();
	}
	
	public boolean isSafe(Board b, int x, int y) {
		byte hisCol = Board.otherTurn(b.board[y][x]);
		byte[][] fill = new byte[Board.SIZE][Board.SIZE];
		for (int i = 0; i < Board.SIZE; i++)
			for (int j = 0; j < Board.SIZE; j++) {
				byte here = b.board[i][j];
				if(here == Board.EMPTY)
					here = hisCol;
				fill[i][j] = b.board[i][j];
			}
		boolean noBreak = true;
		while(noBreak) {
			for (int i = 0; i < Board.SIZE; i++)
				for (int j = 0; j < Board.SIZE; j++) {
					int ct = 0;
					for (int di = -1; di <= 1; di++)
						for (int dj = -1; dj <= 1; dj++) {
							if(i+di<Board.SIZE && i+di>=0 && j+dj<Board.SIZE && j+dj>=0 && fill[i+di][j+dj] == hisCol)
								ct++;
						}
				}
		}
	}

}

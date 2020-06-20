package com.twoswap.reversi.strategy;

import com.twoswap.reversi.board.Board;

public class GreedySmart extends Strategy {
	
	@Override
	public double evaluateAsBlack(Board b) {
		int empties = 0;
		for(int i = 0; i < Board.SIZE; i++)
			for(int j = 0; j < Board.SIZE; j++)
				if(b.board[i][j] == Board.EMPTY)
					empties++;
		int[] values5 = {1, 1, 0}; // corners, non-corners, moves
		int[] values10 = {3, 1, 2}; // corners, non-corners, moves
		int[] values20 = {100, 1, 100}; // corners, non-corners, moves
		int[] values64 = {10000, 1, 100}; // corners, non-corners, moves
		int[] values = {0,0,0};
		if(empties <= 5) values = values5;
		if(empties <= 10) values = values10;
		if(empties <= 20) values = values20;
		if(empties <= 64) values = values64;
		Board blackBoard = b.whoseTurn == Board.BLACK ? b : new Board(b.board, Board.BLACK, -1);
		Board whiteBoard = b.whoseTurn == Board.WHITE ? b : new Board(b.board, Board.WHITE, -1);
		int ct = 0;
		for(int i = 0; i < Board.SIZE; i++)
			for(int j = 0; j < Board.SIZE; j++) {
				if(blackBoard.isLegal(i,j)) ct+=values[2];
				if(whiteBoard.isLegal(i,j)) ct-=values[2];
			}
		for(int i = 0; i < Board.SIZE; i++)
			for(int j = 0; j < Board.SIZE; j++) {
				int value = (Math.abs(i-3.5)+Math.abs(j-3.5)==7)?values[0]:values[1];
				if(b.board[i][j] == Board.BLACK) ct+=value;
				else if(b.board[i][j] == Board.WHITE) ct-=value;
			}
		return ct + Math.random();
	}

}

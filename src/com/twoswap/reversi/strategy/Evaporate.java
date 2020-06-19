package com.twoswap.reversi.strategy;

import com.twoswap.reversi.board.Board;

public class Evaporate extends Strategy {
	
	@Override
	public double evaluateAsBlack(Board b) {
		int ct = 0;
		for(int i = 0; i < Board.SIZE; i++)
			for(int j = 0; j < Board.SIZE; j++) {
				if(b.board[i][j] == Board.BLACK) ct--;
				else if(b.board[i][j] == Board.WHITE) ct++;
			}
		return ct + Math.random();
	}

}

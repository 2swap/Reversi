package com.twoswap.reversi.strategy;

import com.twoswap.reversi.board.Board;

public class GreedyCorners extends Strategy {
	
	@Override
	public double evaluateAsBlack(Board b) {
		int ct = 0;
		for(int i = 0; i < Board.SIZE; i++)
			for(int j = 0; j < Board.SIZE; j++) {
				int value = (Math.abs(i-3.5)+Math.abs(j-3.5)==7)?100:1;
				if(b.board[i][j] == Board.BLACK) ct+=value;
				else if(b.board[i][j] == Board.WHITE) ct-=value;
			}
		return ct + Math.random();
	}

}

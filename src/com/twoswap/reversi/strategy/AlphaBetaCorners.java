package com.twoswap.reversi.strategy;

import java.util.List;

import com.twoswap.reversi.board.Board;

public class AlphaBetaCorners extends Strategy {
	
	private int depth = 100;
	
	public AlphaBetaCorners(int depth) {
		this.depth = depth;
	}
	
	@Override
	public double evaluateAsBlack(Board b) {
		return alphabeta(b, depth, -100000, 100000, true);
	}
	
	//wikipedia pseudocode
	public double alphabeta(Board b, int depth, double alpha, double beta, boolean maximizingPlayer) {
		List<Board> children = b.children();
		if(depth == 0)
			return heuristic(b);
		if (maximizingPlayer) {
			double value = -100000;
			for (Board child : children) {
				value = Math.max(value, alphabeta(child, depth - 1, alpha, beta, false));
				alpha = Math.max(alpha, value);
				if (beta <= alpha)
					break; // (* beta cut-off *)
			}
			return value;
		} else {
			double value = 100000;
			for (Board child : children) {
				value = Math.min(value, alphabeta(child, depth - 1, alpha, beta, true));
				beta = Math.min(beta, value);
				if (beta <= alpha)
					break; // (* alpha cut-off *)
			}
			return value;
		}
	}
	
	public double heuristic(Board b) {
		int ct = 0;
		for(int i = 0; i < Board.SIZE; i++)
			for(int j = 0; j < Board.SIZE; j++) {
				int value = (Math.abs(i-3.5)+Math.abs(j-3.5)==7)?100:1;
				if(b.board[i][j] == Board.BLACK) ct+=value;
				else if(b.board[i][j] == Board.WHITE) ct-=value;
			}
		return ct;
	}

}

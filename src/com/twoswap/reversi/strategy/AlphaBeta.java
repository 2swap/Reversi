package com.twoswap.reversi.strategy;

import java.util.List;

import com.twoswap.reversi.board.Board;

public class AlphaBeta extends Strategy {
	
	private int depth = 100;
	Strategy strat;
	
	public AlphaBeta(Strategy strat, int depth) {
		this.depth = depth;
		this.strat = strat;
	}
	
	@Override
	public double evaluateAsBlack(Board b) {
		return alphabeta(b, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
	}

	public void onWin() {
		strat.onWin();
	}

	public void onLose() {
		strat.onLose();
	}
	
	//wikipedia pseudocode
	public double alphabeta(Board b, int depth, double alpha, double beta, boolean maximizingPlayer) {
		List<Board> children = b.children();
		if(depth == 0 || children.size() == 0)
			return strat.evaluateAsBlack(b);
		if (b.whoseTurn == Board.BLACK) { // if we are the maximizer
			double value = Double.NEGATIVE_INFINITY;
			for (Board child : children) {
				value = Math.max(value, alphabeta(child, depth - 1, alpha, beta, false));
				alpha = Math.max(alpha, value);
				if (beta <= alpha)
					break; // (* beta cut-off *)
			}
			return value;
		} else {
			double value = Double.POSITIVE_INFINITY;
			for (Board child : children) {
				value = Math.min(value, alphabeta(child, depth - 1, alpha, beta, true));
				beta = Math.min(beta, value);
				if (beta <= alpha)
					break; // (* alpha cut-off *)
			}
			return value;
		}
	}

}

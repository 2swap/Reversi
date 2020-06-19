package com.twoswap.reversi.strategy;

import java.util.List;

import com.twoswap.reversi.board.Board;

public abstract class Strategy {
	
	public double evaluateAsBlack(Board b) {
		return 0;
	}
	
	public double evaluate(Board b, byte whoAmI) {
		double score = evaluateAsBlack(b);
		if(whoAmI != Board.BLACK) score = -score;
		return score;
	}
	
	public int getBestMove(Board b, byte whoAmI) {
		List<Board> children = b.children();
		int bestIndex = -1;
		double bestScore = -10000000;
		for(Board child : children) {
			double score = evaluate(child, whoAmI);
			if(score > bestScore) {
				bestScore = score;
				bestIndex = child.lastMove;
			}
		}
		return bestIndex;
	}

}

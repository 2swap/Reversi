package com.twoswap.reversi.strategy;

import java.util.List;

import com.twoswap.reversi.board.Board;

public abstract class Strategy {
	
	public abstract double evaluateAsBlack(Board b);

	public void onWin() {
	}

	public void onLose() {
	}
	
	public int getBestMove(Board b, byte whoAmI) {
		List<Board> children = b.children();
		int bestIndex = -1;
		
		if(whoAmI == Board.BLACK) {
			double bestScore = Double.NEGATIVE_INFINITY;
			for(Board child : children) {
				double score = evaluateAsBlack(child);
				if(score > bestScore) {
					bestScore = score;
					bestIndex = child.lastMove;
				}
			}
		}
		
		else {
			double bestScore = Double.POSITIVE_INFINITY;
			for(Board child : children) {
				double score = evaluateAsBlack(child);
				if(score < bestScore) {
					bestScore = score;
					bestIndex = child.lastMove;
				}
			}
		}
		
		return bestIndex;
	}

}

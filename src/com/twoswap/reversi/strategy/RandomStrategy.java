package com.twoswap.reversi.strategy;

import com.twoswap.reversi.board.Board;

public class RandomStrategy extends Strategy {
	
	@Override
	public double evaluateAsBlack(Board b) {
		return Math.random();
	}

}

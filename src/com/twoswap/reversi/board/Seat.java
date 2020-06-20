package com.twoswap.reversi.board;

import com.twoswap.reversi.Game;
import com.twoswap.reversi.strategy.Strategy;

public class Seat {

	public boolean human;
	public byte color;
	public int humanMove = -1;
	public Strategy strat;
	
	public Seat(byte color) {
		this.color = color;
		this.human = true;
	}
	
	public Seat(byte color, Strategy strat) {
		this.color = color;
		this.human = false;
		this.strat = strat;
	}

	public int move() {
		if(human) {
			int whereToGo = humanMove;
			humanMove = -1;
			return whereToGo;
		}
		return strat.getBestMove(Game.gameBoard, color);
	}
}
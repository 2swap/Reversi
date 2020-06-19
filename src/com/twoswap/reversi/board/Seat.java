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

	/*public static double abp(byte[] node, int depth, double alpha, double beta, boolean maximizingPlayer){
     if (depth == 0 || BoardLogic.isFull(node))
         return eval(node);
     if (maximizingPlayer){
    	 double v = Double.MIN_VALUE;
         for (byte[] child : BoardLogic.children(node, (byte) 1)) {
             v = Math.max(v, abp(child, depth - 1, alpha, beta, false));
             alpha = Math.max(alpha, v);
             if (alpha >= beta)
                 break;
         }
         return v;
     }
     else{
        double v = Double.MAX_VALUE;
     	for (byte[] child : BoardLogic.children(node, (byte) 1)) {
             v = Math.min(v, abp(child, depth - 1, alpha, beta, true));
             beta = Math.min(beta, v);
             if (alpha >= beta)
                 break;
     	}
         return v;}
	}

	public static double eval(byte[] in) {
		return orgs[match - 1 + Board.turn].getValueByNeuron(in);
	}

	public static void ga() {
		int difference = 0;
		for (int i = 0; i < Board.boardSize; i++) {
			if (Board.board[i] == 1)
				difference++;
			else if (Board.board[i] == 2)
				difference--;
		}
		EvAlg loser;
		EvAlg winner;
		if (difference < 0) {
			loser = orgs[match];
			winner = orgs[match + 1];
		} else {
			loser = orgs[match + 1];
			winner = orgs[match];
		}
		loser.lastLoss = 0;
		winner.lastLoss++;
		for (int i = 0; i < loser.neurons.length; i++)
			loser.neurons[i] = (loser.neurons[i] + (orgs[bestOrg].neurons[i] - loser.neurons[i]) * 1.5) / 1.5 + Math.tan(Math.random() * Math.PI - Math.PI / 2) / 100;
		match++;
		if (match >= orgs.length - 1) {
			match = 0;
			gen++;
			System.out.print("\nGeneration " + gen + ", match: ");
		}
		//moves = 0;
		Board.setBoard();
		int bestCount = 0;
		for (int i = 0; i < Seat.orgs.length; i++)
			if (Seat.orgs[i].lastLoss > bestCount) {
				bestCount = Seat.orgs[i].lastLoss;
				bestOrg = i;
			}
	}
	*/
}
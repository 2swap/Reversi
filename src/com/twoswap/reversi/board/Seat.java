package com.twoswap.reversi.board;

public class Seat {

	public boolean human;
	public byte color;
	
	public Seat(byte color, boolean human) {
		this.color = color;
		this.human = human;
	}

	public static int move(byte[] in) {
		int depth = 3;
		int choice = -1;
		double quantifier = (col == 1) ? 1000000 : -1000000, measurer = -quantifier;
		double[] emptyOrig = new double[Board.BOARDSIZE];
		for (int y = 0; y < Board.BOARDSIZE; y++)
			for (int x = 0; x < Board.BOARDSIZE; x++) {

				if (!Board.isLegal(in, i, col))
					continue;

				byte[] firstBoard = new byte[Board.BOARDSIZE];
				System.arraycopy(in, 0, firstBoard, 0, Board.BOARDSIZE);
				firstBoard = Board.place(firstBoard, col);


			}
		if (Seat.human)
			for (int i = 0; i < Board.BOARDSIZE; i++)
				Seat.emptyVals[i] = 0x00bf00;
		return choice;
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
	}*/

	public static double eval(byte[] in) {
		return orgs[match - 1 + Board.turn].getValueByNeuron(in);
	}

/*	public static void ga() {
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
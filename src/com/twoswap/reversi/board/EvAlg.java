package com.twoswap.reversi.board;

public class EvAlg {
	static int boardSize = Board.boardSize, boardLength = Board.boardLength;
	public int lastLoss, layerLengths = 16;
	public double[] neurons = {0.08170419, 0.00332097, 0.02569031, 0.10811186, -0.0198416, 0.03369091, -0.0334171, 0.03270572, -0.0013292, -4.3371202};

	public EvAlg() {
	}

	public double getValueByNeuron(byte[] in) {//Returns the board's objective goodness for one player.
		double stoneCount = 0;
		for (int c = 0; c < 64; c++){
			int x = c&7, y = c/8;
			if(x>3)x=7-x;
			if(y>3)y=7-y;
			if(y>x){
				int temp = x;
				x = y;
				y = temp;
			}
			int i = (int) (x-.5*y*y+3.5*y);
			stoneCount += swapCol(in[c])*neurons[i];
		}

		return stoneCount;
	}

	public static int swapCol(int x){
		return (int)(-1.5*x*x+2.5*x);
	}
	
	public static int fastAbs(int in) {
		return in > 0 ? in : -in;
	}

	public static int fastMax(int a, int b) {
		return a > b ? a : b;
	}

	public static int fastMin(int a, int b) {
		return a > b ? b : a;
	}

	public static int fastPow(int a, int b) {
		int res = 1;
		for (int i = 0; i < b; i++)
			res *= a;
		return res;
	}

}

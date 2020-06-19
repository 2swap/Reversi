package com.twoswap.reversi.board;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public static final int SIZE = 8;
	public static final byte BLACK = 1, WHITE = 2, EMPTY = 0;
	public byte[][] board;
	public byte whoseTurn;
	public int lastMove = -1;

	public Board() {
		resetBoard();
	}
	
	public Board(byte[][] board, byte whoseTurn, int lastMove) {
		this.board = board;
		this.whoseTurn = whoseTurn;
		this.lastMove = lastMove;
	}

	public void resetBoard() {
		board = new byte[SIZE][SIZE];
		
		// Remove pieces from board
		for (int y = 0; y < SIZE; y++)
			for (int x = 0; x < SIZE; x++)
				board[y][x] = EMPTY;
		
		//Set cross in center
		board[3][3] = board[4][4] = WHITE;
		board[3][4] = board[4][3] = BLACK;
		
		whoseTurn = BLACK;
		lastMove = -1;
	}
	
	public static byte otherTurn(byte wT) {
		return wT == BLACK ? WHITE : BLACK;
	}
	
	//TODO cache this
	public boolean isLegal(int x, int y) {
		byte heCol = otherTurn(whoseTurn);
		
		//You can't move where there's already a piece
		if (board[y][x] != 0) return false;

		//Check that it's a valid sandwich
		for (int dx = -1; dx <= 1; dx++)
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) continue;
				int d = 0;
				while(true) {
					++d;
					int nx = x + d*dx, ny = y + d*dy;
					if (nx < 0 || nx >= SIZE || ny < 0 || ny >= SIZE) break;
					
					if (board[ny][nx] == whoseTurn && d>1) return true;
					else if (board[ny][nx] == 0 || (board[ny][nx] == whoseTurn && d==1)) break;
				}
			}

		return false;
	}

	public Board place(int x, int y) {
		//Copy this board in
		byte[][] solved = new byte[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				solved[i][j] = board[i][j];
		
		for (int dx = -1; dx <= 1; dx++)
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) continue;
				int setCount = 0;
				int d = 0;
				while(true) {
					++d;
					int nx = x + d*dx, ny = y + d*dy;
					if (nx < 0 || nx >= SIZE || ny < 0 || ny >= SIZE) break;
					
					if (board[ny][nx] == whoseTurn) {setCount = d; break;}
					else if (board[ny][nx] == 0 || (board[ny][nx] == whoseTurn && d==1)) break;
				}
				for(d = 1; d < setCount; d++) {
					int nx = x + d*dx, ny = y + d*dy;
					solved[ny][nx] = whoseTurn;
				}
			}
		
		//set the played piece
		solved[y][x] = whoseTurn;
		
		byte nextTurn = whoseTurnWillItBe(solved, whoseTurn);
		return new Board(solved, nextTurn, x+y*SIZE);
	}
	
	public static byte whoseTurnWillItBe(byte[][] dreamboard, byte whoJustPlaced) {
		byte nextTurn = otherTurn(whoJustPlaced);
		if(canIMove(dreamboard, nextTurn)) return nextTurn;
		if(canIMove(dreamboard, whoJustPlaced)) return whoJustPlaced;
		return EMPTY;
		
	}
	
	public static boolean canIMove(byte[][] dreamboard, byte myCol) {
		Board b = new Board(dreamboard, myCol, -1);
		for(int y = 0; y < SIZE; y++)
			for(int x = 0; x < SIZE; x++)
				if(b.isLegal(x,y)) return true;
		return false;
	}

	public List<Board> children(){
		ArrayList<Board> out = new ArrayList<>();
		for (int y = 0; y < SIZE; y++)
			for (int x = 0; x < SIZE; x++) {

				if (!isLegal(x,y)) continue;

				byte[][] newBoard = new byte[SIZE][SIZE];
				for (int i = 0; i < SIZE; i++)
					for (int j = 0; j < SIZE; j++)
						newBoard[i][j] = board[i][j];
				Board child = place(x, y);
				out.add(child);
			}
		return out;
	}
}

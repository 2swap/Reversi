package com.twoswap.reversi.board;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public static final int BOARDSIZE = 8;
	public byte[][] board = new byte[BOARDSIZE][BOARDSIZE];
	public boolean whitesTurn = false;

	public Board() {
	}
	
	public Board(byte[][] board, boolean whitesTurn) {
		this.board = board;
		this.whitesTurn = whitesTurn;
	}

	public void setBoard() {
		
		// Remove pieces from board
		for (int y = 0; y < BOARDSIZE; y++)
			for (int x = 0; x < BOARDSIZE; x++)
				board[y][x] = 0;
		
		//Set cross in center: 1 is white, 2 black
		board[3][3] = board[4][4] = 1;
		board[3][4] = board[4][3] = 2;
		
		whitesTurn = false;
	}

	public boolean move(int x, int y, boolean wT) {
		if (isLegal(x, y, wT)) {
			board = place(board, x, y, wT);
			return true;
		}
		return false;
	}
	
	public static byte turnToByte(boolean turn) {
		return (byte)(turn?2:1);
	}
	
	//TODO cache this
	public boolean isLegal(int x, int y, boolean wT) {
		byte heCol = turnToByte(!wT);
		
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
					if (nx < 0 || nx >= BOARDSIZE || ny < 0 || ny >= BOARDSIZE) break;
					
					if (board[ny][nx] == heCol && d>1) return true;
					else if (board[ny][nx] == 0 || (board[ny][nx] == heCol && d==1)) break;
				}
			}

		return false;
	}

	public static byte[][] place(byte[][] in, int x, int y, boolean wT) {
		byte myCol = turnToByte( wT);
		byte heCol = turnToByte(!wT);
		
		//Copy this board in
		byte[][] solved = new byte[BOARDSIZE][BOARDSIZE];
		for (int i = 0; i < BOARDSIZE; i++)
			for (int j = 0; j < BOARDSIZE; j++)
				solved[i][j] = in[i][j];
		
		for (int dx = -1; dx <= 1; dx++)
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) continue;
				int setCount = 0;
				int d = 0;
				while(true) {
					++d;
					int nx = x + d*dx, ny = y + d*dy;
					if (nx < 0 || nx >= BOARDSIZE || ny < 0 || ny >= BOARDSIZE) break;
					
					if (in[ny][nx] == heCol) {setCount = d; break;}
					else if (in[ny][nx] == 0 || (in[ny][nx] == heCol && d==1)) break;
				}
				for(d = 1; d < setCount; d++) {
					int nx = x + d*dx, ny = y + d*dy;
					solved[ny][nx] = myCol;
				}
			}
		
		//set the played piece
		solved[y][x] = myCol;
		
		return solved;
	}
	
	public boolean canPlayerMove(boolean wT) {
		for(int y = 0; y < BOARDSIZE; y++)
			for(int x = 0; x < BOARDSIZE; x++)
				if(isLegal(x,y,wT)) return true;
		return false;
	}

	public List<byte[][]> children(){
		ArrayList<byte[][]> out = new ArrayList<>();
		for (int y = 0; y < BOARDSIZE; y++)
			for (int x = 0; x < BOARDSIZE; x++) {

				if (!isLegal(x,y,whitesTurn)) continue;

				byte[][] newBoard = new byte[BOARDSIZE][BOARDSIZE];
				for (int i = 0; i < BOARDSIZE; i++)
					for (int j = 0; j < BOARDSIZE; j++)
						newBoard[i][j] = board[i][j];
				newBoard = place(newBoard, col, i);
				out.add(newBoard);
			}
		return out;
	}
}

package com.twoswap.reversi.board;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public static int boardLength = 8, boardSize = boardLength * boardLength;
	public static byte[] board = new byte[boardSize];
	public boolean whitesTurn = true;
	public static int lastMove = boardSize / 2;

	public static void setBoard() {
		boardSize = boardLength * boardLength;
		BoardLogic.boardLength = boardLength;
		BoardLogic.boardSize = boardSize;
		for (int loc = 0; loc < boardSize; loc++)
			board[loc] = 0;
		board[27] = board[36] = 1;
		board[28] = board[35] = 2;
		lastMove = boardSize / 2;
		turn = 1;
	}

	public static boolean move(int loc) {
		if (isLegal(board, loc, turn)) {
			board = BoardLogic.solve(board, turn, loc);
			turn = (byte) ((turn == 1) ? 2 : 1);
			lastMove = loc;
			return true;
		}
		return false;
	}
	
	public static boolean isLegal(int loc, boolean col) {
		byte ocol = !col;
		int x = loc % boardLength, y = loc / boardLength;
		if (in[loc] != 0)
			return false;

		boolean nearby = false;
		for (int nx = -1; nx < 2; nx++)
			for (int ny = -1; ny < 2; ny++)
				if (x + nx >= 0 && x + nx < boardLength && y + ny >= 0 && y + ny < boardLength && in[x + nx + (y + ny) * boardLength] == ocol) {
					nearby = true;
					break;
				}
		if (!nearby)
			return false;// quickly dismiss moves not touching enemy stones

		for (int nx = -1; nx < 2; nx++)
			for (int ny = -1; ny < 2; ny++) {
				if (nx == 0 && ny == 0)
					continue;
				int cx = x + nx, cy = y + ny;
				if (cx >= 0 && cx < boardLength && cy >= 0 && cy < boardLength && in[x + nx + (y + ny) * boardLength] == ocol) {
					boolean capped = false;
					while (!capped) {
						cx += nx;
						cy += ny;
						if (cx >= 0 && cx < boardLength && cy >= 0 && cy < boardLength) {
							if (in[cx + cy * boardLength] == col)
								return true;
							else if (in[cx + cy * boardLength] == 0)
								break;
						} else
							break;
					}
				}
			}

		return false;
	}

	public static byte[] solve(byte[] in, boolean col, int loc) {
		boolean ocol = !col;
		byte[] solved = new byte[in.length];
		for (int i = 0; i < in.length; i++)
			solved[i] = in[i];
		int x = loc % boardLength, y = loc / boardLength;
		for (int nx = -1; nx < 2; nx++)
			for (int ny = -1; ny < 2; ny++) {
				if (nx == 0 && ny == 0)
					continue;
				int cx = x + nx, cy = y + ny;
				if (cx >= 0 && cx < boardLength && cy >= 0 && cy < boardLength && (solved[x + nx + (y + ny) * boardLength] == 1) == ocol) {
					boolean capped = false;
					while (!capped) {
						cx += nx;
						cy += ny;
						if (cx >= 0 && cx < boardLength && cy >= 0 && cy < boardLength) {
							if ((solved[cx + cy * boardLength] == 1) == col)
								capped = true;
							else if (solved[cx + cy * boardLength] == 0)
								break;
						} else
							break;
					}
					if (capped) {
						cx = x + nx;
						cy = y + ny;
						while (true) {
							if (solved[cx + cy * boardLength] == col)
								break;
							else
								solved[cx + cy * boardLength] = col;
							cx += nx;
							cy += ny;
						}
					}
				}
			}
		solved[loc] = col;
		return solved;
	}
	
	public static boolean isFull() {
		for(byte b : board)
			if(b==0)
				return false;
		return true;
	}
	
	public static boolean canBlackMove() {
		return todo;
	}
	
	public static List<byte[]> children(byte col){
		ArrayList<byte[]> out = new ArrayList<>();
		for (int y = 0; y < boardLength; y++)
			for (int x = 0; x < boardLength; x++) {
				int i = x + y * boardLength;

				if (!BoardLogic.isLegal(in, i, col))
					continue;

				byte[] newBoard = new byte[in.length];
				System.arraycopy(in, 0, newBoard, 0, boardSize);
				newBoard = solve(newBoard, col, i);
				out.add(newBoard);
			}
		return out;
	}
}

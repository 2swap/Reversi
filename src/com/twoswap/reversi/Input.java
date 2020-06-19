package com.twoswap.reversi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.twoswap.reversi.board.Board;
import com.twoswap.reversi.board.BoardLogic;
import com.twoswap.reversi.board.Seat;
import com.twoswap.reversi.graphics.Screen;

public class Input implements MouseListener, MouseMotionListener {
	public Screen screen;

	public Input(Screen screen) {
		this.screen = screen;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!Seat.human) {
			System.out.println();
			int best = 0, bestCount = 0;
			for (int i = 0; i < Seat.orgs.length; i++)
				if (Seat.orgs[i].lastLoss > bestCount) {
					bestCount = Seat.orgs[i].lastLoss;
					best = i;
				}
			for (int i = 0; i < Seat.orgs[best].neurons.length; i++)
				System.out.print((Seat.orgs[best].neurons[i] + "").substring(0, 10) + ", ");
			System.out.println();
		} else {
			if (e.getButton() == 1) {
				int x = e.getX() / 32;
				int y = e.getY() / 32;
				if (Board.move(x + y * Board.boardLength)) {
					int move = Seat.move(Board.board);
					if (move < 0)
						Board.turn = (byte) ((Board.turn == 1) ? 2 : 1);
					else
						Board.move(move);
				}
				boolean legals = false;
				for (int i = 0; i < Board.boardSize; i++)
					if (BoardLogic.isLegal(Board.board, i, Board.turn)) {
						legals = true;
						break;
					}
				if (!legals) {
					Board.turn = (byte) ((Board.turn == 1) ? 2 : 1);
					int move = Seat.move(Board.board);
					if (move < 0)
						Board.turn = (byte) ((Board.turn == 1) ? 2 : 1);
					else
						Board.move(move);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
package com.twoswap.reversi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.twoswap.reversi.board.Board;
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
		/*if (!Seat.human) {
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
		} else {*/
		
		if (e.getButton() == 1) {
			int x = e.getX() / 32;
			int y = e.getY() / 32;
			int loc = x+y*Board.SIZE;
			if(Game.gameBoard.whoseTurn == Board.BLACK && Game.blackSeat.human) Game.blackSeat.humanMove = loc;
			if(Game.gameBoard.whoseTurn == Board.WHITE && Game.whiteSeat.human) Game.whiteSeat.humanMove = loc;
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
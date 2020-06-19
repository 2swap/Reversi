package com.twoswap.reversi;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.twoswap.reversi.board.Board;
import com.twoswap.reversi.board.EvAlg;
import com.twoswap.reversi.board.Seat;
import com.twoswap.reversi.graphics.Screen;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static int width = Board.boardLength * 32, height = width;
	public static String title = "Reversi";
	private JFrame frame;
	private Thread thread;
	private boolean running = false;
	private Screen screen;
	public static int timePlayed = 0;
	private Input in;
	Random random = new Random();
	static Game game;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		screen = new Screen(width, height, this);
		frame = new JFrame();
		in = new Input(screen);
		addMouseListener(in);
	}

	public synchronized void start() {
		for (int i = 0; i < Seat.orgs.length; i++) {
			Seat.orgs[i] = new EvAlg();
			if (!Seat.human)
				for (int j = 0; j < Seat.orgs[i].neurons.length; j++)
					Seat.orgs[i].neurons[j] = Math.random();
		}
		requestFocusInWindow();
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Board.setBoard();
		requestFocus();
		while (running) {
			timePlayed++;
			render();
			if (!Seat.human)
				tick();
		}
		stop();
	}

	public void tick() {
		int move = Seat.move(Board.board);
		if (move < 0 && !Seat.justPassed) {
			Board.turn = (byte) ((Board.turn == 1) ? 2 : 1);
			Seat.justPassed = true;
		} else if (move < 0 && Seat.justPassed)
			Seat.ga();
		else
			Board.move(move);
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		screen.render();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		game = new Game();
		game.frame.setResizable(true);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}
}
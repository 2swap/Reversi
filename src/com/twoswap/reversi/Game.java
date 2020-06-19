package com.twoswap.reversi;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.twoswap.reversi.board.Board;
import com.twoswap.reversi.board.Seat;
import com.twoswap.reversi.graphics.Screen;
import com.twoswap.reversi.strategy.AlphaBeta;
import com.twoswap.reversi.strategy.AlphaBetaCorners;
import com.twoswap.reversi.strategy.Evaporate;
import com.twoswap.reversi.strategy.EvaporateButCorners;
import com.twoswap.reversi.strategy.GreedyCorners;
import com.twoswap.reversi.strategy.GreedyStrategy;
import com.twoswap.reversi.strategy.RandomStrategy;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static int width = Board.SIZE * 32, height = width;
	public static String title = "Reversi";
	private JFrame frame;
	private Thread thread;
	private boolean running = false;
	private Screen screen;
	public static int timer = 0;
	private Input in;
	static Game game;
	public static Board gameBoard;
	public static int bWins = 0, wWins = 0;
	public static Seat blackSeat = new Seat(Board.BLACK);
	public static Seat whiteSeat = new Seat(Board.WHITE, new AlphaBetaCorners(6));
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		gameBoard = new Board();
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		screen = new Screen(width, height, this);
		frame = new JFrame();
		in = new Input(screen);
		addMouseListener(in);
	}

	public synchronized void start() {
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
		requestFocus();
		while (running) {
			timer++;
			tick();
			render();
		}
		stop();
	}

	public void tick() {
		if(timer < 0) return;
		Seat atTurn = gameBoard.whoseTurn == Board.WHITE ? whiteSeat : blackSeat;
		int move = atTurn.move();
		int y = move/Board.SIZE, x = move%Board.SIZE;
		if(move == -1) return;
		if(!gameBoard.isLegal(x, y)) return;
		gameBoard = gameBoard.place(x, y);
		timer = 0;
		if(gameBoard.whoseTurn == Board.EMPTY) {
			int win = gameBoard.getWinner();
			if(win == 2)wWins++;
			if(win == 1)bWins++;
			System.out.println(Screen.names[win] + " b:" + bWins + " w:" + wWins);
			gameBoard.resetBoard();
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		screen.render(gameBoard);
		for (int i = 0; i < pixels.length; i++) pixels[i] = screen.pixels[i];
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
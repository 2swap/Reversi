package com.twoswap.reversi.strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.twoswap.reversi.board.Board;

public class NeuralNetwork extends Strategy {
	public int layer1length = Board.SIZE*Board.SIZE+2;
	public int layer2length = 32;
	public int layer3length = 16;
	public double[] neurons = new double[layer1length*layer2length+layer2length*layer3length+layer3length*1];
	public String myPath;
	public boolean immortal;

	public NeuralNetwork(boolean immortal) {
		File[] files = new File("res/").listFiles();
		File file = files[(int) (Math.random()*files.length)];
		myPath = "res/"+file.getName();
		readCSV(myPath);
		this.immortal = immortal;
	}

	@Override
	public double evaluateAsBlack(Board b) {
		double[] layer1 = new double[layer1length];
		for(int i = 0; i < Board.SIZE*Board.SIZE; i++)
			layer1[i] = colorToNeuron(b.board[i/Board.SIZE][i%Board.SIZE]);
		layer1[layer1.length-2] = colorToNeuron(b.whoseTurn);
		layer1[layer1.length-1] = 1;
		double[] layer2 = new double[layer2length];
		int currNeuron = 0;
		for(int i = 0; i < layer1.length; i++)
			for(int j = 0; j < layer2.length; j++) {
				layer2[j] += layer1[i]*neurons[currNeuron];
				++currNeuron;
			}
		double[] layer3 = new double[layer3length];
		for(int i = 0; i < layer2.length; i++)
			for(int j = 0; j < layer3.length; j++) {
				layer3[j] += layer2[i]*neurons[currNeuron];
				++currNeuron;
			}
		double result = 0;
		for(int i = 0; i < layer3.length; i++) {
			result += layer3[i]*neurons[currNeuron];
			++currNeuron;
		}
		return result;
	}
	
	public int colorToNeuron(byte b) {
		return b == 2 ? -1 : b;
	}
	
	public void onWin() {
		if (immortal) return;
		double[] newNeurons = neurons.clone();
		for(int i = 0; i < newNeurons.length; i++)
			newNeurons[i] += Math.tan(Math.random()*Math.PI);
		
		int numFiles = new File("res/").list().length;
		for(int i = numFiles; i<100; i++) {
			try {
				FileWriter writer = new FileWriter("res/neurons"+Math.random()+".csv");
				for (int j = 0; j < newNeurons.length; j++) {
					writer.append(String.valueOf(newNeurons[j]));
					writer.append(", ");
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onLose() {
		if (immortal) return;
		File file = new File(myPath);
		file.delete();
	}
	
	public void readCSV(String path) {
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(path));
			int i = 0;
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] values = line.replaceAll(" ", "").split(",");
				for (String str : values) {
					if(str.length() == 0) continue;
					neurons[i] = Double.parseDouble(str);
					i++;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double sigmoid(double x) {
		double exp = Math.exp(x);
		return exp/(exp+1);
	}

}

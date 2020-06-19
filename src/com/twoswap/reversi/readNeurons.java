package com.twoswap.reversi;

import java.io.BufferedReader;
import java.io.FileReader;

public class readNeurons {
	
	public static double[] getCSV() {
		double[] neurons = new double[10];
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader("res/neurons.txt"));
			int i = 0;
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				for (String str : values) {
					String edited = str.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(" ", "");
					neurons[i] = Double.parseDouble(edited);
					i++;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return neurons;
	}
}
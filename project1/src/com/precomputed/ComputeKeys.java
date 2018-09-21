package com.precomputed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class ComputeKeys {
	
	private HashMap<String, String> character_freq;
	HashMap<String,String> keyMapping;
	private int min = 0;
	private int max = 105;
	private String[][] encrypting_keyguess;
	private HashSet<String> dictionary;
	
	/**
	 * Junit -> com.junits.PreComputed
	 * 
	 * This function reads the frequency of the key mapping
	 */
	public ComputeKeys() { 
		dictionary = new HashSet<String>();
		character_freq = new HashMap<String, String>();
		encrypting_keyguess = new String[27][];
		try {
			File file = new File("resources/frequency.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while (null != (line = br.readLine())) {
				String[] keys = line.split(",");
				character_freq.put(keys[0], keys[1]);
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String,String> getRandomKeyAllocation() {
		keyAllocations();
		return keyMapping;
	}
	
	private void keyAllocations() {
		
		this.keyMapping = new HashMap<String, String>(); 
		ArrayList<Integer> numbersGenerated = new ArrayList<Integer>();
		int counter = 0;
		for (String value : character_freq.keySet()) {
			int no_of_allocs_for_eachkey = Integer.parseInt(character_freq.get(value));
			encrypting_keyguess[counter] = new String[no_of_allocs_for_eachkey + 1];
			encrypting_keyguess[counter][0] = value;
			while (no_of_allocs_for_eachkey > 0) {
				int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
				if (!numbersGenerated.contains(randomNum)) {
					numbersGenerated.add(randomNum);
					encrypting_keyguess[counter][no_of_allocs_for_eachkey] = String.valueOf(randomNum);
					no_of_allocs_for_eachkey--;
					if (value.equals("space"))
						keyMapping.put(String.valueOf(randomNum), " ");
					else
						keyMapping.put(String.valueOf(randomNum), value);
				}
			}
			counter++;
		}
		/*for (int i = 0; i <= 26; i++) {
			for (int j = 0 ; j < encrypting_keyguess[i].length; j++) {
				System.out.print(encrypting_keyguess[i][j] + "|");
			}
			System.out.println();
		}*/
 	}
	
	private void printKey() {
		try {
		File file = new File("resources/key_temp.txt");
		FileWriter fw = new FileWriter(file);
		for (int i = 0; i <= 26; i++) {
			for (int j = 0 ; j < encrypting_keyguess[i].length; j++) {
				fw.write(encrypting_keyguess[i][j] + "|");
			}
			fw.write("\n");
		}
		fw.close();}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public HashSet<String> getDictionary() {
		return dictionary;
	}
	
	public HashMap<String,String> getCharacterFrequency() {
		return character_freq;
	}
	
	public String[][] getEncryptKeys() {
		printKey();
		return encrypting_keyguess;
	}
}

package main.java.org.nyu.decrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import main.java.org.nyu.precomputed.ComputeKeys;

public class Decrypt {
	
	private String[] ciphertext;
	private ArrayList<String> st;
	private HashMap<String, Double> frequency_maps;
	private ComputeKeys ck;
	private HashSet<String> dictionary;
	
	public Decrypt(String ciphertext) {
		
		this.ciphertext = ciphertext.split(",");
		this.st = new ArrayList<String>();
		this.frequency_maps = new HashMap<String, Double>();
		this.dictionary = new HashSet<String>();
		this.ck = new ComputeKeys();
		try {
			File file = new File("resources/dictionary.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while (null != (line = br.readLine())) {
				dictionary.add(line);
			}
			br.close();
			br = null;
		}catch(Exception e) {
			e.printStackTrace();
		}
		string_Deduced_Data();
		attemptDecrypt();
		//subsequence(this.ciphertext);
	}
	
	private void string_Deduced_Data() {
		
		int length = this.ciphertext.length;
		HashMap<String,Integer> cipher_letter_counts = new HashMap<String,Integer>();
		for (String val : this.ciphertext) {
			if (cipher_letter_counts.containsKey(val)) {
				cipher_letter_counts.put(val, cipher_letter_counts.get(val) + 1);
			} else {
				cipher_letter_counts.put(val, 1);
			}
		}
		System.out.println(length);
		/*for (String letter: cipher_letter_counts.keySet()) {
			double frequency = (double)cipher_letter_counts.get(letter)/(double)length;
			frequency_maps.put(letter, frequency);
			System.out.println(letter + " --> " + cipher_letter_counts.get(letter) + " --> " + frequency);
		}*/
	}
	
	private void attemptDecrypt() {
		
		boolean message_found = false;
		StringBuilder presumable_message = null;
		LocalTime start = LocalTime.now();
		System.out.println(start);
		while (!message_found) {
			presumable_message = new StringBuilder();
			HashMap<String, String> keymaps = ck.getRandomKeyAllocation();
			for (String value : this.ciphertext) {
				presumable_message.append(keymaps.get(value));
			}
			if (dictionary.contains(presumable_message.toString())) {
				message_found = true;
			}
			//System.out.println("Discarded message can is : "+ presumable_message.toString());
		}
		LocalTime end = LocalTime.now();
		System.out.println(end);
		System.out.println("The message can be : "+ presumable_message.toString());
	}
	
}

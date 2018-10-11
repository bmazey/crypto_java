package org.nyu.decrypt;

import java.util.ArrayList;

import org.nyu.dto.Candidates;

public class Strategy {

	private int low;
	private int high;
	private boolean found;
	private int value;
	private int indexOfMessage;
	private String cipher;
	private String[] candidates;
	private ArrayList<String> countOfSingleLetter;

	public Strategy(String cipher, int low, int high, Candidates candidates) {
		this.value = -1;
		this.found = false;
		this.cipher = cipher;
		this.low = low;
		this.high = high;
		this.candidates = candidates.getCandidates();
		this.countOfSingleLetter = new ArrayList<String>();
		makeCount("z");
	}

	public void run() {

		for (int i = low; i <= high; i++) {
			String[] cipher_temp = cipher.split(",");
			String number_to_be_replaced = String.valueOf(i);
			StringBuilder temp = new StringBuilder();
			for (int j = 0; j < cipher_temp.length - 1; j++) {
				String s = cipher_temp[j];
				if (s.equalsIgnoreCase(number_to_be_replaced)) {
					temp.append("z" + ",");
				} else {
					temp.append(cipher_temp[j] + ",");
				}
			}
			if (cipher_temp[cipher_temp.length - 1].equalsIgnoreCase(number_to_be_replaced)) {
				temp.append("z" + ",");
			} else {
				temp.append(cipher_temp[cipher_temp.length - 1]);
			}
			String positions = returnCountOfPositions(temp.toString(), "z");
			if (countOfSingleLetter.contains(positions)) {
				setResult();
				setValue(i);
				this.indexOfMessage = countOfSingleLetter.indexOf(positions);
			}
			if (getFound())
				break;
		}
	}

	public synchronized void setResult() {
		found = true;
	}

	public synchronized boolean getFound() {
		return found;
	}

	public synchronized void setValue(int value) {
		this.value = value;
	}

	public synchronized int getValue() {
		return value;
	}

	public int getIndexOfMessage() {
		return indexOfMessage;
	}

	/**
	 * count the occurrences of a single character in the message space
	 * 
	 * @param letter
	 */
	private void makeCount(String letter) {

		for (int i = 0; i < candidates.length; i++) {
			countOfSingleLetter.add(returnCountOfPositions(candidates[i], letter));
		}
	}

	/**
	 * 
	 * @param temp
	 * @param letter
	 * @return
	 */
	private String returnCountOfPositions(String temp, String letter) {

		StringBuilder positions = new StringBuilder();
		String[] splits = temp.split(",");
		ArrayList<String> pos = new ArrayList<String>();
		for (int loop = 0; loop < splits.length; loop++) {
			if (splits[loop].equalsIgnoreCase(letter)) {
				pos.add(String.valueOf(loop));
			}
		}
		if (!pos.isEmpty()) {
			for (int j = 0; j < pos.size() - 1; j++) {
				positions.append(pos.get(j) + ",");
			}
		} else {
			positions.append("-1");
		}
		if (!pos.isEmpty())
			positions.append(pos.get(pos.size() - 1));
		return positions.toString();
	}
}

package org.nyu.dto;

public class Frequency {

	/**
	 * This class represents frequency POJO b.mazey@nyu.edu
	 */

	private String letter;
	private int frequency;

	public Frequency() {

	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return "Frequency [letter=" + letter + ", frequency=" + frequency + "]";
	}
}

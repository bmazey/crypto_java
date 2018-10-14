package org.nyu.dto;

public class Key {

	private String letter;
	private int array[];

	public Key(String value, int size) {
		this.letter = value;
		this.array = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = -1;
		}
	}

	/**
	 * Returns the letter assigned
	 * 
	 * @return
	 */
	public String getLetter() {
		return this.letter;
	}

	/**
	 * Set the letter a..b for space we will use " "
	 * 
	 * @param letter
	 */
	public void setLetter(String letter) {
		this.letter = letter;
	}

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	/**
	 * Set the values at the exact position. pos can take values from 1 to size. It
	 * follows 1..(size-1)
	 * 
	 * @param num
	 * @param pos
	 */
	public void setAtPosition(int num, int pos) {
		if (this.array[pos] == -1)
			this.array[pos] = num;
	}

	/**
	 * 
	 * @param num
	 */
	public void setAtPosition(int num) {

		for (int pos = 0; pos < this.array.length; pos++)
			if (this.array[pos] == -1) {
				this.array[pos] = num;
				break;
			}
	}
}

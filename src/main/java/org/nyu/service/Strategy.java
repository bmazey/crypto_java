package org.nyu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nyu.dto.Candidates;
import org.nyu.dto.Dictionary;
import org.nyu.dto.Frequency;
import org.springframework.core.io.ClassPathResource;

public class Strategy {

	private int low;
	private int high;
	private boolean found;
	private int value;
	private int indexOfMessage;
	private String cipher;
	private String[] candidates;
	private ArrayList<String> countOfSingleLetter;
	private Dictionary dictionary;
	private String[] cipher_split;
	private HashMap<String, Integer> occurences;
    private HashMap<String, Integer> frequencies;
	private StringBuilder temp_decrypt_string;
	private String[] partly_decrypt_string;

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

	public Strategy(String cipher) {
		this.cipher = cipher;
        this.cipher_split = this.cipher.split(",");
        this.partly_decrypt_string = null;
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

	public void runStrategy2() {

	    try{
            ObjectMapper mapper = new ObjectMapper();
            // read JSON file to prepare the key map
            this.dictionary = mapper.readValue(new ClassPathResource("dictionary.json").getInputStream(), Dictionary.class);
            Frequency[] frequencies = mapper.readValue(new ClassPathResource("frequency2.json").getInputStream(), Frequency[].class);
            this.frequencies = new HashMap<String,Integer>();
            this.occurences = new HashMap<String,Integer>();
            for (Frequency temp : frequencies) {
                this.frequencies.put(temp.getLetter(), temp.getFrequency());
                this.occurences.put(temp.getLetter(), 0);
            }
            frequencies = null;
            HashMap<String, String> conjectureMap = new HashMap<String, String>();
            for (String word : this.dictionary.getWords()) {
                String[] copy_cipher_comma = new String[500];
                copy_cipher_comma = Arrays.copyOf(this.cipher_split, this.cipher_split.length);
                this.temp_decrypt_string = new StringBuilder();
                if (!runBackTrack(conjectureMap, copy_cipher_comma, word, 0)){
                    System.out.print(".");
                    conjectureMap = new HashMap<String, String>();
                    for (String key : this.occurences.keySet()) {
                        this.occurences.put(key, 0);
                    }
                }
                else {
                    String[] split = partly_decrypt_string;
                    this.temp_decrypt_string = new StringBuilder();
                    for (int i = 0; i < partly_decrypt_string.length; i++) {
                        if (conjectureMap.containsKey(split[i])) {
                            this.temp_decrypt_string.append(conjectureMap.get(split[i]));
                        } else {
                            this.temp_decrypt_string.append(split[i]);
                        }
                    }
                    System.out.println(this.temp_decrypt_string.toString());
                    /*for (String value : conjectureMap.values()) {
                        int count = 0;
                        for (String key : conjectureMap.keySet()) {
                            if (conjectureMap.get(key).equalsIgnoreCase(value)) {
                                count++;
                            }
                        }
                        System.out.println(value + " : " + count);
                    }*/
                    break;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
	}

	private boolean runBackTrack(HashMap<String, String> map, String[] copy_cipher_comma, String word, int current) {
        boolean correct = true;
        HashMap<String, String> temp_conjecture_map = new HashMap<String, String>();
        HashMap<String, Integer> temp_occurences = new HashMap<String, Integer>();
        String[] temp_copy_cipher_text = new String[500];
        if (map.size() < 91) {
            String[] comma_word = word.replaceAll(".(?!$)", "$0,").split(",");
            int comma_word_counter = 0;
            for (int loop = current ; loop< current + comma_word.length ; loop++,comma_word_counter++) {
                if (map.containsKey(copy_cipher_comma[loop])) {
                    // If a number in the cipher text is already present
                    // then we check if the value of the letter mapped
                    // is same as the one that we have assigned
                    if (!map.get(copy_cipher_comma[loop]).equalsIgnoreCase(comma_word[comma_word_counter]))
                        return false;
                } else {
                    map.put(copy_cipher_comma[loop], comma_word[comma_word_counter]);
                    if (this.occurences.containsKey(comma_word[comma_word_counter])) {
                        int value = this.occurences.get(comma_word[comma_word_counter]) + 1;
                        int limit = this.frequencies.get(comma_word[comma_word_counter]);
                        if (value > limit)
                            return false;
                        this.occurences.put(comma_word[comma_word_counter], value);
                    }
                }
                copy_cipher_comma[loop] = comma_word[comma_word_counter];
            }
            map.put(copy_cipher_comma[current + comma_word.length ]," ");
            copy_cipher_comma[current + comma_word.length ] = " ";
            current = current + comma_word.length + 1;
            for (String number : map.keySet()) {
                temp_conjecture_map.put(number, map.get(number));
            }
            for (String number : this.occurences.keySet()) {
                temp_occurences.put(number, this.occurences.get(number));
            }
            for (String word_recursion : dictionary.getWords()){
                temp_copy_cipher_text = Arrays.copyOf(copy_cipher_comma, copy_cipher_comma.length);
                correct = runBackTrack(map, copy_cipher_comma, word_recursion, current);
                if (!correct) {
                    copy_cipher_comma = Arrays.copyOf(temp_copy_cipher_text, temp_copy_cipher_text.length);
                    map.clear();
                    for (String number : temp_conjecture_map.keySet()) {
                        map.put(number, temp_conjecture_map.get(number));
                    }
                    for (String number : temp_occurences.keySet()) {
                        this.occurences.put(number, temp_occurences.get(number));
                    }
                } else {
                    if (null == partly_decrypt_string ){
                        this.partly_decrypt_string = new String[500];
                        this.partly_decrypt_string = Arrays.copyOf(copy_cipher_comma, copy_cipher_comma.length);
                    }
                    return correct;
                }
            }
            return correct;
        }
        else {
            return true;
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

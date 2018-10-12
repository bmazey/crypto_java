package org.nyu.decrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.nyu.dto.Candidates;
import org.nyu.dto.Dictionary;
import org.nyu.dto.Key;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nyu.statics.StaticValues;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Decrypt {

	private String ciphertext;
	private CreateKeyMap keyMap;
	private Candidates candidates;
	private Dictionary dictionary;
	private ArrayList<String> listOfWords;
	private HashMap<Integer, String> keyMapping;
	private boolean complete;

	public Decrypt(String ciphertext, String strategy) {

		this.complete = true;
		this.ciphertext = ciphertext;
		this.keyMap = new CreateKeyMap();
		string_Deduced_Data();
		ObjectMapper mapper = new ObjectMapper();
		this.keyMapping = new HashMap<Integer, String>();
		if (new ClassPathResource("key.txt").exists()) {
			this.keyMap.createKeyList();
			try {
				BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource("key.txt").getFile()));
				String line = br.readLine();
				while (null != line) {
					String[] splits = line.split(",");
					for (int i = 1; i < splits.length; i++) {
						if (!splits[i].equalsIgnoreCase("-1"))
							if (splits[0].equalsIgnoreCase("space"))
								keyMapping.put(Integer.parseInt(splits[i]), " ");
							else
								keyMapping.put(Integer.parseInt(splits[i]), splits[0]);
						else
							this.complete = false;
					}
					line = br.readLine();
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.complete = false;
		}
		if (strategy.equalsIgnoreCase("1")) {
			try {
				File file = new ClassPathResource("candidates.json").getFile();
				this.candidates = mapper.readValue(file, Candidates.class);
				this.candidates.modifyCandidates();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(ciphertext);
			try {
				attemptDecryptByStrategy1();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ( strategy.equalsIgnoreCase("2")) {
			try {
				File file = new ClassPathResource("dictionary.json").getFile();
				this.dictionary = mapper.readValue(file, Dictionary.class);
				this.listOfWords = new ArrayList<String>(Arrays.asList(dictionary.getWords()));
				attemptDecryptByStrategy2();
			} catch(Exception e) {
                e.printStackTrace();
			}
		} else {
			System.out.print("Erroneous input!!");
		}

	}

	private void string_Deduced_Data() {

		String[] ciphertext = this.ciphertext.split(",");
		int length = ciphertext.length;
		HashMap<String, Integer> cipher_letter_counts = new HashMap<String, Integer>();
		for (String val : ciphertext) {
			if (cipher_letter_counts.containsKey(val)) {
				cipher_letter_counts.put(val, cipher_letter_counts.get(val) + 1);
			} else {
				cipher_letter_counts.put(val, 1);
			}
		}
		System.out.println(length);
		/*
		 * for (String letter: cipher_letter_counts.keySet()) { double frequency =
		 * (double)cipher_letter_counts.get(letter)/(double)length;
		 * frequency_maps.put(letter, frequency); System.out.println(letter + " --> " +
		 * cipher_letter_counts.get(letter) + " --> " + frequency); }
		 */
	}

	private void attemptDecryptByStrategy2() {

	    try {
	        /*boolean bb_presence = false;
	        ArrayList<Integer> listOfDuplicates = null;
            String[] cipher_temp = this.ciphertext.split(",");
	        for (int i = 2; i < cipher_temp.length - 1; i++){
                if (cipher_temp[i].equalsIgnoreCase(cipher_temp[i+1])) {
                    bb_presence = true;
                    if (null == listOfDuplicates)
                        listOfDuplicates = new ArrayList<Integer>();
                    listOfDuplicates.add(i);
                    keyMapping.put(Integer.valueOf(cipher_temp[i]), "b");
                }
            }
            if (bb_presence) {

            }*/
            boolean exists = new ClassPathResource("key.txt").exists();
            if (!exists) {
                Strategy strategy = new Strategy(this.ciphertext);
                strategy.runStrategy2();
            } else {
                System.out.println("Key is present");
            }
        } catch(Exception e) {
	        e.printStackTrace();
        }
    }

	private void attemptDecryptByStrategy1() throws IOException {

		boolean exists = new ClassPathResource("key.txt").exists();
		if (!exists) {
			Strategy strategy = new Strategy(this.ciphertext, 0, 105, this.candidates);
			strategy.run();
			int index = strategy.getIndexOfMessage();
			System.out.println("The message is-------");
			StringBuilder message_decrypted = new StringBuilder();
			String[] arr = candidates.getCandidates()[index].split(",");
			for (String s : arr) {
				message_decrypted.append(s);
			}
			System.out.println(message_decrypted.toString());
			mapKey(message_decrypted.toString(), candidates.getCandidates()[index]);
		} else {
			StringBuilder part_key = new StringBuilder();
			String[] cipher_temp = this.ciphertext.split(",");
			for (int j = 0; j < cipher_temp.length; j++) {
				int individual_cipher_text = Integer.parseInt(cipher_temp[j]);
				if (this.keyMapping.containsKey(individual_cipher_text)) {
					part_key.append(String.valueOf(this.keyMapping.get(individual_cipher_text)) + ",");
				} else {
					part_key.append(cipher_temp[j] + ",");
				}
			}
			if (!this.complete) {
				String match_of_key = this.candidates.returnMatchString(part_key.toString());
				System.out.println("=======================");
				System.out.println(match_of_key);
			}
		}
	}

	private void mapKey(String message, String message_comma_seperated) {

		try {
			boolean exists = new ClassPathResource("key.txt").exists();
			if (!exists) {
				File file = new File("src/main/resources/key.txt");
				Key[] keyList = keyMap.getKeyList();
				HashMap<String, Integer> map = keyMap.getFrequencyMap();
				String[] cipher_temp = this.ciphertext.split(",");
				String[] message_temp = message_comma_seperated.split(",");
				keyMapping = new HashMap<Integer, String>();
				for (int i = 0; i < cipher_temp.length; i++) {
					int length_of_list = 0;
					if (message_temp[i].equalsIgnoreCase(" ")) {
						length_of_list = map.get("space");
					} else {
						length_of_list = map.get(message_temp[i]);
					}
					int location = i % length_of_list;
					if (message_temp[i].equalsIgnoreCase(" ")) {
						keyList[0].setAtPosition(Integer.parseInt(cipher_temp[i]), location);
					} else {
						int value = message.charAt(i) - 'a' + 1;
						keyList[value].setAtPosition(Integer.parseInt(cipher_temp[i]), location);
					}
					keyMapping.put(Integer.parseInt(cipher_temp[i]), message_temp[i]);
				}
				if (keyMapping.keySet().size() == 106) {
					System.out.println("Complete key");
					this.complete = true;
				} else {
					System.out.println("Key set incomplete by " + (106 - keyMapping.keySet().size()));
				}
				keyMap.setKeyList(keyList);
				FileWriter fw = new FileWriter(file);
				for (Key key : keyList) {
					StringBuilder temp = new StringBuilder(key.getLetter());
					for (int val : key.getArray()) {
						temp.append("," + String.valueOf(val));
					}
					temp.append("\n");
					fw.write(temp.toString());
				}
				fw.close();
				fw = null;
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

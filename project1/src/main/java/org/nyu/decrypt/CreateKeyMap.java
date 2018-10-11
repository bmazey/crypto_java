package org.nyu.decrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import org.nyu.dto.Frequency;
import org.nyu.dto.Key;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Idea is to prepare the key map as we decrypt or run decryption strategies on
 * the cipher
 * 
 * @author sb6856
 *
 */
public class CreateKeyMap {

	private Key[] keyList;
	private HashMap<String, Integer> frequencyMap;

	public CreateKeyMap() {

		frequencyMap = new HashMap<String, Integer>();
		keyList = new Key[27];
		try {
			ObjectMapper mapper = new ObjectMapper();
			// read JSON file to prepare the key map
			Frequency[] lengthOfList = mapper.readValue(new File("resources/frequency.json"), Frequency[].class);
			int i = 0;
			for (Frequency list : lengthOfList) {
				keyList[i] = new Key(list.getLetter(), list.getFrequency());
				frequencyMap.put(list.getLetter(), list.getFrequency());
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> getFrequencyMap() {
		return frequencyMap;
	}

	public Key[] getKeyList() {
		return keyList;
	}

	public void setKeyList(Key[] keyList) {
		this.keyList = keyList;
	}

	public void createKeyList() {

		File file = new File("resources/key.txt");
		try {
			int count = 0;
			BufferedReader br = new BufferedReader(new FileReader(new File("resources/key.txt")));
			String line = br.readLine();
			while (null != line) {
				String[] splits = line.split(",");
				if (splits[0].equalsIgnoreCase("space"))
					keyList[count].setLetter(" ");
				else
					keyList[count].setLetter(splits[0]);
				for (int i = 1; i < splits.length; i++) {

				}
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

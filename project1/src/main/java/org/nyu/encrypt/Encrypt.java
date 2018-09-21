package main.java.org.nyu.encrypt;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import main.java.org.nyu.precomputed.ComputeKeys;

public class Encrypt {
	
	private ComputeKeys ck;
	private String encryptedMessage;
	
	public Encrypt(String message) {
		HashMap<String, Integer> loc = new HashMap<String, Integer>();
		loc.put("space", 0);
		int counter = 97;
		for (int i=1;i<27;i++) {
			char val = (char)counter;
			loc.put(String.valueOf(val),i);
			counter++;
		}
		this.ck = new ComputeKeys();
		this.ck.getRandomKeyAllocation();
		String[][] key = this.ck.getEncryptKeys();
		HashMap<String,String> characterFrequency = this.ck.getCharacterFrequency();
		StringBuilder encryptedMessage = new StringBuilder();
		for(int i=0; i < message.length() - 1; i++) {
			String chartobeencoded = null; 
			if (message.charAt(i) == ' ') {
				chartobeencoded = "space";
			} else {
				chartobeencoded = String.valueOf(message.charAt(i));
			}
			int value = Integer.parseInt(characterFrequency.get(chartobeencoded));
			int randomNum = ThreadLocalRandom.current().nextInt(1, value + 1);
			encryptedMessage.append(String.valueOf(key[loc.get(chartobeencoded)][randomNum])+",");
		}
		String chartobeencoded = null;
		if (message.charAt(message.length() - 1) == ' ') {
			chartobeencoded = "space";
		} else {
			chartobeencoded = String.valueOf(message.charAt(message.length() - 1));
		}
		int value = Integer.parseInt(characterFrequency.get(chartobeencoded));
		int randomNum = ThreadLocalRandom.current().nextInt(1, value + 1);
		encryptedMessage.append(String.valueOf(key[loc.get(chartobeencoded)][randomNum]));
		this.encryptedMessage = encryptedMessage.toString(); 
	}
	
	public String getEncryptedMessage() {
		
		return this.encryptedMessage;
	}
}


import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

import org.nyu.encrypt.Encrypt;

public class EncryptJunit {

	@Test
	public void encryptmessage() {

		System.out.println("Enter the message");
		Scanner sc = new Scanner(System.in);
		String message = sc.nextLine();
		sc.close();
		if (message.length() > 500) {
			message = message.substring(0, 500);
		}
		Encrypt encrypt = new Encrypt(message);
		System.out.println(message.length());
		String ciphertext = encrypt.getEncryptedMessage();
		System.out.println("The cipher text is :"+ciphertext);
		String[] split = ciphertext.split(",");
		System.out.println(split.length);
		HashMap<String, Integer> frequency_maps = new HashMap<String, Integer>();
		for (String letter: split) {
			if (frequency_maps.containsKey(letter)) {
				int count = frequency_maps.get(letter) + 1;
				frequency_maps.put(letter, count);
			} else {
				frequency_maps.put(letter, 0);
			}
		}
		for (String val: frequency_maps.keySet()) {
			System.out.println(val + "--->" + frequency_maps.get(val));
		}
	}
}

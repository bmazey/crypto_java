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
			message = message.substring(0, 499);
		}
		Encrypt encrypt = new Encrypt(message);
		String ciphertext = encrypt.getEncryptedMessage();
		System.out.println("The cipher text is :"+ciphertext);
	
	}
	
	
}

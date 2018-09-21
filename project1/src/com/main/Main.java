package com.main;

import java.util.Scanner;

import com.decrypt.Decrypt;
import com.encrypt.Encrypt;

public class Main {

	public static void main(String[] args) {

		//System.out.print("Enter cipher text(every letter has to be keyed in comma-separated):");
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
		Decrypt decrypt = new Decrypt(ciphertext);
	}
}

package org.nyu.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


@Service
public class Encryptor {

    public int[] encrypt(HashMap<String, ArrayList<Integer>> keyMap, String plaintext){

        Random r = new Random();
        int[] ciphertextArray = new int[plaintext.length()];


        for(int i = 0; i < plaintext.length(); i++) {

            if (plaintext.charAt(i) == ' ') {
                ArrayList<Integer> temp = keyMap.get("space");
                int cipher = temp.get(r.nextInt(temp.size()));
                ciphertextArray[i] = cipher;
                continue;
            }

            ArrayList<Integer> temp = keyMap.get(Character.toString(plaintext.charAt(i)));
            int cipher = temp.get(r.nextInt(temp.size()));
            ciphertextArray[i] = cipher;

        }

        return ciphertextArray;

    }
}

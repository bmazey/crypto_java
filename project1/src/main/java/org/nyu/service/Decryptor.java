package org.nyu.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


@Service
public class Decryptor {

    public String decrypt(HashMap<String, ArrayList<Integer>> map, int[] ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for(int i = 0; i < ciphertext.length; i++) {

            for(String key : map.keySet()) {
                ArrayList values = map.get(key);
                if (values.contains(ciphertext[i])) {

                    //This is bad - don't concat strings this way!
                    if (key.equals("space")) plaintext.append(" ");
                    else plaintext.append(key);
                }
            }
        }

        return plaintext.toString();
    }

    public String convertIntArrayToCsvString(int[] array) {
        StringBuilder builder = new StringBuilder();
        for (int i : array) {
            builder.append(i + ",");
        }

        // chop off last comma
        return builder.delete(builder.length() - 1, builder.length()).toString();
    }
}

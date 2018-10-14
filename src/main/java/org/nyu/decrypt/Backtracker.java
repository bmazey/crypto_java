package org.nyu.decrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nyu.dto.Dictionary;
import org.nyu.dto.FrequencyPojo;
import org.nyu.service.Decryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Backtracker {

    @Autowired
    Decryptor decryptor;

    private Dictionary dictionary;
    private FrequencyPojo frequency;
    private String ciphertext;
    private Random random;
    private HashMap<String, Integer> frequencyMap;
    private StringBuilder builder;
    private String plaintext;

    @SuppressWarnings("unchecked")
    public Backtracker() {

        random = new Random();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File frequencyFile = new ClassPathResource("frequency.json").getFile();
            File dictionaryFile = new ClassPathResource("dictionary.json").getFile();
            dictionary = objectMapper.readValue(dictionaryFile, Dictionary.class);
            frequency = objectMapper.readValue(frequencyFile, FrequencyPojo.class);
            frequencyMap = objectMapper.readValue(frequencyFile, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void backtrack(int[] ciphertext) {

        HashMap<String, ArrayList<Integer>> conjectureMap = new HashMap<>();

        //copy key set from frequency map to conjecture map
        for (String key : frequencyMap.keySet()) {
            conjectureMap.put(key, new ArrayList<>());
        }

        maptrack(conjectureMap, dictionary.getWords(), ciphertext, 0);

    }


    public void maptrack(HashMap<String, ArrayList<Integer>> map, String[] words, int[] ciphertext, int position) {

        // start with a copy of the conjectureMap for this occurrence
        HashMap<String, ArrayList<Integer>> copy = copymap(map);

        if (!assertFrequency(frequencyMap, copy)) {
            // this violates the frequency!
            return;
        }

        // print map here
        System.out.println("here's our map at position: " + position);
        for (String key : copy.keySet()) {
            System.out.println(key + " : " + Arrays.toString(copy.get(key).toArray()));
        }


        // here is our base case - if we have found a valid mapping for each number in the ciphertext, we can attempt
        // to decrypt.
        ArrayList<Integer> values = new ArrayList<>();
        for (ArrayList<Integer> list :  copy.values()) {
            values.addAll(list);
        }

        boolean ready = true;
        for (int i = 0; i < ciphertext.length; i++) {
            if (!values.contains(ciphertext[i])) ready = false;
        }

        if (ready) {
            plaintext = decryptor.decrypt(copy, ciphertext);
            System.out.println("success!");
            return;
        }


        // ok now we have to build the map based on a guess
        // guess each word until we find a word that fits
        for (String word : words) {

            // here's our dummy break case ...
            if (position + word.length() + 1 > ciphertext.length) {
                System.out.println("dummy break case reached!");
                // TODO - FIXME!
                return;
            }

            int k = 0;
            for (int i = position; i < position + word.length(); i++) {
                String key = Character.toString(word.charAt(k));
                ArrayList<Integer> list = copy.get(key);

                // no cipher value should be duplicated in the key
                if (isUnique(copy, ciphertext[i])) {
                    list.add(ciphertext[i]);
                }

                copy.put(key, list);
                k++;
            }

            // handle space case
            ArrayList<Integer> list = copy.get("space");
            if (isUnique(copy, ciphertext[position + word.length() + 1])) {
                list.add(ciphertext[position + word.length() + 1]);
                copy.put("space", list);
            }

            position += word.length() + 2;
            maptrack(copy, dictionary.getWords(), ciphertext, position);
        }
    }

    public HashMap<String, ArrayList<Integer>> copymap(HashMap<String, ArrayList<Integer>> map) {
        HashMap<String, ArrayList<Integer>> copy = new HashMap<>();
        for (String key : map.keySet()) {
            ArrayList<Integer> list = map.get(key);
            copy.put(key, list);
        }
        return copy;
    }

    public boolean isUnique(HashMap<String, ArrayList<Integer>> map, int value) {
        boolean unique = true;
        for (ArrayList<Integer> list: map.values()) {
            if (list.contains(value)) unique = false;
        }
        return unique;
    }

    // first map is original frequency map, second is conjecture map
    public boolean assertFrequency(HashMap<String, Integer> map1, HashMap<String, ArrayList<Integer>> map2) {
        for (String key : map1.keySet()) {
            if (map2.get(key).size() > map1.get(key))
                return false;
        }
        return true;
    }

//    public static void main(String[] args) {
//
//        int[] ciphertext = {11, 103, 22, 70, 44, 102, 27, 58, 74, 52, 3, 12, 86, 76, 44, 91, 14, 99, 66, 93, 104, 61, 59, 76, 78, 99, 31, 15, 82, 40, 20, 81, 66, 62, 4, 35, 87, 17, 65, 28, 11, 102, 39, 103, 104, 52, 72, 99, 1, 103, 5, 83, 59, 28, 100, 73, 47, 34, 22, 28, 84, 47, 86, 82, 95, 99, 60, 77, 76, 18, 22, 7, 89, 14, 44, 62, 60, 89, 87, 103, 90, 7, 56, 41, 87, 28, 54, 39, 97, 55, 5, 88, 75, 25, 3, 40, 39, 53, 38, 70, 23, 102, 9, 58, 74, 16, 0, 56, 55, 83, 85, 25, 86, 76, 34, 40, 60, 40, 97, 70, 27, 102, 59, 25, 97, 2, 47, 29, 76, 3, 12, 45, 1, 93, 75, 39, 38, 12, 86, 40, 85, 88, 65, 28, 39, 57, 74, 91, 30, 36, 98, 7, 31, 99, 33, 75, 65, 105, 27, 51, 97, 25, 59, 93, 8, 84, 60, 94, 22, 70, 84, 3, 4, 63, 44, 46, 31, 40, 58, 35, 87, 52, 82, 29, 23, 73, 30, 101, 22, 80, 75, 47, 20, 66, 100, 78, 58, 35, 5, 81, 74, 16, 59, 63, 100, 30, 65, 18, 95, 100, 86, 25, 22, 81, 4, 6, 60, 72, 54, 61, 22, 8, 87, 19, 86, 84, 23, 56, 12, 80, 85, 16, 4, 99, 3, 58, 54, 105, 77, 91, 86, 81, 65, 64, 69, 73, 87, 34, 0, 33, 4, 83, 50, 27, 9, 61, 47, 99, 76, 37, 11, 61, 81, 19, 11, 17, 88, 53, 63, 35, 86, 88, 65, 19, 5, 28, 39, 93, 67, 100, 48, 30, 69, 3, 54, 83, 60, 82, 63, 23, 104, 12, 56, 72, 100, 51, 23, 39, 58, 35, 13, 7, 9, 17, 10, 35, 0, 52, 67, 33, 27, 47, 66, 105, 94, 40, 58, 53, 42, 58, 85, 51, 9, 19, 33, 79, 60, 91, 4, 50, 1, 88, 74, 66, 58, 48, 55, 102, 67, 25, 4, 25, 1, 93, 11, 23, 4, 2, 67, 41, 50, 90, 23, 76, 20, 62, 90, 82, 14, 23, 84, 88, 31, 52, 82, 29, 20, 57, 104, 100, 49, 51, 20, 63, 80, 42, 4, 2, 9, 103, 48, 70, 86, 62, 94, 40, 54, 53, 72, 33, 85, 70, 89, 18, 9, 28, 65, 53, 63, 34, 98, 105, 0, 76, 31, 25, 60, 29, 76, 103, 85, 47, 55, 52, 75, 25, 86, 12, 63, 68, 82, 58, 74, 52, 80, 62, 72, 40, 31, 33, 9, 7, 89, 58, 74, 16, 60, 56, 68, 91, 84, 82, 0, 52, 39, 73, 49, 89, 67, 99, 1, 93, 56, 18, 58, 45, 11, 23, 81, 37, 11, 78, 82, 93, 44, 25, 97, 25, 67, 50, 85, 88, 14, 76, 1, 51, 60, 39, 63, 46, 48, 62, 94, 52, 0, 73, 88, 53, 76, 100, 30, 52, 89, 3, 104, 91, 47, 38, 87, 28};
//
//        Backtracker tracker = new Backtracker();
//
//        tracker.backtrack(ciphertext);
//
//        System.out.println(tracker.plaintext);
//    }
}

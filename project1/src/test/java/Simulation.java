import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nyu.dto.Dictionary;
import org.nyu.dto.Frequency;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class Simulation {

    // this is set to 106 because Random.nextInt(inclusive, exclusive) ...
    final int KEYSPACE = 106;


    @Test
    public void simulate() throws IOException {

        /**
         * This test is designed to simulate how project 1 will be executed via key generation and scheduling.
         * We will use this test as a benchmark to compare the results of our decryption attacks to a known I/O
         *
         * b.mazey@nyu.edu
         */

        // let us begin by defining a keyspace and randomly generating a key based on the letter frequency.
        // we will use a Spring class to pull files from the resources folder, which are automatically added
        // to classpath
        File frequencyFile = new ClassPathResource("frequency.json").getFile();
        File dictionaryFile = new ClassPathResource("dictionary.json").getFile();

        // TODO - MARSHALL CANDIDATES MAP FOR FIRST ARG IN PROBLEM1 METHOD!

        // now let's use our object mappers to convert these JSON files into POJOs
        ObjectMapper objectMapper = new ObjectMapper();
        Frequency frequency = objectMapper.readValue(frequencyFile, Frequency.class);
        Dictionary dictionary = objectMapper.readValue(dictionaryFile, Dictionary.class);

        @SuppressWarnings("unchecked")
        HashMap<String, Integer> frequencyMap = objectMapper.readValue(frequencyFile, HashMap.class);

        System.out.println(frequencyMap.get("a"));

        HashMap<String, ArrayList<Integer>> keyMap = generateKey(frequencyMap);

        for (String key : keyMap.keySet()) {
            System.out.println(key + " : " + keyMap.get(key));
        }

        // Now the fun begins! Replace sample text!
        String sample = "stovepipes jeopardous gibbousness hope";

        int[] ciphertext = encrypt(keyMap, sample);


        // Looks great!
        assert sample.equals(decrypt(keyMap, ciphertext));


        // Let's try something harder ...
        String candidate = "\"trawling responsiveness tastiest pulsed restamps telescoping pneuma lampoonist divas " +
                "theosophists pustules checkrowed compactor conditionals envy hairball footslogs wasteful conjures " +
                "deadfall stagnantly procure barked balmier bowery vagary beaten capitalized undersized towpath " +
                "envisages thermoplastic rationalizers professions workbench underarm trudger icicled incisive " +
                "oilbirds citrins chambrays ungainliness weazands prehardened dims determinants fishskin cleanable " +
                "henceforward misarranges fine\"";

        int[] candidate_ciphertext = encrypt(keyMap, candidate);

        // TODO - See above!
        // assert candidate.equals(problem1(candidate_ciphertext));



    }

    public HashMap<String, ArrayList<Integer>> generateKey(HashMap<String, Integer> map) {

        Random r = new Random();

        ArrayList<Integer> numbers = new ArrayList<>(IntStream.range(0, KEYSPACE).boxed().collect(toSet()));

        HashMap<String, ArrayList<Integer>> result = new HashMap<>();

        // this is dumb - clean this up later ...
        for (String key : map.keySet()) {
            result.put(key, new ArrayList<>());
        }

        for (String key : map.keySet()){
            for (int i = 0; i < map.get(key); i++) {
                System.out.println("size: " + numbers.size());
                Integer number = numbers.get(r.nextInt(numbers.size()));
                ArrayList keylist = result.get(key);
                keylist.add(number);
                result.put(key, keylist);

                // remove the number because we've used it ...
                numbers.remove(number);

            }
        }

        return result;

    }


    public int[] encrypt(HashMap<String, ArrayList<Integer>> keyMap, String plaintext){

       int[] ciphertextArray = new int[plaintext.length()];


       Random r = new Random();

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

    public String decrypt(HashMap<String, ArrayList<Integer>> map, int[] ciphertext) {
        String plaintext = "";

        for(int i = 0; i < ciphertext.length; i++) {

            for(String key : map.keySet()) {
                ArrayList values = map.get(key);
                if (values.contains(ciphertext[i])) {

                    //This is bad - don't concat strings this way!
                    if (key.equals("space")) plaintext += " ";
                    else plaintext += key;
                }
            }
        }

        return plaintext;
    }

//    public String getCandidate() {
//
//    }

    public String problem1(ArrayList<String> Candidates, HashMap<String, Integer> frequency, int[] ciphertext) {
        // obviously we don't have the key ... but we do have our nice frequency map, and we might be able to build
        // a matching one!

        String plaintext = "Nothing!";
        HashMap<String, ArrayList<Integer>> result = new HashMap<>();

        // this is dumb - clean this up later ...
        // need to copy keys over
        for (String s : frequency.keySet()) {
            result.put(s, new ArrayList<>());
        }

      for (String candidate : Candidates) {
          for (int i = 0; i < candidate.length(); i++) {
              ArrayList list = result.get(Character.toString(candidate.charAt(i)));
              list.add(ciphertext[i]);

              if (list.size() > frequency.get(Character.toString(candidate.charAt(i))))
                  break;
          }
          plaintext = candidate;
      }


        return plaintext;
    }
}

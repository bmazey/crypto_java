package org.nyu.decrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nyu.dto.Dictionary;
import org.nyu.dto.FrequencyPojo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Backtracker {

    private Dictionary dictionary;
    private FrequencyPojo frequency;
    private String ciphertext;
    private Random random;
    private HashMap<String, Integer> frequencyMap;

    public Backtracker(String ciphertext) throws IOException {

        this.ciphertext = ciphertext;
        this.random = new Random();

        File frequencyFile = new ClassPathResource("frequency.json").getFile();
        File dictionaryFile = new ClassPathResource("dictionary.json").getFile();

        ObjectMapper objectMapper = new ObjectMapper();
        this.dictionary = objectMapper.readValue(dictionaryFile, Dictionary.class);
        this.frequency = objectMapper.readValue(frequencyFile, FrequencyPojo.class);

        frequencyMap = objectMapper.readValue(frequencyFile, HashMap.class);
    }


    // first map is original frequency map, second is conjecture map
    public boolean assertFrequency(HashMap<String, Integer> map1, HashMap<String, List<Integer>> map2) {
        for (String key : map1.keySet()) {
            if (map2.get(key).size() > map1.get(key))
                return false;
        }
        return true;
    }
}

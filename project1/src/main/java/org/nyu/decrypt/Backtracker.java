package org.nyu.decrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nyu.dto.Dictionary;
import org.nyu.dto.Frequency;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Backtracker {

    private Dictionary dictionary;
    private Frequency frequency;
    private String ciphertext;
    private Random random;
    private HashMap<String, Integer> frequencyMap;

    @SuppressWarnings("unchecked")
    public Backtracker(String ciphertext) throws IOException {

        this.ciphertext = ciphertext;
        this.random = new Random();

        File frequencyFile = new ClassPathResource("frequency.json").getFile();
        File dictionaryFile = new ClassPathResource("dictionary.json").getFile();

        ObjectMapper objectMapper = new ObjectMapper();
        this.dictionary = objectMapper.readValue(dictionaryFile, Dictionary.class);
        this.frequency = objectMapper.readValue(frequencyFile, Frequency.class);

        frequencyMap = objectMapper.readValue(frequencyFile, HashMap.class);

    }


    public void backtrack() {

        // TODO - implement ... previous made no sense!



    }
}

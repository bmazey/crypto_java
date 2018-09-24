package org.nyu.decrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nyu.dto.Dictionary;
import org.nyu.dto.Frequency;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class Backtracker {

    public Dictionary dictionary;
    public Frequency frequency;

    //TODO - implement backtracking!
    public Backtracker() throws IOException {
        File frequencyFile = new ClassPathResource("frequency.json").getFile();
        File dictionaryFile = new ClassPathResource("dictionary.json").getFile();

        ObjectMapper objectMapper = new ObjectMapper();
        this.dictionary = objectMapper.readValue(dictionaryFile, Dictionary.class);
        this.frequency = objectMapper.readValue(frequencyFile, Frequency.class);

    }


    public void backtrack(String ciphertext) {


    }
}

package org.nyu.service;


import org.nyu.dto.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class MessageGenerator {

    @Autowired
    private DictionaryGenerator dictionaryGenerator;

    private final int MESSAGE_SPACE = 500;

    private Random r;

    public String generateMessage() {
        r = new Random();
        StringBuilder messageBuilder = new StringBuilder();
        Dictionary dictionary = dictionaryGenerator.generateDictionary();

        while(messageBuilder.length() < 500) {
            messageBuilder.append(dictionary.getWords()[r.nextInt(dictionary.getWords().length)]);
            messageBuilder.append(" ");
        }

        return messageBuilder.subSequence(0, 500).toString();
    }

    public String generateSubsetMessage() {
        r = new Random();
        StringBuilder messageBuilder = new StringBuilder();
        Dictionary dictionary = dictionaryGenerator.generateDictionary();

        // here we assert the first word comes from the first ten entries in the dictionary
        messageBuilder.append(dictionary.getWords()[10]);
        messageBuilder.append(" ");

        while(messageBuilder.length() < 500) {
            messageBuilder.append(dictionary.getWords()[r.nextInt(dictionary.getWords().length)]);
            messageBuilder.append(" ");
        }

        return messageBuilder.subSequence(0, 500).toString();
    }

}

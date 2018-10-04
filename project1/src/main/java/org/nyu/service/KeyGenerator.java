package org.nyu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;


@Service
public class KeyGenerator {

    @Autowired
    FrequencyGenerator frequencyGenerator;

    // this is set to 106 because Random.nextInt(inclusive, exclusive) ...
    final int KEYSPACE = 106;

    public HashMap<String, ArrayList<Integer>> generateKey() throws IOException {

        Random r = new Random();
        HashMap<String, Integer> map = frequencyGenerator.generateFrequency();
        ArrayList<Integer> numbers = new ArrayList<>(IntStream.range(0, KEYSPACE).boxed().collect(toSet()));

        HashMap<String, ArrayList<Integer>> result = new HashMap<>();

        // this is dumb - clean this up later ...
        for (String key : map.keySet()) {
            result.put(key, new ArrayList<>());
        }

        for (String key : map.keySet()){
            for (int i = 0; i < map.get(key); i++) {
                Integer number = numbers.get(r.nextInt(numbers.size()));
                ArrayList<Integer> keyList = result.get(key);
                keyList.add(number);
                result.put(key, keyList);

                // remove the number because we've used it ...
                numbers.remove(number);

            }
        }

        return result;

    }
}

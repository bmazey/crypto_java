package org.nyu.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

@Service
public class FrequencyGenerator {

    private File frequencyFile;
    private ObjectMapper mapper;


    public HashMap<String, Integer> generateFrequency() {
        mapper = new ObjectMapper();
        HashMap<String, Integer> frequency = new HashMap<>();
        try {
            frequencyFile  = new ClassPathResource("frequency.json").getFile();

            // see if this warning can be fixed ...
            frequency = mapper.readValue(frequencyFile, HashMap.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return frequency;
    }
}

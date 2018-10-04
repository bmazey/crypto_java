package org.nyu.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class FrequencyGenerator {

    private File frequencyFile;
    private ObjectMapper mapper;


    public HashMap<String, Integer> generateFrequency() throws IOException {
        mapper = new ObjectMapper();
        frequencyFile  = new ClassPathResource("frequency.json").getFile();

        @SuppressWarnings("unchecked")
        HashMap<String, Integer> frequency = mapper.readValue(frequencyFile, HashMap.class);

        return frequency;
    }
}

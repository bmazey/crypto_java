package org.nyu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nyu.dto.Candidates;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CandidateGenerator {

    private File candidateFile;
    private Candidates candidates;
    private ObjectMapper mapper;


    public Candidates generateCandidates() throws IOException {
        mapper = new ObjectMapper();
        candidateFile  = new ClassPathResource("candidates.json").getFile();
        candidates = mapper.readValue(candidateFile, Candidates.class);

        return candidates;
    }
}

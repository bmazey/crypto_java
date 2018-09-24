import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nyu.dto.Dictionary;
import org.nyu.dto.Frequency;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulation {

    final int KEYSPACE = 105;

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

        // now let's use our object mappers to convert these JSON files into POJOs
        ObjectMapper objectMapper = new ObjectMapper();
        Frequency frequency = objectMapper.readValue(frequencyFile, Frequency.class);
        Dictionary dictionary = objectMapper.readValue(dictionaryFile, Dictionary.class);

        @SuppressWarnings("unchecked")
        HashMap<String, Integer> frequencyMap = objectMapper.readValue(frequencyFile, HashMap.class);

    }

    public void generateKey() {
        int[] primitiveKeySet = IntStream.rangeClosed(1, KEYSPACE).toArray();

        // let's convert that to an ArrayList ...
        List<Integer> KeySet = Arrays.stream(primitiveKeySet).boxed().collect(Collectors.toList());

    }
}

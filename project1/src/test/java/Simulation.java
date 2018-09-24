import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.nyu.dto.Dictionary;
import org.nyu.dto.Frequency;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Simulation {

    final int KEYSPACE = 105;

    @Test
    public void simulate() throws IOException {

        /**
         * This test is designed to simluate how project 1 will be executed via key generation and scheduling.
         * We will use this test as a benchmark to compare the results of our decryption attacks to a known I/O
         *
         * b.mazey@nyu.edu
         */

        // let us begin by defining a keyspace and randomly generating a key based on the letter frequency.
        // we will use a Spring class to pulls files from the resources folder, which automatically added to classpath
        File frequency = new ClassPathResource("frequency.json").getFile();
        File dictionary = new ClassPathResource("dictionary.json").getFile();

        // now let's use our object mappers to convert these JSON files into POJOs
        ObjectMapper objectMapper = new ObjectMapper();
        Frequency frequencyMap = objectMapper.readValue(frequency, Frequency.class);
        Dictionary dictionaryMap = objectMapper.readValue(dictionary, Dictionary.class);


    }
}

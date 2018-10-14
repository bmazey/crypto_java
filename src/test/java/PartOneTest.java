
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nyu.ProjectOneApplication;
import org.nyu.dto.Candidates;
import org.nyu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ProjectOneApplication.class)
public class PartOneTest {

    @Autowired
    CandidateGenerator candidateGenerator;

    @Autowired
    FrequencyGenerator frequencyGenerator;

    @Autowired
    KeyGenerator keyGenerator;

    @Autowired
    Encryptor encryptor;

    @Autowired
    Decryptor decryptor;

    @Autowired
    MessageGenerator messageGenerator;

    private Random r;


    @Test
    public void partOneScheduler() throws IOException {

        r = new Random();
        Candidates candidates = candidateGenerator.generateCandidates();

        // HashMap<String, ArrayList<Integer>> key = keyGenerator.getKey();
        HashMap<String, ArrayList<Integer>> key = deserialzeKey();
        // now we'll draft a random candidate ...
        String candidate = candidates.getCandidates()[r.nextInt(candidates.getCandidates().length)];

        int[] ciphertext = encryptor.encryptMod(key, candidate);

        for (String keys : key.keySet()) {
            System.out.println(keys + " : " + Arrays.toString(key.get(keys).toArray()));
        }

        System.out.println("plaintext: " + candidate);
        System.out.println("ciphertext: " + Arrays.toString(ciphertext));

        serializeKey(key);

        //TODO - do something with it! Merging dev -> master ...

    }

    @Test
    public void partOneRandom() throws IOException {

        r = new Random();
        Candidates candidates = candidateGenerator.generateCandidates();

        HashMap<String, ArrayList<Integer>> key = deserialzeKey();

        // now we'll draft a random candidate ...
        for (int loop = 0; loop< 10; loop++) {
            String candidate = candidates.getCandidates()[r.nextInt(candidates.getCandidates().length)];

            int[] ciphertext = encryptor.encrypt(key, candidate);

            /*for (String keys : key.keySet()) {
                System.out.println(keys + " : " + Arrays.toString(key.get(keys).toArray()));
            }*/
            System.out.println("=======================");
            System.out.println("plaintext: " + candidate);
            System.out.println("ciphertext: " + Arrays.toString(ciphertext));

            //TODO - do something with it! Merging dev -> master ...
        }


    }



    public void serializeKey(HashMap<String, ArrayList<Integer>> key) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("key.json"), key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, ArrayList<Integer>> deserialzeKey() {
        HashMap<String, ArrayList<Integer>> key = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            key = mapper.readValue(new ClassPathResource("key.json").getFile(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }
}

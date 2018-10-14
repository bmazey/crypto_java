
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nyu.ProjectOneApplication;
import org.nyu.dto.Candidates;
import org.nyu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ProjectOneApplication.class)
@AutoConfigureMockMvc
public class PartOneTest {

    @Autowired
    private MockMvc mockMvc;

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

    private JSONParser parser = new JSONParser();


    @Test
    public void partOneScheduler() throws Exception {

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

        MvcResult result = this.mockMvc.perform(post("/api/partone/ciphertext")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ciphertext\": " + Arrays.toString(ciphertext) + "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Object jsonObject = parser.parse(result.getResponse().getContentAsString());
        JSONObject json = (JSONObject)jsonObject;

        String plaintext = json.get("plaintext").toString();
        System.out.println("plaintext part one mod: " + plaintext);

        assert plaintext.equals(candidate);
        //TODO - do something with it! Merging dev -> master ...

    }

    @Test
    public void partOneRandom() throws Exception {

        r = new Random();
        Candidates candidates = candidateGenerator.generateCandidates();

        HashMap<String, ArrayList<Integer>> key = deserialzeKey();

        // now we'll draft a random candidate ...
        for (int loop = 0; loop < 10; loop++) {
            String candidate = candidates.getCandidates()[r.nextInt(candidates.getCandidates().length)];

            int[] ciphertext = encryptor.encrypt(key, candidate);

            System.out.println("plaintext: " + candidate);
            System.out.println("ciphertext: " + Arrays.toString(ciphertext));

            MvcResult result = this.mockMvc.perform(post("/api/partone/ciphertext")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"ciphertext\": " + Arrays.toString(ciphertext) + "}"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            Object jsonObject = parser.parse(result.getResponse().getContentAsString());
            JSONObject json = (JSONObject)jsonObject;

            String plaintext = json.get("plaintext").toString();
            System.out.println("plaintext test part one random: " + plaintext);

            assert plaintext.equals(candidate);

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

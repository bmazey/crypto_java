
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nyu.ProjectOneApplication;
import org.nyu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
public class PartTwoTest {

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

     //@Test
    public void partTwoScheduler() throws Exception {

        String message = messageGenerator.generateSubsetMessage();

            System.out.println("message: " + message);
            System.out.println("message length: " + message.length());

            HashMap<String, ArrayList<Integer>> key = keyGenerator.getKey();

            for (String keys : key.keySet()) {
                System.out.println(keys + " : " + Arrays.toString(key.get(keys).toArray()));
            }

            int[] ciphertext = encryptor.encryptMod(key, message);

            System.out.println("part two cipher text: " + Arrays.toString(ciphertext));

        MvcResult result = this.mockMvc.perform(post("/api/parttwo/ciphertext")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ciphertext\": " + Arrays.toString(ciphertext) + "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Object jsonObject = parser.parse(result.getResponse().getContentAsString());
        JSONObject json = (JSONObject)jsonObject;

        String plaintext = json.get("plaintext").toString();
        System.out.println("plaintext part two mod: " + plaintext);

        //assert plaintext.equals(message);

    }

    //@Test
    public void partTwoRandom() throws Exception {

        String message = messageGenerator.generateSubsetMessage();
        System.out.println("message2: " + message);
        System.out.println("message2 length: " + message.length());

        HashMap<String, ArrayList<Integer>> key = keyGenerator.getKey();

        int[] ciphertext = encryptor.encrypt(key, message);

        System.out.println("part two ciphertext2: " + Arrays.toString(ciphertext));

        MvcResult result = this.mockMvc.perform(post("/api/parttwo/ciphertext")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ciphertext\": " + Arrays.toString(ciphertext) + "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Object jsonObject = parser.parse(result.getResponse().getContentAsString());
        JSONObject json = (JSONObject)jsonObject;

        String plaintext = json.get("plaintext").toString();
        System.out.println("plaintext part two random: " + plaintext);

        //assert plaintext.equals(message);
    }
}

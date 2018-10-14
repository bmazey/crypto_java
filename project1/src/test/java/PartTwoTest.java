import org.junit.Test;
import org.junit.runner.RunWith;
import org.nyu.ProjectOneApplication;
import org.nyu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ProjectOneApplication.class)
public class PartTwoTest {

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
    public void partTwoScheduler() {

        String plaintext = messageGenerator.generateMessage();

            System.out.println("message: " + plaintext);
            System.out.println("message length: " + plaintext.length());

            HashMap<String, ArrayList<Integer>> key = keyGenerator.getKey();

            for (String keys : key.keySet()) {
                System.out.println(keys + " : " + Arrays.toString(key.get(keys).toArray()));
            }

            int[] ciphertext = encryptor.encryptMod(key, plaintext);

            System.out.println("part two cipher text: " + Arrays.toString(ciphertext));

    }

    @Test
    public void partTwoRandom() {

        String plaintext = messageGenerator.generateMessage();
        System.out.println("message2: " + plaintext);
        System.out.println("message2 length: " + plaintext.length());

        HashMap<String, ArrayList<Integer>> key = keyGenerator.getKey();

        int[] ciphertext = encryptor.encrypt(key, plaintext);

        System.out.println("part two ciphertext2: " + Arrays.toString(ciphertext));
    }
}

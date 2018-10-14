
import org.junit.Test;
import org.nyu.encrypt.Encrypt;

import java.util.HashMap;
import java.util.Scanner;

public class EncryptionTest {

    @Test
    public void encrypt() {

        String message = "dipped ligatured cannier cohabitation cuddling coiffeuses pursuance roper eternizes nullo " +
                "framable paddlings femur bebop demonstrational tuberculoid theocracy women reappraise oblongatae " +
                "aphasias loftiness consumptive lip neurasthenically dutchmen grift discredited resourcefulness " +
                "malfeasants swallowed jogger sayable lewder editorials demimondaine tzaritza arrogations wish " +
                "indisputable reproduces hygrometries gamuts alight borderlines draggle reconsolidated anemometer rowels" +
                " staggerers grands nu";

        Encrypt encrypt = new Encrypt(message);
        System.out.println(message.length());

        String ciphertext = encrypt.getEncryptedMessage();
        System.out.println("The cipher text is :"+ciphertext);

        String[] split = ciphertext.split(",");
        System.out.println(split.length);

        HashMap<String, Integer> frequency_maps = new HashMap<String, Integer>();
        for (String letter: split) {
            if (frequency_maps.containsKey(letter)) {
                int count = frequency_maps.get(letter) + 1;
                frequency_maps.put(letter, count);
            } else {
                frequency_maps.put(letter, 0);
            }
        }
        for (String val: frequency_maps.keySet()) {
            System.out.println(val + "--->" + frequency_maps.get(val));
        }

    }

}

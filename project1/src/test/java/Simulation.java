import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class Simulation {

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
        File frequency = new ClassPathResource("frequency.txt").getFile();


    }
}

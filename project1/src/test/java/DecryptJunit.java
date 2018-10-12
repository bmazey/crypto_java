import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;
import org.nyu.decrypt.Decrypt;
import org.springframework.core.io.ClassPathResource;

public class DecryptJunit {

	@Test
	public void testLoadOfCandidatesForStrategy1() {

		LocalTime start = LocalTime.now();
		try {
			// Scanner sc = new Scanner(System.in);

			File file = new ClassPathResource("encrypted_text.txt").getFile();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String ciphertext = br.readLine();
			br.close();
			fr.close();

			// String ciphertext = sc.next();
			Decrypt decrypt = new Decrypt(ciphertext, "1");
			decrypt.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LocalTime end = LocalTime.now();
		// start.until(end, ChronoUnit.MINUTES);
		System.out.println("Time Taken -> " + ChronoUnit.SECONDS.between(start, end) + ":"
				+ ChronoUnit.MILLIS.between(start, end));
	}

	@Test
	public void testLoadOfCandidatesForStrategy2() {

		LocalTime start = LocalTime.now();
		try {
			// Scanner sc = new Scanner(System.in);

			File file = new ClassPathResource("encrypted_text.txt").getFile();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String ciphertext = br.readLine();
			br.close();
			fr.close();

			// String ciphertext = sc.next();
			Decrypt decrypt = new Decrypt(ciphertext, "2");
			decrypt.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LocalTime end = LocalTime.now();
		// start.until(end, ChronoUnit.MINUTES);
		System.out.println("Time Taken -> " + ChronoUnit.SECONDS.between(start, end) + ":"
				+ ChronoUnit.MILLIS.between(start, end));
	}

}

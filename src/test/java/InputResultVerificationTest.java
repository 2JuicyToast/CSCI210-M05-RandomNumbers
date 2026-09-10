import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InputResultVerificationTest {

    @Test
    void verifyStudentResultsFile() throws Exception {
        long studentSeed = -1;
        int studentPrediction = -1;

        // 1. Try reading from Classpath (src/main/resources or src/test/resources)
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("inputresult.txt");

        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.startsWith("#") || line.isEmpty()) {
                        continue;
                    }
                    if (line.toLowerCase().startsWith("seed:")) {
                        studentSeed = Long.parseLong(line.split(":")[1].trim());
                    } else if (line.toLowerCase().contains("16th")) {
                        studentPrediction = Integer.parseInt(line.split(":")[1].trim());
                    }
                }
            }
        } else {
            // Fallback: Read directly from src/main/java/inputresult.txt if placed there
            Path path = Paths.get("src/main/java/inputresult.txt");
            assertTrue(Files.exists(path), 
                "Could not find inputresult.txt in src/main/resources/ or src/main/java/");
            
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                if (line.toLowerCase().startsWith("seed:")) {
                    studentSeed = Long.parseLong(line.split(":")[1].trim());
                } else if (line.toLowerCase().contains("16th")) {
                    studentPrediction = Integer.parseInt(line.split(":")[1].trim());
                }
            }
        }

        // 2. Validate student parsed values
        assertNotEquals(1234567L, studentSeed, 
            "You must choose your own secret seed! Seed 1234567 is an example seed and is not allowed.");
        assertNotEquals(24536L, studentSeed, 
            "You must choose your own secret seed! Seed 24536 is an example seed and is not allowed.");

        assertTrue(studentSeed >= 1 && studentSeed <= 10000000, 
            "Student seed must be between 1 and 10,000,000. Found: " + studentSeed);
        assertTrue(studentPrediction >= 1 && studentPrediction <= 6, 
            "16th roll prediction must be between 1 and 6. Found: " + studentPrediction);

        // 3. Re-simulate the 16 rolls with java.util.Random
        Random prng = new Random(studentSeed);
        for (int i = 0; i < 15; i++) {
            prng.nextInt(6); // Skip first 15 rolls
        }
        int actual16thRoll = prng.nextInt(6) + 1;

        // 4. Assert prediction matches true output
        assertEquals(actual16thRoll, studentPrediction,
            String.format("For seed %d, expected 16th roll was %d, but student predicted %d.", 
                studentSeed, actual16thRoll, studentPrediction));
    }
}

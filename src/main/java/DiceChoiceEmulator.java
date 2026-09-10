import java.util.Scanner;
import java.util.Random;
import java.security.SecureRandom;

public class DiceChoiceEmulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Dice Roll Simulator ===");
        System.out.println("1 - Seeded Random (Predictable Formula)");
        System.out.println("2 - SecureRandom (Unpredictable Physics)");
        System.out.println("Q - Quit the program");
        System.out.println("==========================");

        while (true) {
            System.out.print("\nEnter your choice (1, 2, or Q): ");
            String input = scanner.next().trim();

            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }

            if (input.equals("1")) {
                // Prompt the student for their own custom secret seed
                System.out.print("Enter a secret seed between 1 and 10,000,000: ");
                long studentSeed = scanner.nextLong();
                
                if (studentSeed < 1 || studentSeed > 10000000) {
                    System.out.println("Invalid seed range! Keep it between 1 and 10,000,000.");
                    continue;
                }

                Random standardRandom = new Random(studentSeed);
                
                System.out.println("\n--- Generating 16 Dice Rolls ---");
                for (int i = 1; i <= 16; i++) {
                    int roll = standardRandom.nextInt(6) + 1;
                    System.out.print(roll + " ");
                }
                System.out.println("\n(Copy the FIRST 15 numbers and paste them into the Decoder!)");
                
            } else if (input.equals("2")) {
                SecureRandom secureRandom = new SecureRandom();
                
                System.out.println("\n--- Generating 16 Dice Rolls ---");
                for (int i = 1; i <= 16; i++) {
                    int roll = secureRandom.nextInt(6) + 1;
                    System.out.print(roll + " ");
                }
                System.out.println("\n(Notice: SecureRandom ignores manual tracking; cracking this is impossible.)");
                
            } else {
                System.out.println("Invalid choice! Please enter 1, 2, or Q.");
            }
        }

        scanner.close();
    }
}

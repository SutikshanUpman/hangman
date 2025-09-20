import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
        "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
        "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
        "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
        "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon",
        "python", "rabbit", "ram", "rat", "raven", "rhino", "salmon", "seal",
        "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
        "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
        "wombat", "zebra"};

    public static String randomWord() {
        Random random = new Random();
        int index = random.nextInt(words.length);
        return words[index];
    }

    public static String getHint(String word) {
        // Simple hint: reveal the first letter and length of the word
        return "The word starts with '" + word.charAt(0) + "' and is " + word.length() + " letters long.";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            String selectedWord = randomWord();
            char[] word = selectedWord.toCharArray();
            char[] copy = word.clone();
            int n = word.length;
            char[] misses = new char[7];
            char[] dash = new char[2 * n - 1];
            int incorrect = 0;
            int correct = 0;
            int mistakes_count = 0;
            boolean hintUsed = false;

            // Initialize dash array
            for (int i = 0; i < n; i++) {
                dash[2 * i] = '_';
                if (i < n - 1) dash[2 * i + 1] = ' ';
            }

            System.out.println("Welcome to Hangman! Guess the word by entering one letter at a time.");
            System.out.println("Enter 'hint' for a hint (costs one guess). You have 7 incorrect guesses.");

            while (incorrect < 7) {
                gallows(incorrect);
                System.out.print("Word: ");
                System.out.println(new String(dash));
                System.out.print("Misses: ");
                for (int i = 0; i < mistakes_count; i++) {
                    System.out.print(misses[i] + " ");
                }
                System.out.println("\n");
                System.out.print("Guess (or 'hint' for a hint): ");

                String input = scanner.next().toLowerCase();

                if (input.equals("hint") && !hintUsed) {
                    System.out.println(getHint(selectedWord));
                    incorrect++;
                    hintUsed = true;
                    continue;
                }

                if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Please enter a single letter.");
                    continue;
                }

                char guess = input.charAt(0);
                boolean alreadyGuessed = false;
                for (int i = 0; i < mistakes_count; i++) {
                    if (misses[i] == guess) alreadyGuessed = true;
                }
                for (int i = 0; i < n; i++) {
                    if (dash[2 * i] == guess) alreadyGuessed = true;
                }
                if (alreadyGuessed) {
                    System.out.println("You've already guessed '" + guess + "'. Try another letter.");
                    continue;
                }

                boolean found = false;
                for (int i = 0; i < n; i++) {
                    if (word[i] == guess) {
                        dash[2 * i] = guess;
                        word[i] = 0;
                        correct++;
                        found = true;
                    }
                }

                if (!found) {
                    incorrect++;
                    misses[mistakes_count] = guess;
                    mistakes_count++;
                }

                if (correct == n) {
                    gallows(incorrect);
                    System.out.print("Word: ");
                    System.out.println(new String(dash));
                    System.out.println("\nThe word was: " + new String(copy));
                    System.out.println("You won!");
                    break;
                }

                if (incorrect == 7) {
                    gallows(incorrect);
                    System.out.println("\nThe word was: " + new String(copy));
                    System.out.println("You lost!");
                    break;
                }
            }

            System.out.print("Play again? (y/n): ");
            playAgain = scanner.next().toLowerCase().startsWith("y");
            System.out.println();
        }

        System.out.println("Thanks for playing Hangman!");
        scanner.close();
    }

    public static void gallows(int incorrect) {
        switch (incorrect) {
            case 0:
                System.out.println(
                        "+---+\n" +
                        "|   |\n" +
                        "    |\n" +
                        "    |\n" +
                        "    |\n" +
                        "    |\n" +
                        "=========");
                break;
            case 1:
                System.out.println(
                        "+---+\n" +
                        "|   |\n" +
                        "O   |\n" +
                        "    |\n" +
                        "    |\n" +
                        "    |\n" +
                        "=========");
                break;
            case 2:
                System.out.println(
                        "+---+\n" +
                        "|   |\n" +
                        "O   |\n" +
                        "|   |\n" +
                        "    |\n" +
                        "    |\n" +
                        "=========");
                break;
            case 3:
                System.out.println(
                        "+---+\n" +
                        "|   |\n" +
                        "O   |\n" +
                        "/|  |\n" +
                        "    |\n" +
                        "    |\n" +
                        "=========");
                break;
            case 4:
                System.out.println(
                        "+---+\n" +
                        "|   |\n" +
                        "O   |\n" +
                        "/|\\ |\n" +
                        "    |\n" +
                        "    |\n" +
                        "=========");
                break;
            case 5:
                System.out.println(
                        "+---+\n" +
                        "|   |\n" +
                        "O   |\n" +
                        "/|\\ |\n" +
                        "/   |\n" +
                        "    |\n" +
                        "=========");
                break;
            case 6:
                System.out.println(
                        "+---+\n" +
                        "|   |\n" +
                        "O   |\n" +
                        "/|\\ |\n" +
                        "/ \\ |\n" +
                        "    |\n" +
                        "=========");
                break;
            default:
                System.out.println("Invalid number of incorrect guesses!");
        }
    }
}
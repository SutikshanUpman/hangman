import java.util.Random;
import java.util.Scanner;

class WordBank {
    private static final String[] WORDS = {
        "ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
        "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
        "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
        "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
        "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon",
        "python", "rabbit", "ram", "rat", "raven", "rhino", "salmon", "seal",
        "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
        "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
        "wombat", "zebra"
    };

    private final Random random;

    public WordBank() {
        this.random = new Random();
    }

    public String getRandomWord() {
        int index = random.nextInt(WORDS.length);
        return WORDS[index];
    }

    public String getHint(String word) {
        return "The word starts with '" + word.charAt(0) + "' and is " + word.length() + " letters long.";
    }
}

class Gallows {
    private static final String[] GALLOWS_STAGES = {
        "+---+\n" +
        "|   |\n" +
        "    |\n" +
        "    |\n" +
        "    |\n" +
        "    |\n" +
        "=========",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "    |\n" +
        "    |\n" +
        "    |\n" +
        "=========",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "|   |\n" +
        "    |\n" +
        "    |\n" +
        "=========",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "/|  |\n" +
        "    |\n" +
        "    |\n" +
        "=========",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "/|\\ |\n" +
        "    |\n" +
        "    |\n" +
        "=========",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "/|\\ |\n" +
        "/   |\n" +
        "    |\n" +
        "=========",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "/|\\ |\n" +
        "/ \\ |\n" +
        "    |\n" +
        "========="
    };

    public void display(int incorrectGuesses) {
        if (incorrectGuesses >= 0 && incorrectGuesses < GALLOWS_STAGES.length) {
            System.out.println(GALLOWS_STAGES[incorrectGuesses]);
        } else {
            System.out.println("Invalid number of incorrect guesses!");
        }
    }
}

class HangmanGame {
    private final Scanner scanner;
    private final WordBank wordBank;
    private final Gallows gallows;
    private String selectedWord;
    private char[] word;
    private char[] displayWord;
    private char[] misses;
    private int incorrectGuesses;
    private int correctGuesses;
    private int missesCount;
    private boolean hintUsed;

    public HangmanGame() {
        this.scanner = new Scanner(System.in);
        this.wordBank = new WordBank();
        this.gallows = new Gallows();
        initializeGame();
    }

    private void initializeGame() {
        this.selectedWord = wordBank.getRandomWord();
        this.word = selectedWord.toCharArray();
        this.displayWord = new char[2 * word.length - 1];
        this.misses = new char[7];
        this.incorrectGuesses = 0;
        this.correctGuesses = 0;
        this.missesCount = 0;
        this.hintUsed = false;

        for (int i = 0; i < word.length; i++) {
            displayWord[2 * i] = '_';
            if (i < word.length - 1) {
                displayWord[2 * i + 1] = ' ';
            }
        }
    }

    public void play() {
        System.out.println("Welcome to Hangman! Guess the word by entering one letter at a time.");
        System.out.println("Enter 'hint' for a hint (costs one guess). You have 7 incorrect guesses.");

        while (incorrectGuesses < 7) {
            gallows.display(incorrectGuesses);
            System.out.print("Word: ");
            System.out.println(new String(displayWord));
            System.out.print("Misses: ");
            for (int i = 0; i < missesCount; i++) {
                System.out.print(misses[i] + " ");
            }
            System.out.println("\n");
            System.out.print("Guess (or 'hint' for a hint): ");

            String input = scanner.next().toLowerCase();

            if (input.equals("hint") && !hintUsed) {
                System.out.println(wordBank.getHint(selectedWord));
                incorrectGuesses++;
                hintUsed = true;
                continue;
            }

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Please enter a single letter.");
                continue;
            }

            char guess = input.charAt(0);
            if (isAlreadyGuessed(guess)) {
                System.out.println("You've already guessed '" + guess + "'. Try another letter.");
                continue;
            }

            if (processGuess(guess)) {
                if (correctGuesses == word.length) {
                    displayGameResult(true);
                    break;
                }
            } else {
                incorrectGuesses++;
                misses[missesCount++] = guess;
                if (incorrectGuesses == 7) {
                    displayGameResult(false);
                    break;
                }
            }
        }
    }

    private boolean isAlreadyGuessed(char guess) {
        for (int i = 0; i < missesCount; i++) {
            if (misses[i] == guess) {
                return true;
            }
        }
        for (int i = 0; i < word.length; i++) {
            if (displayWord[2 * i] == guess) {
                return true;
            }
        }
        return false;
    }

    private boolean processGuess(char guess) {
        boolean found = false;
        for (int i = 0; i < word.length; i++) {
            if (word[i] == guess) {
                displayWord[2 * i] = guess;
                word[i] = 0;
                correctGuesses++;
                found = true;
            }
        }
        return found;
    }

    private void displayGameResult(boolean won) {
        gallows.display(incorrectGuesses);
        System.out.print("Word: ");
        System.out.println(new String(displayWord));
        System.out.println("\nThe word was: " + selectedWord);
        System.out.println(won ? "You won!" : "You lost!");
    }

    public boolean playAgain() {
        System.out.print("Play again? (y/n): ");
        boolean playAgain = scanner.next().toLowerCase().startsWith("y");
        System.out.println();
        if (playAgain) {
            initializeGame();
        }
        return playAgain;
    }

    public void close() {
        scanner.close();
    }
}

public class Hangman {
    public static void main(String[] args) {
        HangmanGame game = new HangmanGame();
        boolean playAgain = true;

        while (playAgain) {
            game.play();
            playAgain = game.playAgain();
        }

        System.out.println("Thanks for playing Hangman!");
        game.close();
    }
}
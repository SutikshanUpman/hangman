import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"
    };
    
    public static String randomWord() {
        Random random = new Random();
        int index = random.nextInt(words.length); // Random index within bounds of the words array
        return words[index];
    }

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        String selectedWord = randomWord(); 
        char[] word = selectedWord.toCharArray();
     
        //char[] word = "crow".toCharArray();
        char[] copy = word.clone() ; 
        int n = word.length;

        char[] misses = new char[20];

        int incorrect = 0 ;
        int correct = 0 ;
        int mistakes_count = 0;

        char[] dash = new char[2*n+1] ;

            for(int i = 0 ; i < 2*n ; i++){
                dash[i]='_';
                dash[i+1]=' ';
                i++;
            }

        while( incorrect < 7 ){
            gallows(incorrect+1);
            System.out.print("Word: ");

            System.out.print(new String(dash));

            System.out.println("\n");
            System.out.print("Misses: ");

            System.out.print(new String(misses));
            System.out.print("\n\n");

            System.out.print("Guess: ");

            char guess = scanner.next().charAt(0);

            int flag = 0 ;
            for(int i = 0 ; i < n ; i++){
                if(word[i]==guess){
                    flag=1;
                    dash[2*i]=guess;
                    word[i]=0;
                    correct++; //if correct = n ; user won ; 
                    break;
                }
            }

            if(flag==0){
                incorrect++;//if incorrct = 6 ; user lose
                misses[mistakes_count] = guess;
                mistakes_count++;
            }    

            if(correct==n){
                System.out.print("\n\n");
                System.out.print("The Word was:  ");
                System.out.println(new String(copy));
                System.out.print("\n\n");

                System.out.println("You won");
                System.out.print("\n\n");

                System.exit(0);
            }

            if(incorrect==7){
                System.out.print("\n\n");
                System.out.print("The Word was:  ");
                System.out.println(new String(copy));
                System.out.print("\n\n");

                System.out.println("You lost");
                System.out.print("\n\n");

                System.exit(0);
            }
        }
        
        scanner.close();
    }
    
    public static void gallows(int a) {
    
            switch (a) {
    
                case 1:
                    System.out.println(
                            "+---+\n" +
                            "|   |\n" +
                            "    |\n" +
                            "    |\n" +
                            "    |\n" +
                            "    |\n" +
                            "=========\n");
                    break;
    
                case 2:
                    System.out.println(
                            "+---+\n" +
                            "|   |\n" +
                            "O   |\n" +
                            "    |\n" +
                            "    |\n" +
                            "    |\n" +
                            "=========\n");
                    break;
    
                case 3:
                    System.out.println(
                            "+---+\n" +
                            "|   |\n" +
                            "O   |\n" +
                            "|   |\n" +
                            "    |\n" +
                            "    |\n" +
                            "=========\n");
                    break;
    
                case 4:
                    System.out.println(
                            "+---+\n" +
                            "|   |\n" +
                            "O   |\n" +
                            "/|  |\n" +
                            "    |\n" +
                            "    |\n" +
                            "=========\n");
                    break;
    
                case 5:
                    System.out.println(
                            "+---+\n" +
                            "|   |\n" +
                            "O   |\n" +
                            "/|\\ |\n" +
                            "    |\n" +
                            "    |\n" +
                            "=========\n");
                    break;
    
                case 6:
                    System.out.println(
                            "+---+\n" +
                            "|   |\n" +
                            "O   |\n" +
                            "/|\\ |\n" +
                            "/   |\n" +
                            "    |\n" +
                            "=========\n");
                    break;
    
                case 7:
                    System.out.println(
                            "+---+\n" +
                            "|   |\n" +
                            "O   |\n" +
                            "/|\\ |\n" +
                            "/ \\ |\n" +
                            "    |\n" +
                            "=========\n");
                    break;
    
                default:
                    System.out.println("Invalid number of incorrect guesses!");
            }
        }
    }






import java.io.*;
import java.util.*;

public class Cardgame {
    private int number;
    private String location;



    // the main constructor takes CLI requests for No. of players and .txt file
    public static void main(String[] args){
        Cardgame round = new Cardgame();
        round.getInput();
        
    }
    //nested Player class



    class PlayerClass {
        //ArrayList[Cardclass] hand
        //initiate threads for players
        //define hands
        //define pick up and put down as one atomic action
        //output for each action
    }





    //nested card class dealing with making the pack from the input
    class CardClass{
        //Since we know the size of the deck, we can declare an Int array
        private int[] deck;
        
        public CardClass() {
            //setting the deck to the correct length
            deck = new int[8*number];

            //here we add the numbers in the text file to the deck
            setDeck(location, deck);
        }

        //this method is just to check that the given location has the proper file extension
        public String checkName(String location) {
            String txt = ".txt";
            String fileExtension = location.substring(location.length()-4);
            String checkforSpace = location.substring(location.length()-1);
            if (checkforSpace.equals(" ")) {
                location = location.substring(0, location.length() -1);
            } 
            if (!fileExtension.equals(".txt")) {
                location = location + txt;
            } 
            return location;
        }

        public int[] setDeck(String location, int[] deck) {
            try{
                BufferedReader file = new BufferedReader(new FileReader(location));
                String line;
                int n;
                int currentIndex = 0;
                while ((line = file.readLine()) != null) {
                    n = Integer.parseInt(line);
                    deck[currentIndex] = n;
                    currentIndex++;
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error: File not found at location");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error: there was an IOException in reading the line.");
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: there was a problem with the number of cards in your deck. Please check how many there should be.");
                e.printStackTrace();
            }
            return deck;
        }
    }





    //here we're going to define how to get input
    public void getInput() {
        //here we start a scanner to check inputs, which we have to make sure we clear so that the loop checks work
        Scanner a = new Scanner(System.in);
        number = -1;
        location = "";
        //This while loop handles asking for a number of players, and checks the input is a number
        while(true) {
            if (number > 2) {
                break;
            } else {
                try {
                    System.out.println("Please enter the number of players."); 
                    number = a.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Error: please enter a valid type.");
                    a.nextLine();
                } 
            }

        }
        //here we clear the buffer so that it doesn't double prompt for the name
        a.nextLine();
        //this while loop asks for the name of the file (when getting the file, that is when we will handle whether the file name is valid or not)
        while(true) {
            if (location != "") {
                break;
            } else {
                System.out.println("Please enter the location of the file.");
                location = a.nextLine();
            }
        }
        
        a.close();

    }

    //deck class
    class DeckClass {

    }
    //implement rund robin for dealing

    //start threads

    //declare a winner
}
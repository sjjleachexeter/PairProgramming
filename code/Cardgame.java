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



    class Player {
        //ArrayList[Cardclass] 
        private int[] hand = new int[4];
        private int prefDenom = 0;
        private int nextcard = 0;
        private int[] deck = new int[4];
        //initiate threads for players
        //define hands
        //define pick up and put down as one atomic action
        //output for each action

        public Player(int prefDenom){
            this.prefDenom = prefDenom;

        }
        
        // method for getting the denomination
        public int getDenom() {
            return prefDenom;
        }
        //method that sets the preferred denomination of a player
        public void setDenom(int denomination) {
            prefDenom = denomination;
        }

        public int[] gethand(){
            return hand;
        }

        public int edithand(int card, int index){
            int oldcard = -1;
            try {
                oldcard = hand[index];
                hand[index] = card;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: problem indexing into the deck");
                e.printStackTrace();
            }
            return oldcard;
        }
        
        public int findcard(){
            // First priority: keeping denominations corresponding to the player
            // Second priority: don't let any cards remain in the hand long term
            int index = -1;
            for (int i = 0; i <4; i++){
                if(hand[i] != getDenom()){
                    if(i == nextcard){
                        index = i;
                    }else{
                    nextcard++;
                        if(nextcard == 4){
                        nextcard = 0;
                        }
                    }
                }else{
                    nextcard++;
                    if(nextcard == 4){
                        nextcard = 0;
                    }
                }
            }
            return index;
        }
        
        //this synchronised method makes sure that the pick up and put down process is ONE atomic action
        public synchronized void atomicAction(){
            
        }
        
    }


    class PlayerList {
        ArrayList<Player> players;
        for(int i = 1: i<=number: i++){
            Player player = new Player(i);
            players.add(player);
        }
    }


    //nested card class dealing with making the pack from the input
    class CardClass{
        //Since we know the size of the pack, we can declare an Int array
        private int[] pack;
        
        public CardClass() {
            //setting the deck to the correct length
            pack = new int[8*number];

            //here we add the numbers in the text file to the pack
            setPack(location, pack);
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

        public int[] setPack(String location, int[] pack) {
            try{
                BufferedReader file = new BufferedReader(new FileReader(location));
                String line;
                int n;
                int currentIndex = 0;
                while ((line = file.readLine()) != null) {
                    n = Integer.parseInt(line);
                    pack[currentIndex] = n;
                    currentIndex++;
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error: File not found at location");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error: there was an IOException in reading the line.");
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: there was a problem with the number of cards in your pack. Please check how many there should be.");
                e.printStackTrace();
            }
            return pack;
        }
    }





    //here we're going to define how to get input
    public void getInput() {
        //here we start a scanner to check inputs, which we have to make sure we clear so that the loop checks work
        Scanner a = new Scanner(System.in);
        number = -1; //maybe add int to define?
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

    
    //implement rund robin for dealing

    //start threads

    //declare a winner
}
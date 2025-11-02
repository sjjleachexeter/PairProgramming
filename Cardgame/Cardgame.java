import java.io.*;
import java.util.*;

public class Cardgame {
    private int number;
    private String location;
    private static volatile Boolean gameOver = false; 
    private static volatile int winner;



    // the main constructor takes CLI requests for No. of players and .txt file
    public static void main(String[] args){
        Cardgame round = new Cardgame();
        round.getInput();
        round.location = round.checkName(round.location);

        Cardgame.CardClass pack = round.new CardClass();
        Cardgame.PlayerList players = round.new PlayerList();

        round.dealing(pack.getpack(),players);                                                                              

        for(int i=0;i<players.get_players().length;i++) {
            System.out.println("Player "+ players.get_players()[i].getDenom() + "'s initial hand is "+ players.get_decks()[i]);
            players.get_players()[i].start();
            System.out.println("Player " + players.get_players()[i].getDenom() + " has started");
        }
        
    }
   
   






   
    //nested Player class
    class Player extends Thread {
        //ArrayList[Cardclass] 
        private int[] hand = new int[4];
        private int prefDenom = 0;
        private int nextcard = 0;
        private Deck owndeck;
        private Deck disdeck;
        private volatile Boolean won = false;
        //initiate threads for players
        //define hands
        //define pick up and put down as one atomic action
        //output for each action

        public Player(int prefDenom){
            this.prefDenom = prefDenom;
        }






        // ################ Setter and getter methods for threads ################# //
        // ######################################################################## //

        //this synchronised method makes sure that the pick up and put down process is ONE atomic action
        public synchronized void atomicAction(){
            //Player must pick from top of their own deck and discard to the bottom of player +1's deck
            int index = findcard();
            int draw = getowndeck().getcards()[0];
            reorderowndeck();
            System.out.println("Player "+ prefDenom + " has picked up a "+ draw + " from deck " +prefDenom);
            // Draws the needed card and reorders the deck to be ready to be drawn again

            int discard = edithand(draw, index);
            disdeck.getcards()[3] = discard;
            System.out.println("Player "+ prefDenom + " has discarded a "+ discard +" to deck " + (prefDenom+1)%4);
            // Discard card is given to the right deck
            System.out.print("Player " + prefDenom + "'s current hand is ");
            for (int i = 0; i < 4; i++) {
                System.out.print(getowndeck().getcards()[i] + " ");
            }
            System.out.println();


        }

        //Run is possibly the most important method in this program as it actually contains the functionality
        //for how the game is to work, and how the threads are meant to process the atomicAction

        public void run() {
            int[] cards = owndeck.getcards();
            if((cards[0] == cards[1]) && (cards[1] == cards[2]) && (cards[2] == cards[3])){
                gameOver = true;
            }
            while(!gameOver){
                atomicAction();
                cards = owndeck.getcards();
                if((cards[0] == cards[1]) && (cards[1] == cards[2]) && (cards[2] == cards[3])){
                    won = true;
                    winner = prefDenom;
                    gameOver = true;
                }
            }
            
            if(won == true){
                System.out.println("Player "+ prefDenom + " wins");
            }else{
                //Player who has won must inform all the other players who are playing that they've won
                System.out.println("Player "+ winner + " has informed Player " + prefDenom + " that Player " + winner + " has won");
            }

            System.out.println("Player "+ prefDenom + " exits");
            System.out.print("Player " + prefDenom + "'s final hand is ");
            for (int i = 0; i < 4; i++) {
                System.out.print(getowndeck().getcards()[i] + " ");
            }
            System.out.println();


        }
        

        // ################ Setter and getter methods for attributes ############## //
        // ######################################################################## //
        
    
        public int getDenom() {
            return prefDenom;
        }
        //method that sets the preferred denomination of a player
        public void setDenom(int denomination) {
            this.prefDenom = denomination;
        }

        public void sethand(int[] playerhand) {
            this.hand = playerhand;
        }

        public int[] gethand(){
            return hand;
        }

        public void setowndeck(Deck owndeck){
            this.owndeck = owndeck;
        }

        public Deck getowndeck(){
            return owndeck;
        }

        public void setdisdeck(Deck disdeck){
            this.disdeck = disdeck;
        }

        public Deck getdisdeck(){
            return disdeck;
        }

        public void addcard_hand(int card, int index){
             try {
                hand[index] = card;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: problem indexing into the deck");
                e.printStackTrace();
            }
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
                        nextcard = (nextcard+1)%4;
                        break;
                    }else{
                        nextcard = (nextcard+1)%4;
                    }
                }else{
                        nextcard = (nextcard+1)%4;
                }
            }
            return index;
        }

        public void reorderowndeck(){
            for(int i = 0 ; i<3 ; i++ ){
                owndeck.getcards()[i] = owndeck.getcards()[(i+1)%4];
            }
            owndeck.getcards()[3] = -1;

        }

        
    }









    //Originally, deck was not a Class we had defined and we just had it as an attribute of player, however Zachary found
    //that writing it in greatly simplified writing the logic for the game
    class Deck{
        int[] cards = new int[4];

        private Deck(){}

        
        public void addcard(int card, int index){
             try {
                cards[index] = card;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: problem indexing into the deck");
                e.printStackTrace();
            }
        }

        public int removecard(int index){
            int card = cards[index];
            cards[index] = -1;
            return card;
        }

        public int[] getcards(){
            return cards;
        }


    }









    class PlayerList {

        private Player[] players = new Player[number];
        private Deck[] decks = new Deck[number];
        
        public PlayerList(){
            for(int i = 1; i<=number; i++){
                Player player = new Player(i);
                Deck deck = new Deck();
                
                players[i-1] = player;

                decks[i-1] = deck;
            }
            for(int i = 0; i<number; i++){
                players[i].setowndeck(decks[i]);
                players[i].setdisdeck(decks[(i+1)%number]);
            }
        }

        public Player[] get_players(){
            return players;
        }

        public Deck[] get_decks(){
            return decks;
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


        //set pack is the function that takes the location input and actually turns it into a useful Array
        public int[] setPack(String location, int[] pack) {
            try{
                BufferedReader file = new BufferedReader(new FileReader(location));
                String line;
                int n;
                int currentIndex = 0;
                while ((line = file.readLine()) != null) {
                    //here we enclose another try statement in case the contents of the location cannot be passed as ints
                    try {
                        n = Integer.parseInt(line);
                        pack[currentIndex] = n; 
                        currentIndex++;
                    } catch (NumberFormatException e){
                        System.out.println("The contents of the file at the location are not integers, \nplease fix this and try again.");
                        break;
                    }
                    
                file.close();
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

        public int[] getpack(){
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
            if (number >= 2) {
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

    //this method is just to check that the given location has the proper file extension
    public String checkName(String location) {
        String txt = ".txt";
        // This statement makes sure that only file names long enough to have .txt are checked for these error
        if (location.length() >= 4){
            String fileExtension = location.substring(location.length()-4);
            String checkforSpace = location.substring(location.length()-1);
            if (checkforSpace.equals(" ")) {
                location = location.substring(0, location.length() -1);
            } 
            if (!fileExtension.equals(".txt")) {
                location = location + txt;
            } 
            //What follows is a piece of defensive programming to prevent Path traversal attacks
            //While not completely necessary for this coursework, it was good exploration into basic cybersecurity and dealing with exceptions
            if (location.contains("..")){
                System.out.println("For security reasons, you may not have '..' in your file name.");
            }
        //If the file name is too short it automatically adds .txt
        } else {
            location = location + txt;
        }
        return location;
    }

    public void dealing(int[] pack, PlayerList players){
        // Round Robin dealing, will first deal to the players then the decks.
        for(int card = 0; card < 4 ; card++){
            for(int player = 0; player < number ; player ++){
                (players.get_players()[player]).addcard_hand(pack[player+card*4],card);
            }
        }
        for(int card = 0; card < 4 ; card++){
            for(int player = 0 ;  player < number ; player ++){
                (players.get_decks()[player]).addcard(pack[number*4+player+card*4],card);
            }
        }
    }
}
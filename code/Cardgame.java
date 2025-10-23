import java.util.Scanner;
import java.util.InputMismatchException;

public class Cardgame {
    // the main constructor takes CLI requests for No. of players and .txt file
    public static void main(String[] args){
        //here we start a scanner to check inputs, which we have to make sure we clear so that the loop checks work
        Scanner a = new Scanner(System.in);
        int number = -1;
        String location = "";
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
        

    }
    //nested Player class
    //
    class PlayerClass {
        //ArrayList[Cardclass] hand
        //initiate threads for players
        //define hands
        //define pick up and put down as one atomic action
        //output for each action
    }
    //nested card class
    class CardClass{
        //load txt file into the pack

    }

    //deck class
    class DeckClass {

    }
    //implement rund robin for dealing

    //start threads

    //declare a winner
}
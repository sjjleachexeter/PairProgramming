import java.util.Scanner;
import java.util.InputMismatchException;

public class Cardgame {
    // take CLI requests for No. of players and .txt file
    public static void main(String[] args){
        Scanner a = new Scanner(System.in);
        int number = -1;
        String location = "";
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
        a.nextLine();
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
import java.util.Scanner;
public class Cardgame {
    // take CLI requests for No. of players and .txt file
    public static void main(String[] args){
        Scanner a = new Scanner(System.in);
        int number = -1;
        System.out.println("Please enter the number of players."); 
        while (number < 2){
            // number = a.nextInt();
            // if (Integer.parseInt(number)) {
            //     System.out.println("That is not a valid number. Please try again.");
            //     number = -1;
            // }
        }
        
        System.out.println("Please enter the location of the file.");
        a.nextLine();
        String location = a.nextLine();


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
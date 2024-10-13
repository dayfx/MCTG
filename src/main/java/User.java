import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    final private String username;
    final private String password;

    User(String username, String password) {
    this.username = username;
    this.password = password;
}

public void printSuccess(){
        System.out.println("You successfully registered!");
        System.out.println("Your login info is: " + this.username + " + " + this.password);
}

public void buyCards(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you wish to spend your coins to buy 5 cards? (Y/N)");
        String c = scanner.nextLine().trim().toUpperCase();

        if(c.equals("Y")){
            List<Card> stack = new ArrayList<Card>();

            for(int i = 0; i < 5; i++){
                MonsterCard monstercard = new MonsterCard();
                stack.add(monstercard);
            }

            for(Card card : stack){
                if (card instanceof MonsterCard) { // Check if the card is an instance of MonsterCard
                    MonsterCard monsterCard = (MonsterCard) card; // Cast it to MonsterCard
                    monsterCard.printCard(); // Call the printCard() method on the MonsterCard object
                }
            }
        } else {
            System.out.println("You chose not to buy cards. (lol)");
        }

        scanner.close();

}


}

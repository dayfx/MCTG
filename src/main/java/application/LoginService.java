package application;

import java.util.Scanner;

public class LoginService{

    // mock up (without curl)
    private Scanner scanner;

    public LoginService(){
        this.scanner = new Scanner (System.in);
    }

    public User register() {
        System.out.println("Register: Type in username");
        String username = scanner.nextLine();

        System.out.println("Register: Type in password");
        String password = scanner.nextLine();

        User user = new User(username, password);
        user.printSuccess();

        return user;

    }

}

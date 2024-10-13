import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("Program Started");

        LoginService loginService = new LoginService();
        User user = loginService.register();

        user.buyCards();
    }
}

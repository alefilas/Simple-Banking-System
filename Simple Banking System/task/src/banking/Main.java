package banking;

import banking.bank.Controller;

public class Main {

    public static void main(String[] args) {

        Controller controller = new Controller(args[1]);

        controller.run();
    }
}
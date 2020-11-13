package banking.bank.view;

import banking.bank.account.Account;

public class UserView {

    private Menu menu = Menu.MAIN_MENU;

    public void showMenu() {
        switch (menu) {
            case MAIN_MENU:
                showMainMenu();
                break;
            case ACCOUNT_MENU:
                showAccountMenu();
                break;
        }
    }

    private void showAccountMenu() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
    }

    private void showMainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }

    public void changeMenu() {
        if (menu == Menu.MAIN_MENU) {
            menu = Menu.ACCOUNT_MENU;
        } else {
            menu = Menu.MAIN_MENU;
        }
    }

    public void showAccountInfo(Account account) {
        System.out.printf("\nYour card have been created\n" +
                "Your card number:\n" +
                "%s\n" +
                "Your card PIN:\n" +
                "%s\n\n", account.getCARD_NUMBER(), account.getCARD_PIN());
    }

    public void showResultOfLogIn(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("\nYou have successfully logged in!\n");
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }

    public void showBalance(int balance) {
        System.out.println("\nBalance: " + balance + "\n");
    }

    public void showBye() {
        System.out.println("\nBye");
    }

    public void showUnsupportedOperation() {
        System.out.println("\nUnsupported operation\n");
    }

    public void showResult(String result) {
        System.out.println(result);
    }

    public void showIncome() {
        System.out.println("Enter income:");
    }

    public void showTransfer() {
        System.out.println("\nTransfer\n" +
                "Enter card number:");
    }
}

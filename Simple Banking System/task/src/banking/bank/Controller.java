package banking.bank;

import banking.bank.view.Menu;
import banking.bank.view.UserView;

import java.util.Scanner;

public class Controller {

    private final UserView userView;
    private final Bank bank;
    private final Scanner scanner = new Scanner(System.in);
    private Menu menu = Menu.MAIN_MENU;

    public Controller(String dataBaseName) {
        this.userView = new UserView();
        this.bank = new Bank(dataBaseName);
    }

    public void run() {

        boolean isExit = false;

        while (!isExit) {
            userView.showMenu();
            switch (scanner.next()) {
                case "1":
                    onEnterOne();
                    break;
                case "2":
                    onEnterTwo();
                    break;
                case "3":
                    onEnterThree();
                    break;
                case "4":
                    onEnterFour();
                    break;
                case "5":
                    onEnterFive();
                    break;
                case "0":
                    userView.showBye();
                    isExit = true;
                    break;
                default:
                    userView.showUnsupportedOperation();
            }

        }
    }

    private void onEnterFive() {
        if (menu == Menu.ACCOUNT_MENU) {
            bank.logOut();
            changeMenu();
        } else {
            userView.showUnsupportedOperation();
        }
    }

    private void onEnterFour() {
        if (menu == Menu.ACCOUNT_MENU) {
            userView.showResult(bank.deleteAccount());
            changeMenu();
        } else {
            userView.showUnsupportedOperation();
        }
    }

    private void onEnterThree() {
        if (menu == Menu.ACCOUNT_MENU) {
            userView.showTransfer();
            userView.showResult(bank.doTransfer());

        } else {
            userView.showUnsupportedOperation();
        }
    }

    private void onEnterTwo() {
        switch (menu) {
            case MAIN_MENU:
                logIn();
                break;
            case ACCOUNT_MENU:
                userView.showIncome();
                userView.showResult(bank.addIncome());
        }
    }

    private void onEnterOne() {
        switch (menu) {
            case MAIN_MENU:
                userView.showAccountInfo(bank.createAccount());
                break;
            case ACCOUNT_MENU:
                userView.showBalance(bank.getBalance());
        }
    }

    private void logIn() {

        boolean isSuccess = bank.logIn();
        userView.showResultOfLogIn(isSuccess);

        if (isSuccess) {
            changeMenu();
        }
    }

    private void changeMenu() {
        userView.changeMenu();
        if (menu == Menu.MAIN_MENU) {
            menu = Menu.ACCOUNT_MENU;
        } else {
            menu = Menu.MAIN_MENU;
        }
    }
}

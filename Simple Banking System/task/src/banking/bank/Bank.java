package banking.bank;

import banking.bank.account.Account;
import banking.bank.account.AccountFactory;
import banking.bank.data_base.AccountDAO;


import java.util.Scanner;

public class Bank {

    private final Scanner scanner;
    private Account currentAccount;
    private final AccountDAO dataBase;
    private final AccountFactory factory;

    public Bank(String dataBaseName) {
        this.currentAccount = null;
        this.dataBase = new AccountDAO(dataBaseName);
        this.scanner = new Scanner(System.in);
        this.factory = new AccountFactory(dataBase);
        dataBase.createTable();
    }

    Account createAccount() {
        Account account = factory.createAccount();
        dataBase.save(account);
        return account;
    }

    boolean logIn() {

        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Enter your PIN:");
        String cardPIN = scanner.nextLine();

        currentAccount = dataBase.findAccount(cardNumber, cardPIN);

        return currentAccount != null;
    }

    void logOut() {
        currentAccount = null;
    }

    public int getBalance() {
        return dataBase.getBalanceById(currentAccount.getId());
    }

    public String deleteAccount() {
        dataBase.deleteById(currentAccount.getId());
        currentAccount = null;
        return "\nThe account has been closed!\n";
    }

    public String addIncome() {

        int income;
        try {
            income = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return "\nNot a number!\n";
        }

        if (income > 0) {
            dataBase.addBalance(currentAccount.getId(), income);
            return "Income was added!\n";
        } else {
            return "\nInvalid number\n";
        }
    }

    public String doTransfer() {

        String cardName = scanner.nextLine();

        if (!checkLuhnAlgorithm(cardName)) {
            return "Probably you made mistake in the card number. Please try again!\n";
        }

        if (!dataBase.checkAccountInDataBase(cardName)) {
            return "Such a card does not exist.\n";
        }

        System.out.println("Enter how much money you want to transfer:");

        int transfer;
        try {
            transfer = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return "Not a number\n";
        }

        if (transfer > dataBase.getBalanceById(currentAccount.getId()) || transfer <= 0) {
            return "Not enough money!\n";
        }

        dataBase.transfer(transfer, cardName, currentAccount.getCARD_NUMBER());

        return "Success!\n";

    }

    private boolean checkLuhnAlgorithm(String cardName) {

        int result = 0;
        String cardNumber = cardName.substring(0, cardName.length() - 1);

        for (int i = 0; i < cardNumber.length(); i++) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
            }
            if (digit > 9) {
                digit -= 9;
            }
            result += digit;
        }

        int checksum = 0;
        while ((result + checksum) % 10 != 0) {
            checksum++;
        }

        char lastChar = cardName.charAt(cardName.length() -1);

        return checksum == Character.getNumericValue(lastChar);
    }
}

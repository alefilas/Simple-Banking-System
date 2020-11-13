package banking.bank.account;


public class Account {

    private final String CARD_NUMBER;
    private final String CARD_PIN;
    private int balance;
    private final int id;
    private static int maxId = 0;

    public Account(String cardNumber, String cardPin, int id) {
        this.CARD_NUMBER = cardNumber;
        this.CARD_PIN = cardPin;
        this.id = id;
        this.balance = 0;
    }

    public Account(String CARD_NUMBER, String CARD_PIN, int id, int balance) {
        this.CARD_NUMBER = CARD_NUMBER;
        this.CARD_PIN = CARD_PIN;
        this.id = id;
        this.balance = balance;
    }

    public String getCARD_NUMBER() {
        return CARD_NUMBER;
    }

    public String getCARD_PIN() {
        return CARD_PIN;
    }

    public int getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }
}

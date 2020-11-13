package banking.bank.account;

import banking.bank.data_base.AccountDAO;

import java.util.Random;

public class AccountFactory {

    private final Random random = new Random();
    private final AccountDAO data;

    public AccountFactory(AccountDAO data) {
        this.data = data;
    }

    public Account createAccount() {

        String cardNumber = generateCardNumber();
        String cardPIN = generateCardPIN();

        return new Account(cardNumber, cardPIN, data.findMaxId());
    }

    private String generateCardPIN() {

        int cardPIN = random.nextInt(10000);
        return String.format("%04d", cardPIN);
    }

    private String generateCardNumber() {
        StringBuilder cardNumber;
        do {
            cardNumber = new StringBuilder("400000");
            cardNumber.append(String.format("%09d", random.nextInt(1000000000)));
            cardNumber.append(generateLastDigit(cardNumber.toString()));

        } while (data.checkAccountInDataBase(cardNumber.toString()));
        return cardNumber.toString();
    }

    private char generateLastDigit(String cardNumber) {

        int result = 0;

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
        return Character.forDigit(checksum, 10);
    }

}

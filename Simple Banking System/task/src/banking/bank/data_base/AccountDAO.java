package banking.bank.data_base;

import banking.bank.account.Account;

import java.sql.*;

public class AccountDAO {

    private final String url;
    private final String select = "SELECT * FROM card WHERE number = ?";

    public AccountDAO(String dataBaseName) {
        this.url = "jdbc:sqlite:" + dataBaseName;
    }

    public void save(Account account) {
        try (Connection conn = DriverManager.getConnection(url)) {

            try (Statement st = conn.createStatement()) {
                st.executeUpdate(String.format("INSERT INTO card VALUES (%d, '%s', '%s', %d)",
                        account.getId(), account.getCARD_NUMBER(), account.getCARD_PIN(), account.getBalance()));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable() {

        try (Connection conn = DriverManager.getConnection(url)) {

            try (Statement st = conn.createStatement()) {

                st.executeUpdate("CREATE TABLE IF NOT EXISTS card (" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0 NOT NULL)");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Account findAccount(String cardNumber, String cardPIN) {

        try (Connection conn = DriverManager.getConnection(url)) {

            PreparedStatement st = conn.prepareStatement(select);
            st.setString(1,  cardNumber);

            try (ResultSet set = st.executeQuery()) {

                if (set.next()) {
                    if (cardPIN.equals(set.getString("pin"))) {
                        return new Account(set.getString("number"), set.getString("pin"),
                                set.getInt("id"), set.getInt("balance"));
                    }
                }
            }
            st.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean checkAccountInDataBase(String cardNumber) {
        try (Connection conn = DriverManager.getConnection(url)) {

            PreparedStatement st = conn.prepareStatement(select);
            st.setString(1,  cardNumber);
            try (ResultSet set = st.executeQuery()) {

                if (set.next()) {
                    return true;
                }
            }
            st.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public int findMaxId() {
        try (Connection conn = DriverManager.getConnection(url)) {

            Statement st = conn.createStatement();
            try (ResultSet set = st.executeQuery("SELECT MAX(ID) FROM card")) {

                if (set.next()) {
                    return set.getInt(1) + 1;
                }
            }
            st.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    public int getBalanceById(int id) {

        try (Connection conn = DriverManager.getConnection(url)) {

            Statement st = conn.createStatement();
            try (ResultSet set = st.executeQuery("SELECT balance FROM card WHERE id = " + id)) {

                if (set.next()) {
                    return set.getInt(1);
                }
            }
            st.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(url)) {

            try (Statement st = conn.createStatement()) {
                st.executeUpdate("DELETE FROM card WHERE id = " + id);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addBalance(int id, int income) {
        try (Connection conn = DriverManager.getConnection(url)) {

            try (Statement st = conn.createStatement()) {
                st.executeUpdate("UPDATE card SET balance = balance + " + income + " WHERE id = " + id);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transfer(int transfer, String to, String from) {
        try (Connection conn = DriverManager.getConnection(url)) {

            conn.setAutoCommit(false);

            try (Statement st = conn.createStatement()) {
                st.executeUpdate("UPDATE card SET balance = balance - " + transfer + " WHERE number = " + from);
                st.executeUpdate("UPDATE card SET balance = balance + " + transfer + " WHERE number = " + to);
            }

            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

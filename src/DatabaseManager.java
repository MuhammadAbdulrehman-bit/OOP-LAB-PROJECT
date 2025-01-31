import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
        }
    }

    public boolean validateLogin(String pin, String accountNumber) {
        String loginQuery = "SELECT * FROM Account WHERE Pin = ? AND AccountNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
            preparedStatement.setString(1, pin);
            preparedStatement.setString(2, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a record is found
        } catch (SQLException e) {
            System.out.println("Error validating login: " + e.getMessage());
        }
        return false;
    }

    public boolean validateDepoist(String spin, String rpin) {
        String validateDepositQuery = "SELECT*FROM account where AccountNumber = ? or AccountNumber = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(validateDepositQuery)) {
            preparedStatement.setString(1, spin);
            preparedStatement.setString(2, rpin);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error validating deposit" + e.getMessage());
        }
        return false;
    }

    public boolean validateWithDraw(String pin) {
        String query = "SELECT*FROM account WHERE pin = ?";
        try (PreparedStatement validateWithStmt = connection.prepareStatement(query)) {

            validateWithStmt.setString(1, pin);
            ResultSet validateResult = validateWithStmt.executeQuery();
            return validateResult.next();
        } catch (SQLException e) {
            System.out.println("Withdraw validation error: " + e.getMessage());
            return false;
        }
    }

    public boolean Deposit(String senderPin, String recipientPin, double amount) {
        String deductAmountQuery = "UPDATE Account SET balance = balance - ? WHERE accountNumber = ? AND balance >= ?";
        String addAmountQuery = "UPDATE Account SET balance = balance + ? WHERE accountNumber = ?";

        PreparedStatement deductStmt = null;
        PreparedStatement addStmt = null;

        try {
            connection.setAutoCommit(false);

            deductStmt = connection.prepareStatement(deductAmountQuery);
            deductStmt.setDouble(1, amount);
            deductStmt.setString(2, senderPin);
            deductStmt.setDouble(3, amount);
            int rowsAffectedSender = deductStmt.executeUpdate();

            if (rowsAffectedSender == 0) {

                connection.rollback();
                System.out.println("Insufficient balance or invalid sender PIN.");
                return false;
            }

            addStmt = connection.prepareStatement(addAmountQuery);
            addStmt.setDouble(1, amount);
            addStmt.setString(2, recipientPin);
            int rowsAffectedRecipient = addStmt.executeUpdate();

            if (rowsAffectedRecipient == 0) {

                connection.rollback();
                System.out.println("Invalid recipient PIN.");
                return false;
            }

            connection.commit();
            System.out.println("Transfer successful.");
            return true;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollEx) {
                System.out.println("Error during rollback: " + rollEx.getMessage());
            }
            System.out.println("Error during transfer: " + e.getMessage());
            return false;
        }
    }

    public boolean Withdraw(String pin, double amount) {
        String withQuery = "UPDATE account SET balance = balance - ? WHERE Pin = ?";
        String checkBalanceQuery = "SELECT balance FROM account WHERE pin = ? ;";

        try {
            PreparedStatement withStatemnt = connection.prepareStatement(withQuery);
            PreparedStatement checkBStmt = connection.prepareStatement(checkBalanceQuery);

            checkBStmt.setString(1, pin);
            ResultSet checkBResultStmt = checkBStmt.executeQuery();
            if (checkBResultStmt.next()) {
                double bankBalance = checkBResultStmt.getDouble("balance");
                if (amount > bankBalance) {
                    System.out.println("Insufficient balance.");
                    return false;
                }
                withStatemnt.setDouble(1, amount);
                withStatemnt.setString(2, pin);
                int rowsAffected = withStatemnt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Withdrawal successful.");
                    return true;
                } else {
                    System.out.println("Invalid PIN or account not found.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error! WithDraw: " + e.getMessage());
        }
        return false;
    }

    public double CheckBalance(String pin) {
        String Checkquery = "SELECT balance FROM account WHERE pin= ?;";
        double balance = -1;
        try (PreparedStatement checkPrepareStmt = connection.prepareStatement(Checkquery)) {
            checkPrepareStmt.setString(1, pin);
            ResultSet checkResult = checkPrepareStmt.executeQuery();
            if (checkResult.next()) {
                balance = checkResult.getDouble("balance");
            } else {
                System.out.println("Account not found or invalid pin");
            }
        } catch (SQLException e) {
            System.out.println("Check Balacnce: " + e.getMessage());
        }
        return balance;
    }

    public boolean validateAdminLogin(String adminId, String deptId) {
        String query = "SELECT * FROM Admin WHERE EmployeeID = ? AND DepartmentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, adminId);
            stmt.setString(2, deptId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error validating admin login: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteRowByPin(String pin) {
        String deleteQuery = "DELETE FROM account WHERE pin = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, pin);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting row: " + e.getMessage());
            return false;
        }
    }

   public boolean createRow(String Pin, int accountNumber, double balance, String Name){
        String createRowQuery = "INSERT INTO account(Pin, AccountNumber, Balance, Name) VALUES (? , ? , ? ,? )";
        try(PreparedStatement pstmt = connection.prepareStatement(createRowQuery)){
            pstmt.setString(1,Pin);
            pstmt.setInt(2,accountNumber);
            pstmt.setDouble(3,balance);
            pstmt.setString(4,Name);

           int rowsAffected =  pstmt.executeUpdate();
            return rowsAffected>0 ;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
   return false;
    }
}

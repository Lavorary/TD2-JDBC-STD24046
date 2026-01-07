

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Getter
@Setter
@ToString
@AllArgsConstructor


public class DBConnection {
    // Getters for configuration

    private String JDBC_URL;
    private String USER;
    private final String PASSWORD;
    private Connection connection;

    public DBConnection() {
        this.JDBC_URL = System.getenv("JDBC_URL") != null ?
                      System.getenv("JDBC_URL") :
                      "jdbc:postgresql://localhost:5432/mini_dish_db";

        this.USER = System.getenv("DB_USERNAME") != null ?
                       System.getenv("DB_USERNAME") :
                       "mini_dish_db_manager";

        this.PASSWORD = System.getenv("DB_PASSWORD") != null ?
                       System.getenv("DB_PASSWORD") :
                       "123456";

        loadDriver();
    }

    public DBConnection(String JDBC_URL, String USER, String PASSWORD) {
        this.JDBC_URL = JDBC_URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        loadDriver();
    }

    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgresSQL JDBC Driver not found", e);
        }
    }

    public Connection getDBConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("error, connection not closed", e);
            }
        }
    }

    public boolean testConnection() {
        try {
            Connection conn = getDBConnection();
            boolean isValid = conn != null && !conn.isClosed();
            return isValid;
        } catch (SQLException e) {
            return false;
        }
    }

}
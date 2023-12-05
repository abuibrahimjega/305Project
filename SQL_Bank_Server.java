
package javaapplication1;

    
    import java.sql.*;

public class SQL_Bank_Server {

    public static Connection conn() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:BankServer.db");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("SQLite JDBC driver not found", ex);
        }
    }
}
    



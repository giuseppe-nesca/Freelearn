package it.tweb.java.dao;

import com.sun.istack.internal.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManagerDAO {

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Nullable
    public static Connection connect(String url, String usr, String pwd) {
        try {
            return DriverManager.getConnection(url, usr, pwd);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    @Nullable
    public static Connection connect() {
        final String url = "jdbc:mysql://localhost:3306/tweb";
        final String user = "twebAdmin";
        final String pwd = "iumtweb";
        return connect(url, user, pwd);
    }

    public static void disconnect(@Nullable Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

package it.tweb.java.dao;

import com.sun.istack.internal.Nullable;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManagerDAO {

    private static ManagerDAO manager;
    private String url, user, pwd;

    private ManagerDAO(ServletContext context) {
        //        final String url = "jdbc:mysql://localhost:3306/tweb";
        url = context.getInitParameter("DB-URL");
//        final String user = "twebAdmin";
        user = context.getInitParameter("DB-user");
//        final String pwd = "iumtweb";
        pwd = context.getInitParameter("DB-password");
    }

    public static void registerDriver(ServletContext context) {
        if (manager == null)
            manager = new ManagerDAO(context);
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
 /*   public static Connection connect() {
        final String url = "jdbc:mysql://localhost:3306/tweb";
        final String user = "twebAdmin";
        final String pwd = "iumtweb";
        return connect(url, user, pwd);
    }*/
    public static Connection connect() {
        return connect(
                manager.url,
                manager.user,
                manager.pwd);
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

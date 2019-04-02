package it.tweb.java.dao;

import it.tweb.java.model.User;
import org.jetbrains.annotations.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String sql_selectUserOnLogin = "SELECT * FROM Users WHERE email=? AND password=PASSWORD(?);";
    private static final String sql_isAviable = " SELECT * FROM Users, lessons WHERE users.id = ? AND lessons.date = ? AND lessons.slot = ? AND users.id = lessons.userID; ";
    private static final String sql_getUsersAll = "SELECT id, name, surname FROM users;";

    @NotNull
    private static User resultSetItemToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        String role = resultSet.getString("role");
        return new User(id, name, surname, email, role);
    }

    private static User resultSetInfoToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        return new User(id, name, surname);
    }

    @Nullable
    public static User getUserLogin(String emailAddress, String password) throws SQLException {
        User user = null;
        Connection connection = ManagerDAO.connect();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql_selectUserOnLogin);
                preparedStatement.setString(1, emailAddress);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user = resultSetItemToUser(resultSet);
                }
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else {
            throw new SQLException();
        }
        return user;
    }

    public static boolean isAviable(int userID, String date, int slot) throws SQLException {
        boolean result = true;
        Connection connection = ManagerDAO.connect();
        if (connection != null ) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql_isAviable);
                preparedStatement.setInt(1, userID);
                preparedStatement.setDate(2, Date.valueOf(date));
                preparedStatement.setInt(3, slot);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    result = false;
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return result;
    }

    public static List<User> getUsersAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_getUsersAll);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    users.add(resultSetInfoToUser(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return users;
    }
}

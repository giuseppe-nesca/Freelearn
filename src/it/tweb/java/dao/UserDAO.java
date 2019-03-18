package it.tweb.java.dao;

import it.tweb.java.model.User;
import org.jetbrains.annotations.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String sql_selectUserOnLogin = "SELECT * FROM Users WHERE email=? AND password=PASSWORD(?);";

    @NotNull
    private static User resultSetItemToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        String role = resultSet.getString("role");
        return new User(id, name, surname, email, role);
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
}

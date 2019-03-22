package it.tweb.java.dao;

import it.tweb.java.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private static final String sql_getSubjects = "SELECT * FROM Subjects;";

    static public List<Subject> getLessons() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        Connection connection = ManagerDAO.connect();
        if (connection != null ) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql_getSubjects);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    subjects.add(new Subject(id, name));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return subjects;
    }

}

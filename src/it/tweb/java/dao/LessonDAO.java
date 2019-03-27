package it.tweb.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LessonDAO {
    private static String sql_insertLesson =
            "INSERT INTO Lessons (userID, courseID, date, slot, status)" +
            "VALUES (" +
            "        ?," +
            "        (SELECT C.id FROM Courses AS C WHERE teacherID = ? AND subjectID = ?)," +
            "        ?, ?, ?" +
            ");";
    public static void book(int subjectID, int teacherID, int userID, String date, int slot) throws SQLException {
        Connection connection = ManagerDAO.connect();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql_insertLesson);
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, teacherID);
                preparedStatement.setInt(3, subjectID);
                preparedStatement.setString(4, date);
                preparedStatement.setInt(5, slot);
                preparedStatement.setString(6, "booked");
                int rows = preparedStatement.executeUpdate();
            } catch(SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
    }
}

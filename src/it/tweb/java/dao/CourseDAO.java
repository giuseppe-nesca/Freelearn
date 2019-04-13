package it.tweb.java.dao;

import java.sql.*;

public class CourseDAO {

    private static String sql_checkCourse = "SELECT isActive FROM courses WHERE subjectID = ? AND teacherID = ?;";
    private static String sql_insertCourse = "INSERT INTO courses (subjectID, teacherID, isActive) VALUES (?, ?, 1);";

    public static boolean checkCourse(int subjectID, int teacherID) throws SQLException {
        boolean result;
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_checkCourse);
                preparedStatement.setInt(1, subjectID);
                preparedStatement.setInt(2, teacherID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    result = resultSet.getBoolean("isActive");
                    if (result){
                        return true;
                    }
                }
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return false;
    }

    public static boolean insertCourse(int subjectID, int teacherID) throws SQLException {
        boolean check;
        boolean result = false;
        Connection connection = ManagerDAO.connect();
        if (connection != null) {
            try {
                check = checkCourse(subjectID, teacherID);
                if (!check) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql_insertCourse);
                    preparedStatement.setInt(1, subjectID);
                    preparedStatement.setInt(2, teacherID);
                    int row = preparedStatement.executeUpdate();
                    result = row != 0;
                }
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return result;
    }
}

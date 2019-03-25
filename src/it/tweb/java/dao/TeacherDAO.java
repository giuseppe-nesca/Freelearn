package it.tweb.java.dao;

import java.sql.*;

public class TeacherDAO {
    private final static String sql_isAviable = "SELECT lessons.slot FROM lessons, courses WHERE lessons.date = ? AND courses.teacherID = ? AND lessons.courseID = courses.id;";

    public static boolean[] isAviable(int teacherID, String date) throws SQLException {
        boolean[] aviable = {true, true, true};
        Connection connection = ManagerDAO.connect();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql_isAviable);
                preparedStatement.setDate(1, Date.valueOf(date));
                preparedStatement.setInt(2, teacherID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int slot = resultSet.getInt("slot");
                    aviable[slot] = false;
                }
            } catch (SQLException e) {
                e.getMessage();
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println(e.getMessage());
            }finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return aviable;
    }
}

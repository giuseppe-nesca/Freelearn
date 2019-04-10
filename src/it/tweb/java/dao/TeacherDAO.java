package it.tweb.java.dao;

import java.sql.*;

public class TeacherDAO {
    private final static String sql_isAviable = "SELECT lessons.slot FROM lessons, courses WHERE lessons.date = ? AND courses.teacherID = ? AND lessons.courseID = courses.id;";
    private final static String sql_checkTeacher = "SELECT isActive FROM teachers WHERE surname = ? AND name = ?;";
    private final static String sql_insertTeacher = "INSERT INTO teachers (surname, name) VALUE (?, ?);";


    public static boolean[] isAviable(int teacherID, String date) throws SQLException {
        boolean[] aviable = {true, true, true};
        Connection connection = ManagerDAO.connect();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql_isAviable);
                preparedStatement.setString(1, date);
                preparedStatement.setInt(2, teacherID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int slot = resultSet.getInt("slot");
                    aviable[slot-1] = false;
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

    static public boolean checkTeacher(String surname, String name) throws SQLException {
        boolean isActive = true;
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_checkTeacher);
                preparedStatement.setString(1, surname);
                preparedStatement.setString(2, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    isActive = resultSet.getBoolean("isActive");
                } else {
                    isActive = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                ManagerDAO.disconnect(connection);
            }
        } else {
            throw new SQLException();
        }
        return isActive;
    }

    static public boolean insertTeacher(String surname, String name){
        boolean result = true;
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_insertTeacher);
                preparedStatement.setString(1, surname);
                preparedStatement.setString(2, name);
                int rows = preparedStatement.executeUpdate();
                if (rows == 0){
                    result = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        }
        return result;
    }
}

package it.tweb.java.dao;

import it.tweb.java.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private final static String sql_isAviable = "SELECT lessons.slot FROM lessons, courses WHERE lessons.date = ? AND courses.teacherID = ? AND lessons.courseID = courses.id;";
    private final static String sql_checkTeacher = "SELECT isActive FROM teachers WHERE surname = ? AND name = ?;";
    private final static String sql_insertTeacher = "INSERT INTO teachers (surname, name) VALUE (?, ?);";
    private final static String sql_getAllTeachers = "SELECT * FROM teachers WHERE isActive = true;";
    private static final String sql_checkTeacherByID = "SELECT isActive FROM teachers WHERE id = ?;";
    private static final String sql_checkTeacherOnCourses = "SELECT courses.isActive FROM courses WHERE courses.teacherID = ?;";
    private static final String sql_deleteTeacher = "UPDATE teachers SET isActive = '0' WHERE id = ?;";


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

    static public boolean insertTeacher(String surname, String name) throws SQLException {
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
        } else throw new SQLException();
        return result;
    }

    static public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_getAllTeachers);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    Boolean isActive = resultSet.getBoolean("isActive");
                    if (isActive){
                        teachers.add(new Teacher(id, name, surname));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return teachers;
    }

    static public boolean checkTeacherOnCourses(int id) throws SQLException {
        boolean isActive = false;
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_checkTeacherOnCourses);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    isActive = resultSet.getBoolean("isActive");
                    if(isActive){
                        return true;
                    }
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

    static public boolean deleteTeacher(int id) throws SQLException {
        boolean deleted = false;
        Connection connection = ManagerDAO.connect();
        if(connection != null){
            try{
                PreparedStatement deleteStatement = connection.prepareStatement(sql_deleteTeacher);
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                ManagerDAO.disconnect(connection);
            }
        } else {
            throw new SQLException();
        }
        return deleted;
    }

    public static boolean checkTeacherByID(int teacherID) throws SQLException {
        boolean isActive;
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_checkTeacherByID);
                preparedStatement.setInt(1, teacherID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    isActive = resultSet.getBoolean("isActive");
                    if(isActive){
                        return isActive;
                    }
                }
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return false;
    }
}

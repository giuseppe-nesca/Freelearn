package it.tweb.java.dao;

import it.tweb.java.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private static String sql_checkCourse = "SELECT isActive FROM courses WHERE subjectID = ? AND teacherID = ?;";
    private static String sql_insertCourse = "INSERT INTO courses (subjectID, teacherID, isActive) VALUES (?, ?, 1);";
    private static String sql_getCourse =
            "SELECT c.id as courseID, t.id as teacherID, s.id as subjectID, t.surname as teacherSurname, t.name as teacherName, s.name as subjectName " +
            "FROM subjects as s, courses as c, teachers as t " +
            "WHERE c.subjectID = ? AND c.teacherID = ? AND c.subjectID = s.id AND c.teacherID = t.id;";
    private static String sql_getAllCourses =
            "SELECT c.id as courseID, t.id as teacherID, s.id as subjectID, t.surname as teacherSurname, t.name as teacherName, s.name as subjectName " +
            "FROM subjects as s, courses as c, teachers as t " +
            "WHERE c.subjectID = s.id AND c.teacherID = t.id;";

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

    public static List<Course> getCourse(int subjectID, int teacherID) throws SQLException {
        List<Course> courses = new ArrayList<>();
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_getCourse);
                preparedStatement.setInt(1, subjectID);
                preparedStatement.setInt(2, teacherID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int coursesID = resultSet.getInt("courseID");
                    String teacherSurname = resultSet.getString("teacherSurname");
                    String teacherName = resultSet.getString("teacherName");
                    String subjectName = resultSet.getString("subjectName");
                    courses.add(new Course(coursesID, teacherID, teacherName, teacherSurname, subjectID, subjectName));
                }
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return courses;
    }

    public static List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_getAllCourses);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int coursesID = resultSet.getInt("courseID");
                    int teacherID = resultSet.getInt("teacherID");
                    String teacherSurname = resultSet.getString("teacherSurname");
                    String teacherName = resultSet.getString("teacherName");
                    int subjectID = resultSet.getInt("subjectID");
                    String subjectName = resultSet.getString("subjectName");
                    courses.add(new Course(coursesID, teacherID, teacherName, teacherSurname, subjectID, subjectName));
                }
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return courses;
    }
}

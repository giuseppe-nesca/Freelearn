package it.tweb.java.dao;

import it.tweb.java.model.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LessonDAO {
    private static String sql_insertLesson =
            "INSERT INTO Lessons (userID, courseID, date, slot, status)" +
            "VALUES (" +
            "        ?," +
            "        (SELECT C.id FROM Courses AS C WHERE teacherID = ? AND subjectID = ?)," +
            "        ?, ?, ?" +
            ");";

    private static String sql_getLessonsByUserID =
            "SELECT lessons.id as id, lessons.userID as userID, lessons.courseID as courseID, lessons.date as date, lessons.slot as slot, lessons.status as status, courses.subjectID as subjectID, courses.teacherID as teacherID, subjects.name as subjectName, teachers.surname as teacherSurname, teachers.name as teacherName " +
            "FROM lessons, subjects, teachers, courses " +
            "WHERE lessons.userID = ? AND lessons.courseID = courses.id AND courses.subjectID = subjects.id AND courses.teacherID = teachers.id;";

    private static String sql_deleteLesson = "DELETE FROM Lessons WHERE Lessons.id = ?";

    private static Lesson resultSetToLesson(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userID = rs.getInt("userID");
        int courseID = rs.getInt("courseID");
        Date date = rs.getDate("date");
        int slot = rs.getInt("slot");
        String status = rs.getString("status");
        int subjectID = rs.getInt("subjectID");
        int teacherID = rs.getInt("teacherID");
        String subjectName = rs.getString("subjectName");
        String teacherSurname = rs.getString("teacherSurname");
        String teacherName = rs.getString("teacherName");
        return new Lesson(id, userID, courseID, date, slot, status, subjectID, teacherID, subjectName, teacherSurname + " " + teacherName);
    }

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

    public static List<Lesson> getLessonsByUserID(int userID) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        Connection connection = ManagerDAO.connect();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql_getLessonsByUserID);
                preparedStatement.setInt(1, userID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    lessons.add(resultSetToLesson(resultSet));
                }
            }catch(SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return lessons;
    }

    public static void delete(int lessonID) throws SQLException {
        Connection connection = ManagerDAO.connect();
        if (connection != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql_deleteLesson);
                preparedStatement.setInt(1, lessonID);
                ResultSet resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
    }
}

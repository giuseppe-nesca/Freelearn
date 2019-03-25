package it.tweb.java.dao;

import it.tweb.java.model.Subject;
import it.tweb.java.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private static final String sql_getSubjects = "SELECT * FROM Subjects;";
    private static  final String getSql_getSubjectTeachers =
            "SELECT " +
                    " courses.id as courseID, " +
                    " Teachers.name as teacherName, " +
                    " Teachers.surname as teacherSurname, " +
                    " teacherID, subjectID " +
                    " FROM Teachers, courses, subjects " +
                    " WHERE subjects.id = ? AND courses.subjectID = subjects.id AND courses.teacherID = teachers.id";

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

    static public List<Teacher> getSubjectTeachersByID(int id) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        Connection connection = ManagerDAO.connect();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(getSql_getSubjectTeachers);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int courseID = resultSet.getInt("courseID");
                    int teacherID = resultSet.getInt("teacherID");
                    int subjectID = resultSet.getInt("subjectID");
                    String teacherName = resultSet.getString("teacherName");
                    String teacherSurname = resultSet.getString("teacherSurname");
                    teachers.add(new Teacher(teacherID, teacherName, teacherSurname));
                }
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                ManagerDAO.disconnect(connection);
            }
        } else throw new SQLException();
        return teachers;
    }
}

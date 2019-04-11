package it.tweb.java.controller;

import com.google.gson.Gson;
import it.tweb.java.dao.ManagerDAO;
import it.tweb.java.dao.SubjectDAO;
import it.tweb.java.dao.TeacherDAO;
import it.tweb.java.model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static it.tweb.java.dao.SubjectDAO.getSubjectTeachersByID;
import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "TeacherServlet", value = "/teachers/getTeacher")
public class TeacherServlet extends HttpServlet {
    private Gson gson =  new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleCrossOrigin(response);

        String subjectID = request.getParameter("subjectID");

        if (subjectID != null) {
            int subject = -1;
            try {
                subject = Integer.parseInt(subjectID);
            } catch (NumberFormatException e) {
                response.setStatus(400);
                return;
            }
            try {
                List<Teacher> teachers =  SubjectDAO.getSubjectTeachersByID(subject);
                response.getWriter().write(gson.toJson(teachers));
            } catch (SQLException e) {
                response.setStatus(503);
            }
        } else {
            try {
                List<Teacher> teachers = TeacherDAO.getAllTeachers();
                response.getWriter().write(gson.toJson(teachers));
            } catch (SQLException e) {
                response.setStatus(503);
            }
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ManagerDAO.registerDriver();
    }
}

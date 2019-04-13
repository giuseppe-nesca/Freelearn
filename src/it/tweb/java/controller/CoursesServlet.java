package it.tweb.java.controller;

import com.google.gson.Gson;
import it.tweb.java.dao.CourseDAO;
import it.tweb.java.dao.ManagerDAO;
import it.tweb.java.model.Course;
import it.tweb.java.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "CoursesServlet", value = "/courses/getCourses")
public class CoursesServlet extends HttpServlet {
    private Gson gson = new Gson();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCrossOrigin(response);
        try{
            List<Course> courses = CourseDAO.getAllCourses();
            if (courses != null){
                response.getWriter().write(gson.toJson(courses));
            } else {
                response.setStatus(404);
            }
        } catch (SQLException e) {
            e.getMessage();
            response.setStatus(503);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ManagerDAO.registerDriver();
    }
}

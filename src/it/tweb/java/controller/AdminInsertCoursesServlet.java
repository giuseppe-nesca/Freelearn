package it.tweb.java.controller;

import it.tweb.java.dao.CourseDAO;
import it.tweb.java.model.User;
import org.jetbrains.annotations.Nullable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static it.tweb.java.dao.ManagerDAO.registerDriver;
import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "AdminInsertCoursesServlet", value="/admin/course/insert")
public class AdminInsertCoursesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCrossOrigin(response);
        boolean result;
        HttpSession session = request.getSession(false);
        if (session != null){
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRole().equals("admin")){
                try {
                    @Nullable String sID = request.getParameter("subjectID");
                    int subjectID = Integer.parseInt(sID);
                    @Nullable String tID = request.getParameter("teacherID");
                    int teacherID = Integer.parseInt(tID);
                    result = CourseDAO.insertCourse(subjectID, teacherID);
                    if (result){
                        response.getWriter().write("Course added!");
                        return;
                    } else {
                        response.setStatus(400);
                        return;
                    }
                } catch (SQLException | NullPointerException e) {
                    response.setStatus(503);
                    return;
                }
            }
        }
        response.setStatus(401);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        registerDriver();
    }
}

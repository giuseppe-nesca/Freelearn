package it.tweb.java.controller;

import it.tweb.java.dao.TeacherDAO;
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

@WebServlet(name = "AdminInsertTeachersServlet", value = "/admin/teacher/insert")
public class AdminInsertTeachersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        handleCrossOrigin(response);

        HttpSession session = request.getSession(false);
        if (session != null){
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRole().equals("admin")){
                try{
                    @Nullable String teacherSurname = request.getParameter("teacherSurname");
                    @Nullable String teacherName = request.getParameter("teacherName");
                    boolean isActive = TeacherDAO.checkTeacher(teacherSurname, teacherName);
                    if (!isActive){
                        boolean result = TeacherDAO.insertTeacher(teacherSurname, teacherName);
                        if (result){
                            response.getWriter().write("Teacher inserted correctly");
                            return;
                        } else {
                            response.setStatus(400);
                            response.getWriter().write("An error has occurred, teacher not entered");
                            return;
                        }
                    } else {
                        response.setStatus(400);
                        response.getWriter().write("Teacher already exists");
                    }
                    return;
                } catch (SQLException | NullPointerException e) {
                    response.setStatus(503);
                    response.getWriter().write("Internal Server Error");
                    return;
                }
            }
        }
        response.setStatus(401);
        response.getWriter().write("You are not authorized");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        registerDriver();
    }
}

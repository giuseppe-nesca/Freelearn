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

@WebServlet(name = "AdminDeleteTeachersServlet", value="/admin/teacher/delete")
public class AdminDeleteTeachersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        handleCrossOrigin(response);

        HttpSession session = request.getSession(false);
        if(session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRole().equals("admin")) {
                boolean exist; boolean result;
                try {
                    @Nullable int teacherID = Integer.parseInt(request.getParameter("teacherID"));
                    exist = TeacherDAO.checkTeacherByID(teacherID);
                    if (exist) {
                        boolean isActive = TeacherDAO.checkTeacherOnCourses(teacherID);
                        if (!isActive) {
                            result = TeacherDAO.deleteTeacher(teacherID);
                            if (result) {
                                response.getWriter().write("Teacher correctly deleted!");
                                return;
                            }
                        } else {
                            response.getWriter().write("Error! Teacher is linked to a course!");
                            response.setStatus(400);
                            return;
                        }
                    } else {
                        response.getWriter().write("Error! Teacher doesn't exist!");
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

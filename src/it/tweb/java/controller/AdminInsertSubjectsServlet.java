package it.tweb.java.controller;

import it.tweb.java.dao.ManagerDAO;
import it.tweb.java.dao.SubjectDAO;
import it.tweb.java.model.Subject;
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

import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "AdminInsertSubjectsServlet", value = "/admin/subject/insert")
public class AdminInsertSubjectsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCrossOrigin(response);

        HttpSession session = request.getSession(false);
        if (session != null){
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRole().equals("admin")){
                try{
                    @Nullable String subject = request.getParameter("subjectName");
                    boolean isActive = SubjectDAO.checkSubject(subject);
                    if (!isActive){
                        boolean result = SubjectDAO.insertSubject(subject);
                        if (result){
                            response.getWriter().write("Subject inserted correctly");
                        } else {
                            response.setStatus(400);
                            response.getWriter().write("An error has occurred, subject not entered");
                        }
                    } else {
                        response.setStatus(400);
                        response.getWriter().write("Subject already exists");
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
        ManagerDAO.registerDriver();
    }
}

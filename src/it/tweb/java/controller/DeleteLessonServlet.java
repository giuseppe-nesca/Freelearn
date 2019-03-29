package it.tweb.java.controller;

import it.tweb.java.dao.LessonDAO;
import it.tweb.java.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "DeleteLessonServlet", value = "/lessons/delete")
public class DeleteLessonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleCrossOrigin(response);
        String lesson = request.getParameter("lessonID");
        int lessonID = Integer.parseInt(lesson);

        HttpSession session = request.getSession(false);
        if (session != null){
            User user = (User) session.getAttribute("user");
            if (user != null){
                try {
                    LessonDAO.delete(lessonID);
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
        }
        response.setStatus(401);
    }
}

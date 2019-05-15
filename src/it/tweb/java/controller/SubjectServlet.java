package it.tweb.java.controller;

import com.google.gson.Gson;
import it.tweb.java.dao.ManagerDAO;
import it.tweb.java.dao.SubjectDAO;
import it.tweb.java.model.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;


@WebServlet(name = "SubjectServlet", value="/subjects/getSubjects")
public class SubjectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }
    private Gson gson =  new Gson();
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleCrossOrigin(response);
        try {
            List<Subject> subjects = SubjectDAO.getLessons();
            if (subjects != null) {
                response.getWriter().write(gson.toJson(subjects));
            } else {
                response.setStatus(400);
                response.getWriter().write("There are no subjects");
            }
        } catch (SQLException e) {
            response.setStatus(503);
            response.getWriter().write("Internal Server Error");
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ManagerDAO.registerDriver();
    }
}

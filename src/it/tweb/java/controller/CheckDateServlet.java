package it.tweb.java.controller;

import com.google.gson.Gson;
import it.tweb.java.dao.ManagerDAO;
import it.tweb.java.dao.TeacherDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "CheckDateServlet", value = "/teacher/isAviable")
public class CheckDateServlet extends HttpServlet {
    private static Gson gson = new Gson();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleCrossOrigin(response);
        String teacherID = request.getParameter("teacherID");
        String date = request.getParameter("date");

        if(teacherID != null && date != null) {
            try {
                boolean[] result = TeacherDAO.isAviable(Integer.parseInt(teacherID), date);
                String json = gson.toJson(result);
                response.getWriter().write(gson.toJson(result));
            } catch (SQLException e) {
                response.setStatus(503);
            }
        } else {
            response.setStatus(400);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ManagerDAO.registerDriver(getServletContext());
    }
}

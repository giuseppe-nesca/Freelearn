package it.tweb.java.controller;

import com.google.gson.Gson;
import it.tweb.java.dao.UserDAO;
import it.tweb.java.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static it.tweb.java.dao.ManagerDAO.registerDriver;
import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        handleLogin(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        handleLogin(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        registerDriver(getServletContext());
    }

    private void handleLogin(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        handleCrossOrigin(response); //localhost only
        HttpSession session = request.getSession(false);
        /*If JSESSIONID*/
        if(session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null)
                return; //200 ok
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(email != null && password != null) {
            User user = null;
            try {
                user = UserDAO.getUserLogin(email, password);
                if (user != null) {
                    session = request.getSession();
                    session.setAttribute("user", user);
                    return;
                }
            } catch (SQLException e) {
                response.setStatus(503);
                return;
            }
        }
        response.setStatus(401);
    }
}

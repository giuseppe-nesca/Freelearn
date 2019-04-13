package it.tweb.java.controller;

import com.google.gson.Gson;
import it.tweb.java.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.tweb.java.dao.ManagerDAO.registerDriver;
import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "UserInfoServlet", value = "/userinfo")
public class UserInfoServlet extends HttpServlet {
    private Gson gson = new Gson();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest (HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleCrossOrigin(response);
        HttpSession session = request.getSession(false);
        User user = null;
        if(session != null) {
            user = (User) session.getAttribute("user");
        }

        if (session == null || user == null) {
            response.setStatus(401);
        } else {
            response.getWriter().write(gson.toJson(user)); //200, ok
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        registerDriver();
    }
}

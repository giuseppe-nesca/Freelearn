package it.tweb.java.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "Logout", value = "/logout")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ciao?");
        handleCrossOrigin(response);
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }
}

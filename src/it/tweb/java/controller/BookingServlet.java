package it.tweb.java.controller;

import it.tweb.java.dao.LessonDAO;
import it.tweb.java.dao.TeacherDAO;
import it.tweb.java.dao.UserDAO;
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
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static it.tweb.java.utils.ResponseUtils.handleCrossOrigin;

@WebServlet(name = "BookingServlet", value = "/booking")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        handleCrossOrigin(response);

        HttpSession session = request.getSession(false);
        if (session == null ){
            response.setStatus(401);
            return;
        }
        @Nullable User user  = (User) session.getAttribute("user");
        if (user == null) {response.setStatus(401); return;}

        @Nullable String subject = request.getParameter("subjectID");
        @Nullable String teacher = request.getParameter("teacherID");
        @Nullable String dateString = request.getParameter("date");
        @Nullable String slotParam = request.getParameter("slot");
        int subjectID, teacherID, slot;
        Date date;
        try {
            subjectID = Integer.parseInt(subject);
            teacherID = Integer.parseInt(teacher);
            slot = Integer.parseInt(slotParam);
            if (dateString == null) throw new NullPointerException();
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch( NumberFormatException e) {
            response.setStatus(400);
            return;
        } catch (NullPointerException e) {
            response.setStatus(400);
            return;
        } catch (ParseException e) {
            response.setStatus(400);
            System.err.println("bad input data format");
            return;
        }
        try {
            boolean[] teacherAviable = TeacherDAO.isAviable(teacherID, dateString);
            boolean userAviable = UserDAO.isAviable(user.getId(), dateString, slot);
            if (!(userAviable && teacherAviable[slot -1])) {
                //TODO return false
            } else {
                LessonDAO.book(subjectID, teacherID, user.getId(), dateString, slot);
                //TODO return true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

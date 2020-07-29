package com.elinext.web.servlet;

import com.elinext.dao.RoomDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rooms")
public class ShowAllRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("rooms", RoomDao.getInstance().findAll());
        getServletContext()
                .getRequestDispatcher("/WEB-INF/pages/show-all-rooms.jsp")
                .forward(req,resp);
    }
}

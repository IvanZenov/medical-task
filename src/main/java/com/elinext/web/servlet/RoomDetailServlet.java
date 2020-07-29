package com.elinext.web.servlet;

import com.elinext.dao.RoomDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rooms/*")
public class RoomDetailServlet extends HttpServlet {

    RoomDao roomDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        roomDao = RoomDao.getInstance();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: think about exceptions
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        req.setAttribute("roomInfo", roomDao.findById(id));
        req.getRequestDispatcher("/WEB-INF/pages/room.jsp")
                .forward(req,resp);
    }
}

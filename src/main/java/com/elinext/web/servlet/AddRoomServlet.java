package com.elinext.web.servlet;

import com.elinext.dao.RoomDao;
import com.elinext.model.Room;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addNewRoom")
public class AddRoomServlet extends HttpServlet {

    private RoomDao roomDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        roomDao = RoomDao.getInstance();
        super.init(config);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/add-room.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomNumber = req.getParameter("roomNumber");
        String type = req.getParameter("type");

        if (correctForm(roomNumber,type)) {
            roomDao.create(new Room(Integer.parseInt(roomNumber), type));
            resp.sendRedirect(req.getContextPath()+"/rooms");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/addNewRoom");
        }

    }
    private boolean correctForm(String roomNumber, String type){
        return !roomNumber.isEmpty() && !type.isEmpty();
    }
}

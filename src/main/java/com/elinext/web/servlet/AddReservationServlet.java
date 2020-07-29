package com.elinext.web.servlet;

import com.elinext.dao.ManipulationDao;
import com.elinext.dao.RoomDao;
import com.elinext.dao.UserDao;
import com.elinext.exceptions.ProblemWithReservationException;
import com.elinext.model.Manipulation;
import com.elinext.service.ReservationService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {

    private UserDao userDao;
    private ReservationService reservationService;
    private RoomDao roomDao;
    private ManipulationDao manipulationDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userDao = UserDao.getInstance();
        reservationService = ReservationService.getInstance();
        roomDao = RoomDao.getInstance();
        manipulationDao = ManipulationDao.getInstance();
        super.init(config);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("users",userDao.findAll());
        req.setAttribute("rooms",roomDao.findAll());

        getServletContext()
                .getRequestDispatcher("/WEB-INF/pages/add-reservation.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startDateString = req.getParameter("startDate").replace("T"," ");
        String endDateString = req.getParameter("endDate").replace("T"," ");

        String roomId = req.getParameter("roomId");
        String userId = req.getParameter("userId");
        String manipulationName = req.getParameter("manipulationName");
        String manipulationDescription = req.getParameter("manipulationDescription");


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date startDate;
        java.util.Date endDate;

        try {
            startDate = formatter.parse(startDateString);
            endDate  =  formatter.parse(endDateString);
        }
        catch ( ParseException e) {
            req.setAttribute("error", "Please choose correct date");
            doGet(req,resp);
            return;
        }
        try {
            if (!manipulationName.isEmpty() && !manipulationDescription.isEmpty()) {
                Manipulation manipulation = manipulationDao.create(new Manipulation(manipulationName, manipulationDescription));
                reservationService.add(new Date(startDate.getTime()),new Date(endDate.getTime()),
                        Long.valueOf(roomId),Long.valueOf(userId), manipulation.getId());
                resp.sendRedirect(req.getContextPath() + "/all-reservations" );
            }
            else {
                resp.sendRedirect(req.getContextPath() + "/addReservation");
            }

        }
        catch (ProblemWithReservationException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req,resp);
            return;
        }

        req.setAttribute("reservationResult","The reservation was added successfully");


    }
}

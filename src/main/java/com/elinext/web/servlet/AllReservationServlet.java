package com.elinext.web.servlet;

import com.elinext.dao.ReservationDao;
import com.elinext.service.ReservationService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/all-reservations")
public class AllReservationServlet extends HttpServlet {

    ReservationDao reservationDao;
    ReservationService reservationService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        reservationDao = ReservationDao.getInstance();
        reservationService = ReservationService.getInstance();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idSrt = Optional.ofNullable(req.getParameter("id")).orElse("");
        if (!idSrt.isEmpty()) {
            try {
                Long id = Long.valueOf(idSrt);
                reservationService.cancelReservation(id);
            }
            catch (NumberFormatException ex) {
                resp.sendError(404);
            }
        }
        reservationService.updateActiveStatusWithCurrentTime();
        req.setAttribute("reservations",reservationService.findAll());
        req.getRequestDispatcher("/WEB-INF/pages/all-reservations.jsp").forward(req,resp);
    }


}

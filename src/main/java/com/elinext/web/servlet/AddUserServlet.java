package com.elinext.web.servlet;


import com.elinext.dao.UserDao;
import com.elinext.enums.Gender;
import com.elinext.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/addNewUser")
public class AddUserServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userDao = UserDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/add-user.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String role = req.getParameter("role");
        String gender = req.getParameter("gender");
        String phoneNumber = req.getParameter("phoneNumber");

        if (!firstName.isEmpty() && !secondName.isEmpty() && !role.isEmpty() && !phoneNumber.isEmpty()) {
            userDao.create(new User(firstName,secondName,role, Gender.valueOf(gender.toUpperCase()),phoneNumber));
            resp.sendRedirect(req.getContextPath()+"/users");
        }
        else {
            resp.sendRedirect(req.getContextPath()+"/addNewUser");
        }



    }
}

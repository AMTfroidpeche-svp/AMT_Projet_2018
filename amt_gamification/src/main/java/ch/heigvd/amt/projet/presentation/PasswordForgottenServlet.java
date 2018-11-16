package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.User;
import ch.heigvd.amt.projet.services.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PasswordForgottenServlet extends HttpServlet {
    public static final String VIEW = "WEB-INF/pages/passwordForgotten.jsp";

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /************** LOGIN **************/
        if (req.getParameter("email") != null && userDAO.getUser(req.getParameter("email")) != null) {
            resp.sendRedirect(req.getContextPath() + "/questionAuth?email=" + req.getParameter("email"));
        }
        else {
            req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}

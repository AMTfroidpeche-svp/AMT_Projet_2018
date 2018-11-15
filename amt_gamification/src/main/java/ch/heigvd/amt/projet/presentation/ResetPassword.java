package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.services.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetPassword extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/resetPassword.jsp";
    private static final String LOGIN_VIEW = "/login";
    private String email;

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        email = req.getParameter("email");
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /************** LOGIN **************/
        String token = req.getParameter("token");
        String password = req.getParameter("password");
        if (email != null && password != null && token != null) {
            userDAO.changePassword(email, token, password);
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
        else {
            req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
        }
    }
}

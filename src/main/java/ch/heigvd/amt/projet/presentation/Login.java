package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.business.CipherUtil;
import ch.heigvd.amt.projet.model.User;
import ch.heigvd.amt.projet.services.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login extends javax.servlet.http.HttpServlet {

    public final String CREATE_ACCOUNT_VIEW = "register.jsp";
    public final String LOGIN_VIEW = "login.jsp";
    public final String PASSWORD_FORGOTTEN_VIEW = ".jsp";
    public final String HOMEPAGE_VIEW = "application.jsp";

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        /************** LOGIN **************/
        if (req.getParameter("login") != null) {
            // Verify in DB if email/password are valid
            userDAO.addUser(new User("toto", "tutu", "tata", "toto@tutu.tata", 10, "tototututata"));
            /**** IF VALID ****/
            if (userDAO.checkPassword(email, password)) {

                //req.setAttribute("user", email);
                HttpSession session = req.getSession();
                session.setAttribute("user", email);
                req.getRequestDispatcher(HOMEPAGE_VIEW).forward(req, resp);
            }
            /**** IF INVALID ****/
            else {
                req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
            }
        }

        /************** CREATE ACCOUNT **************/
        else if (req.getParameter("register_account") != null) {
            resp.sendRedirect(req.getContextPath() + CREATE_ACCOUNT_VIEW);
        }

        /************** PASSWORD FORGOTTEN **************/
        else if (req.getParameter("password_forgotten") != null) {
            resp.sendRedirect(req.getContextPath() + PASSWORD_FORGOTTEN_VIEW);
        }
    }
}

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

    private static final String CREATE_ACCOUNT_VIEW = "WEB-INF/pages/register.jsp";
    private static final String LOGIN_VIEW = "WEB-INF/pages/login.jsp";
    private static final String PASSWORD_FORGOTTEN_VIEW = "WEB-INF/pages/.jsp";
    private static final String HOMEPAGE_VIEW = "WEB-INF/pages/application.jsp";
    private static final String USER_SESSION = "userSession";

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

        /************** LOGIN **************/
        if (req.getParameter("login") != null) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Create a session
            HttpSession session = req.getSession();

            User user;
            // Verify in DB if email/password are valid
            /**** IF VALID ****/
            if ((user = userDAO.checkPassword(email, password)) != null) {
                session.setAttribute(USER_SESSION, user);
                //req.getRequestDispatcher(HOMEPAGE_VIEW).forward(req, resp);
                resp.sendRedirect(req.getContextPath() + HOMEPAGE_VIEW);
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

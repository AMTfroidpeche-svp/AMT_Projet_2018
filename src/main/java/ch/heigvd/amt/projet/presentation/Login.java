package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.business.AuthChecker;
import ch.heigvd.amt.projet.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login extends javax.servlet.http.HttpServlet {

    public static final String CREATE_ACCOUNT_VIEW = "/WEB-INF/.jsp";
    public static final String LOGIN_VIEW = "/WEB-INF/login.jsp";
    public static final String PASSWORD_FORGOTTEN_VIEW = "/WEB-INF/.jsp";
    public static final String HOMEPAGE_VIEW = "/WEB-INF/profile.htm";

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

            /**** IF VALID ****/
            if (AuthChecker.checkPassword(email, password)) {

                //req.setAttribute("user", email);
                HttpSession session = req.getSession();
                session.setAttribute("user", email);
                this.getServletContext().getRequestDispatcher(HOMEPAGE_VIEW).forward(req, resp);
            }
            /**** IF INVALID ****/
            else {
                this.getServletContext().getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
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

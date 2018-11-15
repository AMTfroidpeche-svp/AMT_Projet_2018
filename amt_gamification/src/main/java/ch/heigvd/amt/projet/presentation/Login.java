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

public class Login extends HttpServlet {

    private static final String CREATE_ACCOUNT_VIEW = "WEB-INF/pages/register.jsp";
    private static final String LOGIN_VIEW = "WEB-INF/pages/login.jsp";
    private static final String PASSWORD_FORGOTTEN_VIEW = "WEB-INF/pages/.jsp";
    private static final String HOMEPAGE_VIEW = "/app";
    private static final String ADMIN_VIEW = "/adminPanel";
    private static final String CHANGE_PASSWORD_VIEW = "/newPassword";
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

            User user;
            // Verify in DB if email/password are valid
            /**** IF VALID ****/
            if ((user = userDAO.checkPassword(email, password)) != null) {
                // Create a session
                HttpSession session = req.getSession();

                session.setAttribute(USER_SESSION, user);
                //req.getRequestDispatcher(HOMEPAGE_VIEW).forward(req, resp);

                if(user.hasToChangedPassword()) {
                    resp.sendRedirect(req.getContextPath() + CHANGE_PASSWORD_VIEW);
                }
                else if(user.getPermissionLevel() == 1) {
                    resp.sendRedirect(req.getContextPath() + ADMIN_VIEW + "?page=1");
                }
                else {
                    resp.sendRedirect(req.getContextPath() + HOMEPAGE_VIEW + "?page=1");
                }
            }

            /*** IF HAS TO CHANGE PASSWORD ***/
            else if ((user = userDAO.getUser(email)) != null && user.hasToChangedPassword()) {
                req.setAttribute("error", "An administrator requested a password change. Please check your email inbox.");
                req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
            }

            /*** IF INVALID ***/
            else {
                req.setAttribute("error", "Bad username or password!");
                req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
            }
        }
    }
}

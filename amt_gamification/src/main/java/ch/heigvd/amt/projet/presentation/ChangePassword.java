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

public class ChangePassword extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/changePassword.jsp";
    private static final String USER_SESSION = "userSession";

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User)session.getAttribute(USER_SESSION);

        if(!user.hasToChangedPassword()) {
            resp.sendRedirect(req.getContextPath() + PrensentationUrls.LOGOUT_URL);
        }
        else {
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        User user = (User)session.getAttribute(USER_SESSION);

        String oldPassowrd = req.getParameter("oldPassword");
        String password1   = req.getParameter("password1");
        String password2   = req.getParameter("password2");

        if(!password1.equals(password2) || userDAO.checkPassword(user.getEmail(), oldPassowrd) == null) {
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
        else {
            userDAO.changePasswordWithoutToken(user.getEmail(), oldPassowrd, password1);
            if(user.getPermissionLevel() == 1) {
                resp.sendRedirect(req.getContextPath() + PrensentationUrls.ADMIN_PANEL_URL + "?page=1");
            }
            else {
                resp.sendRedirect(req.getContextPath() + PrensentationUrls.APP_URL + "?page=1");
            }
        }
    }
}
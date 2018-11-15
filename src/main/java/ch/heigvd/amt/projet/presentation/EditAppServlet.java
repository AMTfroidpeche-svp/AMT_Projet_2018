package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.Application;
import ch.heigvd.amt.projet.model.User;
import ch.heigvd.amt.projet.services.ApplicationDaoLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditAppServlet extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/editApplication.jsp";
    private static final String USER_SESSION = "userSession";

    @EJB
    ApplicationDaoLocal appDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String appToken = req.getParameter("appToken");
        User user = (User)session.getAttribute(USER_SESSION);

        /***** DB Query to have app info *****/
        Application app = appDAO.getApp(appToken, user.getEmail());

        req.setAttribute("appName", app.getAppName());
        req.setAttribute("appDescr", app.getDescription());
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
    }
}

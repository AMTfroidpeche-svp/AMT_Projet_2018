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
import java.util.List;

public class ApplicationServlet extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/applications.jsp";
    private static final String USER_SESSION = "userSession";
    private static final int    APPS_PER_PAGE = 10;

    @EJB
    ApplicationDaoLocal appDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int pageNumber = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

        User user = (User)session.getAttribute(USER_SESSION);
        List<Application> apps = appDAO.retrieveApp(user.getEmail(), pageNumber, user.getPermissionLevel());

        req.setAttribute("appsPerPage", APPS_PER_PAGE);
        req.setAttribute("apps", apps);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}

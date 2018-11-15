package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.Application;
import ch.heigvd.amt.projet.model.User;
import ch.heigvd.amt.projet.services.ApplicationDaoLocal;
import ch.heigvd.amt.projet.services.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AdminUsersManagement extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/admin/adminManageUser.jsp";
    private static final String ADMIN_USERS_MANAGEMENT_VIEW = "/adminUsersManagement";
    private static final int    APPS_PER_PAGE = 10;

    @EJB
    UserDAOLocal userDAO;

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

        User user = userDAO.getUser(req.getParameter("user"));
        List<Application> apps = appDAO.retrieveApp(user.getEmail(), pageNumber, user.getPermissionLevel());

        req.setAttribute("user", user);
        req.setAttribute("appsPerPage", APPS_PER_PAGE);
        req.setAttribute("apps", apps);

        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userEmail = req.getParameter("user");
        String action = req.getParameter("changeUserAccountActivity");
        /***** Set 'isActive' of the account in DB *****/
        if(action.equals("Enable Account")) {
            userDAO.setActive(userEmail, 1);
        }
        else {
            userDAO.setActive(userEmail, 0);
        }

        resp.sendRedirect(req.getContextPath() + ADMIN_USERS_MANAGEMENT_VIEW + "?user=" + userEmail + "&page=1");
    }
}

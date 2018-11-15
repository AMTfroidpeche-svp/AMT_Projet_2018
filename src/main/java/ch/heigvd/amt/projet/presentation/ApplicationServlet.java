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
import java.util.Enumeration;
import java.util.List;

public class ApplicationServlet extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/applications.jsp";
    private static final String EDIT_VIEW = "/editApp";
    private static final String APP_VIEW = "/app";
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
        //TODO: handle error, ex: ?page=10000
        int pageNumber = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

        User user = (User)session.getAttribute(USER_SESSION);
        List<Application> apps = appDAO.retrieveApp(user.getEmail(), pageNumber, user.getPermissionLevel());

        req.setAttribute("appsPerPage", APPS_PER_PAGE);
        req.setAttribute("apps", apps);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        User user = (User)session.getAttribute(USER_SESSION);

        Enumeration buttonNames = req.getParameterNames();
        String buttonName = (String)buttonNames.nextElement();
        String buttonEffect = buttonName.split("_")[0];
        buttonName = buttonName.split("_")[1];

        /***** Edit an APP *****/
        if(buttonEffect.equals("edit")) {
            resp.sendRedirect(req.getContextPath() + EDIT_VIEW + "?appToken=" + buttonName);
        }

        /***** Delete an APP *****/
        else if(buttonEffect.equals("delete")) {
            /** Delete OK **/
            if(appDAO.deleteApp(buttonName, user.getEmail())) {
                resp.sendRedirect(req.getContextPath() + APP_VIEW + "?page=1");
            }
            /** Delete failed **/
            else {

            }

        }
    }
}

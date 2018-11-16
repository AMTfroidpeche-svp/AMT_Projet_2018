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

public class CreateAppServlet extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/createApplication.jsp";
    private static final String USER_SESSION = "userSession";

    @EJB
    ApplicationDaoLocal appDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession();

        sesion.removeAttribute("error");
        sesion.removeAttribute("success");

        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String appName  = req.getParameter("appName");
        String appDescr = req.getParameter("appDescr");
        // TODO: textField validations

        String appOwner = ((User)session.getAttribute(USER_SESSION)).getEmail();

        Application app = new Application(appOwner, appName, appDescr);
        if(appDAO.createApp(app)) {
            session.setAttribute("success", "Your application has been successfully added !");
            resp.sendRedirect(req.getContextPath() + PrensentationUrls.APP_URL + "?page=1");
        }
        else {
            req.setAttribute("error", "Could not create app!");
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}

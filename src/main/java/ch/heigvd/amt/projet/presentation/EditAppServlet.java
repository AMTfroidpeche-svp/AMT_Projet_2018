package ch.heigvd.amt.projet.presentation;

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
    public static final String VIEW = "WEB-INF/pages/editApplication.jsp";

    @EJB
    ApplicationDaoLocal appDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String appToken = (String)req.getAttribute("appToken");

        /***** DB Query to have app info *****/

        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
    }
}

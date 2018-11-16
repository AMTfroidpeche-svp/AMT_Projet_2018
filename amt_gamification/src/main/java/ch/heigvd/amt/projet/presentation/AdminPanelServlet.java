package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.User;
import ch.heigvd.amt.projet.services.UserDAOLocal;
import ch.heigvd.amt.projet.business.Constants;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AdminPanelServlet extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/admin/adminPanel.jsp";

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int pageNumber = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

        List<User> users = userDAO.getPageUser(pageNumber);

        req.setAttribute("usersPerPage", Constants.USERS_PER_PAGE);
        req.setAttribute("users", users);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // admin looking for a user
        if(req.getParameter("findUser") != null) {
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}

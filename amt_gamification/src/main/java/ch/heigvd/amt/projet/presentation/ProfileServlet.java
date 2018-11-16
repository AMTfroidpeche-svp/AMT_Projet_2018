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

public class ProfileServlet extends HttpServlet {
    private static final String VIEW = "WEB-INF/pages/profile.jsp";
    private static final String PROFILE_VIEW = "/profile";
    private static final String USER_SESSION = "userSession";
    private static final int    SIZE_MAX_DESCR = 300;

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println("URL = " + ((User)session.getAttribute(USER_SESSION)).getImageUrl());
        System.out.println("DES = " + ((User)session.getAttribute(USER_SESSION)).getDescription());

        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        User user = (User)session.getAttribute(USER_SESSION);
        String newDescription = req.getParameter("userDescr");
        String newImageUrl = req.getParameter("profilePictureLink");

        if(newDescription != null) {
            /*** the new description is too big ! ***/
            if (newDescription.length() >= SIZE_MAX_DESCR) {
                resp.sendRedirect(req.getContextPath() + PROFILE_VIEW);
            } else {
                userDAO.setDescription(user.getEmail(), newDescription);
                ((User) session.getAttribute(USER_SESSION)).setDescription(newDescription);
                resp.sendRedirect(req.getContextPath() + PROFILE_VIEW);
            }
        }

        else if(newImageUrl != null) {
            // todo: security checks
            userDAO.updateImage(user.getEmail(), newImageUrl);
            ((User) session.getAttribute(USER_SESSION)).setImageUrl(newImageUrl);
            resp.sendRedirect(req.getContextPath() + PROFILE_VIEW);
        }
    }
}

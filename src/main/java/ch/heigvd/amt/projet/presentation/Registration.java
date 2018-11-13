package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.User;
import ch.heigvd.amt.projet.services.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registration extends javax.servlet.http.HttpServlet {

    public final String VIEW = "WEB-INF/pages/register.jsp";
    public final String LOGIN_VIEW = "/login";

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName            = req.getParameter("firstName");
        String lastName             = req.getParameter("lastName");
        String email                = req.getParameter("email");
        String password             = req.getParameter("password");
        String passwordConfirmation = req.getParameter("passwordConfirmation");
        String secretQuestionID     = req.getParameter("secretQuestion"); // TODO ID QUESTION AND NOT TEXT
        String secretAnswer         = req.getParameter("secretAnswer");

        // check that passwords matches
        if(password.equals(passwordConfirmation)) {

            // TODO: add some textfield validations

            // add new dev account in DB
            User user = new User(firstName, lastName, password, email, 1, secretAnswer);
            if(userDAO.addUser(user)) {
                // user successfully added
                resp.sendRedirect(req.getContextPath() + LOGIN_VIEW);
            }
            else {
                // error when adding user
            }


        }
        else {
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}
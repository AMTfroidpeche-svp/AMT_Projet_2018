package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.Question;
import ch.heigvd.amt.projet.model.User;
import ch.heigvd.amt.projet.services.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Registration extends javax.servlet.http.HttpServlet {

    private static final String VIEW = "WEB-INF/pages/register.jsp";
    private static final String LOGIN_VIEW = "/login";

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Question> questions = userDAO.getAllQuestions();
        req.setAttribute("questions", questions);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName            = req.getParameter("firstName");
        String lastName             = req.getParameter("lastName");
        String email                = req.getParameter("email");
        String password             = req.getParameter("password");
        String passwordConfirmation = req.getParameter("passwordConfirmation");
        int secretQuestionID     = Integer.parseInt(req.getParameter("secretQuestion")); // TODO ID QUESTION AND NOT TEXT
        String secretAnswer         = req.getParameter("secretAnswer");

        // check that passwords matches
        if(password.equals(passwordConfirmation)) {

            // TODO: add some textfield validations

            // add new dev account in DB
            User user = new User(firstName, lastName, password, email, secretQuestionID, secretAnswer);
            if(userDAO.addUser(user)) {
                // user successfully added
                //resp.sendRedirect(req.getContextPath() + LOGIN_VIEW);
                req.setAttribute("success", "Dev account successfully created !");
                req.getRequestDispatcher(VIEW).forward(req, resp);
            }
            else {
                // error when adding user
                req.setAttribute("error", "Could not add user !");
                req.getRequestDispatcher(VIEW).forward(req, resp);
            }


        }
        else {
            req.setAttribute("error", "Passwords need to match !");
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}
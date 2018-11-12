package ch.heigvd.amt.projet.presentation;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registration extends javax.servlet.http.HttpServlet {

    public final String CREATE_ACCOUNT_VIEW = "/register.jsp";
    public final String LOGIN_VIEW = "/login";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(CREATE_ACCOUNT_VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname            = req.getParameter("firstname");
        String lastname             = req.getParameter("lastname");
        String email                = req.getParameter("email");
        String password             = req.getParameter("password");
        String passwordConfirmation = req.getParameter("passwordConfirmation");
        String secretQuestion       = req.getParameter("secret_question");
        String secretAnswer         = req.getParameter("secret_answer");

        // check that passwords matches
        if(password.equals(passwordConfirmation)) {

            ////////////////////////////
            // add new dev account in DB
            /* ....... */
            ////////////////////////////

            resp.sendRedirect(req.getContextPath() + LOGIN_VIEW);
        }
        else {
            this.getServletContext().getRequestDispatcher(CREATE_ACCOUNT_VIEW).forward(req, resp);
        }
    }
}
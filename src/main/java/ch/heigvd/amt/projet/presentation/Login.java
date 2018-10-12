package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends javax.servlet.http.HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Verify in DB if email/password are valid

        //////// IF VALID ////////
        if (true) {
            // Create the user with data fetched from DB
            //User user = getUserFromDB();
            //req.setAttribute("user", user);

            this.getServletContext().getRequestDispatcher("/WEB-INF/.jsp").forward(req, resp);
        }
        //////// IF INVALID ////////
        else {
            this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }
}

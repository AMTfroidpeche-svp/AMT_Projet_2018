package ch.heigvd.amt.projet.presentation;

import ch.heigvd.amt.projet.model.Question;
import ch.heigvd.amt.projet.services.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class QuestionAuth extends HttpServlet {
    public static final String VIEW = "WEB-INF/pages/questionAuth.jsp";
    private String email;

    @EJB
    UserDAOLocal userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        email = req.getParameter("email");
        int questionID = userDAO.RetrieveSecretQuestion(email);
        List<Question> questions = userDAO.getAllQuestions();
        req.setAttribute("questions", questions);
        String question = questions.get(questionID - 1).getQuestion();
        req.setAttribute("question", question);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /************** LOGIN **************/
        if (req.getParameter("response") != null) {
            String response = req.getParameter("response");
            if(userDAO.resetPassword(email, response)){
                resp.sendRedirect(req.getContextPath() + "/resetPassword?email=" + email);
            }
        }
            req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
    }
}

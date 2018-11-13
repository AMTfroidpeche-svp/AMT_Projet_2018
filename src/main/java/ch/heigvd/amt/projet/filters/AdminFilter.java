package ch.heigvd.amt.projet.filters;

import ch.heigvd.amt.projet.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    public static final String HOMEPAGE = "/app";
    public static final String USER_SESSION = "userSession";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // get session from request
        HttpSession session = req.getSession();

        if(((User) session.getAttribute(USER_SESSION)).getPermissionLevel() == 1) {
            filterChain.doFilter(req, resp);
        }
        else {
            req.getRequestDispatcher(HOMEPAGE).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}

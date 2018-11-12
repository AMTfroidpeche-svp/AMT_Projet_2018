package ch.heigvd.amt.projet.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Redirects unauthenticated users to the login page. (excludes .css)
 */
public class ConnectedFilter implements Filter {
    private static final String LOGIN_VIEW = "/login";
    private static final String USER_SESSION = "userSession";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // get session from request
        HttpSession session = req.getSession();

        if(session.getAttribute(USER_SESSION) != null || req.getRequestURI().endsWith(".css") ||
           req.getRequestURI().endsWith("/registration") || req.getRequestURI().endsWith("/newPassword")) {
            filterChain.doFilter(req, resp);
        }
        else {
            req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}

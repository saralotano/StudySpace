package controller;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "adminAuthenticationFilter")
public class adminAuthenticationFilter implements Filter {

    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String loginURI = request.getContextPath() + "/index.jsp";

        if (session != null && session.getAttribute("admin") != null)
            chain.doFilter(request, response);
        else
            response.sendRedirect(loginURI);
    }

}

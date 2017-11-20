package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Vendedor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI();
        logger.log(Level.INFO, path);
        if (!path.startsWith(request.getContextPath() + "/login") && !path.startsWith(request.getContextPath()+"/setup")) {
            if(!verificarAcceso(request, response)) {
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean verificarAcceso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Vendedor vendedor = (Vendedor) session.getAttribute("vendedor");
        if(vendedor == null) {
            response.sendRedirect("login");
            return false;
        }
        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}

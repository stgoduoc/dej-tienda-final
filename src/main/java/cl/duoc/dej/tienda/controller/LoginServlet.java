package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Vendedor;
import cl.duoc.dej.tienda.service.VendedorService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    
    @EJB
    VendedorService vendedorService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        if("logout".equals(operacion)) {
            request.getSession().invalidate();
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("inputUsuario");
        String password = request.getParameter("inputPassword");
        Vendedor vendedor = vendedorService.findVendedor(usuario, password);
        List<String> errores    = new ArrayList<>();
        List<String> mensajes   = new ArrayList<>();
        String error = "";
        String mensaje = "";
        if(vendedor == null) {
            error = "Usuario y/o contraseña inválidos";
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }
        
        if(errores.size() > 0) {
            request.setAttribute("errores", errores);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("vendedor", vendedor);
            response.sendRedirect("index.jsp");
        }
        
    }

    

    
}

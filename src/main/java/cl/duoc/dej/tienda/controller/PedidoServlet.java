package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.service.PedidoService;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/pedidos"})
public class PedidoServlet extends HttpServlet {

    public static final String JSP_LISTAR = "/WEB-INF/jsp/pedido/listar.jsp";
    
    @EJB
    PedidoService pedidoService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pedidos", pedidoService.getPedidos());
        request.getRequestDispatcher(JSP_LISTAR).forward(request, response);
    }
    
}

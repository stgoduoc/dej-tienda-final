package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Vendedor;
import cl.duoc.dej.tienda.service.VendedorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VendedorServlet", urlPatterns = {"/vendedor"})
public class VendedorServlet extends HttpServlet {

    @EJB
    VendedorService vendedorService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("op");
        if ("include".equals(operation)) {
            List<Vendedor> vendedores = vendedorService.getVendedores();
            request.setAttribute("vendedores", vendedores);
            return;
        }

    }

}

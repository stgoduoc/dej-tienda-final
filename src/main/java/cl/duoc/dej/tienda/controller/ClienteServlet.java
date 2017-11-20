package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Categoria;
import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.exception.CategoriaNoEncontradaException;
import cl.duoc.dej.tienda.service.CategoriaService;
import cl.duoc.dej.tienda.service.ClienteService;
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

@WebServlet(name = "ClienteServlet", urlPatterns = {"/clientes"})
public class ClienteServlet extends HttpServlet {

    @EJB
    ClienteService clienteService;

    @EJB
    CategoriaService categoriaService;

    public final String JSP_LISTAR = "/WEB-INF/jsp/cliente/listar.jsp";
    public final String JSP_WS = "/WEB-INF/jsp/cliente/ws.jsp";
    public final String JSP_CREAR = "/WEB-INF/jsp/cliente/crear.jsp";

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";

        switch (operacion) {
            case "crear":
                crear(request, response);
                break;
            case "ws":
                ws(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
        }

    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = clienteService.getClientes();
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher(JSP_LISTAR).forward(request, response);
    }

    private void crear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP_CREAR).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringId = request.getParameter("id");
        if (stringId == null || stringId.isEmpty()) {
            postCrear(request, response);
        } else {
            postEditar(request, response);
        }
    }

    private void postCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("categoria");
        String descripcion = request.getParameter("descripcion");
        Categoria categoria = new Categoria(nombre, descripcion);
        categoria = categoriaService.crearCategoria(categoria);
        request.setAttribute("mensajes", new String[]{String.format("Categoría %s creada correctamente con ID %s", categoria.getNombre(), categoria.getId())});
        request.getRequestDispatcher(JSP_CREAR).forward(request, response);
    }

    private void postEditar(HttpServletRequest request, HttpServletResponse response) {

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringId = request.getParameter("id");
        Long categoriaId = 0L;
        try {
            categoriaId = Long.parseLong(stringId);
            categoriaService.eliminarCategoria(categoriaId);
            mensaje = String.format("Se ha eliminado correctamente la categoría con ID %s", categoriaId);
            logger.log(Level.INFO, mensaje);
            request.setAttribute("mensajes", mensajes);
            mensajes.add(mensaje);
        } catch (NumberFormatException nfe) {
            error = String.format("Formato de ID inválido");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (CategoriaNoEncontradaException ex) {
            error = String.format("No se pudo encontrar la categoría con el ID especificado");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        listar(request, response);
    }

    private void ws(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringRut = request.getParameter("rut");
        int rut = 0;
        Cliente cliente = null;
        try {
            rut = Integer.parseInt(stringRut);
            cliente = clienteService.getClienteByRut(rut);
        } catch(NumberFormatException nfe) {
            logger.log(Level.SEVERE, "Formato del RUT incorrecto");
        }
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher(JSP_WS).forward(request, response);
    }

}

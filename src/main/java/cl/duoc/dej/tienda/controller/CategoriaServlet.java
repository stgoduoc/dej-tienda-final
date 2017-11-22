package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Categoria;
import cl.duoc.dej.tienda.exception.CategoriaNoEncontradaException;
import cl.duoc.dej.tienda.service.CategoriaService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@WebServlet(name = "CategoriaServlet", urlPatterns = {"/categorias"})
public class CategoriaServlet extends HttpServlet {

    @EJB
    CategoriaService categoriaService;

    public final String JSP_LISTAR = "/WEB-INF/jsp/categoria/listar.jsp";
    public final String JSP_CREAR = "/WEB-INF/jsp/categoria/crear.jsp";

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";

        switch (operacion) {
            case "crear":
                crear(request, response);
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
        List<Categoria> categorias = categoriaService.getCategorias();
        request.setAttribute("categorias", categorias);
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
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String nombre = request.getParameter("categoria");
        String descripcion = request.getParameter("descripcion");
        Categoria categoria = new Categoria(nombre, descripcion);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Categoria>> listaValidaciones = validator.validate(categoria);
        for (ConstraintViolation<Categoria> v : listaValidaciones) {
            errores.add(v.getPropertyPath().toString() + ":" + v.getMessage());
        }

        if (errores.size() > 0) {            
            request.setAttribute("errores", errores);
        } else {
            categoria = categoriaService.crearCategoria(categoria);
            mensaje = String.format("Categoría %s creada correctamente con ID %s", categoria.getNombre(), categoria.getId());
            mensajes.add(mensaje);
            request.setAttribute("mensajes", mensajes);
        }

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

}

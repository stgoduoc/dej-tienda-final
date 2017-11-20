package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Categoria;
import cl.duoc.dej.tienda.entity.Producto;
import cl.duoc.dej.tienda.exception.CategoriaNoEncontradaException;
import cl.duoc.dej.tienda.exception.ProductoNoEncontradoException;
import cl.duoc.dej.tienda.service.CategoriaService;
import cl.duoc.dej.tienda.service.ProductoService;
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

@WebServlet(name = "ProductoServlet", urlPatterns = {"/catalogo"})
public class ProductoServlet extends HttpServlet {

    @EJB
    ProductoService productoService;
    @EJB
    CategoriaService categoriaService;

    private final String JSP_LISTA_PRODUCTOS = "/WEB-INF/jsp/producto/listar.jsp";
    private final String JSP_CREAR = "/WEB-INF/jsp/producto/crear.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";
        switch (operacion) {
            case "crear":
                request.setAttribute("categorias", categoriaService.getCategorias());
                request.getRequestDispatcher(JSP_CREAR).forward(request, response);
                break;
            case "buscar":
                buscar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response, List<Producto> productos) throws ServletException, IOException {
        List<Categoria> categorias = categoriaService.getCategorias();

        request.setAttribute("productos", productos);
        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher(JSP_LISTA_PRODUCTOS).forward(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Producto> productos = productoService.getProductos();
        listar(request, response, productos);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringId = request.getParameter("id");
        Long productoId = 0L;
        try {
            productoId = Long.parseLong(stringId);
            productoService.eliminarProducto(productoId);
            mensaje = String.format("Se ha eliminado correctamente el producto con ID %s", productoId);
            logger.log(Level.INFO, mensaje);
            request.setAttribute("mensajes", mensajes);
            mensajes.add(mensaje);
        } catch (NumberFormatException nfe) {
            error = String.format("Formato de ID inválido");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (ProductoNoEncontradoException ex) {
            error = String.format("No se pudo encontrar el producto con el ID especificado");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        listar(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productoBuscado = request.getParameter("producto");
        String stringCategoria = request.getParameter("categoria");
        Long categoriaId = null;
        try {
            if (stringCategoria != null) {
                categoriaId = Long.parseLong(stringCategoria);
            }
        } catch (NumberFormatException nfe) {
            logger.log(Level.INFO, "La Categoría ID entregada no corresponde a un ID válido");
        }
        List<Producto> productos = productoService.buscarProducto(productoBuscado, categoriaId);
        listar(request, response, productos);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";
        
        String nombre = request.getParameter("producto");
        String stringCategoria = request.getParameter("categoria");
        String stringPrecio = request.getParameter("precio");
        String imagen = request.getParameter("imagen");
        String descripcion = request.getParameter("descripcion");
        Long precio = 0L;
        Categoria categoria = null;
        try {
            Long categoriaId = Long.parseLong(stringCategoria);
            categoria = categoriaService.getCategoriaById(categoriaId);
            if(categoria == null) throw new CategoriaNoEncontradaException("No se encontró la categoría asignada al producto");
            precio = Long.parseLong(stringPrecio);
            Producto producto = new Producto(nombre, imagen, descripcion, precio, categoria);
            producto = productoService.crearProducto(producto);
            mensaje = String.format("Producto %s creada correctamente con ID %s", producto.getNombre(), producto.getId());
            mensajes.add(mensaje);
        } catch(NumberFormatException nfe) {
            errores.add("Formato numérico incompatible");
        } catch (CategoriaNoEncontradaException ex) {
            errores.add(ex.getMessage());
        }
        
        request.setAttribute("errores", errores);
        request.setAttribute("mensajes", mensajes);
        request.getRequestDispatcher(JSP_CREAR).forward(request, response);
    }

}

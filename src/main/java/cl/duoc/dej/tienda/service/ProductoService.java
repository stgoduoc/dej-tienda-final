package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Producto;
import cl.duoc.dej.tienda.exception.ProductoNoEncontradoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class ProductoService {

    static final long serialVersionUID = 11L;
    
    @PersistenceContext
    EntityManager em;

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public ProductoService() {
    }

    public Producto crearProducto(Producto producto) {
        em.persist(producto);
        return producto;
    }

    public List<Producto> getProductos() {
        TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p", Producto.class);
        return query.getResultList();
    }

    /**
     *
     * @param id
     * @return retorna el producto o nulo en caso de no ser encontrado
     */
    public Producto getProductoById(Long id) {
        return em.find(Producto.class, id);
    }

    public List<Producto> buscarProducto(String nombreProducto, Long categoriaId) {
        if (nombreProducto != null && !nombreProducto.isEmpty() && categoriaId != null && categoriaId > 0) {
            String jpql = "SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE :nombre AND p.categoria.id = :categoriaId";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("nombre", "%" + nombreProducto + "%");
            query.setParameter("categoriaId", categoriaId);
            return query.getResultList();
        }

        if (nombreProducto != null && !nombreProducto.isEmpty()) {
            String jpql = "SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE :nombre";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("nombre", "%" + nombreProducto + "%");
            return query.getResultList();
        }

        if (nombreProducto == null || nombreProducto.isEmpty()) {
            if (categoriaId != null && categoriaId > 0) {
                String jpql = "SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId";
                TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);                
                query.setParameter("categoriaId", categoriaId);
                return query.getResultList();
            }

        }

        // sino devuelve la lista completa de productos
        return getProductos();
    }

    public void eliminarProducto(Long productoId) throws ProductoNoEncontradoException {
        Producto p = getProductoById(productoId);
        if (p == null) {
            String mensajeException = String.format("Producto con ID %s no encontrado para ser eliminado", productoId);
            logger.log(Level.SEVERE, mensajeException);
            throw new ProductoNoEncontradoException(mensajeException);
        }
        em.remove(p);
    }

}

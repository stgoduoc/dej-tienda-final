package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Vendedor;
import cl.duoc.dej.tienda.exception.VendedorNoEncontradoException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class VendedorService implements Serializable {

    static final long serialVersionUID = 99L;

    @PersistenceContext
    private EntityManager em;

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public Vendedor crearVendedor(Vendedor vendedor) {
        em.persist(vendedor);
        return vendedor;
    }

    public Vendedor getVendedoryById(Long id) {
        return em.find(Vendedor.class, id);
    }

    public Vendedor findVendedor(String usuario, String password) {
        String jpql = "SELECT v FROM Vendedor v WHERE v.usuario = :usuario AND v.contrasena = :contrasena";
        TypedQuery<Vendedor> query = em.createQuery(jpql, Vendedor.class);
        query.setParameter("usuario", usuario);
        query.setParameter("contrasena", password);
        try {
            Vendedor vendedor = query.getSingleResult();
            return vendedor;
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Vendedor> getVendedores() {
        String jpql = "SELECT v FROM Vendedor v";
        TypedQuery<Vendedor> query = em.createQuery(jpql, Vendedor.class);
        return query.getResultList();
    }

    public void eliminarVendedor(Long vendedorId) throws VendedorNoEncontradoException {
        Vendedor vendedor = getVendedoryById(vendedorId);
        if (vendedor == null) {
            String mensajeException = String.format("Vendedor con ID %s no encontrado para ser eliminado", vendedorId);
            logger.log(Level.SEVERE, mensajeException);
            throw new VendedorNoEncontradoException(mensajeException);
        }
        em.remove(vendedor);
    }
}

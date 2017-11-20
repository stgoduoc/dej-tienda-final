package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.exception.PedidoNoEncontradoException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class PedidoService implements Serializable {
    
    static final long serialVersionUID = 66L;
    
    @PersistenceContext
    private EntityManager em;
    
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    
    public Pedido crearPedido(Pedido pedido) {
        em.persist(pedido);
        return pedido;
    }
    
    public Pedido getPedidoById(Long id) {
        return em.find(Pedido.class, id);
    }
    
    public List<Pedido> getPedidos() {
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        return query.getResultList();
    }
    
    public void eliminarPedido(Long pedidoId) throws PedidoNoEncontradoException {
        Pedido p = getPedidoById(pedidoId);                
        if (p == null) {
            String mensajeException = String.format("Pedido con ID %s no encontrado para ser eliminado", pedidoId);
            logger.log(Level.SEVERE, mensajeException);
            throw new PedidoNoEncontradoException(mensajeException);
        }
        em.remove(p);
    }
}

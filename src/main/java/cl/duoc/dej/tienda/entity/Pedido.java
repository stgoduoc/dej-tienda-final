package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class Pedido implements Serializable {

    static final long serialVersionUID = 4449879L;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ElementCollection
    @CollectionTable(name = "LINEAPEDIDO")
    private List<LineaPedido> lineasPedido = new ArrayList<>();
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Vendedor vendedor;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar fecha = Calendar.getInstance();

    // Constructores
    public Pedido() {
    }

    // operaciones
    public void quitarProducto(Long productoId) {
        List<LineaPedido> paraRemover = new ArrayList<>();
        for(LineaPedido lp:getLineasPedido()) {
            if(Objects.equals(lp.getProducto().getId(), productoId)) {
                paraRemover.add(lp);
            }
        }
        getLineasPedido().removeAll(paraRemover);
    }

    // cálculos
    public Long getTotalConIva() {
        return getTotal() + getIva();
    }
    
    public Long getIva() {
        return Math.round(0.19d * getTotal().doubleValue() );
    }
    
    public Long getTotal() {
        Long total = 0L;
        for (LineaPedido lp : lineasPedido) {
            total += lp.getPrecio() * lp.getCantidad();
        }
        return total;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

}

package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vendedor implements Serializable {
    
    static final long serialVersionUID = 78979813L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private int rut;
    @Column(nullable = false)
    private char dv;
    @Column(nullable = false, unique = true)
    private String usuario;
    @Column(nullable = false)
    private String contrasena;

    // Constructor
    public Vendedor() {
    }

    public Vendedor(String nombre, int rut, char dv, String usuario, String contrasena) {
        this.nombre = nombre;
        this.rut = rut;
        this.dv = dv;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }
    
    

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public char getDv() {
        return dv;
    }

    public void setDv(char dv) {
        this.dv = dv;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.dej.tienda.exception;

/**
 *
 * @author Santiago Neira <sant.neira@profesor.duoc.cl>
 */
public class ClienteNoEncontradoException extends Exception {

    /**
     * Creates a new instance of <code>ClienteNoEncontradoException</code>
     * without detail message.
     */
    public ClienteNoEncontradoException() {
    }

    /**
     * Constructs an instance of <code>ClienteNoEncontradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ClienteNoEncontradoException(String msg) {
        super(msg);
    }
}

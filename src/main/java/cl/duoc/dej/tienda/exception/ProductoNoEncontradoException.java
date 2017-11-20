package cl.duoc.dej.tienda.exception;

public class ProductoNoEncontradoException extends Exception {

    public ProductoNoEncontradoException() {
    }

    /**
     * Constructs an instance of <code>ProductoNoEncontradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ProductoNoEncontradoException(String msg) {
        super(msg);
    }
}

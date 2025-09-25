package co.edu.unbosque.util.exception;

/**
 * Excepción personalizada que indica que un texto solo puede contener letras.
 * Se utiliza para validar que los campos de texto no contengan números u otros caracteres inválidos.
 */
public class TextException extends Exception {

    /**
     * Crea una nueva instancia de TextException con un mensaje predeterminado.
     * El mensaje indica que solo puede contener letras.
     */
    public TextException() {
        super("Solo puede contener letras.");
    }
}
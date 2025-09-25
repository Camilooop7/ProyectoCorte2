package co.edu.unbosque.util.exception;

/**
 * Excepción personalizada que indica que un número no puede ser negativo.
 * Se utiliza para validar que los valores numéricos ingresados sean positivos.
 */
public class NegativeNumberException extends Exception {

    /**
     * Crea una nueva instancia de NegativeNumberException con un mensaje predeterminado.
     * El mensaje indica que no puede ser un número negativo.
     */
    public NegativeNumberException() {
        super("No puede ser un numero negativo.");
    }
}
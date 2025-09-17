package co.edu.unbosque.util.exception;

/**
 * Clase la cual es publica para ser llamada dentro de otras clases la cual es
 * heredada de Exception
 */
public class NegativeNumberException extends Exception {

	public NegativeNumberException() {
		super("No puede ser un numero negativo.");
	}

}

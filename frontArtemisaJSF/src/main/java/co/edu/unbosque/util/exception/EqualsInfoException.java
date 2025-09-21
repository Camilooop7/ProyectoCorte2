package co.edu.unbosque.util.exception;

/**
 * Clase la cual es publica para ser llamada dentro de otras clases la cual es
 * heredada de Exception
 */
public class EqualsInfoException extends Exception {
	public EqualsInfoException() {
		super("Informacion ya registrada");
	}

}

package co.edu.unbosque.util.exception;
/**
 * Clase la cual es publica para ser llamada dentro de otras clases
 * la cual es heredada de Exception
 */
public class IsBlackException extends Exception {
	public IsBlackException() {
		super("No puede quedar en blanco este espacio");
	}

}

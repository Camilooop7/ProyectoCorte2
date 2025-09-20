package co.edu.unbosque.util.exception;

public class MailException extends Exception {
	public MailException() {
		super("Debe de tener los parametros necesarios de un correo");
	}
}

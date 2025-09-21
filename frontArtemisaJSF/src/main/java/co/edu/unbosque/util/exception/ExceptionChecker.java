package co.edu.unbosque.util.exception;

public class ExceptionChecker {

    public static void checkerMail(String a) throws MailException {
        if (!a.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new MailException();
        }
    }
	
}

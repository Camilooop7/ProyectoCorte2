package co.edu.unbosque.proyectocorte.exception;

public class SesionException extends RuntimeException {
    public SesionException() {
        super("Correo o contraseña inválidos.");
    }
}

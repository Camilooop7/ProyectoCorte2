package co.edu.unbosque.proyectocorte2.exception;

public class SesionException extends RuntimeException {
    public SesionException() {
        super("Correo o contraseña inválidos.");
    }
}

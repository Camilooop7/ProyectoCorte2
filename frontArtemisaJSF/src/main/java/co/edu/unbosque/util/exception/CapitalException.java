package co.edu.unbosque.util.exception;

/**
 * Excepción personalizada que indica que una cadena debe contener al menos una letra mayúscula.
 * Se utiliza para validar contraseñas u otros campos que requieran este requisito.
 */
public class CapitalException extends Exception {

    /**
     * Crea una nueva instancia de CapitalException con un mensaje predeterminado.
     * El mensaje indica que el texto debe contener una mayúscula.
     */
    public CapitalException() {
        super("Debe contener una mayuscula.");
    }
}
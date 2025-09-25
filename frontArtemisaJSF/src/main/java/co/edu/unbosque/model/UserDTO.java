package co.edu.unbosque.model;

/**
 * Clase que representa un usuario del sistema con sus credenciales principales.
 * Se utiliza para transferir información básica de usuario entre capas de la aplicación.
 */
public class UserDTO {

    /** Correo electrónico del usuario. */
    private String correo;

    /** Contraseña del usuario. */
    private String contrasena;

    /**
     * Constructor vacío.
     */
    public UserDTO() {
        // Constructor por defecto.
    }

    /**
     * Constructor que inicializa el correo y la contraseña del usuario.
     * 
     * @param correo el correo electrónico
     * @param contrasena la contraseña
     */
    public UserDTO(String correo, String contrasena) {
        super();
        this.correo = correo;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return el correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param correo el correo electrónico a establecer
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return la contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     * @param contrasena la contraseña a establecer
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
package co.edu.unbosque.model;

import java.io.Serializable;

/**
 * DTO que representa un usuario genérico. Contiene información básica como
 * correo y contraseña.
 */
public class UserDTO {

	/** Correo del usuario. */
	private String correo;

	/** Contraseña del usuario. */
	private String contrasena;

	/**
	 * Constructor vacío.
	 */
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con parámetros.
	 * 
	 * @param correo     Correo del usuario.
	 * @param contrasena Contraseña del usuario.
	 */
	public UserDTO(String correo, String contrasena) {
		super();
		this.correo = correo;
		this.contrasena = contrasena;
	}

	/**
	 * Obtiene el correo del usuario.
	 * 
	 * @return Correo del usuario.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo del usuario.
	 * 
	 * @param correo Correo del usuario.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contraseña del usuario.
	 * 
	 * @return Contraseña del usuario.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña del usuario.
	 * 
	 * @param contrasena Contraseña del usuario.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
}

package co.edu.unbosque.model;

import java.io.Serializable;
/**
 * Clase Abstracta que representa un Usuario, e implementa Serializable.
 */
public abstract class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Indica el username del usuariio.
	 */
	private String username;
	/**
	 * Indica la contraeña del usuariio.
	 */
	private String contrasena;
	/**
	 * Indica el correo del usuariio.
	 */
	private String correo;
	/**
	 * Constructor vacío por defecto.
	 */

	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	 /**
     * Constructor para crear un usuario con nombre de usuario, contraseña y correo.
     *
     * @param username El nombre de usuario del usuario.
     * @param contrasena La contraseña del usuario.
     * @param correo El correo del usuario.
     */
	public Usuario(String username, String contrasena, String correo) {
		super();
		this.username = username;
		this.contrasena = contrasena;
		this.correo = correo;
	}
	
	@Override
	public String toString() {
		return "Username: " + username + ", contrasena: " + contrasena + ", correo: " + correo + ": ";
	}
	/**
	 * GETTERS&SETTERS
	 */

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	

}

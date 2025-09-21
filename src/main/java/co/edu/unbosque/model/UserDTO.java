package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase Abstracta que representa un Persona,y extiende de USuario e implementa
 * Serializable.
 */
public class UserDTO {

	private String correo;
	private String contrasena;

	/**
	 * Constructor vacío.
	 */
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO(String correo, String contrasena) {
		super();
		this.correo = correo;
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
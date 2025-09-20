package co.edu.unbosque.model.persistence;

import jakarta.persistence.*;

public class UserDTO {

	private Long id;
	private String correo;
	private String password;
	private String rol;

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO(String correo, String password, String rol) {
		super();
		this.correo = correo;
		this.password = password;
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}

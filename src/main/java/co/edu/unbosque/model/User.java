package co.edu.unbosque.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String correo;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String rol;

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public User( String correo, String password, String rol) {
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



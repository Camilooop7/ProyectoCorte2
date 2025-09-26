package co.edu.unbosque.beans;

import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Bean de sesión para gestionar el usuario autenticado. Almacena información
 * del usuario y su estado de autenticación.
 */
@Named("auth")
@SessionScoped
public class CurrentUserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID del usuario. */
	private Long id;

	/** Nombre del usuario. */
	private String nombre;

	/** Correo del usuario. */
	private String correo;

	/** Rol del usuario. */
	private String rol;

	/** Estado de autenticación. */
	private boolean loggedIn;

	/**
	 * Verifica si el usuario es administrador.
	 * 
	 * @return {@code true} si el rol es "admin".
	 */
	public boolean isAdmin() {
		return "admin".equals(rol);
	}

	/**
	 * Verifica si el usuario es profesor.
	 * 
	 * @return {@code true} si el rol es "profesor".
	 */
	public boolean isProfesor() {
		return "profesor".equals(rol);
	}

	/**
	 * Verifica si el usuario es estudiante.
	 * 
	 * @return {@code true} si el rol es "estudiantes".
	 */
	public boolean isEstudiante() {
		return "estudiantes".equals(rol);
	}

	/**
	 * Verifica si el usuario tiene un rol específico.
	 * 
	 * @param r Rol a verificar.
	 * @return {@code true} si el rol coincide.
	 */
	public boolean hasRole(String r) {
		return r != null && r.equalsIgnoreCase(rol);
	}

	/**
	 * Establece los datos del usuario tras el login.
	 * 
	 * @param id     ID del usuario.
	 * @param nombre Nombre del usuario.
	 * @param correo Correo del usuario.
	 * @param rol    Rol del usuario.
	 */
	public void setFromLogin(Long id, String nombre, String correo, String rol) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.rol = rol;
		this.loggedIn = true;
	}

	/**
	 * Cierra la sesión del usuario.
	 * 
	 * @return Ruta de redirección.
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public String getRol() {
		return rol;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
}

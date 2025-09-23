package co.edu.unbosque.beans;

import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("auth")
@SessionScoped
public class CurrentUserBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nombre;
	private String correo;
	private String rol; 
	private boolean loggedIn;

	public boolean isAdmin() {
		return "admin".equals(rol);
	}

	public boolean isProfesor() {
		return "profesor".equals(rol);
	}

	public boolean isEstudiante() {
		return "estudiantes".equals(rol);
	}

	public boolean hasRole(String r) {
		return r != null && r.equalsIgnoreCase(rol);
	}

	public void setFromLogin(Long id, String nombre, String correo, String rol) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.rol = rol;
		this.loggedIn = true;
	}

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

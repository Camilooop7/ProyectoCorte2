package co.edu.unbosque.beans;

import co.edu.unbosque.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

	private String correo;
	private String contrasena;
	private String nuevoCorreo;
	private String nuevoUsuario;
	private String nuevaContrasena;

	private boolean loggedIn = false;

	public String login() {
		try {
			String respuesta = UserService.doPost("http://localhost:8081/login/inicio", correo, contrasena);
			String[] data = respuesta.split("\n");
			if ("200".equals(data[0])) {
				loggedIn = true;
				showMessage(FacesMessage.SEVERITY_INFO, "¡Bienvenida!", data[1]);
				clearLoginFields();
				return "index?faces-redirect=true";
			} else {
				loggedIn = false;
				showMessage(FacesMessage.SEVERITY_WARN, "Advertencia", data[1]);
				return null;
			}
		} catch (Exception e) {
			loggedIn = false;
			showMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al iniciar sesión: " + e.getMessage());
			return null;
		}
	}

	public String registrar() {
		try {
			String respuesta = UserService.doPostRegistro("http://localhost:8081/login/registro", nuevoCorreo,
					nuevaContrasena, nuevoUsuario);
			String[] data = respuesta.split("\n");
			if ("200".equals(data[0])) {
				loggedIn = true;
				showMessage(FacesMessage.SEVERITY_INFO, "¡Cuenta creada!", data[1]);
				clearRegisterFields();
				clearLoginFields();
				return "index?faces-redirect=true";
			} else {
				loggedIn = false;
				showMessage(FacesMessage.SEVERITY_WARN, "Advertencia", data[1]);
				return null;
			}
		} catch (Exception e) {
			loggedIn = false;
			showMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear la cuenta: " + e.getMessage());
			return null;
		}
	}

	public String logout() {
		loggedIn = false;
		clearLoginFields();
		clearRegisterFields();
		return "login?faces-redirect=true";
	}

	private void showMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	private void clearLoginFields() {
		correo = "";
		contrasena = "";
	}

	private void clearRegisterFields() {
		nuevoCorreo = "";
		nuevoUsuario = "";
		nuevaContrasena = "";
	}

	// --- Getters & Setters ---
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

	public String getNuevoCorreo() {
		return nuevoCorreo;
	}

	public void setNuevoCorreo(String nuevoCorreo) {
		this.nuevoCorreo = nuevoCorreo;
	}

	public String getNuevoUsuario() {
		return nuevoUsuario;
	}

	public void setNuevoUsuario(String nuevoUsuario) {
		this.nuevoUsuario = nuevoUsuario;
	}

	public String getNuevaContrasena() {
		return nuevaContrasena;
	}

	public void setNuevaContrasena(String nuevaContrasena) {
		this.nuevaContrasena = nuevaContrasena;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
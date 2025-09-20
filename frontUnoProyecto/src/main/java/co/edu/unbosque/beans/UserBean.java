package co.edu.unbosque.beans;

import co.edu.unbosque.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

@Named("userBean")
@RequestScoped
public class UserBean {
	private String username;
	private String password;

	public void doLogin() {
		try {
			// Envía la petición con los parámetros como query parameters
			String respuesta = UserService.doPost("http://localhost:8081/login/inicio", username, password);

			// Procesa la respuesta
			String[] data = respuesta.split("\n");
			showStickyLogin(data[0], data[1]);

			// Limpia los campos del formulario
			username = "";
			password = "";
		} catch (Exception e) {
			e.printStackTrace();
			showStickyLogin("500", "Error al iniciar sesión: " + e.getMessage());
		}
	}

	public void showStickyLogin(String code, String content) {
		FacesMessage.Severity severity;
		String summary;

		switch (code) {
		case "200":
			severity = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
			break;
		case "401":
			severity = FacesMessage.SEVERITY_WARN;
			summary = "Advertencia";
			break;
		default:
			severity = FacesMessage.SEVERITY_ERROR;
			summary = "Error";
			break;
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, content));
	}

	// Getters y Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

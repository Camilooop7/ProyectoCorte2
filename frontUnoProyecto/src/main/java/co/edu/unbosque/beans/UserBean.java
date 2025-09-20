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

	public String doLogin() {
		try {
			String respuesta = UserService.doPost("http://localhost:8081/login/inicio", username, password);

			String[] data = respuesta.split("\n"); 
			String code = (data.length > 0) ? data[0] : "500";
			String msg = (data.length > 1) ? data[1] : "Respuesta inválida del servidor";

			showStickyLogin(code, msg);

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			username = "";
			password = "";

			if ("200".equals(code)) {
				return "menu?faces-redirect=true";
			} else {
				return null; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			showStickyLogin("500", "Error al iniciar sesión: " + e.getMessage());
			return null;
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

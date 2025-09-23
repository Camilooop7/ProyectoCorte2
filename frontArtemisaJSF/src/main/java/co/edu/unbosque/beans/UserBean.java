package co.edu.unbosque.beans;

import co.edu.unbosque.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

@Named("userBean")
@RequestScoped
public class UserBean {
	private String username;
	private String password;

	@Inject
	private CurrentUserBean auth; 

	public String doLogin() {
		try {
			String respuesta = UserService.doPost("http://localhost:8081/login/inicio", username, password);

			String[] data = respuesta.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "";

			if ("200".equals(code)) {
				String rol = extract(body, "\"rol\":\"", "\"");
				String redirect = extract(body, "\"redirect\":\"", "\"");
				String nombre = extract(body, "\"nombre\":\"", "\"");
				String correo = extract(body, "\"correo\":\"", "\"");
				String idStr = extract(body, "\"id\":", ","); 
				if (idStr == null || idStr.isBlank()) {
					idStr = extract(body, "\"id\":", "}"); 
				}
				Long id = null;
				try {
					id = Long.parseLong(idStr.replaceAll("[^0-9]", ""));
				} catch (Exception ignore) {
				}

				if (rol == null || rol.isBlank()) {
					showStickyLogin("500", "Respuesta inválida del servidor (sin rol).");
					return null;
				}

				auth.setFromLogin(id, nombre, correo, rol);

				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				showStickyLogin("200", "Autenticación exitosa como " + rol);


				return "menu?faces-redirect=true";

			} else if ("401".equals(code)) {
				showStickyLogin("401", "Credenciales inválidas");
				return null;
			} else {
				showStickyLogin("500", "Error en login: " + body);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			showStickyLogin("500", "Error al iniciar sesión: " + e.getMessage());
			return null;
		} finally {
			username = "";
			password = "";
		}
	}

	private static String extract(String json, String start, String end) {
		if (json == null)
			return null;
		int i = json.indexOf(start);
		if (i < 0)
			return null;
		i += start.length();
		int j = json.indexOf(end, i);
		if (j < 0)
			return null;
		return json.substring(i, j);
	}

	private void showStickyLogin(String code, String content) {
		FacesMessage.Severity severity;
		String summary;
		switch (code) {
		case "200" -> {
			severity = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
		}
		case "401" -> {
			severity = FacesMessage.SEVERITY_WARN;
			summary = "Advertencia";
		}
		default -> {
			severity = FacesMessage.SEVERITY_ERROR;
			summary = "Error";
		}
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

package co.edu.unbosque.beans;

import co.edu.unbosque.service.ProfesorRegistroService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("profesorBean")
@RequestScoped
public class RegistroPBean {
	private String name; 
	private String documento;
	private String facultad; 
	private String email; 
	private String password; 
	private String role = "PROFESOR";

	public void createAccount() {
		try {
			String respuesta = ProfesorRegistroService.doPostProfesor("http://localhost:8081/profesor/crear", name,
					documento, email, password, role, facultad 
			);

			String[] data = respuesta.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String msg = (data.length > 1) ? data[1] : "Sin cuerpo de respuesta";

			showMsg(code, msg);

			if ("201".equals(code)) {
				name = "";
				documento = "";
				facultad = "";
				email = "";
				password = "";
				
			}

		} catch (Exception e) {
			showMsg("500", "Error al registrar: " + e.getMessage());
		}
	}

	private void showMsg(String code, String content) {
		FacesMessage.Severity sev;
		String summary;
		switch (code) {
		case "201":
			sev = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
			break;
		case "406":
			sev = FacesMessage.SEVERITY_WARN;
			summary = "Advertencia";
			break;
		default:
			sev = FacesMessage.SEVERITY_ERROR;
			summary = "Error";
			break;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, summary, content));
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
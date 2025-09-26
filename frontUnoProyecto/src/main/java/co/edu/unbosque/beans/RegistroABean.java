package co.edu.unbosque.beans;

import co.edu.unbosque.service.AdminRegistroService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Bean de solicitud para registrar administradores.
 */
@Named("adminBean")
@RequestScoped
public class RegistroABean {

	/** Nombre del administrador. */
	private String name;

	/** Documento del administrador. */
	private String documento;

	/** Cargo del administrador. */
	private String cargo;

	/** Correo del administrador. */
	private String email;

	/** Contraseña del administrador. */
	private String password;

	/** Rol del administrador. */
	private String role = "ADMIN";

	/**
	 * Crea una nueva cuenta de administrador.
	 */
	public void createAccount() {
		try {
			String respuesta = AdminRegistroService.doPostAdmin("http://localhost:8081/admin/crear", name, documento,
					email, password, role, cargo);
			String[] data = respuesta.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String msg = (data.length > 1) ? data[1] : "Sin cuerpo de respuesta";
			showMsg(code, msg);
			if ("201".equals(code)) {
				name = "";
				documento = "";
				cargo = "";
				email = "";
				password = "";
			}
		} catch (Exception e) {
			showMsg("500", "Error al registrar: " + e.getMessage());
		}
	}

	/**
	 * Muestra un mensaje según el código de respuesta.
	 * 
	 * @param code    Código de respuesta.
	 * @param content Contenido del mensaje.
	 */
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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
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

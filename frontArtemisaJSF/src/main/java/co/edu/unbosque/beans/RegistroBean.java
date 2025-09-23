package co.edu.unbosque.beans;

import co.edu.unbosque.service.RegistroService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

@Named("registroBean")
@RequestScoped
public class RegistroBean {
	private String nombre;
	private String documento;
	private String semestre;
	private String carrera;
	private String correo;
	private String contrasena;

	public void doRegister() {
		try {
			String respuesta = RegistroService.doPost("http://localhost:8081/estudiante/crear", nombre, documento,
					correo, contrasena, "estudiantes", carrera, semestre);
			String[] data = respuesta.split("\n");
			showStickyRegister(data[0], data[1]);
			// Limpia los campos del formulario
			nombre = "";
			documento = "";
			semestre = "";
			carrera = "";
			correo = "";
			contrasena = "";
		} catch (Exception e) {
			e.printStackTrace();
			showStickyRegister("500", "Error al registrar: " + e.getMessage());
		}
	}

	public void showStickyRegister(String code, String content) {
		FacesMessage.Severity severity;
		String summary;
		switch (code) {
		case "201":
			severity = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
			break;
		case "406":
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
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

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
}

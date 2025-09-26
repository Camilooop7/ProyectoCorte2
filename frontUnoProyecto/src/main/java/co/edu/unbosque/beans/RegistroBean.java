package co.edu.unbosque.beans;

import co.edu.unbosque.service.RegistroService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

/**
 * Bean de solicitud para registrar estudiantes.
 */
@Named("registroBean")
@RequestScoped
public class RegistroBean {

	/** Nombre del estudiante. */
	private String nombre;

	/** Documento del estudiante. */
	private String documento;

	/** Semestre del estudiante. */
	private String semestre;

	/** Carrera del estudiante. */
	private String carrera;

	/** Correo del estudiante. */
	private String correo;

	/** Contraseña del estudiante. */
	private String contrasena;

	/**
	 * Registra un nuevo estudiante.
	 */
	public void doRegister() {
		try {
			String respuesta = RegistroService.doPost("http://localhost:8081/estudiante/crear", nombre, documento,
					correo, contrasena, "estudiantes", carrera, semestre);
			String[] data = respuesta.split("\n");
			showStickyRegister(data[0], data[1]);
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

	/**
	 * Muestra un mensaje de registro.
	 * 
	 * @param code    Código de respuesta.
	 * @param content Contenido del mensaje.
	 */
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

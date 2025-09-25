package co.edu.unbosque.beans;

import co.edu.unbosque.service.RegistroService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

/**
 * Bean para la gestión del registro de estudiantes.
 * Permite registrar un nuevo estudiante y mostrar mensajes de éxito o error en la vista.
 */
@Named("registroBean")
@RequestScoped
public class RegistroBean {
    /** Nombre del estudiante */
    private String nombre;
    /** Documento del estudiante */
    private String documento;
    /** Semestre actual del estudiante */
    private String semestre;
    /** Carrera del estudiante */
    private String carrera;
    /** Correo electrónico del estudiante */
    private String correo;
    /** Contraseña del estudiante */
    private String contrasena;

    /**
     * Registra un nuevo estudiante utilizando los datos actuales del formulario.
     * Muestra mensajes de éxito o error según el resultado.
     */
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

    /**
     * Muestra un mensaje persistente en la interfaz según el código proporcionado.
     * @param code código de estado de la respuesta
     * @param content contenido del mensaje
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

    /**
     * Obtiene el nombre del estudiante.
     * @return nombre del estudiante
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del estudiante.
     * @param nombre nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el documento del estudiante.
     * @return documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Establece el documento del estudiante.
     * @param documento documento a establecer
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Obtiene el semestre del estudiante.
     * @return semestre
     */
    public String getSemestre() {
        return semestre;
    }

    /**
     * Establece el semestre del estudiante.
     * @param semestre semestre a establecer
     */
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    /**
     * Obtiene la carrera del estudiante.
     * @return carrera
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * Establece la carrera del estudiante.
     * @param carrera carrera a establecer
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * Obtiene el correo electrónico del estudiante.
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del estudiante.
     * @param correo correo a establecer
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del estudiante.
     * @return contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del estudiante.
     * @param contrasena contraseña a establecer
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
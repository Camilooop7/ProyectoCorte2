package co.edu.unbosque.beans;

import co.edu.unbosque.service.ProfesorRegistroService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Bean encargado del registro de profesores en el sistema.
 * Provee los métodos y propiedades necesarios para crear una nueva cuenta de profesor,
 * así como la gestión de mensajes de retroalimentación para la interfaz de usuario.
 */
@Named("profesorBean")
@RequestScoped
public class RegistroPBean {
    /** Nombre del profesor. */
    private String name; 

    /** Documento de identificación del profesor. */
    private String documento;

    /** Facultad a la que pertenece el profesor. */
    private String facultad; 

    /** Correo electrónico del profesor. */
    private String email; 

    /** Contraseña del profesor. */
    private String password; 

    /** Rol del usuario, por defecto "PROFESOR". */
    private String role = "PROFESOR";

    /**
     * Crea una cuenta de profesor utilizando el servicio correspondiente.
     * Envía los datos del formulario a la API y muestra mensajes de éxito o error según la respuesta.
     */
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

    /**
     * Muestra un mensaje en la interfaz dependiendo del código recibido.
     * 
     * @param code Código de respuesta (ej: "201", "406", "500").
     * @param content Mensaje a mostrar.
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

    /**
     * Obtiene el nombre del profesor.
     * @return el nombre del profesor.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del profesor.
     * @param name el nombre del profesor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el documento de identificación del profesor.
     * @return el documento de identificación.
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Establece el documento de identificación del profesor.
     * @param documento el documento de identificación.
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Obtiene la facultad a la que pertenece el profesor.
     * @return la facultad.
     */
    public String getFacultad() {
        return facultad;
    }

    /**
     * Establece la facultad a la que pertenece el profesor.
     * @param facultad la facultad.
     */
    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    /**
     * Obtiene el correo electrónico del profesor.
     * @return el correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del profesor.
     * @param email el correo electrónico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del profesor.
     * @return la contraseña.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del profesor.
     * @param password la contraseña.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     * @return el rol.
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     * @param role el rol.
     */
    public void setRole(String role) {
        this.role = role;
    }

}
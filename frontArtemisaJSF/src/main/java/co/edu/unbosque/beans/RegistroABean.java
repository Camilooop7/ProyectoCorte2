package co.edu.unbosque.beans;

import co.edu.unbosque.service.AdminRegistroService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Bean para la gestión del registro de administradores.
 * Permite crear una cuenta de administrador y manejar mensajes de éxito o error en la vista.
 */
@Named("adminBean")
@RequestScoped
public class RegistroABean {
    /** Nombre del administrador */
    private String name;
    /** Documento de identidad */
    private String documento;
    /** Cargo del administrador */
    private String cargo;
    /** Email del administrador */
    private String email;
    /** Contraseña */
    private String password;
    /** Rol asignado (fijo como ADMIN) */
    private String role = "ADMIN";

    /**
     * Crea una nueva cuenta de administrador usando los datos actuales del formulario.
     * Muestra mensajes de éxito o error según el resultado.
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
     * Muestra un mensaje en la interfaz según el código proporcionado.
     * @param code código de estado de la respuesta
     * @param content contenido del mensaje
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

    // Getters y Setters

    /**
     * Obtiene el nombre del administrador.
     * @return nombre del administrador
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del administrador.
     * @param name nombre a establecer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el documento de identidad.
     * @return número de documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Establece el documento de identidad.
     * @param documento número de documento a establecer
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Obtiene el cargo del administrador.
     * @return cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece el cargo del administrador.
     * @param cargo cargo a establecer
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Obtiene el email del administrador.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del administrador.
     * @param email email a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña.
     * @return contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña.
     * @param password contraseña a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol asignado.
     * @return rol
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol asignado.
     * @param role rol a establecer
     */
    public void setRole(String role) {
        this.role = role;
    }
}
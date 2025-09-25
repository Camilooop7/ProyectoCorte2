package co.edu.unbosque.beans;

import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Bean de sesión para gestionar la información y el estado del usuario autenticado.
 * Permite identificar el rol, obtener datos del usuario y gestionar el cierre de sesión.
 * 
 * <p>
 * Este bean usa el contexto de JSF para invalidar la sesión y redirigir al usuario al cerrar sesión.
 * </p>
 * 
 */
@Named("auth")
@SessionScoped
public class CurrentUserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Identificador único del usuario */
    private Long id;

    /** Nombre del usuario */
    private String nombre;

    /** Correo electrónico del usuario */
    private String correo;

    /** Rol asignado al usuario (admin, profesor, estudiantes, etc.) */
    private String rol;

    /** Indica si el usuario está autenticado */
    private boolean loggedIn;

    /**
     * Verifica si el usuario tiene rol de administrador.
     * 
     * @return true si el usuario es admin, false en otro caso
     */
    public boolean isAdmin() {
        return "admin".equals(rol);
    }

    /**
     * Verifica si el usuario tiene rol de profesor.
     * 
     * @return true si el usuario es profesor, false en otro caso
     */
    public boolean isProfesor() {
        return "profesor".equals(rol);
    }

    /**
     * Verifica si el usuario tiene rol de estudiante.
     * 
     * @return true si el usuario es estudiante, false en otro caso
     */
    public boolean isEstudiante() {
        return "estudiantes".equals(rol);
    }

    /**
     * Verifica si el usuario tiene un rol específico.
     * 
     * @param r Rol a comparar
     * @return true si coincide con el rol del usuario, false en otro caso
     */
    public boolean hasRole(String r) {
        return r != null && r.equalsIgnoreCase(rol);
    }

    /**
     * Establece los datos del usuario luego de un inicio de sesión exitoso.
     * 
     * @param id     Identificador del usuario
     * @param nombre Nombre del usuario
     * @param correo Correo electrónico del usuario
     * @param rol    Rol asignado al usuario
     */
    public void setFromLogin(Long id, String nombre, String correo, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.loggedIn = true;
    }

    /**
     * Cierra la sesión del usuario y redirige a la página de inicio.
     * 
     * @return URL de redirección al index.xhtml
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * Obtiene el identificador del usuario.
     * 
     * @return id del usuario
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return correo del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene el rol del usuario.
     * 
     * @return rol del usuario
     */
    public String getRol() {
        return rol;
    }

    /**
     * Indica si el usuario está autenticado.
     * 
     * @return true si está logueado, false en otro caso
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
}
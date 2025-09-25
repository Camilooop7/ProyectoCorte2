package co.edu.unbosque.beans;

import co.edu.unbosque.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

/**
 * Bean encargado de gestionar la autenticación de usuarios en el sistema.
 * Provee métodos para validar credenciales y mostrar mensajes de retroalimentación.
 */
@Named("userBean")
@RequestScoped
public class UserBean {
    /** Nombre de usuario para iniciar sesión. */
    private String username;

    /** Contraseña del usuario. */
    private String password;

    /** Bean de usuario autenticado en sesión. */
    @Inject
    private CurrentUserBean auth; 

    /**
     * Realiza el proceso de login contra el servicio de autenticación.
     * Si las credenciales son válidas, almacena la información del usuario autenticado y redirige al menú.
     * @return la página a la que se debe redirigir o null si hubo error.
     */
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

    /**
     * Extrae un valor de un JSON a partir de un prefijo y un sufijo.
     * @param json Cadena JSON de entrada.
     * @param start Prefijo donde inicia el valor.
     * @param end Sufijo donde termina el valor.
     * @return El valor extraído o null si no se encuentra.
     */
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

    /**
     * Muestra un mensaje de login persistente en la interfaz de usuario.
     * @param code Código de respuesta ("200", "401", "500", etc.).
     * @param content Mensaje a mostrar.
     */
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

    /**
     * Obtiene el nombre de usuario.
     * @return el nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * @param username el nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return la contraseña.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password la contraseña.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
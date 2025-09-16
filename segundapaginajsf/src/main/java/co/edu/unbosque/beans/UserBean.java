package co.edu.unbosque.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named("userBean")
@RequestScoped
public class UserBean {

    // Campos mapeados al formulario
    private String name;
    private String documento;
    private String semestre;
    private String carrera;
    private String email;
    private String password;
    private String role; // se setea con <f:setPropertyActionListener>

    // --- Acción del botón "Usuario" ---
    public void createAccount() {
        // Imprimir en consola
        System.out.println("=== Crear Cuenta (userBean) ===");
        System.out.println("Role: " + role);
        System.out.println("Name: " + name);
        System.out.println("Documento: " + documento);
        System.out.println("Semestre: " + semestre);
        System.out.println("Carrera: " + carrera);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password); // ⚠️ Solo para pruebas. Evita imprimir contraseñas en producción.

        // Mensaje en la UI
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Cuenta creada",
                "Los datos se imprimieron en consola."));
    }

    // --- Getters & Setters requeridos por JSF ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

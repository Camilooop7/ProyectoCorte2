package co.edu.unbosque.model;

public class LoginResponse {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private String redicrecion;
    private String mensaje;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getRedicrecion() { return redicrecion; }
    public void setRedicrecion(String redicrecion) { this.redicrecion = redicrecion; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

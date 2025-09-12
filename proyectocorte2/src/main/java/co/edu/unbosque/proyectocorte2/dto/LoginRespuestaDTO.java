package co.edu.unbosque.proyectocorte2.dto;

import java.util.Objects;

public class LoginRespuestaDTO {
	  private Long id;
	    private String nombre;
	    private String correo;
	    private String rol;           
	    private String redirectPath;  
	    private String mensaje;       
	    
	    public LoginRespuestaDTO() {
			// TODO Auto-generated constructor stub
		}

		public LoginRespuestaDTO(Long id, String nombre, String correo, String rol, String redirectPath, String mensaje) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.correo = correo;
			this.rol = rol;
			this.redirectPath = redirectPath;
			this.mensaje = mensaje;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getCorreo() {
			return correo;
		}

		public void setCorreo(String correo) {
			this.correo = correo;
		}

		public String getRol() {
			return rol;
		}

		public void setRol(String rol) {
			this.rol = rol;
		}

		public String getRedirectPath() {
			return redirectPath;
		}

		public void setRedirectPath(String redirectPath) {
			this.redirectPath = redirectPath;
		}

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		@Override
		public String toString() {
			return "LoginResponse [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", rol=" + rol
					+ ", redirectPath=" + redirectPath + ", mensaje=" + mensaje + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(correo, id, mensaje, nombre, redirectPath, rol);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LoginRespuestaDTO other = (LoginRespuestaDTO) obj;
			return Objects.equals(correo, other.correo) && Objects.equals(id, other.id)
					&& Objects.equals(mensaje, other.mensaje) && Objects.equals(nombre, other.nombre)
					&& Objects.equals(redirectPath, other.redirectPath) && Objects.equals(rol, other.rol);
		}
	    

}

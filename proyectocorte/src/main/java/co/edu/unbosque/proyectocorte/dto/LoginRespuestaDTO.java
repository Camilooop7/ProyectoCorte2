package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

/**
 * DTO para representar la respuesta de inicio de sesión.
 */
public class LoginRespuestaDTO {

	private Long id;
	private String nombre;
	private String correo;
	private String rol;
	private String redicrecion;
	private String mensaje;

	/**
	 * Constructor por defecto.
	 */
	public LoginRespuestaDTO() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param id          ID del usuario.
	 * @param nombre      Nombre del usuario.
	 * @param correo      Correo electrónico del usuario.
	 * @param rol         Rol del usuario.
	 * @param redicrecion Redirección después del inicio de sesión.
	 * @param mensaje     Mensaje de respuesta.
	 */
	public LoginRespuestaDTO(Long id, String nombre, String correo, String rol, String redicrecion, String mensaje) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.rol = rol;
		this.redicrecion = redicrecion;
		this.mensaje = mensaje;
	}

	/**
	 * Obtiene el ID del usuario.
	 *
	 * @return ID del usuario.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID del usuario.
	 *
	 * @param id ID del usuario.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del usuario.
	 *
	 * @return Nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 *
	 * @param nombre Nombre del usuario.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 *
	 * @return Correo electrónico del usuario.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 *
	 * @param correo Correo electrónico del usuario.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el rol del usuario.
	 *
	 * @return Rol del usuario.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el rol del usuario.
	 *
	 * @param rol Rol del usuario.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el mensaje de respuesta.
	 *
	 * @return Mensaje de respuesta.
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Establece el mensaje de respuesta.
	 *
	 * @param mensaje Mensaje de respuesta.
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "LoginResponse [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", rol=" + rol
				+ ", redicrecion=" + redicrecion + ", mensaje=" + mensaje + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(correo, id, mensaje, nombre, redicrecion, rol);
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
				&& Objects.equals(redicrecion, other.redicrecion) && Objects.equals(rol, other.rol);
	}
}

package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

/**
 * DTO para representar la solicitud de inicio de sesión.
 */
public class LoginEnvioDTO {

	private String correo;
	private String contrasena;

	/**
	 * Constructor por defecto.
	 */
	public LoginEnvioDTO() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param correo     Correo electrónico del usuario.
	 * @param contrasena Contraseña del usuario.
	 */
	public LoginEnvioDTO(String correo, String contrasena) {
		super();
		this.correo = correo;
		this.contrasena = contrasena;
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
	 * Obtiene la contraseña del usuario.
	 *
	 * @return Contraseña del usuario.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña del usuario.
	 *
	 * @param contrasena Contraseña del usuario.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "LoginRequest [correo=" + correo + ", contrasena=" + contrasena + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginEnvioDTO other = (LoginEnvioDTO) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(correo, other.correo);
	}
}

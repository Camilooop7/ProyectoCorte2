package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

public class LoginEnvioDTO {
	private String correo;
	private String contrasena;

	public LoginEnvioDTO() {
		// TODO Auto-generated constructor stub
	}

	public LoginEnvioDTO(String correo, String contrasena) {
		super();
		this.correo = correo;
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

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

package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;
import co.edu.unbosque.proyectocorte.entity.Persona;

/**
 * DTO para representar un administrador. Extiende de {@link Persona}.
 */
public class AdminDTO extends Persona {

	private Long id;
	private String cargo;

	/**
	 * Constructor por defecto.
	 */
	public AdminDTO() {
	}

	/**
	 * Constructor con cargo.
	 *
	 * @param cargo Cargo del administrador.
	 */
	public AdminDTO(String cargo) {
		super();
		this.cargo = cargo;
	}

	/**
	 * Constructor completo.
	 *
	 * @param nombre     Nombre del administrador.
	 * @param documento  Documento de identidad del administrador.
	 * @param correo     Correo electrónico del administrador.
	 * @param contrasena Contraseña del administrador.
	 * @param rol        Rol del administrador.
	 * @param cargo      Cargo del administrador.
	 */
	public AdminDTO(String nombre, int documento, String correo, String contrasena, String rol, String cargo) {
		super(nombre, documento, correo, contrasena, rol);
		this.cargo = cargo;
	}

	/**
	 * Constructor sin cargo.
	 *
	 * @param nombre     Nombre del administrador.
	 * @param documento  Documento de identidad del administrador.
	 * @param correo     Correo electrónico del administrador.
	 * @param contrasena Contraseña del administrador.
	 * @param rol        Rol del administrador.
	 */
	public AdminDTO(String nombre, int documento, String correo, String contrasena, String rol) {
		super(nombre, documento, correo, contrasena, rol);
	}

	/**
	 * Obtiene el cargo del administrador.
	 *
	 * @return Cargo del administrador.
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Establece el cargo del administrador.
	 *
	 * @param cargo Cargo del administrador.
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return super.toString() + " Admin [id=" + id + ", cargo=" + cargo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cargo, id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminDTO other = (AdminDTO) obj;
		return Objects.equals(cargo, other.cargo) && Objects.equals(id, other.id);
	}
}

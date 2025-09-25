package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa a un administrador en el sistema. Hereda de
 * {@link Persona}.
 */
@Entity
public class Admin extends Persona {

	/** Identificador único del administrador. */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/** Cargo del administrador. */
	private String cargo;

	/**
	 * Constructor por defecto.
	 */
	public Admin() {
	}

	/**
	 * Constructor con cargo.
	 *
	 * @param cargo Cargo del administrador.
	 */
	public Admin(String cargo) {
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
	public Admin(String nombre, int documento, String correo, String contrasena, String rol, String cargo) {
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
	public Admin(String nombre, int documento, String correo, String contrasena, String rol) {
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
		return "Admin [id=" + id + ", cargo=" + cargo + "]";
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
		Admin other = (Admin) obj;
		return Objects.equals(cargo, other.cargo) && Objects.equals(id, other.id);
	}
}

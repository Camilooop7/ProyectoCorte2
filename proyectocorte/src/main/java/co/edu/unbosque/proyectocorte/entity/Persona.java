package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad abstracta que representa a una persona en el sistema.
 */
@Entity
@Table(name = "persona")
public abstract class Persona {

	/** Identificador único de la persona. */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/** Nombre de la persona. */
	private String nombre;

	/** Documento de identidad de la persona. */
	@Column(unique = true, name = "documento")
	private int documento;

	/** Correo electrónico de la persona. */
	@Column(unique = true, name = "correo")
	private String correo;

	/** Contraseña de la persona. */
	private String contrasena;

	/** Rol de la persona. */
	private String rol;

	/**
	 * Constructor por defecto.
	 */
	public Persona() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param nombre     Nombre de la persona.
	 * @param documento  Documento de identidad de la persona.
	 * @param correo     Correo electrónico de la persona.
	 * @param contrasena Contraseña de la persona.
	 * @param rol        Rol de la persona.
	 */
	public Persona(String nombre, int documento, String correo, String contrasena, String rol) {
		super();
		this.nombre = nombre;
		this.documento = documento;
		this.correo = correo;
		this.contrasena = contrasena;
		this.rol = rol;
	}

	/**
	 * Obtiene el ID de la persona.
	 *
	 * @return ID de la persona.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID de la persona.
	 *
	 * @param id ID de la persona.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de la persona.
	 *
	 * @return Nombre de la persona.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de la persona.
	 *
	 * @param nombre Nombre de la persona.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el documento de identidad de la persona.
	 *
	 * @return Documento de identidad de la persona.
	 */
	public int getDocumento() {
		return documento;
	}

	/**
	 * Establece el documento de identidad de la persona.
	 *
	 * @param documento Documento de identidad de la persona.
	 */
	public void setDocumento(int documento) {
		this.documento = documento;
	}

	/**
	 * Obtiene el correo electrónico de la persona.
	 *
	 * @return Correo electrónico de la persona.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico de la persona.
	 *
	 * @param correo Correo electrónico de la persona.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contraseña de la persona.
	 *
	 * @return Contraseña de la persona.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña de la persona.
	 *
	 * @param contrasena Contraseña de la persona.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Obtiene el rol de la persona.
	 *
	 * @return Rol de la persona.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el rol de la persona.
	 *
	 * @param rol Rol de la persona.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", documento=" + documento + ", correo=" + correo
				+ ", contrasena=" + contrasena + ", rol=" + rol + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, correo, documento, id, nombre, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(correo, other.correo)
				&& documento == other.documento && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(rol, other.rol);
	}
}

package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;
import co.edu.unbosque.proyectocorte.entity.Persona;

/**
 * DTO para representar un profesor. Extiende de {@link Persona}.
 */
public class ProfesorDTO extends Persona {

	private Long id;
	private String departamento;

	/**
	 * Constructor por defecto.
	 */
	public ProfesorDTO() {
	}

	/**
	 * Constructor con departamento.
	 *
	 * @param departamento Departamento del profesor.
	 */
	public ProfesorDTO(String departamento) {
		super();
		this.departamento = departamento;
	}

	/**
	 * Constructor completo.
	 *
	 * @param nombre       Nombre del profesor.
	 * @param documento    Documento de identidad del profesor.
	 * @param correo       Correo electrónico del profesor.
	 * @param contrasena   Contraseña del profesor.
	 * @param rol          Rol del profesor.
	 * @param departamento Departamento del profesor.
	 */
	public ProfesorDTO(String nombre, int documento, String correo, String contrasena, String rol,
			String departamento) {
		super(nombre, documento, correo, contrasena, rol);
		this.departamento = departamento;
	}

	/**
	 * Constructor sin departamento.
	 *
	 * @param nombre     Nombre del profesor.
	 * @param documento  Documento de identidad del profesor.
	 * @param correo     Correo electrónico del profesor.
	 * @param contrasena Contraseña del profesor.
	 * @param rol        Rol del profesor.
	 */
	public ProfesorDTO(String nombre, int documento, String correo, String contrasena, String rol) {
		super(nombre, documento, correo, contrasena, rol);
	}

	/**
	 * Obtiene el ID del profesor.
	 *
	 * @return ID del profesor.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID del profesor.
	 *
	 * @param id ID del profesor.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el departamento del profesor.
	 *
	 * @return Departamento del profesor.
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * Establece el departamento del profesor.
	 *
	 * @param departamento Departamento del profesor.
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", departamento=" + departamento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(departamento, id);
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
		ProfesorDTO other = (ProfesorDTO) obj;
		return Objects.equals(departamento, other.departamento) && Objects.equals(id, other.id);
	}
}

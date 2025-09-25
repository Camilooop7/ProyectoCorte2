package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad abstracta que representa un libro en el sistema.
 */
@Entity
@Table(name = "libro")
public abstract class Libro {

	/** Identificador único del libro. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Código único del libro. */
	@Column(unique = true, name = "codigo")
	private int codigo;

	/** Nombre del libro. */
	private String nombre;

	/** Descripción del libro. */
	private String descripcion;

	/**
	 * Constructor por defecto.
	 */
	public Libro() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param codigo      Código del libro.
	 * @param nombre      Nombre del libro.
	 * @param descripcion Descripción del libro.
	 */
	public Libro(int codigo, String nombre, String descripcion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el ID del libro.
	 *
	 * @return ID del libro.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID del libro.
	 *
	 * @param id ID del libro.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del libro.
	 *
	 * @return Nombre del libro.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del libro.
	 *
	 * @param nombre Nombre del libro.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la descripción del libro.
	 *
	 * @return Descripción del libro.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del libro.
	 *
	 * @param descripcion Descripción del libro.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el código del libro.
	 *
	 * @return Código del libro.
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Establece el código del libro.
	 *
	 * @param codigo Código del libro.
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descripcion, id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return codigo == other.codigo && Objects.equals(descripcion, other.descripcion) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
}

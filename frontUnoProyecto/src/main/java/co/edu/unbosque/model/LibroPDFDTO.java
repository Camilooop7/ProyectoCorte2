package co.edu.unbosque.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * DTO que representa un libro en formato PDF. Contiene información como código,
 * nombre, descripción y contenido del PDF.
 */
public class LibroPDFDTO {

	/** Código del libro. */
	private int codigo;

	/** Identificador único del libro. */
	private Long id;

	/** Nombre del libro. */
	private String nombre;

	/** Descripción del libro. */
	private String descripcion;

	/** Contenido del PDF en bytes. */
	private byte[] contenidoPdf;

	/**
	 * Constructor vacío.
	 */
	public LibroPDFDTO() {
	}

	/**
	 * Constructor con parámetros.
	 * 
	 * @param codigo       Código del libro.
	 * @param id           Identificador único del libro.
	 * @param nombre       Nombre del libro.
	 * @param descripcion  Descripción del libro.
	 * @param contenidoPdf Contenido del PDF en bytes.
	 */
	public LibroPDFDTO(int codigo, Long id, String nombre, String descripcion, byte[] contenidoPdf) {
		super();
		this.codigo = codigo;
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.contenidoPdf = contenidoPdf;
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

	/**
	 * Obtiene el identificador único del libro.
	 * 
	 * @return Identificador único.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del libro.
	 * 
	 * @param id Identificador único.
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
	 * Obtiene el contenido del PDF en bytes.
	 * 
	 * @return Contenido del PDF.
	 */
	public byte[] getContenidoPdf() {
		return contenidoPdf;
	}

	/**
	 * Establece el contenido del PDF en bytes.
	 * 
	 * @param contenidoPdf Contenido del PDF.
	 */
	public void setContenidoPdf(byte[] contenidoPdf) {
		this.contenidoPdf = contenidoPdf;
	}

	/**
	 * Genera un código hash para el objeto.
	 * 
	 * @return Código hash.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(contenidoPdf);
		result = prime * result + Objects.hash(codigo, descripcion, id, nombre);
		return result;
	}

	/**
	 * Compara este objeto con otro para determinar si son iguales.
	 * 
	 * @param obj Objeto a comparar.
	 * @return {@code true} si los objetos son iguales, {@code false} en caso
	 *         contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LibroPDFDTO other = (LibroPDFDTO) obj;
		return codigo == other.codigo && Arrays.equals(contenidoPdf, other.contenidoPdf)
				&& Objects.equals(descripcion, other.descripcion) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre);
	}

	/**
	 * Genera una representación en cadena del objeto.
	 * 
	 * @return Cadena con la información del libro PDF.
	 */
	@Override
	public String toString() {
		return "LibroPDFDTO [codigo=" + codigo + ", id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", contenidoPdf=" + Arrays.toString(contenidoPdf) + "]";
	}
}

package co.edu.unbosque.proyectocorte.entity;

import java.util.Arrays;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

/**
 * Entidad que representa un libro en formato PDF en el sistema. Hereda de
 * {@link Libro}.
 */
@Entity
public class LibroPDF extends Libro {

	/** Contenido del PDF en bytes. */
	@Lob
	private byte[] contenidoPdf;

	/**
	 * Constructor por defecto.
	 */
	public LibroPDF() {
	}

	/**
	 * Constructor con contenido PDF.
	 *
	 * @param contenidoPdf Contenido del PDF en bytes.
	 */
	public LibroPDF(byte[] contenidoPdf) {
		super();
		this.contenidoPdf = contenidoPdf;
	}

	/**
	 * Constructor completo.
	 *
	 * @param codigo       Código del libro.
	 * @param nombre       Nombre del libro.
	 * @param descripcion  Descripción del libro.
	 * @param contenidoPdf Contenido del PDF en bytes.
	 */
	public LibroPDF(int codigo, String nombre, String descripcion, byte[] contenidoPdf) {
		super(codigo, nombre, descripcion);
		this.contenidoPdf = contenidoPdf;
	}

	/**
	 * Constructor sin contenido PDF.
	 *
	 * @param codigo      Código del libro.
	 * @param nombre      Nombre del libro.
	 * @param descripcion Descripción del libro.
	 */
	public LibroPDF(int codigo, String nombre, String descripcion) {
		super(codigo, nombre, descripcion);
	}

	/**
	 * Obtiene el contenido del PDF en bytes.
	 *
	 * @return Contenido del PDF en bytes.
	 */
	public byte[] getContenidoPdf() {
		return contenidoPdf;
	}

	/**
	 * Establece el contenido del PDF en bytes.
	 *
	 * @param contenidoPdf Contenido del PDF en bytes.
	 */
	public void setContenidoPdf(byte[] contenidoPdf) {
		this.contenidoPdf = contenidoPdf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(contenidoPdf);
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
		LibroPDF other = (LibroPDF) obj;
		return Arrays.equals(contenidoPdf, other.contenidoPdf);
	}

	@Override
	public String toString() {
		return super.toString() + " LibroPDF [contenidoPdf=" + Arrays.toString(contenidoPdf) + "]";
	}
}

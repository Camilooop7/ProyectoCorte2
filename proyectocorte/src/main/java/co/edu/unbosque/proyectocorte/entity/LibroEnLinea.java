package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;
import jakarta.persistence.Entity;

/**
 * Entidad que representa un libro en línea en el sistema. Hereda de
 * {@link Libro}.
 */
@Entity
public class LibroEnLinea extends Libro {

	/** Enlace del libro en línea. */
	private String link;

	/**
	 * Constructor por defecto.
	 */
	public LibroEnLinea() {
	}

	/**
	 * Constructor con enlace.
	 *
	 * @param link Enlace del libro en línea.
	 */
	public LibroEnLinea(String link) {
		super();
		this.link = link;
	}

	/**
	 * Constructor completo.
	 *
	 * @param codigo      Código del libro.
	 * @param nombre      Nombre del libro.
	 * @param descripcion Descripción del libro.
	 * @param link        Enlace del libro.
	 */
	public LibroEnLinea(int codigo, String nombre, String descripcion, String link) {
		super(codigo, nombre, descripcion);
		this.link = link;
	}

	/**
	 * Constructor sin enlace.
	 *
	 * @param codigo      Código del libro.
	 * @param nombre      Nombre del libro.
	 * @param descripcion Descripción del libro.
	 */
	public LibroEnLinea(int codigo, String nombre, String descripcion) {
		super(codigo, nombre, descripcion);
	}

	/**
	 * Obtiene el enlace del libro en línea.
	 *
	 * @return Enlace del libro en línea.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Establece el enlace del libro en línea.
	 *
	 * @param link Enlace del libro en línea.
	 */
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(link);
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
		LibroEnLinea other = (LibroEnLinea) obj;
		return Objects.equals(link, other.link);
	}

	@Override
	public String toString() {
		return super.toString() + " LibroEnLinea [link=" + link + "]";
	}
}

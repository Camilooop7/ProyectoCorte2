package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;
import co.edu.unbosque.proyectocorte.entity.Libro;

/**
 * DTO para representar un libro en línea. Extiende de {@link Libro}.
 */
public class LibroEnLineaDTO extends Libro {

	private String link;

	/**
	 * Constructor por defecto.
	 */
	public LibroEnLineaDTO() {
	}

	/**
	 * Constructor con enlace.
	 *
	 * @param link Enlace del libro en línea.
	 */
	public LibroEnLineaDTO(String link) {
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
	public LibroEnLineaDTO(int codigo, String nombre, String descripcion, String link) {
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
	public LibroEnLineaDTO(int codigo, String nombre, String descripcion) {
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
		LibroEnLineaDTO other = (LibroEnLineaDTO) obj;
		return Objects.equals(link, other.link);
	}

	@Override
	public String toString() {
		return super.toString() + " LibroEnLinea [link=" + link + "]";
	}
}

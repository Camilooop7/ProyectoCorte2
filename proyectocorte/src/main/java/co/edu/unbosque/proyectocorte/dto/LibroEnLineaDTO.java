package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

import co.edu.unbosque.proyectocorte.entity.Libro;

public class LibroEnLineaDTO extends Libro {

	private Long id;
	private String link;

	public LibroEnLineaDTO() {
		// TODO Auto-generated constructor stub
	}

	public LibroEnLineaDTO(String link) {
		super();
		this.link = link;
	}

	public LibroEnLineaDTO(String nombre, String descripcion, byte[] imagen, String link) {
		super(nombre, descripcion, imagen);
		this.link = link;
	}

	public LibroEnLineaDTO(String nombre, String descripcion, byte[] imagen) {
		super(nombre, descripcion, imagen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, link);
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
		return Objects.equals(id, other.id) && Objects.equals(link, other.link);
	}

	@Override
	public String toString() {
		return super.toString() + "LibroEnLinea [id=" + id + ", link=" + link + "]";
	}

}

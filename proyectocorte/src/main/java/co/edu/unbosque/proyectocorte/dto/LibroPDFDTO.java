package co.edu.unbosque.proyectocorte.dto;

import java.util.Arrays;
import java.util.Objects;

import co.edu.unbosque.proyectocorte.entity.Libro;

public class LibroPDFDTO extends Libro {
	private Long id;
	private byte[] contenidoPdf;

	public LibroPDFDTO() {
	}

	public LibroPDFDTO(Long id, byte[] contenidoPdf) {
		super();
		this.id = id;
		this.contenidoPdf = contenidoPdf;
	}

	public LibroPDFDTO(String nombre, String descripcion, byte[] imagen, byte[] contenidoPdf) {
		super(nombre, descripcion, imagen);
		this.contenidoPdf = contenidoPdf;
	}

	public LibroPDFDTO(String nombre, String descripcion, byte[] imagen) {
		super(nombre, descripcion, imagen);
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getContenidoPdf() {
		return contenidoPdf;
	}

	public void setContenidoPdf(byte[] contenidoPdf) {
		this.contenidoPdf = contenidoPdf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(contenidoPdf);
		result = prime * result + Objects.hash(id);
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
		LibroPDFDTO other = (LibroPDFDTO) obj;
		return Arrays.equals(contenidoPdf, other.contenidoPdf) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return super.toString() + "LibroPDFDTO [id=" + id + ", contenidoPdf=" + Arrays.toString(contenidoPdf) + "]";
	}

}

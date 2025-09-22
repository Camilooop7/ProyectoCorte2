package co.edu.unbosque.proyectocorte.dto;

import java.util.Arrays;

import co.edu.unbosque.proyectocorte.entity.Libro;

public class LibroPDFDTO extends Libro {

	private byte[] contenidoPdf;

	public LibroPDFDTO() {
	}

	public LibroPDFDTO(byte[] contenidoPdf) {
		super();
		this.contenidoPdf = contenidoPdf;
	}

	public LibroPDFDTO(int codigo, String nombre, String descripcion, byte[] contenidoPdf) {
		super(codigo, nombre, descripcion);
		this.contenidoPdf = contenidoPdf;
	}

	public LibroPDFDTO(int codigo, String nombre, String descripcion) {
		super(codigo, nombre, descripcion);
		// TODO Auto-generated constructor stub
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
		return Arrays.equals(contenidoPdf, other.contenidoPdf);
	}

	@Override
	public String toString() {
		return super.toString() + "LibroPDF [contenidoPdf=" + Arrays.toString(contenidoPdf) + "]";
	}

}

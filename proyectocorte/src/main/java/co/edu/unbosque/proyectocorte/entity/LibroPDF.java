package co.edu.unbosque.proyectocorte.entity;

import java.util.Arrays;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class LibroPDF extends Libro {

	@Lob
	private byte[] contenidoPdf;

	public LibroPDF() {
	}

	public LibroPDF(byte[] contenidoPdf) {
		super();
		this.contenidoPdf = contenidoPdf;
	}

	public LibroPDF(int codigo, String nombre, String descripcion, byte[] imagen, byte[] contenidoPdf) {
		super(codigo, nombre, descripcion, imagen);
		this.contenidoPdf = contenidoPdf;
	}

	// Getters y setters

	public LibroPDF(int codigo, String nombre, String descripcion, byte[] imagen) {
		super(codigo, nombre, descripcion, imagen);
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
		LibroPDF other = (LibroPDF) obj;
		return Arrays.equals(contenidoPdf, other.contenidoPdf);
	}

	@Override
	public String toString() {
		return super.toString() + "LibroPDF [contenidoPdf=" + Arrays.toString(contenidoPdf) + "]";
	}

}

package co.edu.unbosque.model;

import java.util.Arrays;
import java.util.Objects;

public class LibroPDFDTO {
	private int codigo;
	private Long id;
	private String nombre;
	private String descripcion;
	private byte[] imagen;
	private byte[] contenidoPdf;

	public LibroPDFDTO() {
	}

	public LibroPDFDTO(int codigo, Long id, String nombre, String descripcion, byte[] imagen, byte[] contenidoPdf) {
		super();
		this.codigo = codigo;
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.contenidoPdf = contenidoPdf;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
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
		int result = 1;
		result = prime * result + Arrays.hashCode(contenidoPdf);
		result = prime * result + Arrays.hashCode(imagen);
		result = prime * result + Objects.hash(codigo, descripcion, id, nombre);
		return result;
	}

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
				&& Arrays.equals(imagen, other.imagen) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "LibroPDFDTO [codigo=" + codigo + ", id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", imagen=" + Arrays.toString(imagen) + ", contenidoPdf=" + Arrays.toString(contenidoPdf) + "]";
	}

}
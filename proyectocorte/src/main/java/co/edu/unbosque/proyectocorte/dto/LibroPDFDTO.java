package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

public class LibroPDFDTO {
	private Long id;
	private String nombre;
	private String descripcion;
	private byte[] imagen;
	private byte[] contenidoPdf;

	// Constructores
	public LibroPDFDTO() {
	}

	public LibroPDFDTO(Long id, String nombre, String descripcion, byte[] imagen, byte[] contenidoPdf) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.contenidoPdf = contenidoPdf;
	}

	// Getters y setters
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
		return Objects.hash(id, nombre, descripcion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		LibroPDFDTO other = (LibroPDFDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(descripcion, other.descripcion);
	}
}

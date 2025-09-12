package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

import co.edu.unbosque.proyectocorte.entity.Libro;

public class LibroPDFDTO extends Libro {

	private Long id;
	private String nombrePdf;

	public LibroPDFDTO() {
		// TODO Auto-generated constructor stub
	}

	public LibroPDFDTO(String nombrePdf) {
		super();
		this.nombrePdf = nombrePdf;
	}

	public LibroPDFDTO(String nombre, String descripcion, String imagen, String nombrePdf) {
		super(nombre, descripcion, imagen);
		this.nombrePdf = nombrePdf;
	}

	public LibroPDFDTO(String nombre, String descripcion, String imagen) {
		super(nombre, descripcion, imagen);
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombrePdf() {
		return nombrePdf;
	}

	public void setNombrePdf(String nombrePdf) {
		this.nombrePdf = nombrePdf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, nombrePdf);
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
		return Objects.equals(id, other.id) && Objects.equals(nombrePdf, other.nombrePdf);
	}

	@Override
	public String toString() {
		return super.toString() + "LibroPDF [id=" + id + ", nombrePdf=" + nombrePdf + "]";
	}

}

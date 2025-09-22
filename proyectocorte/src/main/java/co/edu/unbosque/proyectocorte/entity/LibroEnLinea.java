package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;

import jakarta.persistence.Entity;

@Entity

public class LibroEnLinea extends Libro {

	private String link;

	public LibroEnLinea() {
		// TODO Auto-generated constructor stub
	}

	public LibroEnLinea(String link) {
		super();
		this.link = link;
	}

	public LibroEnLinea(int codigo, String nombre, String descripcion, String link) {
		super(codigo, nombre, descripcion);
		this.link = link;
	}

	public LibroEnLinea(int codigo, String nombre, String descripcion) {
		super(codigo, nombre, descripcion);
		// TODO Auto-generated constructor stub
	}

	public String getLink() {
		return link;
	}

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
		return super.toString() + "LibroEnLinea [link=" + link + "]";
	}

}

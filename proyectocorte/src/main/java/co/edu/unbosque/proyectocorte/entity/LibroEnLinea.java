package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "libroenlinea")

public class LibroEnLinea extends Libro {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String link;

	public LibroEnLinea() {
		// TODO Auto-generated constructor stub
	}

	public LibroEnLinea(String link) {
		super();
		this.link = link;
	}

	public LibroEnLinea(String nombre, String descripcion, String imagen, String link) {
		super(nombre, descripcion, imagen);
		this.link = link;
	}

	public LibroEnLinea(String nombre, String descripcion, String imagen) {
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
		LibroEnLinea other = (LibroEnLinea) obj;
		return Objects.equals(id, other.id) && Objects.equals(link, other.link);
	}

	@Override
	public String toString() {
		return super.toString() + "LibroEnLinea [id=" + id + ", link=" + link + "]";
	}

}

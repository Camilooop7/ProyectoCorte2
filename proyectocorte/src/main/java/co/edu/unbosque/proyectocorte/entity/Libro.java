package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public abstract class Libro {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String nombre;
	private String descripcion;
	private String imagen;

	public Libro() {
		// TODO Auto-generated constructor stub
	}

	public Libro(String nombre, String descripcion, String imagen) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen = imagen;
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

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, id, imagen, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(id, other.id)
				&& Objects.equals(imagen, other.imagen) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", imagen=" + imagen + "]";
	}

}

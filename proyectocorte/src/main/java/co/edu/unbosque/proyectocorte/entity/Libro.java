package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "libro")
public abstract class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, name = "codigo")
	private int codigo;
	private String nombre;
	private String descripcion;

	public Libro() {
	}

	public Libro(int codigo, String nombre, String descripcion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descripcion, id, nombre);
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
		return codigo == other.codigo && Objects.equals(descripcion, other.descripcion) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

}

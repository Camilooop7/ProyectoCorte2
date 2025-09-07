package co.edu.unbosque.proyectocorte2.enty;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class Profesor extends Persona {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String departamento;
	
	public Profesor() {
		// TODO Auto-generated constructor stub
	}

	public Profesor(String departamento) {
		super();
		this.departamento = departamento;
	}

	public Profesor(String nombre, int documento, String correo, String contrasena, String rol, String departamento) {
		super(nombre, documento, correo, contrasena, rol);
		this.departamento = departamento;
	}

	public Profesor(String nombre, int documento, String correo, String contrasena, String rol) {
		super(nombre, documento, correo, contrasena, rol);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", departamento=" + departamento + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(departamento, id);
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
		Profesor other = (Profesor) obj;
		return Objects.equals(departamento, other.departamento) && Objects.equals(id, other.id);
	}
	
	
	

}

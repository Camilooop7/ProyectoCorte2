package co.edu.unbosque.proyectocorte2.dto;

import java.util.Objects;

import co.edu.unbosque.proyectocorte2.enty.Persona;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public class ProfesorDTO extends Persona {
	private  Long id;
	private String departamento;
	
	public ProfesorDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProfesorDTO(String departamento) {
		super();
		this.departamento = departamento;
	}

	public ProfesorDTO(String nombre, int edad, String correo, String contrasena, String rol, String departamento) {
		super(nombre, edad, correo, contrasena, rol);
		this.departamento = departamento;
	}

	public ProfesorDTO(String nombre, int edad, String correo, String contrasena, String rol) {
		super(nombre, edad, correo, contrasena, rol);
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
		ProfesorDTO other = (ProfesorDTO) obj;
		return Objects.equals(departamento, other.departamento) && Objects.equals(id, other.id);
	}
	
	
	

}

package co.edu.unbosque.proyectocorte2.dto;

import java.util.Objects;

import co.edu.unbosque.proyectocorte2.enty.Persona;



public class AdminDTO extends Persona{
	private Long id;
	private String cargo;
	 
	public AdminDTO() {
		// TODO Auto-generated constructor stub
	}

	public AdminDTO(String cargo) {
		super();
		this.cargo = cargo;
	}

	public AdminDTO(String nombre, int edad, String correo, String contrasena, String rol, String cargo) {
		super(nombre, edad, correo, contrasena, rol);
		this.cargo = cargo;
	}

	public AdminDTO(String nombre, int edad, String correo, String contrasena, String rol) {
		super(nombre, edad, correo, contrasena, rol);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", cargo=" + cargo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cargo, id);
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
		AdminDTO other = (AdminDTO) obj;
		return Objects.equals(cargo, other.cargo) && Objects.equals(id, other.id);
	}
	

}

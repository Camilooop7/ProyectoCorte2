package co.edu.unbosque.proyectocorte2.enty;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin extends Persona{
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String cargo;
	 
	public Admin() {
		// TODO Auto-generated constructor stub
	}

	public Admin(String cargo) {
		super();
		this.cargo = cargo;
	}

	public Admin(String nombre, int documento, String correo, String contrasena, String rol, String cargo) {
		super(nombre, documento, correo, contrasena, rol);
		this.cargo = cargo;
	}

	public Admin(String nombre, int documento, String correo, String contrasena, String rol) {
		super(nombre, documento, correo, contrasena, rol);
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
		Admin other = (Admin) obj;
		return Objects.equals(cargo, other.cargo) && Objects.equals(id, other.id);
	}
	

}

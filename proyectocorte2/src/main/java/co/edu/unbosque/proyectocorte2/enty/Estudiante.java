package co.edu.unbosque.proyectocorte2.enty;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiante")
public class Estudiante extends Persona{
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String carrera;
	private int semestre;
	
	public Estudiante() {
		// TODO Auto-generated constructor stub
	}

	public Estudiante(String carrera, int semestre) {
		super();
		this.carrera = carrera;
		this.semestre = semestre;
	}

	public Estudiante(String nombre, int documento, String correo, String contrasena, String rol, String carrera,
			int semestre) {
		super(nombre, documento, correo, contrasena, rol);
		this.carrera = carrera;
		this.semestre = semestre;
	}

	public Estudiante(String nombre, int documento, String correo, String contrasena, String rol) {
		super(nombre, documento, correo, contrasena, rol);
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	@Override
	public String toString() {
		return "Estudiante [id=" + id + ", carrera=" + carrera + ", semestre=" + semestre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(carrera, id, semestre);
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
		Estudiante other = (Estudiante) obj;
		return Objects.equals(carrera, other.carrera) && Objects.equals(id, other.id) && semestre == other.semestre;
	}
	

}

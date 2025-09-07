package co.edu.unbosque.proyectocorte2.dto;

import java.util.Objects;

import co.edu.unbosque.proyectocorte2.enty.Persona;



public class EstudianteDTO extends Persona{
	private Long id;
	private String carrera;
	private int semestre;
	
	public EstudianteDTO() {
		// TODO Auto-generated constructor stub
	}

	public EstudianteDTO(String carrera, int semestre) {
		super();
		this.carrera = carrera;
		this.semestre = semestre;
	}

	public EstudianteDTO(String nombre, int documento, String correo, String contrasena, String rol, String carrera,
			int semestre) {
		super(nombre, documento, correo, contrasena, rol);
		this.carrera = carrera;
		this.semestre = semestre;
	}

	public EstudianteDTO(String nombre, int documento, String correo, String contrasena, String rol) {
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
		EstudianteDTO other = (EstudianteDTO) obj;
		return Objects.equals(carrera, other.carrera) && Objects.equals(id, other.id) && semestre == other.semestre;
	}
	

}

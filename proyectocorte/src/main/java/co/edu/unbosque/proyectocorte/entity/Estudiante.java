package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa a un estudiante en el sistema. Hereda de
 * {@link Persona}.
 */
@Entity
public class Estudiante extends Persona {

	/** Identificador único del estudiante. */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/** Carrera del estudiante. */
	private String carrera;

	/** Semestre del estudiante. */
	private int semestre;

	/**
	 * Constructor por defecto.
	 */
	public Estudiante() {
	}

	/**
	 * Constructor con carrera y semestre.
	 *
	 * @param carrera  Carrera del estudiante.
	 * @param semestre Semestre del estudiante.
	 */
	public Estudiante(String carrera, int semestre) {
		super();
		this.carrera = carrera;
		this.semestre = semestre;
	}

	/**
	 * Constructor completo.
	 *
	 * @param nombre     Nombre del estudiante.
	 * @param documento  Documento de identidad del estudiante.
	 * @param correo     Correo electrónico del estudiante.
	 * @param contrasena Contraseña del estudiante.
	 * @param rol        Rol del estudiante.
	 * @param carrera    Carrera del estudiante.
	 * @param semestre   Semestre del estudiante.
	 */
	public Estudiante(String nombre, int documento, String correo, String contrasena, String rol, String carrera,
			int semestre) {
		super(nombre, documento, correo, contrasena, rol);
		this.carrera = carrera;
		this.semestre = semestre;
	}

	/**
	 * Constructor sin carrera y semestre.
	 *
	 * @param nombre     Nombre del estudiante.
	 * @param documento  Documento de identidad del estudiante.
	 * @param correo     Correo electrónico del estudiante.
	 * @param contrasena Contraseña del estudiante.
	 * @param rol        Rol del estudiante.
	 */
	public Estudiante(String nombre, int documento, String correo, String contrasena, String rol) {
		super(nombre, documento, correo, contrasena, rol);
	}

	/**
	 * Obtiene el ID del estudiante.
	 *
	 * @return ID del estudiante.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID del estudiante.
	 *
	 * @param id ID del estudiante.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene la carrera del estudiante.
	 *
	 * @return Carrera del estudiante.
	 */
	public String getCarrera() {
		return carrera;
	}

	/**
	 * Establece la carrera del estudiante.
	 *
	 * @param carrera Carrera del estudiante.
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	/**
	 * Obtiene el semestre del estudiante.
	 *
	 * @return Semestre del estudiante.
	 */
	public int getSemestre() {
		return semestre;
	}

	/**
	 * Establece el semestre del estudiante.
	 *
	 * @param semestre Semestre del estudiante.
	 */
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

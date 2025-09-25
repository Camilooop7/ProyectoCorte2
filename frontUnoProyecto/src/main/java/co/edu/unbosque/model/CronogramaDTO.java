package co.edu.unbosque.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * DTO que representa un evento en el cronograma. Contiene información básica
 * como nombre, enlace y fecha del evento.
 */
public class CronogramaDTO {

	/** Identificador único del evento. */
	private Long id;

	/** Nombre del evento. */
	public String nombre;

	/** Enlace asociado al evento. */
	public String link;

	/** Fecha del evento. */
	private LocalDate fecha;

	/**
	 * Constructor vacío.
	 */
	public CronogramaDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con parámetros.
	 * 
	 * @param nombre Nombre del evento.
	 * @param link   Enlace asociado al evento.
	 * @param fecha  Fecha del evento.
	 */
	public CronogramaDTO(String nombre, String link, LocalDate fecha) {
		super();
		this.nombre = nombre;
		this.link = link;
		this.fecha = fecha;
	}

	/**
	 * Obtiene el identificador único del evento.
	 * 
	 * @return Identificador único.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del evento.
	 * 
	 * @param id Identificador único.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del evento.
	 * 
	 * @return Nombre del evento.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del evento.
	 * 
	 * @param nombre Nombre del evento.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el enlace asociado al evento.
	 * 
	 * @return Enlace del evento.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Establece el enlace asociado al evento.
	 * 
	 * @param link Enlace del evento.
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Obtiene la fecha del evento.
	 * 
	 * @return Fecha del evento.
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del evento.
	 * 
	 * @param fecha Fecha del evento.
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Genera una representación en cadena del objeto.
	 * 
	 * @return Cadena con la información del evento.
	 */
	@Override
	public String toString() {
		String f = fecha != null ? fecha.toString() : "";
		return "Cronograma [id=" + id + ", nombre=" + nombre + ", link=" + link + ", fecha=" + f + "]";
	}

	/**
	 * Genera un código hash para el objeto.
	 * 
	 * @return Código hash.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, link, nombre);
	}

	/**
	 * Compara este objeto con otro para determinar si son iguales.
	 * 
	 * @param obj Objeto a comparar.
	 * @return {@code true} si los objetos son iguales, {@code false} en caso
	 *         contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CronogramaDTO other = (CronogramaDTO) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(link, other.link)
				&& Objects.equals(nombre, other.nombre);
	}
}

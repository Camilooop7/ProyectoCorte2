package co.edu.unbosque.proyectocorte.dto;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO para representar un cronograma.
 */
public class CronogramaDTO {

	private Long id;
	public String nombre;
	public String link;
	private LocalDate fecha;

	/**
	 * Constructor por defecto.
	 */
	public CronogramaDTO() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param nombre Nombre del cronograma.
	 * @param link   Enlace del cronograma.
	 * @param fecha  Fecha del cronograma.
	 */
	public CronogramaDTO(String nombre, String link, LocalDate fecha) {
		super();
		this.nombre = nombre;
		this.link = link;
		this.fecha = fecha;
	}

	/**
	 * Obtiene el ID del cronograma.
	 *
	 * @return ID del cronograma.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID del cronograma.
	 *
	 * @param id ID del cronograma.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del cronograma.
	 *
	 * @return Nombre del cronograma.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del cronograma.
	 *
	 * @param nombre Nombre del cronograma.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el enlace del cronograma.
	 *
	 * @return Enlace del cronograma.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Establece el enlace del cronograma.
	 *
	 * @param link Enlace del cronograma.
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Obtiene la fecha del cronograma.
	 *
	 * @return Fecha del cronograma.
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del cronograma.
	 *
	 * @param fecha Fecha del cronograma.
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		String f = fecha != null ? new SimpleDateFormat("yyyy-MM-dd").format(fecha) : "";
		return "Cronograma [id=" + id + ", nombre=" + nombre + ", link=" + link + ", fecha=" + f + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, link, nombre);
	}

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

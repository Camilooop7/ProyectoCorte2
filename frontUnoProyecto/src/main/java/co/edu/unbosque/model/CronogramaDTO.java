package co.edu.unbosque.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;



public class CronogramaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	
	private String nombre;

	
	private String link;

	
	private LocalDate fecha; 

	public CronogramaDTO() {
	}

	public CronogramaDTO(String nombre, String link, LocalDate fecha) {
		this.nombre = nombre;
		this.link = link;
		this.fecha = fecha;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CronogramaDTO))
			return false;
		CronogramaDTO that = (CronogramaDTO) o;
		return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(link, that.link)
				&& Objects.equals(fecha, that.fecha);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, link, fecha);
	}

	@Override
	public String toString() {
		return "CronogramaDTO{" + "id=" + id + ", nombre='" + nombre + '\'' + ", link='" + link + '\'' + ", fecha="
				+ fecha + '}';
	}
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	
}

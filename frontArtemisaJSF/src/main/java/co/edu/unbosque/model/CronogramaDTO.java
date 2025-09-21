package co.edu.unbosque.model;

import java.util.Date;
import java.util.Objects;

public class CronogramaDTO {

	private Long id;
	public String nombre;
	public String link;
	private Date fecha;

	public CronogramaDTO() {
		// TODO Auto-generated constructor stub
	}

	public CronogramaDTO(String nombre, String link, Date fecha) {
		super();
		this.nombre = nombre;
		this.link = link;
		this.fecha = fecha;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Cronograma [id=" + id + ", nombre=" + nombre + ", link=" + link + "]";
	}

}
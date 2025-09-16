package co.edu.unbosque.model;

import java.util.Date;

public class PersonaDTO {

	
	private String nombre;
	private String color;
	private Date fecha;
	
	
	

	public PersonaDTO(String nombre, String color, Date fecha) {
		super();
		this.nombre = nombre;
		this.color = color;
		this.fecha = fecha;
	}



	public PersonaDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}

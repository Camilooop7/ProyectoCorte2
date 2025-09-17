package co.edu.unbosque.proyectocorte.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cronograma")
public class Cronograma {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	public String nombre;
	public String link;
	private Date fecha;
	
	
	public Cronograma() {
		// TODO Auto-generated constructor stub
	}



	public Cronograma(String nombre, String link, Date fecha) {
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
		return "Cronograma [id=" + id + ", nombre=" + nombre + ", link=" + link + ", fecha=" + fecha + "]";
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
		Cronograma other = (Cronograma) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(link, other.link)
				&& Objects.equals(nombre, other.nombre);
	}


	

}

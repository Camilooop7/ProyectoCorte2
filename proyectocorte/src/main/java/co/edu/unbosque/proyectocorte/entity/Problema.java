package co.edu.unbosque.proyectocorte.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "problema")
public class Problema {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String titulo;
	private int dificultad;
	private String tema;
	private String juez;
	private String link;

	public Problema() {
		// TODO Auto-generated constructor stub
	}

	public Problema(String titulo, int dificultad, String tema, String juez, String link) {
		super();
		this.titulo = titulo;
		this.dificultad = dificultad;
		this.tema = tema;
		this.juez = juez;
		this.link = link;
	}

	@Override
	public String toString() {
		return "Problema [id=" + id + ", titulo=" + titulo + ", dificultad=" + dificultad + ", tema=" + tema + ", juez="
				+ juez + ", link=" + link + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getJuez() {
		return juez;
	}

	public void setJuez(String juez) {
		this.juez = juez;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dificultad, id, juez, link, tema, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Problema other = (Problema) obj;
		return dificultad == other.dificultad && Objects.equals(id, other.id) && Objects.equals(juez, other.juez)
				&& Objects.equals(link, other.link) && Objects.equals(tema, other.tema)
				&& Objects.equals(titulo, other.titulo);
	}
	

}

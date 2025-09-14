package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

public class ProblemaDTO {
	private Long id;
	private String titulo;
	private int dificultad;
	private String tema;
	private String jeuz;
	private String link;

	public ProblemaDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProblemaDTO(String titulo, int dificultad, String tema, String jeuz, String link) {
		super();
		this.titulo = titulo;
		this.dificultad = dificultad;
		this.tema = tema;
		this.jeuz = jeuz;
		this.link = link;
	}

	@Override
	public String toString() {
		return "Problema [id=" + id + ", titulo=" + titulo + ", dificultad=" + dificultad + ", tema=" + tema + ", jeuz="
				+ jeuz + ", link=" + link + "]";
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

	public String getJeuz() {
		return jeuz;
	}

	public void setJeuz(String jeuz) {
		this.jeuz = jeuz;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dificultad, id, jeuz, link, tema, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemaDTO other = (ProblemaDTO) obj;
		return dificultad == other.dificultad && Objects.equals(id, other.id) && Objects.equals(jeuz, other.jeuz)
				&& Objects.equals(link, other.link) && Objects.equals(tema, other.tema)
				&& Objects.equals(titulo, other.titulo);
	}
	

}

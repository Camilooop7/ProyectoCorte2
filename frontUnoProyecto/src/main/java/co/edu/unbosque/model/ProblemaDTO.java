package co.edu.unbosque.model;

import java.util.Objects;

public class ProblemaDTO {
	private Long id;
	private String titulo;
	private int dificultad;
	private String tema;
	private String juez;
	private String link;

	public ProblemaDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProblemaDTO(String titulo, int dificultad, String tema, String juez, String link) {
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

	
	

}

package co.edu.unbosque.model;

import java.util.Objects;

/**
 * DTO que representa un problema de programación. Contiene información como
 * título, dificultad, tema, juez y enlace.
 */
public class ProblemaDTO {

	/** Identificador único del problema. */
	private Long id;

	/** Título del problema. */
	private String titulo;

	/** Dificultad del problema. */
	private int dificultad;

	/** Tema del problema. */
	private String tema;

	/** Juez del problema. */
	private String juez;

	/** Enlace del problema. */
	private String link;

	/**
	 * Constructor vacío.
	 */
	public ProblemaDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con parámetros.
	 * 
	 * @param titulo     Título del problema.
	 * @param dificultad Dificultad del problema.
	 * @param tema       Tema del problema.
	 * @param juez       Juez del problema.
	 * @param link       Enlace del problema.
	 */
	public ProblemaDTO(String titulo, int dificultad, String tema, String juez, String link) {
		super();
		this.titulo = titulo;
		this.dificultad = dificultad;
		this.tema = tema;
		this.juez = juez;
		this.link = link;
	}

	/**
	 * Genera una representación en cadena del objeto.
	 * 
	 * @return Cadena con la información del problema.
	 */
	@Override
	public String toString() {
		return "Problema [id=" + id + ", titulo=" + titulo + ", dificultad=" + dificultad + ", tema=" + tema + ", juez="
				+ juez + ", link=" + link + "]";
	}

	/**
	 * Obtiene el identificador único del problema.
	 * 
	 * @return Identificador único.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del problema.
	 * 
	 * @param id Identificador único.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el título del problema.
	 * 
	 * @return Título del problema.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Establece el título del problema.
	 * 
	 * @param titulo Título del problema.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Obtiene la dificultad del problema.
	 * 
	 * @return Dificultad del problema.
	 */
	public int getDificultad() {
		return dificultad;
	}

	/**
	 * Establece la dificultad del problema.
	 * 
	 * @param dificultad Dificultad del problema.
	 */
	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	/**
	 * Obtiene el tema del problema.
	 * 
	 * @return Tema del problema.
	 */
	public String getTema() {
		return tema;
	}

	/**
	 * Establece el tema del problema.
	 * 
	 * @param tema Tema del problema.
	 */
	public void setTema(String tema) {
		this.tema = tema;
	}

	/**
	 * Obtiene el juez del problema.
	 * 
	 * @return Juez del problema.
	 */
	public String getJuez() {
		return juez;
	}

	/**
	 * Establece el juez del problema.
	 * 
	 * @param juez Juez del problema.
	 */
	public void setJuez(String juez) {
		this.juez = juez;
	}

	/**
	 * Obtiene el enlace del problema.
	 * 
	 * @return Enlace del problema.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Establece el enlace del problema.
	 * 
	 * @param link Enlace del problema.
	 */
	public void setLink(String link) {
		this.link = link;
	}
}

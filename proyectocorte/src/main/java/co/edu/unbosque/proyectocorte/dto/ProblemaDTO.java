package co.edu.unbosque.proyectocorte.dto;

import java.util.Objects;

/**
 * DTO para representar un problema.
 */
public class ProblemaDTO {

	private Long id;
	private String titulo;
	private int dificultad;
	private String tema;
	private String juez;
	private String link;

	/**
	 * Constructor por defecto.
	 */
	public ProblemaDTO() {
	}

	/**
	 * Constructor completo.
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
	 * Obtiene el ID del problema.
	 *
	 * @return ID del problema.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID del problema.
	 *
	 * @param id ID del problema.
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

	@Override
	public String toString() {
		return "Problema [id=" + id + ", titulo=" + titulo + ", dificultad=" + dificultad + ", tema=" + tema + ", juez="
				+ juez + ", link=" + link + "]";
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
		ProblemaDTO other = (ProblemaDTO) obj;
		return dificultad == other.dificultad && Objects.equals(id, other.id) && Objects.equals(juez, other.juez)
				&& Objects.equals(link, other.link) && Objects.equals(tema, other.tema)
				&& Objects.equals(titulo, other.titulo);
	}
}

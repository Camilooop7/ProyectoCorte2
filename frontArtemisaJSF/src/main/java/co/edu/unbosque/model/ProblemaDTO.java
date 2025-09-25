package co.edu.unbosque.model;

import java.util.Objects;

/**
 * Representa un problema con datos como título, dificultad, tema, juez y enlace.
 * Esta clase se utiliza para transferir información sobre problemas de programación o similares.
 */
public class ProblemaDTO {
    /** Identificador único del problema. */
    private Long id;

    /** Título del problema. */
    private String titulo;

    /** Nivel de dificultad del problema. */
    private int dificultad;

    /** Tema principal del problema. */
    private String tema;

    /** Juez asociado al problema. */
    private String juez;

    /** Enlace al problema. */
    private String link;

    /**
     * Constructor vacío para la creación de instancias por defecto.
     */
    public ProblemaDTO() {
        // Constructor por defecto.
    }

    /**
     * Constructor que inicializa los campos principales del problema.
     * 
     * @param titulo título del problema
     * @param dificultad nivel de dificultad del problema
     * @param tema tema principal del problema
     * @param juez juez asociado al problema
     * @param link enlace al problema
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
     * Devuelve una representación en cadena del objeto ProblemaDTO.
     * @return una cadena con la información del problema
     */
    @Override
    public String toString() {
        return "Problema [id=" + id + ", titulo=" + titulo + ", dificultad=" + dificultad + ", tema=" + tema + ", juez="
                + juez + ", link=" + link + "]";
    }

    /**
     * Obtiene el identificador único del problema.
     * @return el id del problema
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del problema.
     * @param id el id a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el título del problema.
     * @return el título del problema
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del problema.
     * @param titulo el título a establecer
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el nivel de dificultad del problema.
     * @return la dificultad del problema
     */
    public int getDificultad() {
        return dificultad;
    }

    /**
     * Establece el nivel de dificultad del problema.
     * @param dificultad la dificultad a establecer
     */
    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    /**
     * Obtiene el tema principal del problema.
     * @return el tema del problema
     */
    public String getTema() {
        return tema;
    }

    /**
     * Establece el tema principal del problema.
     * @param tema el tema a establecer
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * Obtiene el juez asociado al problema.
     * @return el juez del problema
     */
    public String getJuez() {
        return juez;
    }

    /**
     * Establece el juez asociado al problema.
     * @param juez el juez a establecer
     */
    public void setJuez(String juez) {
        this.juez = juez;
    }

    /**
     * Obtiene el enlace al problema.
     * @return el enlace del problema
     */
    public String getLink() {
        return link;
    }

    /**
     * Establece el enlace al problema.
     * @param link el enlace a establecer
     */
    public void setLink(String link) {
        this.link = link;
    }
}
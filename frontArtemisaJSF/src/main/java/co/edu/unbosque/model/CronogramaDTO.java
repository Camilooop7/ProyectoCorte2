package co.edu.unbosque.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa un cronograma con información relevante como nombre, enlace y fecha.
 * Esta clase se utiliza para transferir datos relacionados con cronogramas entre capas del sistema.
 */
public class CronogramaDTO {

    /** Identificador único del cronograma. */
    private Long id;

    /** Nombre del cronograma. */
    public String nombre;

    /** Enlace relacionado al cronograma. */
    public String link;

    /** Fecha del cronograma. */
    private LocalDate fecha;

    /**
     * Constructor vacío para la creación de instancias por defecto.
     */
    public CronogramaDTO() {
        // Constructor por defecto.
    }

    /**
     * Constructor que inicializa los campos principales del cronograma.
     * 
     * @param nombre nombre del cronograma
     * @param link enlace relacionado al cronograma
     * @param fecha fecha del cronograma
     */
    public CronogramaDTO(String nombre, String link, LocalDate fecha) {
        super();
        this.nombre = nombre;
        this.link = link;
        this.fecha = fecha;
    }

    /**
     * Obtiene el identificador único del cronograma.
     * @return el id del cronograma
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del cronograma.
     * @param id el id a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cronograma.
     * @return el nombre del cronograma
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cronograma.
     * @param nombre el nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el enlace relacionado al cronograma.
     * @return el enlace del cronograma
     */
    public String getLink() {
        return link;
    }

    /**
     * Establece el enlace relacionado al cronograma.
     * @param link el enlace a establecer
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Obtiene la fecha del cronograma.
     * @return la fecha del cronograma
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del cronograma.
     * @param fecha la fecha a establecer
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve una representación en cadena del objeto CronogramaDTO.
     * @return una cadena con la información del cronograma
     */
    @Override
    public String toString() {
        String f = fecha != null ? new SimpleDateFormat("yyyy-MM-dd").format(fecha) : "";
        return "Cronograma [id=" + id + ", nombre=" + nombre + ", link=" + link + ", fecha=" + f + "]";
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
        CronogramaDTO other = (CronogramaDTO) obj;
        return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(link, other.link)
                && Objects.equals(nombre, other.nombre);
    }

}
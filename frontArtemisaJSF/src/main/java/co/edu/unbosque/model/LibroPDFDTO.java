package co.edu.unbosque.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representa un libro en formato PDF con sus datos principales y el contenido binario.
 * Utilizado para transferir información sobre libros PDF entre diferentes capas del sistema.
 */
public class LibroPDFDTO {
    /** Código identificador del libro. */
    private int codigo;

    /** Identificador único del libro. */
    private Long id;

    /** Nombre del libro. */
    private String nombre;

    /** Descripción del libro. */
    private String descripcion;

    /** Contenido binario del archivo PDF. */
    private byte[] contenidoPdf;

    /**
     * Constructor vacío para la creación de instancias por defecto.
     */
    public LibroPDFDTO() {
    }

    /**
     * Constructor que inicializa todos los campos del libro PDF.
     * 
     * @param codigo código identificador del libro
     * @param id identificador único del libro
     * @param nombre nombre del libro
     * @param descripcion descripción del libro
     * @param contenidoPdf contenido binario del archivo PDF
     */
    public LibroPDFDTO(int codigo, Long id, String nombre, String descripcion, byte[] contenidoPdf) {
        super();
        this.codigo = codigo;
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contenidoPdf = contenidoPdf;
    }

    /**
     * Obtiene el código identificador del libro.
     * @return el código del libro
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código identificador del libro.
     * @param codigo el código a establecer
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el identificador único del libro.
     * @return el id del libro
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del libro.
     * @param id el id a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del libro.
     * @return el nombre del libro
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del libro.
     * @param nombre el nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del libro.
     * @return la descripción del libro
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del libro.
     * @param descripcion la descripción a establecer
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el contenido binario del archivo PDF.
     * @return el contenido PDF como arreglo de bytes
     */
    public byte[] getContenidoPdf() {
        return contenidoPdf;
    }

    /**
     * Establece el contenido binario del archivo PDF.
     * @param contenidoPdf el contenido PDF como arreglo de bytes
     */
    public void setContenidoPdf(byte[] contenidoPdf) {
        this.contenidoPdf = contenidoPdf;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(contenidoPdf);
        result = prime * result + Objects.hash(codigo, descripcion, id, nombre);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LibroPDFDTO other = (LibroPDFDTO) obj;
        return codigo == other.codigo && Arrays.equals(contenidoPdf, other.contenidoPdf)
                && Objects.equals(descripcion, other.descripcion) && Objects.equals(id, other.id)
                && Objects.equals(nombre, other.nombre);
    }

    /**
     * Devuelve una representación en cadena del objeto LibroPDFDTO.
     * @return una cadena con la información del libro PDF
     */
    @Override
    public String toString() {
        return "LibroPDFDTO [codigo=" + codigo + ", id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", contenidoPdf=" + Arrays.toString(contenidoPdf) + "]";
    }

}
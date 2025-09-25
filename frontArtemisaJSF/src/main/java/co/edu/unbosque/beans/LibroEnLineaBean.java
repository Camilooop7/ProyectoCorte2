package co.edu.unbosque.beans;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.json.JSONObject;
import org.primefaces.PrimeFaces;

import co.edu.unbosque.service.LibroEnLineaService;

/**
 * Bean para gestionar operaciones de libros en línea:
 * creación, consulta y eliminación.
 * Utiliza PrimeFaces y JSF para la interacción en la vista.
 */
@Named("libroEnLineaBean")
@RequestScoped
public class LibroEnLineaBean {

    /** Código único del libro */
    private Integer codigo;
    /** Nombre/título del libro */
    private String nombre;
    /** Descripción del libro */
    private String descripcion;
    /** Enlace en línea del libro */
    private String link;

    /**
     * Crea un libro en línea usando los datos actuales del formulario.
     * Valida los campos y muestra mensajes de éxito o error.
     */
    public void crearLibro() {
        try {
            if (codigo == null || codigo <= 0 || isBlank(nombre) || isBlank(descripcion) || isBlank(link)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Todos los campos son obligatorios."));
                return;
            }

            String url = "http://localhost:8081/libroenlinea/crear";
            String respuesta = LibroEnLineaService.doPostLibroEnLinea(url, codigo, nombre, descripcion, link);

            if (respuesta.startsWith("201") || respuesta.startsWith("200")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro en línea creado con éxito."));
                limpiarFormulario();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", "Error al crear el libro: " + respuesta));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error inesperado: " + e.getMessage()));
        }
    }

    /**
     * Consulta la información del libro dado el código y abre el enlace en una nueva pestaña si existe.
     */
    public void obtenerInformacionLibro() {
        try {
            if (codigo == null || codigo <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
                return;
            }

            String respuesta = LibroEnLineaService.getLibroInfoById(codigo);
            String body = respuesta == null ? "" : respuesta.trim();

            if (body.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(body);
                this.nombre = jsonObject.optString("nombre", "Nombre no disponible");
                this.descripcion = jsonObject.optString("descripcion", "Descripción no disponible");
                this.link = jsonObject.optString("link", "");

                if (!this.link.isEmpty()) {
                    PrimeFaces.current().executeScript("window.open('" + this.link + "', '_blank');");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Advertencia", "El libro no tiene un enlace válido."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Advertencia", "El libro con código " + codigo + " no existe."));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "No se pudo obtener la información del libro: " + e.getMessage()));
        }
    }

    /**
     * Elimina un libro en línea por su código y limpia el formulario si fue exitoso.
     */
    public void eliminarLibro() {
        try {
            if (codigo == null || codigo <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
                return;
            }
            String url = "http://localhost:8081/libroenlinea/eliminar";
            String respuesta = LibroEnLineaService.doDeleteLibroPDF(url, codigo);
            if (respuesta.startsWith("200")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro eliminado con éxito."));
                limpiarFormulario();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", "Error al eliminar el libro: " + respuesta));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error inesperado: " + e.getMessage()));
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarFormulario() {
        this.codigo = null;
        this.nombre = null;
        this.descripcion = null;
        this.link = null;
    }

    /**
     * Verifica si un string está vacío o es nulo.
     * @param s cadena a evaluar
     * @return true si está vacío o nulo, false en otro caso
     */
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // Getters y Setters

    /** @return el código del libro */
    public Integer getCodigo() {
        return codigo;
    }

    /** @param codigo el código a establecer */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /** @return el nombre del libro */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre el nombre a establecer */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return la descripción del libro */
    public String getDescripcion() {
        return descripcion;
    }

    /** @param descripcion la descripción a establecer */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return el enlace del libro */
    public String getLink() {
        return link;
    }

    /** @param link el enlace a establecer */
    public void setLink(String link) {
        this.link = link;
    }
}
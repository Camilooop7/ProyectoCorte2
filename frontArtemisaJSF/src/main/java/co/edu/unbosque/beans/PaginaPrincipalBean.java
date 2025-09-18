package co.edu.unbosque.beans;

import java.io.Serializable;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named(value = "paginaprincipalbean")
@RequestScoped
public class PaginaPrincipalBean implements Serializable {

	private int totalLibros;
	private String eventosProximos = "Taller de programación, Club de lectura, Charla de creatividad";
	private String temarioActual = "Algoritmos y Estructuras de Datos";

	public String irCatalogo() {
		return "libros?faces-redirect=true";
	}

	public String irEventos() {
		return "eventos?faces-redirect=true";
	}

	public String irTemario() {
		return "temario?faces-redirect=true";
	}

	public String irProblemas() {
		return "problemas?faces-redirect=true";
	}

	public String irLinks() {
		return "links?faces-redirect=true";
	}

	public String irInicio() {
		return "index?faces-redirect=true";
	}

	public int getTotalLibros() {
		return totalLibros;
	}

	public void setTotalLibros(int totalLibros) {
		this.totalLibros = totalLibros;
	}

	public String getEventosProximos() {
		return eventosProximos;
	}

	public void setEventosProximos(String eventosProximos) {
		this.eventosProximos = eventosProximos;
	}

	public String getTemarioActual() {
		return temarioActual;
	}

	public void setTemarioActual(String temarioActual) {
		this.temarioActual = temarioActual;
	}
}
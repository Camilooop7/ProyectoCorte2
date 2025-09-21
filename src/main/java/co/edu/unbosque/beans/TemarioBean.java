package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named(value = "paginaprincipalbean")
@RequestScoped
public class TemarioBean implements Serializable {

	private List<Tema> temas;
	private Tema temaSeleccionado;

	public TemarioBean() {
		temas = new ArrayList<>();
		temas.add(new Tema("Introducción a la Programación", "fa-solid fa-laptop-code",
				"Fundamentos esenciales para empezar a programar.",
				"<b>Detalle:</b> Variables, estructuras de control, etc."));
		temas.add(new Tema("Estructuras de Datos", "fa-solid fa-diagram-project",
				"Listas, pilas, colas, árboles, grafos...",
				"<b>Detalle:</b> Implementación, ejemplos en Java, Python, etc."));
		// Agrega más temas aquí...
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public Tema getTemaSeleccionado() {
		return temaSeleccionado;
	}

	public void setTemaSeleccionado(Tema temaSeleccionado) {
		this.temaSeleccionado = temaSeleccionado;
	}

	// Método para seleccionar el tema
	public void seleccionarTema(Tema tema) {
		this.temaSeleccionado = tema;
	}

	public static class Tema implements Serializable {
		private String nombre;
		private String icono;
		private String descripcion;
		private String detalle;

		public Tema(String nombre, String icono, String descripcion, String detalle) {
			this.nombre = nombre;
			this.icono = icono;
			this.descripcion = descripcion;
			this.detalle = detalle;
		}

		public String getNombre() {
			return nombre;
		}

		public String getIcono() {
			return icono;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public String getDetalle() {
			return detalle;
		}
	}
}

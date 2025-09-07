package co.edu.unbosque.proyectocorte2.enty;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "persona")
public abstract class Persona {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(unique = true, name = "nombre")
	private String nombre;
	private int documento;
	@Column(unique = true, name = "correo")
	private String correo;
	private String contrasena;
	private String rol;
	
	public Persona() {
		// TODO Auto-generated constructor stub
	}

	public Persona(String nombre, int documento, String correo, String contrasena, String rol) {
		super();
		this.nombre = nombre;
		this.documento = documento;
		this.correo = correo;
		this.contrasena = contrasena;
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDocumento() {
		return documento;
	}

	public void setDocumento(int documento) {
		this.documento = documento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", edad=" + documento + ", correo=" + correo + ", contrasena="
				+ contrasena + ", rol=" + rol + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, correo, documento, id, nombre, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(correo, other.correo)
				&& documento == other.documento && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(rol, other.rol);
	}

	
	
}

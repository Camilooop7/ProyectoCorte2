package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.Estudiante;

/**
 * Repositorio para la entidad {@link Estudiante}. Proporciona métodos para
 * realizar operaciones CRUD y consultas personalizadas sobre estudiantes.
 */
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

	/**
	 * Busca un estudiante por su nombre.
	 *
	 * @param nombre Nombre del estudiante a buscar.
	 * @return Un {@link Optional} que contiene el estudiante si existe.
	 */
	public Optional<Estudiante> findByNombre(String nombre);

	/**
	 * Elimina un estudiante por su nombre.
	 *
	 * @param nombre Nombre del estudiante a eliminar.
	 */
	public void deleteByNombre(String nombre);
}

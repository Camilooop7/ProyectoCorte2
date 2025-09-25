package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.Profesor;

/**
 * Repositorio para la entidad {@link Profesor}. Proporciona métodos para
 * realizar operaciones CRUD y consultas personalizadas sobre profesores.
 */
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

	/**
	 * Busca un profesor por su nombre.
	 *
	 * @param nombre Nombre del profesor a buscar.
	 * @return Un {@link Optional} que contiene el profesor si existe.
	 */
	public Optional<Profesor> findByNombre(String nombre);

	/**
	 * Elimina un profesor por su nombre.
	 *
	 * @param nombre Nombre del profesor a eliminar.
	 */
	public void deleteByNombre(String nombre);
}

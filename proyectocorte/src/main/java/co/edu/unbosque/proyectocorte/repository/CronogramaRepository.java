package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.Cronograma;

/**
 * Repositorio para la entidad {@link Cronograma}. Proporciona métodos para
 * realizar operaciones CRUD y consultas personalizadas sobre cronogramas.
 */
public interface CronogramaRepository extends JpaRepository<Cronograma, Long> {

	/**
	 * Busca un cronograma por su nombre.
	 *
	 * @param nombre Nombre del cronograma a buscar.
	 * @return Un {@link Optional} que contiene el cronograma si existe.
	 */
	public Optional<Cronograma> findByNombre(String nombre);

	/**
	 * Elimina un cronograma por su nombre.
	 *
	 * @param nombre Nombre del cronograma a eliminar.
	 */
	public void deleteByNombre(String nombre);
}

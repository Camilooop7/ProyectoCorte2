package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.Problema;

/**
 * Repositorio para la entidad {@link Problema}. Proporciona métodos para
 * realizar operaciones CRUD y consultas personalizadas sobre problemas.
 */
public interface ProblemaRepository extends JpaRepository<Problema, Long> {

	/**
	 * Busca un problema por su título.
	 *
	 * @param titulo Título del problema a buscar.
	 * @return Un {@link Optional} que contiene el problema si existe.
	 */
	public Optional<Problema> findByTitulo(String titulo);

	/**
	 * Elimina un problema por su título.
	 *
	 * @param titulo Título del problema a eliminar.
	 */
	public void deleteByTitulo(String titulo);
}

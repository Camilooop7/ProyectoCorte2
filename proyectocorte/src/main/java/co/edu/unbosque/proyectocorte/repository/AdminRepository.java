package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.Admin;

/**
 * Repositorio para la entidad {@link Admin}. Proporciona métodos para realizar
 * operaciones CRUD y consultas personalizadas sobre administradores.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

	/**
	 * Busca un administrador por su nombre.
	 *
	 * @param nombre Nombre del administrador a buscar.
	 * @return Un {@link Optional} que contiene el administrador si existe.
	 */
	public Optional<Admin> findByNombre(String nombre);

	/**
	 * Elimina un administrador por su nombre.
	 *
	 * @param nombre Nombre del administrador a eliminar.
	 */
	public void deleteByNombre(String nombre);
}

package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.Persona;

/**
 * Repositorio para la entidad {@link Persona}, utilizado específicamente para
 * operaciones de inicio de sesión. Proporciona métodos para realizar consultas
 * personalizadas sobre personas, enfocado en el correo electrónico.
 */
public interface LoginRepository extends JpaRepository<Persona, Long> {

	/**
	 * Busca una persona por su correo electrónico.
	 *
	 * @param email Correo electrónico de la persona a buscar.
	 * @return Un {@link Optional} que contiene la persona si existe.
	 */
	Optional<Persona> findByCorreo(String email);
}

package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.Persona;

/**
 * Repositorio para la entidad {@link Persona}. Proporciona métodos para
 * realizar operaciones CRUD y consultas personalizadas sobre personas.
 */
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	/**
	 * Busca una persona por su correo electrónico.
	 *
	 * @param email Correo electrónico de la persona a buscar.
	 * @return Un {@link Optional} que contiene la persona si existe.
	 */
	public Optional<Persona> findByCorreo(String email);
}

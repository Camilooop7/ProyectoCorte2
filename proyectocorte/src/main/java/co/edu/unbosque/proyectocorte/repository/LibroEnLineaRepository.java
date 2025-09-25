package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.LibroEnLinea;

/**
 * Repositorio para la entidad {@link LibroEnLinea}. Proporciona métodos para
 * realizar operaciones CRUD y consultas personalizadas sobre libros en línea.
 */
public interface LibroEnLineaRepository extends JpaRepository<LibroEnLinea, Long> {

	/**
	 * Busca un libro en línea por su nombre.
	 *
	 * @param nombre Nombre del libro en línea a buscar.
	 * @return Un {@link Optional} que contiene el libro en línea si existe.
	 */
	public Optional<LibroEnLinea> findByNombre(String nombre);

	/**
	 * Busca un libro en línea por su código.
	 *
	 * @param codigo Código del libro en línea a buscar.
	 * @return Un {@link Optional} que contiene el libro en línea si existe.
	 */
	public Optional<LibroEnLinea> findByCodigo(int codigo);

	/**
	 * Elimina un libro en línea por su código.
	 *
	 * @param codigo Código del libro en línea a eliminar.
	 */
	public void deleteByCodigo(int codigo);

	/**
	 * Verifica si existe un libro en línea con el código especificado.
	 *
	 * @param codigo Código del libro en línea a verificar.
	 * @return {@code true} si existe un libro en línea con el código especificado,
	 *         {@code false} en caso contrario.
	 */
	public boolean existsByCodigo(int codigo);
}

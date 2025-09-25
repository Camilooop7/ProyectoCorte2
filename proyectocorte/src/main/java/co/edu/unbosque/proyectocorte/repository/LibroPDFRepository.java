package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectocorte.entity.LibroPDF;

/**
 * Repositorio para la entidad {@link LibroPDF}. Proporciona métodos para
 * realizar operaciones CRUD y consultas personalizadas sobre libros en formato
 * PDF.
 */
public interface LibroPDFRepository extends JpaRepository<LibroPDF, Long> {

	/**
	 * Busca un libro PDF por su nombre.
	 *
	 * @param nombre Nombre del libro PDF a buscar.
	 * @return Un {@link Optional} que contiene el libro PDF si existe.
	 */
	public Optional<LibroPDF> findByNombre(String nombre);

	/**
	 * Busca un libro PDF por su código.
	 *
	 * @param codigo Código del libro PDF a buscar.
	 * @return Un {@link Optional} que contiene el libro PDF si existe.
	 */
	public Optional<LibroPDF> findByCodigo(int codigo);

	/**
	 * Elimina un libro PDF por su código.
	 *
	 * @param codigo Código del libro PDF a eliminar.
	 */
	public void deleteByCodigo(int codigo);

	/**
	 * Verifica si existe un libro PDF con el código especificado.
	 *
	 * @param codigo Código del libro PDF a verificar.
	 * @return {@code true} si existe un libro PDF con el código especificado,
	 *         {@code false} en caso contrario.
	 */
	public boolean existsByCodigo(int codigo);
}

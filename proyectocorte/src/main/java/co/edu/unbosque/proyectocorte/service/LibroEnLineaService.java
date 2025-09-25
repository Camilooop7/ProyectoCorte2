package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.unbosque.proyectocorte.dto.LibroEnLineaDTO;
import co.edu.unbosque.proyectocorte.entity.LibroEnLinea;
import co.edu.unbosque.proyectocorte.repository.LibroEnLineaRepository;

/**
 * Servicio para gestionar las operaciones CRUD de la entidad
 * {@link LibroEnLinea}.
 */
@Service
public class LibroEnLineaService implements CRUDOperation<LibroEnLineaDTO> {

	@Autowired
	private LibroEnLineaRepository libroEnLineaRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo libro en línea.
	 *
	 * @param date DTO que contiene los datos del libro en línea a crear.
	 * @return 0 si la creación fue exitosa.
	 */
	@Override
	public int create(LibroEnLineaDTO date) {
		LibroEnLinea entity = modelMapper.map(date, LibroEnLinea.class);
		libroEnLineaRepo.save(entity);
		return 0;
	}

	/**
	 * Obtiene todos los libros en línea.
	 *
	 * @return Lista de DTOs de libros en línea.
	 */
	@Override
	public List<LibroEnLineaDTO> getAll() {
		List<LibroEnLinea> entityList = libroEnLineaRepo.findAll();
		List<LibroEnLineaDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			LibroEnLineaDTO libroEnLineaDTO = modelMapper.map(entity, LibroEnLineaDTO.class);
			dtoList.add(libroEnLineaDTO);
		});
		return dtoList;
	}

	/**
	 * Elimina un libro en línea por su ID.
	 *
	 * @param a ID del libro en línea a eliminar.
	 * @return 0 (método no implementado).
	 */
	@Override
	public int deleteById(Long a) {
		return 0;
	}

	/**
	 * Actualiza un libro en línea por su ID.
	 *
	 * @param a    ID del libro en línea a actualizar.
	 * @param date DTO con los nuevos datos del libro en línea.
	 * @return 0 (método no implementado).
	 */
	@Override
	public int updateById(Long a, LibroEnLineaDTO date) {
		return 0;
	}

	/**
	 * Cuenta el número total de libros en línea.
	 *
	 * @return Número total de libros en línea.
	 */
	@Override
	public Long count() {
		return libroEnLineaRepo.count();
	}

	/**
	 * Verifica si existe un libro en línea con el ID proporcionado.
	 *
	 * @param a ID del libro en línea a verificar.
	 * @return {@code false} (método no implementado).
	 */
	@Override
	public boolean exist(Long a) {
		return false;
	}

	/**
	 * Elimina un libro en línea por su código.
	 *
	 * @param codigo Código del libro en línea a eliminar.
	 * @return 1 si la eliminación fue exitosa, 0 en caso de error.
	 */
	@Transactional
	public int deleteByCodigo(int codigo) {
		if (libroEnLineaRepo.existsByCodigo(codigo)) {
			libroEnLineaRepo.deleteByCodigo(codigo);
			return 1;
		}
		return 0;
	}

	/**
	 * Verifica si existe un libro en línea con el código proporcionado.
	 *
	 * @param codigo Código del libro en línea a verificar.
	 * @return {@code true} si el libro en línea existe, {@code false} en caso
	 *         contrario.
	 */
	public boolean exist(int codigo) {
		return libroEnLineaRepo.existsByCodigo(codigo);
	}

	/**
	 * Obtiene un libro en línea por su código.
	 *
	 * @param codigo Código del libro en línea a buscar.
	 * @return DTO del libro en línea encontrado.
	 * @throws RuntimeException Si no se encuentra el libro en línea.
	 */
	public LibroEnLineaDTO getLibroByCodigo(int codigo) {
		LibroEnLinea entity = libroEnLineaRepo.findByCodigo(codigo)
				.orElseThrow(() -> new RuntimeException("Libro con ID " + codigo + " no encontrado"));
		return modelMapper.map(entity, LibroEnLineaDTO.class);
	}
}

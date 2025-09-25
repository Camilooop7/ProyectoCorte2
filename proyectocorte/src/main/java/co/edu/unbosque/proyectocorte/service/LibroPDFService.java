package co.edu.unbosque.proyectocorte.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.entity.LibroPDF;
import co.edu.unbosque.proyectocorte.repository.LibroPDFRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar las operaciones de la entidad {@link LibroPDF}.
 */
@Service
public class LibroPDFService {

	@Autowired
	private LibroPDFRepository libroPDFRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo libro PDF.
	 *
	 * @param codigo      Código del libro PDF.
	 * @param nombre      Nombre del libro PDF.
	 * @param descripcion Descripción del libro PDF.
	 * @param archivoPdf  Archivo PDF a guardar.
	 * @return 1 si la creación fue exitosa.
	 * @throws IOException Si ocurre un error al leer el archivo PDF.
	 */
	public int create(int codigo, String nombre, String descripcion, MultipartFile archivoPdf) throws IOException {
		LibroPDF entity = new LibroPDF(codigo, nombre, descripcion, archivoPdf.getBytes());
		libroPDFRepo.save(entity);
		return 1;
	}

	/**
	 * Obtiene un libro PDF por su código.
	 *
	 * @param codigo Código del libro PDF a buscar.
	 * @return DTO del libro PDF encontrado.
	 * @throws RuntimeException Si no se encuentra el libro PDF.
	 */
	public LibroPDFDTO getLibroByCodigo(int codigo) {
		LibroPDF entity = libroPDFRepo.findByCodigo(codigo)
				.orElseThrow(() -> new RuntimeException("Libro con ID " + codigo + " no encontrado"));
		return modelMapper.map(entity, LibroPDFDTO.class);
	}

	/**
	 * Obtiene todos los libros PDF.
	 *
	 * @return Lista de DTOs de libros PDF.
	 */
	public List<LibroPDFDTO> getAll() {
		List<LibroPDF> entityList = libroPDFRepo.findAll();
		List<LibroPDFDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> {
			LibroPDFDTO libroPDFDTO = modelMapper.map(entity, LibroPDFDTO.class);
			dtoList.add(libroPDFDTO);
		});
		return dtoList;
	}

	/**
	 * Obtiene el contenido de un libro PDF por su código.
	 *
	 * @param id Código del libro PDF.
	 * @return Contenido del PDF en bytes.
	 * @throws RuntimeException Si no se encuentra el libro PDF.
	 */
	public byte[] getPdfContentByCodigo(int id) {
		return libroPDFRepo.findByCodigo(id)
				.orElseThrow(() -> new RuntimeException("Libro con ID " + id + " no encontrado")).getContenidoPdf();
	}

	/**
	 * Elimina un libro PDF por su código.
	 *
	 * @param codigo Código del libro PDF a eliminar.
	 * @return 1 si la eliminación fue exitosa, 0 en caso de error.
	 */
	@Transactional
	public int deleteByCodigo(int codigo) {
		if (libroPDFRepo.existsByCodigo(codigo)) {
			libroPDFRepo.deleteByCodigo(codigo);
			return 1;
		}
		return 0;
	}

	/**
	 * Cuenta el número total de libros PDF.
	 *
	 * @return Número total de libros PDF.
	 */
	public Long count() {
		return libroPDFRepo.count();
	}

	/**
	 * Verifica si existe un libro PDF con el código proporcionado.
	 *
	 * @param codigo Código del libro PDF a verificar.
	 * @return {@code true} si el libro PDF existe, {@code false} en caso contrario.
	 */
	public boolean exist(int codigo) {
		return libroPDFRepo.existsByCodigo(codigo);
	}
}

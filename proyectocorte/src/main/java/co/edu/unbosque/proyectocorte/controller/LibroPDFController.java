package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.service.LibroPDFService;
import java.io.IOException;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con libros en
 * formato PDF.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libropdf" })
public class LibroPDFController {

	@Autowired
	private LibroPDFService libroPDFService;

	/**
	 * Obtiene la lista de todos los libros PDF.
	 *
	 * @return ResponseEntity con la lista de libros PDF.
	 */
	@GetMapping
	public ResponseEntity<List<LibroPDFDTO>> getAllLibros() {
		List<LibroPDFDTO> libros = libroPDFService.getAll();
		return new ResponseEntity<>(libros, HttpStatus.OK);
	}

	/**
	 * Crea un nuevo libro PDF.
	 *
	 * @param codigo      Código del libro.
	 * @param nombre      Nombre del libro.
	 * @param descripcion Descripción del libro.
	 * @param archivoPdf  Archivo PDF del libro.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 * @throws IOException Si ocurre un error al procesar el archivo PDF.
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam("codigo") int codigo, @RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion, @RequestParam("archivoPdf") MultipartFile archivoPdf) {
		try {
			int status = libroPDFService.create(codigo, nombre, descripcion, archivoPdf);
			if (status == 1) {
				return new ResponseEntity<>("Libro PDF creado con éxito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al crear el libro PDF", HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (IOException e) {
			return new ResponseEntity<>("Error al procesar los archivos", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Descarga el contenido de un libro PDF por su código.
	 *
	 * @param codigo Código del libro.
	 * @return ResponseEntity con el contenido del PDF en bytes.
	 */
	@GetMapping(path = "/pdf/{codigo}")
	public ResponseEntity<byte[]> descargarPdf(@PathVariable int codigo) {
		byte[] pdfContent = libroPDFService.getPdfContentByCodigo(codigo);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"libro_" + codigo + ".pdf\"")
				.body(pdfContent);
	}

	/**
	 * Obtiene un libro PDF por su código.
	 *
	 * @param codigo Código del libro.
	 * @return ResponseEntity con el libro PDF encontrado.
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<LibroPDFDTO> getLibroById(@PathVariable int codigo) {
		LibroPDFDTO libro = libroPDFService.getLibroByCodigo(codigo);
		return ResponseEntity.ok(libro);
	}

	/**
	 * Elimina un libro PDF por su código.
	 *
	 * @param codigo Código del libro a eliminar.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@DeleteMapping("/eliminar/{codigo}")
	public ResponseEntity<String> eliminarLibroPorCodigo(@PathVariable int codigo) {
		int status = libroPDFService.deleteByCodigo(codigo);
		if (status == 1) {
			return new ResponseEntity<>("Libro PDF eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Libro PDF no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}

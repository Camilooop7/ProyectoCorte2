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

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libropdf" })
public class LibroPDFController {

	@Autowired
	private LibroPDFService libroPDFService;

	@GetMapping
	public ResponseEntity<List<LibroPDFDTO>> getAllLibros() {
		List<LibroPDFDTO> libros = libroPDFService.getAll();
		return new ResponseEntity<>(libros, HttpStatus.OK);
	}

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

	@GetMapping(path = "/pdf/{codigo}")
	public ResponseEntity<byte[]> descargarPdf(@PathVariable int codigo) {
		byte[] pdfContent = libroPDFService.getPdfContentByCodigo(codigo);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"libro_" + codigo + ".pdf\"")
				.body(pdfContent);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<LibroPDFDTO> getLibroById(@PathVariable int codigo) {
		LibroPDFDTO libro = libroPDFService.getLibroByCodigo(codigo);
		return ResponseEntity.ok(libro);
	}
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

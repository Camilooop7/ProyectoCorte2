package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.service.LibroPDFService;

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

	@GetMapping("/imagen/{id}")
	public ResponseEntity<byte[]> getImagen(@PathVariable Long id) {
		byte[] imagen = libroPDFService.getImagenById(id);
		return ResponseEntity.ok().header("Content-Type", "image/jpeg") // Asegúrate de que el tipo de contenido sea
																		// correcto
				.header("Content-Disposition", "inline; filename=\"imagen_" + id + ".jpg\"") // Cambia "attachment" a
																								// "inline"
				.body(imagen);
	}

	@GetMapping("/pdf/{id}")
	public ResponseEntity<byte[]> getPdf(@PathVariable Long id) {
		byte[] pdf = libroPDFService.getPdfContentById(id);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"libro_" + id + ".pdf\"")
				.body(pdf);
	}
	@GetMapping("/{id}")
	public ResponseEntity<LibroPDFDTO> getLibroById(@PathVariable Long id) {
	    LibroPDFDTO libro = libroPDFService.getLibroById(id);
	    return ResponseEntity.ok(libro);
	}

}

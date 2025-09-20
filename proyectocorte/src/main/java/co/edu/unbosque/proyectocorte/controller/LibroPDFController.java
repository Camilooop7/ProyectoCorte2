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
			@RequestParam("descripcion") String descripcion, @RequestParam("imagen") MultipartFile imagen,
			@RequestParam("archivoPdf") MultipartFile archivoPdf) {
		try {
			int status = libroPDFService.create(codigo, nombre, descripcion, imagen, archivoPdf);
			if (status == 1) {
				return new ResponseEntity<>("Libro PDF creado con éxito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al crear el libro PDF", HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (IOException e) {
			return new ResponseEntity<>("Error al procesar los archivos", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/imagen/{id}")
	public ResponseEntity<byte[]> getImagen(@PathVariable int id) {
		byte[] imagen = libroPDFService.getImagenByCodigo(id);
		return ResponseEntity.ok().header("Content-Type", "image/jpeg")
				.header("Content-Disposition", "inline; filename=\"imagen_" + id + ".jpg\"").body(imagen);
	}

	@GetMapping("/pdf/{id}")
	public ResponseEntity<byte[]> getPdf(@PathVariable int id) {
		byte[] pdf = libroPDFService.getPdfContentByCodigo(id);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"libro_" + id + ".pdf\"")
				.body(pdf);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LibroPDFDTO> getLibroById(@PathVariable int id) {
		LibroPDFDTO libro = libroPDFService.getLibroByCodigo(id);
		return ResponseEntity.ok(libro);
	}
}

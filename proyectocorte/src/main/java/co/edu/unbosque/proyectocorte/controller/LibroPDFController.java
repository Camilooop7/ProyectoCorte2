package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.service.LibroPDFService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libro/pdf" })
public class LibroPDFController {

	@Autowired
	private LibroPDFService libroPDFSer;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion, @RequestParam("imagen") MultipartFile imagen,
			@RequestParam("archivoPdf") MultipartFile archivoPdf) {
		try {
			int status = libroPDFSer.create(nombre, descripcion, imagen, archivoPdf);
			if (status == 1) {
				return new ResponseEntity<>("Libro PDF creado con éxito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al crear el libro PDF", HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (IOException e) {
			return new ResponseEntity<>("Error al procesar los archivos", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/descargar-imagen/{id}")
	public ResponseEntity<byte[]> descargarImagen(@PathVariable Long id) {
		byte[] imagen = libroPDFSer.getImagenById(id);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"imagen_" + id + ".jpg\"")
				.body(imagen);
	}

	@GetMapping(path = "/descargar-pdf/{id}")
	public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
		byte[] pdfContent = libroPDFSer.getPdfContentById(id);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"libro_" + id + ".pdf\"")
				.body(pdfContent);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<LibroPDFDTO>> getAll() {
	    return ResponseEntity.ok(libroPDFSer.getAll());
	}
}

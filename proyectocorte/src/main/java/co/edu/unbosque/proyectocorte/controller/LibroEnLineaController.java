package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import co.edu.unbosque.proyectocorte.dto.LibroEnLineaDTO;
import co.edu.unbosque.proyectocorte.service.LibroEnLineaService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libro/linea" })
public class LibroEnLineaController {

	@Autowired
	private LibroEnLineaService libroEnLineaSer;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam("codigo") int codigo,@RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion, @RequestParam("imagen") MultipartFile imagen,
			@RequestParam("link") String link) {
		try {
			LibroEnLineaDTO nuevo = new LibroEnLineaDTO(codigo,nombre, descripcion, imagen.getBytes(), link);
			int status = libroEnLineaSer.create(nuevo);
			if (status == 0) {
				return new ResponseEntity<>("Libro en línea creado con éxito", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al crear el libro en línea", HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (IOException e) {
			return new ResponseEntity<>("Error al procesar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

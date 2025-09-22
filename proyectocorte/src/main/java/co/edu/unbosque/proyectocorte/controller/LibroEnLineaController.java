package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.proyectocorte.dto.LibroEnLineaDTO;
import co.edu.unbosque.proyectocorte.service.LibroEnLineaService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libroenlinea" })
public class LibroEnLineaController {

	@Autowired
	private LibroEnLineaService libroEnLineaSer;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam("codigo") int codigo, @RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion, @RequestParam("link") String link) {
		LibroEnLineaDTO nuevo = new LibroEnLineaDTO(codigo, nombre, descripcion, link);
		int status = libroEnLineaSer.create(nuevo);
		if (status == 0) {
			return new ResponseEntity<>("Libro en línea creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el libro en línea", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<LibroEnLineaDTO> getLibroById(@PathVariable int codigo) {
		LibroEnLineaDTO libro = libroEnLineaSer.getLibroByCodigo(codigo);
		return ResponseEntity.ok(libro);
	}

	@DeleteMapping("/eliminar/{codigo}")
	public ResponseEntity<String> eliminarLibroPorCodigo(@PathVariable int codigo) {
		int status = libroEnLineaSer.deleteByCodigo(codigo);
		if (status == 1) {
			return new ResponseEntity<>("Libro PDF eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Libro PDF no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}

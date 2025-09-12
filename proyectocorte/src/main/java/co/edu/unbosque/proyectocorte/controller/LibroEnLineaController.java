package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocorte.dto.LibroEnLineaDTO;
import co.edu.unbosque.proyectocorte.service.LibroEnLineaService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libro/linea" })

public class LibroEnLineaController {

	@Autowired
	private LibroEnLineaService libroEnLineaSer;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String nombre, String descripcion, String imagen, String link) {
		LibroEnLineaDTO nuevo = new LibroEnLineaDTO(nombre, descripcion, imagen, link);
		int status = libroEnLineaSer.create(nuevo);

		if (status == 0) {
			return new ResponseEntity<>("Libro en linea creado con exito", HttpStatus.CREATED);
		} else {

			return new ResponseEntity<>("Error al crear el libro en linea", HttpStatus.NOT_ACCEPTABLE);
		}

	}
}

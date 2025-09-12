package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.service.LibroPDFService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libro/pdf" })

public class LibroPDFController {

	@Autowired
	private LibroPDFService libroPDFSer;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String nombre, String descripcion, String imagen,
			String nombrePdf) {
		LibroPDFDTO nuevo = new LibroPDFDTO(nombre, descripcion, imagen, nombrePdf);
		int status = libroPDFSer.create(nuevo);

		if (status == 0) {
			return new ResponseEntity<>("Libro PDF creado con exito", HttpStatus.CREATED);
		} else {

			return new ResponseEntity<>("Error al crear el libro PDF", HttpStatus.NOT_ACCEPTABLE);
		}

	}
}

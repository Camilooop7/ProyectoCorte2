package co.edu.unbosque.proyectocorte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocorte.dto.ProblemaDTO;
import co.edu.unbosque.proyectocorte.service.ProblemaService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/problema" })
public class ProblemaController {

	@Autowired
	private ProblemaService problemaService;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String titulo, @RequestParam int dificultad,
			@RequestParam String tema, @RequestParam String juez, @RequestParam String link) {
		ProblemaDTO newProblema = new ProblemaDTO(titulo, dificultad, tema, juez, link);
		int status = problemaService.create(newProblema);

		if (status == 0) {
			return new ResponseEntity<>("problema creado con exito", HttpStatus.CREATED);
		} else {

			return new ResponseEntity<>("Error al crear el problema", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping(path = "/mostrar")
	public ResponseEntity<String> mostrarEstudiante() {
		List<ProblemaDTO> listaProblmea = problemaService.getAll();
		if (listaProblmea.isEmpty()) {
			return new ResponseEntity<>("No se encontraron problemas por mostrar", HttpStatus.valueOf(204));
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			listaProblmea.forEach((dto) -> stringBuilder.append(dto.toString() + "\n"));
			return new ResponseEntity<>("admin: " + stringBuilder.toString(), HttpStatus.valueOf(202));

		}
	}

	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Long id) {

		int status = problemaService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Problema eliminado con exito", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Error al elimiar el problema debino a que no exite", HttpStatus.valueOf(400));
		}

	}

}

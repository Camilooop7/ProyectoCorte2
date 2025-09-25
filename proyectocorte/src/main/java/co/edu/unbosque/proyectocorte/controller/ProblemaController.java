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

/**
 * Controlador REST para gestionar operaciones relacionadas con problemas.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/problema" })
public class ProblemaController {

	@Autowired
	private ProblemaService problemaService;

	/**
	 * Crea un nuevo problema.
	 *
	 * @param titulo     Título del problema.
	 * @param dificultad Dificultad del problema.
	 * @param tema       Tema del problema.
	 * @param juez       Juez del problema.
	 * @param link       Enlace del problema.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String titulo, @RequestParam int dificultad,
			@RequestParam String tema, @RequestParam String juez, @RequestParam String link) {
		ProblemaDTO newProblema = new ProblemaDTO(titulo, dificultad, tema, juez, link);
		int status = problemaService.create(newProblema);
		if (status == 0) {
			return new ResponseEntity<>("Problema creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el problema", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Muestra la lista de todos los problemas.
	 *
	 * @return ResponseEntity con la lista de problemas o un mensaje si no hay
	 *         problemas.
	 */
	@GetMapping(path = "/mostrar")
	public ResponseEntity<String> mostrarProblema() {
		List<ProblemaDTO> listaProblema = problemaService.getAll();
		if (listaProblema.isEmpty()) {
			return new ResponseEntity<>("No se encontraron problemas por mostrar", HttpStatus.NO_CONTENT);
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			listaProblema.forEach((dto) -> stringBuilder.append(dto.toString() + "\n"));
			return new ResponseEntity<>("Problemas: " + stringBuilder.toString(), HttpStatus.ACCEPTED);
		}
	}

	/**
	 * Elimina un problema por su ID.
	 *
	 * @param id ID del problema a eliminar.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Long id) {
		int status = problemaService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Problema eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar el problema debido a que no existe", HttpStatus.BAD_REQUEST);
		}
	}
}

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
import co.edu.unbosque.proyectocorte.dto.EstudianteDTO;
import co.edu.unbosque.proyectocorte.service.EstudianteService;

/**
 * Controlador REST para gestionar operaciones relacionadas con estudiantes.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/estudiante" })
public class EstudianteController {

	@Autowired
	private EstudianteService estudianteService;

	/**
	 * Crea un nuevo estudiante.
	 *
	 * @param nombre     Nombre del estudiante.
	 * @param documento  Documento de identidad del estudiante.
	 * @param correo     Correo electrónico del estudiante.
	 * @param contrasena Contraseña del estudiante.
	 * @param rol        Rol del estudiante.
	 * @param carrera    Carrera del estudiante.
	 * @param semestre   Semestre del estudiante.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String nombre, @RequestParam int documento,
			@RequestParam String correo, @RequestParam String contrasena, @RequestParam String rol,
			@RequestParam String carrera, @RequestParam int semestre) {
		EstudianteDTO newEstudiante = new EstudianteDTO(nombre, documento, correo, contrasena, rol, carrera, semestre);
		int status = estudianteService.create(newEstudiante);
		if (status == 0) {
			return new ResponseEntity<>("Estudiante creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el estudiante", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Muestra la lista de todos los estudiantes.
	 *
	 * @return ResponseEntity con la lista de estudiantes o un mensaje si no hay
	 *         estudiantes.
	 */
	@GetMapping(path = "/mostrar")
	public ResponseEntity<String> mostrarEstudiante() {
		List<EstudianteDTO> listaEstudiante = estudianteService.getAll();
		if (listaEstudiante.isEmpty()) {
			return new ResponseEntity<>("No se encontraron estudiantes por mostrar", HttpStatus.NO_CONTENT);
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			listaEstudiante.forEach((dto) -> stringBuilder.append(dto.toString() + "\n"));
			return new ResponseEntity<>("estudiante: " + stringBuilder.toString(), HttpStatus.ACCEPTED);
		}
	}

	/**
	 * Elimina un estudiante por su ID.
	 *
	 * @param id ID del estudiante a eliminar.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Long id) {
		int status = estudianteService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Estudiante eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar el estudiante debido a que no existe",
					HttpStatus.BAD_REQUEST);
		}
	}
}

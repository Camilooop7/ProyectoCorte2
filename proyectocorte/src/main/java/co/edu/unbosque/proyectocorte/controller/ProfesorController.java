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
import co.edu.unbosque.proyectocorte.dto.ProfesorDTO;
import co.edu.unbosque.proyectocorte.service.ProfesorService;

/**
 * Controlador REST para gestionar operaciones relacionadas con profesores.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/profesor" })
public class ProfesorController {

	@Autowired
	private ProfesorService profesorService;

	/**
	 * Crea un nuevo profesor.
	 *
	 * @param nombre       Nombre del profesor.
	 * @param documento    Documento de identidad del profesor.
	 * @param correo       Correo electrónico del profesor.
	 * @param contrasena   Contraseña del profesor.
	 * @param rol          Rol del profesor.
	 * @param departamento Departamento del profesor.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String nombre, @RequestParam int documento,
			@RequestParam String correo, @RequestParam String contrasena, @RequestParam String rol,
			@RequestParam String departamento) {
		ProfesorDTO newProfesor = new ProfesorDTO(nombre, documento, correo, contrasena, rol, departamento);
		int status = profesorService.create(newProfesor);
		if (status == 0) {
			return new ResponseEntity<>("Profesor creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el profesor", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Muestra la lista de todos los profesores.
	 *
	 * @return ResponseEntity con la lista de profesores o un mensaje si no hay
	 *         profesores.
	 */
	@GetMapping(path = "/mostrar")
	public ResponseEntity<String> mostrarProfesor() {
		List<ProfesorDTO> listaProfesor = profesorService.getAll();
		if (listaProfesor.isEmpty()) {
			return new ResponseEntity<>("No se encontraron profesores por mostrar", HttpStatus.NO_CONTENT);
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			listaProfesor.forEach((dto) -> stringBuilder.append(dto.toString() + "\n"));
			return new ResponseEntity<>("Profesores: " + stringBuilder.toString(), HttpStatus.ACCEPTED);
		}
	}

	/**
	 * Elimina un profesor por su ID.
	 *
	 * @param id ID del profesor a eliminar.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Long id) {
		int status = profesorService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Profesor eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar el profesor debido a que no existe", HttpStatus.BAD_REQUEST);
		}
	}
}

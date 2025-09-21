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

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/estudiante" })
public class EstudianteController {
	@Autowired
	private EstudianteService estudianteService;

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
	
	@GetMapping(path = "/mostrar")
	public ResponseEntity<String> mostrarEstudiante() {
		List<EstudianteDTO> listaEstudiante = estudianteService.getAll();
		if (listaEstudiante.isEmpty()) {
			return new ResponseEntity<>("No se encontraron profesores por mostrar", HttpStatus.valueOf(204));
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			listaEstudiante.forEach((dto) -> stringBuilder.append(dto.toString() + "\n"));
			return new ResponseEntity<>("estudiante: " + stringBuilder.toString(), HttpStatus.valueOf(202));

		}
	}

	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Long id) {

		int status = estudianteService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("profesor eliminado con exito", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Error al elimiar el profesor debino a que no exite", HttpStatus.valueOf(400));
		}

	}
}

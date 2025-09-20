package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}

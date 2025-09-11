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
	public ResponseEntity<String> crear(@RequestParam String nombre, int documento, String correo, String contrasena, String rol, String carrera,
			int semestre) {
		EstudianteDTO newEstudiante = new EstudianteDTO(nombre, documento, correo,contrasena,rol,carrera,semestre);
		int status = estudianteService.create(newEstudiante);
		
		if (status == 0) {
			return new ResponseEntity<>("Estudiante creado con exito", HttpStatus.CREATED);
		}else {
			
			return new ResponseEntity<>("Error al crear el Estudiante", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}

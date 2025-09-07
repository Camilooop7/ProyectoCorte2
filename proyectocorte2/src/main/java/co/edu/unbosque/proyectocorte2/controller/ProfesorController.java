package co.edu.unbosque.proyectocorte2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocorte2.dto.ProfesorDTO;
import co.edu.unbosque.proyectocorte2.service.ProfesorService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/profesor" })
public class ProfesorController {
	
	@Autowired
	private ProfesorService profesorService;
	
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String nombre, int documento, String correo, String contrasena, String rol, String departamento) {
		ProfesorDTO newProfesor = new ProfesorDTO(nombre, documento, correo,contrasena,rol,departamento);
		int status = profesorService.create(newProfesor);
		
		if (status == 0) {
			return new ResponseEntity<>("Profesor creado con exito", HttpStatus.CREATED);
		}else {
			
			return new ResponseEntity<>("Error al crear el profesor", HttpStatus.NOT_ACCEPTABLE);
		}
	}


}

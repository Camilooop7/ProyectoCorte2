package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocorte.dto.AdminDTO;
import co.edu.unbosque.proyectocorte.service.AdminService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/admin" })
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String nombre, int documento, String correo, String contrasena,  String rol, String cargo) {
		AdminDTO newAdmin = new AdminDTO(nombre, documento, correo,contrasena,rol,cargo);
		int status = adminService.create(newAdmin);
		
		if (status == 0) {
			return new ResponseEntity<>("Admin creado con exito", HttpStatus.CREATED);
		}else {
			
			return new ResponseEntity<>("Error al crear el admin", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	

}

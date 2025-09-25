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
import co.edu.unbosque.proyectocorte.dto.AdminDTO;
import co.edu.unbosque.proyectocorte.service.AdminService;

/**
 * Controlador REST para gestionar operaciones relacionadas con administradores.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/admin" })
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * Crea un nuevo administrador.
	 *
	 * @param nombre     Nombre del administrador.
	 * @param documento  Documento de identidad del administrador.
	 * @param correo     Correo electrónico del administrador.
	 * @param contrasena Contraseña del administrador.
	 * @param rol        Rol del administrador.
	 * @param cargo      Cargo del administrador.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String nombre, @RequestParam int documento,
			@RequestParam String correo, @RequestParam String contrasena, @RequestParam String rol,
			@RequestParam String cargo) {
		AdminDTO newAdmin = new AdminDTO(nombre, documento, correo, contrasena, rol, cargo);
		int status = adminService.create(newAdmin);
		if (status == 0) {
			return new ResponseEntity<>("Admin creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el admin", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Muestra la lista de todos los administradores.
	 *
	 * @return ResponseEntity con la lista de administradores o un mensaje si no hay
	 *         administradores.
	 */
	@GetMapping(path = "/mostrar")
	public ResponseEntity<String> mostrarAdmin() {
		List<AdminDTO> listaAdmin = adminService.getAll();
		if (listaAdmin.isEmpty()) {
			return new ResponseEntity<>("No se encontraron usuarios por mostrar", HttpStatus.NO_CONTENT);
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			listaAdmin.forEach((dto) -> stringBuilder.append(dto.toString() + "\n"));
			return new ResponseEntity<>("admin: " + stringBuilder.toString(), HttpStatus.ACCEPTED);
		}
	}

	/**
	 * Elimina un administrador por su ID.
	 *
	 * @param id ID del administrador a eliminar.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Long id) {
		int status = adminService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar el usuario debido a que no existe", HttpStatus.BAD_REQUEST);
		}
	}
}

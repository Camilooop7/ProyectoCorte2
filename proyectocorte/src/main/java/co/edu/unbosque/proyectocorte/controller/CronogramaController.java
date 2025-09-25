package co.edu.unbosque.proyectocorte.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.proyectocorte.dto.CronogramaDTO;
import co.edu.unbosque.proyectocorte.service.CronogramaService;

/**
 * Controlador REST para gestionar operaciones relacionadas con cronogramas.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/cronograma" })
public class CronogramaController {

	@Autowired
	private CronogramaService cronogramaService;

	/**
	 * Crea un nuevo cronograma.
	 *
	 * @param nombre Nombre del cronograma.
	 * @param link   Enlace del cronograma.
	 * @param fecha  Fecha del cronograma.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crearCronograma(@RequestParam String nombre, @RequestParam String link,
			@RequestParam("fecha") @DateTimeFormat(iso = ISO.DATE) LocalDate fecha) {
		CronogramaDTO newCronograma = new CronogramaDTO(nombre, link, fecha);
		int status = cronogramaService.create(newCronograma);
		if (status == 0) {
			return new ResponseEntity<>("Cronograma creado con éxito", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Error al crear el cronograma", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Muestra la lista de todos los cronogramas.
	 *
	 * @return ResponseEntity con la lista de cronogramas o un mensaje si no hay
	 *         cronogramas.
	 */
	@GetMapping(path = "/mostrar")
	public ResponseEntity<String> mostrarCronograma() {
		List<CronogramaDTO> lista = cronogramaService.getAll();
		if (lista.isEmpty()) {
			return new ResponseEntity<>("No se encontraron cronogramas por mostrar", HttpStatus.NO_CONTENT);
		}
		StringBuilder sb = new StringBuilder();
		for (CronogramaDTO dto : lista) {
			String nombre = dto.getNombre() == null ? "" : dto.getNombre();
			String link = dto.getLink() == null ? "" : dto.getLink();
			String fechaStr = dto.getFecha() == null ? "" : dto.getFecha().toString();
			sb.append("nombre: ").append(nombre).append(", link: ").append(link).append(", fecha: ").append(fechaStr)
					.append("\n");
		}
		return new ResponseEntity<>(sb.toString(), HttpStatus.ACCEPTED);
	}

	/**
	 * Elimina un cronograma por su ID.
	 *
	 * @param id ID del cronograma a eliminar.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Long id) {
		int status = cronogramaService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Cronograma eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al eliminar el cronograma debido a que no existe",
					HttpStatus.BAD_REQUEST);
		}
	}
}

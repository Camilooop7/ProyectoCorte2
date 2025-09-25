package co.edu.unbosque.proyectocorte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.proyectocorte.dto.LibroEnLineaDTO;
import co.edu.unbosque.proyectocorte.service.LibroEnLineaService;

/**
 * Controlador REST para gestionar operaciones relacionadas con libros en línea.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/libroenlinea" })
public class LibroEnLineaController {

	@Autowired
	private LibroEnLineaService libroEnLineaSer;

	/**
	 * Crea un nuevo libro en línea.
	 *
	 * @param codigo      Código del libro.
	 * @param nombre      Nombre del libro.
	 * @param descripcion Descripción del libro.
	 * @param link        Enlace del libro.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam("codigo") int codigo, @RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion, @RequestParam("link") String link) {
		LibroEnLineaDTO nuevo = new LibroEnLineaDTO(codigo, nombre, descripcion, link);
		int status = libroEnLineaSer.create(nuevo);
		if (status == 0) {
			return new ResponseEntity<>("Libro en línea creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el libro en línea", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene un libro en línea por su código.
	 *
	 * @param codigo Código del libro.
	 * @return ResponseEntity con el libro encontrado.
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<LibroEnLineaDTO> getLibroById(@PathVariable int codigo) {
		LibroEnLineaDTO libro = libroEnLineaSer.getLibroByCodigo(codigo);
		return ResponseEntity.ok(libro);
	}

	/**
	 * Elimina un libro en línea por su código.
	 *
	 * @param codigo Código del libro a eliminar.
	 * @return ResponseEntity con un mensaje indicando el resultado de la operación.
	 */
	@DeleteMapping("/eliminar/{codigo}")
	public ResponseEntity<String> eliminarLibroPorCodigo(@PathVariable int codigo) {
		int status = libroEnLineaSer.deleteByCodigo(codigo);
		if (status == 1) {
			return new ResponseEntity<>("Libro PDF eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Libro PDF no encontrado", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/mostrar")
    public ResponseEntity<String> mostrarLibros() {
        List<LibroEnLineaDTO> listaLibros = libroEnLineaSer.getAll();
        if (listaLibros.isEmpty()) {
            return new ResponseEntity<>("No se encontraron libros por mostrar", HttpStatus.NO_CONTENT);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            listaLibros.forEach((libro) -> stringBuilder.append(libro.toString()).append("\n"));
            return new ResponseEntity<>("Libros en línea: \n" + stringBuilder.toString(), HttpStatus.OK);
        }
    }
}

package co.edu.unbosque.proyectocorte.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocorte.dto.CronogramaDTO;
import co.edu.unbosque.proyectocorte.dto.ProfesorDTO;
import co.edu.unbosque.proyectocorte.service.CronogramaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/cronograma" })
public class CronogramaController {
	@Autowired
	private CronogramaService cronogramaService;
	
	
	@PostMapping(path ="/crear")
	public ResponseEntity<String> crearCronogrma(@RequestBody String nombre, String link, Date Fecha) {
		
		CronogramaDTO newCronograma = new CronogramaDTO(nombre, link, Fecha);
		int status = cronogramaService.create(newCronograma);
		
		if (status == 0) {
			return new ResponseEntity<>("cronogrma creado con exito", HttpStatus.CREATED);
		}else {
			
			return new ResponseEntity<>("Error al crear el cronogrma", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@GetMapping(path ="/mostrar")
	public  ResponseEntity<String>  mostrarConograma( ) {
		List<CronogramaDTO> listaCronograma = cronogramaService.getAll();
		if (listaCronograma.isEmpty()) {
			return new ResponseEntity<>("No se encontraron profesores por mostrar", HttpStatus.valueOf(204));
		}else {
			StringBuilder stringBuilder = new StringBuilder();
			listaCronograma.forEach((dto) -> stringBuilder.append(dto.toString() + "\n"));
			return new ResponseEntity<>("admin: " + stringBuilder.toString(), HttpStatus.valueOf(202));
			
		}
	}
	
	

}

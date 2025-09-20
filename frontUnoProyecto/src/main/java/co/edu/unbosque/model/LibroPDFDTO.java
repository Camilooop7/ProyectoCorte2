package co.edu.unbosque.model;

import org.springframework.web.multipart.MultipartFile;

public class LibroPDFDTO {
	private Long id;
	private String nombre;
	private String descripcion;
	private MultipartFile imagen;
	private MultipartFile contenidoPdf;

	

}

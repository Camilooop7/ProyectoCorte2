package co.edu.unbosque.proyectocorte.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.entity.LibroPDF;
import co.edu.unbosque.proyectocorte.repository.LibroPDFRepository;

@Service
public class LibroPDFService {

	@Autowired
	private LibroPDFRepository libroPDFRepo;

	@Autowired
	private ModelMapper modelMapper;

	public int create(String nombre, String descripcion, MultipartFile imagen, MultipartFile archivoPdf)
			throws IOException {
		LibroPDF entity = new LibroPDF(nombre, descripcion, imagen.getBytes(), archivoPdf.getBytes());
		libroPDFRepo.save(entity);
		return 1;
	}

	public List<LibroPDFDTO> getAll() {
		List<LibroPDF> entityList = libroPDFRepo.findAll();
		List<LibroPDFDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			LibroPDFDTO libroPDFDTO = modelMapper.map(entity, LibroPDFDTO.class);
			dtoList.add(libroPDFDTO);
		});
		return dtoList;
	}

	public byte[] getImagenById(Long id) {
		return libroPDFRepo.findById(id).orElseThrow().getImagen();
	}

	public byte[] getPdfContentById(Long id) {
		return libroPDFRepo.findById(id).orElseThrow().getContenidoPdf();
	}

	public int deleteById(Long id) {
		if (libroPDFRepo.existsById(id)) {
			libroPDFRepo.deleteById(id);
			return 1;
		}
		return 0;
	}

	public Long count() {
		return libroPDFRepo.count();
	}

	public boolean exist(Long id) {
		return libroPDFRepo.existsById(id);
	}
}

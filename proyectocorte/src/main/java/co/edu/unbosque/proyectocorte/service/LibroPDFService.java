package co.edu.unbosque.proyectocorte.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.entity.LibroPDF;
import co.edu.unbosque.proyectocorte.repository.LibroPDFRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibroPDFService {

	@Autowired
	private LibroPDFRepository libroPDFRepo;

	@Autowired
	private ModelMapper modelMapper;

	public int create(int codigo, String nombre, String descripcion, MultipartFile archivoPdf) throws IOException {
		LibroPDF entity = new LibroPDF(codigo, nombre, descripcion, archivoPdf.getBytes());
		libroPDFRepo.save(entity);
		return 1;
	}

	public LibroPDFDTO getLibroByCodigo(int codigo) {
		LibroPDF entity = libroPDFRepo.findByCodigo(codigo)
				.orElseThrow(() -> new RuntimeException("Libro con ID " + codigo + " no encontrado"));
		return modelMapper.map(entity, LibroPDFDTO.class);
	}

	public List<LibroPDFDTO> getAll() {
		List<LibroPDF> entityList = libroPDFRepo.findAll();
		List<LibroPDFDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> {
			LibroPDFDTO libroPDFDTO = modelMapper.map(entity, LibroPDFDTO.class);
			dtoList.add(libroPDFDTO);
		});
		return dtoList;
	}

	public byte[] getPdfContentByCodigo(int id) {
		return libroPDFRepo.findByCodigo(id)
				.orElseThrow(() -> new RuntimeException("Libro con ID " + id + " no encontrado")).getContenidoPdf();
	}
	@Transactional
	public int deleteByCodigo(int codigo) {
		if (libroPDFRepo.existsByCodigo(codigo)) {
			libroPDFRepo.deleteByCodigo(codigo);
			return 1;
		}
		return 0;
	}

	public Long count() {
		return libroPDFRepo.count();
	}

	public boolean exist(int codigo) {
		return libroPDFRepo.existsByCodigo(codigo);
	}
}
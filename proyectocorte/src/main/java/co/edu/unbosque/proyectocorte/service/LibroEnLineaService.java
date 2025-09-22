package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.proyectocorte.dto.LibroEnLineaDTO;
import co.edu.unbosque.proyectocorte.entity.LibroEnLinea;
import co.edu.unbosque.proyectocorte.repository.LibroEnLineaRepository;

@Service
public class LibroEnLineaService implements CRUDOperation<LibroEnLineaDTO> {

	@Autowired
	private LibroEnLineaRepository libroEnLineaRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public int create(LibroEnLineaDTO date) {
		LibroEnLinea entity = modelMapper.map(date, LibroEnLinea.class);
		libroEnLineaRepo.save(entity);
		return 0;
	}

	@Override
	public List<LibroEnLineaDTO> getAll() {
		List<LibroEnLinea> entityList = libroEnLineaRepo.findAll();
		List<LibroEnLineaDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			LibroEnLineaDTO libroEnLineaDTO = modelMapper.map(entity, LibroEnLineaDTO.class);
			dtoList.add(libroEnLineaDTO);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long a, LibroEnLineaDTO date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long count() {
		return libroEnLineaRepo.count();
	}

	@Override
	public boolean exist(Long a) {
		// TODO Auto-generated method stub
		return false;
	}
	@Transactional
	public int deleteByCodigo(int codigo) {
		if (libroEnLineaRepo.existsByCodigo(codigo)) {
			libroEnLineaRepo.deleteByCodigo(codigo);
			return 1;
		}
		return 0;
	}

	public boolean exist(int codigo) {
		return libroEnLineaRepo.existsByCodigo(codigo);
	}

	public LibroEnLineaDTO getLibroByCodigo(int codigo) {
		LibroEnLinea entity = libroEnLineaRepo.findByCodigo(codigo)
				.orElseThrow(() -> new RuntimeException("Libro con ID " + codigo + " no encontrado"));
		return modelMapper.map(entity, LibroEnLineaDTO.class);
	}
}

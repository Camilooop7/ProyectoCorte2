package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exist(Long a) {
		// TODO Auto-generated method stub
		return false;
	}

}

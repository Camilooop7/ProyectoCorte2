package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.proyectocorte.dto.LibroPDFDTO;
import co.edu.unbosque.proyectocorte.entity.LibroPDF;
import co.edu.unbosque.proyectocorte.repository.LibroPDFRepository;

@Service
public class LibroPDFService implements CRUDOperation<LibroPDFDTO> {

	@Autowired
	private LibroPDFRepository libroPDFRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public int create(LibroPDFDTO date) {
		LibroPDF entity = modelMapper.map(date, LibroPDF.class);
		libroPDFRepo.save(entity);
		return 0;
	}

	@Override
	public List<LibroPDFDTO> getAll() {
		List<LibroPDF> entityList = libroPDFRepo.findAll();
		List<LibroPDFDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			LibroPDFDTO libroPDFDTO = modelMapper.map(entityList, LibroPDFDTO.class);
			dtoList.add(libroPDFDTO);
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long a, LibroPDFDTO date) {
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

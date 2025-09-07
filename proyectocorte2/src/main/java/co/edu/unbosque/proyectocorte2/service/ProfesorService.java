package co.edu.unbosque.proyectocorte2.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocorte2.dto.ProfesorDTO;
import co.edu.unbosque.proyectocorte2.enty.Profesor;
import co.edu.unbosque.proyectocorte2.repository.ProfesorRepository;

@Service
public class ProfesorService implements CRUDOperation<ProfesorDTO> {
	
	@Autowired
	private ProfesorRepository profesorRepository;
	@Autowired
	private ModelMapper modelMapper ;
	
	
	@Override
	public int create(ProfesorDTO date) {
		
		Profesor entity = modelMapper.map(date, Profesor.class);
		profesorRepository.save(entity);
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<ProfesorDTO> getAll() {
		List<Profesor> entityList = profesorRepository.findAll();
		List<ProfesorDTO> dtoList = new ArrayList<>();
		
		entityList.forEach((entity)->{
			ProfesorDTO profesorDTO = modelMapper.map(entityList, ProfesorDTO.class);
			dtoList.add(profesorDTO);
		});

		return dtoList;
	}
	@Override
	public int deleteById(Long a) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int updateById(Long a, ProfesorDTO date) {
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

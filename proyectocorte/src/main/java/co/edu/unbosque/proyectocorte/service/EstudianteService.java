package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocorte.dto.EstudianteDTO;
import co.edu.unbosque.proyectocorte.entity.Estudiante;
import co.edu.unbosque.proyectocorte.repository.EstudianteRepository;
@Service
public class EstudianteService implements CRUDOperation<EstudianteDTO>{

	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private ModelMapper modelMapper ;
	
	@Override
	public int create(EstudianteDTO date) {
		Estudiante entity = modelMapper.map(date, Estudiante.class);
		estudianteRepository.save(entity);
		return 0;
		
	}   

	@Override
	public List<EstudianteDTO> getAll() {
		List<Estudiante> entityList = estudianteRepository.findAll();
		List<EstudianteDTO> dtoList = new ArrayList<>();
		
		entityList.forEach((entity)->{
			EstudianteDTO estudianteDTO = modelMapper.map(entityList, EstudianteDTO.class);
			dtoList.add(estudianteDTO);
		});

		return dtoList;
		
	}

	@Override
	public int deleteById(Long a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long a, EstudianteDTO date) {
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

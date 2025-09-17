package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.proyectocorte.dto.CronogramaDTO;
import co.edu.unbosque.proyectocorte.entity.Cronograma;
import co.edu.unbosque.proyectocorte.repository.CronogramaRepository;

public class CronogramaService implements CRUDOperation<CronogramaDTO> {
	@Autowired
	private CronogramaRepository cronogramaRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public int create(CronogramaDTO date) {
		Cronograma enty = modelMapper.map(date, Cronograma.class);
		cronogramaRepository.save(enty);
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CronogramaDTO> getAll() {
		List<Cronograma> entityList = cronogramaRepository.findAll();
		List<CronogramaDTO> dtoList = new ArrayList<>();
		
		entityList.forEach((entity)->{
			CronogramaDTO cronogramaDTO = modelMapper.map(entity, CronogramaDTO.class);
			dtoList.add(cronogramaDTO);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long a) {

		
		return 0;
	}

	@Override
	public int updateById(Long a, CronogramaDTO date) {
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

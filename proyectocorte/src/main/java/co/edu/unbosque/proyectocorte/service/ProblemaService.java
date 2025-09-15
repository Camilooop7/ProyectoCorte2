package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerspring.entity.User;
import co.edu.unbosque.proyectocorte.dto.ProblemaDTO;
import co.edu.unbosque.proyectocorte.entity.Problema;
import co.edu.unbosque.proyectocorte.repository.ProblemaRepository;

@Service
public class ProblemaService implements CRUDOperation<ProblemaDTO>{

    private final EstudianteService estudianteService;
	@Autowired
	private ProblemaRepository problemaRepository;
	@Autowired
	private ModelMapper modelMapper;

    ProblemaService(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

	@Override
	public int create(ProblemaDTO date) {
		Problema enty = modelMapper.map(date, Problema.class);
		problemaRepository.save(enty);
		

		return 0;
	}

	@Override
	public List<ProblemaDTO> getAll() {
		List<Problema> entityList = problemaRepository.findAll();
		List<ProblemaDTO> dtoList = new ArrayList<>();
		
		entityList.forEach((entity)->{
			ProblemaDTO problemaDTO = modelMapper.map(entity, ProblemaDTO.class);
			dtoList.add(problemaDTO);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long a) {
		if (problemaRepository.existsById(a)) {
			problemaRepository.deleteById(a);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long a, ProblemaDTO date) {
		Optional<Problema> opt = problemaRepository.findById(a);
		if (opt.isPresent()) {
			Problema entity = opt.get();
			entity.setTitulo(date.getTitulo());
			entity.setDificultad(date.getDificultad());
			entity.setTema(date.getTema());
			entity.setJuez(date.getJuez());
			entity.setLink(date.getLink());
			
			problemaRepository.save(entity);
			return 0;
		}
		return 1;
	}

	@Override
	public Long count() {
		long todo = getAll().size();
		return todo;
	}

	@Override
	public boolean exist(Long a) {
		// TODO Auto-generated method stub
		return false;
	}

}

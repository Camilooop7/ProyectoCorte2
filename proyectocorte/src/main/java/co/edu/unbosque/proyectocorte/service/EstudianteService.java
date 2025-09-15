package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocorte.dto.EstudianteDTO;
import co.edu.unbosque.proyectocorte.entity.Estudiante;
import co.edu.unbosque.proyectocorte.entity.Profesor;
import co.edu.unbosque.proyectocorte.exception.CapitalException;
import co.edu.unbosque.proyectocorte.exception.CharacterException;
import co.edu.unbosque.proyectocorte.exception.ExceptionCheker;
import co.edu.unbosque.proyectocorte.exception.MailException;
import co.edu.unbosque.proyectocorte.exception.NegativeNumberException;
import co.edu.unbosque.proyectocorte.exception.NumberException;
import co.edu.unbosque.proyectocorte.exception.SmallException;
import co.edu.unbosque.proyectocorte.exception.SymbolException;
import co.edu.unbosque.proyectocorte.exception.TextException;
import co.edu.unbosque.proyectocorte.repository.EstudianteRepository;
@Service
public class EstudianteService implements CRUDOperation<EstudianteDTO>{

	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private ModelMapper modelMapper ;
	
	@Override
	public int create(EstudianteDTO date) {
		try {
			ExceptionCheker.checkerText(date.getNombre());
			ExceptionCheker.checkerText(date.getCarrera());
			ExceptionCheker.checkerNegativeNumber(date.getDocumento());
			ExceptionCheker.checkerNegativeNumber(date.getSemestre());
			ExceptionCheker.checkerMail(date.getCorreo());
			ExceptionCheker.checkerPasword(date.getContrasena());
			Estudiante entity = modelMapper.map(date, Estudiante.class);
			estudianteRepository.save(entity);
			return 0;
		} catch (TextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegativeNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CapitalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CharacterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
		
	}   

	@Override
	public List<EstudianteDTO> getAll() {
		List<Estudiante> entityList = estudianteRepository.findAll();
		List<EstudianteDTO> dtoList = new ArrayList<>();
		
		entityList.forEach((entity)->{
			EstudianteDTO estudianteDTO = modelMapper.map(entity, EstudianteDTO.class);
			dtoList.add(estudianteDTO);
		});

		return dtoList;
		
	}

	@Override
	public int deleteById(Long a) {
		if (estudianteRepository.existsById(a)) {
			estudianteRepository.deleteById(a);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long a, EstudianteDTO date) {
		Optional<Estudiante> opt = estudianteRepository.findById(a);
		if (opt.isPresent()) {
			Estudiante entity = opt.get();
			entity.setNombre(date.getNombre());
			entity.setDocumento(date.getDocumento());
			entity.setCorreo(date.getCorreo());
			entity.setContrasena(date.getContrasena());
			entity.setCarrera(date.getCarrera());
			entity.setSemestre(date.getSemestre());
			estudianteRepository.save(entity);
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

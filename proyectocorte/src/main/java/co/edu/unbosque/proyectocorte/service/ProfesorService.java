package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocorte.dto.ProfesorDTO;
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
import co.edu.unbosque.proyectocorte.repository.ProfesorRepository;

@Service
public class ProfesorService implements CRUDOperation<ProfesorDTO> {
	
	@Autowired
	private ProfesorRepository profesorRepository;
	@Autowired
	private ModelMapper modelMapper ;
	
	
	@Override
	public int create(ProfesorDTO date) {
		
		try {
			ExceptionCheker.checkerText(date.getNombre());
			ExceptionCheker.checkerText(date.getDepartamento());
			ExceptionCheker.checkerNegativeNumber(date.getDocumento());
			ExceptionCheker.checkerMail(date.getCorreo());
			ExceptionCheker.checkerPasword(date.getContrasena());
			Profesor entity = modelMapper.map(date, Profesor.class);
			profesorRepository.save(entity);
			// TODO Auto-generated method stub
			return 0;
			
		} catch (TextException e) {
			// TODO Auto-generated catch block
		} catch (NegativeNumberException e) {
			// TODO Auto-generated catch block
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
		} catch (MailException e) {
			// TODO Auto-generated catch block
		} catch (CapitalException e) {
			// TODO Auto-generated catch block
		} catch (CharacterException e) {
			// TODO Auto-generated catch block
		} catch (NumberException e) {
			// TODO Auto-generated catch block
		} catch (SymbolException e) {
			// TODO Auto-generated catch block
		} catch (SmallException e) {
			// TODO Auto-generated catch block
		}
		return 1;
	}
	@Override
	public List<ProfesorDTO> getAll() {
		List<Profesor> entityList = profesorRepository.findAll();
		List<ProfesorDTO> dtoList = new ArrayList<>();
		
		entityList.forEach((entity)->{
			ProfesorDTO profesorDTO = modelMapper.map(entity, ProfesorDTO.class);
			dtoList.add(profesorDTO);
		});

		return dtoList;
	}
	@Override
	public int deleteById(Long a) {
		if (profesorRepository.existsById(a)) {
			profesorRepository.deleteById(a);
			return 0;
		}
		return 1;
	}
	@Override
	public int updateById(Long a, ProfesorDTO date) {
		Optional<Profesor> opt = profesorRepository.findById(a);
		if (opt.isPresent()) {
			Profesor entity = opt.get();
			entity.setNombre(date.getNombre());
			entity.setDocumento(date.getDocumento());
			entity.setCorreo(date.getCorreo());
			entity.setContrasena(date.getContrasena());
			entity.setDepartamento(date.getDepartamento());
			profesorRepository.save(entity);
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

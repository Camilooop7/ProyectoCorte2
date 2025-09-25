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

/**
 * Servicio para gestionar las operaciones CRUD de la entidad {@link Profesor}.
 */
@Service
public class ProfesorService implements CRUDOperation<ProfesorDTO> {

	@Autowired
	private ProfesorRepository profesorRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo profesor.
	 *
	 * @param date DTO que contiene los datos del profesor a crear.
	 * @return 0 si la creación fue exitosa, 1 en caso de error.
	 */
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
			return 0;
		} catch (TextException | NegativeNumberException | InputMismatchException | MailException | CapitalException
				| CharacterException | NumberException | SymbolException | SmallException e) {
			return 1;
		}
	}

	/**
	 * Obtiene todos los profesores.
	 *
	 * @return Lista de DTOs de profesores.
	 */
	@Override
	public List<ProfesorDTO> getAll() {
		List<Profesor> entityList = profesorRepository.findAll();
		List<ProfesorDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			ProfesorDTO profesorDTO = modelMapper.map(entity, ProfesorDTO.class);
			dtoList.add(profesorDTO);
		});
		return dtoList;
	}

	/**
	 * Elimina un profesor por su ID.
	 *
	 * @param a ID del profesor a eliminar.
	 * @return 0 si la eliminación fue exitosa, 1 en caso de error.
	 */
	@Override
	public int deleteById(Long a) {
		if (profesorRepository.existsById(a)) {
			profesorRepository.deleteById(a);
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un profesor por su ID.
	 *
	 * @param a    ID del profesor a actualizar.
	 * @param date DTO con los nuevos datos del profesor.
	 * @return 0 si la actualización fue exitosa, 1 en caso de error.
	 */
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

	/**
	 * Cuenta el número total de profesores.
	 *
	 * @return Número total de profesores.
	 */
	@Override
	public Long count() {
		return (long) getAll().size();
	}

	/**
	 * Verifica si existe un profesor con el ID proporcionado.
	 *
	 * @param a ID del profesor a verificar.
	 * @return {@code false} (método no implementado).
	 */
	@Override
	public boolean exist(Long a) {
		return false;
	}
}

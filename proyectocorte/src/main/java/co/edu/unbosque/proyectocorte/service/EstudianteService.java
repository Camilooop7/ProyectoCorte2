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

/**
 * Servicio para gestionar las operaciones CRUD de la entidad
 * {@link Estudiante}.
 */
@Service
public class EstudianteService implements CRUDOperation<EstudianteDTO> {

	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo estudiante.
	 *
	 * @param date DTO que contiene los datos del estudiante a crear.
	 * @return 0 si la creación fue exitosa, 1 en caso de error.
	 */
	@Override
	public int create(EstudianteDTO date) {
		try {
			ExceptionCheker.checkerText(date.getNombre());
			ExceptionCheker.checkerText(date.getCarrera());
			ExceptionCheker.checkerNegativeNumber(date.getDocumento());
			ExceptionCheker.checkerNegativeNumber(date.getSemestre());
			ExceptionCheker.checkerPasword(date.getContrasena());
			ExceptionCheker.checkerMail(date.getCorreo());
			Estudiante entity = modelMapper.map(date, Estudiante.class);
			estudianteRepository.save(entity);
			return 0;
		} catch (TextException | NegativeNumberException | CapitalException | CharacterException | NumberException
				| SymbolException | SmallException | InputMismatchException | MailException e) {
			return 1;
		}
	}

	/**
	 * Obtiene todos los estudiantes.
	 *
	 * @return Lista de DTOs de estudiantes.
	 */
	@Override
	public List<EstudianteDTO> getAll() {
		List<Estudiante> entityList = estudianteRepository.findAll();
		List<EstudianteDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			EstudianteDTO estudianteDTO = modelMapper.map(entity, EstudianteDTO.class);
			dtoList.add(estudianteDTO);
		});
		return dtoList;
	}

	/**
	 * Elimina un estudiante por su ID.
	 *
	 * @param a ID del estudiante a eliminar.
	 * @return 0 si la eliminación fue exitosa, 1 en caso de error.
	 */
	@Override
	public int deleteById(Long a) {
		if (estudianteRepository.existsById(a)) {
			estudianteRepository.deleteById(a);
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un estudiante por su ID.
	 *
	 * @param a    ID del estudiante a actualizar.
	 * @param date DTO con los nuevos datos del estudiante.
	 * @return 0 si la actualización fue exitosa, 1 en caso de error.
	 */
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

	/**
	 * Cuenta el número total de estudiantes.
	 *
	 * @return Número total de estudiantes.
	 */
	@Override
	public Long count() {
		return (long) getAll().size();
	}

	/**
	 * Verifica si existe un estudiante con el ID proporcionado.
	 *
	 * @param a ID del estudiante a verificar.
	 * @return {@code true} si el estudiante existe, {@code false} en caso
	 *         contrario.
	 */
	@Override
	public boolean exist(Long a) {
		return estudianteRepository.existsById(a);
	}
}

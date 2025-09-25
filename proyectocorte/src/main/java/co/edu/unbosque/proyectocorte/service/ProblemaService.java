package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.proyectocorte.dto.ProblemaDTO;
import co.edu.unbosque.proyectocorte.entity.Problema;
import co.edu.unbosque.proyectocorte.exception.ExceptionCheker;
import co.edu.unbosque.proyectocorte.exception.NegativeNumberException;
import co.edu.unbosque.proyectocorte.repository.ProblemaRepository;

/**
 * Servicio para gestionar las operaciones CRUD de la entidad {@link Problema}.
 */
@Service
public class ProblemaService implements CRUDOperation<ProblemaDTO> {

	@Autowired
	private ProblemaRepository problemaRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo problema.
	 *
	 * @param date DTO que contiene los datos del problema a crear.
	 * @return 0 si la creación fue exitosa.
	 */
	@Override
	public int create(ProblemaDTO date) {
		try {
			ExceptionCheker.checkerNegativeNumber(date.getDificultad());
			Problema enty = modelMapper.map(date, Problema.class);
			problemaRepository.save(enty);
		} catch (NegativeNumberException e) {
			return 1;
		}
		return 0;
	}

	/**
	 * Obtiene todos los problemas.
	 *
	 * @return Lista de DTOs de problemas.
	 */
	@Override
	public List<ProblemaDTO> getAll() {
		List<Problema> entityList = problemaRepository.findAll();
		List<ProblemaDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			ProblemaDTO problemaDTO = modelMapper.map(entity, ProblemaDTO.class);
			dtoList.add(problemaDTO);
		});
		return dtoList;
	}

	/**
	 * Elimina un problema por su ID.
	 *
	 * @param a ID del problema a eliminar.
	 * @return 0 si la eliminación fue exitosa, 1 en caso de error.
	 */
	@Override
	public int deleteById(Long a) {
		if (problemaRepository.existsById(a)) {
			problemaRepository.deleteById(a);
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un problema por su ID.
	 *
	 * @param a    ID del problema a actualizar.
	 * @param date DTO con los nuevos datos del problema.
	 * @return 0 si la actualización fue exitosa, 1 en caso de error.
	 */
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

	/**
	 * Cuenta el número total de problemas.
	 *
	 * @return Número total de problemas.
	 */
	@Override
	public Long count() {
		return (long) getAll().size();
	}

	/**
	 * Verifica si existe un problema con el ID proporcionado.
	 *
	 * @param a ID del problema a verificar.
	 * @return {@code false} (método no implementado).
	 */
	@Override
	public boolean exist(Long a) {
		return false;
	}
}

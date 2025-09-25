package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.proyectocorte.dto.CronogramaDTO;
import co.edu.unbosque.proyectocorte.entity.Cronograma;
import co.edu.unbosque.proyectocorte.repository.CronogramaRepository;

/**
 * Servicio para gestionar las operaciones CRUD de la entidad
 * {@link Cronograma}.
 */
@Service
public class CronogramaService implements CRUDOperation<CronogramaDTO> {

	@Autowired
	private CronogramaRepository cronogramaRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo cronograma.
	 *
	 * @param date DTO que contiene los datos del cronograma a crear.
	 * @return 0 si la creación fue exitosa.
	 */
	@Override
	public int create(CronogramaDTO date) {
		Cronograma enty = modelMapper.map(date, Cronograma.class);
		cronogramaRepository.save(enty);
		return 0;
	}

	/**
	 * Obtiene todos los cronogramas.
	 *
	 * @return Lista de DTOs de cronogramas.
	 */
	@Override
	public List<CronogramaDTO> getAll() {
		List<Cronograma> entityList = cronogramaRepository.findAll();
		List<CronogramaDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			CronogramaDTO cronogramaDTO = modelMapper.map(entity, CronogramaDTO.class);
			dtoList.add(cronogramaDTO);
		});
		return dtoList;
	}

	/**
	 * Elimina un cronograma por su ID.
	 *
	 * @param a ID del cronograma a eliminar.
	 * @return 0 si la eliminación fue exitosa, 1 en caso de error.
	 */
	@Override
	public int deleteById(Long a) {
		if (cronogramaRepository.existsById(a)) {
			cronogramaRepository.deleteById(a);
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un cronograma por su ID.
	 *
	 * @param a    ID del cronograma a actualizar.
	 * @param date DTO con los nuevos datos del cronograma.
	 * @return 0 (método no implementado).
	 */
	@Override
	public int updateById(Long a, CronogramaDTO date) {
		return 0;
	}

	/**
	 * Cuenta el número total de cronogramas.
	 *
	 * @return {@code null} (método no implementado).
	 */
	@Override
	public Long count() {
		return null;
	}

	/**
	 * Verifica si existe un cronograma con el ID proporcionado.
	 *
	 * @param a ID del cronograma a verificar.
	 * @return {@code false} (método no implementado).
	 */
	@Override
	public boolean exist(Long a) {
		return false;
	}
}

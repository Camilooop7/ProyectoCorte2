package co.edu.unbosque.proyectocorte.service;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones CRUD básicas para cualquier
 * entidad DTO.
 *
 * @param <D> Tipo de DTO con el que se trabajarán las operaciones CRUD.
 */
public interface CRUDOperation<D> {

	/**
	 * Crea una nueva entidad a partir de los datos proporcionados en el DTO.
	 *
	 * @param date DTO que contiene los datos de la entidad a crear.
	 * @return Código de estado de la operación. 0 si fue exitosa, otro valor en
	 *         caso de error.
	 */
	public int create(D date);

	/**
	 * Obtiene todas las entidades del tipo DTO.
	 *
	 * @return Lista de DTOs que representan todas las entidades.
	 */
	public List<D> getAll();

	/**
	 * Elimina una entidad por su identificador único.
	 *
	 * @param a Identificador único de la entidad a eliminar.
	 * @return Código de estado de la operación. 0 si fue exitosa, otro valor en
	 *         caso de error.
	 */
	public int deleteById(Long a);

	/**
	 * Actualiza una entidad existente con los datos proporcionados en el DTO.
	 *
	 * @param a    Identificador único de la entidad a actualizar.
	 * @param date DTO que contiene los nuevos datos de la entidad.
	 * @return Código de estado de la operación. 0 si fue exitosa, otro valor en
	 *         caso de error.
	 */
	public int updateById(Long a, D date);

	/**
	 * Cuenta el número total de entidades.
	 *
	 * @return Número total de entidades.
	 */
	public Long count();

	/**
	 * Verifica si existe una entidad con el identificador único proporcionado.
	 *
	 * @param a Identificador único de la entidad a verificar.
	 * @return {@code true} si la entidad existe, {@code false} en caso contrario.
	 */
	public boolean exist(Long a);
}

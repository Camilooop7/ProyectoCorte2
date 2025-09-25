package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.proyectocorte.dto.AdminDTO;
import co.edu.unbosque.proyectocorte.entity.Admin;
import co.edu.unbosque.proyectocorte.exception.CapitalException;
import co.edu.unbosque.proyectocorte.exception.CharacterException;
import co.edu.unbosque.proyectocorte.exception.ExceptionCheker;
import co.edu.unbosque.proyectocorte.exception.MailException;
import co.edu.unbosque.proyectocorte.exception.NegativeNumberException;
import co.edu.unbosque.proyectocorte.exception.NumberException;
import co.edu.unbosque.proyectocorte.exception.SmallException;
import co.edu.unbosque.proyectocorte.exception.SymbolException;
import co.edu.unbosque.proyectocorte.exception.TextException;
import co.edu.unbosque.proyectocorte.repository.AdminRepository;

/**
 * Servicio para gestionar las operaciones CRUD de la entidad {@link Admin}.
 */
@Service
public class AdminService implements CRUDOperation<AdminDTO> {

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo administrador.
	 *
	 * @param date DTO que contiene los datos del administrador a crear.
	 * @return 0 si la creación fue exitosa, 1 en caso de error.
	 */
	@Override
	public int create(AdminDTO date) {
		try {
			ExceptionCheker.checkerText(date.getNombre());
			ExceptionCheker.checkerText(date.getCargo());
			ExceptionCheker.checkerNegativeNumber(date.getDocumento());
			ExceptionCheker.checkerMail(date.getCorreo());
			ExceptionCheker.checkerPasword(date.getContrasena());
			Admin entity = modelMapper.map(date, Admin.class);
			adminRepo.save(entity);
			return 0;
		} catch (TextException | NegativeNumberException | InputMismatchException | MailException | CapitalException
				| CharacterException | NumberException | SymbolException | SmallException e) {
			return 1;
		}
	}

	/**
	 * Obtiene todos los administradores.
	 *
	 * @return Lista de DTOs de administradores.
	 */
	@Override
	public List<AdminDTO> getAll() {
		List<Admin> entityList = adminRepo.findAll();
		List<AdminDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			AdminDTO adminDTO = modelMapper.map(entity, AdminDTO.class);
			dtoList.add(adminDTO);
		});
		return dtoList;
	}

	/**
	 * Elimina un administrador por su ID.
	 *
	 * @param a ID del administrador a eliminar.
	 * @return 0 si la eliminación fue exitosa, 1 en caso de error.
	 */
	@Override
	public int deleteById(Long a) {
		if (adminRepo.existsById(a)) {
			adminRepo.deleteById(a);
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un administrador por su ID.
	 *
	 * @param a    ID del administrador a actualizar.
	 * @param date DTO con los nuevos datos del administrador.
	 * @return 0 si la actualización fue exitosa, 1 en caso de error.
	 */
	@Override
	public int updateById(Long a, AdminDTO date) {
		Optional<Admin> opt = adminRepo.findById(a);
		if (opt.isPresent()) {
			Admin entity = opt.get();
			entity.setNombre(date.getNombre());
			entity.setDocumento(date.getDocumento());
			entity.setCorreo(date.getCorreo());
			entity.setContrasena(date.getContrasena());
			entity.setCargo(date.getCargo());
			adminRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el número total de administradores.
	 *
	 * @return Número total de administradores.
	 */
	@Override
	public Long count() {
		return (long) getAll().size();
	}

	/**
	 * Verifica si existe un administrador con el ID proporcionado.
	 *
	 * @param a ID del administrador a verificar.
	 * @return {@code false} (método no implementado).
	 */
	@Override
	public boolean exist(Long a) {
		return false;
	}
}

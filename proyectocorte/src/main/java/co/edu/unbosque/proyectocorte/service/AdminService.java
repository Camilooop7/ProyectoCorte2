package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocorte.dto.AdminDTO;
import co.edu.unbosque.proyectocorte.entity.Admin;
import co.edu.unbosque.proyectocorte.entity.Profesor;
import co.edu.unbosque.proyectocorte.repository.AdminRepository;

@Service
public class AdminService implements CRUDOperation<AdminDTO> {

	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public int create(AdminDTO date) {
		Admin entity = modelMapper.map(date, Admin.class);
		adminRepo.save(entity);
		return 0;
	}

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

	@Override
	public int deleteById(Long a) {
		if (adminRepo.existsById(a)) {
			adminRepo.deleteById(a);
			return 0;
		}
		return 1;
	}

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

package co.edu.unbosque.proyectocorte.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocorte.dto.AdminDTO;
import co.edu.unbosque.proyectocorte.entity.Admin;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long a, AdminDTO date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exist(Long a) {
		// TODO Auto-generated method stub
		return false;
	}

}

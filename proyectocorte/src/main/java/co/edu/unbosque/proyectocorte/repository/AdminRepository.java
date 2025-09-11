package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte.entity.Admin;


public interface AdminRepository extends JpaRepository<Admin, Long> {
	public Optional<Admin> findByNombre(String nombre);
	public void deleteByNombre(String nombre);
}

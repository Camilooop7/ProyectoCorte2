package co.edu.unbosque.proyectocorte2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte2.enty.Admin;


public interface AdminRepository extends JpaRepository<Admin, Long> {
	public Optional<Admin> findByNombre(String nombre);
	public void deleteByNombre(String nombre);
}

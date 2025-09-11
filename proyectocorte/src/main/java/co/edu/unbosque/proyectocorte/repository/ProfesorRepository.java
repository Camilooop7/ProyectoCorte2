package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte.entity.Profesor;

public interface ProfesorRepository  extends JpaRepository<Profesor, Long>{
	public Optional<Profesor> findByNombre(String nombre);
	public void deleteByNombre(String nombre);
}

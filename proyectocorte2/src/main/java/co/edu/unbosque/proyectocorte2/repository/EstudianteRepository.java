package co.edu.unbosque.proyectocorte2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte2.enty.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	public Optional<Estudiante> findByNombre(String nombre);
	public void deleteByNombre(String nombre);
}

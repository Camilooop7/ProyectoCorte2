package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte.entity.Cronograma;


public interface CronogramaRepository extends JpaRepository<Cronograma, Long>{
	public Optional<Cronograma> findByNombre(String nombre);
	public void deleteByNombre(String nombre);

}

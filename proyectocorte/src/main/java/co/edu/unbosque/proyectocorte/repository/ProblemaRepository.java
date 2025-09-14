package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte.entity.Problema;

public interface ProblemaRepository extends JpaRepository<Problema, Long>{
	public Optional<Problema> findByTitulo(String titulo);
	public void deleteByTitulo(String titulo);

}

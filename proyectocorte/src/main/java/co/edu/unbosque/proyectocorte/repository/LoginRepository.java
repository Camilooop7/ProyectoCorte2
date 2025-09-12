package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte.entity.Persona;


public interface LoginRepository extends JpaRepository<Persona, Long> {
	    Optional<Persona> findByCorreo(String email);
	    
}

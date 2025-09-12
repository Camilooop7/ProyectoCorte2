package co.edu.unbosque.proyectocorte2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte2.enty.Persona;

public interface LoginRepository extends JpaRepository<Persona, Long> {
	    Optional<Persona> findByCorreo(String email);
	    
}

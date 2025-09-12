package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte.entity.LibroPDF;

public interface LibroPDFRepository extends JpaRepository<LibroPDF, Long> {
	public Optional<LibroPDF> findByNombre(String nombre);

	public void deleteByNombre(String nombre);

}

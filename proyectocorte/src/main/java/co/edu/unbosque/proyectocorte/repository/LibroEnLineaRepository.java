package co.edu.unbosque.proyectocorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.proyectocorte.entity.LibroEnLinea;

public interface LibroEnLineaRepository extends JpaRepository<LibroEnLinea, Long> {
	public Optional<LibroEnLinea> findByNombre(String nombre);

	public Optional<LibroEnLinea> findByCodigo(int codigo);

	public void deleteByCodigo(int codigo);

	public boolean existsByCodigo(int codigo);

}

package co.edu.unbosque.proyectocorte.service;

import java.util.List;

public interface CRUDOperation<D> {

	public int create(D date);

	public List<D> getAll();

	public int deleteById(Long a);

	public int updateById(Long a, D date);

	public Long count();

	public boolean exist(Long a);
}

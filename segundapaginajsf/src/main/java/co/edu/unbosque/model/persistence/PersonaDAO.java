package co.edu.unbosque.model.persistence;

import java.util.ArrayList;
import java.util.Date;

import co.edu.unbosque.model.Persona;
import co.edu.unbosque.model.PersonaDTO;



public class PersonaDAO implements OperacionDAO<PersonaDTO, Persona>{

private ArrayList<Persona> listaPersona;
	
	private final String TEXT_FILE_NAME = "persona.csv";
	private final String SERIAL_FILE_NAME = "persona.dat";
	
	public PersonaDAO() {
		listaPersona = new ArrayList<>();
//		cargarDesdeArchivo();
		cargarArchivoSerializado();
	}
	


	@Override
	public String showAll() {
		String salida = "";
		if (listaPersona.isEmpty()) {
			return "No hay mascotas en la lista";
		} else {
			for (Persona persona : listaPersona) {
				salida += persona;
			}
			return salida;
		}
	}




	@Override
	public ArrayList<PersonaDTO> getAll() {
		return DataMapper.listaMascotaToListaMascotaDTO(listaPersona);
	}

	@Override
	public boolean add(PersonaDTO newData) {
		if (find(DataMapper.mascotaDTOToMascota(newData)) == null) {
			listaPersona.add(DataMapper.mascotaDTOToMascota(newData));
			return true;
		} else {
			return false;
		}
	}




	@Override
	public boolean delete(PersonaDTO toDelete) {
		Persona found = find(DataMapper.mascotaDTOToMascota(toDelete));
		if (found != null) {
			return listaPersona.remove(found);
		} else {
			return false;
		}
	}






	@Override
	public Persona find(Persona toFind) {
		Persona found = null;
		if (!listaPersona.isEmpty()) {
			for (Persona persona : listaPersona) {
				if (persona.getNombre().equals(toFind.getNombre())) {
					found = persona;
					return found;
				} else {
					continue; // las sig lineas desps de continue no se ejecutan, saltan a la sig iteracion
				}
			}
		} else {
			return null;
		}
		return null;
	}



	@Override
	public boolean update(PersonaDTO previous, PersonaDTO newData) {
		Persona found = find(DataMapper.mascotaDTOToMascota(previous));
		if (found != null) {
			listaPersona.remove(found);
			listaPersona.add(DataMapper.mascotaDTOToMascota(newData));
			return true;
		} else {
			return false;
		}
	}



	public ArrayList<Persona> getListaPersona() {
		return listaPersona;
	}



	public void setListaPersona(ArrayList<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}



	public String getTEXT_FILE_NAME() {
		return TEXT_FILE_NAME;
	}



	public String getSERIAL_FILE_NAME() {
		return SERIAL_FILE_NAME;
	}


	public void escribirEnArchivo() {
		String contenido = "";
		for (int i = 0; i < listaPersona.size(); i++) {
			contenido += listaPersona.get(i).getNombre() + ";";
			contenido += listaPersona.get(i).getColor() + ";";
			contenido += listaPersona.get(i).getFecha() + "\n";
			
		}
		FileManager.escribirEnArchivoDeTexto(TEXT_FILE_NAME, contenido);
		
	}
	
	
	
	
	
	
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, listaPersona);
	}

	public void cargarArchivoSerializado() {
		listaPersona = (ArrayList<Persona>) FileManager.leerArchivoSerializado(SERIAL_FILE_NAME);
		if(listaPersona == null) {
			listaPersona = new ArrayList<>();
		}
	}

	
	

}
	


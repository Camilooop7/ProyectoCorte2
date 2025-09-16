package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.Persona;
import co.edu.unbosque.model.PersonaDTO;



public class DataMapper {

	
	//Persona
		public static Persona mascotaDTOToMascota(PersonaDTO dto) {
			Persona entity;
			entity = new Persona(dto.getNombre(), dto.getColor(), dto.getFecha());
				return entity;
		}

		public static PersonaDTO mascotaToMascotaDTO(Persona entity) {
			PersonaDTO dto;
			dto = new PersonaDTO(entity.getNombre(), entity.getColor(), entity.getFecha());
			return dto;
		}

		public static ArrayList<PersonaDTO> listaMascotaToListaMascotaDTO(ArrayList<Persona> entityList) {
			ArrayList<PersonaDTO> dtoList = new ArrayList<>();
			for (Persona m : entityList) {
				dtoList.add(new PersonaDTO(m.getNombre(), m.getColor(), m.getFecha()));
			}
			return dtoList;
		}

		public static ArrayList<Persona> listaMascotaDTOToListaMascota(ArrayList<PersonaDTO> dtoList) {
			ArrayList<Persona> entityList = new ArrayList<>();
			for (PersonaDTO d : dtoList) {
				entityList.add(new Persona(d.getNombre(), d.getColor(), d.getFecha()));
			}
			return entityList;
		}
	
}

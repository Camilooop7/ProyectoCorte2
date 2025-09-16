package co.edu.unbosque.service;

import co.edu.unbosque.model.ModelFacade;
import co.edu.unbosque.model.PersonaDTO;

public class PersonaService {

	
	
	public PersonaService() {
		//una memoria de solo una vez entonces tiene que ser static
	}
	
	//
	public void crearPersona(PersonaDTO nuevo) {
		ModelFacade.personaDAO.add(nuevo);
	}
}

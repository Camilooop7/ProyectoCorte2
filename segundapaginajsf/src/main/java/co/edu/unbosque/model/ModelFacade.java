package co.edu.unbosque.model;

import co.edu.unbosque.model.persistence.PersonaDAO;

public class ModelFacade {

	public static PersonaDAO personaDAO;
	
	
	
	public  ModelFacade() {
	
		personaDAO = new PersonaDAO();
		
	}
	
}

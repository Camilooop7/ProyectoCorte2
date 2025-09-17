package co.edu.unbosque.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.UserDTO;
import co.edu.unbosque.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;

@Named("userBean")
@RequestScoped
public class UserBean {

	private String saludo = "";
	private String correo = "";
	private String contrasena = "";

	private String newUsername = "";
	private String newPass = "";
	

	private List<UserDTO> listUsers = new ArrayList<>();
	
	
	public UserBean() {
		
	}
	
	
	@PostConstruct
    public void init() {
        cargarUsuarios();
    }

    public void cargarUsuarios() {
        listUsers = UserService.doGetAll("http://localhost:8081/user/getall");
    }
	
	
	
	public void showStickyLogin(String code, String content) {
		if (code.equals("201")) {
			FacesContext.getCurrentInstance().addMessage("sticky-key",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Hecho", content));
		} else if (code.equals("406")) {
			FacesContext.getCurrentInstance().addMessage("sticky-key",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", content));
		} else {
			System.out.println("Error en crear cuenta");
			System.out.println("Status code: " + code);
			System.out.println("reason: " + content);
			FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error Critico", "Error al crear," + "comuniquese con el administrador"));
		}
	}
	
	
	
	public void crearUsuario() {
		//newUsername = AESUtil.encrypt(newUsername);
		//newPass1 = AESUtil.encrypt(newPass1);
		String json = "{";
		json += "\"correo\" : \"" + newUsername + "\",";
		json += "\"password\" : \"" + newPass + "\"";
		json += "}";
		String respuesta = UserService.doPost("http://localhost:8081/user/createjson", json);
		String[] data = respuesta.split("\n");
		showStickyLogin(data[0], data[1]);
		newUsername = "";
		newPass = "";
		
	}
	
	
	public void showSticky() {
		FacesContext.getCurrentInstance().addMessage("sticky-key",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sticky Message", "Message Content"));
	}
	
	
	

	


	public String getSaludo() {
		return saludo;
	}


	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public String getNewUsername() {
		return newUsername;
	}


	public void setNewUsername(String newUsername) {
		this.newUsername = newUsername;
	}


	public String getNewPass() {
		return newPass;
	}


	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}


	public List<UserDTO> getListUsers() {
		return listUsers;
	}


	public void setListUsers(List<UserDTO> listUsers) {
		this.listUsers = listUsers;
	}
	
	
	
	
	
}

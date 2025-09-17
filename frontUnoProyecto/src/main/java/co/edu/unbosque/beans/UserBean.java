package co.edu.unbosque.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;

import jakarta.enterprise.context.RequestScoped;

@Named(value = "userBean")
@RequestScoped
public class UserBean {

    public void checkSession() {
        String usuario = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuario == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cerrarSesion() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.invalidateSession(); 
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al redirigir"));
        }
    }
    

    public void mostrarAdmin() {
    	try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("principal.xhtml");
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al redirigir"));
        }
    }

    public void mostrarInfo() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Mostrando información..."));
    }

    public void comprar() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Compra realizada con éxito"));
    }
    
    public String getApplyThemeScript() {
    	  return "function applyThemeByInputId(id){var el=document.getElementById(id);if(!el)return;document.documentElement.setAttribute('data-theme',el.value);document.body.setAttribute('data-theme',el.value);}";
    	}

}

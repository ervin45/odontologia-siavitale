package controllers;
import org.hibernate.Session;

import javafx.fxml.FXML;

public class NuevoServicioOdontologicoController{	
	private Ventanas ProgramaPrincipal = new Ventanas();
			
	public NuevoServicioOdontologicoController(){			
	}
		
	@FXML
	private void initialize(){	
		System.out.println("en controlador de nuevo servicio odontologico");
	}	
	
	private Session openSesion(){		
		Session sesion = Principal.fabrica.getCurrentSession();
		sesion.beginTransaction();		
		return sesion;
	}
	
	private void closeSesion(Session sesion){		
		sesion.getTransaction().commit();
	}	
	
	public void setProgramaPrincipal(Ventanas ProgramaPrincipal) {
		 System.out.println("Controlador nuevo servicio odontologico - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
}
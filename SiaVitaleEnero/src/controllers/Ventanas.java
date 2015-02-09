package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Ventanas extends Application{
	
	private Stage stagePrincipal;
	
    private AnchorPane rootPane;
	
	@Override
    public void start(Stage stagePrincipal) throws Exception {
    	System.out.println("pruebaVentanas");
    	this.stagePrincipal = stagePrincipal;
        mostrarVentanaCronogramaCita();       
    } 
	
	public void mostrarVentanaCronogramaCita(){
		System.out.println("mostrar ventana cronograma cita en ventanas.java");
    	
    	try{
    		FXMLLoader loader = new FXMLLoader(Ventanas.class.getResource("CitasOdontologicas.fxml"));
    		AnchorPane ventanaR = (AnchorPane) loader.load();
    		Stage ventana = new Stage();
    		ventana.initOwner(stagePrincipal);
    		ventana.initModality(Modality.WINDOW_MODAL);
    		Scene scene = new Scene(ventanaR);
            ventana.setScene(scene);
            CitasOdontologicasController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            ventana.show(); 
    	}catch(Exception e){
    		 //tratar la excepción
    	}
	}
	
	public void mostrarVentanaNuevaCita(){
		System.out.println("mostrar ventana nueva cita");
		
    	try{
    		FXMLLoader loader = new FXMLLoader(Ventanas.class.getResource("NuevaCitaOdontologica.fxml"));
    		AnchorPane ventanaR = (AnchorPane) loader.load();
    		Stage ventana = new Stage();
    		ventana.initOwner(stagePrincipal);
    		ventana.initModality(Modality.WINDOW_MODAL);
    		Scene scene = new Scene(ventanaR);
            ventana.setScene(scene);
            NuevaCitaOdontologicaController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            ventana.show(); 
    	}catch(Exception e){
    		 //tratar la excepción
    	}
	}
	
	public void mostrarVentanaEliminarCita(){
		System.out.println("mostrar ventana eliminar cita");
    	
    	try{
    		FXMLLoader loader = new FXMLLoader(Ventanas.class.getResource("EliminarCitaOdontologica.fxml"));
    		AnchorPane ventanaR = (AnchorPane) loader.load();
    		Stage ventana = new Stage();
    		ventana.initOwner(stagePrincipal);
    		ventana.initModality(Modality.WINDOW_MODAL);
    		Scene scene = new Scene(ventanaR);
            ventana.setScene(scene);
            EliminarCitaOdontologicaController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            ventana.show(); 
    	}catch(Exception e){
    		 //tratar la excepción
    	}
	}
}

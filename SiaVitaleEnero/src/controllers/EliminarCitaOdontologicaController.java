package controllers;
import org.hibernate.Query;
import org.hibernate.Session;
import data.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EliminarCitaOdontologicaController{	
	
	@FXML
	private Button bEliminarCita;
	
	@FXML
	private Button bCancelar;
	
	private Ventanas ProgramaPrincipal = new Ventanas();
			
	public EliminarCitaOdontologicaController(){		
	}
		
	@FXML
	private void initialize(){	
		System.out.println("en controlador de eliminar cita");
	}
	
	@FXML
	private void actionEliminarCita(){
		System.out.println("boton eliminar cita");	
		ContextoCronograma.getInstance().setBanderaEliminarCita(true);
		ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
		Stage stage = (Stage) bEliminarCita.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void actionCancelar(){
		System.out.println("boton cancelar eliminar cita");	
		ContextoCronograma.getInstance().setBanderaEliminarCita(false);
		ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
		Stage stage = (Stage) bEliminarCita.getScene().getWindow();
		stage.close();
		
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
		 System.out.println("Controlador eliminar cita odontologica - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
}

//tfObservacion.focusedProperty().addListener(new ChangeListener<Boolean>(){
//@Override
//public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
//	if (cbPaciente.getSelectionModel().getSelectedIndex()!=-1){
//		btAgregarCita.setDisable(false);
//		System.out.println("listo");
//	}else
//		System.out.println("no hay pacient selec");
//}
//});		

//cbPaciente.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
//@Override
//public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {						
//	if (!opcionPaciente.isEmpty()){
//		System.out.println("Pacientbt: "+arg2.intValue());
//	}
//	if (!tfObservacion.getText().equals("")){
//		btAgregarCita.setDisable(false);
//	}
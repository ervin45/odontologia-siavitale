package controllers;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Doctor;
import data.Paciente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NuevaCitaOdontologicaController{	
	
	@FXML
	private ChoiceBox cbPaciente;
	
	@FXML
	private TextField tfObservacion;
	
	@FXML
	private Button bAgregarCita;
	
	@FXML
	private Button bCancelar;
	
	ObservableList<Paciente> PacienteList = FXCollections.observableArrayList();
	private ObservableList<String> opcionPaciente = FXCollections.observableArrayList();	
	
	private Ventanas ProgramaPrincipal = new Ventanas();
			
	public NuevaCitaOdontologicaController(){			
	}
		
	@FXML
	private void initialize(){	
		System.out.println("en controlador de nueva cita");
		Session sesion1 = openSesion();
		Query queryResultDoctores = sesion1.createQuery("from Paciente");	
				
		PacienteList = FXCollections.observableArrayList(queryResultDoctores.list());		
		for (int r=0;r<PacienteList.size();r++){
			opcionPaciente.add(PacienteList.get(r).getPersona().getNombres() +" "+PacienteList.get(r).getPersona().getApellidos());
		}
		closeSesion(sesion1);
		cbPaciente.setItems(opcionPaciente);	
	}
	
	@FXML
	private void actionAgregarCita(){
		System.out.println("boton agregar cita");
		ContextoCronograma.getInstance().setBanderaVentNuevaCita(false);
		if (cbPaciente.getSelectionModel().getSelectedIndex()!=-1){
			System.out.println("listo "+cbPaciente.getSelectionModel().getSelectedIndex());
			System.out.println("listo "+PacienteList.get(cbPaciente.getSelectionModel().getSelectedIndex()).getPersona().getNombres()+", "+PacienteList.get(cbPaciente.getSelectionModel().getSelectedIndex()).getPersona().getApellidos());
			ContextoCronograma.getInstance().setPaciente(PacienteList.get(cbPaciente.getSelectionModel().getSelectedIndex()));
			System.out.println("paciente: "+ContextoCronograma.getInstance().getPaciente().getPersona().getNombres()+"   ......");
			ContextoCronograma.getInstance().setBanderaVentana(false);
			ContextoCronograma.getInstance().setObservacionCita(tfObservacion.getText());
			Stage stage = (Stage) bAgregarCita.getScene().getWindow();
			stage.close();
		}else
			System.out.println("seleccione paciente");
		
	}
	
	@FXML
	private void actionCancelar(){
		System.out.println("boton cancelar nueva cita");	
		ContextoCronograma.getInstance().setBanderaVentNuevaCita(false);
		Stage stage = (Stage) bCancelar.getScene().getWindow();
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
		 System.out.println("Controlador nueva cita odontologica - setPP");
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
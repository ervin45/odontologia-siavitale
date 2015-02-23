package controllers;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import data.CitaOdontologica;
import data.Paciente;
import data.Personal;
import data.ServicioOdontologico;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.util.Callback;

public class FacturarCitaOdontologicaController{	
	
	private Ventanas ProgramaPrincipal = new Ventanas();
	
	private ObservableList<CitaOdontologica> citaOdontologicaList = FXCollections.observableArrayList();
	private ObservableList<CitaOdontologica> citaOdontologicaSeleccionadaList = FXCollections.observableArrayList();
	
	@FXML
	private ChoiceBox cbServicioOdontologico;
	private ObservableList<ServicioOdontologico> serviciosOdontologicosListCB = FXCollections.observableArrayList();
	private ObservableList<String> opcionServicioOdontologico = FXCollections.observableArrayList();	
	
	@FXML
	private TableView<CitaOdontologica> tvCitas; 
	
	@FXML
	private TableColumn<CitaOdontologica,String> tcDescripcion;
	
	@FXML
	private TableView<CitaOdontologica> tvCitasSeleccionadas;
	
	@FXML
	private TableColumn<CitaOdontologica,String> tcDescripcionCS;	
		
	private ObservableList<ServicioOdontologico> servicioOdontologicoListItems = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<String,String> tcDescripcionSO;
	
	@FXML
	private TableView<ServicioOdontologico> tvItems;
	
	@FXML
	private TableColumn<ServicioOdontologico,String> tcDescripcionItem;
	
	@FXML
	private TableColumn<ServicioOdontologico,String> tcCostoItem;
	
	private ObservableList<Double> precioList = FXCollections.observableArrayList();
	
	@FXML
	private Label lFecha;
	
	@FXML
	private TextField tfCedulaPaciente;
	
	@FXML
	private Button bMostrarCitas;
	
	@FXML
	private Button bFacturar;
		
	private String hoy;
	
	private double totalFactura;
	
	@FXML
	private Label lValorTotal;
	
	public FacturarCitaOdontologicaController(){	}
		
	@FXML
	private void initialize(){				
		hoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		System.out.println("en controlador de Facturar cita "+hoy);
		lFecha.setText("FECHA "+hoy);
		
		tfCedulaPaciente.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 System.out.println("tf cp: "+tfCedulaPaciente.getText());	
				 if (tfCedulaPaciente.getText().equals("")){
					 tvCitas.getSelectionModel().clearSelection();
					 bMostrarCitas.setDisable(true);
			 	 }else
					 bMostrarCitas.setDisable(false);					
			 }
		});		
		
		tvCitas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue){		        
		    	if(tvCitas.getSelectionModel().getSelectedItem() != null){  
		    		TableViewSelectionModel<CitaOdontologica> selectionModel = tvCitas.getSelectionModel();
		    		ObservableList selectedCells = selectionModel.getSelectedCells();
		    		TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		    		Object val = tablePosition.getTableColumn().getCellData(newValue);
		    		System.out.println("val aqui en tvCitas "+tablePosition.getRow()+"  new value "+tablePosition+ " lista "+citaOdontologicaList.get(tablePosition.getRow()));
		    		posicionarEnCitasSeleccionadas(citaOdontologicaList.get(tablePosition.getRow()));
		    	}
		}});   
		
		tvCitasSeleccionadas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        	    			    	
		    	if ( (tvCitasSeleccionadas.getSelectionModel().getSelectedItem() != null) &&
		    		 (tvCitasSeleccionadas.getSelectionModel().getSelectedIndex() != -1) ){
			    		TableViewSelectionModel<CitaOdontologica> selectionModel = tvCitasSeleccionadas.getSelectionModel();
			    		ObservableList selectedCells = selectionModel.getSelectedCells();
			    		TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			    		Object val = tablePosition.getTableColumn().getCellData(newValue);
			    		System.out.println("CITA SELECCIONADA A BORRAR val "+val+"  new value "+tablePosition);			    		
			    		if (!ContextoCronograma.getInstance().getBanderaTablaEliminarCitaSeleccionada())
			    			eliminarCitaSeleccionada(tablePosition.getRow());
			    		tvCitasSeleccionadas.getSelectionModel().clearSelection();
		    	}
		    	if (tvCitasSeleccionadas.getSelectionModel().getSelectedIndex() == -1){
		    		tvCitasSeleccionadas.getSelectionModel().clearSelection();
		    		ContextoCronograma.getInstance().setBanderaTablaEliminarCitaSeleccionada(false);
		    	}
		}});   

		tvItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        
		    	if ( (tvItems.getSelectionModel().getSelectedItem() != null) && 
		    	  (tvItems.getSelectionModel().getSelectedIndex() != -1) ){  
		    		TableViewSelectionModel<ServicioOdontologico> selectionModel = tvItems.getSelectionModel();
		    		ObservableList selectedCells = selectionModel.getSelectedCells();
		    		TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		    		Object val = tablePosition.getTableColumn().getCellData(newValue);
		    		System.out.println("ITEM SELECCIONADO "+servicioOdontologicoListItems.get(tablePosition.getRow())+"  new value "+tablePosition);
		    		
		    		if (!ContextoCronograma.getInstance().getBanderaTablaEliminarItemSeleccionado())
		    			eliminarItemSeleccionado(tablePosition.getRow());
		    		tvItems.getSelectionModel().clearSelection();
		    	}
		    	if (tvItems.getSelectionModel().getSelectedIndex() == -1){
		    		tvItems.getSelectionModel().clearSelection();
		    		ContextoCronograma.getInstance().setBanderaTablaEliminarItemSeleccionado(false);
		    	}
		}}); 
		
		Session sesion1 = openSesion();
		Query queryResultDoctores = sesion1.createQuery("from ServicioOdontologico");	
				
		serviciosOdontologicosListCB = FXCollections.observableArrayList(queryResultDoctores.list());		
		for (int r=0;r<serviciosOdontologicosListCB.size();r++){
			opcionServicioOdontologico.add(serviciosOdontologicosListCB.get(r).getNombre());
		}
		closeSesion(sesion1);
		cbServicioOdontologico.setItems(opcionServicioOdontologico);
		cbServicioOdontologico.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2){
				System.out.println("servicio odontologico: "+arg2.intValue());	
				posicionarItemSeleccionado(arg2.intValue());
		}});			
	}
	
	private void posicionarItemSeleccionado(int pos){
		System.out.println("pos:  "+pos);
		servicioOdontologicoListItems.add(serviciosOdontologicosListCB.get(pos));
	
		tvItems.setItems(servicioOdontologicoListItems);
		
		precioList.add(serviciosOdontologicosListCB.get(pos).getPrecio());
		totalFactura += serviciosOdontologicosListCB.get(pos).getPrecio();
		
		lValorTotal.setText(String.valueOf(totalFactura));
		
		validarBotonFacturar();
		
		tcDescripcionItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<ServicioOdontologico, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getNombre()));
			}			
		});	
		tcCostoItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<ServicioOdontologico, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getPrecio()));
			}			
		});	
	}
	
	private void posicionarEnCitasSeleccionadas(CitaOdontologica cita){		
		if (citaOdontologicaSeleccionadaList.size()==0)
			citaOdontologicaSeleccionadaList.add(citaOdontologicaSeleccionadaList.size(),cita);
		else if (citaOdontologicaSeleccionadaList.size()>0)
			citaOdontologicaSeleccionadaList.add(citaOdontologicaSeleccionadaList.size(),cita);
		
		validarBotonFacturar();
		
		tvCitasSeleccionadas.setItems(citaOdontologicaSeleccionadaList);
		tcDescripcionCS.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getFecha()));
			}			
		});
	}
	
	private void eliminarItemSeleccionado(int posEliminar){
		ContextoCronograma.getInstance().setBanderaTablaEliminarItemSeleccionado(true);
		System.out.println("eliminar pos de la lista de items seleccionados: "+posEliminar+" // precioList "+precioList.get(posEliminar));
		
		totalFactura -= servicioOdontologicoListItems.get(posEliminar).getPrecio();
		servicioOdontologicoListItems.remove(posEliminar);
		System.out.println("* * * * * * * total factura  "+totalFactura);
		lValorTotal.setText(String.valueOf(totalFactura));
		
		validarBotonFacturar();
		
		tcDescripcionItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<ServicioOdontologico, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getNombre()));
			}			
		});	
		tcCostoItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<ServicioOdontologico, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getPrecio()));
			}			
		});	
		tvItems.getSelectionModel().clearSelection();
	}
	
	private void eliminarCitaSeleccionada(int posEliminar){
		ContextoCronograma.getInstance().setBanderaTablaEliminarCitaSeleccionada(true);
		System.out.println("eliminar pos de la lista de citas seleccionadas: "+posEliminar);
			
		citaOdontologicaSeleccionadaList.remove(posEliminar);	
		
		validarBotonFacturar();
		
		tcDescripcionCS.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getFecha()));
			}			
		});		
		tvCitasSeleccionadas.getSelectionModel().clearSelection();
	}
	
	private void validarBotonFacturar(){
		
		if ((servicioOdontologicoListItems.size()!=0) && (citaOdontologicaSeleccionadaList.size()!=0))
			bFacturar.setDisable(false);
		else
			bFacturar.setDisable(true);
	}
	
	@FXML
	private void actionMostrarCita(){
		System.out.println("Boton buscar citas de esa cédula "+tfCedulaPaciente.getText());
		
		Session sesion2 = openSesion();
		Personal persona;
		Query query2 = sesion2.createQuery("from Personal where Cedula =:ced");
		query2.setString("ced", tfCedulaPaciente.getText());
		query2.setMaxResults(1);
		persona = (Personal) query2.uniqueResult();
		System.out.println("el nombre del posible a facturar: "+persona.getApellidos() +" "+persona.getNombres());
		if (persona!=null){
			Query query3 = sesion2.createQuery("from Paciente where idPersona =:id");
			query3.setInteger("id", persona.getId());
			query3.setMaxResults(1);
			Paciente paciente = (Paciente) query3.uniqueResult();
			
			if (paciente!=null){
				Query query4 = sesion2.createQuery("from CitaOdontologica where idPaciente =:idp");
				query4.setInteger("idp", paciente.getId());					
				citaOdontologicaList = FXCollections.observableArrayList(query4.list());

				tvCitas.setItems(citaOdontologicaList);
				tcDescripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
						return new SimpleStringProperty(String.valueOf(arg0.getValue().getFecha()));
					}			
				});
			}
		}	
		closeSesion(sesion2);	
	}
	
	@FXML
	private void actionFacturar(){
		System.out.println("facturar boton");
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
		 System.out.println("Controlador facturar cita odontologica - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
	
//	@FXML
//	private void actionAgregarItem(){
//		System.out.println("agregar a tabla");
//	}
	
//	private void actionEliminarCita(){
//		System.out.println("boton eliminar cita");	
//		ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
//		ContextoCronograma.getInstance().setBanderaEliminarCita(true);
//		Stage stage = (Stage) bEliminarCita.getScene().getWindow();
//		stage.close();
//	}
//	
//	@FXML
//	private void actionCancelar(){
//		System.out.println("boton cancelar cronograma cita");	
//		ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
//		Stage stage = (Stage) bCancelar.getScene().getWindow();
//		stage.close();		
//	}
}
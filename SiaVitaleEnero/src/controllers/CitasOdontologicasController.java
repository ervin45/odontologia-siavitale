package controllers;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.CitaOdontologica;
import data.Doctor;
import eu.schudt.javafx.controls.calendar.DatePicker;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CitasOdontologicasController{	
			
	@FXML
	private TableView<CitaOdontologica> tvLunes;
	@FXML
	private TableView<CitaOdontologica> tvMartes;
	@FXML
	private TableView<CitaOdontologica> tvMiercoles;
	@FXML
	private TableView<CitaOdontologica> tvJueves;
	@FXML
	private TableView<CitaOdontologica> tvViernes;	
	@FXML
	private TableColumn<CitaOdontologica, String> tcLunes;	
	@FXML
	private TableColumn<CitaOdontologica, String> tcMartes;
	@FXML	
	private TableColumn<CitaOdontologica, String> tcMiercoles;
	@FXML
	private TableColumn<CitaOdontologica, String> tcJueves;
	@FXML
	private TableColumn<CitaOdontologica, String> tcViernes;
	
	@FXML
	private TableView tvHorario;
	
	@FXML
	private TableColumn tcHora;
	
	@FXML
	private ChoiceBox cbDoctor;
	
	@FXML	
	private GridPane gpdvFecha;
	
	private DatePicker dpFecha;
	
	@FXML
	private Button bGuardarCitaBD;
	
	@FXML
	private Button bCancelar;
	
	@FXML
	private Label lMsjRegistroExito;
	
	@FXML
	private Label lMsjEliminacionExito;
	
	@FXML
	private Label lMsjSeleccioneFecha;
	
	@FXML
	private Label lMsjSeleccioneDoctor;
	
	@FXML
	private Label lMsjSeleccioneTurno;
	
	@FXML
	private Button btAM;
	
	@FXML
	private Button btPM;
	
	intervalosHora ih;
	
	ObservableList<intervalosHora> horaList = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaList = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListLunes = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListMartes = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListMiercoles = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListJueves = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListViernes = FXCollections.observableArrayList();
		
	private String posNuevaLunes="", posNuevaMartes="", posNuevaMiercoles="", posNuevaJueves="", posNuevaViernes="";
	private List<String> posCitasVienenBDLunes = new LinkedList<String>(Arrays.asList("0","0","0","0","0","0","0","0","0"));
	private List<String> posCitasVienenBDMartes = new LinkedList<String>(Arrays.asList("0","0","0","0","0","0","0","0","0"));
	private List<String> posCitasVienenBDMiercoles = new LinkedList<>(Arrays.asList("0","0","0","0","0","0","0","0","0"));
	private List<String> posCitasVienenBDJueves = new LinkedList<String>(Arrays.asList("0","0","0","0","0","0","0","0","0"));
	private List<String> posCitasVienenBDViernes = new LinkedList<String>(Arrays.asList("0","0","0","0","0","0","0","0","0"));
	
	ObservableList<Doctor> DoctorList = FXCollections.observableArrayList();
	private ObservableList<String> opcionDoctor = FXCollections.observableArrayList();	
	
	private boolean turno=false;
	
	private int idDoctorSeleccionado=0;
	
	private List<String> semana = new LinkedList<>();
	private SimpleDateFormat patron = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat patroND = new SimpleDateFormat("EE");
	private SimpleDateFormat patronHora = new SimpleDateFormat("h");
	private SimpleDateFormat patronMinuto = new SimpleDateFormat("mm");
	
	NuevaCitaOdontologicaV NuevaCitaOVentana;
	
	private Ventanas ProgramaPrincipal = new Ventanas();
	
	private int posCitaSelec = 0, posDoctorSeleccionado = 0;
		
	public CitasOdontologicasController(){			
	}
		
	@FXML
	private void initialize(){	
				
		dpFecha = new DatePicker();		
		dpFecha.setDateFormat( new SimpleDateFormat("dd-MM-yyyy"));
		dpFecha.getCalendarView().setShowTodayButton(false);
		dpFecha.getCalendarView().setShowWeeks(false);
		dpFecha.getStylesheets().add(getClass().getResource("DatePicker.css").toExternalForm());
		gpdvFecha.add(dpFecha,0,0);
		tcHora.setCellValueFactory(new PropertyValueFactory<intervalosHora, String>("intervalo"));
				
		tvHorario.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<intervalosHora>(){
			@Override
			public void changed(ObservableValue<? extends intervalosHora> arg0, intervalosHora arg1, intervalosHora arg2) {
				if (arg2 != null){
					System.out.println("Seleccione intervalo de horas:  "+arg2.getIntervalo());
				}    
			}
		});	
		
		Session sesion1 = openSesion();
		Query queryResultDoctores = sesion1.createQuery("from Doctor");	
				
		DoctorList = FXCollections.observableArrayList(queryResultDoctores.list());		
		for (int r=0;r<DoctorList.size();r++){
			opcionDoctor.add(DoctorList.get(r).getPersona().getNombres() +" "+DoctorList.get(r).getPersona().getApellidos());
		}
		closeSesion(sesion1);
		cbDoctor.setItems(opcionDoctor);
		cbDoctor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {						
				if (!opcionDoctor.isEmpty()){
					System.out.println("0: "+arg2.intValue());
					posDoctorSeleccionado=arg2.intValue();
					idDoctorSeleccionado=DoctorList.get(arg2.intValue()).getId();					
					posNuevaLunes=posNuevaMartes=posNuevaMiercoles=posNuevaJueves=posNuevaViernes="";
					lMsjRegistroExito.setVisible(false);
					lMsjEliminacionExito.setVisible(false);
					lMsjSeleccioneDoctor.setVisible(false);				}				
		}});		
	}	

	@FXML
	private void actionTurnoPM(){
		ContextoCronograma.getInstance().setBanderaInicio_cbDr(true);
		lMsjRegistroExito.setVisible(false);	lMsjEliminacionExito.setVisible(false);
		lMsjSeleccioneFecha.setVisible(false);	bGuardarCitaBD.setDisable(true);
		lMsjSeleccioneTurno.setVisible(false);
		
		horaList.clear();cargarHorasPM();tvHorario.setItems(horaList);
		posNuevaLunes=posNuevaMartes=posNuevaMiercoles=posNuevaJueves=posNuevaViernes="";
		turno=true;		
		limpiezaTabla();
		
		if (dpFecha.getSelectedDate()!=null){
			semana.clear();
			cargarSemana(dpFecha.getSelectedDate());
		}else{
			lMsjSeleccioneFecha.setVisible(true);
		}
	}
	
	@FXML
	private void actionTurnoAM(){
		ContextoCronograma.getInstance().setBanderaInicio_cbDr(true);
		lMsjRegistroExito.setVisible(false);	lMsjEliminacionExito.setVisible(false);		
		lMsjSeleccioneFecha.setVisible(false);	bGuardarCitaBD.setDisable(true);
		lMsjSeleccioneTurno.setVisible(false);
		horaList.clear();cargarHorasAM();tvHorario.setItems(horaList);
		posNuevaLunes=posNuevaMartes=posNuevaMiercoles=posNuevaJueves=posNuevaViernes="";
		turno=false;		
		limpiezaTabla();
		if (dpFecha.getSelectedDate()!=null){
			semana.clear();
			cargarSemana(dpFecha.getSelectedDate());
		}else{
			lMsjSeleccioneFecha.setVisible(true);
		}
	}	

	private void cargarSemana(Date fechaSeleccionada){
		SimpleDateFormat numeroDia = new SimpleDateFormat("u");
			
		Calendar c = Calendar.getInstance();
		c.setTime(fechaSeleccionada);
		
		switch (numeroDia.format(fechaSeleccionada)){
			case "1":	semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						for (int t=0;t<semana.size();t++)   System.out.print(" "+semana.get(t));
						break;						
			case "2":	c.add(Calendar.DAY_OF_MONTH, -1);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						for (int t=0;t<semana.size();t++)	System.out.print(" "+semana.get(t));					
						break;
			case "3":	c.add(Calendar.DAY_OF_MONTH, -2);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						System.out.println("miercoles .... lista : "+semana.size());
						for (int t=0;t<semana.size();t++)	System.out.print(" "+semana.get(t));						
						break;
			case "4":	c.add(Calendar.DAY_OF_MONTH, -3);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						for (int t=0;t<semana.size();t++)	System.out.print("  "+semana.get(t));						
						break;
			case "5":	c.add(Calendar.DAY_OF_MONTH, -4);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						for (int t=0;t<semana.size();t++)	System.out.print("  "+semana.get(t));						
						break;				
		}		
		System.out.println("");	consultaBDSemana();		
	}
	
	private void algoritmo(Calendar c, int l){		
		for (int y=1;y<=l;y++){
			c.add(Calendar.DAY_OF_MONTH, 1);
			semana.add(patron.format(c.getTime()));
		}			
	}
	
	private void consultaBDSemana(){		
		try{
			Session sesion = openSesion();
			Query queryr= sesion.createQuery("from CitaOdontologica where (fecha >= :fi and fecha <= :ff) and AMPM = :t and idDoctor = :doc");
			queryr.setString("fi", semana.get(0));
			queryr.setString("ff", semana.get(5));
			queryr.setInteger("doc", idDoctorSeleccionado);
			if (turno)
				queryr.setString("t", "PM");
			else if (!turno)
				queryr.setString("t", "AM");
						
			citaList = FXCollections.observableArrayList(queryr.list());
			closeSesion(sesion);
		}catch (HibernateException e1){
			e1.printStackTrace();
		}
		System.out.println("citaList: "+citaList.size());
		
		Iterator<CitaOdontologica> i = citaList.iterator();
		
		while(i.hasNext()){
			CitaOdontologica co=i.next();
			ContextoCronograma.getInstance().setBanderaLimpiezaTabla(false);
			if (patroND.format(co.getFecha()).compareTo("lun")==0){		
				mostrarCitaSegunIntervaloHora(co,"lunes");		
				tvLunes.setItems(citaListLunes);
			}else if (patroND.format(co.getFecha()).compareTo("mar")==0){		
				mostrarCitaSegunIntervaloHora(co,"martes");		
				tvMartes.setItems(citaListMartes);				
			}else if (patroND.format(co.getFecha()).compareTo("mié")==0){		
				mostrarCitaSegunIntervaloHora(co,"miercoles");			
				tvMiercoles.setItems(citaListMiercoles);				
			}else if (patroND.format(co.getFecha()).compareTo("jue")==0){				
				mostrarCitaSegunIntervaloHora(co,"jueves");
				tvJueves.setItems(citaListJueves);				
			}else if (patroND.format(co.getFecha()).compareTo("vie")==0){						
				mostrarCitaSegunIntervaloHora(co,"viernes");
				tvViernes.setItems(citaListViernes);
			}			
		}
		actualizarCells();
	}
	
	private void mostrarCitaSegunIntervaloHora(CitaOdontologica co, String dia){
		Iterator<intervalosHora> ih = horaList.iterator();
		int contador=0;
		while (ih.hasNext()){
			intervalosHora obj = ih.next();
			String[] horas = obj.getIntervalo().split("-");
			String[] inicio = horas[0].split(":");
			String[] fin = horas[1].split(":");
			
			if ( ( patronHora.format(co.getFecha()).compareTo(inicio[0]) == 0 ) &&
					( patronHora.format(co.getFecha()).compareTo(fin[0]) == 0 ) &&
					( Integer.parseInt(patronMinuto.format(co.getFecha())) >= Integer.parseInt(inicio[1])) && 
					( Integer.parseInt(patronMinuto.format(co.getFecha())) < Integer.parseInt(fin[1]))	) {
						
						switch(dia){
							case "lunes": citaListLunes.set(contador, co);	posCitasVienenBDLunes.set(contador, "1");
										  break;
							case "martes": citaListMartes.set(contador, co); posCitasVienenBDMartes.set(contador, "1");											  
										   break;
							case "miercoles": citaListMiercoles.set(contador, co);	posCitasVienenBDMiercoles.set(contador, "1");	
										      break;
							case "jueves": citaListJueves.set(contador, co);	posCitasVienenBDJueves.set(contador, "1");	
										   break;
							case "viernes": citaListViernes.set(contador, co);	posCitasVienenBDViernes.set(contador, "1");   
											break;
						}				
			}else if ( ( patronHora.format(co.getFecha()).compareTo(inicio[0]) == 0 ) && 
					( Integer.parseInt(patronHora.format(co.getFecha())) == Integer.parseInt(fin[0])-1 ) &&
					( Integer.parseInt(patronMinuto.format(co.getFecha()))>=30 && Integer.parseInt(patronMinuto.format(co.getFecha()))<=59 )){ 
						switch(dia){
							case "lunes": citaListLunes.set(contador, co);	posCitasVienenBDLunes.set(contador, "1");
										  break;
							case "martes": citaListMartes.set(contador, co);	posCitasVienenBDMartes.set(contador, "1");	
											break;
							case "miercoles": citaListMiercoles.set(contador, co);	posCitasVienenBDMiercoles.set(contador, "1");	
												break;
							case "jueves": citaListJueves.set(contador, co);	posCitasVienenBDJueves.set(contador, "1");	
											break;
							case "viernes": citaListViernes.set(contador, co);	posCitasVienenBDViernes.set(contador, "1");
											break;
						}	
			}
			contador++;						
		}
	}
	
	private void EliminarCita(){
		ContextoCronograma.getInstance().setBanderaVentEliminarCita(true);
		ProgramaPrincipal.mostrarVentanaEliminarCita();		
	}
	
	private void actualizarCells(){			
			tvLunes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    @Override
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			       
			        if(tvLunes.getSelectionModel().getSelectedItem() != null){    
			           TableViewSelectionModel<CitaOdontologica> selectionModel = tvLunes.getSelectionModel();
			           ObservableList selectedCells = selectionModel.getSelectedCells();
			           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			           Object val = tablePosition.getTableColumn().getCellData(newValue);
			           posCitaSelec = tablePosition.getRow();	
			           ContextoCronograma.getInstance().setDiaClick("lunes");
			           lMsjEliminacionExito.setVisible(false);
			           lMsjRegistroExito.setVisible(false);
			           if (val.toString().compareTo("     ")!=0){
			        	   	if (!ContextoCronograma.getInstance().getBanderaVentEliminarCita()){			        	   		
			        	   		EliminarCita();
			        	   	}
			           }else  if (val.toString().compareTo("     ")==0){			           	   
			           	   if (!ContextoCronograma.getInstance().getBanderaVentNuevaCita())
			           		   AgregarCita();
			           }
			         }
			    }
		 });	
	 
		 tvMartes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    @Override
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			        
			    	if(tvMartes.getSelectionModel().getSelectedItem() != null){    
			           TableViewSelectionModel<CitaOdontologica> selectionModel = tvMartes.getSelectionModel();
			           ObservableList selectedCells = selectionModel.getSelectedCells();
			           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			           Object val = tablePosition.getTableColumn().getCellData(newValue);
			           posCitaSelec = tablePosition.getRow();
			           ContextoCronograma.getInstance().setDiaClick("martes");
			           lMsjEliminacionExito.setVisible(false);
			           lMsjRegistroExito.setVisible(false);
			           if (val.toString().compareTo("     ")!=0){
			        	    if (!ContextoCronograma.getInstance().getBanderaVentEliminarCita()){
			        	    	EliminarCita();
			           		}
			           }else  if (val.toString().compareTo("     ")==0){
			        	   if (!ContextoCronograma.getInstance().getBanderaVentNuevaCita())
			        		   AgregarCita();
			           }
			         }
			    }
		 });
	 
		 tvMiercoles.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    @Override
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			        			    	
			        if(tvMiercoles.getSelectionModel().getSelectedItem() != null) {    
			           TableViewSelectionModel<CitaOdontologica> selectionModel = tvMiercoles.getSelectionModel();
			           ObservableList selectedCells = selectionModel.getSelectedCells();
			           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			           Object val = tablePosition.getTableColumn().getCellData(newValue);
			           posCitaSelec = tablePosition.getRow();
			           ContextoCronograma.getInstance().setDiaClick("miercoles");
			           lMsjEliminacionExito.setVisible(false);
			           lMsjRegistroExito.setVisible(false);
			           if (val.toString().compareTo("     ")!=0){
			        	   	if (!ContextoCronograma.getInstance().getBanderaVentEliminarCita()){
			        	   		EliminarCita();
			        	   	}
			           }else  if (val.toString().compareTo("     ")==0){		        	   
			        	   if (!ContextoCronograma.getInstance().getBanderaVentNuevaCita())
			        		   AgregarCita();
			           }
			         }
			    }
		 });
	 
		 tvJueves.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    @Override
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			       
			    	if(tvJueves.getSelectionModel().getSelectedItem() != null){    
			           TableViewSelectionModel<CitaOdontologica> selectionModel = tvJueves.getSelectionModel();
			           ObservableList selectedCells = selectionModel.getSelectedCells();
			           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			           Object val = tablePosition.getTableColumn().getCellData(newValue);
			           posCitaSelec = tablePosition.getRow();
			           ContextoCronograma.getInstance().setDiaClick("jueves");
			           lMsjEliminacionExito.setVisible(false);
			           lMsjRegistroExito.setVisible(false);
			           if (val.toString().compareTo("     ")!=0){
			        	    if (!ContextoCronograma.getInstance().getBanderaVentEliminarCita()){
			        	    	EliminarCita();
			        	    }
			           }else  if (val.toString().compareTo("     ")==0){  	   
			        	   if (!ContextoCronograma.getInstance().getBanderaVentNuevaCita())
			        		   AgregarCita();
			           }
			        }
			    }
		 });
	 
		 tvViernes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    @Override
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			        
			    	if(tvViernes.getSelectionModel().getSelectedItem() != null){    
			           TableViewSelectionModel<CitaOdontologica> selectionModel = tvViernes.getSelectionModel();
			           ObservableList selectedCells = selectionModel.getSelectedCells();
			           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			           Object val = tablePosition.getTableColumn().getCellData(newValue);
			           posCitaSelec = tablePosition.getRow();
			           ContextoCronograma.getInstance().setDiaClick("viernes");
			           lMsjEliminacionExito.setVisible(false);
			           lMsjRegistroExito.setVisible(false);
			           if (val.toString().compareTo("     ")!=0){
			        	   	if (!ContextoCronograma.getInstance().getBanderaVentEliminarCita()){
			        	   		EliminarCita();
			        	   	}
			           }else  if (val.toString().compareTo("     ")==0){			        	  
			        	   if (!ContextoCronograma.getInstance().getBanderaVentNuevaCita())
			        		   AgregarCita();
			           }
			        }
			    }
		 });
		
		 tcLunes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {

					if (arg0.getValue().getObservacion().compareTo("libre")!=0){
						if (arg0.getValue().getFecha().getDay()==1){
							return new SimpleStringProperty(""+arg0.getValue().getObservacion());
						}else return new SimpleStringProperty("     ");
					}else return new SimpleStringProperty("     ");
				}			
		});
		tcMartes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
					
					if (arg0.getValue().getObservacion().compareTo("libre")!=0){
						if (arg0.getValue().getFecha().getDay()==2){
							return new SimpleStringProperty(""+arg0.getValue().getObservacion());
						}else return new SimpleStringProperty("     ");
					}else return new SimpleStringProperty("     ");
					}			
		});	
		tcMiercoles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
			
				if (arg0.getValue().getObservacion().compareTo("libre")!=0){	
					if (arg0.getValue().getFecha().getDay()==3){
						return new SimpleStringProperty(""+arg0.getValue().getObservacion());
					}else return new SimpleStringProperty("     ");
				}else return new SimpleStringProperty("     ");
				}			
		});	
		tcJueves.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
								
				if (arg0.getValue().getObservacion().compareTo("libre")!=0){
					if (arg0.getValue().getFecha().getDay()==4){
						return new SimpleStringProperty(""+arg0.getValue().getObservacion());
					}else return new SimpleStringProperty("     ");
				}else return new SimpleStringProperty("     ");
			}			
		});	
		tcViernes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
				
				if (arg0.getValue().getObservacion().compareTo("libre")!=0){
					if (arg0.getValue().getFecha().getDay()==5){
						return new SimpleStringProperty(""+arg0.getValue().getObservacion());
					}else return new SimpleStringProperty("     ");
				}else return new SimpleStringProperty("     ");
			}			
		});					
	}	
	
	private void limpiezaTabla(){
		ContextoCronograma.getInstance().setBanderaLimpiezaTabla(true);
		if (ContextoCronograma.getInstance().getBanderaInicio_cbDr()){			
			citaListLunes.clear();		citaListMartes.clear();
			citaListMiercoles.clear();	citaListJueves.clear();	
			citaListViernes.clear();
			posCitasVienenBDLunes.clear(); 
			posCitasVienenBDMartes.clear(); posCitasVienenBDMiercoles.clear();
			posCitasVienenBDJueves.clear();posCitasVienenBDViernes.clear();
			
			posCitasVienenBDLunes = posCitasVienenBDMartes = posCitasVienenBDMiercoles = posCitasVienenBDJueves = posCitasVienenBDViernes = new LinkedList<String>(Arrays.asList("0","0","0","0","0","0","0","0","0"));
		
			CitaOdontologica oco = new CitaOdontologica();	oco.setObservacion("libre");
			for (int e=0;e<9;e++){
				citaListLunes.add(oco);		citaListMartes.add(oco);
				citaListMiercoles.add(oco);		citaListJueves.add(oco);
				citaListViernes.add(oco);			
			}
			tvLunes.setItems(citaListLunes);	tvMartes.setItems(citaListMartes);
			tvMiercoles.setItems(citaListMiercoles);	tvJueves.setItems(citaListJueves);
			tvViernes.setItems(citaListViernes);
		}	
	}  
	
	@FXML
	private void actionMouseEnter(){
		System.out.println(" -* - - - - - - mouse volvio a cronograma - - - - - - *-");
		
		if (dpFecha.getSelectedDate()!=null && cbDoctor.getSelectionModel().getSelectedIndex()!=-1){
			
//			if (ContextoCronograma.getInstance().getBanderaLimpiezaTabla())
//				lMsjSeleccioneTurno.setVisible(false);
//			else
				lMsjSeleccioneTurno.setVisible(false);
			
			lMsjSeleccioneDoctor.setVisible(false);
			lMsjSeleccioneFecha.setVisible(false);
			btAM.setDisable(false); btPM.setDisable(false);			
		}else if (dpFecha.getSelectedDate()==null && cbDoctor.getSelectionModel().getSelectedIndex()!=-1){
			lMsjSeleccioneFecha.setVisible(true);
			lMsjSeleccioneDoctor.setVisible(false);
			lMsjSeleccioneTurno.setVisible(false);
			btAM.setDisable(true); btPM.setDisable(true);
			limpiezaTabla();		
		}else if (cbDoctor.getSelectionModel().getSelectedIndex()==-1 && dpFecha.getSelectedDate()!=null){
			lMsjSeleccioneDoctor.setVisible(true);
			lMsjSeleccioneTurno.setVisible(false);
			lMsjSeleccioneFecha.setVisible(false);
			btAM.setDisable(true); btPM.setDisable(true);			
			limpiezaTabla();
		}
		
		tvLunes.getSelectionModel().clearSelection();		tvMartes.getSelectionModel().clearSelection();
		tvMiercoles.getSelectionModel().clearSelection();		tvJueves.getSelectionModel().clearSelection();
		tvViernes.getSelectionModel().clearSelection();
		
//		System.out.println(" *.*.*.*.* PROCESO NORMAL *.*.*.*.*");
//		System.out.format("%n lunes: ");
//		Iterator<CitaOdontologica> iLunes = citaListLunes.iterator();
//		while (iLunes.hasNext())	System.out.print(" - "+iLunes.next().getObservacion());
//	
//		System.out.format("%n martes: ");
//		Iterator<CitaOdontologica> iMartes = citaListMartes.iterator();
//		while (iMartes.hasNext())	System.out.print(" - "+iMartes.next().getObservacion());
//		
//		System.out.format("%n miercoles: ");
//		Iterator<CitaOdontologica> iMiercoles = citaListMiercoles.iterator();
//		while (iMiercoles.hasNext())	System.out.print(" - "+iMiercoles.next().getObservacion());
//		
//		System.out.format("%n jueves: ");
//		Iterator<CitaOdontologica> iJueves = citaListJueves.iterator();
//		while (iJueves.hasNext())	System.out.print(" - "+iJueves.next().getObservacion());
//		
//		System.out.format("%n viernes: ");
//		Iterator<CitaOdontologica> iViernes = citaListViernes.iterator();
//		while (iViernes.hasNext())	System.out.print(" - "+iViernes.next().getObservacion());
			
//		System.out.format("%n/-------/");
//		System.out.format("%n lunes: ");
//		for (int y=0;y<9;y++)	System.out.print(" - "+posCitasVienenBDLunes.get(y));
//		
//		System.out.format("%n martes");
//		for (int y=0;y<9;y++)	System.out.print(" - "+posCitasVienenBDMartes.get(y));
//		
//		System.out.format("%n miercoles");
//		for (int y=0;y<9;y++)	System.out.print(" - "+posCitasVienenBDMiercoles.get(y));
//		
//		System.out.format("%n jueves");
//		for (int y=0;y<9;y++)	System.out.print(" - "+posCitasVienenBDJueves.get(y));
//		
//		System.out.format("%n viernes");
//		for (int y=0;y<9;y++)	System.out.print(" - "+posCitasVienenBDViernes.get(y));
//		
//		System.out.format("%n/-------/");		
		
		if (ContextoCronograma.getInstance().getPaciente()!=null && !ContextoCronograma.getInstance().getBanderaVentana()){
			ContextoCronograma.getInstance().setBanderaVentana(true);			
			switch (ContextoCronograma.getInstance().getDiaClick()){
				case "lunes":   System.out.println("size lunes "+citaListLunes.size());
								posNuevaLunes=posNuevaLunes+""+posCitaSelec;	
								posCitasVienenBDLunes.set(posCitaSelec, "2");
								posicionarNuevaCita(citaListLunes, 0);
								break;
				case "martes":  System.out.println("size martes "+citaListMartes.size());
								posNuevaMartes=posNuevaMartes+""+posCitaSelec;	
								posCitasVienenBDMartes.set(posCitaSelec, "2");
								posicionarNuevaCita(citaListMartes, 1);
								break;
				case "miercoles":  System.out.println("size miercoles "+citaListMiercoles.size());
								   posNuevaMiercoles=posNuevaMiercoles+""+posCitaSelec;	
								   posCitasVienenBDMiercoles.set(posCitaSelec, "2");
								   posicionarNuevaCita(citaListMiercoles, 2);
								   break;
				case "jueves":  System.out.println("size jueves "+citaListJueves.size());
								posNuevaJueves=posNuevaJueves+""+posCitaSelec;	
								posCitasVienenBDJueves.set(posCitaSelec, "2");
								posicionarNuevaCita(citaListJueves, 3);
								break;
				case "viernes": System.out.println("size viernes "+citaListViernes.size());
								posNuevaViernes=posNuevaViernes+""+posCitaSelec;	
								posCitasVienenBDViernes.set(posCitaSelec, "2");
								posicionarNuevaCita(citaListViernes, 4);
								break;
			}
			bGuardarCitaBD.setDisable(false);
		}
		if (!ContextoCronograma.getInstance().getBanderaVentNuevaCita()||!ContextoCronograma.getInstance().getBanderaVentEliminarCita()) {
			tvLunes.getSelectionModel().clearSelection();
			tvMartes.getSelectionModel().clearSelection();
	        tvMiercoles.getSelectionModel().clearSelection();
	        tvJueves.getSelectionModel().clearSelection();
	        tvViernes.getSelectionModel().clearSelection();
		}
		
		if (ContextoCronograma.getInstance().getBanderaEliminarCita()){
			ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
			ContextoCronograma.getInstance().setBanderaEliminarCita(false);
			CitaOdontologica oco = new CitaOdontologica();	
			
			switch (ContextoCronograma.getInstance().getDiaClick()){
				case "lunes":	System.out.println("borrar del dia lunes "+citaListLunes.get(posCitaSelec));
								if (posCitasVienenBDLunes.get(posCitaSelec).equals("1"))
									eliminarCitaBD(citaListLunes.get(posCitaSelec));									
								posCitasVienenBDLunes.set(posCitaSelec,"0");
								citaListLunes.remove(posCitaSelec);							
								oco.setObservacion("libre");
								citaListLunes.add(posCitaSelec, oco);								
								break;
				case "martes":	System.out.println("borrar del dia martes "+citaListMartes.get(posCitaSelec));
								if (posCitasVienenBDMartes.get(posCitaSelec).equals("1"))
									eliminarCitaBD(citaListMartes.get(posCitaSelec));
								posCitasVienenBDMartes.set(posCitaSelec,"0");
								citaListMartes.remove(posCitaSelec);							
								oco.setObservacion("libre");
								citaListMartes.add(posCitaSelec, oco);
								break;
				case "miercoles":	System.out.println("borrar del dia miercoles "+citaListMiercoles.get(posCitaSelec));									
									if (posCitasVienenBDMiercoles.get(posCitaSelec).equals("1"))
										eliminarCitaBD(citaListMiercoles.get(posCitaSelec));
									posCitasVienenBDMiercoles.set(posCitaSelec,"0");
									citaListMiercoles.remove(posCitaSelec);
									oco.setObservacion("libre");
									citaListMiercoles.add(posCitaSelec, oco);
									break;
				case "jueves":	System.out.println("borrar del dia jueves "+citaListJueves.get(posCitaSelec));								
								if (posCitasVienenBDJueves.get(posCitaSelec).equals("1"))
									eliminarCitaBD(citaListJueves.get(posCitaSelec));
								posCitasVienenBDJueves.set(posCitaSelec,"0");
								citaListJueves.remove(posCitaSelec);
								oco.setObservacion("libre");
								citaListJueves.add(posCitaSelec, oco);
								break;
				case "viernes":	System.out.println("borrar del dia viernes "+citaListViernes.get(posCitaSelec));
								if (posCitasVienenBDViernes.get(posCitaSelec).equals("1"))
									eliminarCitaBD(citaListViernes.get(posCitaSelec));
								posCitasVienenBDViernes.set(posCitaSelec,"0");
								citaListViernes.remove(posCitaSelec);
								oco.setObservacion("libre");
								citaListViernes.add(posCitaSelec, oco);
								break;
			}
			lMsjEliminacionExito.setVisible(true);
		}	
		
	}
	
	private void posicionarNuevaCita(ObservableList<CitaOdontologica> lista, int diaSemana){
		System.out.println(" &  &  dia semana:   "+diaSemana);

		CitaOdontologica citaO = new CitaOdontologica();
		
		citaO.setDoctor(DoctorList.get(posDoctorSeleccionado));								
		citaO.setPaciente(ContextoCronograma.getInstance().getPaciente());
		citaO.setObservacion(ContextoCronograma.getInstance().getObservacionCita());
		
		if (turno)	citaO.setAMPM("PM");
		else	citaO.setAMPM("AM");
		
		String fechaamd = semana.get(diaSemana).substring(8)+"-"+semana.get(diaSemana).substring(5,7)+"-"+semana.get(diaSemana).substring(0,4)+" "+horaList.get(posCitaSelec).getIntervalo().substring(0,4);
		
		String dateInString = fechaamd;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy h:mm");		 
		try {
			Date date = sdf.parse(dateInString);
			citaO.setFecha(date);
		} catch (ParseException e) {	e.printStackTrace(); }				
		lista.remove(posCitaSelec);							
		lista.add(posCitaSelec, citaO);
		
		Iterator<CitaOdontologica> ite = lista.iterator();
		while (ite.hasNext()){
			CitaOdontologica oco = ite.next();
			System.out.println(oco.getObservacion());
		}
	}
		
	private void AgregarCita(){
		 ContextoCronograma.getInstance().setBanderaVentNuevaCita(true);
		 ProgramaPrincipal.mostrarVentanaNuevaCita();		 
	}
	
	@FXML
	private void actionGuardarCita(){	
		bGuardarCitaBD.setDisable(true);
		
		System.out.println("LUNES");
		Iterator<CitaOdontologica> i = citaListLunes.iterator();
		while (i.hasNext()){
			CitaOdontologica co = i.next();
			if (!co.getObservacion().equals("libre"))
				System.out.println(""+co+" "+co.getObservacion());			
		}
		System.out.println("MARTES");
		i = citaListMartes.iterator();
		while (i.hasNext()){
			CitaOdontologica co = i.next();
			if (!co.getObservacion().equals("libre"))
				System.out.println(""+co+" "+co.getObservacion());
		}
		System.out.println("MIERCOLES");
		i = citaListMiercoles.iterator();
		while (i.hasNext()){
			CitaOdontologica co = i.next();
			if (!co.getObservacion().equals("libre"))
				System.out.println(""+co+" "+co.getObservacion());
		}
		System.out.println("JUEVES");
		i = citaListJueves.iterator();
		while (i.hasNext()){
			CitaOdontologica co = i.next();
			if (!co.getObservacion().equals("libre"))
				System.out.println(""+co+" "+co.getObservacion());
		}
		System.out.println("VIERNES");
		i = citaListViernes.iterator();
		while (i.hasNext()){
			CitaOdontologica co = i.next();
			if (!co.getObservacion().equals("libre"))
				System.out.println(""+co+" "+co.getObservacion());
		}
		System.out.println("- - - - - - - - - - - ");
		System.out.println("Lunes: "+posNuevaLunes);
		System.out.println("Martes: "+posNuevaMartes);
		System.out.println("Miercoles: "+posNuevaMiercoles);
		System.out.println("Jueves: "+posNuevaJueves);
		System.out.println("Viernes: "+posNuevaViernes);
		System.out.println("- - - - - - - - - - - ");
		int pos=0;
		
		if (!posNuevaLunes.equals("")){
			char [] prov = posNuevaLunes.toCharArray();
			for (int f=0;f<prov.length;f++){
				pos = Integer.parseInt(prov[f]+"");
				if (posCitasVienenBDLunes.get(pos).equals("2"))	{			
					guardarNuevaCitaBD(citaListLunes.get(pos));
					posCitasVienenBDLunes.set(pos, "1");
				}
			}
		}
		
		if (!posNuevaMartes.equals("")){
			char [] prov = posNuevaMartes.toCharArray();		
			
			for (int f=0;f<prov.length;f++){
				 pos = Integer.parseInt(prov[f]+"");
				 if (posCitasVienenBDMartes.get(pos).equals("2"))	{	
					 guardarNuevaCitaBD(citaListMartes.get(pos));
					 posCitasVienenBDMartes.set(pos, "1");
				 }
			}
		}
		
		if (!posNuevaMiercoles.equals("")){
			char [] prov = posNuevaMiercoles.toCharArray();			
			for (int f=0;f<prov.length;f++){
				pos = Integer.parseInt(prov[f]+"");
				if (posCitasVienenBDMiercoles.get(pos).equals("2"))	{
					guardarNuevaCitaBD(citaListMiercoles.get(pos));
					posCitasVienenBDMiercoles.set(pos, "1");
				}
			}
		}
		
		if (!posNuevaJueves.equals("")){
			char [] prov = posNuevaJueves.toCharArray();		
			
			for (int f=0;f<prov.length;f++){
				 pos = Integer.parseInt(prov[f]+"");
				 if (posCitasVienenBDJueves.get(pos).equals("2"))	{
					 guardarNuevaCitaBD(citaListJueves.get(pos));
					 posCitasVienenBDJueves.set(pos, "1");
				 }
			}
		}
		
		if (!posNuevaViernes.equals("")){
			char [] prov = posNuevaViernes.toCharArray();		
			
			for (int f=0;f<prov.length;f++){
				 pos = Integer.parseInt(prov[f]+"");
				 if (posCitasVienenBDViernes.get(pos).equals("2"))	{
					 guardarNuevaCitaBD(citaListViernes.get(pos));
					 posCitasVienenBDViernes.set(pos, "1");
				 }
			}
		}	
	}
	
	@FXML
	private void actionCancelar(){
		System.out.println("boton cancelar cronograma cita");
		limpiezaTabla();
		semana.clear();		
		citaListLunes.clear();citaListMartes.clear();citaListMiercoles.clear();citaListJueves.clear();
		citaListViernes.clear();
		posCitasVienenBDLunes.clear(); 
		posCitasVienenBDMartes.clear(); posCitasVienenBDMiercoles.clear();
		posCitasVienenBDJueves.clear();posCitasVienenBDViernes.clear();
		ContextoCronograma.getInstance().setBanderaVentana(false);
		ContextoCronograma.getInstance().setBanderaVentNuevaCita(false);
		ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
		ContextoCronograma.getInstance().setBanderaInicio_cbDr(false);
		ContextoCronograma.getInstance().setBanderaLimpiezaTabla(false);
		ContextoCronograma.getInstance().setDiaClick("");
		ContextoCronograma.getInstance().setPaciente(null);
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		stage.close();
	}
	
	private void guardarNuevaCitaBD(CitaOdontologica cita){		
		Session sesion = openSesion();	
		cita.setAsistencia("libre");
		sesion.saveOrUpdate(cita);		
		closeSesion(sesion);
		lMsjRegistroExito.setVisible(true);
	}
	
	private void eliminarCitaBD(CitaOdontologica cita){		
		Session sesion = openSesion();	
		sesion.delete(cita);
		closeSesion(sesion);
	}
				
	public void algoritmoHora(Calendar cal, Calendar calendar, String horainicio, String horafinal, int intervalo, int horaInicioJornada, int horaFinalJornada, int minInicioJornada, int minFinalJornada){
		boolean bandera=true;
		
		cal.add(calendar.HOUR, -horaInicioJornada);		
		calendar.add(calendar.HOUR, - cal.getTime().getHours());
		calendar.add(calendar.MINUTE, - calendar.getTime().getMinutes());
		calendar.add(calendar.SECOND, - calendar.getTime().getSeconds());
		
		if (calendar.getTime().getMinutes()==0)
			horainicio = calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes()+"0";
		else
			horainicio = calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes();	
					
		while (bandera){			
			if (calendar.getTime().getMinutes()==0)
				calendar.add(calendar.MINUTE, intervalo);
			else if (calendar.getTime().getMinutes()==30){
				calendar.add(calendar.HOUR, 1);
				calendar.add(calendar.MINUTE, -intervalo);
			}			
			
			if (calendar.getTime().getMinutes()==0)
				horafinal = calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes()+"0";
			else
				horafinal = calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes();
			
			horaList.add(new intervalosHora(horainicio+"-"+horafinal));
			horainicio=horafinal;
			
			
			if ((calendar.getTime().getHours()==horaFinalJornada) && (calendar.getTime().getMinutes()==minFinalJornada))
				bandera=false;			
		}
	}
	
	public void cargarHorasAM(){
		Calendar cal = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		
		String horainicio = "", horafinal = "";
		int intervalo = 30, horaInicioJornada = 07, minInicioJornada = 00, horaFinalJornada = 11, minFinalJornada=30; 	
								
		algoritmoHora(cal, calendar, horainicio, horafinal, intervalo, horaInicioJornada, horaFinalJornada, minInicioJornada, minFinalJornada);
	}
	
	public void cargarHorasPM(){
		Calendar cal = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
	
		String horainicio = "", horafinal = "";
		int intervalo = 30, horaInicioJornada = 01, minInicioJornada = 00, horaFinalJornada = 05, minFinalJornada=30; 	
								
		algoritmoHora(cal, calendar, horainicio, horafinal, intervalo, horaInicioJornada, horaFinalJornada, minInicioJornada, minFinalJornada);			
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
		 System.out.println("Controlador cronograma cita odonotologica - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	}
	
}
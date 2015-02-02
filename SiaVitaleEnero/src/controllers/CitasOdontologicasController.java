package controllers;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.util.HotSwapper;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.CitaOdontologica;
import data.Doctor;
import data.Personal;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
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
	
//	Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
//        @Override
//        public TableCell call(TableColumn p) {
//            return new EditingCell();
//        }
//    };
	
	@FXML
	private TableView tvHorario;
	
	@FXML
	private TableColumn tcHora;
	
	@FXML
	private ChoiceBox cbDoctor;
	
	@FXML	
	private GridPane gpdvFecha;
	
	private DatePicker dpFecha;
	
	private Calendar calendar;
	
	@FXML
	private Button btAgregarCita;
	
	
	intervalosHora ih;
	
	ObservableList<intervalosHora> horaList = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaList = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListLunes = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListMartes = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListMiercoles = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListJueves = FXCollections.observableArrayList();
	ObservableList<CitaOdontologica> citaListViernes = FXCollections.observableArrayList();
	
	ObservableList<Doctor> DoctorList = FXCollections.observableArrayList();
	private ObservableList<String> opcionDoctor = FXCollections.observableArrayList();	
	
		
	private List<String> fechasSemana = new LinkedList<>();
	private List<String> diasSemana = new LinkedList<>(); 
	
	private boolean turno=false;
	
	private int idDoctorSeleccionado=0;
	
	private List<String> semana = new LinkedList<>();
	private SimpleDateFormat patron = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat patronH = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	private SimpleDateFormat patroND = new SimpleDateFormat("EE");
	private SimpleDateFormat patronHora = new SimpleDateFormat("h");
	private SimpleDateFormat patronMinuto = new SimpleDateFormat("mm");
	
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
//					System.out.println("1: "+opcionDoctor.get(arg2.intValue()));
//					System.out.println("nombre doctor: "+DoctorList.get(arg2.intValue()).getPersona().getApellidos());
					idDoctorSeleccionado=DoctorList.get(arg2.intValue()).getId();
//					System.out.println("id: "+idDoctorSeleccionado);
					btAgregarCita.setDisable(false);
				}
		}});		
	}	

	@FXML
	private void actionTurnoPM(){
		horaList.clear();cargarHorasPM();tvHorario.setItems(horaList);
		turno=true;
		CitaOdontologica oco = null;
		for (int e=0;e<9;e++){
			citaListLunes.add(oco);		citaListMartes.add(oco);
			citaListMiercoles.add(oco);		citaListJueves.add(oco);
			citaListViernes.add(oco);			
		}
		citaListLunes.clear();		citaListMartes.clear();
		citaListMiercoles.clear();		citaListJueves.clear();
		citaListViernes.clear();	
		tvLunes.setItems(citaListLunes);		tvMartes.setItems(citaListMartes);
		tvMiercoles.setItems(citaListMiercoles);		tvJueves.setItems(citaListJueves);
		tvViernes.setItems(citaListViernes);
		
		if (dpFecha.getSelectedDate()!=null){
			System.out.println("TURNO AM - getSelectedDate: "+dpFecha.getSelectedDate());
			System.out.println("dia: "+dpFecha.getSelectedDate().getDate());
			semana.clear();
			cargarSemana(dpFecha.getSelectedDate());
		}else
			System.out.println("- * - * - * - * / * /* Seleccione fecha * - * - /* - /* - /* - *");
	}
	
	@FXML
	private void actionTurnoAM(){	
		horaList.clear();cargarHorasAM();tvHorario.setItems(horaList);
		turno=false;
		CitaOdontologica oco = null;
		for (int e=0;e<9;e++){
			citaListLunes.add(oco);		citaListMartes.add(oco);
			citaListMiercoles.add(oco);		citaListJueves.add(oco);
			citaListViernes.add(oco);			
		}
		citaListLunes.clear();		citaListMartes.clear();
		citaListMiercoles.clear();	citaListJueves.clear();	
		citaListViernes.clear();					
		tvLunes.setItems(citaListLunes);	tvMartes.setItems(citaListMartes);
		tvMiercoles.setItems(citaListMiercoles);	tvJueves.setItems(citaListJueves);
		tvViernes.setItems(citaListViernes);
		if (dpFecha.getSelectedDate()!=null){
			System.out.println("TURNO PM - getSelectedDate: "+dpFecha.getSelectedDate());
			System.out.println("dia: "+dpFecha.getSelectedDate().getDate());
			semana.clear();
			cargarSemana(dpFecha.getSelectedDate());
		}else
			System.out.println("- * - * - * - * / * /* Seleccione fecha * - * - /* - /* - /* - *");
	}	

	private void cargarSemana(Date fechaSeleccionada){
//		SimpleDateFormat nombreDia = new SimpleDateFormat("E");new SimpleDateFormat("a");
		SimpleDateFormat numeroDia = new SimpleDateFormat("u");
		System.out.println("dd: "+patron.format(fechaSeleccionada));
			
		Calendar c = Calendar.getInstance();
		c.setTime(fechaSeleccionada);
		
		switch (numeroDia.format(fechaSeleccionada)){
			case "1":	semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						System.out.println("lunes .... lista : "+semana.size());
						for (int t=0;t<semana.size();t++)   System.out.print(" "+semana.get(t));
						break;						
			case "2":	c.add(Calendar.DAY_OF_MONTH, -1);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						System.out.println("martes .... lista : "+semana.size());
						for (int t=0;t<semana.size();t++)	System.out.print(" "+semana.get(t));					
						break;
			case "3":	c.add(Calendar.DAY_OF_MONTH, -2);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						System.out.println("miercoles .... lista : "+semana.size());
						for (int t=0;t<semana.size();t++)	System.out.print(" "+semana.get(t));						
						break;
			case "4":	c.add(Calendar.DAY_OF_MONTH, -3);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						System.out.println("jueves .... lista : "+semana.size());
						for (int t=0;t<semana.size();t++)	System.out.print("  "+semana.get(t));						
						break;
			case "5":	c.add(Calendar.DAY_OF_MONTH, -4);
						semana.add(patron.format(c.getTime()));		algoritmo(c, 5);
						System.out.println("viernes .... lista : "+semana.size());
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
		
		System.out.println("0:  "+semana.get(0)+"  5:  "+semana.get(5)+" ids "+idDoctorSeleccionado+"  "+turno);
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
			System.out.println(" "+co);
			System.out.println(" "+patronH.format(co.getFecha()));
			System.out.println(" "+patroND.format(co.getFecha()));
			
			if (patroND.format(co.getFecha()).compareTo("lun")==0){
				System.out.println("cita para lunes "+co.getFecha().getHours() +":"+co.getFecha().getMinutes());
				citaListLunes.add(co);
				tvLunes.setItems(citaListLunes);
			}else if (patroND.format(co.getFecha()).compareTo("mar")==0){
				System.out.println("cita para martes "+co.getFecha().getHours() +":"+co.getFecha().getMinutes());
				citaListMartes.add(co);
				tvMartes.setItems(citaListMartes);				
			}else if (patroND.format(co.getFecha()).compareTo("mie")==0){
				System.out.println("cita para miercoles "+co.getFecha().getHours() +":"+co.getFecha().getMinutes());
				citaListMiercoles.add(co);
				tvMiercoles.setItems(citaListMiercoles);				
			}else if (patroND.format(co.getFecha()).compareTo("jue")==0){
				System.out.println("cita para jueves "+co.getFecha().getHours() +":"+co.getFecha().getMinutes());
				
				System.out.println("hora jueves:  "+patronHora.format(co.getFecha()));
				System.out.println("minutos jueves:  "+patronMinuto.format(co.getFecha()));	
				mostrarCitaSegunIntervaloHora(co,"jueves");
				tvJueves.setItems(citaListJueves);				
			}else if (patroND.format(co.getFecha()).compareTo("vie")==0){
				System.out.println("cita para viernes "+co.getFecha().getHours() +":"+co.getFecha().getMinutes());
				System.out.println("hora viernes:  "+patronHora.format(co.getFecha()));
				System.out.println("minutos viernes:  "+patronMinuto.format(co.getFecha()));		
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
//			System.out.println(" - + - + - + - + - "+obj.getIntervalo());
			String[] horas = obj.getIntervalo().split("-");
			String[] inicio = horas[0].split(":");
			String[] fin = horas[1].split(":");
//			System.out.println("horas[0] "+horas[0]+" horas[1] "+horas[1]);
//			System.out.println("inicio[0] "+inicio[0]+" inicio[1] "+inicio[1]);
//			System.out.println("fin[0] "+fin[0]+" fin[1] "+fin[1]);
			if ( ( patronHora.format(co.getFecha()).compareTo(inicio[0]) == 0 ) &&
				( Integer.parseInt(patronMinuto.format(co.getFecha())) >= Integer.parseInt(inicio[1])) && 
				( Integer.parseInt(patronMinuto.format(co.getFecha())) < Integer.parseInt(fin[1]))	) {
					System.out.println("pos: "+contador);
					switch(dia){
						case "lunes": citaListLunes.set(contador, co);
					   				  break;
						case "martes": citaListMartes.set(contador, co);
		   				  			   break;
						case "miercoles": citaListMiercoles.set(contador, co);
		   				  			      break;
						case "jueves": citaListJueves.set(contador, co);
									   break;
						case "viernes": citaListViernes.set(contador, co);
		   				  		        break;
					}				
			}
			contador++;					
		}
	}
	
	private void actualizarCells(){
		
		System.out.println("act cells");
		
		tvLunes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tvLunes.getSelectionModel().getSelectedItem() != null){    
		           TableViewSelectionModel<CitaOdontologica> selectionModel = tvLunes.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           Object val = tablePosition.getTableColumn().getCellData(newValue);
		           System.out.println("getrow lunes "+tablePosition.getRow());
		           System.out.println("getcolumn "+tablePosition.getColumn());
		           System.out.println("Selected Value " + val);
		         }
		    }
	 });	
	 
	 tvMartes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tvMartes.getSelectionModel().getSelectedItem() != null){    
		           TableViewSelectionModel<CitaOdontologica> selectionModel = tvMartes.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           Object val = tablePosition.getTableColumn().getCellData(newValue);
		           System.out.println("getrow martes "+tablePosition.getRow());
		           System.out.println("getcolumn "+tablePosition.getColumn());
		           System.out.println("Selected Value " + val);
		         }
		    }
	 });
	 
	 tvMiercoles.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tvMiercoles.getSelectionModel().getSelectedItem() != null) {    
		           TableViewSelectionModel<CitaOdontologica> selectionModel = tvMiercoles.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           Object val = tablePosition.getTableColumn().getCellData(newValue);
		           System.out.println("getrow miercoles "+tablePosition.getRow());
		           System.out.println("getcolumn "+tablePosition.getColumn());
		           System.out.println("Selected Value " + val);
		         }
		    }
	 });
	 
	 tvJueves.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tvJueves.getSelectionModel().getSelectedItem() != null){    
		           TableViewSelectionModel<CitaOdontologica> selectionModel = tvJueves.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           Object val = tablePosition.getTableColumn().getCellData(newValue);
		           System.out.println("getrow jueves "+tablePosition.getRow());
		           System.out.println("getcolumn "+tablePosition.getColumn());
		           System.out.println("Selected Value " + val);
		         }
		    }
	 });
	 
	 tvViernes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tvViernes.getSelectionModel().getSelectedItem() != null){    
		           TableViewSelectionModel<CitaOdontologica> selectionModel = tvViernes.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           Object val = tablePosition.getTableColumn().getCellData(newValue);
		           System.out.println("getrow viernes "+tablePosition.getRow());
		           System.out.println("getcolumn "+tablePosition.getColumn());
		           System.out.println("Selected Value " + val);
		         }
		    }
	 });
		
		 tcLunes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {

					if (arg0.getValue()!=null){
						if (arg0.getValue().getFecha().getDay()==1){
							System.out.println("la hora de lunes: "+arg0.getValue().getFecha().getHours() + ":" + arg0.getValue().getFecha().getMinutes());
							return new SimpleStringProperty(""+arg0.getValue().getObservacion());
						}else return new SimpleStringProperty("     ");
					}else return new SimpleStringProperty("     ");
				}			
		});
		tcMartes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
					
					if (arg0.getValue()!=null){
						if (arg0.getValue().getFecha().getDay()==2){
							System.out.println("la hora de martes: "+arg0.getValue().getFecha().getHours() + ":" + arg0.getValue().getFecha().getMinutes());
							return new SimpleStringProperty(""+arg0.getValue().getObservacion());
						}else return new SimpleStringProperty("     ");
					}else return new SimpleStringProperty("     ");
					}			
		});	
		tcMiercoles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
			
				if (arg0.getValue()!=null){	
					if (arg0.getValue().getFecha().getDay()==3){
						System.out.println("la hora de miercoles: "+arg0.getValue().getFecha().getHours() + ":" + arg0.getValue().getFecha().getMinutes());
						return new SimpleStringProperty(""+arg0.getValue().getObservacion());
					}else return new SimpleStringProperty("     ");
				}else return new SimpleStringProperty("     ");
				}			
		});	
		tcJueves.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
								
				if (arg0.getValue()!=null){
					if (arg0.getValue().getFecha().getDay()==4){
						System.out.println("la hora de jueves: "+arg0.getValue().getFecha().getHours() + ":" + arg0.getValue().getFecha().getMinutes());
						return new SimpleStringProperty(""+arg0.getValue().getObservacion());
					}else return new SimpleStringProperty("     ");
				}else return new SimpleStringProperty("     ");
			}			
		});	
		tcViernes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
				
				if (arg0.getValue()!=null){
					if (arg0.getValue().getFecha().getDay()==5){
						System.out.println("la hora de viernes: "+arg0.getValue().getFecha().getHours() + ":" + arg0.getValue().getFecha().getMinutes());
						return new SimpleStringProperty(""+arg0.getValue().getObservacion());
					}else return new SimpleStringProperty("     ");
				}else return new SimpleStringProperty("     ");
			}			
		});			
		
	}	
	
	@FXML
	private void actionAgregarCita(){	
		System.out.println("agregar cita");
	}
				
	public void algoritmoHora(Calendar cal, Calendar calendar, String horainicio, String horafinal, int intervalo, int horaInicioJornada, int horaFinalJornada, int minInicioJornada, int minFinalJornada){
		boolean bandera=true;
		
		cal.add(calendar.HOUR, -horaInicioJornada);		
		calendar.add(calendar.HOUR, - cal.getTime().getHours());
		calendar.add(calendar.MINUTE, - calendar.getTime().getMinutes());
		calendar.add(calendar.SECOND, - calendar.getTime().getSeconds());
		
		System.out.println("Hora inicial lista "+calendar.getTime());
		
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
		int intervalo = 30, horaInicioJornada = 7, minInicioJornada = 0, horaFinalJornada = 11, minFinalJornada=30; 	
								
		algoritmoHora(cal, calendar, horainicio, horafinal, intervalo, horaInicioJornada, horaFinalJornada, minInicioJornada, minFinalJornada);
	}
	
	public void cargarHorasPM(){
		Calendar cal = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
	
		String horainicio = "", horafinal = "";
		int intervalo = 30, horaInicioJornada = 1, minInicioJornada = 0, horaFinalJornada = 5, minFinalJornada=30; 	
								
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
	
}
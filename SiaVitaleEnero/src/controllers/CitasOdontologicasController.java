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

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.CitaOdontologica;
import data.Personal;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class CitasOdontologicasController{	
			
	@FXML
	private TableView tvCronograma;
	
	@FXML
	private TableColumn tcLunes;	
	
	@FXML
	private TableColumn tcMartes;
	
	@FXML	
	private TableColumn tcMiercoles;
	
	@FXML
	private TableColumn tcJueves;
	
	@FXML
	private TableColumn tcViernes;
	
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
	
	ObservableList<Personal> DoctorList = FXCollections.observableArrayList();
	private ObservableList<String> opcionDoctor = FXCollections.observableArrayList();	
	
		
	private List<String> fechasSemana = new LinkedList<>(); 

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
		
		tvHorario.setItems(horaList);
		tcHora.setCellValueFactory(new PropertyValueFactory<intervalosHora, String>("intervalo"));
		
		tvHorario.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<intervalosHora>(){
			@Override
			public void changed(ObservableValue<? extends intervalosHora> arg0, intervalosHora arg1, intervalosHora arg2) {
				if (arg2 != null){
					System.out.println("Seleccione intervalo de horas:  "+arg2.getIntervalo());
				}    
			}
		});		
		
//		tvCronograma.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<intervalosHora>(){
//			@Override
//			public void changed(ObservableValue<? extends intervalosHora> arg0, intervalosHora arg1, intervalosHora arg2) {
//				if (arg2 != null){
//					System.out.println("Seleccione intervalo de horas:  "+arg2.getIntervalo());
//				}    
//			}
//		});		
		
		
		Session sesion1 = openSesion();
		Query queryResultDoctores = sesion1.createQuery("from Personal");	
				
		DoctorList = FXCollections.observableArrayList(queryResultDoctores.list());		
		for (int r=0;r<DoctorList.size();r++){
			opcionDoctor.add(DoctorList.get(r).getNombres() +" "+DoctorList.get(r).getApellidos());
		}
		closeSesion(sesion1);
		cbDoctor.setItems(opcionDoctor);
		cbDoctor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {						
				if (!opcionDoctor.isEmpty()){
					System.out.println("0: "+arg2.intValue());
					System.out.println("1: "+opcionDoctor.get(arg2.intValue()));
					System.out.println("2: "+DoctorList.get(arg2.intValue()));
				}
		}});		
	}	
	
	@FXML
	private void actionTurnoAM(){	
		horaList.clear();cargarHorasAM();
	}
	
	@FXML
	private void actionTurnoPM(){
		horaList.clear();cargarHorasPM();		
	}
	
	@FXML
	private void actionCargarSemana(){	
		calendar = Calendar.getInstance();
		System.out.println("cargar semana actual  "+calendar.getTime());
		fechasSemana.clear();
		calculoSemana(calendar);		
		System.out.println("dia lunes "+fechasSemana.get(0)+"  dia viernes!!!!!  "+fechasSemana.get(4));

		tvCronograma.setItems(citaList);
		tcLunes.setCellValueFactory(new PropertyValueFactory<CitaOdontologica, String>("fecha"));
	}	
	
	@FXML
	private void actionAgregarCita(){
		System.out.println("getSelectedDate: "+dpFecha.getSelectedDate());
		System.out.println("dia: "+dpFecha.getSelectedDate().getDate());
		calendar = Calendar.getInstance();
		calendar.setTime(dpFecha.getSelectedDate());
		fechasSemana.clear();
		calculoSemana(calendar);
		System.out.println("dia lunes "+fechasSemana.get(0)+"  dia viernes!!!!!  "+fechasSemana.get(4));
	}
	
	private void calculoSemana(Calendar calendar){
		int dayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
		switch(dayofWeek){
			case 1:	System.out.println("es domingo");
					break;					
			case 2: calendar.add(calendar.DATE,-2);
					calculofecha(6,2);							
					break;
			case 3: calendar.add(calendar.DATE,-3);
					calculofecha(6,3);	
					break;
			case 4: calendar.add(calendar.DATE,-4);
					calculofecha(6,4);						
					break;
			case 5: calendar.add(calendar.DATE,-5);
					calculofecha(6,5);					
					break;	
			case 6: calendar.add(calendar.DATE,-6);
					calculofecha(6,6);
					break;	
			case 7: System.out.println("es sabado");
					break;		
		}

		System.out.println(fechasSemana.size()+" 0: "+fechasSemana.get(0)+" 4: "+fechasSemana.get(4));
		Iterator<String> ite = fechasSemana.iterator();
		System.out.println("ueuweueueueueueueueu");
		
		while (ite.hasNext())
			System.out.println(ite.next());
				
		try{
			Session sesion = openSesion();
			Query queryr = sesion.createQuery("from CitaOdontologica where fecha >= :fi and fecha <= :ff");
			queryr.setString("fi", fechasSemana.get(0));
			queryr.setString("ff", fechasSemana.get(4));
			citaList = FXCollections.observableArrayList(queryr.list());
			closeSesion(sesion);
		}catch (HibernateException e1){
			e1.printStackTrace();
		}
		Iterator<String> iterator = fechasSemana.iterator();
		
		for (int p=0;p<citaList.size();p++){
			System.out.println(""+citaList.get(p).getObservacion()+" // "+citaList.get(p).getFecha().toString());
			String[] partido=citaList.get(p).getFecha().toString().split(" ");
			System.out.println(partido[0]);System.out.println(partido[1]);
			System.out.println("**"+fechasSemana.get(p));
						
		}
		System.out.println("cantidad de citas "+citaList.size());		
	}
	
	private void calculofecha(int limite,int diaSelec){
		for (int r=0;r<=limite;r++){					
			
			calendar.add(calendar.DATE,1);
			System.out.println("aja: "+calendar.getTime());
			if (diaSelec==calendar.get(Calendar.DAY_OF_WEEK))
				System.out.println("es el dia que selecioneee");
			String dia="", mes="" ,anho="" ;
			
			int temp=calendar.get(Calendar.DAY_OF_WEEK);
			
			mes=""+calendar.get(Calendar.MONTH)+1;
			
			if (calendar.get(Calendar.DATE)<10)
				dia="0"+calendar.get(Calendar.DATE);
			else
				dia=""+calendar.get(Calendar.DATE);
			
			switch(temp){
				case 2: tcLunes.setText("Lunes "+dia);
						fechasSemana.add(calendar.get(Calendar.YEAR)+"-"+mes+"-"+dia);						
						break;
				case 3: tcMartes.setText("Martes "+dia);
						fechasSemana.add(calendar.get(Calendar.YEAR)+"-"+mes+"-"+dia);
						break;
				case 4: tcMiercoles.setText("Miercoles "+dia);
						fechasSemana.add(calendar.get(Calendar.YEAR)+"-"+mes+"-"+dia);
						break;
				case 5: tcJueves.setText("Jueves "+dia);
						fechasSemana.add(calendar.get(Calendar.YEAR)+"-"+mes+"-"+dia);
						break;
				case 6: tcViernes.setText("Viernes "+dia);
						fechasSemana.add(calendar.get(Calendar.YEAR)+"-"+mes+"-"+dia);						
						break;
			}			
		}		
	}
	
	public void cargarHorasAM(){
		horaList.add(new intervalosHora("7:00 - 7:30"));
		horaList.add(new intervalosHora("7:30 - 8:00"));
		horaList.add(new intervalosHora("8:00 - 8:30"));
		horaList.add(new intervalosHora("8:30 - 9:00"));
		horaList.add(new intervalosHora("9:00 - 9:30"));
		horaList.add(new intervalosHora("9:30 - 10:00"));
		horaList.add(new intervalosHora("10:00 - 10:30"));	
		horaList.add(new intervalosHora("10:30 - 11:00"));
		horaList.add(new intervalosHora("11:00 - 11:30"));	
	}
	
	public void cargarHorasPM(){
		horaList.add(new intervalosHora("1:00 - 1:30"));
		horaList.add(new intervalosHora("1:30 - 2:00"));
		horaList.add(new intervalosHora("2:00 - 2:30"));
		horaList.add(new intervalosHora("2:30 - 3:00"));
		horaList.add(new intervalosHora("3:00 - 3:30"));
		horaList.add(new intervalosHora("3:30 - 4:00"));
		horaList.add(new intervalosHora("4:00 - 4:30"));
		horaList.add(new intervalosHora("4:30 - 5:00"));
		horaList.add(new intervalosHora("5:00 - 5:30"));
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
package controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Horario;
import data.Turno;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class HorariosTurnosController {	
		
	@FXML
	private TableView TablaHorario;
		
	@FXML
	private TableColumn ColHoraInicio;
	
	@FXML
	private TableColumn ColHoraFin;
	
	@FXML
	private TableView TablaHoraTurno;
		
	@FXML
	private TableColumn ColHorarioTurno;
	
	@FXML
	private TableView TablaTurno;
		
	@FXML
	private TableColumn ColTurno;
	
	@FXML
	private Button btAgregarHorario;
	
	@FXML
	private Button btEliminarHorario;
	
	@FXML
	private Button btAgregarHoraTurno;
	
	@FXML
	private Button btEliminarHoraTurno;
	
	@FXML
	private Button btEliminarTurno;
	
	@FXML
	private Button btGuardarHoraTurno;
	
	@FXML
	private ChoiceBox cbHorario;
	
	@FXML
	private Label mensaje;
	
	ObservableList<DataModelHorario> listHorario = FXCollections.observableArrayList();
	
	ObservableList<DataModelTurno> listTurno = FXCollections.observableArrayList();
	
	ObservableList<DataModelHorario> listHorarioTurno = FXCollections.observableArrayList();
	
	private int idHorarioEliminar, idTurnoSeleccionado, idHoraTurnoSeleccionado;	
	
	private ObservableList<Horario> HorarioList = FXCollections.observableArrayList();  
	private ObservableList<String> opcionHorario = FXCollections.observableArrayList();		
	
	private Horario horarioSelec = new Horario();
				
	public HorariosTurnosController(){}
		
	@FXML
	@SuppressWarnings("unchecked")	
	private void initialize(){
		System.out.println("controlador de horarios turnos!!!");
//		------------------------------- HORARIO ------------------------------
		TablaHorario.setEditable(true);
		
		ColHoraInicio.setCellValueFactory(new PropertyValueFactory<DataModelHorario, String>("horaInicio"));
		ColHoraInicio.setCellFactory(editableFactory);
		ColHoraInicio.setOnEditCommit(new EventHandler<CellEditEvent<DataModelHorario, String>>(){
			@Override
			public void handle(CellEditEvent<DataModelHorario, String> arg0) {
				((DataModelHorario)arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setHoraInicio(arg0.getNewValue());
				actualizatablaHorario(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getHorario());
			}
		});
				
		ColHoraFin.setCellValueFactory(new PropertyValueFactory<DataModelHorario, String>("horaFin"));
		ColHoraFin.setCellFactory(editableFactory);
		ColHoraFin.setOnEditCommit(new EventHandler<CellEditEvent<DataModelHorario, String>>(){
			@Override
			public void handle(CellEditEvent<DataModelHorario, String> arg0) {
				((DataModelHorario)arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setHoraFin(arg0.getNewValue());
				actualizatablaHorario(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getHorario());
			}
		});
		cargarHorario();
		TablaHorario.setItems(listHorario);
		
		TablaHorario.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DataModelHorario>(){
			@Override
			public void changed(ObservableValue<? extends DataModelHorario> arg0, DataModelHorario arg1, DataModelHorario arg2) {
				if (arg2 != null){
					btEliminarHorario.setDisable(false);
					System.out.println("Seleccione horario:  "+arg2.getHoraFin() + "    " + arg2.getHoraInicio()+"   "+arg2.getHorario().getIdHorario() +"   "+arg2.getHorario().getHoraInicio() + "  "+arg2.getHorario().getHoraFin());
					idHorarioEliminar=arg2.getHorario().getIdHorario();
				}
			}
		});
		
//		------------------------------- TURNO ------------------------------
		TablaTurno.setEditable(true);
		ColTurno.setCellValueFactory(new PropertyValueFactory<DataModelTurno, String>("descripcion"));
		ColTurno.setCellFactory(editableFactory);
		ColTurno.setOnEditCommit(new EventHandler<CellEditEvent<DataModelTurno, String>>(){
			@Override
			public void handle(CellEditEvent<DataModelTurno, String> arg0) {
				((DataModelTurno)arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setDescripcion(arg0.getNewValue());
				actualizatablaTurno(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getTurno());
			}
		});
		cargarTurno();	
		TablaTurno.setItems(listTurno);
		
		TablaTurno.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DataModelTurno>(){
			@Override
			public void changed(ObservableValue<? extends DataModelTurno> arg0, DataModelTurno arg1, DataModelTurno arg2) {
				if (arg2 != null){
					btEliminarTurno.setDisable(false);
					btEliminarHoraTurno.setDisable(true);	
					cbHorario.setVisible(false);btGuardarHoraTurno.setVisible(false);mensaje.setVisible(false);
					idTurnoSeleccionado=arg2.getTurno().getIdTurno();
					System.out.println("Seleccione turno:  "+arg2.getDescripcion());
					cargarHorarioTurno();
				}
			}
		});			
		
//		------------------------------ HORARIO-TURNO ----------------------------
		ColHorarioTurno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataModelHorario,String>, ObservableValue<String>>() {			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DataModelHorario, String> arg0) {				
				return new SimpleStringProperty(""+arg0.getValue().getHoraInicio()+" - "+arg0.getValue().getHoraFin());
		}});
//		final int idHorarioSeleccionado;
		TablaHoraTurno.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DataModelHorario>(){
			@Override
			public void changed(ObservableValue<? extends DataModelHorario> arg0, DataModelHorario arg1, DataModelHorario arg2) {
				if (arg2 != null){
					btEliminarHoraTurno.setDisable(false);
					System.out.println("Seleccione horario:  "+arg2.getHoraInicio() + " : "+ arg2.getHoraFin());
					idHoraTurnoSeleccionado=arg2.getHorario().getIdHorario();
				}
			}
		});		
	}	
	
//	------------------------------- HORARIO TURNO ------------------------------
	
	private void cargarHorarioTurno(){
		System.out.println("cargar horario-turno");
				
		try{
			Session sesion = openSesion();
					
			Query query = sesion.createQuery("from Turno where IdTurno= :cod");
			query.setInteger("cod", idTurnoSeleccionado);
			query.setMaxResults(1);
			
			Turno turno = new Turno();
			turno = (Turno)query.uniqueResult();
			if (turno!=null){
				System.out.println("  los horarios son: "+turno.getHorarios()+" cant: "+turno.getHorarios().size());
				
				List<Horario> temp = FXCollections.observableArrayList();
				temp = (List<Horario>) turno.getHorarios().subList(0,turno.getHorarios().size());
				
				closeSesion(sesion);
				
				listHorarioTurno.clear();
				if (!temp.isEmpty()){
					for (Horario xx : temp){
						listHorarioTurno.add(new DataModelHorario(xx));
					}
				}
			}
		}catch (HibernateException e1){
			e1.printStackTrace();
		}
		TablaHoraTurno.setItems(listHorarioTurno);
		
	}
	
//	------------------------------- TURNO ------------------------------
	
	private void cargarTurno(){
		System.out.println("cargar turno");
		
		List<Turno> temp = FXCollections.observableArrayList();
		try{
			Session sesion = openSesion();
			Query queryr = sesion.createQuery("from Turno order by IdTurno asc");
			temp = (List<Turno>) queryr.list();
			closeSesion(sesion);
		}catch (HibernateException e1){
			e1.printStackTrace();
		}
		listTurno.clear();
		if (!temp.isEmpty()){
			for (Turno xx : temp){
				listTurno.add(new DataModelTurno(xx));
			}
		}
	}
	
//	------------------------------- HORARIO ------------------------------
	
	private void cargarHorario() {
		System.out.println("cargar Horario");
		List<Horario> temp = FXCollections.observableArrayList();
		try{
			Session sesion = openSesion();
			Query queryr = sesion.createQuery("from Horario order by IdHorario asc");
			temp = (List<Horario>) queryr.list();
			closeSesion(sesion);
		}catch (HibernateException e1){
			e1.printStackTrace();
		}
		listHorario.clear();
		if (!temp.isEmpty()){
			for (Horario xx : temp){
				listHorario.add(new DataModelHorario(xx));
			}
		}			
	}
	
	@FXML
	private void actionAgregarHorario(){
		System.out.println("agregar horario");
		DataModelHorario newH = new DataModelHorario("","");
		listHorario.add(newH);
		TablaHorario.setItems(listHorario);
	}
	
	@FXML
	private void actionEliminarHorario(){
		System.out.println("id horario a eliminar  "+idHorarioEliminar);
		Session sesion1 = openSesion();
		Horario obj;
		Query queryResultHorario = sesion1.createQuery("from Horario where id = :pc");
		queryResultHorario.setInteger("pc",idHorarioEliminar);
		queryResultHorario.setMaxResults(1);
		obj = (Horario) queryResultHorario.uniqueResult();
		sesion1.delete(obj);
		closeSesion(sesion1);
		cargarHorario();
	}
	
	@FXML
	private void actionGuardarHoraTurno(){
		btEliminarHoraTurno.setDisable(true);	btAgregarHoraTurno.setDisable(false);
		cbHorario.setVisible(false);btGuardarHoraTurno.setVisible(false);mensaje.setVisible(false);
		System.out.println("guardar horario turno  "+horarioSelec.getHoraInicio() + " _______ " +horarioSelec.getHoraFin() + " turno: "+idTurnoSeleccionado);
				
		Session sesion1 = openSesion();
		Query queryHT = sesion1.createQuery("from Turno where IdTurno = :codigo");	
		queryHT.setInteger("codigo",idTurnoSeleccionado);
		queryHT.setMaxResults(1);
		
		Turno t = new Turno();
		t = (Turno) queryHT.uniqueResult();		
		t.getHorarios().add(horarioSelec);
		sesion1.saveOrUpdate(t);
		closeSesion(sesion1);		
		cargarHorarioTurno();
	}
	
	@FXML
	private void actionEliminarHoraTurno(){
		System.out.println("eliminar horario turno "+TablaHoraTurno.getSelectionModel().getSelectedIndex());
				
		Session sesion1 = openSesion();
			Query queryHT = sesion1.createQuery("from Turno where IdTurno = :codigo");	
			queryHT.setInteger("codigo",idTurnoSeleccionado);
			queryHT.setMaxResults(1);
			
			Turno t = new Turno();
			t = (Turno) queryHT.uniqueResult();			
			t.getHorarios().remove(TablaHoraTurno.getSelectionModel().getSelectedIndex());
		
			sesion1.update(t);
		closeSesion(sesion1);
		cargarHorarioTurno();	
	}
	
	@FXML
	private void actionAgregarHoraTurno(){		
		System.out.println("agregar horario turno");
		cbHorario.setDisable(false);		mensaje.setVisible(true);		cbHorario.setVisible(true);		
		btEliminarHoraTurno.setDisable(true);
		Session sesion1 = openSesion();
		Query queryResultHorariosDisponibles = sesion1.createQuery("from Horario");	
				
		HorarioList = FXCollections.observableArrayList(queryResultHorariosDisponibles.list());		
		for (int r=0;r<HorarioList.size();r++){
			opcionHorario.add(HorarioList.get(r).getHoraInicio() +" "+HorarioList.get(r).getHoraFin());
		}
		closeSesion(sesion1);
		cbHorario.setItems(opcionHorario);
		cbHorario.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {						
				if (!opcionHorario.isEmpty()){
					btGuardarHoraTurno.setDisable(false);btGuardarHoraTurno.setVisible(true);
					btAgregarHoraTurno.setDisable(true);
					System.out.println(arg1.intValue()+" cbhorario "+arg2.intValue());
					System.out.println("1: "+opcionHorario.get(arg2.intValue()));
					System.out.println("2: "+HorarioList.get(arg2.intValue()));
					horarioSelec = HorarioList.get(arg2.intValue());
					
				}
		}});		
	}	
	
	@FXML
	private void actionAgregarTurno(){
		System.out.println("agregar turno");
		DataModelTurno newT = new DataModelTurno("");
		listTurno.add(newT);
		TablaTurno.setItems(listTurno);
		System.out.println("agregar turno 1111111111111111");
	}
	
	@FXML
	private void actionEliminarTurno(){
		System.out.println("eliminar turno");
	}
	
	protected void actualizatablaHorario(Horario hor) {
		try{
			Session sesion = openSesion();
			sesion.saveOrUpdate(hor);
			closeSesion(sesion);
		}catch (HibernateException e1){
			e1.printStackTrace();
		}		
	}
	
	protected void actualizatablaTurno(Turno turno) {
		try{
			Session sesion = openSesion();
			sesion.saveOrUpdate(turno);
			closeSesion(sesion);
		}catch (HibernateException e1){
			e1.printStackTrace();
		}		
	}
				
	Callback<TableColumn, TableCell> editableFactory =
			new Callback<TableColumn, TableCell>() {

				@Override
				public TableCell call(TableColumn arg0) {
					return new EditarCelda();
				}
	};

	public class EditarCelda<S extends Object, T extends String> extends AbstractEditableTableCell<S, T> {
	    public EditarCelda() {
	    }
	    @Override
	    protected String getString() {
	        return getItem() == null ? "" : getItem().toString();
	    }
	    @Override
	    protected void commitHelper( boolean losingFocus ) {
	        commitEdit(((T) textField.getText()));
	    }	     
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




package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import data.Cargo;
import data.Estado;
import data.Ambiente;

public class CargoController {

	@FXML
	private TableView<Cargo> Tabla;
	
	@FXML
	private TableColumn<Cargo, String> ColCodigo;
	
	@FXML
	private TableColumn<Cargo, String> ColNombre;
	
	@FXML
	private TableColumn<Cargo, String> ColDescripcion;
	
	@FXML
	private TextField tfCodigo;
	
	@FXML
	private TextField tfNombre;
	
	@FXML
	private TextField tfFunciongral;
	
	@FXML
	private ComboBox cbSupervisaA;
	
	private ObservableList<Estado> estadoarray = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox cbSupervisadoPor;
		
	@FXML
	private ComboBox cbAmbiente;
	
	@FXML
	private ComboBox cbEducacion;
	
	@FXML
	private ComboBox cbEsfuerzo;
		
	@FXML
	private ComboBox cbExperiencia;
	
	@FXML
	private ComboBox cbResponsabilidad;
	
	@FXML
	private ComboBox cbRiesgo;
	
	@FXML
	private Button botonBorrar;
	
	@FXML
	private Button botonNuevo;

	@FXML
	private Button botonModifica;

	@FXML
	private Button botonCancela;

	@FXML
	private Label titulo;
	
	@FXML
	private Button botonGuarda;
	
	private ObservableList<Cargo> tablaarray = FXCollections.observableArrayList();
	private ObservableList<Ambiente> ambientearray = FXCollections.observableArrayList();
	
	private Cargo actual = null;

	public CargoController(){		
	}

	@FXML
	private void initialize() {
		
		titulo.setText("Cargo");
		
		ColCodigo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("Codigo"));
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<Cargo, String>("Descripcion"));
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);		
		
		mostrarTablaDetalles(null);
		cargaEmpleado();
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cargo>(){

			@Override
			public void changed(ObservableValue<? extends Cargo> arg0,
					Cargo arg1, Cargo arg2) {
				// TODO Auto-generated method stub
				actual = arg2;
				mostrarTablaDetalles(actual);
			}
			
		});		
		//cargarTabla();	
		edicion(false);
		enableboton();
	}
	
	
	private void cargarTabla() {
		// TODO Auto-generated method stub
		ObservableList ordenn = FXCollections.observableArrayList(); 
		ordenn = Tabla.getSortOrder();   // para mantener ordenado después de grabar!!!
		tablaarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Cargo");
            tablaarray = FXCollections.observableArrayList(queryResult.list());  
			sesion.getTransaction().commit();
			Tabla.setItems(tablaarray);
			if (ordenn.size()>0)		// para mantener ordenado después de grabar!!! el TableView
			{							// Esta modificación debe ir en todos los controllers... 
				Tabla.getSortOrder().addAll(ordenn);	
			}
		} catch (HibernateException e2) {
			e2.printStackTrace();
		}
	}

	private void mostrarTablaDetalles(Cargo mostrar) {
		if (mostrar != null)
		{
//			tfCodigo.setText(mostrar.getCodigo());
//			tfDescripcion.setText(mostrar.getDescripcion());
//			tfComentario.setText(mostrar.getComentario());
//			tfStatus.setText(mostrar.getStatus());
		}else
		{
//			tfCodigo.setText("");
//			tfDescripcion.setText("");
//			tfComentario.setText("");
//			tfStatus.setText("");			
		}
	}
	
	
	@FXML
	private void clickbotonBorrar(){
		int borrar = Tabla.getSelectionModel().getSelectedIndex();
		Cargo Dborrar = Tabla.getSelectionModel().getSelectedItem();
		System.out.println("Entroó en Botón Borrar!!!!");
		if (borrar > -1)
		{
			DialogResponse i = Dialogs.showConfirmDialog(Principal.getStagePrincipal(), 
                    "Está seguro que desea eliminar elemento?", 
                    "Por eliminar...",
                    "Eliminar Elemento" );
            if (i==DialogResponse.YES) 
            {
				Tabla.getItems().remove(borrar);
				try {
					Session sesion = Principal.fabrica.getCurrentSession();		
					sesion.beginTransaction();
					sesion.delete(Dborrar);
					sesion.getTransaction().commit();
				} catch (HibernateException e2) 
				{
					e2.printStackTrace();
				}				
			}
		}else
		{
			Dialogs.showWarningDialog(Principal.getStagePrincipal(), 
                    "Por Favor, Seleccione un Elemento de la Tabla", 
                    "Por Seleccionar...",
                    "Eliminar Elemento" );
		}
		mostrarTablaDetalles(null);
	}	
	
	private void disableboton(){
		botonNuevo.setDisable(true);
		botonBorrar.setDisable(true);
		botonGuarda.setDisable(false);
		botonCancela.setDisable(false);
		botonModifica.setDisable(true);
	}
	
	private void enableboton(){
		botonNuevo.setDisable(false);
		botonBorrar.setDisable(false);
		botonModifica.setDisable(false);
		botonGuarda.setDisable(true);
		botonCancela.setDisable(true);
	}
	
	private void edicion(Boolean edit){
		if (edit)
		{
			tfNombre.setDisable(false);
			tfFunciongral.setDisable(false);
			cbSupervisaA.setDisable(false);
			cbSupervisadoPor.setDisable(false);
			cbAmbiente.setDisable(false);
			cbEducacion.setDisable(false);
			cbEsfuerzo.setDisable(false);
			cbExperiencia.setDisable(false);
			cbResponsabilidad.setDisable(false);
			cbRiesgo.setDisable(false);
		}else{
			tfNombre.setDisable(true);
			tfFunciongral.setDisable(true);
			cbSupervisaA.setDisable(true);
			cbSupervisadoPor.setDisable(true);
			cbAmbiente.setDisable(true);
			cbEducacion.setDisable(true);
			cbEsfuerzo.setDisable(true);
			cbExperiencia.setDisable(true);
			cbResponsabilidad.setDisable(true);
			cbRiesgo.setDisable(true);
		}
	}
	
	@FXML
	private void clickbotonNuevo(){
		actual = new Cargo();
		disableboton();
		mostrarTablaDetalles(null);
		edicion(true);
		tfNombre.requestFocus();
	}

	
	@FXML
	private void clickbotonModifica(){
		Cargo modi = Tabla.getSelectionModel().getSelectedItem();
		actual = modi;
		System.out.println("Entroó en Botón Borrar!!!!");
		if (modi != null)
		{
			edicion(true);
			disableboton();
		}else
		{
			Dialogs.showWarningDialog(Principal.getStagePrincipal(), 
                    "Por Favor, Seleccione un Elemento de la Tabla", 
                    "Por Seleccionar...",
                    "Modificar Elemento" );
		}
	}
	
	@FXML
	private void clickbotonGuarda(){
		DialogResponse i = Dialogs.showConfirmDialog(Principal.getStagePrincipal(), 
                "Está seguro que desea Guardar ese elemento?", 
                "Por Guardar...",
                "Guardar Elemento" );
        if (i==DialogResponse.YES) 
        {
			int sel = Tabla.getSelectionModel().getSelectedIndex();	
//			actual.setDescripcion(tfDescripcion.getText());
//			actual.setComentario(tfComentario.getText());
//			actual.setStatus(tfStatus.getText());
//			actual.setCodigo(tfCodigo.getText());
			try {
				Session sesion = Principal.fabrica.getCurrentSession();		
				sesion.beginTransaction();
				sesion.saveOrUpdate(actual);
				sesion.getTransaction().commit();
			} catch (HibernateException e2) {

				e2.printStackTrace();
			}
			cargarTabla();
			Tabla.getSelectionModel().select(sel);
		}
		edicion(false);
		enableboton();
	}
	
	@FXML
	private void clickbotonCancela(){
		Cargo sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		mostrarTablaDetalles(actual);
		edicion(false);
		enableboton();
	}
	
	private void cargaEmpleado()
	{
		estadoarray.clear();
		ambientearray.clear();
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			
			Query queryResult = sesion.createQuery("from Estado order by descripcion");
			Query queryResult2 = sesion.createQuery("from Ambiente order by descripcion");
            estadoarray = FXCollections.observableArrayList(queryResult.list()); 
            ambientearray = FXCollections.observableArrayList(queryResult2.list());
            
            System.out.println("AQUI AQUI AQUI");
			sesion.getTransaction().commit();											
		}catch (HibernateException e2) {
			e2.printStackTrace();
			actual = null;	
		}
		cbSupervisaA.setItems(estadoarray);
		cbSupervisadoPor.setItems(estadoarray);
		cbAmbiente.setItems(ambientearray);
		cbEducacion.setItems(estadoarray);
		cbEsfuerzo.setItems(estadoarray);
		cbExperiencia.setItems(estadoarray);
		cbResponsabilidad.setItems(estadoarray);
		cbRiesgo.setItems(estadoarray);
		
		
		
		System.out.println("Cargó todo!");
	}
	
	
}

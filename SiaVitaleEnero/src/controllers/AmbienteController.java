package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Ambiente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AmbienteController {
	
	private static AnchorPane root;
	private static Stage stage = new Stage();
	
	@FXML
	private Label titulo;
	
	@FXML
	private TableView<Ambiente> Tabla;
	
	@FXML
	private TableColumn<Ambiente, String> ColCodigo;
	
	@FXML
	private TableColumn<Ambiente, String> ColDescripcion;
		
	@FXML
	private TextField tfDescripcion;
	
	@FXML
	private TextField tfComentario;
	
	@FXML
	private TextField tfStatus;
	
	@FXML
	private Button botonNuevo;
	
	@FXML
	private Button botonModifica;
	
	@FXML
	private Button botonBorrar;
	
	@FXML
	private Button botonCancela;
	
	@FXML
	private Button botonGuarda;
	
	private ObservableList<Ambiente> tablaarray = FXCollections.observableArrayList();
	
	private Ambiente actual = null; 
	
	private Boolean modifica = false;
	
	public AmbienteController(){		
	}
		
	@FXML
	private void initialize(){
		  
		titulo.setText("Ambiente");
		ColCodigo.setCellValueFactory(new PropertyValueFactory<Ambiente, String>("Codigo"));
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<Ambiente, String>("Descripcion"));
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		mostrarTablaDetalles(null);
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Ambiente>() {
		
			public void changed(ObservableValue<? extends Ambiente> arg0, Ambiente arg1, Ambiente arg2){
				
				actual = arg2;
				mostrarTablaDetalles(actual);
			}		
		});
		cargarTabla();
		edicion(false);
		enableboton();
	}	
	
	private void mostrarTablaDetalles(Ambiente mostrar){
		if (mostrar != null){
			tfDescripcion.setText(mostrar.getDescripcion());			
		}else{
			tfDescripcion.setText("");			
		}
	}
	
	private void cargarTabla(){
		ObservableList ordenn = FXCollections.observableArrayList(); 
		ordenn = Tabla.getSortOrder();   // para mantener ordenado después de grabar!!!
		tablaarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Ambiente");
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

	private void edicion(Boolean edit){
		if (edit){
			tfDescripcion.setDisable(false);
			tfComentario.setDisable(false);
		}else{
			tfDescripcion.setDisable(true);
			tfComentario.setDisable(true);
		}
	}

	private void disableboton(){
		botonNuevo.setDisable(true);
		botonModifica.setDisable(true);
		botonBorrar.setDisable(true);
		botonCancela.setDisable(false);
		botonGuarda.setDisable(false);
	}
	
	private void enableboton(){
		
		botonCancela.setDisable(true);
		botonGuarda.setDisable(true);
		botonNuevo.setDisable(false);
		botonModifica.setDisable(false);
		botonBorrar.setDisable(false);
	}
	
	@FXML
	private void clickbotonNuevo(){
		actual = new Ambiente();
		disableboton();
		mostrarTablaDetalles(null);
		edicion(true);
		modifica = false;
		tfDescripcion.requestFocus();
	}
	
	@FXML
	private void clickbotonModifica(){
		
		Ambiente modi = Tabla.getSelectionModel().getSelectedItem();
		actual = modi;
		
		if (modi != null){
			edicion(true);
			disableboton();
		}else{
			
			Dialogs.showWarningDialog(Principal.getStagePrincipal(), 
                    "Por Favor, Seleccione un Elemento de la Tabla", 
                    "Por Seleccionar...",
                    "Modificar Elemento" );
					
		}
		modifica = true;
	}
	
	@FXML
	private void clickbotonBorrar(){
		int borrar = Tabla.getSelectionModel().getSelectedIndex();
		Ambiente Dborrar = Tabla.getSelectionModel().getSelectedItem();
		
		if (borrar > -1){
			
            DialogResponse i = Dialogs.showConfirmDialog(Principal.getStagePrincipal(), 
                    "Está seguro que desea eliminar elemento?", 
                    "Por eliminar...",
                    "Eliminar Elemento" );
            if (i==DialogResponse.YES) 
            {
            	Tabla.getItems().remove(borrar);
				try{
					Session sesion = Principal.fabrica.getCurrentSession();
					sesion.beginTransaction();
					sesion.delete(Dborrar);
					sesion.getTransaction().commit();
				}catch(HibernateException e2){
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
	
	@FXML
	private void clickbotonGuarda(){
		System.out.println("entro a guardar");
		
		DialogResponse i = Dialogs.showConfirmDialog(Principal.getStagePrincipal(), 
                "Está seguro que desea Guardar ese elemento?", 
                "Por Guardar...",
                "Guardar Elemento" );
        if (i==DialogResponse.YES) 
        {
			int sel = Tabla.getSelectionModel().getSelectedIndex();
			actual.setDescripcion(tfDescripcion.getText());
			
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
		Ambiente sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		mostrarTablaDetalles(actual);
		modifica = false;
		edicion(false);
		enableboton();
	}
}


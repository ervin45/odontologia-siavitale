package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Esfuerzo;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EsfuerzoController {

	@FXML
	private Label titulo;
	
	@FXML
	private TableColumn<Esfuerzo, String> ColCodigo;
	
	@FXML
	private TableColumn<Esfuerzo, String> ColDescripcion;
	
	@FXML
	private TableView<Esfuerzo> Tabla;
	
	@FXML
	private TextField tfDescripcion;
	
	@FXML 
	private TextField tfComentario;
	
	@FXML
	private TextField tfCodigo;
	
	private Esfuerzo actual = null;
	
	private ObservableList<Esfuerzo> tablaarray = FXCollections.observableArrayList();
	
	@FXML
	private Button botonBorrar;
	
	@FXML
	private Button botonNuevo;

	@FXML
	private Button botonModifica;

	@FXML
	private Button botonCancela;

	@FXML
	private Button botonGuarda;
	
	private Boolean modifica = false;
	
	public EsfuerzoController(){		
	}
	
	@FXML
	private void initialize(){
		titulo.setText("Esfuerzo");
		
		ColCodigo.setCellValueFactory(new PropertyValueFactory<Esfuerzo, String>("Codigo"));
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<Esfuerzo, String>("Descripcion"));		
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		mostrarTablaDetalles(null);
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Esfuerzo>(){

			@Override
			public void changed(ObservableValue<? extends Esfuerzo> arg0,
					Esfuerzo arg1, Esfuerzo arg2) {
				
				actual = arg2;
				mostrarTablaDetalles(actual);
			}			
		});
		
		cargarTabla();
		edicion(false);
		enableboton();		
	}
	
	private void mostrarTablaDetalles(Esfuerzo mostrar) {
		if (mostrar != null)
		{
			tfDescripcion.setText(mostrar.getDescripcion());
		}else
		{
			tfDescripcion.setText("");	
		}
	}
	
	private void cargarTabla() {
		// TODO Auto-generated method stub
		ObservableList ordenn = FXCollections.observableArrayList(); 
		ordenn = Tabla.getSortOrder();   // para mantener ordenado después de grabar!!!
		tablaarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Esfuerzo");
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
	
	private void edicion(Boolean edit)
	{
		if (edit){
			tfDescripcion.setDisable(false);
			tfComentario.setDisable(false);
		}else{
			tfDescripcion.setDisable(true);
			tfComentario.setDisable(true); 
		}
	}
	
	private void disableboton()
	{
		botonNuevo.setDisable(true);
		botonBorrar.setDisable(true);
		botonGuarda.setDisable(false);
		botonCancela.setDisable(false);
		botonModifica.setDisable(true);
	}
	
	private void enableboton()
	{
		botonNuevo.setDisable(false);
		botonBorrar.setDisable(false);
		botonModifica.setDisable(false);
		botonGuarda.setDisable(true);
		botonCancela.setDisable(true);
	}
	
	@FXML
	private void clickbotonBorrar()
	{
		int borrar = Tabla.getSelectionModel().getSelectedIndex();
		Esfuerzo Dborrar = Tabla.getSelectionModel().getSelectedItem();
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
	
	@FXML
	private void clickbotonNuevo() 
	{
		actual = new Esfuerzo();
		disableboton();
		mostrarTablaDetalles(null);
		edicion(true);
		modifica = false;
		tfDescripcion.requestFocus();
	}

	
	@FXML
	private void clickbotonModifica()
	{
		Esfuerzo modi = Tabla.getSelectionModel().getSelectedItem();
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
		modifica = true;
	}
	
	@FXML
	private void clickbotonGuarda()
	{
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
	private void clickbotonCancela()
	{
		Esfuerzo sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		mostrarTablaDetalles(actual);
		modifica = false;
		edicion(false);
		enableboton();
	}
}

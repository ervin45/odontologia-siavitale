package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Responsabilidad;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.cell.PropertyValueFactory;

public class ResponsabilidadController {
	
	@FXML
	private Label titulo;
	
	@FXML
	private TableColumn<Responsabilidad, String> ColCodigo;
	
	@FXML
	private TableColumn<Responsabilidad, String> ColDescripcion;
	
	@FXML
	private TableView<Responsabilidad> Tabla;
	
	@FXML
	private TextField tfDescripcion;
	
	@FXML 
	private TextField tfComentario;
	
	@FXML
	private TextField tfCodigo;
	
	private Responsabilidad actual = null;
	
	private ObservableList<Responsabilidad> tablaarray = FXCollections.observableArrayList();
	
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
	
	public ResponsabilidadController(){		
	}
	
	@FXML
	private void initialize(){
		titulo.setText("Responsabilidad");
		
		ColCodigo.setCellValueFactory(new PropertyValueFactory<Responsabilidad, String>("Codigo"));
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<Responsabilidad, String>("Descripcion"));
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		mostrarTablaDetalles(null);
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Responsabilidad>(){
			
			@Override
			public void changed(ObservableValue<? extends Responsabilidad> arg0,
					Responsabilidad arg1, Responsabilidad arg2) {
				
				actual = arg2;
				mostrarTablaDetalles(actual);
			}
			
		});		
		cargarTabla();	
		edicion(false);
		enableboton();
	}
	
	private void mostrarTablaDetalles(Responsabilidad mostrar) {
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
		ordenn = Tabla.getSortOrder();   // para mantener ordenado despu�s de grabar!!!
		tablaarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Responsabilidad");
            tablaarray = FXCollections.observableArrayList(queryResult.list());  
			sesion.getTransaction().commit();
			Tabla.setItems(tablaarray);
			if (ordenn.size()>0)		// para mantener ordenado despu�s de grabar!!! el TableView
			{							// Esta modificaci�n debe ir en todos los controllers... 
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
		Responsabilidad Dborrar = Tabla.getSelectionModel().getSelectedItem();
		System.out.println("Entro� en Bot�n Borrar!!!!");
		if (borrar > -1)
		{
			DialogResponse i = Dialogs.showConfirmDialog(Principal.getStagePrincipal(), 
                    "Est� seguro que desea eliminar elemento?", 
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
		actual = new Responsabilidad();
		disableboton();
		mostrarTablaDetalles(null);
		edicion(true);
		modifica = false;
		tfDescripcion.requestFocus();
	}

	
	@FXML
	private void clickbotonModifica()
	{
		Responsabilidad modi = Tabla.getSelectionModel().getSelectedItem();
		actual = modi;
		System.out.println("Entro� en Bot�n Borrar!!!!");
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
                "Est� seguro que desea Guardar ese elemento?", 
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
		Responsabilidad sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		mostrarTablaDetalles(actual);
		modifica = false;
		edicion(false);
		enableboton();
	}

}

package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.DocProveedor;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class DocProveedorController {

	@FXML private TableView<DocProveedor> Tabla;
	@FXML private TableColumn<DocProveedor, Boolean> ColPropuesta;
	@FXML private TableColumn<DocProveedor, String> ColTipoDoc;
	@FXML private TableColumn<DocProveedor, String> ColDocumento;
	@FXML private TableColumn<DocProveedor, String> ColControl;
	@FXML private TableColumn<DocProveedor, String> ColProveedor;
	@FXML private TableColumn<DocProveedor, String> ColDescripcion;
	@FXML private TableColumn<DocProveedor, Double> ColMontoExento;
	@FXML private TableColumn<DocProveedor, String> ColBaseImponible;
	@FXML private TableColumn<DocProveedor, String> ColIva;
	@FXML private TableColumn<DocProveedor, String> ColMontoTotal;
	@FXML private TextField tfFecha;
	@FXML private Button botonBorrar;
	@FXML private Button botonNuevo;
	@FXML private Button botonModifica;
	@FXML private Button botonCancela;
	@FXML private Label titulo;
	@FXML private Button botonGuarda;
	
	private ObservableList<DocProveedor> tablaarray = FXCollections.observableArrayList();
	private DocProveedor actual = null;

	DetalleDocProveedorV DetalleDocProveedorVentana;
	
	public DocProveedorController(){
		
	}

	@FXML private void initialize() {
		
		
		titulo.setText("Documentos Proveedores y Beneficiarios");

		ColPropuesta.setCellValueFactory(new PropertyValueFactory("Propuesta"));

		ColPropuesta.setCellFactory(new Callback<TableColumn<DocProveedor, Boolean>, TableCell<DocProveedor, Boolean>>(){

			@Override
			public TableCell<DocProveedor, Boolean> call(
					TableColumn<DocProveedor, Boolean> arg0) {
				// TODO Auto-generated method stub
				return new CheckBoxTableCell<DocProveedor, Boolean>();
			}
			
		}
		);
		
		Tabla.setEditable(true);
		
		
		ColTipoDoc.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("tipoDocumento.Desccripcion"));
		ColDocumento.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("Documento"));
		ColControl.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("Control"));
		ColProveedor.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("proveedor.Razon"));
		
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("Descripcion"));
		
		ColDescripcion.setCellFactory(TextFieldTableCell.<DocProveedor>forTableColumn());
		ColDescripcion.setOnEditCommit(
				new EventHandler<CellEditEvent<DocProveedor, String>>() {

					@Override
					public void handle(CellEditEvent<DocProveedor, String> t) {
						// TODO Auto-generated method stub
						((DocProveedor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescripcion(t.getNewValue());
					}
					
				}
				
			);
		
		ColMontoExento.setCellValueFactory(new PropertyValueFactory<DocProveedor, Double>("MontoExento"));

		ColMontoExento.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<DocProveedor, Double>>(){

					@Override
					public void handle(CellEditEvent<DocProveedor, Double> event) {
						// TODO Auto-generated method stub
						((DocProveedor)event.getTableView().getItems().get(
								event.getTablePosition().getRow())).setMontoExento(event.getNewValue()
								);
					}
					
				}
				);
		
		ColBaseImponible.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("BaseImponible"));
		ColIva.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("MontoIva"));
		ColMontoTotal.setCellValueFactory(new PropertyValueFactory<DocProveedor, String>("MontoTotal"));

		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
//		mostrarTablaDetalles(null);
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DocProveedor>(){

			@Override
			public void changed(ObservableValue<? extends DocProveedor> arg0,
					DocProveedor arg1, DocProveedor arg2) {
				// TODO Auto-generated method stub
				actual = arg2;
//				mostrarTablaDetalles(actual);
			}
			
		});				
		cargarTabla();	
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
			Query queryResult = sesion.createQuery("from DocProveedor");
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

//	private void mostrarTablaDetalles(DocProveedor mostrar) {
//		if (mostrar != null)
//		{
////			tfCodigo.setText(mostrar.getCodigo());
////			tfDescripcion.setText(mostrar.getDescripcion());
////			tfComentario.setText(mostrar.getComentario());
////			tfStatus.setText(mostrar.getStatus());
//		}else
//		{
////			tfCodigo.setText("");
////			tfDescripcion.setText("");
////			tfComentario.setText("");
////			tfStatus.setText("");			
//		}
//	}
//	
	
	@FXML private void clickbotonBorrar()
	{
		int borrar = Tabla.getSelectionModel().getSelectedIndex();
		DocProveedor Dborrar = Tabla.getSelectionModel().getSelectedItem();
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
//		mostrarTablaDetalles(null);
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
	
	private void edicion(Boolean edit)
	{
		if (edit)
		{
//			tfCodigo.setEditable(true);
//			tfDescripcion.setEditable(true);
//			tfComentario.setEditable(true);
//			tfStatus.setEditable(true);						
//			tfCodigo.setDisable(false);
//			tfDescripcion.setDisable(false);
//			tfComentario.setDisable(false);
//			tfStatus.setDisable(false);						

		}else
		{
//			tfCodigo.setDisable(true);
//			tfDescripcion.setDisable(true);
//			tfComentario.setDisable(true);
//			tfStatus.setDisable(true);
//			tfCodigo.setEditable(false);
//			tfDescripcion.setEditable(false);
//			tfComentario.setEditable(false);
//			tfStatus.setEditable(false);				

		}
	}
	
	@FXML private void clickbotonNuevo() 
	{
		if (DetalleDocProveedorVentana==null)
		{
			DetalleDocProveedorVentana = new DetalleDocProveedorV();
			DetalleDocProveedorVentana.setDocumento(null);
			DetalleDocProveedorVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			DetalleDocProveedorVentana.setDocumento(null);
			DetalleDocProveedorVentana.mostrar();
		}
	}

	
	@FXML private void clickbotonModifica()
	{
		DocProveedor modi = Tabla.getSelectionModel().getSelectedItem();
		actual = modi;
		System.out.println("Entró en Botón Borrar!!!!");
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
	
	@FXML private void clickbotonGuarda()
	{
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
	
	@FXML private void clickbotonCancela()
	{
		DocProveedor sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		edicion(false);
		enableboton();
	}
	
	
	private void CancelarEdicion()
	{
		DocProveedor sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		edicion(false);
		enableboton();
	}

	@FXML private void clickbotonAnterior()
	{
	}


	@FXML private void clickbotonSiguiente()
	{
	}

	
	
}

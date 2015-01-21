package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Presentacion;
import data.UndMedida;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PresentacionController {

	private static AnchorPane root;	
	private static Stage stage = new Stage();
	
	private static double initx = 0;
	private static double inity = 0;

	@FXML
	private TableView<Presentacion> Tabla;
	
	@FXML
	private TableColumn<Presentacion, String> ColCodigo;
	
	@FXML
	private TableColumn<Presentacion, String> ColCantidad;

	@FXML
	private TableColumn<Presentacion, String> ColUndMedida;

	@FXML
	private TextField tfCodigo;
	
	@FXML
	private TextField tfCantidad;
	
	@FXML
	private ComboBox cbUndMedida;
	
	@FXML
	private TextField tfComentario;
	
	@FXML
	private TextField tfStatus;
	
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
	
	private ObservableList<Presentacion> tablaarray = FXCollections.observableArrayList();
	
	private ObservableList<UndMedida> comboarray = FXCollections.observableArrayList();
	
	private Presentacion actual = null;

	private Boolean modifica = false;

//	public static void mostrarPresentacion() {
//		
//		try {
//			FXMLLoader cargador2 = new FXMLLoader(Principal.class.getResource("DescriGralView.fxml"));
//			root = (AnchorPane) cargador2.load();
//			stage.setScene(new Scene(root, Color.WHITE));
//			stage.initStyle(StageStyle.UNDECORATED);
////			stage.setResizable(true);
////			stage.setTitle("Descripción General");
//			stage.initModality(Modality.NONE);
//			stage.initOwner(Principal.getStagePrincipal());
//			stage.show();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	    root.setOnMousePressed(new EventHandler<MouseEvent>() {
//	        public void handle(MouseEvent me) {
//	            initx = me.getScreenX() - Principal.getStagePrincipal().getX();// - me.getSceneX(); 
//	            inity = me.getScreenY() - Principal.getStagePrincipal().getY();
//	        	System.out.println("Pasó por aquí... Mouse Pressed!!!");
//	        }
//	    });
//	    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//
//	        public void handle(MouseEvent me) {
//	        	Principal.getStagePrincipal().setX(me.getScreenX() - initx);
//	        	Principal.getStagePrincipal().setY(me.getScreenY() - inity);
//	        	System.out.println("Pasó por aquí... Mouse Dragged!!!");
//	        }
//	    });
//		
//	}

	
	public PresentacionController(){
		
	}

	@FXML
	private void initialize() {
		
		titulo.setText("Presentación de Productos");
		
		ColCodigo.setCellValueFactory(new PropertyValueFactory<Presentacion, String>("Codigo"));
		ColCantidad.setCellValueFactory(new PropertyValueFactory<Presentacion, String>("Cantidad"));
		ColUndMedida.setCellValueFactory(new PropertyValueFactory<Presentacion, String>("UndMedida"));

		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		mostrarTablaDetalles(null);
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Presentacion>(){

			@Override
			public void changed(ObservableValue<? extends Presentacion> arg0,
					Presentacion arg1, Presentacion arg2) {
				// TODO Auto-generated method stub
				actual = arg2;
				mostrarTablaDetalles(actual);
			}
			
		});		
		cargarTabla();	
		cargarCombo();
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
			Query queryResult = sesion.createQuery("from Presentacion");
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

	private void cargarCombo() {
		// TODO Auto-generated method stub
		comboarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from UndMedida");
            comboarray = FXCollections.observableArrayList(queryResult.list());  
			sesion.getTransaction().commit();
			cbUndMedida.setItems(comboarray);
		} catch (HibernateException e2) {

			e2.printStackTrace();
		}
	}

	
	private void mostrarTablaDetalles(Presentacion mostrar) {
		if (mostrar != null)
		{
//			tfCodigo.setText(""+mostrar.getCodigo());
			tfCantidad.setText(""+mostrar.getCantidad());
			cbUndMedida.getSelectionModel().select(mostrar.getUndMedida());
//			tfDescripcion.setText(mostrar.getDescripcion());
//			tfComentario.setText(mostrar.getComentario());
//			tfStatus.setText(mostrar.getStatus());
		}else
		{
//			tfCodigo.setText("");
			tfCantidad.setText("");
			cbUndMedida.getSelectionModel().select(null);
//			tfDescripcion.setText("");
//			tfComentario.setText("");
//			tfStatus.setText("");			
		}
	}
	
	
	@FXML
	private void clickbotonBorrar()
	{
		int borrar = Tabla.getSelectionModel().getSelectedIndex();
		Presentacion Dborrar = Tabla.getSelectionModel().getSelectedItem();
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
			cbUndMedida.setDisable(false);
			tfCantidad.setDisable(false);
//			tfDescripcion.setDisable(false);
//			tfComentario.setDisable(false);
//			tfStatus.setDisable(false);						

		}else
		{
//			tfCodigo.setDisable(true);
			cbUndMedida.setDisable(true);
			tfCantidad.setDisable(true);
//			tfDescripcion.setDisable(true);
//			tfComentario.setDisable(true);
//			tfStatus.setDisable(true);
//			tfCodigo.setEditable(false);
//			tfDescripcion.setEditable(false);
//			tfComentario.setEditable(false);
//			tfStatus.setEditable(false);				

		}
	}
	
	@FXML
	private void clickbotonNuevo() 
	{
		cargarCombo();
		actual = new Presentacion();
		disableboton();
		mostrarTablaDetalles(null);
		edicion(true);
		modifica = false;
		tfCantidad.requestFocus();
	}

	
	@FXML
	private void clickbotonModifica()
	{
		Presentacion modi = Tabla.getSelectionModel().getSelectedItem();
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
			actual.setCantidad(tfCantidad.getText());
			actual.setUndMedida((UndMedida) cbUndMedida.getSelectionModel().getSelectedItem());
//			actual.setStatus(tfStatus.getText());
//			actual.setCodigo(Integer.parseInt(tfCodigo.getText()));
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
		Presentacion sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		mostrarTablaDetalles(actual);
		modifica = false;
		edicion(false);
		enableboton();
	}
	
	
}

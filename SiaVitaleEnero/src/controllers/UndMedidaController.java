package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.UndMedida;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class UndMedidaController {

	private static AnchorPane root;	
	private static Stage stage = new Stage();
	
	private static double initx = 0;
	private static double inity = 0;

	@FXML
	private TableView<UndMedida> Tabla;
	
	@FXML
	private TableColumn<UndMedida, String> ColCodigo;
	
	@FXML
	private TableColumn<UndMedida, String> ColDescripcion;
	
	@FXML
	private TextField tfCodigo;
	
	@FXML
	private TextField tfDescripcion;
	
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
	
	private ObservableList<UndMedida> tablaarray = FXCollections.observableArrayList();
	
	private UndMedida actual = null;

	private Boolean modifica = false;

//	public static void mostrarDescriGral() {
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

	
//	public UndMedidaController(){
//		
//	}

	@FXML
	private void initialize() {
		
		titulo.setText("Unidad de Medida");
		
		ColCodigo.setCellValueFactory(new PropertyValueFactory<UndMedida, String>("Codigo"));
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<UndMedida, String>("Descripcion"));
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		mostrarTablaDetalles(null);
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UndMedida>(){

			@Override
			public void changed(ObservableValue<? extends UndMedida> arg0,
					UndMedida arg1, UndMedida arg2) {
				// TODO Auto-generated method stub
					actual = arg2;
					mostrarTablaDetalles(actual);
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
			Query queryResult = sesion.createQuery("from UndMedida");
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

	private void mostrarTablaDetalles(UndMedida mostrar) {
		if (mostrar != null)
		{
			tfCodigo.setText(mostrar.getCodigo());
			tfDescripcion.setText(mostrar.getDescripcion());
			tfComentario.setText(mostrar.getComentario());
			tfStatus.setText(mostrar.getStatus());
		}else
		{
			tfCodigo.setText("");
			tfDescripcion.setText("");
			tfComentario.setText("");
			tfStatus.setText("");			
		}
	}
	
	
	@FXML
	private void clickbotonBorrar()
	{
		int borrar = Tabla.getSelectionModel().getSelectedIndex();
		UndMedida Dborrar = Tabla.getSelectionModel().getSelectedItem();
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
			tfCodigo.setDisable(false);
			tfDescripcion.setDisable(false);
			tfComentario.setDisable(false);
			tfStatus.setDisable(false);						

		}else
		{
			tfCodigo.setDisable(true);
			tfDescripcion.setDisable(true);
			tfComentario.setDisable(true);
			tfStatus.setDisable(true);
//			tfCodigo.setEditable(false);
//			tfDescripcion.setEditable(false);
//			tfComentario.setEditable(false);
//			tfStatus.setEditable(false);				

		}
	}
	
	@FXML
	private void clickbotonNuevo() 
	{
		actual = new UndMedida();
		disableboton();
		mostrarTablaDetalles(null);
		edicion(true);
		modifica = false;
		tfCodigo.requestFocus();
	}

	
	@FXML
	private void clickbotonModifica()
	{
		UndMedida modi = Tabla.getSelectionModel().getSelectedItem();
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
			actual.setComentario(tfComentario.getText());
			actual.setStatus(tfStatus.getText());
			actual.setCodigo(tfCodigo.getText());
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
		UndMedida sel = Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		mostrarTablaDetalles(actual);
		modifica = false;
		edicion(false);
		enableboton();
	}
	
	
}

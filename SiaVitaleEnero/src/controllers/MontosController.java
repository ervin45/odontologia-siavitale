package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


import data.Estado;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MontosController {
	
	
	@FXML
	private TableView<Estado> Tabla;
	
	@FXML//ColId
	private TableColumn<Estado, String> ColCodigo;
	
	@FXML//ColDescripcion
	private TableColumn<Estado, String> ColNombre;
	
	@FXML
	private TextField tfCodigo;
	
	@FXML
	private TextField tfDescripcion;
		
	@FXML
	private Label titulo;
	
	@FXML
	private Button botonGuarda;
	
	private ObservableList<Estado> estadoarray = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox cbedos;
	
	private Estado actual = null;
		
	@FXML
	private TextField tfMontoExento;
	
	@FXML
	private TextField tfBaseImponible;
	
	@FXML
	private TextField tfIva;
	
	@FXML
	private TextField tfMontoTotal;
	
	
	
	public MontosController(){
		
	}

	@FXML
	private void initialize() {
		
		titulo.setText("Montos");
		
		ColCodigo.setCellValueFactory(new PropertyValueFactory<Estado, String>("Id"));
		ColNombre.setCellValueFactory(new PropertyValueFactory<Estado, String>("Descripcion"));
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);	
		
		edicion(true);
		disableboton();
		
		
		
		//getDecimal();
	}
	 
	@FXML
	public void acciontextfield(){
		
		System.out.println("Monto exento " + tfMontoExento.getText());
		System.out.println("Base Imponible " + tfBaseImponible.getText());
		
		if ((tfMontoExento.getText().isEmpty()==false)&&(tfBaseImponible.getText().isEmpty()==false)){
			System.out.println("Ambos campos tienen valor");
			
			Double me = Double.parseDouble(tfMontoExento.getText());
			Double bi = Double.parseDouble(tfBaseImponible.getText());
			Double iva = bi*0.12;
			Double mt = me+bi+iva;
			tfIva.setText(iva.toString());
			tfMontoTotal.setText(mt.toString());
		}else if ((tfMontoExento.getText().isEmpty()==true)&&(tfBaseImponible.getText().isEmpty()==false)){
			System.out.println("Ingresar monto exento");
			tfIva.setText("0.00");
			tfMontoTotal.setText("0.00");
		}else if ((tfMontoExento.getText().isEmpty()==false)&&(tfBaseImponible.getText().isEmpty()==true)){
			System.out.println("Ingresar base imponible");
			tfIva.setText("0.00");
			tfMontoTotal.setText("0.00");
		}else if ((tfMontoExento.getText().isEmpty()==true)&&(tfBaseImponible.getText().isEmpty()==true)){
			System.out.println("Ambos campos estan vacíos");
			tfIva.setText("0.00");
			tfMontoTotal.setText("0.00");
		}		
	}
	
	
	public String getDecimal(){
		System.out.println("hooolaaaa");
		String rr = "198,57889";
		NumberFormat nf = new DecimalFormat("#,##0.00");
		nf.format(2.00);
		nf.setMaximumFractionDigits(2);
		nf.setRoundingMode(RoundingMode.DOWN);
		Number parsedNumber;
		try {
			parsedNumber = nf.parse(rr);
			System.out.println(parsedNumber + " .... ");
			BigDecimal newValue = new BigDecimal(parsedNumber.toString());
			System.out.println(newValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rr;
	}
	//cada letra onKeyPressed="#teclaaqui" 
	@FXML
	private void teclaaqui(){
		System.out.println("tecla");
	}
	
	//onKeyReleased="#teclados"... tambien acciona por cada letra
	@FXML
	private void teclados(){
		System.out.println("tecleo!!    "+tfDescripcion.getText());
		
		estadoarray.clear();
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Estado where descripcion like '%" + tfDescripcion.getText() + "%' order by Descripcion");
            estadoarray = FXCollections.observableArrayList(queryResult.list());  
			sesion.getTransaction().commit();											
		}catch (HibernateException e2) {
			e2.printStackTrace();
		}
		cbedos.setItems(estadoarray);		
		System.out.println(estadoarray.size());
		
		Tabla.setItems(estadoarray);
		//int sel = Tabla.getSelectionModel().getSelectedIndex();
		//actual.setDescripcion(descripcion)
	}
	
	@FXML
	private void clickbotonBorrar()
	{
		
	}
	
	
	private void disableboton()
	{		
		botonGuarda.setDisable(false);
	}
	
	private void enableboton()
	{
		botonGuarda.setDisable(true);
	}
	
	private void edicion(Boolean edit)
	{
		if (edit)
		{
			tfDescripcion.setDisable(false);
		}else
		{
			tfDescripcion.setDisable(true);
		}
	}
	
	@FXML
	private void clickbotonGuarda()
	{
		
	}
	
	
}
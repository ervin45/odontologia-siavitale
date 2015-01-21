
package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import data.DocProveedor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DetalleDocProveedorController {

	@FXML private Label titulo;
	@FXML private Button btGuardar;
	@FXML private Button btCancelar;
	@FXML private TextField tfDocumento;
	@FXML private TextField tfFecha;
	@FXML private TextField tfControl;
	@FXML private TextField tfDescripcion;
	@FXML private TextField tfMontoExento;
	@FXML private TextField tfBaseImponible;
	@FXML private TextField tfMontoIva;
	@FXML private TextField tfMontoTotal;
	
	@FXML private ComboBox cbProveedor;
	@FXML private ComboBox cbTipoDocumento;

	SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/yyyy");
	
	private DocProveedor actual;
	
	@FXML private void initialize()
	{
		titulo.setText("Actualizar Documento Proveedor");
		
	}
	
	public void setDocumento(DocProveedor docc)
	{
		actual = docc;
		refreshDocProveedor();
	}
	
	
	private void refreshDocProveedor()
	{
		if (actual!= new DocProveedor())
		{
			tfDocumento.setText("");
			tfFecha.setText("");
			tfControl.setText("");
			tfDescripcion.setText("");
			tfMontoExento.setText("");
			tfBaseImponible.setText("");
			tfMontoIva.setText("");
			tfMontoTotal.setText("");
		}else
		{
			tfDocumento.setText(actual.getDocumento());
			tfFecha.setText(""+formatofecha.format(actual.getFecha()));
			tfControl.setText(actual.getControl());
			tfDescripcion.setText(actual.getDescripcion());
			tfMontoExento.setText(""+actual.getMontoExento());
			tfBaseImponible.setText(""+actual.getBaseImponible());
			tfMontoIva.setText(""+actual.getMontoIva());
			tfMontoTotal.setText(""+actual.getMontoTotal());			
		}
	}
	
	
}

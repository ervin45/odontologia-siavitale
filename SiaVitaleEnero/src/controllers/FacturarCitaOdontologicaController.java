package controllers;
import groovy.model.DefaultTableModel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hibernate.Query;
import org.hibernate.Session;

import data.CitaOdontologica;
import data.Factura;
import data.Paciente;
import data.Personal;
import data.PrecioServicioOdontologico;
import data.RazonSocial;
import data.ServicioOdontologico;
import data.itemsFactura;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FacturarCitaOdontologicaController{	
	
	private Ventanas ProgramaPrincipal = new Ventanas();
	
	private ObservableList<CitaOdontologica> citaOdontologicaList = FXCollections.observableArrayList();
	private ObservableList<CitaOdontologica> citaOdontologicaSeleccionadaList = FXCollections.observableArrayList();
	
	@FXML
	private ChoiceBox cbServicioOdontologico;
	private ObservableList<ServicioOdontologico> serviciosOdontologicosListCB = FXCollections.observableArrayList();
	private ObservableList<String> opcionServicioOdontologico = FXCollections.observableArrayList();	
	
	@FXML
	private TableView<CitaOdontologica> tvCitas; 
	
	@FXML
	private TableColumn<CitaOdontologica,String> tcDescripcion;
	
	@FXML
	private TableView<CitaOdontologica> tvCitasSeleccionadas;
	
	@FXML
	private TableColumn<CitaOdontologica,String> tcDescripcionCS;	
		
	private ObservableList<PrecioServicioOdontologico> precioSOListItems = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<String,String> tcDescripcionSO;
	
	@FXML
	private TableView<PrecioServicioOdontologico> tvItems;
	
	@FXML
	private TableColumn<PrecioServicioOdontologico,String> tcDescripcionItem;
	
	@FXML
	private TableColumn<PrecioServicioOdontologico,String> tcCostoItem;
	
	private ObservableList<Double> precioList = FXCollections.observableArrayList();
	
	@FXML
	private Label lFecha;
	
	@FXML
	private TextField tfCedulaPaciente;
	
	@FXML
	private Button bMostrarCitas;
	
	@FXML
	private Button bFacturar;
		
	private String hoy;
	
	private double totalFactura;
	
	@FXML
	private Label lValorTotal;
	
	@FXML
	private TextField tfRIF;
	
	@FXML
	private Label lRazonSocial;
	
	@FXML
	private TextField tfRazonSocial;
	
	private Personal objpersona;
	
	private RazonSocial objRazonSocial;
	
	private Factura objfactura;
	
	private itemsFactura objitem;
	
	@FXML
	private Label lMsjExito;
	
	@FXML
	private Button bBuscarRazonSocial;
	
	@FXML
	private Label lValorRazonSocial;
	
	@FXML
	private Button bRegistrarRazonSocial;
	
	@FXML
	private Label lMsjPaciente;
	
	@FXML
	private Button bNuevaFactura;
	
	private PrecioServicioOdontologico precioSO = new PrecioServicioOdontologico();
	
	private List<itemsFactura> listaItems = new ArrayList<itemsFactura>();
	
	public FacturarCitaOdontologicaController(){	}
		
	@FXML
	private void initialize(){
				
		hoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		System.out.println("en controlador de Facturar cita "+hoy);
		lFecha.setText("FECHA "+hoy);
		
		tfCedulaPaciente.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 lMsjPaciente.setVisible(false);lValorTotal.setVisible(false);
				 lRazonSocial.setVisible(false);lMsjExito.setVisible(false);
				 tvCitas.getItems().clear();	 tvCitasSeleccionadas.getItems().clear();
				 tvItems.getItems().clear();	 tfRIF.setText("");	 
				 bFacturar.setDisable(true); bBuscarRazonSocial.setDisable(true);
				 lValorRazonSocial.setVisible(false);
				 if (tfCedulaPaciente.getText().equals("")){				
					 bMostrarCitas.setDisable(true);
			 	 }else
					 bMostrarCitas.setDisable(false);					
			 }
		});		
		
		tvCitas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue){		        
		    	if(tvCitas.getSelectionModel().getSelectedItem() != null){  
		    		TableViewSelectionModel<CitaOdontologica> selectionModel = tvCitas.getSelectionModel();
		    		ObservableList selectedCells = selectionModel.getSelectedCells();
		    		TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		    		Object val = tablePosition.getTableColumn().getCellData(newValue);
		    		System.out.println("val aqui en tvCitas "+tablePosition.getRow()+"  new value "+tablePosition+ " lista "+citaOdontologicaList.get(tablePosition.getRow()));
		    		posicionarEnCitasSeleccionadas(citaOdontologicaList.get(tablePosition.getRow()));
		    	}
		}});   
		
		tvCitasSeleccionadas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        	    			    	
		    	if ( (tvCitasSeleccionadas.getSelectionModel().getSelectedItem() != null) &&
		    		 (tvCitasSeleccionadas.getSelectionModel().getSelectedIndex() != -1) ){
			    		TableViewSelectionModel<CitaOdontologica> selectionModel = tvCitasSeleccionadas.getSelectionModel();
			    		ObservableList selectedCells = selectionModel.getSelectedCells();
			    		TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			    		Object val = tablePosition.getTableColumn().getCellData(newValue);
			    		System.out.println("CITA SELECCIONADA A BORRAR val "+val+"  new value "+tablePosition);			    		
			    		if (!ContextoCronograma.getInstance().getBanderaTablaEliminarCitaSeleccionada())
			    			eliminarCitaSeleccionada(tablePosition.getRow());
			    		tvCitasSeleccionadas.getSelectionModel().clearSelection();
		    	}
		    	if (tvCitasSeleccionadas.getSelectionModel().getSelectedIndex() == -1){
		    		tvCitasSeleccionadas.getSelectionModel().clearSelection();
		    		ContextoCronograma.getInstance().setBanderaTablaEliminarCitaSeleccionada(false);
		    	}
		}});   

		tvItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        
		    	if ( (tvItems.getSelectionModel().getSelectedItem() != null) && 
		    	  (tvItems.getSelectionModel().getSelectedIndex() != -1) ){  
		    		TableViewSelectionModel<PrecioServicioOdontologico> selectionModel = tvItems.getSelectionModel();
		    		ObservableList selectedCells = selectionModel.getSelectedCells();
		    		TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		    		Object val = tablePosition.getTableColumn().getCellData(newValue);		    		
		    		System.out.println("ITEM SELECCIONADO "+precioSOListItems.get(tablePosition.getRow())+"  new value "+tablePosition);
		    		if (!ContextoCronograma.getInstance().getBanderaTablaEliminarItemSeleccionado())
		    			eliminarItemSeleccionado(tablePosition.getRow());
		    		tvItems.getSelectionModel().clearSelection();
		    	}
		    	if (tvItems.getSelectionModel().getSelectedIndex() == -1){
		    		tvItems.getSelectionModel().clearSelection();
		    		ContextoCronograma.getInstance().setBanderaTablaEliminarItemSeleccionado(false);
		    	}
		}}); 
				
		tcCostoItem.setCellFactory(new Callback<TableColumn<PrecioServicioOdontologico, String>, TableCell<PrecioServicioOdontologico, String>>() {
	        @Override
	        public TableCell<PrecioServicioOdontologico, String> call(TableColumn<PrecioServicioOdontologico, String> param) {
	            return new TableCellFormatCosto();
	        }
	    });
		
		Session sesion1 = openSesion();
		Query queryResultDoctores = sesion1.createQuery("from ServicioOdontologico");	
				
		serviciosOdontologicosListCB = FXCollections.observableArrayList(queryResultDoctores.list());		
		for (int r=0;r<serviciosOdontologicosListCB.size();r++){
			opcionServicioOdontologico.add(serviciosOdontologicosListCB.get(r).getNombre());
		}
		closeSesion(sesion1);
		cbServicioOdontologico.setItems(opcionServicioOdontologico);
		cbServicioOdontologico.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2){
				if (cbServicioOdontologico.getSelectionModel().getSelectedIndex() != -1){
					System.out.println("servicio odontologico: "+arg2.intValue());	
					posicionarItemSeleccionado(arg2.intValue());
				}
		}});			
		
		tfRIF.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 
				 lRazonSocial.setVisible(false);				 tfRazonSocial.setVisible(false);
				 lValorRazonSocial.setVisible(false);
				 bBuscarRazonSocial.setVisible(true); bBuscarRazonSocial.setDisable(false);
				 bRegistrarRazonSocial.setVisible(false); bRegistrarRazonSocial.setDisable(true);	
				 bFacturar.setDisable(true);
				 
				 if (newValue.equals(""))
					 bFacturar.setDisable(true);
				 
			 }
		});		
		
		tfRazonSocial.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
					 				 
					 if (tfRazonSocial.getText().equals(""))
						 bRegistrarRazonSocial.setDisable(true);
					 else
						 bRegistrarRazonSocial.setDisable(false);
				}
		});	
	}
	
	private class TableCellFormatCosto extends TableCell<PrecioServicioOdontologico, String>{
	    @Override
	    protected void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty); 
	        this.setText(item);
	        this.setAlignment(Pos.CENTER_RIGHT);
	    }
	}
	
	private void posicionarItemSeleccionado(int pos){
		System.out.println("pos:  "+pos+"  "+serviciosOdontologicosListCB.get(pos).getNombre()+" "+serviciosOdontologicosListCB.get(pos).getId());
		
		Session s = openSesion();
		Query que = s.createQuery("from PrecioServicioOdontologico where idServicioOdontologico =:num order by Fecha desc");
		que.setInteger("num", serviciosOdontologicosListCB.get(pos).getId());
		que.setMaxResults(1);
		precioSO = (PrecioServicioOdontologico) que.uniqueResult();		
		System.out.println("precio seleccionado "+precioSO.getFecha()+"  "+precioSO.getMonto()+" "+precioSO.getId());				
		closeSesion(s);
		
		precioList.add(precioSO.getMonto());
		totalFactura += precioSO.getMonto();
		
		precioSOListItems.add(precioSO);
		tvItems.setItems(precioSOListItems);
		
		DecimalFormat df = new DecimalFormat("", new DecimalFormatSymbols(Locale.getDefault()));
		lValorTotal.setText(df.format(totalFactura));	
		lValorTotal.setVisible(true);
		validarBotonFacturar();
	
		tcDescripcionItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<PrecioServicioOdontologico, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getServicioOdontologico().getNombre()));
			}			
		});	
		tcCostoItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<PrecioServicioOdontologico, String> arg0) {
				DecimalFormat df = new DecimalFormat("", new DecimalFormatSymbols(Locale.getDefault()));				
				return new SimpleStringProperty(df.format(arg0.getValue().getMonto()));
			}			
		});			
	}
	
	private void posicionarEnCitasSeleccionadas(CitaOdontologica cita){		
		if (citaOdontologicaSeleccionadaList.size()==0)
			citaOdontologicaSeleccionadaList.add(citaOdontologicaSeleccionadaList.size(),cita);
		else if (citaOdontologicaSeleccionadaList.size()>0)
			citaOdontologicaSeleccionadaList.add(citaOdontologicaSeleccionadaList.size(),cita);
		
		validarBotonFacturar();
		
		tvCitasSeleccionadas.setItems(citaOdontologicaSeleccionadaList);
		tcDescripcionCS.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getFecha()));
			}			
		});
	}
	
	private void eliminarItemSeleccionado(int posEliminar){
		ContextoCronograma.getInstance().setBanderaTablaEliminarItemSeleccionado(true);		
		
		totalFactura -= precioSOListItems.get(posEliminar).getMonto();
		precioSOListItems.remove(posEliminar);
		DecimalFormat df = new DecimalFormat("", new DecimalFormatSymbols(Locale.getDefault()));
		lValorTotal.setText(df.format(totalFactura));
		
		validarBotonFacturar();
		
		tcDescripcionItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<PrecioServicioOdontologico, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getServicioOdontologico().getNombre()));
			}			
		});	
		tcCostoItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<PrecioServicioOdontologico, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getMonto()));
			}			
		});	
		tvItems.getSelectionModel().clearSelection();
	}
	
	private void eliminarCitaSeleccionada(int posEliminar){
		ContextoCronograma.getInstance().setBanderaTablaEliminarCitaSeleccionada(true);
		citaOdontologicaSeleccionadaList.remove(posEliminar);	
		
		validarBotonFacturar();
		
		tcDescripcionCS.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
				return new SimpleStringProperty(String.valueOf(arg0.getValue().getFecha()));
			}			
		});		
		tvCitasSeleccionadas.getSelectionModel().clearSelection();
	}
	
	private void validarBotonFacturar(){
		if ((precioSOListItems.size()!=0) && (citaOdontologicaSeleccionadaList.size()!=0))
			bFacturar.setDisable(false);
		else
			bFacturar.setDisable(true);	
	}
	
	@FXML
	private void actionMostrarCita(){
		System.out.println("action mostrar citas!!!!!! "+tfCedulaPaciente.getText());
		lMsjExito.setVisible(false);
		lValorTotal.setText("");
		lValorRazonSocial.setText(""); lValorRazonSocial.setVisible(false);
		bFacturar.setDisable(true);
		
		Session sesion2 = openSesion();		
		Query query2 = sesion2.createQuery("from Personal where Cedula =:ced");
		query2.setString("ced", tfCedulaPaciente.getText());
		query2.setMaxResults(1);
		objpersona = (Personal) query2.uniqueResult();		
		
		if (objpersona!=null){
			Query query3 = sesion2.createQuery("from Paciente where idPersona =:id");
			query3.setInteger("id", objpersona.getId());
			query3.setMaxResults(1);
			Paciente paciente = (Paciente) query3.uniqueResult();
			
			if (paciente!=null){
				Query query4 = sesion2.createQuery("from CitaOdontologica where idPaciente =:idp and asistencia =:cad");
				query4.setString("cad", "libre");
				query4.setInteger("idp", paciente.getId());					
				citaOdontologicaList = FXCollections.observableArrayList(query4.list());

				tvCitas.setItems(citaOdontologicaList);
				tvCitasSeleccionadas.getItems().clear();
				tvItems.getItems().clear();
				tfRIF.setText("");
//				cbServicioOdontologico.getSelectionModel().select(-1);
				tcDescripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CitaOdontologica, String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(CellDataFeatures<CitaOdontologica, String> arg0) {
						return new SimpleStringProperty(String.valueOf(arg0.getValue().getFecha()));
					}			
				});
				if (citaOdontologicaList.size()==0){
					lMsjPaciente.setVisible(true);	lMsjPaciente.setText("Paciente no posee citas");
				}
			}else{
				lMsjPaciente.setVisible(true);	lMsjPaciente.setText("Paciente no registrado");
			}
		}else{
			lMsjPaciente.setVisible(true);	lMsjPaciente.setText("Paciente no registrado");
		}			
		closeSesion(sesion2);	
	}
	
	@FXML
	private void actionBuscarRazonSocial(){
		System.out.println("buscar razon social");
		
		Session sesion2 = openSesion();
		
		Query query2 = sesion2.createQuery("from RazonSocial where numeroDocumento =:num");
		query2.setString("num", tfRIF.getText());
		query2.setMaxResults(1);
		objRazonSocial = new RazonSocial();
		objRazonSocial = (RazonSocial) query2.uniqueResult();
				
		if (objRazonSocial!=null){	
			lValorRazonSocial.setText(objRazonSocial.getRazonSocial());
			lValorRazonSocial.setVisible(true);		tfRazonSocial.setVisible(false);
			bFacturar.setDisable(false);
		}else{
			lRazonSocial.setVisible(true);	lRazonSocial.setText("No se encuentra registrado");
			lValorRazonSocial.setVisible(false);	tfRazonSocial.setVisible(true); tfRazonSocial.setText("");
			bRegistrarRazonSocial.setVisible(true);	bRegistrarRazonSocial.setDisable(true);
			bBuscarRazonSocial.setVisible(false); bBuscarRazonSocial.setDisable(true);
			bFacturar.setDisable(true);
		}			
		closeSesion(sesion2);	
	}
	
	@FXML
	private void actionRegistrarRazonSocial(){
		lRazonSocial.setText("Registrado exitosamente");
		lValorRazonSocial.setVisible(true); lValorRazonSocial.setText(tfRazonSocial.getText());
		bRegistrarRazonSocial.setVisible(false); 
		bRegistrarRazonSocial.setDisable(true);
		bBuscarRazonSocial.setVisible(true); bBuscarRazonSocial.setDisable(false);		
		
		objRazonSocial = new RazonSocial();
		objRazonSocial.setRazonSocial(tfRazonSocial.getText());
		objRazonSocial.setNumeroDocumento(tfRIF.getText());
		
		Session ses = openSesion();
		ses.save(objRazonSocial);
		closeSesion(ses);
		
		lValorRazonSocial.setText(objRazonSocial.getRazonSocial()); lValorRazonSocial.setVisible(true);
		tfRazonSocial.setText(""); tfRazonSocial.setVisible(false);
		bFacturar.setDisable(false);
	}
	
	@FXML
	private void actionFacturar(){
		lMsjExito.setVisible(true);		lValorRazonSocial.setVisible(true);
		lRazonSocial.setVisible(false);
		System.out.println("facturar boton");
		objfactura = new Factura();
		if (!tfRIF.getText().equals("")){
			objfactura.setRazonSocial(objRazonSocial);
			lValorRazonSocial.setText(objRazonSocial.getRazonSocial());		
//			guardar objeto de razon social en el objeto de factura			
		}else{
			objfactura.setPersona(objpersona);
			lValorRazonSocial.setText(objpersona.getNombres()+" "+objpersona.getApellidos());			
//			guardar objeto de paciente-persona en el objeto de factura
		}
//		guardar el monto total en el objeto de factura		
		System.out.println(" uno   "+lValorTotal.getText());
		System.out.println(" dos "+totalFactura);		
		
		objfactura.setMontoTotal(totalFactura);
		
//		guardar el monto de iva en el objeto de factura
//		objf.setIvaTotal(ivaTotal);
		
//		guardar la factura en bd
		Session ses1 = openSesion();
		ses1.save(objfactura);
		closeSesion(ses1);
				
//		recorrer las citas odontologicas y actualizar con el id del recien numero de factura creado
		ObservableList<CitaOdontologica> lco = tvCitasSeleccionadas.getItems();
		Iterator<CitaOdontologica> iteco = lco.iterator();
		System.out.println("cantidad de citas seleccionadas "+lco.size());
	
		CitaOdontologica oco = new CitaOdontologica();
		while (iteco.hasNext()){
//			System.out.println(" *-* "+iteco.next().getFecha());			
			Session ses2= openSesion();			
			oco = (CitaOdontologica) ses2.get(CitaOdontologica.class, iteco.next().getId());
			oco.setAsistencia("ocupado "+objfactura.getId());
			oco.setFactura(objfactura);
			ses2.update(oco);
			closeSesion(ses2);
		}
		
//		guardar para cada item de la tabla de servicio odontologico, un registro en la tabla de item factura 
//		asociada a ese objeto de servicio odontologico mas el objeto de la factura actual
			
		ObservableList<PrecioServicioOdontologico> lso = tvItems.getItems();
		Iterator<PrecioServicioOdontologico> iteso = lso.iterator();		
		System.out.println("cantidad de items "+lso.size());
		
		while (iteso.hasNext()){
			Session ses3 = openSesion();
			objitem = new itemsFactura();
			objitem.setFactura(objfactura);
			PrecioServicioOdontologico precio = new PrecioServicioOdontologico();
			precio = iteso.next();
			objitem.setMonto(precio.getMonto());
			objitem.setServicioOdontologico(precio.getServicioOdontologico());	
			objitem.setNombreSO(precio.getServicioOdontologico().getNombre());
			ses3.save(objitem);
			listaItems.add(objitem);
		
			closeSesion(ses3);
		}
		
		System.out.println("VOY A IMPRIMIR!!!!!!");
		imprimirFactura();
	}
	
	public void imprimirFactura(){
		DefaultTableModel datos;
		
		System.out.println("---------------");
		System.out.println("objeto encabezado factura");
//		System.out.print("id: "+objfactura.getId()+" /p: "+objfactura.getPersona().getNombres()+" /rs: "+objfactura.getRazonSocial()+" /it: "+objfactura.getIvaTotal()+" /mt: "+objfactura.getMontoTotal());		
		System.out.println("\nobjetos items");
				
		Iterator<itemsFactura> itef = listaItems.iterator();
				
		while (itef.hasNext()){
			itemsFactura it = new itemsFactura();
			it = itef.next();
			System.out.println("id: "+it.getId()+" /so: "+it.getServicioOdontologico().getNombre()+" /idf: "+it.getFactura().getId()+" /m: "+it.getMonto()+" /iva: "+it.getIva());
		}
		System.out.println("---------------");
		
		try{			
			Map cabecera = new HashMap();
			System.out.println("---------------2");
			cabecera.put("id",""+objfactura.getId());
			System.out.println("---------------3");
			cabecera.put("fecha", "27/03/2015");
			if (objfactura.getPersona()!=null){
				cabecera.put("pacientenomape",""+objfactura.getPersona().getNombres()+" "+objfactura.getPersona().getApellidos());
			}
			System.out.println("---------------4");
			
			if (objfactura.getRazonSocial()!=null)
				cabecera.put("razonsocial",""+objfactura.getRazonSocial().getRazonSocial());
			System.out.println("---------------5 "+objfactura.getIvaTotal());
			
//			cabecera.put("totaliva", ""+objfactura.getIvaTotal());
			System.out.println("---------------6");
			cabecera.put("totalmonto", ""+objfactura.getMontoTotal());						
			
			System.out.println("ya cargue map");
			JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listaItems);
			JasperReport rfac = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResource("reportes/reporte.jasper"));
			JasperPrint pfac = JasperFillManager.fillReport(rfac, cabecera, data);
	
			System.out.println("Voy a export manager");
			try {
				JasperExportManager.exportReportToPdfFile(pfac,"C:/hibernate/resultado.pdf");
			}catch (JRException exp){
				System.out.println("Está abierto archivo de resultado. Debe cerrarlo para poderlo generar nuevamente!");
			}
			System.out.println("Despues de export manager");
//			try {
//				Runtime.getRuntime().exec("cmd /c start C:/hibernate/resultado.pdf");			
//	//			File file = new File("c:/ticlab2/resultado.pdf");
//	//			file.deleteOnExit();
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
	//		JasperViewer.viewReport(pfac, false);
		
		}catch(JRException e1){
			e1.printStackTrace();
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
	
	public void setProgramaPrincipal(Ventanas ProgramaPrincipal) {
		 System.out.println("Controlador facturar cita odontologica - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
	
	@FXML
	private void actionNuevaFactura(){
		System.out.println("action nueva factura");
		tfCedulaPaciente.setText("");
		tfRazonSocial.setText("");
		tfRIF.setText("");
		tvItems.getItems().clear();
		tvCitasSeleccionadas.getItems().clear();
		tvCitas.getItems().clear();
		DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault()));
		lValorTotal.setText(df.format(0));lValorTotal.setVisible(false);
		lValorRazonSocial.setText("");lValorRazonSocial.setVisible(false);
		lRazonSocial.setText(""); lRazonSocial.setVisible(false);
		cbServicioOdontologico.getSelectionModel().select(-1);
	}
	
	@FXML
	private void actionCancelar(){
		System.out.println("boton cancelar cronograma cita");	
		ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
		Stage stage = (Stage) bNuevaFactura.getScene().getWindow();
		stage.close();		
	}
}
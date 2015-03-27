package controllers;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.CreateKeySecondPass;

import data.PrecioServicioOdontologico;
import data.ServicioOdontologico;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NuevoServicioOdontologicoController{	
	private Ventanas ProgramaPrincipal = new Ventanas();
			
	@FXML
	private Button bGuardar;
	   
	@FXML
	private Button bModificarNombre;
	
	@FXML
	private Button bAgregarPrecio;
	
	@FXML
	private Button bCancelar;
	
	@FXML
	private Button bEliminar;
	
	@FXML
	private Button bNuevo;
	
	@FXML
	private Button bGuardarNuevoPrecio;
	
	@FXML
	private Button bEliminarPrecios;
		
	@FXML
	private TextField tfSO;
	
	@FXML
	private TextField tfPrecio;
	
	@FXML
	private Label lMsj;
	
	@FXML
	private Label lServicioOdontologico;
	
	@FXML
	private Label lPrecio;
	
	@FXML
	private ChoiceBox cbServicioOdontologico;
	
	ObservableList<ServicioOdontologico> SOList = FXCollections.observableArrayList();
	private ObservableList<String> opcionSO = FXCollections.observableArrayList();
	
	@FXML
	private Label lIdSO;
	
	@FXML
	private Label lband;
	
	ObservableList<PrecioServicioOdontologico> precioEliminarList = FXCollections.observableArrayList();
	ObservableList<PrecioServicioOdontologico> precioList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<PrecioServicioOdontologico> tvPrecios;
	
	@FXML
	private TableColumn<PrecioServicioOdontologico, String> tcPrecio;
	
	@FXML
	private TableColumn<PrecioServicioOdontologico, String> tcFecha;
	
	@FXML
	private Label lMsjServOdontologico;
	
	@FXML
	private Label lMsjGuia;
	
	Tooltip ttNuevo = new Tooltip();
	Tooltip ttModificar = new Tooltip();
	Tooltip ttAgregarPrecio = new Tooltip();
	Tooltip ttGuardar = new Tooltip();
	
	private int posPrecioEliminar=0;
	
	public NuevoServicioOdontologicoController(){	}	
	
	@FXML
	private void initialize(){		

		ttNuevo.setText("Nuevo registro");
		bNuevo.setTooltip(ttNuevo);
		
		ttModificar.setText("Modificar nombre de servicio odontologico");
		bModificarNombre.setTooltip(ttModificar);
		
		ttAgregarPrecio.setText("Agregar precio");
		bAgregarPrecio.setTooltip(ttAgregarPrecio);
		
		ttGuardar.setText("Guardar registro");
		bGuardar.setTooltip(ttGuardar);
		
		tcPrecio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<PrecioServicioOdontologico, String> arg0) {
				DecimalFormat df = new DecimalFormat("", new DecimalFormatSymbols(Locale.getDefault()));				
				return new SimpleStringProperty(df.format(arg0.getValue().getMonto()));
			}			
		});
		
		tcFecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioServicioOdontologico, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<PrecioServicioOdontologico, String> arg0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				return new SimpleStringProperty(sdf.format(arg0.getValue().getFecha()));
			}			
		});	
		
		tcFecha.setCellFactory(new Callback<TableColumn<PrecioServicioOdontologico, String>, TableCell<PrecioServicioOdontologico, String>>() {
	        @Override
	        public TableCell<PrecioServicioOdontologico, String> call(TableColumn<PrecioServicioOdontologico, String> param) {
	            return new TableCellFormatFecha();
	        }
	    });
		
		tcPrecio.setCellFactory(new Callback<TableColumn<PrecioServicioOdontologico, String>, TableCell<PrecioServicioOdontologico, String>>() {
	        @Override
	        public TableCell<PrecioServicioOdontologico, String> call(TableColumn<PrecioServicioOdontologico, String> param) {
	            return new TableCellFormatPrecio();
	        }
	    });
				
		Session sesion1 = openSesion();
		Query queryResultSO = sesion1.createQuery("from ServicioOdontologico");	
				
		SOList = FXCollections.observableArrayList(queryResultSO.list());		
		for (int r=0;r<SOList.size();r++){
			opcionSO.add(SOList.get(r).getNombre());
		}
		closeSesion(sesion1);		
		cbServicioOdontologico.setItems(opcionSO);
				
		cbServicioOdontologico.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {	
				if (!opcionSO.isEmpty() && (cbServicioOdontologico.getSelectionModel().getSelectedIndex() != -1) ){
					tvPrecios.getItems().clear();				
					bModificarNombre.setDisable(false);	
					lIdSO.setText(String.valueOf(SOList.get(arg2.intValue()).getId()));
					System.out.println("id so "+lIdSO.getText());
					tfSO.setText(SOList.get(arg2.intValue()).getNombre());
					
					Session s = openSesion();					
						Query que = s.createQuery("from PrecioServicioOdontologico where idServicioOdontologico =:num order by Fecha desc");
						que.setInteger("num", Integer.parseInt(lIdSO.getText()));
						que.setMaxResults(1);
						PrecioServicioOdontologico PSO = new PrecioServicioOdontologico();
						System.out.println("ajaaaaa ojo "+que.uniqueResult());
						if (que.uniqueResult() != null){
							PSO = (PrecioServicioOdontologico) que.uniqueResult();	
							System.out.println("precio seleccionado "+PSO.getFecha()+"  "+PSO.getMonto()+" "+PSO.getId());
							
							DecimalFormat df = new DecimalFormat("", new DecimalFormatSymbols(Locale.getDefault()));
							
							tfPrecio.setText(df.format(PSO.getMonto()));
							Query que2 = s.createQuery("from PrecioServicioOdontologico where idServicioOdontologico =:num order by Fecha desc");
							que2.setInteger("num", Integer.parseInt(lIdSO.getText()));					
							precioList = FXCollections.observableArrayList(que2.list());	
							tvPrecios.setItems(precioList);
							tvPrecios.setVisible(true);			
							System.out.println("size: "+precioList.size());
						}
					closeSesion(s); 
					
					if (lband.getText().equals("modificarnombre")){
						tvPrecios.setDisable(true);	tfPrecio.setDisable(true);	tfSO.setDisable(false);
						bGuardar.setDisable(false);	bGuardar.setVisible(true);
					}else if (lband.getText().equals("agregarprecio")){
						tfPrecio.setText(""); tvPrecios.setDisable(false);
						tfSO.setDisable(true);	 tfPrecio.setDisable(false); tvPrecios.setVisible(true);	
					}else if (lband.getText().equals("nuevo")){
						tfSO.setDisable(false);	 tfPrecio.setDisable(false);
					}else if (lband.getText().equals("eliminar")){
						tfSO.setDisable(true);	 tfSO.setVisible(false);
						tfPrecio.setDisable(true); 	tfPrecio.setVisible(false); 
						tvPrecios.setVisible(true);						
					} 
				}
		}});	
		
		tfSO.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 	lMsj.setVisible(false);
				 	if ( (lband.getText().equals("nuevo") && newValue.equals("") && tfPrecio.getText().equals("")) ||
				 		 (lband.getText().equals("nuevo") && newValue.equals("") && !tfPrecio.getText().equals("")) ||
				 		 (lband.getText().equals("nuevo") && !newValue.equals("") && tfPrecio.getText().equals("")) ){	
				 		bGuardar.setDisable(true);	bGuardar.setVisible(false); bModificarNombre.setDisable(false);
				 	}else if (lband.getText().equals("nuevo") && !newValue.equals("") && !tfPrecio.getText().equals("")){	
				 		bGuardar.setDisable(false); bGuardar.setVisible(true);	//bModificarNombre.setDisable(true);
				 	} 
			 	}   
		});
			
		tfPrecio.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 	lMsj.setVisible(false);
				 	
				 	if ( (lband.getText().equals("nuevo") && newValue.equals("") && tfSO.getText().equals("")) ||
				 		 (lband.getText().equals("nuevo") && newValue.equals("") && !tfSO.getText().equals("")) ||
				 		 (lband.getText().equals("nuevo") && !newValue.equals("") && tfSO.getText().equals("")) ){	
				 		bGuardar.setDisable(true);	bModificarNombre.setDisable(false);
				 	}else if (!newValue.equals("") && lband.getText().equals("nuevo") && !tfSO.getText().equals("")){	
				 		bGuardar.setDisable(false);	//bModificarNombre.setDisable(true);
				 	}	
				 					 	
				 	if (newValue.length()>=1){
					 	char c = newValue.charAt(newValue.length()-1);
					 	
					 	if (c=='0' || c=='1'|| c=='2' || c=='3'	|| c=='4' || c=='5'
					 		|| c=='6' || c=='7'	|| c=='8' || c=='9' || c=='.' || c==','){
					 			
					 	}else{
					 		Locale local = Locale.getDefault();
							DecimalFormat df = new DecimalFormat("", new DecimalFormatSymbols(local));	
							String val =  (newValue.replace(newValue.charAt(newValue.length()-1),' ')).trim();
					 		tfPrecio.setText(df.format(Double.parseDouble(val)));				 		
					 	}
				 	}
				 	if  (newValue.equals("") && lband.getText().equals("nuevo")){	
				 		bGuardar.setDisable(true);	bGuardar.setVisible(false);
				 	}else if ( !newValue.equals("") && lband.getText().equals("nuevo") ){	
				 		bGuardar.setDisable(false);		bGuardar.setVisible(true);
				 	}else if (lband.getText().equals("agregarprecio")){
				 		if (!tfPrecio.getText().equals("")){
							bGuardarNuevoPrecio.setDisable(false);	bGuardarNuevoPrecio.setVisible(true);
						}else{
							bGuardarNuevoPrecio.setDisable(true);   bGuardarNuevoPrecio.setVisible(false);
						}
				 	}
			 	}
		});		
	
		tfSO.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		    	System.out.println("tfso focusedproperty");
		    	if  (lband.getText().equals("modificar")){
		    		bGuardar.setDisable(false);
			 	}		    	
		    }
		});			
	
		tfPrecio.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		    	if  (lband.getText().equals("modificar")){
		    		bGuardar.setDisable(false);
			 	}			 		
		    }
		});	
		
		tvPrecios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        
		    	if ( (tvPrecios.getSelectionModel().getSelectedItem() != null) && 
		    	  (tvPrecios.getSelectionModel().getSelectedIndex() != -1) ){  
		    		TableViewSelectionModel<PrecioServicioOdontologico> selectionModel = tvPrecios.getSelectionModel();
		    		ObservableList selectedCells = selectionModel.getSelectedCells();
		    		TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		    		Object val = tablePosition.getTableColumn().getCellData(newValue);	
		    		posPrecioEliminar=tablePosition.getRow();
		    		mensajeEliminar();
		    	}
//		    	if (tvPrecios.getSelectionModel().getSelectedIndex() == -1){
//		    		tvPrecios.getSelectionModel().clearSelection();
//		    	}
		}}); 
	}
	
	private class TableCellFormatPrecio extends TableCell<PrecioServicioOdontologico, String>{
	    		
		@Override
	    protected void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty);
	        this.setText(item);
	        this.setAlignment(Pos.CENTER_RIGHT);
	    }
	}
	
	private class TableCellFormatFecha extends TableCell<PrecioServicioOdontologico, String>{
	    @Override
	    protected void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty); 
	        this.setText(item);
	        this.setAlignment(Pos.CENTER);
	    }
	}
	
	@FXML
	public void actionGuardar(){
		
		System.out.println("action agregar n so "+lband.getText());
		
		ServicioOdontologico so = new ServicioOdontologico();
				
		if (lband.getText().equals("modificarnombre")){
			so.setId(Integer.parseInt(lIdSO.getText()));
			lMsj.setText("Modificación exitosa");			
		}else
			lMsj.setText("Registro exitoso");
		lMsj.setVisible(true);	
		
		so.setNombre(tfSO.getText());
		
		Session s = openSesion();
			s.saveOrUpdate(so);
		closeSesion(s);
		
		if (!lband.getText().equals("modificarnombre")){
			PrecioServicioOdontologico pso = new PrecioServicioOdontologico();		
			
			pso.setMonto(Double.parseDouble(tfPrecio.getText()));
			pso.setServicioOdontologico(so);
			pso.setFecha(new Date());
			
			Session s2 = openSesion();
				s2.saveOrUpdate(pso);
			closeSesion(s2);
		}else{		
			
			opcionSO.clear();
			cbServicioOdontologico.setItems(opcionSO);
			Session sesion1 = openSesion();
			Query queryResultSO = sesion1.createQuery("from ServicioOdontologico");	
					
			SOList = FXCollections.observableArrayList(queryResultSO.list());		
			for (int r=0;r<SOList.size();r++){
				opcionSO.add(SOList.get(r).getNombre());
			}
			closeSesion(sesion1);		
			cbServicioOdontologico.setItems(opcionSO);
		}
		bModificarNombre.setDisable(false);bGuardar.setDisable(true);
		lServicioOdontologico.setVisible(true);		lPrecio.setVisible(true);
		tfPrecio.setText("");	tfSO.setText("");
	}
	
	@FXML
	public void actionAgregarPrecio(){		
		System.out.println("agregar precio");		lband.setText("agregarprecio");
		tfPrecio.setDisable(false);		tfSO.setDisable(true);
		tfPrecio.setText("");	tfSO.setText("");
		bModificarNombre.setDisable(false);
		bGuardar.setDisable(true);		cbServicioOdontologico.setVisible(true);
		lMsjServOdontologico.setVisible(true);
		lServicioOdontologico.setVisible(true);		lPrecio.setVisible(true);
		tfSO.setVisible(true);	tfPrecio.setVisible(true); tfPrecio.setText("");
		tvPrecios.getItems().clear();tvPrecios.setVisible(false);tvPrecios.setDisable(false);
		lMsjGuia.setText("Seleccione serv. odontologico y luego \n\t\t agregue un nuevo precio");
		bEliminarPrecios.setVisible(false); 
		lMsj.setVisible(false);
		bGuardar.setDisable(true);	bGuardar.setVisible(false);
		cbServicioOdontologico.getSelectionModel().select(-1);	
		reloadcbServOdontologico();
	}
	
	@FXML
	public void actionModificarNombre(){
		System.out.println("action modificar so");
		lband.setText("modificarnombre");
		tfPrecio.setText("");	tfPrecio.setDisable(true); tfPrecio.setVisible(true);
		tfSO.setText(""); 		tfSO.setVisible(true);		tfSO.setDisable(false);
		lPrecio.setVisible(true);	lServicioOdontologico.setVisible(true);
		tvPrecios.getItems().clear(); tvPrecios.setVisible(false);
		cbServicioOdontologico.setVisible(true);lMsjServOdontologico.setVisible(true);
		bGuardar.setDisable(false); bAgregarPrecio.setDisable(false);
		bGuardarNuevoPrecio.setVisible(false);
		lServicioOdontologico.setVisible(true);		
		tfSO.setVisible(true);
		lMsjGuia.setText("Seleccione servicio odontologico y luego \n\t\t modifique su nombre");
		bEliminarPrecios.setVisible(false);	lMsj.setVisible(false);
		bGuardar.setDisable(true);	bGuardar.setVisible(false);
		cbServicioOdontologico.getSelectionModel().select(-1);
		reloadcbServOdontologico();
	}
			
	@FXML
	public void actionEliminar(){  
		System.out.println("eliminar");
		lband.setText("eliminar");
		cbServicioOdontologico.setVisible(true);	lMsjServOdontologico.setVisible(true);
		lServicioOdontologico.setVisible(false);	lPrecio.setVisible(false);
		tfSO.setVisible(false);		tfPrecio.setVisible(false);
		tvPrecios.getItems().clear();	tvPrecios.setVisible(false); 	tvPrecios.setDisable(false);
		bEliminarPrecios.setVisible(false); 	bGuardarNuevoPrecio.setVisible(false);
		lMsjGuia.setText("Seleccione servicio odontologico y luego \n\t\t elimine sus precios");
		lMsj.setVisible(false);
		bGuardar.setDisable(true);	bGuardar.setVisible(false);
		cbServicioOdontologico.getSelectionModel().select(-1);
		reloadcbServOdontologico();
		
	}
		
	public void mensajeEliminar(){
		bEliminarPrecios.setVisible(true); bEliminarPrecios.setDisable(false);
		System.out.println("aja voy a elimnar esto: "+precioList.get(posPrecioEliminar).getMonto()+" "+precioList.get(posPrecioEliminar).getId());
		lMsj.setText("Fecha: "+precioList.get(posPrecioEliminar).getFecha()+"\n\tMonto: "+precioList.get(posPrecioEliminar).getMonto());
		lMsj.setLayoutY(100);		lMsj.setLayoutX(150);
		lMsj.setVisible(true);	
	}
	
	@FXML
	public void actionEliminarPrecios(){
		Session ses = openSesion();
			Query query = ses.createQuery("from PrecioServicioOdontologico where id =:num ");
			query.setInteger("num", precioList.get(posPrecioEliminar).getId());
			PrecioServicioOdontologico pso = (PrecioServicioOdontologico) query.uniqueResult();
			ses.delete(pso);
		closeSesion(ses);
		lMsj.setText("Eliminado exitoso");lMsj.setVisible(true);	
		precioList.remove(posPrecioEliminar);
			
		tvPrecios.getSelectionModel().clearSelection();		
	
		bEliminarPrecios.setVisible(false);
		bGuardar.setDisable(true);	bGuardar.setVisible(false);
		lMsj.setText("Eliminado exitoso");		
	}
	
	@FXML
	public void actionNuevo(){
		System.out.println("action nuevo!!!");
		tfPrecio.setDisable(false);tfPrecio.setText("");tfPrecio.setVisible(true);	
		tfSO.setDisable(false);tfSO.setText(""); tfSO.setVisible(true);	
		lband.setText("nuevo");		lIdSO.setText("");		
		cbServicioOdontologico.setVisible(false);lMsjServOdontologico.setVisible(false);
		lMsj.setVisible(false);		bGuardar.setDisable(true);		
		bAgregarPrecio.setDisable(false);	bModificarNombre.setDisable(false);
		tvPrecios.getItems().clear();		tvPrecios.setVisible(false);
		bGuardarNuevoPrecio.setVisible(false);
		lServicioOdontologico.setVisible(true);		lPrecio.setVisible(true);		
		lMsjGuia.setText("Ingrese información requerida");
		bEliminarPrecios.setVisible(false); 
		bGuardar.setDisable(true);	bGuardar.setVisible(false);
		cbServicioOdontologico.getSelectionModel().select(-1);
	}
	
	@FXML
	public void actionCancelar(){
		lMsj.setVisible(false);
		System.out.println("action cancelar so");
		ContextoCronograma.getInstance().setBanderaVentEliminarCita(false);
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		stage.close();	
	}
	
	@FXML
	public void actionGuardarNuevoPrecio(){
		System.out.println("agregar nuevo precio!!!!! "+tfPrecio.getText()+"  de "+lIdSO.getText());
		
		PrecioServicioOdontologico pso = new PrecioServicioOdontologico();
		
		Session s = openSesion();
			Query consulta = s.createQuery("from ServicioOdontologico where id =:num");
			consulta.setInteger("num", Integer.parseInt(lIdSO.getText()));
			consulta.setMaxResults(1);
			ServicioOdontologico so = new ServicioOdontologico();
			so = (ServicioOdontologico) consulta.uniqueResult();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			pso.setFecha(new Date());
			pso.setMonto(Double.parseDouble(tfPrecio.getText()));
			pso.setServicioOdontologico(so);
			s.save(pso);
		closeSesion(s);	
		
		lMsj.setText("Registro de precio exitoso"); lMsj.setVisible(true);
		tfPrecio.setText(""); tfSO.setText("");
		bGuardarNuevoPrecio.setVisible(false); bGuardarNuevoPrecio.setDisable(true);
		
		ObservableList<PrecioServicioOdontologico> listpso = tvPrecios.getItems();
		System.out.println("tamaño de tabla I  "+listpso.size());
		
		listpso.add(listpso.size(), pso);
		System.out.println("tamaño de tabla II  "+listpso.size());
		
		tvPrecios.setItems(listpso);
		bGuardar.setDisable(true);	bGuardar.setVisible(false);
		cbServicioOdontologico.getSelectionModel().select(-1);
	}
	
	public void reloadcbServOdontologico(){
		opcionSO.clear();
		cbServicioOdontologico.setItems(opcionSO);
		
		Session sesion1 = openSesion();
		Query queryResultSO = sesion1.createQuery("from ServicioOdontologico");	
				
		SOList = FXCollections.observableArrayList(queryResultSO.list());		
		for (int r=0;r<SOList.size();r++){
			opcionSO.add(SOList.get(r).getNombre());
		}
		closeSesion(sesion1);		
		cbServicioOdontologico.setItems(opcionSO);
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
		 System.out.println("Controlador nuevo servicio odontologico - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
}
package controllers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.AArea;
import data.DataModelRequisicion;
import data.DescriGral;
import data.Iva;
import data.Marca;
import data.Material_req;
import data.Movinventario;
import data.Movimiento;
import data.Presentacion;
import data.Producto;
import data.ProductoMarca;
import data.Proveedor;
import data.Requisicion;
import data.Unidad;
import controllers.libreria;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

public class RequisicionController {

	@FXML
	private GridPane gridFecha;
	
	private DatePicker dpfecha;

	private Boolean modifica=true;
		
	@FXML
	private TextField tfDescriGral;
	
	@FXML
    private ComboBox cbPresentacion;
    @FXML
    private ComboBox cbMarca;
    @FXML
    private ComboBox cbAArea;
    @FXML
    private ComboBox cbIva;
    @FXML
    private Button btAgregar;
    @FXML
    private Button btBorrar;
    @FXML
    private TextField tfCantidad;
    @FXML
    private Button btSiguiente;
    @FXML
    private TableView<DataModelRequisicion> TablaMateriales;
    @FXML
    private TableView TablaDescriGral;
    @FXML
    private TableColumn ColDescriGral;
    @FXML
    private TableColumn ColItem;
    @FXML
    private TableColumn ColDescripcion;
    @FXML
    private TableColumn ColPresentacion;
    @FXML
    private TableColumn ColMarca;
    @FXML
    private TableColumn ColCantidad;
    @FXML
    private Button btNuevo;
    @FXML
    private Button btEditar;
    @FXML
    private Button btPrimero;
    @FXML
    private Button btAnterior;
    @FXML
    private Button btUltimo;
    @FXML
    private Button btEliminar;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btGuardar;

    private DescriGral dd;
    private Presentacion pp;
    private Marca mm;
    private ProductoMarca pm;

    private ObservableList<DescriGral> descrigralarray = FXCollections.observableArrayList();
    private ObservableList<Presentacion> presentacionarray = FXCollections.observableArrayList();
    private ObservableList<Material_req> materialarray = FXCollections.observableArrayList();
    private ObservableList<Marca> marcaarray = FXCollections.observableArrayList();
    private ObservableList<AArea> aareaarray = FXCollections.observableArrayList();
    private Requisicion actual;
        
    @FXML
    private void initialize()
    {

// Initialize the DatePicker 
    	dpfecha = new DatePicker(Locale.getDefault());
    	dpfecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    	dpfecha.getCalendarView().todayButtonTextProperty().set("Today");
    	dpfecha.getCalendarView().setShowWeeks(false);
    	dpfecha.getStylesheets().add("controllers/DatePicker.css");
    	
// Add DatePicker to grid
    	gridFecha.add(dpfecha, 0, 0);
	
    	tfCantidad.addEventFilter(KeyEvent.KEY_TYPED , libreria.numeric_Validation(10));    	    	
    	ColDescriGral.setCellValueFactory(new PropertyValueFactory<DescriGral, String>("Descripcion"));
    	
        ColItem.setCellValueFactory(new PropertyValueFactory<Material_req, String>("Id"));
        ColDescripcion.setCellValueFactory(new PropertyValueFactory<Material_req, DescriGral>("descrigral"));
       	ColPresentacion.setCellValueFactory(new PropertyValueFactory<Material_req, Presentacion>("presentacion"));
       	ColMarca.setCellValueFactory(new PropertyValueFactory<Material_req, Marca>("marca"));
       	ColCantidad.setCellValueFactory(new PropertyValueFactory<Material_req, String>("cantidad"));
       	
	    cargaDescriGral();
	    cargaAArea();
       	actual = obtenerUltimo();
//    	mostrarmovimiento(actual);
    	if (actual==null)
    	{
    		btNuevo.fire();
    	}else
    	{
    		mostrarmovimiento(actual);
    		enableboton();
    	}
    	
    	TablaDescriGral.setOnMouseClicked(new EventHandler<MouseEvent>()
    	{
			@Override
			public void handle(MouseEvent arg0) {
                if (arg0.getClickCount() == 2 && TablaDescriGral.getSelectionModel().getSelectedItem() != null) 
                {
                	dd = (DescriGral) TablaDescriGral.getSelectionModel().getSelectedItem();
                	
                	tfDescriGral.setText(dd.getDescripcion());
                	
                	System.out.println(""+tfDescriGral.getText()+"//"+dd.getDescripcion());
//                	DataModelRequisicion temp = new DataModelRequisicion();
//                	temp.setItem(new TextField("0"));
                	DescriGral xx = (DescriGral) TablaDescriGral.getSelectionModel().getSelectedItem();
                	
//                	temp.setDescrigral(new TextField((xx).getDescripcion()));
//                	temp.setDescrigral(((DescriGral) TablaDescriGral.getSelectionModel().getSelectedItem()).getDescripcion());
                	
                	List<Producto> temp2 = new ArrayList();
                	
            		try 
            		{
            			Session sesion = Principal.fabrica.getCurrentSession();
            			sesion.beginTransaction();
            			Query queryr = sesion.createQuery("from Producto where cosa = :cos");
            			queryr.setEntity("cos", xx);
            			temp2 = (List<Producto>) queryr.list();
            			sesion.getTransaction().commit();
            		}catch (HibernateException e1)
            		{
            			e1.printStackTrace();
            		}

            		presentacionarray = FXCollections.observableArrayList();
            		presentacionarray.clear();
            		
            		List<Presentacion> yyy = new ArrayList();
            		
            		for (Producto xxx : temp2)
            		{
            			yyy.add((xxx.getPresentacionEntrada()));
            		}

            		cbPresentacion.setItems(FXCollections.observableArrayList(yyy));
            		           		
            		cbMarca.setDisable(true);
            		tfCantidad.setText("");
            		tfCantidad.setDisable(true);
                }				
			}	
    	});
    	
    }
 

    @FXML
    void selPresentacion(ActionEvent event) {
    	if (cbPresentacion.getSelectionModel().getSelectedItem()!=null)
    	{
    		List<ProductoMarca> temp3 = new ArrayList();
    		pp = (Presentacion) cbPresentacion.getSelectionModel().getSelectedItem();
    		System.out.println("Quiero Saber si entró aquí!!! - ya seleccionada la presentación!!!");
			try 
			{
				Session sesion = Principal.fabrica.getCurrentSession();
				sesion.beginTransaction();
				Query queryr = sesion.createQuery("from ProductoMarca where producto.cosa = :cos and producto.presentacionEntrada = :pre");
				queryr.setEntity("cos", dd);
				queryr.setEntity("pre", pp);
				temp3 = queryr.list();
				sesion.getTransaction().commit();
			}catch (HibernateException e1)
			{
				e1.printStackTrace();
			}    		
			
			List<Marca> yy2 = new ArrayList();
			
			for (ProductoMarca xx2 : temp3)
			{
				yy2.add(xx2.getMarca());
			}
							
			cbMarca.setItems(FXCollections.observableArrayList(yy2));
			
			cbMarca.setDisable(false);
			tfCantidad.setDisable(false);
    	}
    }
    

    @FXML
    void selMarca(ActionEvent event) {
    	if (cbMarca.getSelectionModel().getSelectedItem()!=null)
    	{
    		mm = (Marca) cbMarca.getSelectionModel().getSelectedItem();
    		System.out.println("Quiero Saber si entró aquí!!! - ya seleccionada la presentación!!!");
			try 
			{
				Session sesion = Principal.fabrica.getCurrentSession();
				sesion.beginTransaction();
				Query queryr = sesion.createQuery("from ProductoMarca where producto.cosa = :cos and producto.presentacionEntrada = :pre and marca = :mar");
				queryr.setEntity("cos", dd);
				queryr.setEntity("pre", pp);
				queryr.setEntity("mar", mm);
				pm = (ProductoMarca) queryr.uniqueResult();
				System.out.println("Seleccionado el Producto Final!!!"+pm.toString());
				sesion.getTransaction().commit();
			}catch (HibernateException e1)
			{
				e1.printStackTrace();
			}    					
    	}

    }

    
    @FXML
    void clickbtNuevo(ActionEvent event) {
    	System.out.println("!!!!! Hizo click... se disparó!!!!");
    	modifica=false;
    	disableboton();
    	actual = new Requisicion();
    	actual.setMateriales(new ArrayList());
    	mostrarmovimiento(null);
    }

    @FXML
	void clickbtBorrar(ActionEvent event) {

	}

    @FXML
    void clickbtGuardar(ActionEvent event) {
    	Boolean verificar = (this.dpfecha.getSelectedDate()!=null && actual.getMateriales().size()>0);
    	if (verificar)
    	{
        	Calendar c = Calendar.getInstance();
            c.setTime(this.dpfecha.getSelectedDate());
            actual.setFecha(c.getTime());
        	{
        		try 
        		{
        			Session sesion = Principal.fabrica.getCurrentSession();
        			sesion.beginTransaction();
       				sesion.saveOrUpdate(actual);        			
        			sesion.getTransaction().commit();
        		}catch (HibernateException e1)
        		{
        			e1.printStackTrace();
        		}    		
        		enableboton();
        	}    		
    	}
    	modifica=true;
    }

    @FXML
    void clickbtEditar(ActionEvent event) 
    {
    	modifica=true;
    	disableboton();
    }

    @FXML
    void clickbtCancelar(ActionEvent event) 
    {
    	modifica=true;
    	if (actual==null)
    	{
    		actual=obtenerUltimo();
    		btSiguiente.fire();
    	}
    	enableboton();
    }

    @FXML
    void clickbtPrimero(ActionEvent event) {
    	actual = obtenerPrimero();
		mostrarmovimiento(actual);
    }

    @FXML
    void clickbtAnterior(ActionEvent event) {
		Requisicion temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Requisicion where Id < :ii order by Id asc");
			queryr.setInteger("ii", actual.getId());
			queryr.setMaxResults(1);
			temp = (Requisicion) queryr.uniqueResult();
			sesion.getTransaction().commit();
			if (temp!=null)
			{
				actual = temp;
			}
		}catch (HibernateException e1)
		{
			e1.printStackTrace();
		}
		mostrarmovimiento(actual);
    }

    @FXML
    void clickbtSiguiente(ActionEvent event) {
		Requisicion temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Requisicion where Id > :ii order by Id asc");
			queryr.setInteger("ii", actual.getId());
			queryr.setMaxResults(1);
			temp = (Requisicion) queryr.uniqueResult();
			sesion.getTransaction().commit();
			if (temp!=null)
			{
				actual = temp;
			}

		}catch (HibernateException e1)
		{
			e1.printStackTrace();
		}
		mostrarmovimiento(actual);
    }

    @FXML
    void clickbtUltimo(ActionEvent event) {
    	actual = obtenerUltimo();
		mostrarmovimiento(actual);
    }

    @FXML
    void clickbtAgregar(ActionEvent event) {
//    	if (cbAArea.getSelectionModel().getSelectedIndex()>-1 && !(tfCantidad.getText().equals("")))
//    	{
//    		List<MovInventario> te = new ArrayList();
//    		Material_req temp = new Material_req();
//    		temp.set((ProductoMarca) cbProducto.getSelectionModel().getSelectedItem());
//    		temp.setMovimient(actual);
//    		System.err.println("El movimiento Guardado es:"+temp.getMovimient());
//    		temp.setIva((Iva) cbIva.getSelectionModel().getSelectedItem());
//    		temp.setMontoIva(temp.getPrecioUnitario().multiply(temp.getIva().getAlicuota().divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP));
//    		temp.setTotal((temp.getPrecioUnitario().add(temp.getMontoIva())).multiply(temp.getCantidad()));
//    		if (actual.getMovinventario()==null)
//    		{
//    			actual.setMovinventario(new ArrayList());    			
//    		}
//    		actual.getMovinventario().add(temp);
//    	}
//    	mostrartabla(actual.getMovinventario());
//    	cbIva.getSelectionModel().select(-1);
//    	tfMontoIva.setText("");
//    	tfPrecio.setText("");
//    	tfCantidad.setText("");
//    	cbProducto.getSelectionModel().select(-1);
    }
//////
    @FXML
    void clickbtEliminar(ActionEvent event) {
//////
//////    	if (TablaMateriales.getSelectionModel().getSelectedItem()!=null)
//////    	{
//////        	itemsborrados.add((Movinventario) TablaMateriales.getSelectionModel().getSelectedItem());
//////    		actual.getMovinventario().remove((Movinventario) TablaMateriales.getSelectionModel().getSelectedItem());
//////    	}
//////    	mostrartabla(actual.getMovinventario());
    }

    //FIN ELEMENTOS CREADOS EN EL SCENE BUILDER
    
    
    // INICIALIZACIÓN DE OBJETOS PARA EL CONTROLLER
    

	
	private void cargaDescriGral() {
		descrigralarray.clear();
		try
		{
			Session sse = Principal.fabrica.getCurrentSession();
			sse.beginTransaction();
			Query qqe = sse.createQuery("from DescriGral");
			descrigralarray.addAll(FXCollections.observableArrayList(qqe.list()));
			sse.getTransaction().commit();
		} catch (HibernateException he)
		{
			he.printStackTrace();
		}
		TablaDescriGral.setItems(descrigralarray);
	}
	
	private void cargaAArea() {
		aareaarray.clear();
		try
		{
			Session sse = Principal.fabrica.getCurrentSession();
			sse.beginTransaction();
			Query qqe = sse.createQuery("from AArea");
			aareaarray.addAll(FXCollections.observableArrayList(qqe.list()));
			sse.getTransaction().commit();
		} catch (HibernateException he)
		{
			he.printStackTrace();
		}
		cbAArea.setItems(aareaarray);
	}
	
	private void mostrarmovimiento(Requisicion actual2) {

		if (actual2!=null)
		{
//			this.tfDocumento.setText(""+actual.getComprobante());
			this.dpfecha.setSelectedDate(actual.getFecha());
//			this.cbUnidad.getSelectionModel().select(actual2.getUnidadReq());;
//			this.cbProveedor.setValue((Proveedor) actual.getProveedor());
//			cargaProductoMarca();
			mostrartabla(new ArrayList<DataModelRequisicion>());
		}else
		{
//			this.tfDocumento.setText("");
			this.dpfecha.setSelectedDate(null);
//			this.cbProveedor.setValue(null);
			mostrartabla(null);
		}
	}

	private void mostrartabla(List<DataModelRequisicion> list) {
		// TODO Auto-generated method stub
		materialarray.clear();
		if (list!=null)
		{
			TablaMateriales.setItems(FXCollections.observableArrayList(list));
		}
		else
		{
			TablaMateriales.setItems(null);
		}
		BigDecimal cant = new BigDecimal(0);
		BigDecimal iva = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		BigDecimal total1 = new BigDecimal(0);
		BigDecimal ivatemp = new BigDecimal(0);
		BigDecimal montotemp = new BigDecimal(0);
		if (list!=null && !(list.isEmpty()))
		{
			for (DataModelRequisicion xx : list)
			{
				cant = cant.add(new BigDecimal(xx.getCantidad().toString()));
//				montotemp = (xx.getCantidad().multiply(xx.getPrecioUnitario())).setScale(2, RoundingMode.HALF_UP);
				iva = iva.add(ivatemp);
				total = total.add(montotemp);
				total1 = total1.add(ivatemp.add(montotemp));
//				System.out.println("Debería estar pintando los lbls de totales..."+xx.getCantidad()+xx.getPrecioUnitario()+iva+total);
			}
//			lblCant.setText(cant.setScale(2, RoundingMode.HALF_UP).toString());
//			lblIva.setText(iva.setScale(2, RoundingMode.HALF_UP).toString());
//			lblTotal.setText(total.setScale(2, RoundingMode.HALF_UP).toString());
//			lblTotal11.setText(total1.setScale(2,RoundingMode.HALF_UP).toString());
		}
	}

	private Requisicion obtenerUltimo() {
		Requisicion temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Requisicion order by Id desc");
			queryr.setMaxResults(1);
			temp = (Requisicion) queryr.uniqueResult();
			sesion.getTransaction().commit();
		}catch (HibernateException e1)
		{
			
		}
		return temp;
	}

	private Requisicion obtenerPrimero() {
		Requisicion temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Requisicion order by Id asc");
			queryr.setMaxResults(1);
			temp = (Requisicion) queryr.uniqueResult();
			sesion.getTransaction().commit();
		}catch (HibernateException e1)
		{
			
		}
		return temp;
	}

	private void disableboton()
	{
		btNuevo.setDisable(true);
		btBorrar.setDisable(true);
		btGuardar.setDisable(false);
		btCancelar.setDisable(false);
		btEditar.setDisable(true);
		btPrimero.setDisable(true);
		btAnterior.setDisable(true);
		btSiguiente.setDisable(true);
		btUltimo.setDisable(true);

		this.dpfecha.setDisable(false);
		
		this.btAgregar.setDisable(false);
		this.btEliminar.setDisable(false);

//		cbProducto.setDisable(false);
////		cbProveedor.setDisable(modifica);
	
	}
	
	private void enableboton()
	{
		btNuevo.setDisable(false);
		btBorrar.setDisable(false);
		btEditar.setDisable(false);
		btGuardar.setDisable(true);
		btCancelar.setDisable(true);
		btPrimero.setDisable(false);
		btAnterior.setDisable(false);
		btSiguiente.setDisable(false);
		btUltimo.setDisable(false);
		
		this.dpfecha.setDisable(true);

		this.btAgregar.setDisable(true);
		this.btEliminar.setDisable(true);
		
//		cbProducto.setDisable(true);
////		cbProveedor.setDisable(modifica);
		
	}

	
    private class TableCellFormat extends TableCell<Material_req, String>{
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty); 
            this.setText(item);
            this.setAlignment(Pos.BASELINE_RIGHT);
        }
    }

	
}

package controllers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
//import java.time.LocalDate;                              /// INCOMPATIBILIDAD DE JAVA8
//import java.time.format.DateTimeFormatter;               /// INCOMPATIBILIDAD DE JAVA8
//import java.time.temporal.TemporalAccessor;             /// INCOMPATIBILIDAD DE JAVA8
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.DescriGral;
import data.Iva;
import data.Movinventario;
import data.Movimiento;
import data.Producto;
import data.ProductoMarca;
import data.Proveedor;
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
//import javafx.scene.control.DatePicker;   /// INCOMPATIBILIDAD DE JAVA8
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class InventarioController {

	@FXML
	private GridPane gridFecha;
	
	private DatePicker dpfecha;

	private Boolean modifica=true;
		
    @FXML
    private TableColumn ColPrecio;
    @FXML
    private ComboBox cbProducto;
    @FXML
    private ComboBox cbProveedor;
    @FXML
    private ComboBox cbIva;
    @FXML
    private TableColumn ColTotal;
    @FXML
    private TextField tfPrecio;
    @FXML
    private TextField tfMontoIva;
    @FXML
    private Button btAgregar;
    @FXML
    private TableColumn ColCantidad;
    @FXML
    private TableColumn ColIva;
    @FXML
    private Button btBorrar;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TextField tfDocumento;
    @FXML
    private Button btSiguiente;
    @FXML
    private TableView TablaInventario;
    @FXML
    private TableColumn ColItem;
    @FXML
    private Button btNuevo;
    @FXML
    private TableColumn ColProducto;
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
    
    @FXML
    private TextField tfCodigoBarra;
    
    @FXML
    private Label lblCant;
    @FXML
    private Label lblIva;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblCant1;
    @FXML
    private Label lblIva1;
    @FXML
    private Label lblTotal1;
    @FXML
    private Label lblCant11;
    @FXML
    private Label lblIva11;
    @FXML
    private Label lblTotal11;

    private List<Movinventario> itemsborrados = new ArrayList();
    
    @FXML
    void clickbtNuevo(ActionEvent event) {
    	System.out.println("!!!!! Hizo click... se disparó!!!!");
    	modifica=false;
    	disableboton();
    	actual = new Movimiento();
    	actual.setMovinventario(new ArrayList());
    	mostrarmovimiento(null);
    }

    @FXML
	void clickbtBorrar(ActionEvent event) {

	}

    @FXML
    void clickbtGuardar(ActionEvent event) {
    	Boolean verificar = (!this.tfDocumento.getText().isEmpty() && actual.getMovinventario().size()>0);
    	if (verificar)
    	{
// ERROR GRAVE!!! 	actual.setId(Integer.parseInt(this.tfDocumento.getText())); /// Este fue el error que me echó vaina mucho tiempo al modificar movimientos de inventario...
        	Calendar c = Calendar.getInstance();
            c.setTime(this.dpfecha.getSelectedDate());
            actual.setFecha(c.getTime());
            actual.setProveedor((Proveedor) cbProveedor.getSelectionModel().getSelectedItem());
            actual.setComprobante(this.tfDocumento.getText());
//        	actual.setFecha(this.dpFecha.getValue());    /// INCOMPATIBILIDAD DE JAVA8
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
		Movimiento temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Movimiento where Id < :ii order by Id asc");
			queryr.setInteger("ii", actual.getId());
			queryr.setMaxResults(1);
			temp = (Movimiento) queryr.uniqueResult();
			sesion.getTransaction().commit();
			if (temp!=null)
			{
				actual = temp;
			}
		}catch (HibernateException e1)
		{
			
		}
		mostrarmovimiento(actual);
    }

    @FXML
    void clickbtSiguiente(ActionEvent event) {
		Movimiento temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Movimiento where Id > :ii order by Id asc");
			queryr.setInteger("ii", actual.getId());
			queryr.setMaxResults(1);
			temp = (Movimiento) queryr.uniqueResult();
			sesion.getTransaction().commit();
			if (temp!=null)
			{
				actual = temp;
			}

		}catch (HibernateException e1)
		{
			
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
    	if (cbProducto.getSelectionModel().getSelectedIndex()>-1 && !(tfCantidad.getText().equals("")) && !(tfPrecio.getText().equals("")) && cbIva.getSelectionModel().getSelectedIndex()>-1)
    	{
//    		List<MovInventario> te = new ArrayList();
    		Movinventario temp = new Movinventario();
    		temp.setCantidad(new BigDecimal(tfCantidad.getText()));
    		temp.setPrecioUnitario(new BigDecimal(Double.parseDouble(tfPrecio.getText())));
    		temp.setProducto((ProductoMarca) cbProducto.getSelectionModel().getSelectedItem());
    		temp.setMovimient(actual);
    		System.err.println("El movimiento Guardado es:"+temp.getMovimient());
    		temp.setIva((Iva) cbIva.getSelectionModel().getSelectedItem());
    		temp.setMontoIva(temp.getPrecioUnitario().multiply(temp.getIva().getAlicuota().divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP));
    		temp.setTotal((temp.getPrecioUnitario().add(temp.getMontoIva())).multiply(temp.getCantidad()));
    		if (actual.getMovinventario()==null)
    		{
    			actual.setMovinventario(new ArrayList());    			
    		}
    		actual.getMovinventario().add(temp);
    	}
    	mostrartabla(actual.getMovinventario());
    	cbIva.getSelectionModel().select(-1);
    	tfMontoIva.setText("");
    	tfPrecio.setText("");
    	tfCantidad.setText("");
    	cbProducto.getSelectionModel().select(-1);
    }

    @FXML
    void clickbtEliminar(ActionEvent event) {

    	if (TablaInventario.getSelectionModel().getSelectedItem()!=null)
    	{
        	itemsborrados.add((Movinventario) TablaInventario.getSelectionModel().getSelectedItem());
    		actual.getMovinventario().remove((Movinventario) TablaInventario.getSelectionModel().getSelectedItem());
    	}
    	mostrartabla(actual.getMovinventario());
    }

    //FIN ELEMENTOS CREADOS EN EL SCENE BUILDER
    
    
    // INICIALIZACIÓN DE OBJETOS PARA EL CONTROLLER
    
    
    private ObservableList<Proveedor> proveedorarray = FXCollections.observableArrayList();
    private ObservableList<Iva> ivaarray = FXCollections.observableArrayList();
    private ObservableList<Movinventario> movimientoarray = FXCollections.observableArrayList();
    private ObservableList<ProductoMarca> productoarray = FXCollections.observableArrayList();
    private Movimiento actual;
        
    @FXML
    private void initialize()
    {

// Initialize the DatePicker 
    	dpfecha = new DatePicker(Locale.getDefault());
    	dpfecha.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
    	dpfecha.getCalendarView().todayButtonTextProperty().set("Hoy");
    	dpfecha.getCalendarView().setShowWeeks(false);
    	dpfecha.getStylesheets().add("controllers/DatePicker.css");
    	
// Add DatePicker to grid
	gridFecha.add(dpfecha, 0, 0);
	
    	tfPrecio.addEventFilter(KeyEvent.KEY_TYPED , libreria.numeric_Validation(10));
    	tfCantidad.addEventFilter(KeyEvent.KEY_TYPED , libreria.numeric_Validation(10));
//    tfCodigoBarra.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(25));
    	
    	tfCodigoBarra.textProperty().addListener(libreria.mayuscula(tfCodigoBarra));
    	
       	ColItem.setCellValueFactory(new PropertyValueFactory<Movinventario, String>("Id"));
//       	ColItem.setCellFactory(libreria.alineacentro(ColItem));   /// probando para llevarlo a un método.

       	ColProducto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movinventario, String>, ObservableValue<String>>()
    			{

					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Movinventario, String> arg0) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(""+arg0.getValue().getProducto().toString());
					}
    			});
       	
       	ColCantidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movinventario, String>, ObservableValue<String>>()
    			{

					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Movinventario, String> arg0) {
						// TODO Auto-generated method stub
						DecimalFormat xx = new DecimalFormat("###,###,##0.00");
						return new SimpleStringProperty(""+xx.format(arg0.getValue().getCantidad()));
					}
    			});       	
	     ColCantidad.setCellFactory(new Callback<TableColumn<Movinventario, String>, TableCell<Movinventario, String>>() {
			@Override
			public TableCell<Movinventario, String> call(
					TableColumn<Movinventario, String> arg0) {
				// TODO Auto-generated method stub
				return new TableCellFormat();
			}
	        });

       	
    	
    	ColPrecio.setCellValueFactory(new PropertyValueFactory<Movinventario, String>("PrecioUnitario"));
       	ColPrecio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movinventario, String>, ObservableValue<String>>()
    			{

					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Movinventario, String> arg0) {
						// TODO Auto-generated method stub
						DecimalFormat xx = new DecimalFormat("###,###,##0.00");
						return new SimpleStringProperty(""+xx.format(arg0.getValue().getPrecioUnitario()));
					}
    			});
	     ColPrecio.setCellFactory(new Callback<TableColumn<Movinventario, String>, TableCell<Movinventario, String>>() {
			@Override
			public TableCell<Movinventario, String> call(
					TableColumn<Movinventario, String> arg0) {
				// TODO Auto-generated method stub
				return new TableCellFormat();
			}
	        });

    	
    	ColIva.setCellValueFactory(new PropertyValueFactory<Movinventario, String>("MontoIva"));
       	ColIva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movinventario, String>, ObservableValue<String>>()
    			{
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Movinventario, String> arg0) {
						// TODO Auto-generated method stub
						DecimalFormat xx = new DecimalFormat("###,###,##0.00");
						return new SimpleStringProperty(""+xx.format(arg0.getValue().getMontoIva()));
					}
    			});
	     ColIva.setCellFactory(new Callback<TableColumn<Movinventario, String>, TableCell<Movinventario, String>>() {
			@Override
			public TableCell<Movinventario, String> call(
					TableColumn<Movinventario, String> arg0) {
				// TODO Auto-generated method stub
				return new TableCellFormat();
			}
	        });

    	
    	ColTotal.setCellValueFactory(new PropertyValueFactory<Movinventario, String>("Total"));
       	ColTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movinventario, String>, ObservableValue<String>>()
    			{
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Movinventario, String> arg0) {
						// TODO Auto-generated method stub
						DecimalFormat xx = new DecimalFormat("###,###,##0.00");
						return new SimpleStringProperty(""+xx.format(arg0.getValue().getTotal()));
					}
    			});
	     ColTotal.setCellFactory(new Callback<TableColumn<Movinventario, String>, TableCell<Movinventario, String>>() {
			@Override
			public TableCell<Movinventario, String> call(
					TableColumn<Movinventario, String> arg0) {
				// TODO Auto-generated method stub
				return new TableCellFormat();
			}
	        });

    	
    	cargaProveedor();
    	cargaIva();
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
    }
 

	public void cargaProveedor() {
		// TODO Auto-generated method stub
		proveedorarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Proveedor");
            proveedorarray = FXCollections.observableArrayList(queryResult.list());
			sesion.getTransaction().commit();
			cbProveedor.setItems(proveedorarray);
		} catch (HibernateException e2) {
			e2.printStackTrace();
		}					
	}

	public void cargaIva() {
		// TODO Auto-generated method stub
		ivaarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Iva");
            ivaarray = FXCollections.observableArrayList(queryResult.list());
			sesion.getTransaction().commit();
			cbIva.setItems(ivaarray);
		} catch (HibernateException e2) {
			e2.printStackTrace();
		}					
	}

	
	public void cargaProductoMarca() {
		productoarray.clear();
		System.out.println("Proveedor Seleccionado!!! : "+cbProveedor.getSelectionModel().getSelectedItem());
		if (cbProveedor.getSelectionModel().getSelectedItem()!=null)
		{
			Proveedor aa = (Proveedor) cbProveedor.getSelectionModel().getSelectedItem();
			for (DescriGral xx : aa.getProvDescriGral())
			{
				System.out.println(xx);
				try 
				{
					Session sesion = Principal.fabrica.getCurrentSession();
					sesion.beginTransaction();
					Query queryr = sesion.createQuery("from ProductoMarca where producto.cosa = :xxx");
					queryr.setEntity("xxx", xx);
					productoarray.addAll(FXCollections.observableArrayList(queryr.list()));
					sesion.getTransaction().commit();
				}catch (HibernateException e1)
				{
					
				}				
			}
			cbProducto.setItems(productoarray);
		}
	}
	
	private void setCodigoBarra()
	{
		this.tfCodigoBarra.setText(((ProductoMarca) cbProducto.getSelectionModel().getSelectedItem()).getCodigoBarra());
	}
	
	private void mostrarmovimiento(Movimiento actual2) {

		if (actual2!=null)
		{
			this.tfDocumento.setText(""+actual.getComprobante());
			this.dpfecha.setSelectedDate(actual.getFecha());
			this.cbProveedor.setValue((Proveedor) actual.getProveedor());
			cargaProductoMarca();
			mostrartabla(actual2.getMovinventario());
		}else
		{
			this.tfDocumento.setText("");
			this.dpfecha.setSelectedDate(null);
			this.cbProveedor.setValue(null);
			mostrartabla(null);
		}
		itemsborrados.clear();
	}

	private void mostrartabla(List<Movinventario> movinventario) {
		// TODO Auto-generated method stub
		movimientoarray.clear();
		if (movinventario!=null)
		{
			TablaInventario.setItems(FXCollections.observableArrayList(movinventario));
		}
		else
		{
			TablaInventario.setItems(null);
		}
		BigDecimal cant = new BigDecimal(0);
		BigDecimal iva = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		BigDecimal total1 = new BigDecimal(0);
		BigDecimal ivatemp = new BigDecimal(0);
		BigDecimal montotemp = new BigDecimal(0);
		if (movinventario!=null && !(movinventario.isEmpty()))
		{
			for (Movinventario xx : movinventario)
			{
				cant = cant.add(xx.getCantidad());
				ivatemp = (xx.getPrecioUnitario().multiply(xx.getIva().getAlicuota().divide(new BigDecimal(100)))).multiply(xx.getCantidad()).setScale(2, RoundingMode.HALF_UP);
				montotemp = (xx.getCantidad().multiply(xx.getPrecioUnitario())).setScale(2, RoundingMode.HALF_UP);
				iva = iva.add(ivatemp);
				total = total.add(montotemp);
				total1 = total1.add(ivatemp.add(montotemp));
				System.out.println("Debería estar pintando los lbls de totales..."+xx.getCantidad()+xx.getPrecioUnitario()+iva+total);
			}
			lblCant.setText(cant.setScale(2, RoundingMode.HALF_UP).toString());
			lblIva.setText(iva.setScale(2, RoundingMode.HALF_UP).toString());
			lblTotal.setText(total.setScale(2, RoundingMode.HALF_UP).toString());
			lblTotal11.setText(total1.setScale(2,RoundingMode.HALF_UP).toString());
		}
	}

	private Movimiento obtenerUltimo() {
		Movimiento temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Movimiento order by Id desc");
			queryr.setMaxResults(1);
			temp = (Movimiento) queryr.uniqueResult();
			sesion.getTransaction().commit();
		}catch (HibernateException e1)
		{
			
		}
		return temp;
	}

	private Movimiento obtenerPrimero() {
		Movimiento temp = null;
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Movimiento order by Id asc");
			queryr.setMaxResults(1);
			temp = (Movimiento) queryr.uniqueResult();
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

		this.tfCantidad.setDisable(false);
		this.tfDocumento.setDisable(false);
		this.tfCantidad.setDisable(false);
		this.tfCodigoBarra.setDisable(false);
		this.tfPrecio.setDisable(false);
		this.dpfecha.setDisable(false);
		
		this.btAgregar.setDisable(false);
		this.btEliminar.setDisable(false);

		cbProducto.setDisable(false);
		cbProveedor.setDisable(modifica);
	
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
		
		this.tfCantidad.setDisable(true);
		this.tfDocumento.setDisable(true);
		this.tfCantidad.setDisable(true);
		this.tfCodigoBarra.setDisable(true);
		this.tfPrecio.setDisable(true);
		this.dpfecha.setDisable(true);

		this.btAgregar.setDisable(true);
		this.btEliminar.setDisable(true);
		
		cbProducto.setDisable(true);
		cbProveedor.setDisable(modifica);
		
	}

	@FXML
	private void calculaIva()
	{
		if (cbIva.getSelectionModel().getSelectedIndex()>-1)
		{
			tfMontoIva.setText(
					((Iva) cbIva.getSelectionModel().getSelectedItem()).getAlicuota()
					.divide(new BigDecimal(100))
					.multiply(new BigDecimal(Double.parseDouble(tfPrecio.getText()))).setScale(2, RoundingMode.HALF_UP).toString());			
		}
	}
	
    private class TableCellFormat extends TableCell<Movinventario, String>{
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty); 
            this.setText(item);
            this.setAlignment(Pos.BASELINE_RIGHT);
        }
    }

	
}

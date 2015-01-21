package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.AArea;
import data.Ciudad;
import data.DescriGral;
import data.Estado;
import data.Marca;
import data.Presentacion;
import data.Producto;
import data.ProductoMarca;
import data.Proveedor;
import data.UndMedida;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ProductoController {

	@FXML
	private TableView Tabla;
	@FXML
	private TableView TablaPresentacion;
	@FXML
	private TableView TablaProductoMarca;
	@FXML
	private TableView TablaProveedor;
	
	@FXML
	private TableColumn<DescriGral, String> ColCodigo;
	@FXML
	private TableColumn<DescriGral, String> ColDescripcion;

	@FXML
	private TableColumn<Presentacion, String> ColIdPresentacion;
	@FXML
	private TableColumn<Presentacion, String> ColDescripcionPresentacion;
	
	@FXML
	private TableColumn<ProductoMarca, String> ColIdProductoMarca;
	@FXML
	private TableColumn<ProductoMarca, String> ColDescriGral;
	@FXML
	private TableColumn<ProductoMarca, String> ColMarca;
	@FXML
	private TableColumn<ProductoMarca, String> ColPresentacion;
	
	@FXML
	private TextField tfDescriGral0;
	@FXML
	private TextField tfComentario0;
	@FXML
	private TextField tfDescriGral;
	@FXML
	private TextField tfDescriGral2;
	
	@FXML
	private ComboBox cbPresentacion;
	@FXML
	private TextField tfPresentacion;

	@FXML
	private TextField tfCantidadPresentacion;
	
	@FXML
	private TextField tfCodigoBarra;
	
	@FXML
	private TextField tfComentario;
	
	@FXML
	private ComboBox cbUndMedida;

	@FXML
	private ComboBox cbMarca;
	
// BOTONES DE LA PANTALLA PRINCIPAL
	@FXML
	private Button btBorra;
	@FXML
	private Button btNuevo;
	@FXML
	private Button btEdita;
	@FXML
	private Button btCancela;
	@FXML
	private Button btBusca;
	@FXML
	private Button btGuarda;
// FIN BOTONES DE LA PANTALLA PRINCIPAL

	
// BOTONES DE LA PESTAÑA PRESENTACION
	@FXML
	private Button btAgregar1;
	@FXML
	private Button btEliminar1;
	@FXML
	private Button btCheck1;
	
	
// BOTONES DE LA PESTAÑA PRESENTACION
	@FXML
	private Button btAgregar2;
	@FXML
	private Button btEliminar2;
	@FXML
	private Button btCheck2;
	

//	private Principal StagePrincipal;
	
	private ObservableList<DescriGral> descrigralarray = FXCollections.observableArrayList();
	private ObservableList<Presentacion> presentacionarray = FXCollections.observableArrayList();
	private ObservableList<ProductoMarca> marcaarray = FXCollections.observableArrayList();
	
	private DescriGral actual = null;
	private Producto actual2 = null;
	
	private Boolean modifica = false;
	
	private ObservableList<DescriGral> tablaarray = FXCollections.observableArrayList();
	private ObservableList<Presentacion> presentacioncb = FXCollections.observableArrayList();
	private ObservableList<UndMedida> undMedidacb = FXCollections.observableArrayList();
	private ObservableList<Marca> marcacb = FXCollections.observableArrayList();

	private boolean bandera=true; 
	
//	public ProductoController(){
		
//	}

	@FXML
	private void initialize() {
		ColCodigo.setCellValueFactory(new PropertyValueFactory<DescriGral, String>("Id"));
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<DescriGral, String>("Descripcion"));
		
		ColIdPresentacion.setCellValueFactory(new PropertyValueFactory<Presentacion, String>("Id"));
		ColDescripcionPresentacion.setCellValueFactory(new PropertyValueFactory<Presentacion, String>("Descripcion"));
		
		ColIdProductoMarca.setCellValueFactory(new PropertyValueFactory<ProductoMarca, String>("Id"));
		ColMarca.setCellValueFactory(new PropertyValueFactory<ProductoMarca, String>("Marca"));
//		ColPresentacion.setCellValueFactory(new PropertyValueFactory<ProductoMarca, String>("Presentacion"));
	
		ColDescriGral.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMarca, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<ProductoMarca, String> arg0) {
				return new SimpleStringProperty(""+arg0.getValue().getProducto().getCosa().getDescripcion());
			}			
		});
		
		
		ColPresentacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMarca, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMarca, String> p) {
	            return new SimpleStringProperty(""+p.getValue().getProducto().getPresentacionEntrada());
		    }
		});
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		mostrarDescriGral(null);

		cargaUndMedida();
		cargaMarca();
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DescriGral>(){

			@Override
			public void changed(ObservableValue<? extends DescriGral> arg0,
					DescriGral arg1, DescriGral arg2) {
				// TODO Auto-generated method stub
				actual = arg2;
				mostrarDescriGral(actual);
			}
			
		});		

		TablaProductoMarca.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductoMarca>(){

			@Override
			public void changed(ObservableValue<? extends ProductoMarca> arg0,
					ProductoMarca arg1, ProductoMarca arg2) {
				// TODO Auto-generated method stub
				mostrarProductoMarca(arg2);
				System.out.println("!!!!!Está refrescando el Producto!!!!");
			}
			
		});
		
		cargarTabla();
		edicion(false);
		enableboton();
	}
	
	protected void mostrarProductoMarca(ProductoMarca arg2) {
		// TODO Auto-generated method stub
		cbPresentacion.getSelectionModel().select(arg2.getProducto().getPresentacionEntrada());
		cbMarca.getSelectionModel().select(arg2.getMarca());
		tfCodigoBarra.setText(arg2.getCodigoBarra());
		tfComentario.setText(arg2.getComentario());
	}

	private void obtenerUltimo() {
		// TODO Auto-generated method stub
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from DescriGral order by Descripcion");
			queryResult.setMaxResults(1);
            actual = (DescriGral) queryResult.uniqueResult();  
			sesion.getTransaction().commit();
		} catch (HibernateException e2) {
			e2.printStackTrace();
			actual = null;
		}		
	}

	private void mostrarDescriGral(DescriGral mostrar) {
		if (bandera)
		{
			if (mostrar != null)
			{
				List<Producto> press = new ArrayList();
				tfDescriGral0.setText(mostrar.getDescripcion());
				tfComentario.setText(mostrar.getComentario());
				tfDescriGral.setText(mostrar.getDescripcion());
				tfDescriGral2.setText(mostrar.getDescripcion());

				cbMarca.getSelectionModel().select(-1);
				cbPresentacion.getSelectionModel().select(-1);

				Session sesio = Principal.fabrica.getCurrentSession();
				
				sesio.beginTransaction();

				Query queryResult = sesio.createQuery("from Producto where Cod_Cosa = :id");
				queryResult.setInteger("id", mostrar.getId());
				
				press = queryResult.list();
				
				sesio.getTransaction().commit();

				presentacionarray.clear();
				if (press.size()>0)
				{
					for (Producto xx : press)
					{
						presentacionarray.add(xx.getPresentacionEntrada());
					}
				}
				TablaPresentacion.setItems(presentacionarray);
//				tfComentario.setText(mostrar.getComentario());
//				tfStatus.setText(mostrar.getStatus());

				presentacioncb = FXCollections.observableArrayList(presentacionarray);
				cbPresentacion.setItems(presentacioncb);
				
				List<ProductoMarca> prodm = new ArrayList();
				Session ssess = Principal.fabrica.getCurrentSession();
				ssess.beginTransaction();
				Query qqer = ssess.createQuery("from ProductoMarca where producto.cosa = :cid");
				qqer.setEntity("cid", mostrar);
				marcaarray = FXCollections.observableArrayList(qqer.list());
				ssess.getTransaction().commit();
				
				if (marcaarray.size()>0)
				{
					TablaProductoMarca.setItems(marcaarray);	
				}else
				{
					TablaProductoMarca.setItems(null);
				}

			}else
			{
				tfDescriGral.setText("");
				tfDescriGral0.setText("");
				tfComentario0.setText("");
				tfDescriGral2.setText("");
				presentacionarray.clear();
				TablaPresentacion.setItems(presentacionarray);
				presentacioncb.clear();
				cbPresentacion.setItems(presentacioncb);
				cbPresentacion.getSelectionModel().select(-1);
				cbMarca.getSelectionModel().select(-1);
			}
			System.out.println("Debería desseleccionar la Marca!!!!!!!");
			TablaProductoMarca.getSelectionModel().select(-1);
			cargaUndMedida();
			cargaMarca();
			tfCodigoBarra.setText("");
			tfComentario.setText("");
			cbMarca.getSelectionModel().select(-1);
			cbPresentacion.getSelectionModel().select(-1);
			System.out.println("Debería desseleccionar la Marca2222!!!!!!!");
		}

	}
	
	
	private void disableboton()
	{
//		botonNuevo.setDisable(true);
//		botonBorrar.setDisable(true);
//		botonGuarda.setDisable(false);
//		botonCancela.setDisable(false);
//		botonEditar.setDisable(true);
	}
	
	private void enableboton()
	{
//		botonNuevo.setDisable(false);
//		botonBorrar.setDisable(false);
//		botonEditar.setDisable(false);
//		botonGuarda.setDisable(true);
//		botonCancela.setDisable(true);
	}
	
	private void edicion(Boolean edit)
	{
		if (edit)
		{
//			tfRIF.setDisable(false);
//			tfRazon.setDisable(false);
//			tfCalle.setDisable(false);
//			tfEdificio.setDisable(false);
//			tfNumero.setDisable(false);
//			tfSector.setDisable(false);
//			cbCiudad.setDisable(false);
//			cbEstado.setDisable(false);
//			tfTelefono.setDisable(false);
//			tfFax.setDisable(false);
//			tfContacto.setDisable(false);
//			tfTelContacto.setDisable(false);
//			tfEmail.setDisable(false);
//			tfWeb.setDisable(false);
//			tfComentario.setDisable(false);
//			tfStatus.setDisable(false);
		}else
		{
//			tfRIF.setDisable(true);
//			tfRazon.setDisable(true);
//			tfCalle.setDisable(true);
//			tfEdificio.setDisable(true);
//			tfNumero.setDisable(true);
//			tfSector.setDisable(true);
//			cbCiudad.setDisable(true);
//			cbEstado.setDisable(true);
//			tfTelefono.setDisable(true);
//			tfFax.setDisable(true);
//			tfContacto.setDisable(true);
//			tfTelContacto.setDisable(true);
//			tfEmail.setDisable(true);
//			tfWeb.setDisable(true);
//			tfComentario.setDisable(true);
//			tfStatus.setDisable(true);
		}
	}

    private void modoedicion()
    {
    	this.btBorra.setDisable(true);
    	this.btNuevo.setDisable(true);
    	this.btBusca.setDisable(true);
    	this.btGuarda.setDisable(false);
    	this.btCancela.setDisable(false);
    	this.btEdita.setDisable(true);
    	this.tfComentario0.setEditable(true);
    	this.tfDescriGral0.setEditable(true);
    }
    
    private void modoconsulta()
    {
    	this.btBorra.setDisable(false);
    	this.btNuevo.setDisable(false);
    	this.btBusca.setDisable(false);
    	this.btGuarda.setDisable(true);
    	this.btCancela.setDisable(true);
    	this.btEdita.setDisable(false);
    	this.tfComentario0.setEditable(false);
    	this.tfDescriGral0.setEditable(false);    	
    }

    private void grabaDescriGral(Object actual3)
    {
    	try
    	{
    		Session sss = Principal.fabrica.getCurrentSession();
    		sss.getTransaction().begin();
    		sss.saveOrUpdate(actual3);
    		sss.getTransaction().commit();
    	}catch (HibernateException ee)
    	{
    		ee.printStackTrace();
    	}
    }

    
    @FXML
    void clickbtNuevo(ActionEvent event) {
    	actual = new DescriGral();
    	modoedicion();
    	Tabla.getSelectionModel().clearSelection();
//    	Tabla.getItems().add(new Marca());
//    	Tabla.edit(0, ColDescripcion);
    }

    @FXML
    void clickbtBorra(ActionEvent event) {
    	if (Tabla.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual = (DescriGral) Tabla.getSelectionModel().getSelectedItem();
//        	modoedicion();
			DialogResponse i = Dialogs.showConfirmDialog(Principal.getStagePrincipal(), 
                    "Está seguro que desea eliminar elemento?", 
                    "Por eliminar...",
                    "Eliminar Elemento" );
            if (i==DialogResponse.YES) 
				{
				try {
					Session sesion = Principal.fabrica.getCurrentSession();		
					sesion.beginTransaction();
					sesion.delete(actual);
					sesion.getTransaction().commit();
				} catch (HibernateException e2) 
				{
					e2.printStackTrace();
				}				
				cargarTabla();
			}

    	}    	    	
    }

    @FXML
    void clickbtEdita(ActionEvent event) {
    	if (Tabla.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual = (DescriGral) Tabla.getSelectionModel().getSelectedItem();
        	modoedicion();
    	}    	
    }

    @FXML
    void clickbtBusca(ActionEvent event) {

    }

    @FXML
    void clickbtGuarda(ActionEvent event) {
    	if (!tfDescriGral0.getText().trim().equals("") && !tfComentario0.getText().trim().equals(""))
    	{
    		actual.setDescripcion(tfDescriGral0.getText());
    		actual.setComentario(tfComentario0.getText());
    		grabaDescriGral(actual);
    		modoconsulta();
    		cargarTabla();
    	}

    }

    @FXML
    void clickbtCancela(ActionEvent event) {
    	modoconsulta();
    }
	
	private void cargarTabla() {
		// TODO Auto-generated method stub

		int ac = -1;
		
		DescriGral acc = null; 
		
		System.out.println("Seleccion Tabla!!! "+(Tabla.getSelectionModel().getSelectedIndex()>-1));
		
		if (Tabla.getSelectionModel().getSelectedIndex()>-1)
			{
			ac = Tabla.getSelectionModel().getSelectedIndex();
			acc = tablaarray.get(ac);
			System.out.println(""+acc.getDescripcion());
			}

		//ObservableList ordenn = FXCollections.observableArrayList(); 
		//ordenn = Tabla.getSortOrder();   // para mantener ordenado después de grabar!!!
//		tablaarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from DescriGral order by Id");
            tablaarray = FXCollections.observableArrayList(queryResult.list());  
			sesion.getTransaction().commit();
			Tabla.setItems(tablaarray);
//			if (ordenn.size()>0)		// para mantener ordenado después de grabar!!! el TableView
//			{							// Esta modificación debe ir en todos los controllers... 
//				Tabla.getSortOrder().addAll(ordenn);	
//			}
			
		
		} catch (HibernateException e2) {

			e2.printStackTrace();
		}
		
		if (ac>-1)
		{
			for (int i=0;i<tablaarray.size();  i++ ) 
			{
				if (tablaarray.get(i).getId()==acc.getId())
					ac = i;
			}				
		}

		Tabla.getSelectionModel().select(ac);
			System.out.println(""+acc==null);
			if (acc!=null)
				System.out.println("Al final del método cargarTabla: "+acc.getDescripcion());
			
	}


	private void cargaPresentacion() {
		presentacioncb.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Presentacion");
            presentacioncb = FXCollections.observableArrayList(queryResult.list());
			sesion.getTransaction().commit();
			cbPresentacion.setItems(presentacioncb);
		} catch (HibernateException e2) {

			e2.printStackTrace();
		}	
	}
	
	private void cargaUndMedida() {
		undMedidacb.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from UndMedida");
			undMedidacb = FXCollections.observableArrayList(queryResult.list());
			sesion.getTransaction().commit();
			cbUndMedida.setItems(undMedidacb);
		} catch (HibernateException e2) 
		{
			e2.printStackTrace();
		}
	}

	
	private void cargaMarca() {
		marcacb.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Marca order by Descripcion");
            marcacb = FXCollections.observableArrayList(queryResult.list());
			sesion.getTransaction().commit();
			cbMarca.setItems(marcacb);
		} catch (HibernateException e2) {

			e2.printStackTrace();
		}	
	}

	
	@FXML
	private void clickbtAgregar1() {
		Presentacion pres = new Presentacion();
		try {
			int cc = Integer.parseInt(tfCantidadPresentacion.getText());
			UndMedida und = (UndMedida) cbUndMedida.getSelectionModel().getSelectedItem();
			int uu = und.getId();
			
			System.out.println("Presentación Seleccionada!!!");
			System.out.println("Cantidad: "+tfCantidadPresentacion.getText()+"/  UnidadMedida: "+cbUndMedida.getSelectionModel().getSelectedIndex());
			
			if (Integer.parseInt(tfCantidadPresentacion.getText())>0 && cbUndMedida.getSelectionModel().getSelectedIndex()>-1)
			{
				try {
					Session sesion1 = Principal.fabrica.getCurrentSession();
					sesion1.beginTransaction();
					Query queryResult = sesion1.createQuery("from Presentacion where cantidad=:cc and Cod_UndMedida=:uu");
					queryResult.setInteger("cc", cc);
					queryResult.setInteger("uu", uu);
					pres = (Presentacion) queryResult.uniqueResult();
					if (pres==null)
					{
						Presentacion nueva = new Presentacion();
						nueva.setCantidad(""+cc);
						nueva.setUndMedida(und);
						sesion1.save(nueva);
						pres = nueva;

					}
					
					sesion1.getTransaction().commit();

					Session sesion2 = Principal.fabrica.getCurrentSession();
					sesion2.beginTransaction();
					Producto enc = null;
					Query queryResult2 = sesion2.createQuery("from Producto where Cod_Cosa = :id and Cod_PresentacionEntrada = :cpe");
					queryResult2.setInteger("id", actual.getId());
					queryResult2.setInteger("cpe", pres.getId());
					enc = (Producto) queryResult2.uniqueResult();
					if (enc!=null)
					{
						///Error... conseguí esa presentacion... no pasa nada... 
					}else
					{
						Producto nuevo = new Producto();
						nuevo.setCosa(actual);
						nuevo.setPresentacionEntrada(pres);
						sesion2.save(nuevo);
					}
					sesion2.getTransaction().commit();

				}catch (HibernateException e2)
				{
					e2.printStackTrace();
				}				
			}
		}catch (NullPointerException nu)
		{
			nu.printStackTrace();
		}
		
		mostrarDescriGral(actual);

	}

	
	@FXML
	private void clickbtAgregar2() {
		ProductoMarca prmar= new ProductoMarca();
		try {
			Marca mar = (Marca) cbMarca.getSelectionModel().getSelectedItem();
			int uu = mar.getId();
			int cc = actual2.getId();
			
			System.out.println("Marca Seleccionada!!!");
			
			if (cbMarca.getSelectionModel().getSelectedIndex()>-1)
			{
				try {
					Session sesion1 = Principal.fabrica.getCurrentSession();
					sesion1.beginTransaction();
					Query queryResult = sesion1.createQuery("from ProductoMarca where Cod_Producto=:cc and Cod_Marca=:uu");
					queryResult.setInteger("cc", cc);
					queryResult.setInteger("uu", uu);
					prmar = (ProductoMarca) queryResult.uniqueResult();
					if (prmar==null)
					{
						ProductoMarca nueva = new ProductoMarca();
						nueva.setProducto(actual2);
						nueva.setMarca(mar);
						nueva.setCodigoBarra(""+tfCodigoBarra.getText());
						nueva.setComentario(""+tfComentario.getText());
						sesion1.save(nueva);
						
					}
					else
					{
						//// no pasa nada
					}

					sesion1.getTransaction().commit();

				}catch (HibernateException e2)
				{
					e2.printStackTrace();
				}				
			}
		}catch (NullPointerException nu)
		{
			nu.printStackTrace();
		}
		mostrarDescriGral(actual);
	}

	
	@FXML
	private void clickbtEliminar1() {
		if (TablaPresentacion.getSelectionModel().getSelectedIndex()>-1)
		{
			Presentacion ppp = (Presentacion) TablaPresentacion.getSelectionModel().getSelectedItem();
			try
			{
				Session sess1 = Principal.fabrica.getCurrentSession();
				sess1.beginTransaction();
				Query qquer = sess1.createQuery("from Producto where Cod_Cosa=:cc and Cod_PresentacionEntrada=:cp");
				qquer.setInteger("cc", actual.getId());
				qquer.setInteger("cp", ppp.getId());
				Producto produc = (Producto) qquer.uniqueResult(); 
//				try
//				{
					sess1.delete(produc);
//				}catch (HibernateException xe)
//				{
//					xe.printStackTrace();
//				}			
				sess1.getTransaction().commit();
			}catch (HibernateException ee)
			{
				ee.printStackTrace();
			}
		}
		mostrarDescriGral(actual);
	}
	
	@FXML
	private void clickbtEliminar2()
	{
		if (TablaProductoMarca.getSelectionModel().getSelectedIndex()>-1)
		{
			ProductoMarca ppmm = (ProductoMarca) TablaProductoMarca.getSelectionModel().getSelectedItem();
			try
			{
				Session sess1 = Principal.fabrica.getCurrentSession();
				sess1.beginTransaction();
//				Query qquer = sess1.createQuery("from ProductoMarca where producto =:cc and PresentacionEntrada = :cp");
//				qquer.setEntity("cc", ppmm.getProducto());
//				qquer.setEntity("cp", ppmm.getMarca());
//				ProductoMarca produc = (Producto) qquer.uniqueResult(); 
//				try
//				{
					sess1.delete(ppmm);
//				}catch (HibernateException xe)
//				{
//					xe.printStackTrace();
//				}			
				sess1.getTransaction().commit();
			}catch (HibernateException ee)
			{
				ee.printStackTrace();
			}
		}
		mostrarDescriGral(actual);		
	}

	@FXML
	private void manejadorcbPresentacionAction() 
	{
		Presentacion seleccion = (Presentacion) cbPresentacion.getSelectionModel().getSelectedItem();
		int cc = tablaarray.get(Tabla.getSelectionModel().getSelectedIndex()).getId();
		if (seleccion != null)
		{
//			marcaarray.clear();
			Producto produc = null;
			try {
				Session sesion = Principal.fabrica.getCurrentSession();		
				sesion.beginTransaction();
				Query queryResult = sesion.createQuery("from Producto where Cod_Cosa=:cc and Cod_PresentacionEntrada=:cp");
				queryResult.setInteger("cc", cc);
				queryResult.setInteger("cp", seleccion.getId());
				actual2 = (Producto) queryResult.uniqueResult(); 
//				for (ProductoMarca promar : produc.getProductomarca())
//					marcaarray.add(promar.getMarca());
//				actual2=produc;
				sesion.getTransaction().commit();
			} catch (HibernateException e2) {
				e2.printStackTrace();
			}
//			TablaProductoMarca.setItems(marcaarray);	
		}
//		else
//		{
//			TablaProductoMarca.setItems(null);
//		}
		
	}

	@FXML
	private void manejadorcbMarcaAction() 
	{
		Marca seleccion = (Marca) cbMarca.getSelectionModel().getSelectedItem();
//		try {
//			Session sesion = Principal.fabrica.getCurrentSession();		
//			sesion.beginTransaction();
//			Query queryResult = sesion.createQuery("from Marca");
//			 = (Producto) queryResult.uniqueResult(); 
//			sesion.getTransaction().commit();
//		} catch (HibernateException e2) {
//
//			e2.printStackTrace();
//		}
//		TablaMarca.setItems(marcaarray);
		
	}

	
	
	@FXML
	private void refrescar(){
		bandera = false;
		cargarTabla();
//		cargaUndMedida();
//		cargaMarca();
		bandera = true;
	}
	
}

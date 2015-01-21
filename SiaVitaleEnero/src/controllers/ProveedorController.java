package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Ciudad;
import data.DescriGral;
import data.Estado;
import data.Municipio;
import data.Proveedor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProveedorController {

	@FXML
	private Label lbProveedor; 
	
	@FXML
	private TableView<Proveedor> Tabla;
	
	@FXML
	private TableView<DescriGral> TablaDescriGral;
	
	@FXML
	private TableColumn<Proveedor, String> ColRIF;
	
	@FXML
	private TableColumn<Proveedor, String> ColRazon;
	
	@FXML
	private TableColumn<DescriGral, String> ColDescripcion;

	@FXML
	private TextField tfRIF;
	
	@FXML
	private TextField tfRazon;
	
	@FXML
	private TextField tfComentario;
	
	@FXML
	private TextField tfStatus;

	@FXML
	private TextField tfCalle;
	
	@FXML
	private TextField tfEdificio;
	
	@FXML
	private TextField tfNumero;
	
	@FXML
	private TextField tfSector;

	@FXML
	private ComboBox cbCiudad;

	@FXML
	private ComboBox cbEstado;
	
	@FXML
	private ComboBox cbDescriGral;
	
	@FXML
	private TextField tfContacto;
	
	@FXML
	private TextField tfTelefono;

	@FXML
	private TextField tfCelular;

	@FXML
	private TextField tfTelContacto;
	
	@FXML
	private TextField tfFax;

	@FXML
	private TextField tfWeb;

	@FXML
	private TextField tfEmail;

	@FXML
	private Button botonBorrar;
	
	@FXML
	private Button botonNuevo;

	@FXML
	private Button botonModifica;

	@FXML
	private Button botonCancela;

	@FXML
	private Button botonGuarda;
	
	@FXML
	private Button botonPrimero;
	
	@FXML
	private Button botonAnterior;

	@FXML
	private Button botonSiguiente;

	@FXML
	private Button botonUltimo;

	@FXML
	private Button botonSalir;

	@FXML
	private Button botonmas;
	
	@FXML
	private Button botonmenos;
	
	private Principal StagePrincipal;
	
	private ObservableList<DescriGral> descrigralarray = FXCollections.observableArrayList();
	
	private Proveedor actual = null;

	private Boolean modifica = false;
	
	private ObservableList<Proveedor> tablaarray = FXCollections.observableArrayList();
	private ObservableList<Estado> estadoarray = FXCollections.observableArrayList();
	private ObservableList<Ciudad> ciudadarray = FXCollections.observableArrayList();
	private ObservableList<DescriGral> tabladescrigralarray = FXCollections.observableArrayList();
	
	
	public ProveedorController(){
		
	}

	@FXML
	private void initialize() {
		lbProveedor.setText("");
		ColRIF.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("Rif"));
		ColRazon.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("Razon"));
		
		Tabla.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		ColDescripcion.setCellValueFactory(new PropertyValueFactory<DescriGral, String>("Descripcion"));
		
		cargarTabla();
		cargaEstado();
		cargaMunicipio(null);
		mostrarProveedor(null);
		cargaDescriGral();
		
		Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proveedor>(){
			@Override
			public void changed(ObservableValue<? extends Proveedor> arg0,
					Proveedor arg1, Proveedor arg2) {
				// TODO Auto-generated method stub
				actual = arg2;
				mostrarProveedor(actual);
				edicion(false);
			}
		});	
		
		cbEstado.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Estado>(){

			@Override
			public void changed(ObservableValue<? extends Estado> arg0,
					Estado arg1, Estado arg2) {
				// TODO Auto-generated method stub
				ciudadarray.clear();
				if (arg2!=null)
				cargaMunicipio(arg2);
				cbCiudad.setItems(ciudadarray);				
			}
			
		});
		
		mostrarProveedor(actual);		
		edicion(false);
		enableboton();
	}
	
	

	private void cargaDescriGral() {
		// TODO Auto-generated method stub
		descrigralarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from DescriGral");
            descrigralarray = FXCollections.observableArrayList(queryResult.list());  
			sesion.getTransaction().commit();
		} catch (HibernateException e2) {
			e2.printStackTrace();
		}
		cbDescriGral.setItems(descrigralarray);
	}

	private void obtenerUltimo() {
		// TODO Auto-generated method stub
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Proveedor order by Razon");
			queryResult.setMaxResults(1);
            actual = (Proveedor) queryResult.uniqueResult();  
			sesion.getTransaction().commit();
		} catch (HibernateException e2) {
			e2.printStackTrace();
			actual = null;
		}		
	}

	private void mostrarProveedor(Proveedor mostrar) {
		tabladescrigralarray.clear();
		if (mostrar != null)
		{
			lbProveedor.setText(mostrar.getRazon());
			tfRIF.setText(mostrar.getRif());
			tfRazon.setText(mostrar.getRazon());
			tfCalle.setText(mostrar.getCalle());
			tfEdificio.setText(mostrar.getEdificio());
			tfNumero.setText(mostrar.getNumero());
			tfSector.setText(mostrar.getSector());
			cbCiudad.setValue(mostrar.getCiudad());
			cbEstado.setValue(mostrar.getEstado());
//			tfCelular.setText(mostrar.getCelular());
			tfTelefono.setText(mostrar.getTelefono());
			tfFax.setText(mostrar.getFax());
			tfContacto.setText(mostrar.getContacto());
			tfTelContacto.setText(mostrar.getTelContacto());
			tfEmail.setText(mostrar.getEmail());
			tfWeb.setText(mostrar.getWeb());
			tfComentario.setText(mostrar.getComentario());
			tfStatus.setText(mostrar.getStatus());

			tabladescrigralarray = FXCollections.observableArrayList(mostrar.getProvDescriGral());
			
			TablaDescriGral.setItems(tabladescrigralarray);
		
		}else
		{
			lbProveedor.setText("");
			tfRIF.setText("");
			tfRazon.setText("");
			tfCalle.setText("");
			tfEdificio.setText("");
			tfNumero.setText("");
			tfSector.setText("");
			cbCiudad.setValue(null);
			cbEstado.setValue(null);
//			tfCelular.setText(null);
			tfTelefono.setText("");
			tfFax.setText("");
			tfContacto.setText("");
			tfTelContacto.setText("");
			tfEmail.setText("");
			tfWeb.setText("");
			tfComentario.setText("");
			tfStatus.setText("");
		
			tabladescrigralarray = FXCollections.observableArrayList();
			
			TablaDescriGral.setItems(tabladescrigralarray);

		}
	}
	
	
	@FXML
	private void clickbotonBorrar()
	{
		Proveedor Dborrar = (Proveedor) Tabla.getSelectionModel().getSelectedItem(); 
		System.out.println("Entroó en Botón Borrar!!!!");
		if (Dborrar!= null)
		{
			DialogResponse i = Dialogs.showConfirmDialog(Principal.getStagePrincipal(), 
                    "Está seguro que desea eliminar elemento?", 
                    "Por eliminar...",
                    "Eliminar Elemento" );
            if (i==DialogResponse.YES) 
            {
//				CosaTable.getItems().remove(borrar);
				try {
					Session sesion = Principal.fabrica.getCurrentSession();		
					sesion.beginTransaction();
					sesion.delete(Dborrar);
					sesion.getTransaction().commit();
				} catch (HibernateException e2) {

					e2.printStackTrace();
				}				
				cargarTabla();
				mostrarProveedor((Proveedor) Tabla.getSelectionModel().getSelectedItem());
			}
		}else
		{
			Dialogs.showWarningDialog(Principal.getStagePrincipal(), 
                    "Por Favor, Seleccione un Elemento de la Tabla", 
                    "Por Seleccionar...",
                    "Eliminar Elemento" );
		}
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
			tfRIF.setDisable(false);
			tfRazon.setDisable(false);
			tfCalle.setDisable(false);
			tfEdificio.setDisable(false);
			tfNumero.setDisable(false);
			tfSector.setDisable(false);
			cbCiudad.setDisable(false);
			cbEstado.setDisable(false);
			tfTelefono.setDisable(false);
			tfFax.setDisable(false);
			tfContacto.setDisable(false);
			tfTelContacto.setDisable(false);
			tfEmail.setDisable(false);
			tfWeb.setDisable(false);
			tfComentario.setDisable(false);
			tfStatus.setDisable(false);
			
			cbDescriGral.setDisable(false);
			TablaDescriGral.setDisable(false);

			
		}else
		{
			tfRIF.setDisable(true);
			tfRazon.setDisable(true);
			tfCalle.setDisable(true);
			tfEdificio.setDisable(true);
			tfNumero.setDisable(true);
			tfSector.setDisable(true);
			cbCiudad.setDisable(true);
			cbEstado.setDisable(true);
			tfTelefono.setDisable(true);
			tfFax.setDisable(true);
			tfContacto.setDisable(true);
			tfTelContacto.setDisable(true);
			tfEmail.setDisable(true);
			tfWeb.setDisable(true);
			tfComentario.setDisable(true);
			tfStatus.setDisable(true);
			
			cbDescriGral.setDisable(true);
			TablaDescriGral.setDisable(true);
			
		}
	}
	
	@FXML
	private void clickbotonNuevo() 
	{
		actual = new Proveedor();
		disableboton();
		mostrarProveedor(null);
		edicion(true);
		modifica = false;
	}


	@FXML
	private void clickbotonBusca()
	{
		
	}
	
	@FXML
	private void clickbotonModifica()
	{
		Proveedor modi = (Proveedor) Tabla.getSelectionModel().getSelectedItem();
		actual = modi;
		System.out.println("Entroó en Botón Modifica!!!!");
		if (modi != null)
		{
			edicion(true);
			modifica = true;
			disableboton();
		}else
		{
			Dialogs.showWarningDialog(Principal.getStagePrincipal(), 
                    "Por Favor, Seleccione un Elemento de la Tabla", 
                    "Por Seleccionar...",
                    "Modificar Elemento" );
		}
//		modifica = true;
//		disableboton();
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
			actual.setRif(tfRIF.getText());
			actual.setRazon(tfRazon.getText());
			actual.setCalle(tfCalle.getText());
			actual.setEdificio(tfEdificio.getText());
			actual.setNumero(tfNumero.getText());
			actual.setSector(tfSector.getText());
			actual.setCiudad((Ciudad) cbCiudad.getValue());
			actual.setEstado((Estado) cbEstado.getValue());
			actual.setTelefono(tfTelefono.getText());
			actual.setFax(tfFax.getText());
			actual.setContacto(tfContacto.getText());
			actual.setTelContacto(tfTelContacto.getText());
			actual.setEmail(tfEmail.getText());
			actual.setWeb(tfWeb.getText());
			actual.setComentario(tfComentario.getText());
			actual.setStatus(tfStatus.getText());
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
		Proveedor sel = (Proveedor) Tabla.getSelectionModel().getSelectedItem();
		actual = sel;
		mostrarProveedor(actual);
		modifica = false;
		edicion(false);
		enableboton();
	}
	
	@FXML
	private void clickbotonPrimero()
	{
		
	}

	@FXML
	private void clickbotonAnterior()
	{
		
	}
	
	@FXML
	private void clickbotonSiguiente()
	{
		
	}

	@FXML
	private void clickbotonUltimo()
	{
		
	}

	@FXML
	private void clickbotonSalir()
	{
		
	}
	
	private void cargarTabla() {
		// TODO Auto-generated method stub
		ObservableList ordenn = FXCollections.observableArrayList(); 
		ordenn = Tabla.getSortOrder();   // para mantener ordenado después de grabar!!!
		tablaarray.clear();
		try {
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Proveedor order by Razon");
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


	private void cargaEstado()
	{
		estadoarray.clear();
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Estado order by Descripcion");
            estadoarray = FXCollections.observableArrayList(queryResult.list());  
			sesion.getTransaction().commit();											
		}catch (HibernateException e2) {
			e2.printStackTrace();
			actual = null;	
		}
		cbEstado.setItems(estadoarray);
		System.out.println("Cargó los Estados!!!");
	}
	
	protected void cargaMunicipio(Estado arg2) {
		// TODO Auto-generated method stub
		ciudadarray.clear();
		if (arg2!=null){
			try 
			{
				Session sesion = Principal.fabrica.getCurrentSession();		
				sesion.beginTransaction();
				Query queryResult = sesion.createQuery("from Ciudad where Cod_Estado=:intt order by Descripcion");
				queryResult.setInteger("intt", arg2.getId());
	            ciudadarray = FXCollections.observableArrayList(queryResult.list());  
				sesion.getTransaction().commit();											
			}catch (HibernateException e2) {
				e2.printStackTrace();
				actual = null;	
			}			
		}
		cbCiudad.setItems(ciudadarray);
		System.out.println("Cargó los Municipios!!!");
	}

	@FXML
	private void clickbotonmas() 
	{
//		tabladescrigralarray.clear();
		if (cbDescriGral.getSelectionModel().getSelectedIndex()>-1)
		{
			DescriGral temp = (DescriGral) cbDescriGral.getSelectionModel().getSelectedItem();
			if (actual.getProvDescriGral()==null)
			{
				actual.setProvDescriGral(new ArrayList());
			}
			System.out.println("Quiero saber si existe el elemento al dar la primera vez click en más: "+tabladescrigralarray.size());
			Boolean ban = false;
			for (DescriGral xx : actual.getProvDescriGral())
			{
				if (xx.getId() == ((DescriGral) cbDescriGral.getSelectionModel().getSelectedItem()).getId())
				{
					ban = true;
					break;
				}
			}
			if (!ban)
			{
				actual.getProvDescriGral().add(temp);
			}
		}
		tabladescrigralarray = FXCollections.observableArrayList(actual.getProvDescriGral());
		TablaDescriGral.setItems(tabladescrigralarray);
	}
	
	@FXML
	private void clickbotonmenos()
	{
		System.out.println("Entró en click botón menos!!!");
		if (TablaDescriGral.getSelectionModel().getSelectedIndex()>-1)
		{
			System.out.println("y entró en la condición de que hay algún elemento seleccionado!!!");
			DescriGral temp = (DescriGral) TablaDescriGral.getSelectionModel().getSelectedItem();
			System.out.println(temp.toString());
			actual.getProvDescriGral().remove(temp);
			descrigralarray = FXCollections.observableArrayList(actual.getProvDescriGral());
//			descrigralarray.remove((DescriGral) TablaDescriGral.getSelectionModel().getSelectedItem());
			TablaDescriGral.setItems(descrigralarray);		
		}
	}
	
}

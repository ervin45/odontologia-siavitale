package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import data.Banco;
import data.CuentaBancaria2;
import data.CuentaContable;
import data.PersonaContactoBanco;
import data.TipoCuentaBancaria;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class InstrumentosBancariosController {	
	
	@FXML
	private TableView TablaBanco;
	
	@FXML
	private TableColumn<Banco, String> ColBanco;
	
	@FXML
	private TableView TablaCuentasBancarias;	
	
	@FXML
	private TableColumn<CuentaBancaria2, String> ColTipoCuenta;
	
	@FXML
	private TableColumn<CuentaBancaria2, String> ColNumero;
	
	@FXML
	private Label lNombreBancoValor;
	
	@FXML
	private TextField tfCodigoAgencia;
	
	@FXML
	private TextField tfDescripcionAgencia;
	
	@FXML
	private ChoiceBox cbTipoCta;
	
	private ObservableList<String> opcionTipoCuenta= FXCollections.observableArrayList();
	
	private ObservableList<TipoCuentaBancaria> TipoCtaBancariaList = FXCollections.observableArrayList();
	
	@FXML
	private TextField tfNroCta;
	
	@FXML
	private ChoiceBox cbCtaContable;
	
	private ObservableList<String> opcionCuentaContable= FXCollections.observableArrayList();
	
	private ObservableList<CuentaContable> CuentaContableList = FXCollections.observableArrayList();
	
	@FXML
	private TextField tfTlfAgencia;
	
	@FXML
	private TextField tfDirAgencia;
	
	@FXML
	private TableView TablaPersonasContacto;
		
	@FXML
	private TableColumn<PersonaContactoBanco, String> ColNombre;
	
	@FXML
	private TableColumn<PersonaContactoBanco, String> ColTelefono;

	@FXML
	private TableColumn<PersonaContactoBanco, String> ColCorreo;
	
	@FXML
	private TextField tfNombre;
	
	@FXML
	private TextField tfPaginaWeb;
	
	@FXML
	private TextField tfNombrePC;
	
	@FXML
	private TextField tfTelefonoPC;
	
	@FXML
	private TextField tfCorreoElectronicoPC;
	
	@FXML
	private Button btAgregarPC;
	
	@FXML
	private Button btAgregarCB;
	
	@FXML
	private Button btEliminarPC;
	
	@FXML
	private Button btEliminarCB;
	
	@FXML
	private Button btEliminarBanco;
	
	@FXML
	private Button btEditarPC;
	
	@FXML
	private Button btEditarCB;
		
	@FXML
	private Button btEditarBanco;	
	
	@FXML
	private Label Alerta;
	
	Query queryResultBanco;
	
	private ObservableList<Banco> itemsBanco = FXCollections.observableArrayList();
	
	Query queryResultCuentasBancarias;
	
	private ObservableList<CuentaBancaria2> itemsCtaBancaria = FXCollections.observableArrayList();
	
	Query queryResultPersonasContacto;
	
	private ObservableList<PersonaContactoBanco> itemsPersonasContacto = FXCollections.observableArrayList();
	
	private int idBancoSelec=0, idPCSelec=0, idCBSelec=0; 
	
	private Banco objBanco = new Banco();	
		
	public InstrumentosBancariosController(){		
	}
		
	@FXML
	private void initialize(){
		System.out.println("controlador de instrumentos bancarios!!!");
		Session sesion = openSesion();		
		Query queryResultTipoCuenta = sesion.createQuery("from TipoCuentaBancaria");
		TipoCtaBancariaList = FXCollections.observableArrayList(queryResultTipoCuenta.list());
		for (int r=0;r<TipoCtaBancariaList.size();r++){
			opcionTipoCuenta.add(TipoCtaBancariaList.get(r).getDescripcion());
		}
		cbTipoCta.setItems(opcionTipoCuenta);
		
		Query queryResultCtaContable = sesion.createQuery("from CuentaContable");
		CuentaContableList = FXCollections.observableArrayList(queryResultCtaContable.list());
		for (int r=0;r<CuentaContableList.size();r++){
			opcionCuentaContable.add(CuentaContableList.get(r).getDescripcion());
		}
		cbCtaContable.setItems(opcionCuentaContable);
		
		closeSesion(sesion);
		tfNombrePC.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.numeric_Validation(15));		
		cargalistabancos();		
	}	
	
	private void cargalistabancos(){
		
		ColBanco.setCellValueFactory(new PropertyValueFactory<Banco,String>("Descripcion"));
		TablaBanco.setColumnResizePolicy(TablaBanco.UNCONSTRAINED_RESIZE_POLICY);	
		Session sesion1 = openSesion();		
		queryResultBanco = sesion1.createQuery("from Banco");
		itemsBanco = FXCollections.observableArrayList(queryResultBanco.list());
		TablaBanco.setItems(itemsBanco);
		closeSesion(sesion1);
		
		TablaBanco.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Banco>(){
			@Override
			public void changed(ObservableValue<? extends Banco> arg0, Banco arg1, Banco arg2) {
				if (arg2 != null){
					System.out.println("SSeleccioneeee: "+arg2.getDescripcion()+"  "+arg2.getId());
					idBancoSelec=arg2.getId();
					objBanco = (Banco) arg2;
					tfNombre.setText(arg2.getDescripcion());
					tfPaginaWeb.setText(arg2.getPaginaWeb());
					btEliminarBanco.setDisable(false);btEditarBanco.setDisable(false);
					btEliminarPC.setDisable(true);btEditarPC.setDisable(true);
					btEliminarCB.setDisable(true);btEditarCB.setDisable(true);
					tfNombrePC.setDisable(false);tfCorreoElectronicoPC.setDisable(false);
					tfTelefonoPC.setDisable(false);tfCodigoAgencia.setDisable(false);
					tfDescripcionAgencia.setDisable(false);tfDirAgencia.setDisable(false);
					tfTlfAgencia.setDisable(false);tfNroCta.setDisable(false);
					btAgregarCB.setDisable(false); btAgregarPC.setDisable(false);
					cbCtaContable.setDisable(false); cbTipoCta.setDisable(false);
					limpieza();
					actualizarPersonasContacto();
					actualizarTabCuentasBancarias();
				}
			}
		});	
	}
	
	private void limpieza(){
		tfNombrePC.setText("");tfCorreoElectronicoPC.setText("");tfTelefonoPC.setText("");
		lNombreBancoValor.setText("");tfCodigoAgencia.setText("");tfDescripcionAgencia.setText("");
		tfNroCta.setText(""); tfTlfAgencia.setText("");tfDirAgencia.setText("");
		cbCtaContable.getSelectionModel().select(-1);
		cbTipoCta.getSelectionModel().select(-1);
	}
		
	private void actualizarTabCuentasBancarias(){
				
		ColNumero.setCellValueFactory(new PropertyValueFactory<CuentaBancaria2,String>("numero"));
		
		ColTipoCuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CuentaBancaria2,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<CuentaBancaria2, String> arg0) {
				return new SimpleStringProperty(""+arg0.getValue().getTipocuentabancaria().getDescripcion());
		}});
		
		TablaCuentasBancarias.setColumnResizePolicy(TablaCuentasBancarias.UNCONSTRAINED_RESIZE_POLICY);	
		Session sesion1 = openSesion();		
		queryResultCuentasBancarias  = sesion1.createQuery("from CuentaBancaria2 where banco = :codbanco");
		queryResultCuentasBancarias.setInteger("codbanco", idBancoSelec);	
		itemsCtaBancaria = FXCollections.observableArrayList(queryResultCuentasBancarias.list());
		TablaCuentasBancarias.setItems(itemsCtaBancaria);
		closeSesion(sesion1);
		
		TablaCuentasBancarias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CuentaBancaria2>(){
			@Override
			public void changed(ObservableValue<? extends CuentaBancaria2> arg0, CuentaBancaria2 arg1, CuentaBancaria2 arg2) {
				if (arg2 != null){
					idCBSelec=arg2.getId();									
					actualizarInfoCtaBancaria(arg2);
					btEliminarCB.setDisable(false);btEditarCB.setDisable(false);
					Alerta.setVisible(false);
				}
			}
		});	
	}
	
	private void actualizarPersonasContacto(){
		ColTelefono.setCellValueFactory(new PropertyValueFactory<PersonaContactoBanco,String>("Telefono"));
		
		ColCorreo.setCellValueFactory(new PropertyValueFactory<PersonaContactoBanco,String>("CorreoElectronico"));
		
		ColNombre.setCellValueFactory(new PropertyValueFactory<PersonaContactoBanco,String>("Nombre"));
				
		TablaPersonasContacto.setColumnResizePolicy(TablaPersonasContacto.UNCONSTRAINED_RESIZE_POLICY);	
		Session sesion1 = openSesion();		
		queryResultPersonasContacto  = sesion1.createQuery("from PersonaContactoBanco where cod_banco = :codbanco");
		queryResultPersonasContacto.setInteger("codbanco", idBancoSelec);	
		itemsPersonasContacto = FXCollections.observableArrayList(queryResultPersonasContacto.list());
		TablaPersonasContacto.setItems(itemsPersonasContacto);
		closeSesion(sesion1);
				
		TablaPersonasContacto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PersonaContactoBanco>(){
			@Override
			public void changed(ObservableValue<? extends PersonaContactoBanco> arg0, PersonaContactoBanco arg1, PersonaContactoBanco arg2) {
				if (arg2 != null){
					idPCSelec=arg2.getId();									
					tfNombrePC.setText(arg2.getNombre());
					tfTelefonoPC.setText(arg2.getTelefono());
					tfCorreoElectronicoPC.setText(arg2.getCorreoElectronico());
					btEliminarPC.setDisable(false);
					btEditarPC.setDisable(false);
					Alerta.setVisible(false);
				}
			}
		});	
	}
	
	private void actualizarInfoCtaBancaria(CuentaBancaria2 arg2){
		lNombreBancoValor.setText(arg2.getBanco().getDescripcion());
		tfCodigoAgencia.setText(arg2.getCodigoAgencia());
		tfDescripcionAgencia.setText(arg2.getDescripcionAgencia());	
		tfNroCta.setText(arg2.getNumero());
		tfTlfAgencia.setText(arg2.getTelefonoAgencia());
		tfDirAgencia.setText(arg2.getDireccionAgencia());
		for (int r=0;r<TipoCtaBancariaList.size();r++){
			if (arg2.getTipocuentabancaria()!=null){
				if ( arg2.getTipocuentabancaria().getDescripcion().equals(TipoCtaBancariaList.get(r).getDescripcion()) ){
					cbTipoCta.getSelectionModel().select(r);
					break;
				}
			}
		}
		
		for (int r=0;r<CuentaContableList.size();r++){
			if (arg2.getCuentacontable()!=null){
				if ( arg2.getCuentacontable().getDescripcion().equals(CuentaContableList.get(r).getDescripcion()) ){
					cbCtaContable.getSelectionModel().select(r);
					break;
				}
			}
		}
	}
		
	private Session openSesion(){		
		Session sesion = Principal.fabrica.getCurrentSession();
		sesion.beginTransaction();		return sesion;
	}
	
	private void closeSesion(Session sesion){		
		sesion.getTransaction().commit();
	}
	
	@FXML
	private void actionEliminarPersonaContacto(){
		System.out.println("action PC  "+idPCSelec);
		
		Session sesion1 = openSesion();
		PersonaContactoBanco obj;
		Query queryResultPersonaContacto = sesion1.createQuery("from PersonaContactoBanco where id = :pc");
		queryResultPersonaContacto.setInteger("pc",idPCSelec);
		queryResultPersonaContacto.setMaxResults(1);
		obj = (PersonaContactoBanco) queryResultPersonaContacto.uniqueResult();
		sesion1.delete(obj);
		closeSesion(sesion1);
		tfNombrePC.setText("");tfCorreoElectronicoPC.setText("");tfTelefonoPC.setText("");
		Session sesion2 = openSesion();
		queryResultPersonaContacto = sesion2.createQuery("from PersonaContactoBanco where cod_banco = :codbanco");
		queryResultPersonaContacto.setInteger("codbanco", idBancoSelec);	
		itemsPersonasContacto = FXCollections.observableArrayList(queryResultPersonaContacto.list());
		TablaPersonasContacto.setItems(itemsPersonasContacto);
		closeSesion(sesion2);
		Alerta.setVisible(true);Alerta.setText("Eliminación exitosa");
	}
	
	@FXML
	private void actionEliminarCuentaBancaria(){
		System.out.println("action CB  "+idCBSelec);
		
		Session sesion1 = openSesion();
		CuentaBancaria2 obj;
		Query queryResultCuentaBancaria = sesion1.createQuery("from CuentaBancaria2 where id = :pc");
		queryResultCuentaBancaria.setInteger("pc",idCBSelec);
		queryResultCuentaBancaria.setMaxResults(1);
		obj = (CuentaBancaria2) queryResultCuentaBancaria.uniqueResult();
		sesion1.delete(obj);
		closeSesion(sesion1);
		
		Session sesion2 = openSesion();
		queryResultCuentaBancaria = sesion2.createQuery("from CuentaBancaria2 where banco = :codbanco");
		queryResultCuentaBancaria.setInteger("codbanco", idBancoSelec);	
		itemsCtaBancaria = FXCollections.observableArrayList(queryResultCuentaBancaria.list());
		TablaCuentasBancarias.setItems(itemsCtaBancaria);
		closeSesion(sesion2);		
		Alerta.setVisible(true);Alerta.setText("Eliminación exitosa");
	}
	
	@FXML
	private void actionEliminarBanco(){
		System.out.println("action Banco  "+idBancoSelec);
		boolean permisoBorrar=false;
		
		Session sesion_ = openSesion();	
		Query queryResultPCBanco  = sesion_.createQuery("from PersonaContactoBanco where cod_banco = :codbanco");
		queryResultPCBanco.setInteger("codbanco", idBancoSelec);	
		ObservableList<PersonaContactoBanco> itemsPC = FXCollections.observableArrayList(queryResultPCBanco.list());
		System.out.println("cant personas  "+itemsPC.size());
		
		if (itemsPC.size()>0){	System.out.println("tiene asociacion con personas!!!!!");			
			for (PersonaContactoBanco o : itemsPC){
				System.out.println("items pc "+o.getNombre());
				sesion_.delete(o);				
			}	permisoBorrar=true;
			queryResultPCBanco  = sesion_.createQuery("from PersonaContactoBanco");
			itemsPC = FXCollections.observableArrayList(queryResultPCBanco.list());
			TablaPersonasContacto.setItems(itemsPC);
		}
		
		Query queryResultCBBanco  = sesion_.createQuery("from CuentaBancaria2 where cod_banco = :codbanco");
		queryResultCBBanco.setInteger("codbanco", idBancoSelec);	
		ObservableList<CuentaBancaria2> itemsCB = FXCollections.observableArrayList(queryResultCBBanco.list());
		System.out.println("cant cuentas bancarias  "+itemsCB.size());
		
		if (itemsCB.size()>0){	System.out.println("tiene asociacion con cuentas!!!!!");
			for (CuentaBancaria2 o : itemsCB){
				System.out.println("items cb "+o.getNumero());
				sesion_.delete(o);					
			}	permisoBorrar=true;
			queryResultCBBanco  = sesion_.createQuery("from CuentaBancaria2");
			itemsCB = FXCollections.observableArrayList(queryResultCBBanco.list());
			TablaCuentasBancarias.setItems(itemsCB);
		}	
	
		closeSesion(sesion_);
		
		if ( (permisoBorrar) || (itemsCB.size()==0 && itemsPC.size()==0) ){
			Session sesion1 = openSesion();	
			try{
				Banco obj;
				Query queryResultBanco = sesion1.createQuery("from Banco where id = :pc");
				queryResultBanco.setInteger("pc",idBancoSelec);
				queryResultBanco.setMaxResults(1);
				obj = (Banco) queryResultBanco.uniqueResult();
				sesion1.delete(obj);
				queryResultBanco = sesion1.createQuery("from Banco");
				itemsBanco = FXCollections.observableArrayList(queryResultBanco.list());
				TablaBanco.setItems(itemsBanco);
				closeSesion(sesion1);		
				tfNombre.setText("");tfPaginaWeb.setText("");
				Alerta.setVisible(true);Alerta.setText("Eliminación exitosa");
			}catch(ConstraintViolationException ee){
				Alerta.setVisible(true);Alerta.setText("Eliminación fallida");
			}
		}		
	}

	@FXML
	private void actionAgregarBanco(){
		
		if ( (tfNombre.getText().compareTo("")!=0) && (tfPaginaWeb.getText().compareTo("")!=0) ){
			Session sesion = openSesion();
			Banco objB = new Banco();
			objB.setDescripcion(tfNombre.getText());
			objB.setPaginaWeb(tfPaginaWeb.getText());
			sesion.save(objB);
			queryResultBanco = sesion.createQuery("from Banco");
			itemsBanco = FXCollections.observableArrayList(queryResultBanco.list());
			TablaBanco.setItems(itemsBanco);
			closeSesion(sesion);
			Alerta.setVisible(true);Alerta.setText("Registro exitoso");
		}else{
			Alerta.setVisible(true);Alerta.setText("Ingrese la información necesaria");
		}
	}
	
	@FXML
	private void actionAgregarPersonaContacto(){
		if ( (tfNombrePC.getText().compareTo("")!=0) && (tfCorreoElectronicoPC.getText().compareTo("")!=0)
		   && (tfTelefonoPC.getText().compareTo("")!=0) ){
			Session sesion = openSesion();
			PersonaContactoBanco objB = new PersonaContactoBanco();
			objB.setNombre(tfNombrePC.getText());
			objB.setTelefono(tfTelefonoPC.getText());
			objB.setCorreoElectronico(tfCorreoElectronicoPC.getText());
			objB.setBanco(objBanco);
			sesion.save(objB);
			queryResultPersonasContacto = sesion.createQuery("from PersonaContactoBanco where cod_banco = :codbanco");
			queryResultPersonasContacto.setInteger("codbanco", objBanco.getId());	
			itemsPersonasContacto = FXCollections.observableArrayList(queryResultPersonasContacto.list());
			TablaPersonasContacto.setItems(itemsPersonasContacto);
			closeSesion(sesion);
			Alerta.setVisible(true);Alerta.setText("Registro exitoso");
		}else{
			Alerta.setVisible(true);Alerta.setText("Ingrese la información necesaria");
		}
	}
	
	@FXML
	private void actionAgregarCuentaBancaria(){
	
		if ( (tfCodigoAgencia.getText().compareTo("")!=0) && (tfDescripcionAgencia.getText().compareTo("")!=0) && (tfDirAgencia.getText().compareTo("")!=0) 
				&& (tfNroCta.getText().compareTo("")!=0) &&	(tfTlfAgencia.getText().compareTo("")!=0) ){			
				Session sesion = openSesion();
				CuentaBancaria2 objB = new CuentaBancaria2();
				
				objB.setBanco(objBanco);
				objB.setCodigoAgencia(tfCodigoAgencia.getText());
				objB.setDescripcionAgencia(tfDescripcionAgencia.getText());
				objB.setDireccionAgencia(tfDirAgencia.getText());
				objB.setTelefonoAgencia(tfTlfAgencia.getText());
				objB.setNumero(tfNroCta.getText());
				objB.setCuentacontable(CuentaContableList.get(cbCtaContable.getSelectionModel().getSelectedIndex()));
				objB.setTipocuentabancaria(TipoCtaBancariaList.get(cbTipoCta.getSelectionModel().getSelectedIndex()));				
				sesion.save(objB);
				
				queryResultCuentasBancarias = sesion.createQuery("from CuentaBancaria2 where cod_banco = :codbanco");
				queryResultCuentasBancarias.setInteger("codbanco", objBanco.getId());	
				itemsCtaBancaria = FXCollections.observableArrayList(queryResultCuentasBancarias.list());
				TablaCuentasBancarias.setItems(itemsCtaBancaria);
				closeSesion(sesion);
				Alerta.setVisible(true);Alerta.setText("Registro exitoso");
			}else{
				Alerta.setVisible(true);Alerta.setText("Ingrese la información necesaria");
			}			
	}
	
	@FXML
	private void actionEditarBanco(){
		System.out.println("editar_banco:    "+idBancoSelec);
		if (tfNombre.getText().compareTo("")!=0 && tfPaginaWeb.getText().compareTo("")!=0){
			Session _sesion = openSesion();
			Banco objbanco = (Banco) _sesion.get(Banco.class, idBancoSelec);
			objbanco.setDescripcion(tfNombre.getText());
			objbanco.setPaginaWeb(tfPaginaWeb.getText());
			_sesion.update(objbanco);
			queryResultBanco = _sesion.createQuery("from Banco");
			itemsBanco = FXCollections.observableArrayList(queryResultBanco.list());
			TablaBanco.setItems(itemsBanco);
			closeSesion(_sesion);
			Alerta.setVisible(true);Alerta.setText("Modificación exitosa");
		}else{
			Alerta.setVisible(true);Alerta.setText("Ingrese la información necesaria");
		}
	}
	
	@FXML
	private void actionEditarPersonaContacto(){
		System.out.println("editar persona contacto:    "+idPCSelec);
		
		if (tfCorreoElectronicoPC.getText().compareTo("")!=0 && tfNombrePC.getText().compareTo("")!=0
				&& tfTelefonoPC.getText().compareTo("")!=0){
			Session sesion_ = openSesion();
			PersonaContactoBanco objpc = (PersonaContactoBanco) sesion_.get(PersonaContactoBanco.class, idPCSelec);
			objpc.setNombre(tfNombrePC.getText());
			objpc.setCorreoElectronico(tfCorreoElectronicoPC.getText());
			objpc.setTelefono(tfTelefonoPC.getText());
			objpc.setBanco(objBanco);
			sesion_.update(objpc);
			queryResultPersonasContacto  = sesion_.createQuery("from PersonaContactoBanco where cod_banco = :codbanco");
			queryResultPersonasContacto.setInteger("codbanco", idBancoSelec);	
			itemsPersonasContacto = FXCollections.observableArrayList(queryResultPersonasContacto.list());
			TablaPersonasContacto.setItems(itemsPersonasContacto);
			closeSesion(sesion_);
			Alerta.setVisible(true);Alerta.setText("Modificación exitosa");
		}else{
			Alerta.setVisible(true);Alerta.setText("Ingrese la información necesaria");
		}
	}
	
	@FXML
	private void actionEditarCuentaBancaria(){
		System.out.println("editar cuenta bancaria:    "+idCBSelec);
		
		if (tfCodigoAgencia.getText().compareTo("")!=0 
			&& tfNroCta.getText().compareTo("")!=0 
			&& tfDescripcionAgencia.getText().compareTo("")!=0 
			&& tfTlfAgencia.getText().compareTo("")!=0
			&& tfDirAgencia.getText().compareTo("")!=0 
			&& cbCtaContable.getSelectionModel().getSelectedItem().toString().compareTo("")!=0
			&& cbTipoCta.getSelectionModel().getSelectedItem().toString().compareTo("")!=0){
				Session sesion = openSesion();
				CuentaBancaria2 objcb = (CuentaBancaria2) sesion.get(CuentaBancaria2.class, idCBSelec);
				objcb.setNumero(tfNroCta.getText());
				objcb.setTelefonoAgencia(tfTlfAgencia.getText());
				objcb.setDireccionAgencia(tfDirAgencia.getText());
				objcb.setDescripcionAgencia(tfDescripcionAgencia.getText());
				objcb.setCodigoAgencia(tfCodigoAgencia.getText());
				
				if (cbCtaContable.getSelectionModel().getSelectedIndex() != -1){
					objcb.setCuentacontable(CuentaContableList.get(cbCtaContable.getSelectionModel().getSelectedIndex()));					
				}				
				if (cbTipoCta.getSelectionModel().getSelectedIndex() != -1){
					objcb.setTipocuentabancaria(TipoCtaBancariaList.get(cbTipoCta.getSelectionModel().getSelectedIndex()));					
				}				
				sesion.update(objcb);
				queryResultCuentasBancarias  = sesion.createQuery("from CuentaBancaria2 where banco = :codbanco");
				queryResultCuentasBancarias.setInteger("codbanco", idBancoSelec);	
				itemsCtaBancaria = FXCollections.observableArrayList(queryResultCuentasBancarias.list());
				TablaCuentasBancarias.setItems(itemsCtaBancaria);
				closeSesion(sesion);
				Alerta.setVisible(true);Alerta.setText("Modificación exitosa");
		}else{
			Alerta.setVisible(true);Alerta.setText("Ingrese la información necesaria");
		}
	}
}



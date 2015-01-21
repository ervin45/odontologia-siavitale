package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.GrupoProducto;
import data.Marca;
import data.UndMedida;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConfiguraProductoController {

    private ObservableList<Marca> marcaarray = FXCollections.observableArrayList(); 
    
    Marca actual;
	
    @FXML
    private Button btBorra3;

    @FXML
    private TableColumn ColDescripcion;

    @FXML
    private Button btBusca1;

    @FXML
    private Button btBusca3;

//    @FXML
//    private TableColumn ColComentario;

    @FXML
    private Button btNuevo1;

//    @FXML
//    private TableColumn ColComentario3;

    @FXML
    private TextField tfComentario;

    @FXML
    private Button btNuevo3;

//    @FXML
//    private TableColumn ColComentario1;

    @FXML
    private TableColumn ColDescripcion1;

    @FXML
    private TableColumn ColDescripcion3;

    @FXML
    private TableView Tabla3;

    @FXML
    private Button btBorra1;

    @FXML
    private Button btEdita3;

    @FXML
    private TableView Tabla1;

    @FXML
    private Button btEdita1;

    @FXML
    private TextField tfComentario3;

    @FXML
    private TextField tfComentario1;

    @FXML
    private TextField tfDescripcion;

    @FXML
    private TableView Tabla;

    @FXML
    private Button btGuarda1;

    @FXML
    private Button btGuarda3;

    @FXML
    private TextField tfDescripcion3;

    @FXML
    private TextField tfDescripcion1;

    @FXML
    private Button btCancela3;

    @FXML
    private Button btCancela1;
 
    Boolean buscando = false;
    
    @FXML
    private void initialize() 
    {
    	initialize0();
    	initialize1();
    	initialize3();
    }

    private void initialize0()
    {
    	ColDescripcion.setCellValueFactory(new PropertyValueFactory<Marca, String>("Descripcion"));
//    	ColComentario.setCellValueFactory(new PropertyValueFactory<Marca, String>("Comentario"));
    	cargaMarca();
    	modoconsulta();

    	tfDescripcion.textProperty().addListener(libreria.mayuscula(tfDescripcion));
    	tfComentario.textProperty().addListener(libreria.mayuscula(tfComentario));
    	
    	tfDescripcion.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				// TODO Auto-generated method stub
				if (buscando)
				{
					for (int i=0 ; i < marcaarray.size() ; i++)
					{
						System.out.println("debería estar buscando!!!"+(marcaarray.get(i).getDescripcion().substring(0, arg2.trim().length()))+"//"+arg2+"//"+(marcaarray.get(i).getDescripcion().substring(0, arg2.trim().length())).equals(arg2.trim()));
						if ((marcaarray.get(i).getDescripcion().substring(0, arg2.trim().length())).equals(arg2.trim()))
						{
							Tabla.scrollTo(i);
							Tabla.getFocusModel().focus(i);;
							System.out.println("Moverse!!!");
						}
					}
				}
			}    		
    	});
    	
    	Tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Marca>()
    			{

					@Override
					public void changed(ObservableValue<? extends Marca> arg0,
						Marca arg1, Marca arg2) {
						actual = arg2;
						Tabla.getSelectionModel().select(actual);
						muestraMarca(actual);
						if (buscando)
						{
							modoconsulta();
						}
						buscando = false;
					}
				
    			});
    }
    
    
    private void cargaMarca()
    {
    	if (!buscando)
    	{
	    	marcaarray.clear();
	    	try
	    	{
	    		Session sees = Principal.fabrica.getCurrentSession();
	    		sees.getTransaction().begin();
	    		Query quee = sees.createQuery("from Marca order by Descripcion");
	    		marcaarray = FXCollections.observableArrayList(quee.list());
	    		sees.getTransaction().commit();
	    	} catch (HibernateException ee)
	    	{
	    		ee.printStackTrace();
	    	}
	    	Tabla.setItems(marcaarray);    		
    	}
    }

    private void grabaMarca(Marca mar)
    {
    	try
    	{
    		Session sss = Principal.fabrica.getCurrentSession();
    		sss.getTransaction().begin();
    		sss.saveOrUpdate(mar);
    		sss.getTransaction().commit();
    	}catch (HibernateException ee)
    	{
    		ee.printStackTrace();
    	}
    }

    private void muestraMarca(Marca mar)
    {
    	actual = mar;
    	if (actual==null)
    	{
    		tfDescripcion.setText("");
        	tfComentario.setText("");    		
    	}else
    	{
    		tfDescripcion.setText(mar.getDescripcion());
        	tfComentario.setText(mar.getComentario());
    	}
    }
    
    @FXML
    private Button btNuevo;

    @FXML
    private Button btBorra;

    @FXML
    private Button btEdita;

    @FXML
    private Button btCancela;

    @FXML
    private Button btBusca;

    @FXML
    private Button btGuarda;

    
    @FXML
    void clickbtNuevo(ActionEvent event) {
    	actual = new Marca();
    	modoedicion();
    	Tabla.getSelectionModel().clearSelection();
    	tfDescripcion.setText("");
    	tfComentario.setText("");
//    	Tabla.getItems().add(new Marca());
//    	Tabla.edit(0, ColDescripcion);
    }

    @FXML
    void clickbtBorra(ActionEvent event) {
    	if (Tabla.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual = (Marca) Tabla.getSelectionModel().getSelectedItem();
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
				cargaMarca();
			}

    	}    	    	
    }

    @FXML
    void clickbtEdita(ActionEvent event) {
    	if (Tabla.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual = (Marca) Tabla.getSelectionModel().getSelectedItem();
    		buscando = true;
        	modoedicion();
    	}    	
    }

    @FXML
    void clickbtBusca(ActionEvent event) {
    	modoedicion();
    	buscando = true;
    }

    @FXML
    void clickbtGuarda(ActionEvent event) {
    	if (!tfDescripcion.getText().trim().equals("")) // && !tfComentario.getText().trim().equals(""))
    	{
    		actual.setDescripcion(tfDescripcion.getText());
    		actual.setComentario(tfComentario.getText());
    		grabaMarca(actual);
    		modoconsulta();
    		cargaMarca();
    	}

    }

    @FXML
    void clickbtCancela(ActionEvent event) {
    	modoconsulta();
    }

    private void modoedicion()
    {
    	this.btBorra.setDisable(true);
    	this.btNuevo.setDisable(true);
    	this.btBusca.setDisable(true);
    	this.btGuarda.setDisable(false);
    	this.btCancela.setDisable(false);
    	this.btEdita.setDisable(true);
    	this.tfComentario.setEditable(true);
    	this.tfDescripcion.setEditable(true);
    }
    
    private void modoconsulta()
    {
    	this.btBorra.setDisable(false);
    	this.btNuevo.setDisable(false);
    	this.btBusca.setDisable(false);
    	this.btGuarda.setDisable(true);
    	this.btCancela.setDisable(true);
    	this.btEdita.setDisable(false);
    	this.tfComentario.setEditable(false);
    	this.tfDescripcion.setEditable(false);    	
    }


    private ObservableList<UndMedida> undMedidaarray = FXCollections.observableArrayList(); 
    
    UndMedida actual1;

    private void initialize1()
    {
    	ColDescripcion1.setCellValueFactory(new PropertyValueFactory<UndMedida, String>("Descripcion"));
 //   	ColComentario1.setCellValueFactory(new PropertyValueFactory<UndMedida, String>("Comentario"));
    	cargaUndMedida();
    	modoconsulta1();
    	
    	tfDescripcion1.textProperty().addListener(libreria.mayuscula(tfDescripcion1));
    	tfComentario1.textProperty().addListener(libreria.mayuscula(tfComentario1));
    	   
    	Tabla1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UndMedida>()
    			{

					@Override
					public void changed(ObservableValue<? extends UndMedida> arg0,
							UndMedida arg1, UndMedida arg2) {
						actual1 = arg2;
						muestraUndMedida(actual1);
					}
    		
    			});
    }

    private void cargaUndMedida()
    {
    	undMedidaarray.clear();
    	try
    	{
    		Session sees = Principal.fabrica.getCurrentSession();
    		sees.getTransaction().begin();
    		Query quee = sees.createQuery("from UndMedida");
    		undMedidaarray = FXCollections.observableArrayList(quee.list());
    		sees.getTransaction().commit();
    	} catch (HibernateException ee)
    	{
    		ee.printStackTrace();
    	}
    	Tabla1.setItems(undMedidaarray);
    }

    private void grabaUndMedida(UndMedida mar)
    {
    	try
    	{
    		Session sss = Principal.fabrica.getCurrentSession();
    		sss.getTransaction().begin();
    		sss.saveOrUpdate(mar);
    		sss.getTransaction().commit();
    	}catch (HibernateException ee)
    	{
    		ee.printStackTrace();
    	}
    }

    private void muestraUndMedida(UndMedida mar)
    {
    	actual1 = mar;
    	if (actual1==null)
    	{
    		tfDescripcion1.setText("");
        	tfComentario1.setText("");    		
    	}else
    	{
    		tfDescripcion1.setText(mar.getDescripcion());
        	tfComentario1.setText(mar.getComentario());
    	}
    }

    private void modoedicion1()
    {
    	this.btBorra1.setDisable(true);
    	this.btNuevo1.setDisable(true);
    	this.btBusca1.setDisable(true);
    	this.btGuarda1.setDisable(false);
    	this.btCancela1.setDisable(false);
    	this.btEdita1.setDisable(true);
    	this.tfComentario1.setEditable(true);
    	this.tfDescripcion1.setEditable(true);
    }
    
    private void modoconsulta1()
    {
    	this.btBorra1.setDisable(false);
    	this.btNuevo1.setDisable(false);
    	this.btBusca1.setDisable(false);
    	this.btGuarda1.setDisable(true);
    	this.btCancela1.setDisable(true);
    	this.btEdita1.setDisable(false);
    	this.tfComentario1.setEditable(false);
    	this.tfDescripcion1.setEditable(false);    	
    }

    @FXML
    void clickbtNuevo1(ActionEvent event) {
    	actual1 = new UndMedida();
    	modoedicion1();
    	Tabla1.getSelectionModel().clearSelection();
    	tfDescripcion1.setText("");
    	tfComentario1.setText("");

//    	Tabla.getItems().add(new Marca());
//    	Tabla.edit(0, ColDescripcion);
    }

    @FXML
    void clickbtBorra1(ActionEvent event) {
    	if (Tabla1.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual1 = (UndMedida) Tabla1.getSelectionModel().getSelectedItem();
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
					sesion.delete(actual1);
					sesion.getTransaction().commit();
				} catch (HibernateException e2) 
				{
					e2.printStackTrace();
				}				
				cargaUndMedida();
			}

    	}    	    	
    }

    @FXML
    void clickbtEdita1(ActionEvent event) {
    	if (Tabla1.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual1 = (UndMedida) Tabla1.getSelectionModel().getSelectedItem();
        	modoedicion1();
    	}    	
    }

    @FXML
    void clickbtBusca1(ActionEvent event) {

    }

    @FXML
    void clickbtGuarda1(ActionEvent event) {
    	if (!tfDescripcion1.getText().trim().equals("")) // && !tfComentario1.getText().trim().equals(""))
    	{
    		actual1.setDescripcion(tfDescripcion1.getText());
    		actual1.setComentario(tfComentario1.getText());
    		grabaUndMedida(actual1);
    		modoconsulta1();
    		cargaUndMedida();
    	}

    }

    @FXML
    void clickbtCancela1(ActionEvent event) {
    	modoconsulta1();
    }


    private ObservableList<GrupoProducto> grupoarray = FXCollections.observableArrayList(); 
    
    GrupoProducto actual3;

    private void initialize3()
    {
    	ColDescripcion3.setCellValueFactory(new PropertyValueFactory<GrupoProducto, String>("Descripcion"));
//    	ColComentario3.setCellValueFactory(new PropertyValueFactory<GrupoProducto, String>("Comentario"));
    	cargaGrupo();
    	modoconsulta3();
    	   
    	tfDescripcion3.textProperty().addListener(libreria.mayuscula(tfDescripcion3));
    	tfComentario3.textProperty().addListener(libreria.mayuscula(tfComentario3));
    	
    	Tabla3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GrupoProducto>()
    			{

					@Override
					public void changed(ObservableValue<? extends GrupoProducto> arg0,
							GrupoProducto arg1, GrupoProducto arg2) {
						actual3 = arg2;
						muestraGrupo(actual3);
					}
    		
    			});
    }

    private void cargaGrupo()
    {
    	grupoarray.clear();
    	try
    	{
    		Session sees = Principal.fabrica.getCurrentSession();
    		sees.getTransaction().begin();
    		Query quee = sees.createQuery("from GrupoProducto");
    		grupoarray = FXCollections.observableArrayList(quee.list());
    		sees.getTransaction().commit();
    	} catch (HibernateException ee)
    	{
    		ee.printStackTrace();
    	}
    	Tabla3.setItems(grupoarray);
    }

    private void grabaGrupo(GrupoProducto mar)
    {
    	try
    	{
    		Session sss = Principal.fabrica.getCurrentSession();
    		sss.getTransaction().begin();
    		sss.saveOrUpdate(mar);
    		sss.getTransaction().commit();
    	}catch (HibernateException ee)
    	{
    		ee.printStackTrace();
    	}
    }

    private void muestraGrupo(GrupoProducto mar)
    {
    	actual3 = mar;
    	if (actual3==null)
    	{
    		tfDescripcion3.setText("");
        	tfComentario3.setText("");    		
    	}else
    	{
    		tfDescripcion3.setText(mar.getDescripcion());
        	tfComentario3.setText(mar.getComentario());
    	}
    }

    private void modoedicion3()
    {
    	this.btBorra3.setDisable(true);
    	this.btNuevo3.setDisable(true);
    	this.btBusca3.setDisable(true);
    	this.btGuarda3.setDisable(false);
    	this.btCancela3.setDisable(false);
    	this.btEdita3.setDisable(true);
    	this.tfComentario3.setEditable(true);
    	this.tfDescripcion3.setEditable(true);
    }
    
    private void modoconsulta3()
    {
    	this.btBorra3.setDisable(false);
    	this.btNuevo3.setDisable(false);
    	this.btBusca3.setDisable(false);
    	this.btGuarda3.setDisable(true);
    	this.btCancela3.setDisable(true);
    	this.btEdita3.setDisable(false);
    	this.tfComentario3.setEditable(false);
    	this.tfDescripcion3.setEditable(false);    	
    }

    @FXML
    void clickbtNuevo3(ActionEvent event) {
    	actual3 = new GrupoProducto();
    	modoedicion3();
    	Tabla3.getSelectionModel().clearSelection();
    	tfDescripcion3.setText("");
    	tfComentario3.setText("");

//    	Tabla.getItems().add(new Marca());
//    	Tabla.edit(0, ColDescripcion);
    }

    @FXML
    void clickbtBorra3(ActionEvent event) {
    	if (Tabla3.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual3 = (GrupoProducto) Tabla3.getSelectionModel().getSelectedItem();
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
					sesion.delete(actual3);
					sesion.getTransaction().commit();
				} catch (HibernateException e2) 
				{
					e2.printStackTrace();
				}				
				cargaGrupo();
			}

    	}    	    	
    }

    @FXML
    void clickbtEdita3(ActionEvent event) {
    	if (Tabla3.getSelectionModel().getSelectedItem()!=null)
    	{
    		actual3 = (GrupoProducto) Tabla3.getSelectionModel().getSelectedItem();
        	modoedicion3();
    	}    	
    }

    @FXML
    void clickbtBusca3(ActionEvent event) {

    }

    @FXML
    void clickbtGuarda3(ActionEvent event) {
    	if (!tfDescripcion3.getText().trim().equals(""))  // && !tfComentario3.getText().trim().equals(""))
    	{
    		actual3.setDescripcion(tfDescripcion3.getText());
    		actual3.setComentario(tfComentario3.getText());
    		grabaGrupo(actual3);
    		modoconsulta3();
    		cargaGrupo();
    	}

    }

    @FXML
    void clickbtCancela3(ActionEvent event) {
    	modoconsulta3();
    }

}

package controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Estado;
import data.Personal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class PersonalController {

    @FXML
    private TableColumn ColTelefonos;

    @FXML
    private Button btAgregar;

    @FXML
    private TableColumn ColCedula;

    @FXML
    private TableView TablaPersonas;

    @FXML
    private TableColumn ColNombres;

    @FXML
    private Button btEliminar;

    @FXML
    private TableColumn ColDireccion;

    @FXML
    private TableColumn ColApellidos;

    @FXML
    private TableColumn ColPrueba;

    @FXML
    void btAgregarHandler(ActionEvent event) {
        
       DataModelPersonal newRec = new DataModelPersonal(
               "",
               "", 
               "", 
               "", 
               "",
               "");
       datos.add(newRec);
///       TablaPersonas.getSelectionModel().select(TablaPersonas.getItems().size());  ///FALLO!!!!
        
    }

    @FXML
    void btEliminarHandler(ActionEvent event) {

    }

    ObservableList<DataModelPersonal> datos = FXCollections.observableArrayList();
    
    ObservableList<Estado> Prueba = FXCollections.observableArrayList();


    @FXML
    private void initialize(){
    	TablaPersonas.setEditable(true);
    	    	
        ColCedula.setCellValueFactory(new PropertyValueFactory<DataModelPersonal, String>("Cedula"));
        ColCedula.setCellFactory(editableFactory);
        ColCedula.setOnEditCommit(new EventHandler<CellEditEvent<DataModelPersonal, String>>() {
			@Override
			public void handle(CellEditEvent<DataModelPersonal, String> arg0) {
				// TODO Auto-generated method stub
				((DataModelPersonal) arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setCedula(arg0.getNewValue());
				actualizatabla(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPersona());
			}    
        });
        
        ColNombres.setCellValueFactory(new PropertyValueFactory<DataModelPersonal, String>("Nombres"));
        ColNombres.setCellFactory(editableFactory);
        ColNombres.setOnEditCommit(new EventHandler<CellEditEvent<DataModelPersonal, String>>() {
			@Override
			public void handle(CellEditEvent<DataModelPersonal, String> arg0) {
				// TODO Auto-generated method stub
				((DataModelPersonal) arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setNombres(arg0.getNewValue());
				actualizatabla(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPersona());
			}    
        });

        ColApellidos.setCellValueFactory(new PropertyValueFactory<DataModelPersonal, String>("Apellidos"));
        ColApellidos.setCellFactory(editableFactory);
        ColApellidos.setOnEditCommit(new EventHandler<CellEditEvent<DataModelPersonal, String>>() {
			@Override
			public void handle(CellEditEvent<DataModelPersonal, String> arg0) {
				// TODO Auto-generated method stub
				((DataModelPersonal) arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setApellidos(arg0.getNewValue());
				actualizatabla(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPersona());
			}    
        });
        
        ColDireccion.setCellValueFactory(new PropertyValueFactory<DataModelPersonal, String>("Direccion"));
        ColDireccion.setCellFactory(editableFactory);
        ColDireccion.setOnEditCommit(new EventHandler<CellEditEvent<DataModelPersonal, String>>() {
			@Override
			public void handle(CellEditEvent<DataModelPersonal, String> arg0) {
				// TODO Auto-generated method stub
				((DataModelPersonal) arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setDireccion(arg0.getNewValue());
				actualizatabla(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPersona());
			}    
        });

        ColTelefonos.setCellValueFactory(new PropertyValueFactory<DataModelPersonal, String>("Telefonos"));
        ColTelefonos.setCellFactory(editableFactory);
        ColTelefonos.setOnEditCommit(new EventHandler<CellEditEvent<DataModelPersonal, String>>() {
			@Override
			public void handle(CellEditEvent<DataModelPersonal, String> arg0) {
				// TODO Auto-generated method stub
				((DataModelPersonal) arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setTelefonos(arg0.getNewValue());
				actualizatabla(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPersona());
			}    
        });

        
        
        ColPrueba.setCellValueFactory(new PropertyValueFactory<DataModelPersonal, Estado>("estado"));
        
//        ColPrueba.setCellFactory(
//        		new Callback<TableColumn<DataModelPersonal, String>, 
//        		TableCell<DataModelPersonal, List<Estado>>>()
//        		
//        	{
//			@Override
//			public TableCell<DataModelPersonal, List<Estado>> call(
//					TableColumn<DataModelPersonal, String> arg0) {
//
//				return null;
//			}
//
//        	
//        });
        
//        ColPrueba.setCellFactory(new PropertyValueFactory<DataModelPersonal, String>("estado"));
        
        
//        ColPrueba.setCellFactory(comboBoxFactory);
        ColPrueba.setCellFactory(ComboBoxTableCell.forTableColumn(Prueba));

        ColPrueba.setOnEditCommit(
        		new EventHandler<CellEditEvent<DataModelPersonal, Estado>>() {
			@Override
			public void handle(CellEditEvent<DataModelPersonal, Estado> arg0) {
				// TODO Auto-generated method stub
				((DataModelPersonal) arg0.getTableView().getItems().get(arg0.getTablePosition().getRow())).setEstado(arg0.getNewValue());
				System.out.println("Este es el valor seleccionado en el ComboBox"+arg0.getNewValue());
				actualizatabla(arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPersona());
			}    
        });

        cargarEstado();
        cargarPersonal();
        TablaPersonas.setItems(datos);
    	
    }    
    
	private void cargarEstado() {
		List<Estado> temp = FXCollections.observableArrayList();
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Estado order by Id asc");
			temp = (List<Estado>) queryr.list();
			sesion.getTransaction().commit();
		}catch (HibernateException e1)
		{
			e1.printStackTrace();
		}
		Prueba.addAll(temp);
	}

	protected void actualizatabla(Personal persona) {
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			sesion.saveOrUpdate(persona);
			sesion.getTransaction().commit();
		}catch (HibernateException e1)
		{
			e1.printStackTrace();
		}		
	}

	private void cargarPersonal() {
		List<Personal> temp = FXCollections.observableArrayList();
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Personal order by Id asc");
			temp = (List<Personal>) queryr.list();
			sesion.getTransaction().commit();
		}catch (HibernateException e1)
		{
			e1.printStackTrace();
		}
		if (!temp.isEmpty())
		{
			for (Personal xx : temp)
			{
				datos.add(new DataModelPersonal(xx));
			}
		}			
	}

	public class EditarCelda<S extends Object, T extends String> extends AbstractEditableTableCell<S, T> {
	    public EditarCelda() {
	    }
	    @Override
	    protected String getString() {
	        return getItem() == null ? "" : getItem().toString();
	    }
	    @Override
	    protected void commitHelper( boolean losingFocus ) {
	        commitEdit(((T) textField.getText()));
	    }
	     
	}    

	Callback<TableColumn, TableCell> editableFactory =
			new Callback<TableColumn, TableCell>() {

				@Override
				public TableCell call(TableColumn arg0) {
					// TODO Auto-generated method stub
					return new EditarCelda();
				}
	};

	
	Callback<TableColumn, TableCell> comboBoxFactory = 
			new Callback<TableColumn, TableCell>() {
        @Override
        public TableCell call(TableColumn p) {
            return new ComboBoxCell();
        }
    };

	
	public class ComboBoxCell extends ComboBoxTableCell<DataModelPersonal, String> {
	    private ComboBox<String> comboBox;
	    public ComboBoxCell() {
	    }
	    @Override
	    public void startEdit() {
	        super.startEdit();

	        if (comboBox == null) {
	            createComboBox();
	        }

	        setGraphic(comboBox);
	        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	    }

	    @Override
	    public void cancelEdit() {
	        super.cancelEdit();

	        setText(String.valueOf(getItem()));
	        setContentDisplay(ContentDisplay.TEXT_ONLY);
	    }

	    public void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty);

	        if (empty) {
	            setText(null);
	            setGraphic(null);
	        } else {
	            if (isEditing()) {
	                if (comboBox != null) {
	                    comboBox.setValue(getString());
	                }
	                setGraphic(comboBox);
	                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            } else {
	                setText(getString());
	                setContentDisplay(ContentDisplay.TEXT_ONLY);
	            }
	        }
	    }

	    private void createComboBox() {
	        // ClassesController.getLevelChoice() is the observable list of String
	        comboBox = new ComboBox((ObservableList) DataModelPersonal.getLista());
	        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
	        comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent t) {
	                if (t.getCode() == KeyCode.ENTER) {
	                    commitEdit(comboBox.getSelectionModel().getSelectedItem());
	                } else if (t.getCode() == KeyCode.ESCAPE) {
	                    cancelEdit();
	                }
	            }
	        });
	    }

	    private String getString() {
	        return getItem() == null ? "" : getItem().toString();
	    }
	}

}

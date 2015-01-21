package data;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DataModelRequisicion {

	private SimpleObjectProperty<TextField> item;
	private SimpleObjectProperty<TextField> descrigral;
	private SimpleObjectProperty<ComboBox<String>> presentacion;
	private SimpleObjectProperty<ComboBox<String>> marca;
	private SimpleObjectProperty<TextField> cantidad;
	private Requisicion requisicion;
	
//	public DataModelRequisicion(SimpleObjectProperty<Label> item,
//			SimpleObjectProperty<Label> descrigral,
//			SimpleObjectProperty<ComboBox<String>> presentacion,
//			SimpleObjectProperty<ComboBox<String>> marca,
//			SimpleObjectProperty<TextField> cant) {
//		super();
//		this.item = item;
//		this.descrigral = descrigral;
//		this.presentacion = presentacion;
//		this.marca = marca;
//		this.cantidad = cant;
//	}

	public DataModelRequisicion() {
		this.item = new SimpleObjectProperty<TextField>();
		this.descrigral = new SimpleObjectProperty<TextField>();
		this.presentacion = new SimpleObjectProperty<ComboBox<String>>();
		this.marca = new SimpleObjectProperty<ComboBox<String>>();
		this.cantidad = new SimpleObjectProperty<TextField>();
		this.requisicion = new Requisicion();
	}

	
	public TextField getItem() {
		return item.get();
	}

	public void setItem(TextField item) {
		this.item.set(item);
	}

	public TextField getDescrigral() {
		return descrigral.get();
	}

	public void setDescrigral(TextField descrigral) {
		this.descrigral.set(descrigral);;
	}

	public ComboBox<String> getPresentacion() {
		return presentacion.get();
	}

	public void setPresentacion(ComboBox<String> presentacion) {
		this.presentacion.set(presentacion);;
	}

	public ComboBox<String> getMarca() {
		return marca.get();
	}

	public void setMarca(ComboBox<String> marca) {
		this.marca.set(marca);
	}

	public TextField getCantidad() {
		return cantidad.get();
	}

	public void setCantidad(TextField cant) {
		this.cantidad.set(cant);
	}

	public Requisicion getRequisicion() {
		return requisicion;
	}

	public void setRequisicion(Requisicion requisicion) {
		this.requisicion = requisicion;
	}
	
}

package controllers;

import java.util.List;

import javax.persistence.Transient;

import data.Turno;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModelTurno {
		
	private SimpleStringProperty descripcion;
	Turno turno; 
	private static ObservableList<String> lista;
	
	public DataModelTurno(String string) {
		descripcion = new SimpleStringProperty(string);
		turno = new Turno();
	}
	
	public DataModelTurno(Turno turno) {
		this.descripcion = new SimpleStringProperty(turno.getDescripcion()==null?"":turno.getDescripcion());
		this.turno = turno;
	}

	public String getDescripcion() {
		return descripcion.get();
	}

	public void setDescripcion(String descrip) {
		turno.setDescripcion(descrip);
		descripcion.set(descrip);
	}
	
	@Transient
	public Turno getTurno(){
		return this.turno;
	}
	
	public static ObservableList<String> getLista() {
		List<Turno> temp = FXCollections.observableArrayList();
//		try 
//		{
//			Session sesion = Principal.fabrica.getCurrentSession();
//			sesion.beginTransaction();
//			Query queryr = sesion.createQuery("from Estado order by Id asc");
//			temp = (List<Estado>) queryr.list();
//			System.out.println("Quiero saber si entró aquí!!!");
//			sesion.getTransaction().commit();
//		}catch (HibernateException e1)
//		{
//			e1.printStackTrace();
//		}
		return lista;
	}

	public void setLista(ObservableList<String> lista) {
		this.lista = lista;
	}
}

package controllers;

import java.util.List;

import javax.persistence.Transient;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Horario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModelHorario {
	
	private SimpleStringProperty horaInicio;
	private SimpleStringProperty horaFin;
	Horario horario; 
	private static ObservableList<String> lista;
	
	public DataModelHorario(String string,	String string2) {
		horaInicio = new SimpleStringProperty(string);
		horaFin = new SimpleStringProperty(string2);
		horario = new Horario();
	}


	public DataModelHorario(Horario horario) {
		this.horaInicio = new SimpleStringProperty(horario.getHoraInicio()==null?"":horario.getHoraInicio());
		this.horaFin = new SimpleStringProperty(horario.getHoraFin()==null?"":horario.getHoraFin());
		this.horario = horario;
	}

	public String getHoraInicio() {
		return horaInicio.get();
	}
	public void setHoraInicio(String hInicio) {
		horario.setHoraInicio(hInicio);
		horaInicio.set(hInicio);
	}
	
	public String getHoraFin() {
		return horaFin.get();
	}
	public void setHoraFin(String hFin) {
		horario.setHoraFin(hFin);
		horaFin.set(hFin);
	}
	
	@Transient
	public Horario getHorario(){
		return this.horario;
	}

	public static ObservableList<String> getLista() {
		List<Horario> temp = FXCollections.observableArrayList();
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

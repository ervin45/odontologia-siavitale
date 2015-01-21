package controllers;

import java.util.List;

import javax.persistence.Transient;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Estado;
import data.Personal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModelPersonal {
	
	private SimpleStringProperty Cedula;
	private SimpleStringProperty Nombres;
	private SimpleStringProperty Apellidos;
	private SimpleStringProperty Direccion;
	private SimpleStringProperty Telefonos;
	private SimpleStringProperty Prueba;
	private Estado estado;
	private Personal persona;
	private static ObservableList<String> lista;
	
	public DataModelPersonal(String string,
			String string2, String string3,
			String string4, String string5,
			String string6) {
		Cedula = new SimpleStringProperty(string);
		Nombres = new SimpleStringProperty(string2);
		Apellidos = new SimpleStringProperty(string3);
		Direccion = new SimpleStringProperty(string4);
		Telefonos = new SimpleStringProperty(string5);
		Prueba = new SimpleStringProperty(string6);
		persona = new Personal();
	}


	public DataModelPersonal(Personal persona) {
		this.Cedula = new SimpleStringProperty(persona.getCedula()==null?"":persona.getCedula());
		this.Nombres = new SimpleStringProperty(persona.getNombres()==null?"":persona.getNombres());
		this.Apellidos = new SimpleStringProperty(persona.getApellidos()==null?"":persona.getApellidos());
		this.Direccion = new SimpleStringProperty(persona.getDireccion()==null?"":persona.getDireccion());
		this.Telefonos = new SimpleStringProperty(persona.getTelefonos()==null?"":persona.getTelefonos());
		this.Prueba = new SimpleStringProperty(persona.getEstado()==null?"":persona.getEstado().getDescripcion());
		this.persona = persona;
	}

	public String getCedula() {
		return Cedula.get();
	}
	public void setCedula(String cedula) {
		persona.setCedula(cedula);
		Cedula.set(cedula);
	}
	public String getNombres() {
		return Nombres.get();
	}
	public void setNombres(String nombres) {
		persona.setNombres(nombres);
		Nombres.set(nombres);
	}
	public String getApellidos() {
		return Apellidos.get();
	}
	public void setApellidos(String apellidos) {
		persona.setApellidos(apellidos);
		Apellidos.set(apellidos);
	}
	public String getDireccion() {
		return Direccion.get();
	}
	public void setDireccion(String direccion) {
		persona.setDireccion(direccion);
		Direccion.set(direccion);
	}
	public String getTelefonos() {
		return Telefonos.get();
	}
	public void setTelefonos(String telefonos) {
		persona.setTelefonos(telefonos);
		Telefonos.set(telefonos);
	}
	
	@Transient
	public Personal getPersona()
	{
		return this.persona;
	}

	public String getPrueba() {
		return Prueba.get();
	}

	public void setPrueba(String prueba) {
		Prueba.set(prueba);
	}
	
	public Estado getEstado()
	{
		return this.getPersona().getEstado();
	}

	public void setEstado(Estado est)
	{
		this.getPersona().setEstado(est);
	}

	public static ObservableList<String> getLista() {
		List<Estado> temp = FXCollections.observableArrayList();
		try 
		{
			Session sesion = Principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryr = sesion.createQuery("from Estado order by Id asc");
			temp = (List<Estado>) queryr.list();
			System.out.println("Quiero saber si entró aquí!!!");
			sesion.getTransaction().commit();
		}catch (HibernateException e1)
		{
			e1.printStackTrace();
		}
		return lista;
	}

	public void setLista(ObservableList<String> lista) {
		this.lista = lista;
	}
	
}

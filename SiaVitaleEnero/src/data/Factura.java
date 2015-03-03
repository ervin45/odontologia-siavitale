package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

 @Entity
public class Factura {

	private int Id;
	private Personal persona;
	private RazonSocial razonSocial;
	private double ivaTotal;
	private double montoTotal;
	
	@javax.persistence.Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="idPersona")
	public Personal getPersona() {
		return persona;
	}
	public void setPersona(Personal persona) {
		this.persona = persona;
	}
	
	@ManyToOne
	@JoinColumn(name="idRazonSocial")
	public RazonSocial getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(RazonSocial razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public double getIvaTotal() {
		return ivaTotal;
	}
	public void setIvaTotal(double ivaTotal) {
		this.ivaTotal = ivaTotal;
	}
	
	public double getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	
}

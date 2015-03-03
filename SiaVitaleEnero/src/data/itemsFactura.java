package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class itemsFactura {

	private int id;
	private ServicioOdontologico servicioOdontologico;
	private Factura factura;
	private double iva;
	private double monto;
		
	@javax.persistence.Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="idServicioOdontologico")
	public ServicioOdontologico getServicioOdontologico() {
		return servicioOdontologico;
	}
	public void setServicioOdontologico(ServicioOdontologico servicioOdontologico) {
		this.servicioOdontologico = servicioOdontologico;
	}
	
	@ManyToOne
	@JoinColumn(name="idFactura")
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	public double getIva() {
		return iva;
	}
	public void setIva(double iva) {
		this.iva = iva;
	}
	
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	
}

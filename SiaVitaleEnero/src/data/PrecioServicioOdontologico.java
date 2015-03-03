package data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PrecioServicioOdontologico {

	private int id;
	private double monto;
	private Date Fecha;
	private ServicioOdontologico servicioOdontologico;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	@ManyToOne
	@JoinColumn(name="idServicioOdontologico")
	public ServicioOdontologico getServicioOdontologico() {
		return servicioOdontologico;
	}
	public void setServicioOdontologico(ServicioOdontologico servicioOdontologico) {
		this.servicioOdontologico = servicioOdontologico;
	}		
}

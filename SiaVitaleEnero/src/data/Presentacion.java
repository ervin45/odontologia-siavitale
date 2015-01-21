package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Presentacion {
	private int Id;
	private String Cantidad;
	private UndMedida undMedida;
	private String Descripcion; 
	private String Descripcion2;
	
	@Transient
	public String getDescripcion()
	{
		return Cantidad + " "+undMedida.getDescripcion();
	}
	
	@Transient
	public String getDescripcion2()
	{
		return undMedida.getDescripcion()+" "+Cantidad;
	}
	
	@Override
	public String toString()
	{
		return ""+Cantidad + " " + undMedida.getDescripcion();
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int codigo) {
		Id = codigo;
	}
	public String getCantidad() {
		return Cantidad;
	}
	public void setCantidad(String cantidad) {
		Cantidad = cantidad;
	}
	public void setUndMedida(UndMedida undMedida) {
		this.undMedida = undMedida;
	}

	@ManyToOne
	@JoinColumn(name="Cod_UndMedida")
	public UndMedida getUndMedida() {
		return undMedida;
	}
	
		
}

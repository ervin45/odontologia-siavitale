package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PresentacionProducto {
	private int Id;
	private Producto producto;
	private Presentacion presentacion;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int codigo) {
		Id = codigo;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_Producto")
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto prod) {
		producto = prod;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_Presentacion")
	public Presentacion getPresentacion() {
		return presentacion;
	}
	
	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

}

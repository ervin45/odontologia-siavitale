package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductoMarca {

	private int Id;
	private Producto producto;
	private Marca marca;
	private String CodigoBarra;
	private String Comentario;
	
	@javax.persistence.Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_Producto")
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	
	@ManyToOne
	@JoinColumn(name="Cod_Marca")
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
	}
	
	public String getCodigoBarra() {
		return CodigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		CodigoBarra = codigoBarra;
	}
	
	@Override
	public String toString()
	{
		return ""+this.getProducto().getCosa().getDescripcion()+" / "+this.getProducto().getPresentacionEntrada()+" / "+this.getMarca().getDescripcion();
	}	
	
}

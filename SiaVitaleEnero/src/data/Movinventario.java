package data;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Movinventario {
	private int Id;
	private ProductoMarca productoMarca;
//	private Date Fecha;
//	private Presentacion presentacion;
//	private String Comprobante;
//	private Deposito MinDepSol;
	private BigDecimal Cantidad;
	private BigDecimal PrecioUnitario;
	private Iva iva; 
	private BigDecimal MontoIva;
	private BigDecimal Total;
//	private Character Tipo;
//	private Area area;
	private Movimiento movimient;
	
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
	public ProductoMarca getProducto() {
		return productoMarca;
	}
	public void setProducto(ProductoMarca producto) {
		this.productoMarca = producto;
	}

//	public Date getFecha() {
//		return Fecha;
//	}
//	public void setFecha(Date fecha) {
//		Fecha = fecha;
//	}
//	
//	@ManyToOne
//	@JoinColumn(name="Cod_Presentacion")
//	public Presentacion getPresentacion() {
//		return presentacion;
//	}
//	public void setPresentacion(Presentacion presentacion) {
//		this.presentacion = presentacion;
//	}
	
	public BigDecimal getCantidad() {
		return Cantidad;
	}
	public void setCantidad(BigDecimal bigDecimal) {
		Cantidad = bigDecimal;
	}

	@ManyToOne
	@JoinColumn(name="codmovimiento")
	public Movimiento getMovimient() {
		return movimient;
	}
	public void setMovimient(Movimiento movimiento) {
		this.movimient = movimiento;
	}

	
	public BigDecimal getPrecioUnitario() {
		return PrecioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		PrecioUnitario = precioUnitario;
	}

	@ManyToOne
	@JoinColumn(name="Cod_Iva")
	public Iva getIva() {
		return iva;
	}
	public void setIva(Iva iva) {
		this.iva = iva;
	}
	public BigDecimal getMontoIva() {
		return MontoIva;
	}
	public void setMontoIva(BigDecimal montoIva) {
		MontoIva = montoIva;
	}
	public BigDecimal getTotal() {
		return Total;
	}
	public void setTotal(BigDecimal total) {
		Total = total;
	}
	
}

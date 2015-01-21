package data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class DocProveedor {
	private int Id;
	private TipoDocProveedor tipoDocumento;
	private String Documento;
	private String Control;
	private String Comprobante;
	private Proveedor proveedor;
	private Date Fecha;
	private AArea area;
	private String Descripcion;
	private TipoMovimiento tipoMovimiento;
	private Double MontoExento;
	private BigDecimal BaseImponible;
	private BigDecimal MontoIva;
	private BigDecimal MontoTotal;
	private String Status;
	private List<DetDocProveedor> detalle;
	private String Comentario;
	private CentrodeCosto centrodecosto;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int codigo) {
		Id = codigo;
	}
	public String getComprobante() {
		return Comprobante;
	}
	public void setComprobante(String comprobante) {
		Comprobante = comprobante;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}

	@ManyToOne
	@JoinColumn(name="Cod_Area")
	public AArea getArea() {
		return area;
	}
	public void setArea(AArea area) {
		this.area = area;
	}

	@ManyToOne
	@JoinColumn(name="Cod_TipoMovimiento")
	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

	@OneToMany(targetEntity = DetDocProveedor.class, 
			mappedBy = "doc", 
			cascade=CascadeType.ALL, 
			fetch=FetchType.EAGER)
	public List<DetDocProveedor> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<DetDocProveedor> detall) {
		this.detalle = detall;
	}

	@ManyToOne
	@JoinColumn(name="Cod_Proveedor")
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getDocumento() {
		return Documento;
	}
	public void setDocumento(String documento) {
		Documento = documento;
	}
	public String getControl() {
		return Control;
	}
	public void setControl(String control) {
		Control = control;
	}
	public Double getMontoExento() {
		return MontoExento;
	}
	public void setMontoExento(Double montoExento) {
		MontoExento = montoExento;
	}
	public BigDecimal getBaseImponible() {
		return BaseImponible;
	}
	public void setBaseImponible(BigDecimal baseImponible) {
		BaseImponible = baseImponible;
	}
	public BigDecimal getMontoIva() {
		return MontoIva;
	}
	public void setMontoIva(BigDecimal montoIva) {
		MontoIva = montoIva;
	}
	public BigDecimal getMontoTotal() {
		return MontoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		MontoTotal = montoTotal;
	}

	@ManyToOne
	@JoinColumn(name="Cod_TipDocProveedor")
	public TipoDocProveedor getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocProveedor tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	@ManyToOne
	@JoinColumn(name="Cod_CentrodeCosto")
	public CentrodeCosto getCentrodecosto() {
		return centrodecosto;
	}
	public void setCentrodecosto(CentrodeCosto centrodecosto) {
		this.centrodecosto = centrodecosto;
	}

	
}


package data;

//import java.time.LocalDate;
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
public class Movimiento {
	private int Id;
	private String Comprobante;
	private Date Fecha;
	private AArea area;
	private Proveedor proveedor;
	private TipoMovimiento tipoMovimiento;
	private Character Status;
	private List<Movinventario> movinventario;

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
	public Character getStatus() {
		return Status;
	}
	public void setStatus(Character status) {
		Status = status;
	}

	@OneToMany(targetEntity = Movinventario.class, 
			mappedBy = "movimient", 
			cascade=CascadeType.ALL,
			orphanRemoval=true,
			fetch=FetchType.EAGER)
	public List<Movinventario> getMovinventario() {
		return movinventario;
	}
	
	public void setMovinventario(List<Movinventario> movinventario) {
		this.movinventario = movinventario;
	}

	@ManyToOne
	@JoinColumn(name="Cod_Proveedor")
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
//	public void addMovinventario(Movinventario xx)
//	{
//		xx.setMovimient(this);
//		this.getMovinventario().add(xx);
//	}
//
//	public void remMovinventario(Movinventario xx)
//	{
//		this.getMovinventario().remove(xx);
//	}
}


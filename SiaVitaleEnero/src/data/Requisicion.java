package data;

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

import data.Material_req;

@Entity
public class Requisicion {
	private int Id;
	private Date Fecha;
	private Unidad UnidadReq;
	private Prioridad PrioridadReq;
	private List<Material_req> materiales;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
		
	@ManyToOne
	@JoinColumn(name="Unidad_Id")
	public Unidad getUnidadReq() {
		return UnidadReq;
	}
	public void setUnidadReq(Unidad unidad) {
		this.UnidadReq = unidad;
	}
	
	@ManyToOne
	@JoinColumn(name="Prioridad_Id")
	public Prioridad getPrioridadReq() {
		return PrioridadReq;
	}
	public void setPrioridadReq(Prioridad prioridad) {
		this.PrioridadReq = prioridad;
	}
	
	@OneToMany(targetEntity = Material_req.class,
			mappedBy = "requisicion",
			cascade=CascadeType.ALL,
			fetch=FetchType.EAGER)
	public List<Material_req> getMateriales() {
		return materiales;
	}
	public void setMateriales(List<Material_req> materiales1) {
		this.materiales = materiales1;
	}

}

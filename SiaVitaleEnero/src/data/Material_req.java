package data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Material_req {
	private int Id;
	private Requisicion requisicion;
	private DescriGral descrigral;
	private BigDecimal Cantidad;
	private Presentacion presentacion;
	private Marca marca;
	private String Comentario;
	private Character Status;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="Requis_Id")
	public Requisicion getRequisicion() {
		return requisicion;
	}
	public void setRequisicion(Requisicion requisicion) {
		this.requisicion = requisicion;
	}

	@ManyToOne
	@JoinColumn(name="Cosa_Id")
	public DescriGral getDescrigral() {
		return descrigral;
	}
	public void setDescrigral(DescriGral cosa) {
		this.descrigral = cosa;
	}

	public BigDecimal getCantidad() {
		return Cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		Cantidad = cantidad;
	}
	
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
	}
	
	public Character getStatus() {
		return Status;
	}
	public void setStatus(Character status) {
		Status = status;
	}
	
	@ManyToOne
	@JoinColumn(name="Id_Presenta")
	public Presentacion getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(Presentacion presentaMat) {
		presentacion = presentaMat;
	}
	
	
	@ManyToOne
	@JoinColumn(name="Id_Marca")
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
}

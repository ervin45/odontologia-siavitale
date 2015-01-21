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

@Entity
public class MovBancario {
	private int Id;
	private Banco banco;
	private Date Fecha;
	private String TipoComprobante;
	private String Codigo;
	private String Descripcion;
	private Character Status;


	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int codigo) {
		Id = codigo;
	}

	@ManyToOne
	@JoinColumn(name="Cod_Banco")
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	
	public String getTipoComprobante() {
		return TipoComprobante;
	}
	public void setTipoComprobante(String tipoComprobante) {
		TipoComprobante = tipoComprobante;
	}
	
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	public Character getStatus() {
		return Status;
	}
	public void setStatus(Character status) {
		Status = status;
	}

}


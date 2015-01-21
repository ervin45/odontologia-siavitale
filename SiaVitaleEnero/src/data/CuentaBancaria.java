package data;

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
public class CuentaBancaria {
	private int Id;
	private Banco banco;
	private String Descripcion;
	private CuentaContable cuentacontable;
	private TipoCuentaBancaria tipocuentabancaria;
	private String Codigo;
	private String Comentario;
	private String Status;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int idcc) {
		Id = idcc;
	}

	public String getDescripcion()
	{
		return Descripcion;
	}
	
	public void setDescripcion(String descri)
	{
		Descripcion = descri;
	}

	
	@ManyToOne
	@JoinColumn(name="Cod_Banco")
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	@ManyToOne
	@JoinColumn(name="Cod_CuentaContable")
	public CuentaContable getCuentacontable() {
		return cuentacontable;
	}
	
	public void setCuentacontable(CuentaContable cuentacontable) {
		this.cuentacontable = cuentacontable;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_TipoCuentaBancaria")	
	public TipoCuentaBancaria getTipocuentabancaria() {
		return tipocuentabancaria;
	}
	public void setTipocuentabancaria(TipoCuentaBancaria tipocuentabancaria) {
		this.tipocuentabancaria = tipocuentabancaria;
	}
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
	}
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
}

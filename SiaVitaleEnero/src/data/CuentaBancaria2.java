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
public class CuentaBancaria2 {
	private int Id;
	private Banco banco;
	private String codigoAgencia;
	private String descripcionAgencia;
	private String telefonoAgencia;
	private String direccionAgencia;
	private TipoCuentaBancaria tipocuentabancaria;
	private String numero;
	private CuentaContable cuentacontable;
	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int idcc) {
		Id = idcc;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_Banco")
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}	

	public String getCodigoAgencia() {
		return codigoAgencia;
	}
	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}
	
	public String getDescripcionAgencia() {
		return descripcionAgencia;
	}
	public void setDescripcionAgencia(String descripcionAgencia) {
		this.descripcionAgencia = descripcionAgencia;
	}
	
	public String getTelefonoAgencia() {
		return telefonoAgencia;
	}	
	public void setTelefonoAgencia(String telefonoAgencia) {
		this.telefonoAgencia = telefonoAgencia;
	}
	
	public String getDireccionAgencia() {
		return direccionAgencia;
	}
	public void setDireccionAgencia(String direccionAgencia) {
		this.direccionAgencia = direccionAgencia;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_TipoCuentaBancaria")	
	public TipoCuentaBancaria getTipocuentabancaria() {
		return tipocuentabancaria;
	}
	public void setTipocuentabancaria(TipoCuentaBancaria tipocuentabancaria) {
		this.tipocuentabancaria = tipocuentabancaria;
	}	
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_CuentaContable")
	public CuentaContable getCuentacontable() {
		return cuentacontable;
	}	
	public void setCuentacontable(CuentaContable cuentacontable) {
		this.cuentacontable = cuentacontable;
	}	
}

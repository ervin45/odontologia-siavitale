package data;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Iva {
	private int Cod;
	private String Descripcion;
	private BigDecimal Alicuota;
	private Date Vigencia;

	@Id
	@GeneratedValue
	public int getCod() {
		return Cod;
	}
	public void setCod(int cod) {
		Cod = cod;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public BigDecimal getAlicuota() {
		return Alicuota;
	}
	public void setAlicuota(BigDecimal alicuota) {
		Alicuota = alicuota;
	}
	public Date getVigencia() {
		return Vigencia;
	}
	public void setVigencia(Date vigencia) {
		Vigencia = vigencia;
	}
	@Override
	public String toString()
	{
		return this.getDescripcion()+" ("+this.getAlicuota().toString()+"%)";
	}
}

package data;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DetDocProveedor {
	private int Id;
	private String Descripcion;
//	private Date Fecha;
	private BigDecimal Monto;
	private DocProveedor doc;
	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int codigo) {
		Id = codigo;
	}

	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public BigDecimal getMonto() {
		return Monto;
	}
	public void setMonto(BigDecimal monto) {
		Monto = monto;
	}

	@ManyToOne
	@JoinColumn(name="Cod_DocProveedor")
	public DocProveedor getDoc() {
		return doc;
	}
	
	public void setDoc(DocProveedor doc) {
		this.doc = doc;
	}
	


}

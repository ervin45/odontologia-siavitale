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
public class CuentaContable {
	private int Id;
	private String Codigo;
	private String Descripcion;
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

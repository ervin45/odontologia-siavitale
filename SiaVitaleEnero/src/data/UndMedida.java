package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UndMedida {
	private int Id;
	private String Codigo;
	private String Descripcion;
	private String Comentario;
	private TipoUndMedida tipoUndMedida;
	private String Status;
	
	@Override
	public String toString()
	{
		return Descripcion;
	}

	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
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
	public void setDescripcion(String descri) {
		Descripcion = descri;
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

	@ManyToOne
	@JoinColumn(name="Cod_TipoUndMedida")
	public TipoUndMedida getTipoUndMedida() {
		return tipoUndMedida;
	}


	public void setTipoUndMedida(TipoUndMedida tipoUndMedida) {
		this.tipoUndMedida = tipoUndMedida;
	}
	
	
}

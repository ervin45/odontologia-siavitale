package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Marca {
	private int Id;
//	private String Codigo;
	private String Descripcion;
	private String Comentario;
	private String Status;
	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}

//	public String getCodigo() {
//		return Codigo;
//	}
//	public void setCodigo(String cod) {
//		Codigo = cod;
//	}

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
	public void setStatus(String stat) {
		Status = stat;
	}

	@Override
	public String toString()
	{
		return Descripcion;
	}

}

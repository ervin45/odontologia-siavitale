package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Unidad {
	private int Id;
	private String Descripcion;
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
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
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
	
	@Override
	public String toString()
	{
		return this.getDescripcion();
	}	
}

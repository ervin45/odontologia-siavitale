package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AArea {
	private int Cod;
	private String Descripcion;
	private String Comentario;
	
	@Id
	@GeneratedValue
	public int getId() {
		return Cod;
		
	}
	public void setId(int cod) {
		Cod = cod;
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
}

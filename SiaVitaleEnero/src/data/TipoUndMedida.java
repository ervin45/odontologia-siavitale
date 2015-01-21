package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class TipoUndMedida {
	private int Id;
	private String Descripcion;
	
	@javax.persistence.Id
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
	
	
}

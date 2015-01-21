package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Estado {

	private int Id;
	private String Descripcion;
	private String Iso_3166_2;
	
	@Override
	public String toString() {
		return Descripcion;
	}
	
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
	public String getIso_3166_2() {
		return Iso_3166_2;
	}
	public void setIso_3166_2(String iso_3166_2) {
		Iso_3166_2 = iso_3166_2;
	}
	
	
}

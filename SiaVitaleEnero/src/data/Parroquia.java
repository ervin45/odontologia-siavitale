package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Parroquia {

	private int Id;
	private String Descripcion;
	private Municipio municipio;
	
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
	
	@ManyToOne
	@JoinColumn(name="Cod_Municipio")
	public Municipio getMunicipio() {
		return municipio;
	}
	public void setMunicipio(Municipio mun) {
		this.municipio = mun;
	}
	
	
}

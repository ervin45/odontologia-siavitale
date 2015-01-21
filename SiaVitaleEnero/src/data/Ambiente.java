package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Ambiente {

	private int ambienteId;
	private String Descripcion;
	private List<Cargo> cargo = new ArrayList<Cargo>();
	
	@Override 
	public String toString() {
		return Descripcion;
	}
	
	@Id
	@GeneratedValue
	public int getAmbienteId() {
		return ambienteId;
	}
	public void setAmbienteId(int id) {
		ambienteId = id;
	}	
	
	public String getDescripcion() {
		return Descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="ASOCIAT_CARGO_AMBIENTE", 
	joinColumns={@JoinColumn(name="ambienteId")},
	inverseJoinColumns={@JoinColumn(name="cargoId")})
	public List<Cargo> getCargo() {
		return cargo;
	}	
	
	public void setCargo(List<Cargo> cargo) {
		this.cargo = cargo;
	}
}

package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Paciente {
	
	private int Id;
	private String sintomas;
	private Personal persona;
	
	@javax.persistence.Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getSintomas() {
		return sintomas;
	}
	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}
	
	@ManyToOne
	@JoinColumn(name="idPersona")
	public Personal getPersona() {
		return persona;
	}
	public void setPersona(Personal persona) {
		this.persona = persona;
	}
}

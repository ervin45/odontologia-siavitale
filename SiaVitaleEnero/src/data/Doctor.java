package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Doctor {

	private int Id;
	private String especialidad;
	private Personal persona;
	
	@javax.persistence.Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
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

package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@Entity
public class Turno {

	private int IdTurno;
	private String descripcion;
	private List<Horario> horarios = new ArrayList<Horario>();
		
	@javax.persistence.Id
	@GeneratedValue
	public int getIdTurno() {
		return IdTurno;
	}
	public void setIdTurno(int id) {
		IdTurno = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	@JoinTable(name="Horario_Turno",
				joinColumns={@JoinColumn(name="Id_Turno")},
				inverseJoinColumns={@JoinColumn(name="Id_Horario")})
	public List<Horario> getHorarios() {
		return horarios;
	}
	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}
}

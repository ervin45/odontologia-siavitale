package data;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;


@Entity
public class Horario {

	private int IdHorario;
	private String horaInicio;
	private String horaFin;
	private List<Turno> turnos = new ArrayList<Turno>();
	
	@javax.persistence.Id
	@GeneratedValue
	public int getIdHorario() {
		return IdHorario;
	}
	public void setIdHorario(int idHorario) {
		IdHorario = idHorario;
	}
	
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	@JoinTable(name="Horario_Turno",
				joinColumns={@JoinColumn(name="Id_Horario")},
				inverseJoinColumns={@JoinColumn(name="Id_Turno")})
	public List<Turno> getTurnos() {
		return turnos;
	}
	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}	
}

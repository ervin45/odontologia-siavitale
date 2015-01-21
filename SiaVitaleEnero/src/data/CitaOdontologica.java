package data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CitaOdontologica {

	private int Id;
	private Paciente paciente;
	private Doctor doctor;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	private String observacion;
	private String asistencia;

	@javax.persistence.Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		this.Id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="idPaciente")
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@ManyToOne
	@JoinColumn(name="idDoctor")
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}	
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	 
	
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public String getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}	
	
	@Override
	public String toString(){
		return this.paciente.getPersona().getNombres()+" "+this.paciente.getPersona().getApellidos()+", "+this.getObservacion();
	}
}

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
import javax.persistence.OneToMany;


@Entity
public class Cargo {
	
	private int cargoId;
	private String Nombre;
	private int SupervisaA;  // Código de Cargo a quien Supervisa
	private int SupervisadoPor;  // Código de Cargo de Supervisor
	private String FuncionGeneral;  // Texto que describe la función General
	private List<Responsabilidad> responsabilidad;
	//private List<Ambiente> ambiente;
	private List<Ambiente> ambiente = new ArrayList<Ambiente>();  // Condiciones Ambientales y Riesgo de Trabajo
	private List<Riesgo> riesgo; // Condiciones Ambientales y Riesgo de Trabajo
	private List<Esfuerzo> esfuerzo;  // Condiciones Ambientales y Riesgo de Trabajo
	private List<Experiencia> experiencia;  // Perfil del Cargo
	private List<Educacion> educacion;  // Perfil del Cargo
	
	@Id
	@GeneratedValue
	public int getcargoId() {
		return cargoId;
	}	
	public void setcargoId(int id) {
		cargoId = id;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public int getSupervisaA() {
		return SupervisaA;
	}
	public void setSupervisaA(int supervisaA) {
		SupervisaA = supervisaA;
	}
	public int getSupervisadoPor() {
		return SupervisadoPor;
	}
	public void setSupervisadoPor(int supervisadoPor) {
		SupervisadoPor = supervisadoPor;
	}
	public String getFuncionGeneral() {
		return FuncionGeneral;
	}
	public void setFuncionGeneral(String funcionGeneral) {
		FuncionGeneral = funcionGeneral;
	}
	
	@ManyToMany(targetEntity = Responsabilidad.class)
	public List<Responsabilidad> getResponsabilidad() {
		return responsabilidad;
	}
	public void setResponsabilidad(List<Responsabilidad> responsabilidad) {
		this.responsabilidad = responsabilidad;
	}
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="ASOCIAT_CARGO_AMBIENTE", 
	joinColumns={@JoinColumn(name="cargoId")},
	inverseJoinColumns={@JoinColumn(name="ambienteId")})
	public List<Ambiente> getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(List<Ambiente> ambiente) {
		this.ambiente = ambiente;
	}
	
	@ManyToMany(targetEntity = Riesgo.class)
	public List<Riesgo> getRiesgo() {
		return riesgo;
	}
	public void setRiesgo(List<Riesgo> riesgo) {
		this.riesgo = riesgo;
	}	
	
	@ManyToMany(targetEntity = Esfuerzo.class)		
	public List<Esfuerzo> getEsfuerzo() {
		return esfuerzo;
	}
	public void setEsfuerzo(List<Esfuerzo> esfuerzo) {
		this.esfuerzo = esfuerzo;
	}
	
	@ManyToMany(targetEntity = Experiencia.class)
	public List<Experiencia> getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(List<Experiencia> experiencia) {
		this.experiencia = experiencia;
	}
		
	@ManyToMany(targetEntity = Educacion.class)
	public List<Educacion> getEducacion() {
		return educacion;
	}
	public void setEducacion(List<Educacion> educacion) {
		this.educacion = educacion;
	}
	
}

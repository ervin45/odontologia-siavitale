package controllers;

import data.Paciente;

public class ContextoCronograma {
	
	private static final ContextoCronograma instance = new ContextoCronograma();
	
	private Paciente Paciente;
	private String diaClick;
	private boolean banderaVentana;
	private String observacionCita;
	
	public static ContextoCronograma getInstance() {
        return instance;        
	}

	public Paciente getPaciente() {
		return Paciente;
	}

	public void setPaciente(Paciente objPaciente) {
		this.Paciente = objPaciente;
	}

	public String getDiaClick() {
		return diaClick;
	}

	public void setDiaClick(String diaClick) {
		this.diaClick = diaClick;
	}

	public boolean getBanderaVentana() {
		return banderaVentana;
	}

	public void setBanderaVentana(boolean bandera) {
		this.banderaVentana = bandera;
	}

	public String getObservacionCita() {
		return observacionCita;
	}

	public void setObservacionCita(String observacion) {
		observacionCita = observacion;
	}		
	
	
}

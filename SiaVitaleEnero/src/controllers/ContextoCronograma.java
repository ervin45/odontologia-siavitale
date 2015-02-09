package controllers;

import data.Paciente;

public class ContextoCronograma {
	
	private static final ContextoCronograma instance = new ContextoCronograma();
	
	private Paciente Paciente;
	private String diaClick;
	private boolean banderaVentana;
	private boolean banderaVentNuevaCita;
	private boolean banderaVentEliminarCita;
	private String observacionCita;
	private boolean banderaEliminarCita;
	
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

	public boolean getBanderaVentNuevaCita() {
		return banderaVentNuevaCita;
	}

	public void setBanderaVentNuevaCita(boolean banderaVentNuevaCita) {
		this.banderaVentNuevaCita = banderaVentNuevaCita;
	}

	public boolean getBanderaEliminarCita() {
		return banderaEliminarCita;
	}

	public void setBanderaEliminarCita(boolean banderaEliminarCita) {
		this.banderaEliminarCita = banderaEliminarCita;
	}

	public boolean getBanderaVentEliminarCita() {
		return banderaVentEliminarCita;
	}

	public void setBanderaVentEliminarCita(boolean banderaVentEliminarCita) {
		this.banderaVentEliminarCita = banderaVentEliminarCita;
	}	
	
}

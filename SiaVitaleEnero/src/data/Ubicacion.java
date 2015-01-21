package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Ubicacion {
	private int Id;
	private String Codigo;
	private Boolean Activo;
	private String Estante;
	private String Columna;
	private String Nivel;
	private Almacen almacen;
	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}	
	
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	
	public Boolean getActivo() {
		return Activo;
	}
	public void setActivo(Boolean activo) {
		Activo = activo;
	}

	@ManyToOne
	@JoinColumn(name="Almacen_Id")
	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	
	public String getEstante() {
		return Estante;
	}
	public void setEstante(String estante) {
		Estante = estante;
	}
	
	public String getColumna() {
		return Columna;
	}
	public void setColumna(String columna) {
		Columna = columna;
	}
	
	public String getNivel() {
		return Nivel;
	}
	public void setNivel(String nivel) {
		Nivel = nivel;
	}

}

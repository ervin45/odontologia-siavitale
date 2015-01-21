package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PersonaContactoBanco {

	private int Id;
	private String Nombre;
	private String Telefono;
	private String CorreoElectronico;
	private Banco banco;	
	
//	@Override
//	public String toString() {
//		return Descripcion;
//	}
	
	@javax.persistence.Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	
	public String getCorreoElectronico() {
		return CorreoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		CorreoElectronico = correoElectronico;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_Banco")
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}	
	
//	@ManyToOne
//	@JoinColumn(name="Cod_Municipio")
//	public Municipio getMunicipio() {
//		return municipio;
//	}
//	public void setMunicipio(Municipio mun) {
//		this.municipio = mun;
//	}	
}

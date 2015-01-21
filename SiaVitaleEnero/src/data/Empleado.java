package data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Empleado {
	
	private int Id;
	private String Cedula;
	private String Nombre;
	private String Apellido;
	private Date FechadeNacimiento;
	private Character Sexo;
	private String Calle;
	private String Edificio;
	private String Numero;
	private String Sector;
	private Ciudad ciudad;
	private Estado estado;
	private String Telefono;
	private String Celular;
	private String Email;
	private String Twitter;
	private String Facebook;
	private String GrupoSanguineo;

	@javax.persistence.Id
	public int getId() {
		return Id;
	}
	public void setId(int cod) {
		Id = cod;
	}
	
	public String getCedula() {
		return Cedula;
	}

	public void setCedula(String cedula) {
		Cedula = cedula;
	}

	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getApellido() {
		return Apellido;
	}
	public void setApellido(String apellido) {
		Apellido = apellido;
	}
	public Date getFechadeNacimiento() {
		return FechadeNacimiento;
	}
	public void setFechadeNacimiento(Date fechadeNacimiento) {
		FechadeNacimiento = fechadeNacimiento;
	}
	public Character getSexo() {
		return Sexo;
	}
	public void setSexo(Character sexo) {
		Sexo = sexo;
	}
	public String getCalle() {
		return Calle;
	}
	public void setCalle(String calle) {
		Calle = calle;
	}
	public String getEdificio() {
		return Edificio;
	}
	public void setEdificio(String edificio) {
		Edificio = edificio;
	}
	public String getNumero() {
		return Numero;
	}
	public void setNumero(String numero) {
		Numero = numero;
	}
	public String getSector() {
		return Sector;
	}
	public void setSector(String sector) {
		Sector = sector;
	}
	
	@ManyToOne
	@JoinColumn(name="Id_Ciudad")
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	
	@ManyToOne
	@JoinColumn(name="Id_Estado")
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getCelular() {
		return Celular;
	}
	public void setCelular(String celular) {
		Celular = celular;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getTwitter() {
		return Twitter;
	}
	public void setTwitter(String twitter) {
		Twitter = twitter;
	}
	public String getFacebook() {
		return Facebook;
	}
	public void setFacebook(String facebook) {
		Facebook = facebook;
	}
	public String getGrupoSanguineo() {
		return GrupoSanguineo;
	}
	public void setGrupoSanguineo(String grupoSanguineo) {
		GrupoSanguineo = grupoSanguineo;
	}
	
}

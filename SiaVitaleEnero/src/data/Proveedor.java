package data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Proveedor {
	private int Id;
	private String Rif;
	private String Razon;
	private String Calle;
	private String Edificio;
	private String Numero;
	private String Sector;
	private Ciudad ciudad;
	private Estado estado;
	private String Telefono;
	private String Fax;
	private String Celular;
	private String Email;
	private String Web;
	private String Contacto;
	private String TelContacto;
	private String Status;
	private String Comentario;
	private List<DescriGral> ProvDescriGral;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getRif() {
		return Rif;
	}
	public void setRif(String rif) {
		Rif = rif;
	}
	public String getRazon() {
		return Razon;
	}
	public void setRazon(String razon) {
		Razon = razon;
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
	public String getFax() {
		return Fax;
	}
	public void setFax(String fax) {
		Fax = fax;
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
	public String getWeb() {
		return Web;
	}
	public void setWeb(String web) {
		Web = web;
	}
	public String getContacto() {
		return Contacto;
	}
	public void setContacto(String contacto) {
		Contacto = contacto;
	}
	public String getTelContacto() {
		return TelContacto;
	}
	public void setTelContacto(String telContacto) {
		TelContacto = telContacto;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
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

	@ManyToMany(targetEntity = DescriGral.class, 
			cascade=CascadeType.PERSIST, 
			fetch=FetchType.EAGER)
	public List<DescriGral> getProvDescriGral() {
		return ProvDescriGral;
	}
	
	public void setProvDescriGral(List<DescriGral> provDescriGral) {
		ProvDescriGral = provDescriGral;
	}

	@Override
	public String toString()
	{
		return this.getRazon();
	}

	
}

package data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Banco {
	private int Id;
	private String Descripcion;
	private String Comentario;
	private String Status;
	private String PaginaWeb;
	private String NombreAgencia;
	private String CodigoAgencia;
	private String Direccion;
	private String Telefono;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int idcc) {
		Id = idcc;
	}

	public String getDescripcion(){
		return Descripcion;
	}
	
	public void setDescripcion(String descri){
		Descripcion = descri;
	}
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
	}
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	public String getPaginaWeb() {
		return PaginaWeb;
	}
	public void setPaginaWeb(String paginaWeb) {
		PaginaWeb = paginaWeb;
	}
	
	public String getNombreAgencia() {
		return NombreAgencia;
	}
	public void setNombreAgencia(String nombreAgencia) {
		NombreAgencia = nombreAgencia;
	}
	
	public String getCodigoAgencia() {
		return CodigoAgencia;
	}
	public void setCodigoAgencia(String codigoAgencia) {
		CodigoAgencia = codigoAgencia;
	}
	
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}	
}

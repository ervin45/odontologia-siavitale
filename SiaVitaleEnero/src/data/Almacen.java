package data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Almacen {

	private int Cod;
//	private String Codigo;
	private String Nombre;
	private String Descripcion;
	private Boolean Activo;
	private String Direccion;
	private List<Ubicacion> ubicaciones;
	
	@Id
	@GeneratedValue
	public int getId() {
		return Cod;
	}
	public void setId(int id) {
		Cod = id;
	}	
	
//	public String getCodigo() {
//		return Codigo;
//	}
//	public void setCodigo(String string) {
//		Codigo = string;
//	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descrip) {
		Descripcion = descrip;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setActivo(Boolean activo) {
		Activo = activo;
	}
	public Boolean getActivo() {
		return Activo;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getDireccion() {
		return Direccion;
	}

	@OneToMany(targetEntity = Ubicacion.class,
			mappedBy = "almacen",
			cascade=CascadeType.ALL,
			fetch=FetchType.EAGER)
	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}	

	public void setUbicaciones(List<Ubicacion> ubicacion) {
		this.ubicaciones = ubicacion;
	}
}

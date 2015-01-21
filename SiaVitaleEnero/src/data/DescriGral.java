package data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DescriGral {
	private int Id;
//	private String Codigo;
	private String Descripcion;
	private String Comentario;
	private String Status;
	private List<Producto> productos;
//	private GrupoProducto GrupoCosa;
	
	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}

//	public String getCodigo() {
//		return Codigo;
//	}
//	public void setCodigo(String id) {
//		Codigo = id;
//	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
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
	
	@OneToMany(targetEntity = Producto.class, 
			mappedBy = "cosa", 
			cascade=CascadeType.ALL)
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
//	@ManyToOne
//	@JoinColumn(name="Grupo_Id")
//	public GrupoProducto getGrupoCosa() {
//		return GrupoCosa;
//	}
//	public void setGrupoCosa(GrupoProducto grupo) {
//		this.GrupoCosa = grupo;
//	}

	@Override
	public String toString()
	{
		return this.getDescripcion();
	}

}
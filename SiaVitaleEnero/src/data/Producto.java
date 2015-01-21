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
public class Producto {
	private int Id;
	private DescriGral cosa;
	private Presentacion PresentacionEntrada;
	private List<ProductoMarca> productomarca;

	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int proCodigo) {
		Id = proCodigo;
	}
	
	@ManyToOne
	@JoinColumn(name="Cod_Cosa")
	public DescriGral getCosa() {
		return cosa;
	}
	public void setCosa(DescriGral cosaProducto) {
		cosa = cosaProducto;
	}

	@ManyToOne
	@JoinColumn(name="Cod_PresentacionEntrada")
	public Presentacion getPresentacionEntrada() {
		return PresentacionEntrada;
	}
	public void setPresentacionEntrada(Presentacion proPresen) {
		PresentacionEntrada = proPresen;
	}
	
	@OneToMany(targetEntity = ProductoMarca.class, 
			mappedBy = "producto", 
			cascade=CascadeType.ALL)
	public List<ProductoMarca> getProductomarca() {
		return productomarca;
	}
	public void setProductomarca(List<ProductoMarca> productomarca) {
		this.productomarca = productomarca;
	}
	

//	public void setSalidaProducto(Presentacion salidaProducto) {
//		SalidaProducto = salidaProducto;
//	}
//
//	@ManyToOne
//	@JoinColumn(name="Cod_UMedida2")
//	public Presentacion getSalidaProducto() {
//		return SalidaProducto;
//	}	
	
}

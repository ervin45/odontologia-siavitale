package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TipoDocProveedor {
	private int Id;
	private String Descripcion;
	private Boolean AfectaInventario;
	private Boolean Imprime;
	private Boolean Entrada;
	
	@Id
	@GeneratedValue
	public int getId() {
		return Id;
	}
	public void setId(int codigo) {
		Id = codigo;
	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	public Boolean getAfectaInventario() {
		return AfectaInventario;
	}
	public void setAfectaInventario(Boolean afectaInventario) {
		AfectaInventario = afectaInventario;
	}
	
	public Boolean getImprime() {
		return Imprime;
	}
	public void setImprime(Boolean imprime) {
		Imprime = imprime;
	}
	
	public Boolean getEntrada() {
		return Entrada;
	}
	public void setEntrada(Boolean entrada) {
		Entrada = entrada;
	}
}

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
public class TipoCuentaBancaria {

	private int Id;
	private String Descripcion;

	@Id
	@GeneratedValue
	public int getId(){
		return Id;
	}
	public void setId(int idcc){
		Id = idcc;
	}

	public String getDescripcion(){
		return Descripcion;
	}
	
	public void setDescripcion(String descri){
		Descripcion = descri;
	}	
}

package org.clintonhealthaccess.vca.domain.relationships;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Objeto que representa la clave unica de relacion foco/localidad
 * 
 * @author William Aviles
 **/
@Embeddable
public class FocoLocalidadId implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String foco;
	private String localidad;
	
	public FocoLocalidadId(){
		
	}
	
	

	public FocoLocalidadId(String foco, String localidad) {
		super();
		this.foco = foco;
		this.localidad = localidad;
	}



	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FocoLocalidadId))
			return false;
		FocoLocalidadId castOther = (FocoLocalidadId) other;

		return this.getFoco().equals(castOther.getFoco())
				&& this.getLocalidad().equals(castOther.getLocalidad());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 * this.foco.hashCode() * this.localidad.hashCode();
		return result;	
	}
	
	@Column(name = "foco", nullable = false, length =50)
	public String getFoco() {
		return foco;
	}


	public void setFoco(String foco) {
		this.foco = foco;
	}

	@Column(name = "locality", nullable = false, length =50)
	public String getLocalidad() {
		return localidad;
	}


	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString(){
		return foco;
	}

}
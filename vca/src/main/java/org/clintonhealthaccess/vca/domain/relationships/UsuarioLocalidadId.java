package org.clintonhealthaccess.vca.domain.relationships;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Objeto que representa la clave unica de relacion usuario/localidad
 * 
 * @author William Aviles
 **/
@Embeddable
public class UsuarioLocalidadId implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String localidad;
	
	public UsuarioLocalidadId(){
		
	}
	
	

	public UsuarioLocalidadId(String usuario, String localidad) {
		super();
		this.usuario = usuario;
		this.localidad = localidad;
	}



	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UsuarioLocalidadId))
			return false;
		UsuarioLocalidadId castOther = (UsuarioLocalidadId) other;

		return this.getUsuario().equals(castOther.getUsuario())
				&& this.getLocalidad().equals(castOther.getLocalidad());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 * this.usuario.hashCode() * this.localidad.hashCode();
		return result;	
	}
	
	@Column(name = "user", nullable = false, length =50)
	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
		return usuario;
	}

}
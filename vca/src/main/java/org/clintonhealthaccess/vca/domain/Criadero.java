package org.clintonhealthaccess.vca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;



/**
 * Criadero 
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "criaderos", catalog = "vca")
public class Criadero extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Localidad local;
	private String tipo;
	private String info;
	private Double size;
	private String especie;
	
	
	public Criadero() {
		super();
	}



	@Id
    @Column(name = "id", nullable = false, length = 50)
	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	
	@ManyToOne(optional=false)
	@JoinColumn(name="local")
    @ForeignKey(name = "FK_CRIADERO_LOCALIDAD")
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}

	@Column(name = "tipo", nullable = false, length = 50)
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	@Column(name = "info", nullable = true, length = 500)
	public String getInfo() {
		return info;
	}



	public void setInfo(String info) {
		this.info = info;
	}
	
	
	@Column(name = "size", nullable = true)
	public Double getSize() {
		return size;
	}



	public void setSize(Double size) {
		this.size = size;
	}


	@Column(name = "especie", nullable = false, length = 50)
	public String getEspecie() {
		return especie;
	}



	public void setEspecie(String especie) {
		this.especie = especie;
	}



	@Override
	public boolean isFieldAuditable(String fieldname) {
		return true;
	}
	
	
	@Override
	public String toString(){
		return this.getIdent();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Criadero))
			return false;
		
		Criadero castOther = (Criadero) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

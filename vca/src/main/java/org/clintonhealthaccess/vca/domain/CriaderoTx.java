package org.clintonhealthaccess.vca.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;



/**

 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "criaderotxs", catalog = "vca")
public class CriaderoTx extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Criadero criadero;
	private Date txDate;
	private String txType;
	private String obs;
	
	

	public CriaderoTx() {
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
	@JoinColumn(name="criadero")
    @ForeignKey(name = "FK_TX_CR")
	public Criadero getCriadero() {
		return criadero;
	}

	public void setCriadero(Criadero criadero) {
		this.criadero = criadero;
	}




	@Column(name = "txDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)	
	public Date getTxDate() {
		return txDate;
	}



	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}



	@Column(name = "txType", nullable = false , length = 50)
	public String getTxType() {
		return txType;
	}



	public void setTxType(String txType) {
		this.txType = txType;
	}
	

	@Column(name = "obs", nullable = true , length = 500)
	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
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
		if (!(other instanceof CriaderoTx))
			return false;
		
		CriaderoTx castOther = (CriaderoTx) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

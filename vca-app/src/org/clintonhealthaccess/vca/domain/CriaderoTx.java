package org.clintonhealthaccess.vca.domain;

import java.util.Date;




/**

 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class CriaderoTx extends BaseMetaData{
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



	public String getIdent() {
		return ident;
	}



	public void setIdent(String ident) {
		this.ident = ident;
	}



	public Criadero getCriadero() {
		return criadero;
	}



	public void setCriadero(Criadero criadero) {
		this.criadero = criadero;
	}



	public Date getTxDate() {
		return txDate;
	}



	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}



	public String getTxType() {
		return txType;
	}



	public void setTxType(String txType) {
		this.txType = txType;
	}



	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}



}

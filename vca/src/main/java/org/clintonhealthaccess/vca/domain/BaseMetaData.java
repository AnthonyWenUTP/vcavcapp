package org.clintonhealthaccess.vca.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BaseMetaData es la clase que almacena la información de referencia acerca de los objetos.
 * 
 * BaseMetaData incluye información como:
 * 
 * <ul>
 * <li>Fecha del registro
 * <li>Usuario que registra
 * <li>Estado del registro
 * <li>Identificador del dispositivo
 * <li>Si el registro fué borrado
 * </ul>
 * 
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@MappedSuperclass 
public class BaseMetaData implements Serializable 
{  


	private static final long serialVersionUID = 1L;
	private Date recordDate;
	private String recordUser;
	private char estado='0';
	private char pasive = '0';
	private String deviceid;
	
	public BaseMetaData() {

	}

	public BaseMetaData(Date recordDate, String recordUser) {
		super();
		this.recordDate = recordDate;
		this.recordUser = recordUser;
	}
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="recordDate")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name="recordUser", length = 50)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	
	@Column(name="pasive", nullable = false, length = 1)
	public char getPasive() {
		return pasive;
	}

	public void setPasive(char pasive) {
		this.pasive = pasive;
	}
	
	@Column(name="status", nullable = false, length = 1)
	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}
	

	@Column(name="deviceid", length = 100)
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	

}  

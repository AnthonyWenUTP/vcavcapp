package org.clintonhealthaccess.vca.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseMetaData implements Serializable 
{  

	/**
	 * 
	 */
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
	
	
	
	public BaseMetaData(Date recordDate, String recordUser, char pasive,
			Integer idInstancia, String instancePath, char estado,
			String start, String end, String deviceid, String simserial,
			String phonenumber, Date today) {
		super();
		this.recordDate = recordDate;
		this.recordUser = recordUser;
		this.pasive = pasive;
		this.estado = estado;
		this.deviceid = deviceid;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	
	public char getPasive() {
		return pasive;
	}

	public void setPasive(char pasive) {
		this.pasive = pasive;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

}  

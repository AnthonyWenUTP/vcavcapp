package org.clintonhealthaccess.vca.users.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "accesses", catalog = "vca")
public class UserAccess {

	private int id;
	private UserSistema usuario;
	private Date loginDate;
	private String sessionId;
	private String remoteIpAddress;
	private Date logoutDate;
	private String logoutRefererUrl;
	
	
	public UserAccess() {
		super();
	}
	
	
	public UserAccess(UserSistema usuario, Date loginDate, String sessionId,
			String remoteIpAddress) {
		super();
		this.usuario = usuario;
		this.loginDate = loginDate;
		this.sessionId = sessionId;
		this.remoteIpAddress = remoteIpAddress;
	}

	@Id
	@GenericGenerator(name="idautoinc2" , strategy="increment")
	@GeneratedValue(generator="idautoinc2")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="username")
	@ForeignKey(name = "userAccess_FK")
	public UserSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UserSistema usuario) {
		this.usuario = usuario;
	}

	@Column(name = "loginDate")
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	@Column(name = "sessionId", nullable = true, length =100)
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Column(name = "remoteIpAddress", nullable = true, length =50)
	public String getRemoteIpAddress() {
		return remoteIpAddress;
	}
	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}
	@Column(name = "logoutDate")
	public Date getLogoutDate() {
		return logoutDate;
	}
	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}
	@Column(name = "logoutRefererUrl", nullable = true, length =500)
	public String getLogoutRefererUrl() {
		return logoutRefererUrl;
	}
	public void setLogoutRefererUrl(String logoutRefererUrl) {
		this.logoutRefererUrl = logoutRefererUrl;
	}
	@Override
	public String toString(){
		return usuario.toString();
	}
}
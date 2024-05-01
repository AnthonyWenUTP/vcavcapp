package org.clintonhealthaccess.vca.users.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "attemps", catalog = "vca")
public class UserAttempts {

	private int id;
	private String username;
	private int attempts;
	private Date lastModified;
	
	public UserAttempts() {
		super();
	}
	public UserAttempts(String username, int attempts, Date lastModified) {
		super();
		this.username = username;
		this.attempts = attempts;
		this.lastModified = lastModified;
	}
	@Id
	@GenericGenerator(name="idautoinc" , strategy="increment")
	@GeneratedValue(generator="idautoinc")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "username", nullable = false, length =50)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "attempts")
	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	@Column(name = "lastModified")
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	@Override
	public String toString(){
		return username;
	}

}
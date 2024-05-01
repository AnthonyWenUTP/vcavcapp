package org.clintonhealthaccess.vca.users.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "passwordresettoken", catalog = "vca")
public class PasswordResetToken {
	
	
	private static final int EXPIRATION = 3600*1000*24; //24 horas
	  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
  
    private String token;
  
    @OneToOne(targetEntity = UserSistema.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "username")
    private UserSistema user;
  
    private Date expiryDate;
    
    

	public PasswordResetToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordResetToken(String token, UserSistema user) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = new Date(new Date().getTime() + EXPIRATION);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserSistema getUser() {
		return user;
	}

	public void setUser(UserSistema user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}

    
    
    
}

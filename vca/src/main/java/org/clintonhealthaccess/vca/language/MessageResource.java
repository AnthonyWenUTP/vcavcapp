package org.clintonhealthaccess.vca.language;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.clintonhealthaccess.vca.domain.audit.Auditable;


/**
 * MessageResource es la clase que representa el registro de catalogos y mensajes en el sistema.
 * 
 * MessageResource incluye información como:
 * 
 * <ul>
 * <li>Clave del mensaje
 * <li>Catalogo al que pertenece
 * <li>Clave del catalogo
 * <li>Valor en español
 * <li>Valor en inglés
 * </ul>
 * <p>
 * 
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "messages", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"catRoot" , "catKey"})})
public class MessageResource implements Serializable, Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String messageKey;
    private String catRoot;
    private String catKey;
    private char pasive = '0';
    private char isCat = '0';
    private int order=0;
    private String spanish;
    private String english;
    
    public MessageResource() {

	}

    @Id
    @Column(name = "messageKey", nullable = false, length = 100)
    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
    
    @Column(name = "catRoot", nullable = true, length = 50)
    public String getCatRoot() {
		return catRoot;
	}

	public void setCatRoot(String catRoot) {
		this.catRoot = catRoot;
	}

	@Column(name = "catKey", nullable = true, length = 50)
    public String getCatKey() {
		return catKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}
	
	@Column(name="isCat", nullable = false, length = 1)
	public char getIsCat() {
		return isCat;
	}

	public void setIsCat(char isCat) {
		this.isCat = isCat;
	}

	@Column(name="catPasive", nullable = false, length = 1)
	public char getPasive() {
		return pasive;
	}

	public void setPasive(char pasive) {
		this.pasive = pasive;
	}
	
	
	@Column(name="orden", nullable = false)
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Column(name = "en", nullable = true, length = 255)
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    
    @Column(name = "es", nullable = true, length = 255)
    public String getSpanish() {
		return spanish;
	}

	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageResource that = (MessageResource) o;

        if (messageKey != null ? !messageKey.equals(that.messageKey) : that.messageKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return messageKey != null ? messageKey.hashCode() : 0;
    }
    
	@Override
	public boolean isFieldAuditable(String fieldname) {
		return false;
	}

	

}

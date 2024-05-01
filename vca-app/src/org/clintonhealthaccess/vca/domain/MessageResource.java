package org.clintonhealthaccess.vca.domain;

import java.io.Serializable;


public class MessageResource implements Serializable {

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

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
    
    public String getCatRoot() {
		return catRoot;
	}

	public void setCatRoot(String catRoot) {
		this.catRoot = catRoot;
	}

	public String getCatKey() {
		return catKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}
	
	public char getIsCat() {
		return isCat;
	}

	public void setIsCat(char isCat) {
		this.isCat = isCat;
	}

	public char getPasive() {
		return pasive;
	}

	public void setPasive(char pasive) {
		this.pasive = pasive;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

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
    
}

package org.clintonhealthaccess.vca.movil.controller;

import java.io.Serializable;
import java.util.List;

import org.clintonhealthaccess.vca.language.MessageResource;

public class CatalogosMapeo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<MessageResource> catalogos;

	
	public CatalogosMapeo() {
		super();
	}





	public CatalogosMapeo(List<MessageResource> catalogos) {
		super();
		this.catalogos = catalogos;

	}


	public List<MessageResource> getCatalogos() {
		return catalogos;
	}


	public void setCatalogos(List<MessageResource> catalogos) {
		this.catalogos = catalogos;
	}

}

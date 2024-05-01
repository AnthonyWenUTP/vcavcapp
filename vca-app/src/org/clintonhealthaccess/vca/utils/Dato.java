package org.clintonhealthaccess.vca.utils;

public class Dato {
	
	private String campo;
	private String valor;
	
	
	
	public Dato(String campo, String valor) {
		super();
		this.campo = campo;
		this.valor = valor;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}

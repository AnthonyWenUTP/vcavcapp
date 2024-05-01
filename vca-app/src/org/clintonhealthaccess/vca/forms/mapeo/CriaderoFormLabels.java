package org.clintonhealthaccess.vca.forms.mapeo;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class CriaderoFormLabels {
	
	protected String introCriaderoMessage;
	protected String introCriaderoMessageHint;
	
	protected String localidad;
	protected String localidadHint;	
	
	protected String especie;
	protected String especieHint;
	
	protected String tipoC;
	protected String tipoCHint;
	
	protected String size;
	protected String sizeHint;
	
	protected String infoC;
	protected String infoCHint;
	
	
	
	public CriaderoFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introCriaderoMessage = res.getString(R.string.introCriaderoMessage);
		introCriaderoMessageHint = res.getString(R.string.introCriaderoMessageHint);
		
		localidad = res.getString(R.string.localidadF);
		localidadHint = res.getString(R.string.localidadFHint);
		
		especie = res.getString(R.string.especie);
		especieHint = res.getString(R.string.especieHint);
		
		tipoC = res.getString(R.string.tipoC);
		tipoCHint = res.getString(R.string.tipoCHint);
		
		size = res.getString(R.string.size);
		sizeHint = res.getString(R.string.sizeHint);
		
		infoC = res.getString(R.string.infoC);
		infoCHint = res.getString(R.string.infoCHint);


		
	}



	public String getIntroCriaderoMessage() {
		return introCriaderoMessage;
	}



	public void setIntroCriaderoMessage(String introCriaderoMessage) {
		this.introCriaderoMessage = introCriaderoMessage;
	}



	public String getIntroCriaderoMessageHint() {
		return introCriaderoMessageHint;
	}



	public void setIntroCriaderoMessageHint(String introCriaderoMessageHint) {
		this.introCriaderoMessageHint = introCriaderoMessageHint;
	}



	public String getEspecie() {
		return especie;
	}



	public void setEspecie(String especie) {
		this.especie = especie;
	}



	public String getEspecieHint() {
		return especieHint;
	}



	public void setEspecieHint(String especieHint) {
		this.especieHint = especieHint;
	}



	public String getTipoC() {
		return tipoC;
	}



	public void setTipoC(String tipoC) {
		this.tipoC = tipoC;
	}



	public String getTipoCHint() {
		return tipoCHint;
	}



	public void setTipoCHint(String tipoCHint) {
		this.tipoCHint = tipoCHint;
	}



	public String getSize() {
		return size;
	}



	public void setSize(String size) {
		this.size = size;
	}



	public String getSizeHint() {
		return sizeHint;
	}



	public void setSizeHint(String sizeHint) {
		this.sizeHint = sizeHint;
	}



	public String getInfoC() {
		return infoC;
	}



	public void setInfoC(String infoC) {
		this.infoC = infoC;
	}



	public String getInfoCHint() {
		return infoCHint;
	}



	public void setInfoCHint(String infoCHint) {
		this.infoCHint = infoCHint;
	}



	public String getLocalidad() {
		return localidad;
	}



	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}



	public String getLocalidadHint() {
		return localidadHint;
	}



	public void setLocalidadHint(String localidadHint) {
		this.localidadHint = localidadHint;
	}



	
}

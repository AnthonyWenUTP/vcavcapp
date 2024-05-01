package org.clintonhealthaccess.vca.forms.mapeo;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class PuntoDxFormLabels {
	
	protected String introPointMessage;
	protected String introPointMessageHint;

	protected String localidad;
	protected String localidadHint;	
	
	protected String clave;
	protected String claveHint;
	
	protected String tipo;
	protected String tipoHint;
	
	protected String status;
	protected String statusHint;
	
	protected String info;
	protected String infoHint;
	
	
	
	public PuntoDxFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introPointMessage = res.getString(R.string.introPointMessage);
		introPointMessageHint = res.getString(R.string.introPointMessageHint);

		localidad = res.getString(R.string.localidadF);
		localidadHint = res.getString(R.string.localidadFHint);
		
		clave = res.getString(R.string.clave);
		claveHint = res.getString(R.string.claveHint);
		
		tipo = res.getString(R.string.tipo);
		tipoHint = res.getString(R.string.tipoHint);
		
		status = res.getString(R.string.status);
		statusHint = res.getString(R.string.statusHint);
		
		info = res.getString(R.string.info);
		infoHint = res.getString(R.string.infoHint);


		
	}



	public String getIntroPointMessage() {
		return introPointMessage;
	}



	public void setIntroPointMessage(String introPointMessage) {
		this.introPointMessage = introPointMessage;
	}



	public String getIntroPointMessageHint() {
		return introPointMessageHint;
	}



	public void setIntroPointMessageHint(String introPointMessageHint) {
		this.introPointMessageHint = introPointMessageHint;
	}



	public String getClave() {
		return clave;
	}



	public void setClave(String clave) {
		this.clave = clave;
	}



	public String getClaveHint() {
		return claveHint;
	}



	public void setClaveHint(String claveHint) {
		this.claveHint = claveHint;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getTipoHint() {
		return tipoHint;
	}



	public void setTipoHint(String tipoHint) {
		this.tipoHint = tipoHint;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getStatusHint() {
		return statusHint;
	}



	public void setStatusHint(String statusHint) {
		this.statusHint = statusHint;
	}



	public String getInfo() {
		return info;
	}



	public void setInfo(String info) {
		this.info = info;
	}



	public String getInfoHint() {
		return infoHint;
	}



	public void setInfoHint(String infoHint) {
		this.infoHint = infoHint;
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

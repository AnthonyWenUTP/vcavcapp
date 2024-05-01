package org.clintonhealthaccess.vca.forms.mapeo;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class MuestraFormLabels {
	
	protected String introTestMessage;
	protected String introTestMessageHint;
	
	protected String localidad;
	protected String localidadHint;
	
	protected String mxDate;
	protected String mxDateHint;
	
	protected String casa;
	protected String casaHint;
	
	protected String mxProactiva;
	protected String mxProactivaHint;
	
	protected String mxReactiva;
	protected String mxReactivaHint;
	
	
	
	public MuestraFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introTestMessage = res.getString(R.string.introTestMessage);
		introTestMessageHint = res.getString(R.string.introTestMessageHint);
		
		localidad = res.getString(R.string.localidadF);
		localidadHint = res.getString(R.string.localidadFHint);
		
		mxDate = res.getString(R.string.mxDate);
		mxDateHint = res.getString(R.string.mxDateHint);
		
		casa = res.getString(R.string.codCasa);
		casaHint = res.getString(R.string.codCasaHint);
		
		mxProactiva = res.getString(R.string.mxProactiva);
		mxProactivaHint = res.getString(R.string.mxProactivaHint);
		
		mxReactiva = res.getString(R.string.mxReactiva);
		mxReactivaHint = res.getString(R.string.mxReactivaHint);


		
	}



	public String getIntroTestMessage() {
		return introTestMessage;
	}



	public void setIntroTestMessage(String introTestMessage) {
		this.introTestMessage = introTestMessage;
	}



	public String getIntroTestMessageHint() {
		return introTestMessageHint;
	}



	public void setIntroTestMessageHint(String introTestMessageHint) {
		this.introTestMessageHint = introTestMessageHint;
	}



	public String getMxDate() {
		return mxDate;
	}



	public void setMxDate(String mxDate) {
		this.mxDate = mxDate;
	}



	public String getMxDateHint() {
		return mxDateHint;
	}



	public void setMxDateHint(String mxDateHint) {
		this.mxDateHint = mxDateHint;
	}



	public String getCasa() {
		return casa;
	}



	public void setCasa(String casa) {
		this.casa = casa;
	}



	public String getCasaHint() {
		return casaHint;
	}



	public void setCasaHint(String casaHint) {
		this.casaHint = casaHint;
	}



	public String getMxProactiva() {
		return mxProactiva;
	}



	public void setMxProactiva(String mxProactiva) {
		this.mxProactiva = mxProactiva;
	}



	public String getMxProactivaHint() {
		return mxProactivaHint;
	}



	public void setMxProactivaHint(String mxProactivaHint) {
		this.mxProactivaHint = mxProactivaHint;
	}



	public String getMxReactiva() {
		return mxReactiva;
	}



	public void setMxReactiva(String mxReactiva) {
		this.mxReactiva = mxReactiva;
	}



	public String getMxReactivaHint() {
		return mxReactivaHint;
	}



	public void setMxReactivaHint(String mxReactivaHint) {
		this.mxReactivaHint = mxReactivaHint;
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

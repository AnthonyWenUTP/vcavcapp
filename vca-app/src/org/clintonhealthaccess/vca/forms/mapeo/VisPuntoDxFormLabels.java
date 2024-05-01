package org.clintonhealthaccess.vca.forms.mapeo;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class VisPuntoDxFormLabels {
	
	protected String introVisPointMessage;
	protected String introVisPointMessageHint;
	
	
	protected String visitDate;
	protected String visitDateHint;
	
	protected String visitType;
	protected String visitTypeHint;
	
	protected String obs;
	protected String obsHint;
	
	public VisPuntoDxFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introVisPointMessage = res.getString(R.string.introVisPointMessage);
		introVisPointMessageHint = res.getString(R.string.introVisPointMessageHint);

		
		visitDate = res.getString(R.string.visitDate);
		visitDateHint = res.getString(R.string.visitDateHint);
		
		visitType = res.getString(R.string.visitType);
		visitTypeHint = res.getString(R.string.visitTypeHint);
		
		obs = res.getString(R.string.obs);
		obsHint = res.getString(R.string.obsHint);
		
	}

	public String getIntroVisPointMessage() {
		return introVisPointMessage;
	}

	public void setIntroVisPointMessage(String introVisPointMessage) {
		this.introVisPointMessage = introVisPointMessage;
	}

	public String getIntroVisPointMessageHint() {
		return introVisPointMessageHint;
	}

	public void setIntroVisPointMessageHint(String introVisPointMessageHint) {
		this.introVisPointMessageHint = introVisPointMessageHint;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getVisitDateHint() {
		return visitDateHint;
	}

	public void setVisitDateHint(String visitDateHint) {
		this.visitDateHint = visitDateHint;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getVisitTypeHint() {
		return visitTypeHint;
	}

	public void setVisitTypeHint(String visitTypeHint) {
		this.visitTypeHint = visitTypeHint;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getObsHint() {
		return obsHint;
	}

	public void setObsHint(String obsHint) {
		this.obsHint = obsHint;
	}


}

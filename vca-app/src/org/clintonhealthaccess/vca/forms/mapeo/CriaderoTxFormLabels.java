package org.clintonhealthaccess.vca.forms.mapeo;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class CriaderoTxFormLabels {
	
	protected String introTxCriaderoMessage;
	protected String introTxCriaderoMessageHint;

	protected String txDate;
	protected String txDateHint;
	
	protected String txType;
	protected String txTypeHint;
	
	protected String obsT;
	protected String obsTHint;
	
	public CriaderoTxFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introTxCriaderoMessage = res.getString(R.string.introTxCriaderoMessage);
		introTxCriaderoMessageHint = res.getString(R.string.introTxCriaderoMessageHint);
		
		txDate = res.getString(R.string.txDate);
		txDateHint = res.getString(R.string.txDateHint);
		
		txType = res.getString(R.string.txType);
		txTypeHint = res.getString(R.string.txTypeHint);
		
		obsT = res.getString(R.string.obsT);
		obsTHint = res.getString(R.string.obsTHint);
		
	}

	public String getIntroTxCriaderoMessage() {
		return introTxCriaderoMessage;
	}

	public void setIntroTxCriaderoMessage(String introTxCriaderoMessage) {
		this.introTxCriaderoMessage = introTxCriaderoMessage;
	}

	public String getIntroTxCriaderoMessageHint() {
		return introTxCriaderoMessageHint;
	}

	public void setIntroTxCriaderoMessageHint(String introTxCriaderoMessageHint) {
		this.introTxCriaderoMessageHint = introTxCriaderoMessageHint;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getTxDateHint() {
		return txDateHint;
	}

	public void setTxDateHint(String txDateHint) {
		this.txDateHint = txDateHint;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getTxTypeHint() {
		return txTypeHint;
	}

	public void setTxTypeHint(String txTypeHint) {
		this.txTypeHint = txTypeHint;
	}

	public String getObsT() {
		return obsT;
	}

	public void setObsT(String obsT) {
		this.obsT = obsT;
	}

	public String getObsTHint() {
		return obsTHint;
	}

	public void setObsTHint(String obsTHint) {
		this.obsTHint = obsTHint;
	}
	

}

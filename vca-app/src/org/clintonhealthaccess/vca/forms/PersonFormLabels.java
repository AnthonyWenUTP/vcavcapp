package org.clintonhealthaccess.vca.forms;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class PersonFormLabels {
	
	protected String introMessagePerson;
	protected String introMessagePersonHint;
	protected String codePerson;
	protected String codePersonHint;
	protected String namePerson;
	protected String namePersonHint;
	protected String sexPerson;
	protected String sexPersonHint;
	protected String agePerson;
	protected String agePersonHint;
	protected String pregPerson;
	protected String pregPersonHint;
	protected String obsPerson;
	protected String obsPersonHint;
	
	
	
	public PersonFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introMessagePerson = res.getString(R.string.introMessagePerson);
		introMessagePersonHint = res.getString(R.string.introMessagePersonHint);
		codePerson = res.getString(R.string.codePerson);
		codePersonHint = res.getString(R.string.codePersonHint);
		namePerson = res.getString(R.string.namePerson);
		namePersonHint = res.getString(R.string.namePersonHint);
		sexPerson = res.getString(R.string.sexPerson);
		sexPersonHint = res.getString(R.string.sexPersonHint);
		agePerson = res.getString(R.string.agePerson);
		agePersonHint = res.getString(R.string.agePersonHint);
		pregPerson = res.getString(R.string.pregPerson);
		pregPersonHint = res.getString(R.string.pregPersonHint);
		obsPerson = res.getString(R.string.obsPerson);
		obsPersonHint = res.getString(R.string.obsPersonHint);
	}



	public String getIntroMessagePerson() {
		return introMessagePerson;
	}



	public void setIntroMessagePerson(String introMessagePerson) {
		this.introMessagePerson = introMessagePerson;
	}



	public String getIntroMessagePersonHint() {
		return introMessagePersonHint;
	}



	public void setIntroMessagePersonHint(String introMessagePersonHint) {
		this.introMessagePersonHint = introMessagePersonHint;
	}



	public String getCodePerson() {
		return codePerson;
	}



	public void setCodePerson(String codePerson) {
		this.codePerson = codePerson;
	}



	public String getCodePersonHint() {
		return codePersonHint;
	}



	public void setCodePersonHint(String codePersonHint) {
		this.codePersonHint = codePersonHint;
	}



	public String getNamePerson() {
		return namePerson;
	}



	public void setNamePerson(String namePerson) {
		this.namePerson = namePerson;
	}



	public String getNamePersonHint() {
		return namePersonHint;
	}



	public void setNamePersonHint(String namePersonHint) {
		this.namePersonHint = namePersonHint;
	}



	public String getSexPerson() {
		return sexPerson;
	}



	public void setSexPerson(String sexPerson) {
		this.sexPerson = sexPerson;
	}



	public String getSexPersonHint() {
		return sexPersonHint;
	}



	public void setSexPersonHint(String sexPersonHint) {
		this.sexPersonHint = sexPersonHint;
	}



	public String getAgePerson() {
		return agePerson;
	}



	public void setAgePerson(String agePerson) {
		this.agePerson = agePerson;
	}



	public String getAgePersonHint() {
		return agePersonHint;
	}



	public void setAgePersonHint(String agePersonHint) {
		this.agePersonHint = agePersonHint;
	}



	public String getPregPerson() {
		return pregPerson;
	}



	public void setPregPerson(String pregPerson) {
		this.pregPerson = pregPerson;
	}



	public String getPregPersonHint() {
		return pregPersonHint;
	}



	public void setPregPersonHint(String pregPersonHint) {
		this.pregPersonHint = pregPersonHint;
	}



	public String getObsPerson() {
		return obsPerson;
	}



	public void setObsPerson(String obsPerson) {
		this.obsPerson = obsPerson;
	}



	public String getObsPersonHint() {
		return obsPersonHint;
	}



	public void setObsPersonHint(String obsPersonHint) {
		this.obsPersonHint = obsPersonHint;
	}
	
	
	
	
}

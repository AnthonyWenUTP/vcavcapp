package org.clintonhealthaccess.vca.forms.mapeo;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class CaseFormLabels {
	
	protected String introCaseMessage;
	protected String introCaseMessageHint;
	
	protected String codeCase;
	protected String codeCaseHint;
	
	protected String localidad;
	protected String localidadHint;
	
	protected String sintCase;
	protected String sintCaseHint;
	
	protected String fisDate;
	protected String fisDateHint;
	
	protected String mxDate;
	protected String mxDateHint;
	
	protected String mxType;
	protected String mxTypeHint;
	
	protected String cuiCase;
	protected String cuiCaseHint;
	
	protected String codE1;
	protected String codE1Hint;
	
	protected String codCasa;
	protected String codCasaHint;
	
	protected String nameCase;
	protected String nameCaseHint;
	
	protected String infoCase;
	protected String infoCaseHint;
	
	protected String sexo;
	protected String sexoHint;
	
	protected String edad;
	protected String edadHint;
	
	protected String embarazada;
	protected String embarazadaHint;
	
	protected String menor6meses;
	protected String menor6mesesHint;
	
	
	public CaseFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introCaseMessage = res.getString(R.string.introCaseMessage);
		introCaseMessageHint = res.getString(R.string.introCaseMessageHint);
		codeCase = res.getString(R.string.codeCase);
		codeCaseHint = res.getString(R.string.codeCaseHint);
		localidad = res.getString(R.string.localidadF);
		localidadHint = res.getString(R.string.localidadFHint);
		
		sintCase = res.getString(R.string.sintCase);
		sintCaseHint = res.getString(R.string.sintCaseHint);
		fisDate = res.getString(R.string.fisDate);
		fisDateHint = res.getString(R.string.fisDateHint);
		mxDate = res.getString(R.string.mxDate);
		mxDateHint = res.getString(R.string.mxDateHint);
		mxDate = res.getString(R.string.mxDate);
		mxDateHint = res.getString(R.string.mxDateHint);
		mxType = res.getString(R.string.mxType);
		mxTypeHint = res.getString(R.string.mxTypeHint);
		cuiCase = res.getString(R.string.cuiCase);
		cuiCaseHint = res.getString(R.string.cuiCaseHint);
		codE1 = res.getString(R.string.codE1);
		codE1Hint = res.getString(R.string.codE1Hint);
		codCasa = res.getString(R.string.codCasa);
		codCasaHint = res.getString(R.string.codCasaHint);
		nameCase = res.getString(R.string.nameCase);
		nameCaseHint = res.getString(R.string.nameCaseHint);
		infoCase = res.getString(R.string.infoCase);
		infoCaseHint = res.getString(R.string.infoCaseHint);
		
		sexo = res.getString(R.string.sexo);
		sexoHint = res.getString(R.string.sexoHint);
		
		edad = res.getString(R.string.edad);
		edadHint = res.getString(R.string.edadHint);
		
		embarazada = res.getString(R.string.embarazada);
		embarazadaHint = res.getString(R.string.embarazadaHint);
		
		menor6meses = res.getString(R.string.menor6meses);
		menor6mesesHint = res.getString(R.string.menor6mesesHint);
	}


	public String getIntroCaseMessage() {
		return introCaseMessage;
	}


	public void setIntroCaseMessage(String introCaseMessage) {
		this.introCaseMessage = introCaseMessage;
	}


	public String getIntroCaseMessageHint() {
		return introCaseMessageHint;
	}


	public void setIntroCaseMessageHint(String introCaseMessageHint) {
		this.introCaseMessageHint = introCaseMessageHint;
	}


	public String getCodeCase() {
		return codeCase;
	}


	public void setCodeCase(String codeCase) {
		this.codeCase = codeCase;
	}


	public String getCodeCaseHint() {
		return codeCaseHint;
	}


	public void setCodeCaseHint(String codeCaseHint) {
		this.codeCaseHint = codeCaseHint;
	}


	public String getFisDate() {
		return fisDate;
	}


	public void setFisDate(String fisDate) {
		this.fisDate = fisDate;
	}


	public String getFisDateHint() {
		return fisDateHint;
	}


	public void setFisDateHint(String fisDateHint) {
		this.fisDateHint = fisDateHint;
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


	public String getMxType() {
		return mxType;
	}


	public void setMxType(String mxType) {
		this.mxType = mxType;
	}


	public String getMxTypeHint() {
		return mxTypeHint;
	}


	public void setMxTypeHint(String mxTypeHint) {
		this.mxTypeHint = mxTypeHint;
	}


	public String getCuiCase() {
		return cuiCase;
	}


	public void setCuiCase(String cuiCase) {
		this.cuiCase = cuiCase;
	}


	public String getCuiCaseHint() {
		return cuiCaseHint;
	}


	public void setCuiCaseHint(String cuiCaseHint) {
		this.cuiCaseHint = cuiCaseHint;
	}


	public String getCodE1() {
		return codE1;
	}


	public void setCodE1(String codE1) {
		this.codE1 = codE1;
	}


	public String getCodE1Hint() {
		return codE1Hint;
	}


	public void setCodE1Hint(String codE1Hint) {
		this.codE1Hint = codE1Hint;
	}


	public String getCodCasa() {
		return codCasa;
	}


	public void setCodCasa(String codCasa) {
		this.codCasa = codCasa;
	}


	public String getCodCasaHint() {
		return codCasaHint;
	}


	public void setCodCasaHint(String codCasaHint) {
		this.codCasaHint = codCasaHint;
	}


	public String getNameCase() {
		return nameCase;
	}


	public void setNameCase(String nameCase) {
		this.nameCase = nameCase;
	}


	public String getNameCaseHint() {
		return nameCaseHint;
	}


	public void setNameCaseHint(String nameCaseHint) {
		this.nameCaseHint = nameCaseHint;
	}


	public String getInfoCase() {
		return infoCase;
	}


	public void setInfoCase(String infoCase) {
		this.infoCase = infoCase;
	}


	public String getInfoCaseHint() {
		return infoCaseHint;
	}


	public void setInfoCaseHint(String infoCaseHint) {
		this.infoCaseHint = infoCaseHint;
	}


	public String getSintCase() {
		return sintCase;
	}


	public void setSintCase(String sintCase) {
		this.sintCase = sintCase;
	}


	public String getSintCaseHint() {
		return sintCaseHint;
	}


	public void setSintCaseHint(String sintCaseHint) {
		this.sintCaseHint = sintCaseHint;
	}


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public String getSexoHint() {
		return sexoHint;
	}


	public void setSexoHint(String sexoHint) {
		this.sexoHint = sexoHint;
	}


	public String getEdad() {
		return edad;
	}


	public void setEdad(String edad) {
		this.edad = edad;
	}


	public String getEdadHint() {
		return edadHint;
	}


	public void setEdadHint(String edadHint) {
		this.edadHint = edadHint;
	}


	public String getEmbarazada() {
		return embarazada;
	}


	public void setEmbarazada(String embarazada) {
		this.embarazada = embarazada;
	}


	public String getEmbarazadaHint() {
		return embarazadaHint;
	}


	public void setEmbarazadaHint(String embarazadaHint) {
		this.embarazadaHint = embarazadaHint;
	}


	public String getMenor6meses() {
		return menor6meses;
	}


	public void setMenor6meses(String menor6meses) {
		this.menor6meses = menor6meses;
	}


	public String getMenor6mesesHint() {
		return menor6mesesHint;
	}


	public void setMenor6mesesHint(String menor6mesesHint) {
		this.menor6mesesHint = menor6mesesHint;
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

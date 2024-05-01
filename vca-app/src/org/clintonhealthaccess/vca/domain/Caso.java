package org.clintonhealthaccess.vca.domain;

import java.util.Date;




/**
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Caso extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Localidad local;
	private String codigo;
	private String cui;
	private String codE1;
	private String casa;
	private String nombre;
	private String sexo;
	private Integer edad;
	private String embarazada;
	private String menor6meses;
	private String sint="1";
	private Date fisDate;
	private Date mxDate;
	private String mxType;
	private String inv="0";
	private Date invDate;
	private Date invCompDate;
	private String tx="0";
	private String txResultType;
	private String txSup="No";
	private Date txDate;
	private String txComp="0";
	private Date txCompDate;
	private String sx="0";
	private String txSusp="0";
	private Date txSuspDate;
	private String txSuspReason;
	private String txSuspOtherReason;
	private Date sxDate;
	private String sxResult;
	private String sxComp="0";
	private Date sxCompDate;
	private String sxCompResult;
	private String lostFollowUp="0";
	private String lostFollowUpReason;
	private String lostFollowUpOtherReason;
	private String estadocaso="CONF";
	private String info;
	private Double latitude;
	private Double longitude;
	private Float exactitud;
	private Double altitud;
	private Integer zoom=10;
	
	
	private Date dayTx01;
	private Date dayTx02;
	private Date dayTx03;
	private Date dayTx04;
	private Date dayTx05;
	private Date dayTx06;
	private Date dayTx07;
	private Date dayTx08;
	private Date dayTx09;
	private Date dayTx10;
	private Date dayTx11;
	private Date dayTx12;
	private Date dayTx13;
	private Date dayTx14;
	
	private Double latitudeOrigin;
	private Double longitudeOrigin;
	private Integer zoomOrigin=10;
	
	
	public Caso() {
		super();
	}



	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}


	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}


	public String getCodigo() {
		return codigo;
	}


	public String getCodE1() {
		return codE1;
	}



	public void setCodE1(String codE1) {
		this.codE1 = codE1;
	}



	public String getCui() {
		return cui;
	}



	public void setCui(String cui) {
		this.cui = cui;
	}


	public String getCasa() {
		return casa;
	}



	public void setCasa(String casa) {
		this.casa = casa;
	}


	
	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	

	public String getSint() {
		return sint;
	}



	public void setSint(String sint) {
		this.sint = sint;
	}



	public Date getFisDate() {
		return fisDate;
	}



	public void setFisDate(Date fisDate) {
		this.fisDate = fisDate;
	}


	public Date getMxDate() {
		return mxDate;
	}



	public void setMxDate(Date mxDate) {
		this.mxDate = mxDate;
	}

	
	

	public String getMxType() {
		return mxType;
	}



	public void setMxType(String mxType) {
		this.mxType = mxType;
	}


	public String getInv() {
		return inv;
	}



	public void setInv(String inv) {
		this.inv = inv;
	}



	public Date getInvDate() {
		return invDate;
	}



	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}


	public String getTx() {
		return tx;
	}



	public void setTx(String tx) {
		this.tx = tx;
	}


	public String getTxSup() {
		return txSup;
	}



	public void setTxSup(String txSup) {
		this.txSup = txSup;
	}



	public Date getTxDate() {
		return txDate;
	}



	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}



	public String getTxComp() {
		return txComp;
	}



	public void setTxComp(String txComp) {
		this.txComp = txComp;
	}


	public Date getTxCompDate() {
		return txCompDate;
	}



	public void setTxCompDate(Date txCompDate) {
		this.txCompDate = txCompDate;
	}



	public String getSx() {
		return sx;
	}



	public void setSx(String sx) {
		this.sx = sx;
	}



	public Date getSxDate() {
		return sxDate;
	}



	public void setSxDate(Date sxDate) {
		this.sxDate = sxDate;
	}



	public String getLostFollowUp() {
		return lostFollowUp;
	}



	public void setLostFollowUp(String lostFollowUp) {
		this.lostFollowUp = lostFollowUp;
	}


	public Date getSxCompDate() {
		return sxCompDate;
	}



	public void setSxCompDate(Date sxCompDate) {
		this.sxCompDate = sxCompDate;
	}


	public String getSxComp() {
		return sxComp;
	}



	public void setSxComp(String sxComp) {
		this.sxComp = sxComp;
	}
	

	public String getLostFollowUpReason() {
		return lostFollowUpReason;
	}



	public void setLostFollowUpReason(String lostFollowUpReason) {
		this.lostFollowUpReason = lostFollowUpReason;
	}



	public String getInfo() {
		return info;
	}



	public void setInfo(String info) {
		this.info = info;
	}
	
	
	
	public String getEstadocaso() {
		return estadocaso;
	}



	public void setEstadocaso(String estadocaso) {
		this.estadocaso = estadocaso;
	}



	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	
	public Integer getZoom() {
		return zoom;
	}



	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	
	
	public String getSxResult() {
		return sxResult;
	}



	public void setSxResult(String sxResult) {
		this.sxResult = sxResult;
	}


	public String getSxCompResult() {
		return sxCompResult;
	}



	public void setSxCompResult(String sxCompResult) {
		this.sxCompResult = sxCompResult;
	}
	
	

	
	public Float getExactitud() {
		return exactitud;
	}



	public void setExactitud(Float exactitud) {
		this.exactitud = exactitud;
	}



	public Double getAltitud() {
		return altitud;
	}



	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}
	
	
	



	public String getSexo() {
		return sexo;
	}



	public void setSexo(String sexo) {
		this.sexo = sexo;
	}



	public Integer getEdad() {
		return edad;
	}



	public void setEdad(Integer edad) {
		this.edad = edad;
	}



	public String getEmbarazada() {
		return embarazada;
	}



	public void setEmbarazada(String embarazada) {
		this.embarazada = embarazada;
	}



	public String getMenor6meses() {
		return menor6meses;
	}



	public void setMenor6meses(String menor6meses) {
		this.menor6meses = menor6meses;
	}



	public Date getDayTx01() {
		return dayTx01;
	}



	public void setDayTx01(Date dayTx01) {
		this.dayTx01 = dayTx01;
	}



	public Date getDayTx02() {
		return dayTx02;
	}



	public void setDayTx02(Date dayTx02) {
		this.dayTx02 = dayTx02;
	}



	public Date getDayTx03() {
		return dayTx03;
	}



	public void setDayTx03(Date dayTx03) {
		this.dayTx03 = dayTx03;
	}



	public Date getDayTx04() {
		return dayTx04;
	}



	public void setDayTx04(Date dayTx04) {
		this.dayTx04 = dayTx04;
	}



	public Date getDayTx05() {
		return dayTx05;
	}



	public void setDayTx05(Date dayTx05) {
		this.dayTx05 = dayTx05;
	}



	public Date getDayTx06() {
		return dayTx06;
	}



	public void setDayTx06(Date dayTx06) {
		this.dayTx06 = dayTx06;
	}



	public Date getDayTx07() {
		return dayTx07;
	}



	public void setDayTx07(Date dayTx07) {
		this.dayTx07 = dayTx07;
	}



	public Date getDayTx08() {
		return dayTx08;
	}



	public void setDayTx08(Date dayTx08) {
		this.dayTx08 = dayTx08;
	}



	public Date getDayTx09() {
		return dayTx09;
	}



	public void setDayTx09(Date dayTx09) {
		this.dayTx09 = dayTx09;
	}



	public Date getDayTx10() {
		return dayTx10;
	}



	public void setDayTx10(Date dayTx10) {
		this.dayTx10 = dayTx10;
	}



	public Date getDayTx11() {
		return dayTx11;
	}



	public void setDayTx11(Date dayTx11) {
		this.dayTx11 = dayTx11;
	}



	public Date getDayTx12() {
		return dayTx12;
	}



	public void setDayTx12(Date dayTx12) {
		this.dayTx12 = dayTx12;
	}



	public Date getDayTx13() {
		return dayTx13;
	}



	public void setDayTx13(Date dayTx13) {
		this.dayTx13 = dayTx13;
	}



	public Date getDayTx14() {
		return dayTx14;
	}



	public void setDayTx14(Date dayTx14) {
		this.dayTx14 = dayTx14;
	}



	public Double getLatitudeOrigin() {
		return latitudeOrigin;
	}



	public void setLatitudeOrigin(Double latitudeOrigin) {
		this.latitudeOrigin = latitudeOrigin;
	}



	public Double getLongitudeOrigin() {
		return longitudeOrigin;
	}



	public void setLongitudeOrigin(Double longitudeOrigin) {
		this.longitudeOrigin = longitudeOrigin;
	}



	public Integer getZoomOrigin() {
		return zoomOrigin;
	}



	public void setZoomOrigin(Integer zoomOrigin) {
		this.zoomOrigin = zoomOrigin;
	}
	
	



	public Date getInvCompDate() {
		return invCompDate;
	}



	public void setInvCompDate(Date invCompDate) {
		this.invCompDate = invCompDate;
	}



	public String getTxResultType() {
		return txResultType;
	}



	public void setTxResultType(String txResultType) {
		this.txResultType = txResultType;
	}



	public String getTxSusp() {
		return txSusp;
	}



	public void setTxSusp(String txSusp) {
		this.txSusp = txSusp;
	}



	public Date getTxSuspDate() {
		return txSuspDate;
	}



	public void setTxSuspDate(Date txSuspDate) {
		this.txSuspDate = txSuspDate;
	}



	public String getTxSuspReason() {
		return txSuspReason;
	}



	public void setTxSuspReason(String txSuspReason) {
		this.txSuspReason = txSuspReason;
	}



	public String getTxSuspOtherReason() {
		return txSuspOtherReason;
	}



	public void setTxSuspOtherReason(String txSuspOtherReason) {
		this.txSuspOtherReason = txSuspOtherReason;
	}



	public String getLostFollowUpOtherReason() {
		return lostFollowUpOtherReason;
	}



	public void setLostFollowUpOtherReason(String lostFollowUpOtherReason) {
		this.lostFollowUpOtherReason = lostFollowUpOtherReason;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString(){
		return this.getCodigo();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Caso))
			return false;
		
		Caso castOther = (Caso) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

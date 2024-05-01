package org.clintonhealthaccess.vca.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "casos", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"codigo","pasive"})})
public class Caso extends BaseMetaData implements Auditable{
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



	@Id
    @Column(name = "id", nullable = false, length = 50)
	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	
	@ManyToOne(optional=false)
	@JoinColumn(name="local")
    @ForeignKey(name = "FK_CASO_LOCALIDAD")
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}

	@Column(name = "codigo", nullable = false, length = 100)
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "codE1", nullable = true, length = 100)
	public String getCodE1() {
		return codE1;
	}



	public void setCodE1(String codE1) {
		this.codE1 = codE1;
	}



	@Column(name = "cui", nullable = true, length = 100)
	public String getCui() {
		return cui;
	}



	public void setCui(String cui) {
		this.cui = cui;
	}


	@Column(name = "casa", nullable = true, length = 100)
	public String getCasa() {
		return casa;
	}



	public void setCasa(String casa) {
		this.casa = casa;
	}


	@Column(name = "nombre", nullable = true, length = 500)
	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	@Column(name = "sint", nullable = false, length = 1)
	public String getSint() {
		return sint;
	}



	public void setSint(String sint) {
		this.sint = sint;
	}



	@Column(name = "fisDate", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getFisDate() {
		return fisDate;
	}



	public void setFisDate(Date fisDate) {
		this.fisDate = fisDate;
	}


	@Column(name = "mxDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getMxDate() {
		return mxDate;
	}



	public void setMxDate(Date mxDate) {
		this.mxDate = mxDate;
	}

	
	
	
	@Column(name = "mxType", nullable = false, length = 50)
	public String getMxType() {
		return mxType;
	}



	public void setMxType(String mxType) {
		this.mxType = mxType;
	}



	@Column(name = "inv", nullable = false, length = 1)
	public String getInv() {
		return inv;
	}



	public void setInv(String inv) {
		this.inv = inv;
	}


	@Column(name = "invDate", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getInvDate() {
		return invDate;
	}



	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}
	
	

	@Column(name = "invCompDate", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getInvCompDate() {
		return invCompDate;
	}



	public void setInvCompDate(Date invCompDate) {
		this.invCompDate = invCompDate;
	}



	@Column(name = "tx", nullable = false, length = 1)
	public String getTx() {
		return tx;
	}



	public void setTx(String tx) {
		this.tx = tx;
	}
	
	

	@Column(name = "txResultType", nullable = true, length = 5)
	public String getTxResultType() {
		return txResultType;
	}



	public void setTxResultType(String txResultType) {
		this.txResultType = txResultType;
	}



	@Column(name = "txSup", nullable = false, length = 100)
	public String getTxSup() {
		return txSup;
	}



	public void setTxSup(String txSup) {
		this.txSup = txSup;
	}


	@Column(name = "txDate", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getTxDate() {
		return txDate;
	}



	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}


	@Column(name = "txComp", nullable = false, length = 1)
	public String getTxComp() {
		return txComp;
	}



	public void setTxComp(String txComp) {
		this.txComp = txComp;
	}


	@Column(name = "txCompDate", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getTxCompDate() {
		return txCompDate;
	}



	public void setTxCompDate(Date txCompDate) {
		this.txCompDate = txCompDate;
	}
	
	
	

	@Column(name = "txSusp", nullable = false, length = 1)
	public String getTxSusp() {
		return txSusp;
	}



	public void setTxSusp(String txSusp) {
		this.txSusp = txSusp;
	}


	@Column(name = "txSuspDate", nullable = true)
	public Date getTxSuspDate() {
		return txSuspDate;
	}



	public void setTxSuspDate(Date txSuspDate) {
		this.txSuspDate = txSuspDate;
	}


	@Column(name = "txSuspReason", nullable = true, length = 50)
	public String getTxSuspReason() {
		return txSuspReason;
	}



	public void setTxSuspReason(String txSuspReason) {
		this.txSuspReason = txSuspReason;
	}


	@Column(name = "txSuspOtherReason", nullable = true, length = 500)
	public String getTxSuspOtherReason() {
		return txSuspOtherReason;
	}



	public void setTxSuspOtherReason(String txSuspOtherReason) {
		this.txSuspOtherReason = txSuspOtherReason;
	}



	@Column(name = "sx", nullable = false, length = 1)
	public String getSx() {
		return sx;
	}



	public void setSx(String sx) {
		this.sx = sx;
	}


	@Column(name = "sxDate", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getSxDate() {
		return sxDate;
	}



	public void setSxDate(Date sxDate) {
		this.sxDate = sxDate;
	}


	@Column(name = "lostFollowUp", nullable = false, length = 1)
	public String getLostFollowUp() {
		return lostFollowUp;
	}



	public void setLostFollowUp(String lostFollowUp) {
		this.lostFollowUp = lostFollowUp;
	}


	@Column(name = "sxCompDate", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getSxCompDate() {
		return sxCompDate;
	}



	public void setSxCompDate(Date sxCompDate) {
		this.sxCompDate = sxCompDate;
	}

	@Column(name = "sxComp", nullable = false, length = 1)
	public String getSxComp() {
		return sxComp;
	}



	public void setSxComp(String sxComp) {
		this.sxComp = sxComp;
	}
	
	@Column(name = "lostFollowUpReason", nullable = true, length = 50)
	public String getLostFollowUpReason() {
		return lostFollowUpReason;
	}



	public void setLostFollowUpReason(String lostFollowUpReason) {
		this.lostFollowUpReason = lostFollowUpReason;
	}
	
	
	

	@Column(name = "lostFollowUpOtherReason", nullable = true, length = 500)
	public String getLostFollowUpOtherReason() {
		return lostFollowUpOtherReason;
	}



	public void setLostFollowUpOtherReason(String lostFollowUpOtherReason) {
		this.lostFollowUpOtherReason = lostFollowUpOtherReason;
	}



	@Column(name = "info", nullable = true, length = 750)
	public String getInfo() {
		return info;
	}



	public void setInfo(String info) {
		this.info = info;
	}
	
	
	
	@Column(name = "estadocaso", nullable = false, length = 50)
	public String getEstadocaso() {
		return estadocaso;
	}



	public void setEstadocaso(String estadocaso) {
		this.estadocaso = estadocaso;
	}



	@Column(name = "latitude", nullable = true)
	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	@Column(name = "longitude", nullable = true)
	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	
	@Column(name = "zoom", nullable = true)
	public Integer getZoom() {
		return zoom;
	}



	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	
	
	
	@Column(name = "latitudeOrigin", nullable = true)
	public Double getLatitudeOrigin() {
		return latitudeOrigin;
	}



	public void setLatitudeOrigin(Double latitudeOrigin) {
		this.latitudeOrigin = latitudeOrigin;
	}


	@Column(name = "longitudeOrigin", nullable = true)
	public Double getLongitudeOrigin() {
		return longitudeOrigin;
	}



	public void setLongitudeOrigin(Double longitudeOrigin) {
		this.longitudeOrigin = longitudeOrigin;
	}


	@Column(name = "zoomOrigin", nullable = true)
	public Integer getZoomOrigin() {
		return zoomOrigin;
	}



	public void setZoomOrigin(Integer zoomOrigin) {
		this.zoomOrigin = zoomOrigin;
	}



	@Column(name = "sxResult", nullable = true, length = 50)
	public String getSxResult() {
		return sxResult;
	}



	public void setSxResult(String sxResult) {
		this.sxResult = sxResult;
	}


	@Column(name = "sxCompResult", nullable = true, length = 50)
	public String getSxCompResult() {
		return sxCompResult;
	}



	public void setSxCompResult(String sxCompResult) {
		this.sxCompResult = sxCompResult;
	}

	
	

	@Column(name = "tx1Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx01() {
		return dayTx01;
	}



	public void setDayTx01(Date dayTx01) {
		this.dayTx01 = dayTx01;
	}


	@Column(name = "tx2Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx02() {
		return dayTx02;
	}



	public void setDayTx02(Date dayTx02) {
		this.dayTx02 = dayTx02;
	}


	@Column(name = "tx3Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx03() {
		return dayTx03;
	}



	public void setDayTx03(Date dayTx03) {
		this.dayTx03 = dayTx03;
	}


	@Column(name = "tx4Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx04() {
		return dayTx04;
	}



	public void setDayTx04(Date dayTx04) {
		this.dayTx04 = dayTx04;
	}


	@Column(name = "tx5Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx05() {
		return dayTx05;
	}



	public void setDayTx05(Date dayTx05) {
		this.dayTx05 = dayTx05;
	}


	@Column(name = "tx6Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx06() {
		return dayTx06;
	}



	public void setDayTx06(Date dayTx06) {
		this.dayTx06 = dayTx06;
	}


	@Column(name = "tx7Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx07() {
		return dayTx07;
	}



	public void setDayTx07(Date dayTx07) {
		this.dayTx07 = dayTx07;
	}


	@Column(name = "tx8Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx08() {
		return dayTx08;
	}



	public void setDayTx08(Date dayTx08) {
		this.dayTx08 = dayTx08;
	}


	@Column(name = "tx9Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx09() {
		return dayTx09;
	}



	public void setDayTx09(Date dayTx09) {
		this.dayTx09 = dayTx09;
	}


	@Column(name = "tx10Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx10() {
		return dayTx10;
	}



	public void setDayTx10(Date dayTx10) {
		this.dayTx10 = dayTx10;
	}


	@Column(name = "tx11Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx11() {
		return dayTx11;
	}



	public void setDayTx11(Date dayTx11) {
		this.dayTx11 = dayTx11;
	}


	@Column(name = "tx12Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx12() {
		return dayTx12;
	}



	public void setDayTx12(Date dayTx12) {
		this.dayTx12 = dayTx12;
	}


	@Column(name = "tx13Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx13() {
		return dayTx13;
	}



	public void setDayTx13(Date dayTx13) {
		this.dayTx13 = dayTx13;
	}


	@Column(name = "tx14Date", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getDayTx14() {
		return dayTx14;
	}



	public void setDayTx14(Date dayTx14) {
		this.dayTx14 = dayTx14;
	}
	
	@Column(name = "sexo", nullable = false, length = 50)
	public String getSexo() {
		return sexo;
	}



	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	@Column(name = "edad", nullable = false)
	public Integer getEdad() {
		return edad;
	}



	public void setEdad(Integer edad) {
		this.edad = edad;
	}


	@Column(name = "embarazada", nullable = true, length = 50)
	public String getEmbarazada() {
		return embarazada;
	}



	public void setEmbarazada(String embarazada) {
		this.embarazada = embarazada;
	}

	@Column(name = "menor6meses", nullable = true, length = 50)
	public String getMenor6meses() {
		return menor6meses;
	}



	public void setMenor6meses(String menor6meses) {
		this.menor6meses = menor6meses;
	}
	
	

	@Column(name = "exactitud", nullable = true)
	public Float getExactitud() {
		return exactitud;
	}



	public void setExactitud(Float exactitud) {
		this.exactitud = exactitud;
	}


	@Column(name = "altitud", nullable = true)
	public Double getAltitud() {
		return altitud;
	}



	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}



	@Override
	public boolean isFieldAuditable(String fieldname) {
		return true;
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

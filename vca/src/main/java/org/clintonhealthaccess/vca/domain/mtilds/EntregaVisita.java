package org.clintonhealthaccess.vca.domain.mtilds;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;




/**
 * 
 * EntregaVisita es la clase que representa la visita de entrega de mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "llinvisits", catalog = "vca")
public class EntregaVisita extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private EntregaTarget target;
	private Date visitDate;
	private String visitor;
	
	private String entrega;

	private Integer mtildEntregadosCama;
	private Integer mtildEntregadosHamaca;
	private Integer mtildEntregadosSuelo;
	private Integer mtildEntregadosOtro;
	
	private String razonNoEntrega;
	private String visitaRecuperacion;


	private String obs;
	

	public EntregaVisita() {
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
	@JoinColumn(name="target")
    @ForeignKey(name = "FK_DEL_TAR2")
	public EntregaTarget getTarget() {
		return target;
	}



	public void setTarget(EntregaTarget target) {
		this.target = target;
	}

	@Column(name = "visitDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getVisitDate() {
		return visitDate;
	}



	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	
	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	
	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	@Column(name = "mtildEntregadosCama", nullable = false)
	public Integer getMtildEntregadosCama() {
		return mtildEntregadosCama;
	}

	public void setMtildEntregadosCama(Integer mtildEntregadosCama) {
		this.mtildEntregadosCama = mtildEntregadosCama;
	}

	@Column(name = "mtildEntregadosHamaca", nullable = false)
	public Integer getMtildEntregadosHamaca() {
		return mtildEntregadosHamaca;
	}

	public void setMtildEntregadosHamaca(Integer mtildEntregadosHamaca) {
		this.mtildEntregadosHamaca = mtildEntregadosHamaca;
	}

	@Column(name = "mtildEntregadosSuelo", nullable = false)
	public Integer getMtildEntregadosSuelo() {
		return mtildEntregadosSuelo;
	}

	public void setMtildEntregadosSuelo(Integer mtildEntregadosSuelo) {
		this.mtildEntregadosSuelo = mtildEntregadosSuelo;
	}

	@Column(name = "mtildEntregadosOtro", nullable = false)
	public Integer getMtildEntregadosOtro() {
		return mtildEntregadosOtro;
	}

	public void setMtildEntregadosOtro(Integer mtildEntregadosOtro) {
		this.mtildEntregadosOtro = mtildEntregadosOtro;
	}

	@Column(name = "razonNoEntrega", nullable = true, length = 50)
	public String getRazonNoEntrega() {
		return razonNoEntrega;
	}

	public void setRazonNoEntrega(String razonNoEntrega) {
		this.razonNoEntrega = razonNoEntrega;
	}

	@Column(name = "visitaRecuperacion", nullable = true, length = 50)
	public String getVisitaRecuperacion() {
		return visitaRecuperacion;
	}

	public void setVisitaRecuperacion(String visitaRecuperacion) {
		this.visitaRecuperacion = visitaRecuperacion;
	}
	
	@Column(name = "obs", nullable = true , length = 500)
	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}


	@Override
	public String toString(){
		return this.getIdent();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EntregaVisita))
			return false;
		
		EntregaVisita castOther = (EntregaVisita) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

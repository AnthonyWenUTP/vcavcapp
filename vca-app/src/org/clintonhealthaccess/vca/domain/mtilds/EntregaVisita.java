package org.clintonhealthaccess.vca.domain.mtilds;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.irs.Personal;




/**
 * 
 * EntregaVisita es la clase que representa la visita de entrega de mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class EntregaVisita extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private EntregaTarget target;
	private Date visitDate;
	private Personal visitor;
	
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

	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	public EntregaTarget getTarget() {
		return target;
	}



	public void setTarget(EntregaTarget target) {
		this.target = target;
	}


	public Date getVisitDate() {
		return visitDate;
	}



	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	

	public Personal getVisitor() {
		return visitor;
	}

	public void setVisitor(Personal visitor) {
		this.visitor = visitor;
	}
	
	

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	public Integer getMtildEntregadosCama() {
		return mtildEntregadosCama;
	}

	public void setMtildEntregadosCama(Integer mtildEntregadosCama) {
		this.mtildEntregadosCama = mtildEntregadosCama;
	}

	public Integer getMtildEntregadosHamaca() {
		return mtildEntregadosHamaca;
	}

	public void setMtildEntregadosHamaca(Integer mtildEntregadosHamaca) {
		this.mtildEntregadosHamaca = mtildEntregadosHamaca;
	}

	public Integer getMtildEntregadosSuelo() {
		return mtildEntregadosSuelo;
	}

	public void setMtildEntregadosSuelo(Integer mtildEntregadosSuelo) {
		this.mtildEntregadosSuelo = mtildEntregadosSuelo;
	}

	public Integer getMtildEntregadosOtro() {
		return mtildEntregadosOtro;
	}

	public void setMtildEntregadosOtro(Integer mtildEntregadosOtro) {
		this.mtildEntregadosOtro = mtildEntregadosOtro;
	}

	public String getRazonNoEntrega() {
		return razonNoEntrega;
	}

	public void setRazonNoEntrega(String razonNoEntrega) {
		this.razonNoEntrega = razonNoEntrega;
	}

	public String getVisitaRecuperacion() {
		return visitaRecuperacion;
	}

	public void setVisitaRecuperacion(String visitaRecuperacion) {
		this.visitaRecuperacion = visitaRecuperacion;
	}

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

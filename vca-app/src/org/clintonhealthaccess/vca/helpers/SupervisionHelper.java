package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class SupervisionHelper {
	
	public static ContentValues crearSupervisionValues(Supervision supervision){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, supervision.getIdent());
		
		cv.put(MainDBConstants.target, supervision.getTarget().getIdent());
		if (supervision.getSupervisionDate() != null) cv.put(MainDBConstants.supervisionDate, supervision.getSupervisionDate().getTime());
		
		cv.put(MainDBConstants.rociador, supervision.getRociador().getIdent());
		cv.put(MainDBConstants.supervisor, supervision.getSupervisor().getIdent());
		cv.put(MainDBConstants.usoEqProt, supervision.getUsoEqProt());
		
		cv.put(MainDBConstants.eqProtBien, supervision.getEqProtBien());
		cv.put(MainDBConstants.numIden, supervision.getNumIden());
		cv.put(MainDBConstants.aguaOp, supervision.getAguaOp());
		cv.put(MainDBConstants.prepViv, supervision.getPrepViv());
		cv.put(MainDBConstants.coopPrepViv, supervision.getCoopPrepViv());
		
		cv.put(MainDBConstants.mezcla, supervision.getMezcla());
		cv.put(MainDBConstants.aguaAdec, supervision.getAguaAdec());
		cv.put(MainDBConstants.mezclaPrep, supervision.getMezclaPrep());
		cv.put(MainDBConstants.agitaBomba, supervision.getAgitaBomba());
		cv.put(MainDBConstants.bombaCerrada, supervision.getBombaCerrada());
		
		cv.put(MainDBConstants.bombaPresion, supervision.getBombaPresion());
		cv.put(MainDBConstants.compruebaBomba, supervision.getCompruebaBomba());
		cv.put(MainDBConstants.colocApropiada, supervision.getColocApropiada());
		cv.put(MainDBConstants.distApropiada, supervision.getDistApropiada());
		cv.put(MainDBConstants.distBoquilla, supervision.getDistBoquilla());
		
		cv.put(MainDBConstants.pasoFrente, supervision.getPasoFrente());
		cv.put(MainDBConstants.mantRitmo, supervision.getMantRitmo());
		cv.put(MainDBConstants.metConteo, supervision.getMetConteo());
		cv.put(MainDBConstants.velocSuperficies, supervision.getVelocSuperficies());
		cv.put(MainDBConstants.supFajas, supervision.getSupFajas());
		
		cv.put(MainDBConstants.pasosLaterales, supervision.getPasosLaterales());
		cv.put(MainDBConstants.salvarObstaculos, supervision.getSalvarObstaculos());
		cv.put(MainDBConstants.bienRociado, supervision.getBienRociado());
		cv.put(MainDBConstants.supInvertidas, supervision.getSupInvertidas());
		cv.put(MainDBConstants.objPiso, supervision.getObjPiso());
		
		cv.put(MainDBConstants.reportaConsumoAprop, supervision.getReportaConsumoAprop());
		cv.put(MainDBConstants.transEqAprop, supervision.getTransEqAprop());
		cv.put(MainDBConstants.eqCompleto, supervision.getEqCompleto());
		cv.put(MainDBConstants.cuidaMatEq, supervision.getCuidaMatEq());
		cv.put(MainDBConstants.buenAspPersonal, supervision.getBuenAspPersonal());
		
		cv.put(MainDBConstants.cumpleInstrucciones, supervision.getCumpleInstrucciones());
		cv.put(MainDBConstants.aceptaSuperv, supervision.getAceptaSuperv());
		cv.put(MainDBConstants.respetuoso, supervision.getRespetuoso());
		cv.put(MainDBConstants.camp, supervision.getCamp());
		
		cv.put(MainDBConstants.obs, supervision.getObs());
		//METADATA
		if (supervision.getRecordDate() != null) cv.put(MainDBConstants.recordDate, supervision.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, supervision.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(supervision.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(supervision.getEstado()));
		cv.put(MainDBConstants.deviceId, supervision.getDeviceid());
		return cv; 
	}	
	
	public static Supervision crearSupervision(Cursor cursorSupervision){
		
		Supervision mSupervision = new Supervision();
		mSupervision.setIdent(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.ident)));
		
		mSupervision.setTarget(null);
		
		mSupervision.setRociador(null);
		mSupervision.setSupervisor(null);
		
		if(cursorSupervision.getLong(cursorSupervision.getColumnIndex(MainDBConstants.supervisionDate))>0) mSupervision.setSupervisionDate(new Date(cursorSupervision.getLong(cursorSupervision.getColumnIndex(MainDBConstants.supervisionDate))));
		
		mSupervision.setUsoEqProt(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.usoEqProt)));
		
		mSupervision.setEqProtBien(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.eqProtBien)));
		mSupervision.setNumIden(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.numIden)));
		mSupervision.setAguaOp(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.aguaOp)));
		mSupervision.setPrepViv(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.prepViv)));
		mSupervision.setCoopPrepViv(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.coopPrepViv)));
		
		mSupervision.setMezcla(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.mezcla)));
		mSupervision.setAguaAdec(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.aguaAdec)));
		mSupervision.setMezclaPrep(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.mezclaPrep)));
		mSupervision.setAgitaBomba(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.agitaBomba)));
		mSupervision.setBombaCerrada(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.bombaCerrada)));
		
		mSupervision.setBombaPresion(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.bombaPresion)));
		mSupervision.setCompruebaBomba(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.compruebaBomba)));
		mSupervision.setColocApropiada(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.colocApropiada)));
		mSupervision.setDistApropiada(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.distApropiada)));
		mSupervision.setDistBoquilla(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.distBoquilla)));
		
		mSupervision.setPasoFrente(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.pasoFrente)));
		mSupervision.setMantRitmo(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.mantRitmo)));
		mSupervision.setMetConteo(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.metConteo)));
		mSupervision.setVelocSuperficies(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.velocSuperficies)));
		mSupervision.setSupFajas(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.supFajas)));
		
		mSupervision.setPasosLaterales(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.pasosLaterales)));
		mSupervision.setSalvarObstaculos(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.salvarObstaculos)));
		mSupervision.setBienRociado(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.bienRociado)));
		mSupervision.setSupInvertidas(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.supInvertidas)));
		mSupervision.setObjPiso(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.objPiso)));
		
		mSupervision.setReportaConsumoAprop(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.reportaConsumoAprop)));
		mSupervision.setTransEqAprop(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.transEqAprop)));
		mSupervision.setEqCompleto(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.eqCompleto)));
		mSupervision.setCuidaMatEq(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.cuidaMatEq)));
		mSupervision.setBuenAspPersonal(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.buenAspPersonal)));
		
		mSupervision.setCumpleInstrucciones(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.cumpleInstrucciones)));
		mSupervision.setAceptaSuperv(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.aceptaSuperv)));
		mSupervision.setRespetuoso(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.respetuoso)));
		mSupervision.setCamp(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.camp)));


		mSupervision.setObs(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.obs)));
		//METADATA
		if(cursorSupervision.getLong(cursorSupervision.getColumnIndex(MainDBConstants.recordDate))>0) mSupervision.setRecordDate(new Date(cursorSupervision.getLong(cursorSupervision.getColumnIndex(MainDBConstants.recordDate))));
		mSupervision.setRecordUser(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.recordUser)));
		mSupervision.setPasive(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mSupervision.setEstado(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mSupervision.setDeviceid(cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.deviceId)));
		return mSupervision;
	}
	
}

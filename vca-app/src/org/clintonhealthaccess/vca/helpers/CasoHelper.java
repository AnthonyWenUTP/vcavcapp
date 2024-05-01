package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.Caso;

import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class CasoHelper {
	
	public static ContentValues crearCasoValues(Caso caso){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, caso.getIdent());
		cv.put(MainDBConstants.local, caso.getLocal().getIdent());
		cv.put(MainDBConstants.codigo, caso.getCodigo());
		cv.put(MainDBConstants.cui, caso.getCui());
		cv.put(MainDBConstants.codE1, caso.getCodE1());
		cv.put(MainDBConstants.casa, caso.getCasa());
		cv.put(MainDBConstants.nombre, caso.getNombre());
		
		cv.put(MainDBConstants.sexo, caso.getSexo());
		cv.put(MainDBConstants.edad, caso.getEdad());
		cv.put(MainDBConstants.embarazada, caso.getEmbarazada());
		cv.put(MainDBConstants.menor6meses, caso.getMenor6meses());
		
		cv.put(MainDBConstants.sint, caso.getSint());
		if (caso.getFisDate() != null) {
			cv.put(MainDBConstants.fisDate, caso.getFisDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.fisDate);
		}
		if (caso.getMxDate() != null) cv.put(MainDBConstants.mxDate, caso.getMxDate().getTime());
		cv.put(MainDBConstants.mxType, caso.getMxType());
		cv.put(MainDBConstants.inv, caso.getInv());
		if (caso.getInvDate() != null) {
			cv.put(MainDBConstants.invDate, caso.getInvDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.invDate);
		}
		if (caso.getInvCompDate() != null) {
			cv.put(MainDBConstants.invCompDate, caso.getInvCompDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.invCompDate);
		}
		cv.put(MainDBConstants.tx, caso.getTx());
		cv.put(MainDBConstants.txResultType, caso.getTxResultType());
		cv.put(MainDBConstants.txSup, caso.getTxSup());
		if (caso.getTxDate() != null) {
			cv.put(MainDBConstants.txDate, caso.getTxDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.txDate);
		}
		cv.put(MainDBConstants.txSusp, caso.getTxSusp());
		if (caso.getTxSuspDate() != null) {
			cv.put(MainDBConstants.txSuspDate, caso.getTxSuspDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.txSuspDate);
		}
		cv.put(MainDBConstants.txSuspReason, caso.getTxSuspReason());
		cv.put(MainDBConstants.txSuspOtherReason, caso.getTxSuspOtherReason());
		cv.put(MainDBConstants.txComp, caso.getTxComp());
		if (caso.getTxCompDate() != null) {
			cv.put(MainDBConstants.txCompDate, caso.getTxCompDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.txCompDate);
		}
		cv.put(MainDBConstants.sx, caso.getSx());
		if (caso.getSxDate() != null) {
			cv.put(MainDBConstants.sxDate, caso.getSxDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.sxDate);
		}
		cv.put(MainDBConstants.sxResult, caso.getSxResult());
		cv.put(MainDBConstants.sxComp, caso.getSxComp());
		if (caso.getSxCompDate() != null) {
			cv.put(MainDBConstants.sxCompDate, caso.getSxCompDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.sxCompDate);
		}
		cv.put(MainDBConstants.sxCompResult, caso.getSxCompResult());
		cv.put(MainDBConstants.lostFollowUp, caso.getLostFollowUp());
		cv.put(MainDBConstants.lostFollowUpReason, caso.getLostFollowUpReason());
		cv.put(MainDBConstants.lostFollowUpOtherReason, caso.getLostFollowUpOtherReason());
		cv.put(MainDBConstants.estadocaso, caso.getEstadocaso());
		cv.put(MainDBConstants.info, caso.getInfo());
		cv.put(MainDBConstants.latitude, caso.getLatitude());
		cv.put(MainDBConstants.longitude, caso.getLongitude());
		cv.put(MainDBConstants.altitud, caso.getAltitud());
		cv.put(MainDBConstants.exactitud, caso.getExactitud());
		cv.put(MainDBConstants.zoom, caso.getZoom());
		
		
		cv.put(MainDBConstants.latitudeOrigin, caso.getLatitudeOrigin());
		cv.put(MainDBConstants.longitudeOrigin, caso.getLongitudeOrigin());
		
		cv.put(MainDBConstants.zoomOrigin, caso.getZoomOrigin());
		
		if (caso.getDayTx01() != null) {
			cv.put(MainDBConstants.dayTx01, caso.getDayTx01().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx01);
		}
		if (caso.getDayTx02() != null) {
			cv.put(MainDBConstants.dayTx02, caso.getDayTx02().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx02);
		}
		if (caso.getDayTx03() != null) {
			cv.put(MainDBConstants.dayTx03, caso.getDayTx03().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx03);
		}
		if (caso.getDayTx04() != null) {
			cv.put(MainDBConstants.dayTx04, caso.getDayTx04().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx04);
		}
		if (caso.getDayTx05() != null) {
			cv.put(MainDBConstants.dayTx05, caso.getDayTx05().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx05);
		}
		if (caso.getDayTx06() != null) {
			cv.put(MainDBConstants.dayTx06, caso.getDayTx06().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx06);
		}
		if (caso.getDayTx07() != null) {
			cv.put(MainDBConstants.dayTx07, caso.getDayTx07().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx07);
		}
		if (caso.getDayTx08() != null) {
			cv.put(MainDBConstants.dayTx08, caso.getDayTx08().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx08);
		}
		if (caso.getDayTx09() != null) {
			cv.put(MainDBConstants.dayTx09, caso.getDayTx09().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx09);
		}
		if (caso.getDayTx10() != null) {
			cv.put(MainDBConstants.dayTx10, caso.getDayTx10().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx10);
		}
		if (caso.getDayTx11() != null) {
			cv.put(MainDBConstants.dayTx11, caso.getDayTx11().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx11);
		}
		if (caso.getDayTx12() != null) {
			cv.put(MainDBConstants.dayTx12, caso.getDayTx12().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx12);
		}
		if (caso.getDayTx13() != null) {
			cv.put(MainDBConstants.dayTx13, caso.getDayTx13().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx13);
		}
		if (caso.getDayTx14() != null) {
			cv.put(MainDBConstants.dayTx14, caso.getDayTx14().getTime());
		}
		else {
			cv.putNull(MainDBConstants.dayTx14);
		}
		
		
		//METADATA
		if (caso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, caso.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, caso.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(caso.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(caso.getEstado()));
		cv.put(MainDBConstants.deviceId, caso.getDeviceid());
		return cv; 
	}	
	
	public static Caso crearCaso(Cursor cursorCaso){
		
		Caso mCaso = new Caso();
		mCaso.setIdent(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.ident)));
		mCaso.setLocal(null);
		mCaso.setCodigo(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.codigo)));
		mCaso.setCui(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.cui)));
		mCaso.setCodE1(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.codE1)));
		mCaso.setCasa(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.casa)));
		mCaso.setNombre(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.nombre)));
		mCaso.setSexo(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.sexo)));
		mCaso.setEmbarazada(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.embarazada)));
		mCaso.setEdad(cursorCaso.getInt(cursorCaso.getColumnIndex(MainDBConstants.edad)));
		mCaso.setMenor6meses(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.menor6meses)));
		
		
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.fisDate))>0) mCaso.setFisDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.fisDate))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.mxDate))>0) mCaso.setMxDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.mxDate))));
		mCaso.setMxType(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.mxType)));
		mCaso.setInv(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.inv)));
		mCaso.setSint(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.sint)));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.invDate))>0) mCaso.setInvDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.invDate))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.invCompDate))>0) mCaso.setInvCompDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.invCompDate))));
		mCaso.setTx(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.tx)));
		mCaso.setTxResultType(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.txResultType)));
		mCaso.setTxSusp(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.txSusp)));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.txSuspDate))>0) mCaso.setTxSuspDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.txSuspDate))));
		mCaso.setTxSuspReason(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.txSuspReason)));
		mCaso.setTxSuspOtherReason(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.txSuspOtherReason)));
		mCaso.setTxSup(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.txSup)));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.txDate))>0) mCaso.setTxDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.txDate))));
		mCaso.setTxComp(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.txComp)));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.txCompDate))>0) mCaso.setTxCompDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.txCompDate))));
		mCaso.setSx(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.sx)));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.sxDate))>0) mCaso.setSxDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.sxDate))));
		mCaso.setSxResult(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.sxResult)));
		mCaso.setSxComp(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.sxComp)));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.sxCompDate))>0) mCaso.setSxCompDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.sxCompDate))));
		mCaso.setSxCompResult(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.sxCompResult)));
		
		mCaso.setLostFollowUp(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.lostFollowUp)));
		mCaso.setLostFollowUpReason(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.lostFollowUpReason)));
		mCaso.setLostFollowUpOtherReason(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.lostFollowUpOtherReason)));
		mCaso.setEstadocaso(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.estadocaso)));
		
		
		if(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.latitude))!=0) 
			mCaso.setLatitude(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.latitude)));
		if(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.longitude))!=0)
			mCaso.setLongitude(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.longitude)));
		
		if(cursorCaso.getInt(cursorCaso.getColumnIndex(MainDBConstants.zoom))!=0)
			mCaso.setZoom(cursorCaso.getInt(cursorCaso.getColumnIndex(MainDBConstants.zoom)));
		
		if(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.altitud))!=0) 
			mCaso.setAltitud(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.altitud)));
		if(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.exactitud))!=0)
			mCaso.setExactitud(cursorCaso.getFloat(cursorCaso.getColumnIndex(MainDBConstants.exactitud)));
		
		
		if(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.latitudeOrigin))!=0) 
			mCaso.setLatitudeOrigin(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.latitudeOrigin)));
		if(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.longitudeOrigin))!=0)
			mCaso.setLongitudeOrigin(cursorCaso.getDouble(cursorCaso.getColumnIndex(MainDBConstants.longitudeOrigin)));
		
		if(cursorCaso.getInt(cursorCaso.getColumnIndex(MainDBConstants.zoomOrigin))!=0)
			mCaso.setZoomOrigin(cursorCaso.getInt(cursorCaso.getColumnIndex(MainDBConstants.zoomOrigin)));
		
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx01))>0) mCaso.setDayTx01(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx01))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx02))>0) mCaso.setDayTx02(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx02))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx03))>0) mCaso.setDayTx03(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx03))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx04))>0) mCaso.setDayTx04(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx04))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx05))>0) mCaso.setDayTx05(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx05))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx06))>0) mCaso.setDayTx06(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx06))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx07))>0) mCaso.setDayTx07(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx07))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx08))>0) mCaso.setDayTx08(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx08))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx09))>0) mCaso.setDayTx09(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx09))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx10))>0) mCaso.setDayTx10(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx10))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx11))>0) mCaso.setDayTx11(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx11))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx12))>0) mCaso.setDayTx12(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx12))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx13))>0) mCaso.setDayTx13(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx13))));
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx14))>0) mCaso.setDayTx14(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.dayTx14))));
		
		
		
		mCaso.setInfo(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.info)));
		//METADATA
		if(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.recordDate))>0) mCaso.setRecordDate(new Date(cursorCaso.getLong(cursorCaso.getColumnIndex(MainDBConstants.recordDate))));
		mCaso.setRecordUser(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.recordUser)));
		mCaso.setPasive(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCaso.setEstado(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCaso.setDeviceid(cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.deviceId)));
		return mCaso;
	}
	
}

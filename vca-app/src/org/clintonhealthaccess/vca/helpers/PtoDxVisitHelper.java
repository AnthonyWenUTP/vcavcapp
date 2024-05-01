package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class PtoDxVisitHelper {
	
	public static ContentValues crearPtoDxVisitValues(PtoDxVisit visitaPto){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, visitaPto.getIdent());
		cv.put(MainDBConstants.punto, visitaPto.getPunto().getIdent());
		cv.put(MainDBConstants.visitType, visitaPto.getVisitType());
		if (visitaPto.getVisitDate() != null) {
			cv.put(MainDBConstants.visitDate, visitaPto.getVisitDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.visitDate);
		}
		cv.put(MainDBConstants.obs, visitaPto.getObs());
		
		//METADATA
		if (visitaPto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaPto.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, visitaPto.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(visitaPto.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(visitaPto.getEstado()));
		cv.put(MainDBConstants.deviceId, visitaPto.getDeviceid());
		return cv; 
	}	
	
	public static PtoDxVisit crearPtoDxVisit(Cursor cursorPtoDxVisit){
		
		PtoDxVisit mPtoDxVisit = new PtoDxVisit();
		mPtoDxVisit.setIdent(cursorPtoDxVisit.getString(cursorPtoDxVisit.getColumnIndex(MainDBConstants.ident)));
		mPtoDxVisit.setPunto(null);
		mPtoDxVisit.setVisitType(cursorPtoDxVisit.getString(cursorPtoDxVisit.getColumnIndex(MainDBConstants.visitType)));
		if(cursorPtoDxVisit.getLong(cursorPtoDxVisit.getColumnIndex(MainDBConstants.visitDate))>0) mPtoDxVisit.setVisitDate(new Date(cursorPtoDxVisit.getLong(cursorPtoDxVisit.getColumnIndex(MainDBConstants.visitDate))));
		mPtoDxVisit.setObs(cursorPtoDxVisit.getString(cursorPtoDxVisit.getColumnIndex(MainDBConstants.obs)));
		
		//METADATA
		if(cursorPtoDxVisit.getLong(cursorPtoDxVisit.getColumnIndex(MainDBConstants.recordDate))>0) mPtoDxVisit.setRecordDate(new Date(cursorPtoDxVisit.getLong(cursorPtoDxVisit.getColumnIndex(MainDBConstants.recordDate))));
		mPtoDxVisit.setRecordUser(cursorPtoDxVisit.getString(cursorPtoDxVisit.getColumnIndex(MainDBConstants.recordUser)));
		mPtoDxVisit.setPasive(cursorPtoDxVisit.getString(cursorPtoDxVisit.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mPtoDxVisit.setEstado(cursorPtoDxVisit.getString(cursorPtoDxVisit.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mPtoDxVisit.setDeviceid(cursorPtoDxVisit.getString(cursorPtoDxVisit.getColumnIndex(MainDBConstants.deviceId)));
		return mPtoDxVisit;
	}
	
}

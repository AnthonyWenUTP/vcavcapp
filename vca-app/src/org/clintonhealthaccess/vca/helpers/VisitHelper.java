package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class VisitHelper {
	
	public static ContentValues crearVisitValues(Visit visita){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, visita.getIdent());
		
		cv.put(MainDBConstants.target, visita.getTarget().getIdent());
		if (visita.getVisitDate() != null) cv.put(MainDBConstants.visitDate, visita.getVisitDate().getTime());
		
		cv.put(MainDBConstants.visitor, visita.getVisitor().getIdent());
		cv.put(MainDBConstants.supervisor, visita.getSupervisor().getIdent());
		cv.put(MainDBConstants.brigada, visita.getBrigada().getIdent());
		
		cv.put(MainDBConstants.visit, visita.getVisit());
		cv.put(MainDBConstants.activity, visita.getActivity());
		cv.put(MainDBConstants.compVisit, visita.getCompVisit());
		
		cv.put(MainDBConstants.reasonNoVisit, visita.getReasonNoVisit());
		cv.put(MainDBConstants.reasonNoVisitOther, visita.getReasonNoVisitOther());
		cv.put(MainDBConstants.reasonReluctant, visita.getReasonReluctant());
		cv.put(MainDBConstants.reasonReluctantOther, visita.getReasonReluctantOther());
		cv.put(MainDBConstants.sprayedRooms, visita.getSprayedRooms());
		cv.put(MainDBConstants.numCharges, visita.getNumCharges());
		cv.put(MainDBConstants.reasonIncomplete, visita.getReasonIncomplete());
		cv.put(MainDBConstants.supervised, visita.getSupervised());
		cv.put(MainDBConstants.personasCharlas, visita.getPersonasCharlas());
		cv.put(MainDBConstants.obs, visita.getObs());
		//METADATA
		if (visita.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visita.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, visita.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(visita.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(visita.getEstado()));
		cv.put(MainDBConstants.deviceId, visita.getDeviceid());
		return cv; 
	}	
	
	public static Visit crearVisit(Cursor cursorVisit){
		
		Visit mVisit = new Visit();
		mVisit.setIdent(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.ident)));
		
		mVisit.setTarget(null);
		mVisit.setBrigada(null);
		mVisit.setVisitor(null);
		mVisit.setSupervisor(null);
		
		if(cursorVisit.getLong(cursorVisit.getColumnIndex(MainDBConstants.visitDate))>0) mVisit.setVisitDate(new Date(cursorVisit.getLong(cursorVisit.getColumnIndex(MainDBConstants.visitDate))));
		
		mVisit.setVisit(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.visit)));
		
		
		mVisit.setActivity(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.activity)));
		mVisit.setCompVisit(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.compVisit)));
		
		mVisit.setReasonNoVisit(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.reasonNoVisit)));
		mVisit.setReasonNoVisitOther(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.reasonNoVisitOther)));
		mVisit.setReasonReluctant(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.reasonReluctant)));
		mVisit.setReasonReluctantOther(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.reasonReluctantOther)));
		
				
		
		
		if(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.sprayedRooms))!=null) 
			mVisit.setSprayedRooms(Integer.valueOf(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.sprayedRooms))));
			
		
		if(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.numCharges))!=null)
			mVisit.setNumCharges(Integer.valueOf(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.numCharges))));
		
		if(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.personasCharlas))!=null) 
			mVisit.setPersonasCharlas(Integer.valueOf(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.personasCharlas))));
		
		
		mVisit.setReasonIncomplete(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.reasonIncomplete)));
		
		mVisit.setObs(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.obs)));
		//METADATA
		if(cursorVisit.getLong(cursorVisit.getColumnIndex(MainDBConstants.recordDate))>0) mVisit.setRecordDate(new Date(cursorVisit.getLong(cursorVisit.getColumnIndex(MainDBConstants.recordDate))));
		mVisit.setRecordUser(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.recordUser)));
		mVisit.setPasive(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mVisit.setEstado(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mVisit.setDeviceid(cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.deviceId)));
		return mVisit;
	}
	
}

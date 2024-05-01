package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class TargetHelper {
	
	public static ContentValues crearTargetValues(Target meta){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, meta.getIdent());
		cv.put(MainDBConstants.irsSeason, meta.getIrsSeason().getIdent());
		cv.put(MainDBConstants.household, meta.getHousehold().getIdent());
		
		if (meta.getLastModified() != null) cv.put(MainDBConstants.lastModified, meta.getLastModified().getTime());
		cv.put(MainDBConstants.sprayStatus, meta.getSprayStatus());
		if (meta.getAssignedTo() != null) cv.put(MainDBConstants.assignedTo, meta.getAssignedTo().getIdent());
		//METADATA
		if (meta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, meta.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, meta.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(meta.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(meta.getEstado()));
		cv.put(MainDBConstants.deviceId, meta.getDeviceid());
		return cv; 
	}	
	
	public static Target crearTarget(Cursor cursorTarget){
		
		Target mTarget = new Target();
		mTarget.setIdent(cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.ident)));
		mTarget.setIrsSeason(null);
		mTarget.setHousehold(null);
		mTarget.setAssignedTo(null);
		if(cursorTarget.getLong(cursorTarget.getColumnIndex(MainDBConstants.lastModified))>0) mTarget.setLastModified(new Date(cursorTarget.getLong(cursorTarget.getColumnIndex(MainDBConstants.lastModified))));
		mTarget.setSprayStatus(cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.sprayStatus)));
		
		//METADATA
		if(cursorTarget.getLong(cursorTarget.getColumnIndex(MainDBConstants.recordDate))>0) mTarget.setRecordDate(new Date(cursorTarget.getLong(cursorTarget.getColumnIndex(MainDBConstants.recordDate))));
		mTarget.setRecordUser(cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.recordUser)));
		mTarget.setPasive(cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mTarget.setEstado(cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mTarget.setDeviceid(cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.deviceId)));
		return mTarget;
	}
	
}

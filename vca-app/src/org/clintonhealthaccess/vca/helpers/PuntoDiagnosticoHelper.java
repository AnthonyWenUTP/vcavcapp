package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class PuntoDiagnosticoHelper {
	
	public static ContentValues crearPuntoDiagnosticoValues(PuntoDiagnostico punto){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, punto.getIdent());
		cv.put(MainDBConstants.local, punto.getLocal().getIdent());
		cv.put(MainDBConstants.clave, punto.getClave());
		cv.put(MainDBConstants.tipo, punto.getTipo());
		cv.put(MainDBConstants.status, punto.getStatus());
		cv.put(MainDBConstants.info, punto.getInfo());
		cv.put(MainDBConstants.latitude, punto.getLatitude());
		cv.put(MainDBConstants.longitude, punto.getLongitude());
		cv.put(MainDBConstants.zoom, punto.getZoom());
		
		//METADATA
		if (punto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, punto.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, punto.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(punto.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(punto.getEstado()));
		cv.put(MainDBConstants.deviceId, punto.getDeviceid());
		return cv; 
	}	
	
	public static PuntoDiagnostico crearPuntoDiagnostico(Cursor cursorPuntoDiagnostico){
		
		PuntoDiagnostico mPuntoDiagnostico = new PuntoDiagnostico();
		mPuntoDiagnostico.setIdent(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.ident)));
		mPuntoDiagnostico.setLocal(null);
		mPuntoDiagnostico.setTipo(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.tipo)));
		mPuntoDiagnostico.setClave(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.clave)));
		mPuntoDiagnostico.setStatus(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.status)));
		mPuntoDiagnostico.setInfo(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.info)));
		if(cursorPuntoDiagnostico.getDouble(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.latitude))!=0) 
			mPuntoDiagnostico.setLatitude(cursorPuntoDiagnostico.getDouble(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.latitude)));
		if(cursorPuntoDiagnostico.getDouble(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.longitude))!=0)
			mPuntoDiagnostico.setLongitude(cursorPuntoDiagnostico.getDouble(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.longitude)));
		
		if(cursorPuntoDiagnostico.getInt(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.zoom))!=0)
			mPuntoDiagnostico.setZoom(cursorPuntoDiagnostico.getInt(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.zoom)));
		
		//METADATA
		if(cursorPuntoDiagnostico.getLong(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.recordDate))>0) mPuntoDiagnostico.setRecordDate(new Date(cursorPuntoDiagnostico.getLong(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.recordDate))));
		mPuntoDiagnostico.setRecordUser(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.recordUser)));
		mPuntoDiagnostico.setPasive(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mPuntoDiagnostico.setEstado(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mPuntoDiagnostico.setDeviceid(cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.deviceId)));
		return mPuntoDiagnostico;
	}
	
}

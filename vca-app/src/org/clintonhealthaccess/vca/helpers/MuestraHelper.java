package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class MuestraHelper {
	
	public static ContentValues crearMuestraValues(Muestra muestra){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, muestra.getIdent());
		cv.put(MainDBConstants.local, muestra.getLocal().getIdent());
		cv.put(MainDBConstants.casa, muestra.getCasa());
		cv.put(MainDBConstants.casa, muestra.getCasa());
		cv.put(MainDBConstants.mxProactiva, muestra.getMxProactiva());
		cv.put(MainDBConstants.mxReactiva, muestra.getMxReactiva());
		if (muestra.getMxDate() != null) cv.put(MainDBConstants.mxDate, muestra.getMxDate().getTime());
		cv.put(MainDBConstants.latitude, muestra.getLatitude());
		cv.put(MainDBConstants.longitude, muestra.getLongitude());
		cv.put(MainDBConstants.altitud, muestra.getAltitud());
		cv.put(MainDBConstants.exactitud, muestra.getExactitud());
		cv.put(MainDBConstants.zoom, muestra.getZoom());
		
		//METADATA
		if (muestra.getRecordDate() != null) cv.put(MainDBConstants.recordDate, muestra.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, muestra.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(muestra.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(muestra.getEstado()));
		cv.put(MainDBConstants.deviceId, muestra.getDeviceid());
		return cv; 
	}	
	
	public static Muestra crearMuestra(Cursor cursorMuestra){
		
		Muestra mMuestra = new Muestra();
		mMuestra.setIdent(cursorMuestra.getString(cursorMuestra.getColumnIndex(MainDBConstants.ident)));
		mMuestra.setLocal(null);
		mMuestra.setCasa(cursorMuestra.getString(cursorMuestra.getColumnIndex(MainDBConstants.casa)));
		mMuestra.setMxProactiva(cursorMuestra.getInt(cursorMuestra.getColumnIndex(MainDBConstants.mxProactiva)));
		mMuestra.setMxReactiva(cursorMuestra.getInt(cursorMuestra.getColumnIndex(MainDBConstants.mxReactiva)));
		if(cursorMuestra.getLong(cursorMuestra.getColumnIndex(MainDBConstants.mxDate))>0) mMuestra.setMxDate(new Date(cursorMuestra.getLong(cursorMuestra.getColumnIndex(MainDBConstants.mxDate))));
		if(cursorMuestra.getDouble(cursorMuestra.getColumnIndex(MainDBConstants.latitude))!=0) 
			mMuestra.setLatitude(cursorMuestra.getDouble(cursorMuestra.getColumnIndex(MainDBConstants.latitude)));
		if(cursorMuestra.getDouble(cursorMuestra.getColumnIndex(MainDBConstants.longitude))!=0)
			mMuestra.setLongitude(cursorMuestra.getDouble(cursorMuestra.getColumnIndex(MainDBConstants.longitude)));
		
		if(cursorMuestra.getInt(cursorMuestra.getColumnIndex(MainDBConstants.zoom))!=0)
			mMuestra.setZoom(cursorMuestra.getInt(cursorMuestra.getColumnIndex(MainDBConstants.zoom)));
		
		if(cursorMuestra.getDouble(cursorMuestra.getColumnIndex(MainDBConstants.altitud))!=0) 
			mMuestra.setAltitud(cursorMuestra.getDouble(cursorMuestra.getColumnIndex(MainDBConstants.altitud)));
		if(cursorMuestra.getDouble(cursorMuestra.getColumnIndex(MainDBConstants.exactitud))!=0)
			mMuestra.setExactitud(cursorMuestra.getFloat(cursorMuestra.getColumnIndex(MainDBConstants.exactitud)));
		
		
		//METADATA
		if(cursorMuestra.getLong(cursorMuestra.getColumnIndex(MainDBConstants.recordDate))>0) mMuestra.setRecordDate(new Date(cursorMuestra.getLong(cursorMuestra.getColumnIndex(MainDBConstants.recordDate))));
		mMuestra.setRecordUser(cursorMuestra.getString(cursorMuestra.getColumnIndex(MainDBConstants.recordUser)));
		mMuestra.setPasive(cursorMuestra.getString(cursorMuestra.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mMuestra.setEstado(cursorMuestra.getString(cursorMuestra.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mMuestra.setDeviceid(cursorMuestra.getString(cursorMuestra.getColumnIndex(MainDBConstants.deviceId)));
		return mMuestra;
	}
	
}

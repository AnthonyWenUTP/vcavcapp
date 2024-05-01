package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class CriaderoHelper {
	
	public static ContentValues crearCriaderoValues(Criadero punto){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, punto.getIdent());
		cv.put(MainDBConstants.local, punto.getLocal().getIdent());
		cv.put(MainDBConstants.tipo, punto.getTipo());
		cv.put(MainDBConstants.info, punto.getInfo());
		cv.put(MainDBConstants.especie, punto.getEspecie());
		cv.put(MainDBConstants.size, punto.getSize());
		
		//METADATA
		if (punto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, punto.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, punto.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(punto.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(punto.getEstado()));
		cv.put(MainDBConstants.deviceId, punto.getDeviceid());
		return cv; 
	}	
	
	public static Criadero crearCriadero(Cursor cursorCriadero){
		
		Criadero mCriadero = new Criadero();
		mCriadero.setIdent(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.ident)));
		mCriadero.setLocal(null);
		mCriadero.setTipo(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.tipo)));
		mCriadero.setEspecie(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.especie)));
		mCriadero.setInfo(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.info)));
		if(cursorCriadero.getDouble(cursorCriadero.getColumnIndex(MainDBConstants.size))!=0) 
			mCriadero.setSize(cursorCriadero.getDouble(cursorCriadero.getColumnIndex(MainDBConstants.size)));
		
		//METADATA
		if(cursorCriadero.getLong(cursorCriadero.getColumnIndex(MainDBConstants.recordDate))>0) mCriadero.setRecordDate(new Date(cursorCriadero.getLong(cursorCriadero.getColumnIndex(MainDBConstants.recordDate))));
		mCriadero.setRecordUser(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.recordUser)));
		mCriadero.setPasive(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCriadero.setEstado(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCriadero.setDeviceid(cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.deviceId)));
		return mCriadero;
	}
	
}

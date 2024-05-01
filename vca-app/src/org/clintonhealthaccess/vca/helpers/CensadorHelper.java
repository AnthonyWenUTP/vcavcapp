package org.clintonhealthaccess.vca.helpers;

import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class CensadorHelper {
	
	public static ContentValues crearCensadorValues(Censador censador){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, censador.getIdent());
		cv.put(MainDBConstants.code, censador.getCode());
		cv.put(MainDBConstants.name, censador.getName());
		return cv; 
	}	
	
	public static Censador crearCensador(Cursor cursorCensador){
		
		Censador mCensador = new Censador();
		mCensador.setIdent(cursorCensador.getString(cursorCensador.getColumnIndex(MainDBConstants.ident)));
		mCensador.setCode(cursorCensador.getString(cursorCensador.getColumnIndex(MainDBConstants.code)));
		mCensador.setName(cursorCensador.getString(cursorCensador.getColumnIndex(MainDBConstants.name)));
		return mCensador;
	}
	
}

package org.clintonhealthaccess.vca.helpers;

import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class BrigadaHelper {
	
	public static ContentValues crearBrigadaValues(Brigada brigada){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, brigada.getIdent());
		cv.put(MainDBConstants.code, brigada.getCode());
		cv.put(MainDBConstants.name, brigada.getName());
		return cv; 
	}	
	
	public static Brigada crearBrigada(Cursor cursorBrigada){
		
		Brigada mBrigada = new Brigada();
		mBrigada.setIdent(cursorBrigada.getString(cursorBrigada.getColumnIndex(MainDBConstants.ident)));
		mBrigada.setCode(cursorBrigada.getString(cursorBrigada.getColumnIndex(MainDBConstants.code)));
		mBrigada.setName(cursorBrigada.getString(cursorBrigada.getColumnIndex(MainDBConstants.name)));
		return mBrigada;
	}
	
}

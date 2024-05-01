package org.clintonhealthaccess.vca.helpers;

import org.clintonhealthaccess.vca.domain.Distrito;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class DistritoHelper {
	
	public static ContentValues crearDistritoValues(Distrito distrito){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, distrito.getIdent());
		cv.put(MainDBConstants.code, distrito.getCode());
		cv.put(MainDBConstants.name, distrito.getName());
		cv.put(MainDBConstants.area, distrito.getArea().getIdent());
		return cv; 
	}	
	
	public static Distrito crearDistrito(Cursor cursorDistrito){
		
		Distrito mDistrito = new Distrito();
		mDistrito.setIdent(cursorDistrito.getString(cursorDistrito.getColumnIndex(MainDBConstants.ident)));
		mDistrito.setCode(cursorDistrito.getString(cursorDistrito.getColumnIndex(MainDBConstants.code)));
		mDistrito.setName(cursorDistrito.getString(cursorDistrito.getColumnIndex(MainDBConstants.name)));
		mDistrito.setArea(null);
		return mDistrito;
	}
	
}

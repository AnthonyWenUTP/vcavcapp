package org.clintonhealthaccess.vca.helpers;

import org.clintonhealthaccess.vca.domain.Area;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class AreaHelper {
	
	public static ContentValues crearAreaValues(Area area){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, area.getIdent());
		cv.put(MainDBConstants.code, area.getCode());
		cv.put(MainDBConstants.name, area.getName());
		return cv; 
	}	
	
	public static Area crearArea(Cursor cursorArea){
		
		Area mArea = new Area();
		mArea.setIdent(cursorArea.getString(cursorArea.getColumnIndex(MainDBConstants.ident)));
		mArea.setCode(cursorArea.getString(cursorArea.getColumnIndex(MainDBConstants.code)));
		mArea.setName(cursorArea.getString(cursorArea.getColumnIndex(MainDBConstants.name)));
		return mArea;
	}
	
}

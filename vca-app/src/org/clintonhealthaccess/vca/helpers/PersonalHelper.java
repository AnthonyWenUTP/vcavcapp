package org.clintonhealthaccess.vca.helpers;

import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class PersonalHelper {
	
	public static ContentValues crearPersonalValues(Personal personal){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, personal.getIdent());
		cv.put(MainDBConstants.code, personal.getCode());
		cv.put(MainDBConstants.name, personal.getName());
		cv.put(MainDBConstants.sprayer, personal.isSprayer());
		cv.put(MainDBConstants.sentinel, personal.isSentinel());
		cv.put(MainDBConstants.supervisor, personal.isSupervisor());
		return cv; 
	}	
	
	public static Personal crearPersonal(Cursor cursorPersonal){
		
		Personal mPersonal = new Personal();
		mPersonal.setIdent(cursorPersonal.getString(cursorPersonal.getColumnIndex(MainDBConstants.ident)));
		mPersonal.setCode(cursorPersonal.getString(cursorPersonal.getColumnIndex(MainDBConstants.code)));
		mPersonal.setName(cursorPersonal.getString(cursorPersonal.getColumnIndex(MainDBConstants.name)));
		mPersonal.setSprayer(cursorPersonal.getInt(cursorPersonal.getColumnIndex(MainDBConstants.sprayer))==1?true:false);
		mPersonal.setSentinel(cursorPersonal.getInt(cursorPersonal.getColumnIndex(MainDBConstants.sentinel))==1?true:false);
		mPersonal.setSupervisor(cursorPersonal.getInt(cursorPersonal.getColumnIndex(MainDBConstants.supervisor))==1?true:false);
		return mPersonal;
	}
	
}

package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class PersonHelper {
	
	public static ContentValues crearPersonValues(Person person){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, person.getIdent());
		cv.put(MainDBConstants.household, person.getCasa().getIdent());
		cv.put(MainDBConstants.codePerson, person.getCode());
		cv.put(MainDBConstants.namePerson, person.getName());
		cv.put(MainDBConstants.agePerson, person.getAge());
		cv.put(MainDBConstants.sexPerson, person.getSex());
		cv.put(MainDBConstants.pregPerson, person.getPreg());
		cv.put(MainDBConstants.obs, person.getObs());
		//METADATA
		if (person.getRecordDate() != null) cv.put(MainDBConstants.recordDate, person.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, person.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(person.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(person.getEstado()));
		cv.put(MainDBConstants.deviceId, person.getDeviceid());
		return cv; 
	}	
	
	public static Person crearPerson(Cursor cursorPerson){
		
		Person mPerson = new Person();
		mPerson.setIdent(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.ident)));
		mPerson.setCasa(null);
		mPerson.setCode(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.codePerson)));
		mPerson.setName(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.namePerson)));
		mPerson.setSex(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.sexPerson)));
		mPerson.setAge(cursorPerson.getInt(cursorPerson.getColumnIndex(MainDBConstants.agePerson)));
		mPerson.setPreg(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.pregPerson)));
		mPerson.setObs(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.obs)));
		//METADATA
		if(cursorPerson.getLong(cursorPerson.getColumnIndex(MainDBConstants.recordDate))>0) mPerson.setRecordDate(new Date(cursorPerson.getLong(cursorPerson.getColumnIndex(MainDBConstants.recordDate))));
		mPerson.setRecordUser(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.recordUser)));
		mPerson.setPasive(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mPerson.setEstado(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mPerson.setDeviceid(cursorPerson.getString(cursorPerson.getColumnIndex(MainDBConstants.deviceId)));
		return mPerson;
	}
	
}

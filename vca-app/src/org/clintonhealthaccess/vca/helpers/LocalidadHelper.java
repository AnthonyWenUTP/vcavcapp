package org.clintonhealthaccess.vca.helpers;

import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class LocalidadHelper {
	
	public static ContentValues crearLocalidadValues(Localidad localidad){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, localidad.getIdent());
		cv.put(MainDBConstants.code, localidad.getCode());
		cv.put(MainDBConstants.name, localidad.getName());
		cv.put(MainDBConstants.distrito, localidad.getDistrict().getIdent());
		cv.put(MainDBConstants.latitude, localidad.getLatitude());
		cv.put(MainDBConstants.longitude, localidad.getLongitude());
		cv.put(MainDBConstants.population, localidad.getPopulation());
		cv.put(MainDBConstants.pattern, localidad.getPattern());
		cv.put(MainDBConstants.obs, localidad.getObs());
		cv.put(MainDBConstants.acceso, localidad.isTieneAcceso());
		return cv; 
	}	
	
	public static Localidad crearLocalidad(Cursor cursorLocalidad){
		
		Localidad mLocalidad = new Localidad();
		mLocalidad.setIdent(cursorLocalidad.getString(cursorLocalidad.getColumnIndex(MainDBConstants.ident)));
		mLocalidad.setCode(cursorLocalidad.getString(cursorLocalidad.getColumnIndex(MainDBConstants.code)));
		mLocalidad.setName(cursorLocalidad.getString(cursorLocalidad.getColumnIndex(MainDBConstants.name)));
		mLocalidad.setDistrict(null);
		mLocalidad.setLatitude(cursorLocalidad.getDouble(cursorLocalidad.getColumnIndex(MainDBConstants.latitude)));
		mLocalidad.setLongitude(cursorLocalidad.getDouble(cursorLocalidad.getColumnIndex(MainDBConstants.longitude)));
		mLocalidad.setPopulation(cursorLocalidad.getInt(cursorLocalidad.getColumnIndex(MainDBConstants.population)));
		mLocalidad.setPattern(cursorLocalidad.getString(cursorLocalidad.getColumnIndex(MainDBConstants.pattern)));
		mLocalidad.setObs(cursorLocalidad.getString(cursorLocalidad.getColumnIndex(MainDBConstants.obs)));
		mLocalidad.setTieneAcceso(cursorLocalidad.getInt(cursorLocalidad.getColumnIndex(MainDBConstants.acceso))>0);
		return mLocalidad;
	}
	
}

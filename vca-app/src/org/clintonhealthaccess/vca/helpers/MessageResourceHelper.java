package org.clintonhealthaccess.vca.helpers;

import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class MessageResourceHelper {
	
	public static ContentValues crearMessageResourceValues(MessageResource mensaje){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.messageKey, mensaje.getMessageKey());
		cv.put(MainDBConstants.catRoot, mensaje.getCatRoot());
		cv.put(MainDBConstants.catKey, mensaje.getCatKey());
		cv.put(MainDBConstants.pasive, String.valueOf(mensaje.getPasive()));
		cv.put(MainDBConstants.isCat, String.valueOf(mensaje.getIsCat()));
		cv.put(MainDBConstants.order, mensaje.getOrder());
		cv.put(MainDBConstants.spanish, mensaje.getSpanish());
		cv.put(MainDBConstants.english, mensaje.getEnglish());
		return cv; 
	}	
	
	public static MessageResource crearMessageResource(Cursor cursorMessageResource){
		
		MessageResource mMessageResource = new MessageResource();
		mMessageResource.setMessageKey(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.messageKey)));
		mMessageResource.setCatRoot(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.catRoot)));
		mMessageResource.setCatKey(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.catKey)));
		mMessageResource.setPasive(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mMessageResource.setOrder(cursorMessageResource.getInt(cursorMessageResource.getColumnIndex(MainDBConstants.order)));
		mMessageResource.setSpanish(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.spanish)));
		mMessageResource.setEnglish(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.english)));
		return mMessageResource;
	}
	
}

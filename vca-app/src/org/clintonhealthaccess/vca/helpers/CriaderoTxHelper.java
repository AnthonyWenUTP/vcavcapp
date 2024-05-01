package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class CriaderoTxHelper {
	
	public static ContentValues crearCriaderoTxValues(CriaderoTx txCriadero){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, txCriadero.getIdent());
		cv.put(MainDBConstants.criadero, txCriadero.getCriadero().getIdent());
		cv.put(MainDBConstants.txType, txCriadero.getTxType());
		if (txCriadero.getTxDate() != null) {
			cv.put(MainDBConstants.txDate, txCriadero.getTxDate().getTime());
		}
		else {
			cv.putNull(MainDBConstants.txDate);
		}
		cv.put(MainDBConstants.obs, txCriadero.getObs());
		
		//METADATA
		if (txCriadero.getRecordDate() != null) cv.put(MainDBConstants.recordDate, txCriadero.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, txCriadero.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(txCriadero.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(txCriadero.getEstado()));
		cv.put(MainDBConstants.deviceId, txCriadero.getDeviceid());
		return cv; 
	}	
	
	public static CriaderoTx crearCriaderoTx(Cursor cursorCriaderoTx){
		
		CriaderoTx mCriaderoTx = new CriaderoTx();
		mCriaderoTx.setIdent(cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.ident)));
		mCriaderoTx.setCriadero(null);
		mCriaderoTx.setTxType(cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.txType)));
		if(cursorCriaderoTx.getLong(cursorCriaderoTx.getColumnIndex(MainDBConstants.txDate))>0) mCriaderoTx.setTxDate(new Date(cursorCriaderoTx.getLong(cursorCriaderoTx.getColumnIndex(MainDBConstants.txDate))));
		mCriaderoTx.setObs(cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.obs)));
		
		//METADATA
		if(cursorCriaderoTx.getLong(cursorCriaderoTx.getColumnIndex(MainDBConstants.recordDate))>0) mCriaderoTx.setRecordDate(new Date(cursorCriaderoTx.getLong(cursorCriaderoTx.getColumnIndex(MainDBConstants.recordDate))));
		mCriaderoTx.setRecordUser(cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.recordUser)));
		mCriaderoTx.setPasive(cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCriaderoTx.setEstado(cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCriaderoTx.setDeviceid(cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.deviceId)));
		return mCriaderoTx;
	}
	
}

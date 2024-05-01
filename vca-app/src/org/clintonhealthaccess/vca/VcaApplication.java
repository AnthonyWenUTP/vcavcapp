package org.clintonhealthaccess.vca;

import android.app.Application;
import android.content.Context;

public class VcaApplication extends Application{
	
	private String passApp;
	private static Context mContext;
	private static VcaApplication singleton;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		//Mapbox.getInstance(getApplicationContext(), "pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw");
	}
	
	public static Context getContext(){
        return mContext;
    }

	public String getPassApp() {
		return passApp;
	}

	protected void setPassApp(String passApp) {
		this.passApp = passApp;
	}
	
	public static VcaApplication getInstance() {
        return singleton;
    }

}

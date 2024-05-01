package org.clintonhealthaccess.vca.utils;

import android.location.Location;

public class CalculateDistance {
	
	
	
	public float calcDistancia(double latActual, double lonActual, double latAlmacenada, double lonAlmacenada) {
		float distancia=(float) 0;
		
		Location locationA = new Location("point A");

		locationA.setLatitude(latAlmacenada);
		locationA.setLongitude(lonAlmacenada);

		Location locationB = new Location("point B");

		locationB.setLatitude(latActual);
		locationB.setLongitude(lonActual);

		distancia = locationA.distanceTo(locationB);
		
		return distancia;
		
	}

}

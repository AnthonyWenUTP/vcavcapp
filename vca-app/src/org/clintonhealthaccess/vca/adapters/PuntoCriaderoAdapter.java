package org.clintonhealthaccess.vca.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;

public class PuntoCriaderoAdapter extends ArrayAdapter<PuntosCriadero> {
	
	//private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public PuntoCriaderoAdapter(Context context, int textViewResourceId,
                          List<PuntosCriadero> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.household_list_item, null);
		}
		PuntosCriadero p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {	
				textView.setText(String.valueOf(p.getOrder()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.BLACK);
			
			textView.setText("Lat: "+ String.valueOf(p.getLatitude()) + " Long: " + String.valueOf(p.getLongitude()));
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (String.valueOf(p.getEstado()).equals("0")) {
				imageView.setImageResource(R.drawable.ic_pending);
			} 
			else if (String.valueOf(p.getEstado()).equals("1")) {
				imageView.setImageResource(R.drawable.red);
			}
			else if (String.valueOf(p.getEstado()).equals("2")) {
				imageView.setImageResource(R.drawable.green);
			}
			
			
		}
		return v;
	}
}

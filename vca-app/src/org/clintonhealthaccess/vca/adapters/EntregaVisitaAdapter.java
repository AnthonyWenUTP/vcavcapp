package org.clintonhealthaccess.vca.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaVisita;

public class EntregaVisitaAdapter extends ArrayAdapter<EntregaVisita> {
	
		
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public EntregaVisitaAdapter(Context context, int textViewResourceId,
                          List<EntregaVisita> items) {
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
		EntregaVisita p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				textView.setText("Entrega: " + p.getEntrega());
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {
				textView.setText("Recuperación: " + p.getVisitaRecuperacion());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			if(p.getPasive()=='1') {
				textView.setTextColor(Color.RED);
			}
			else {
				textView.setTextColor(Color.BLACK);
			}
			
			textView.setText("Fecha de visita: "+ mDateFormat.format(p.getVisitDate()));
			
			textView = (TextView) v.findViewById(R.id.infoc_text);
			
			
		}
		return v;
	}
}

package org.clintonhealthaccess.vca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.domain.Localidad;

public class LocalidadAdapter extends ArrayAdapter<Localidad> {
	
	public LocalidadAdapter(Context context, int textViewResourceId,
                          List<Localidad> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.localidad_list_item, null);
		}
		Localidad p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.dep_text);
			if (textView != null) {
				textView.setText(p.getDistrict().getArea().getName());
			}
			
			textView = (TextView) v.findViewById(R.id.mun_text);
			if (textView != null) {
				textView.setText(p.getDistrict().getName());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setText(p.getName());
			
		}
		return v;
	}
}

package org.clintonhealthaccess.vca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.utils.Dato;

public class DatosAdapter extends ArrayAdapter<Dato> {
	
	public DatosAdapter(Context context, int textViewResourceId,
                          List<Dato> items) {
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
		Dato p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.dep_text);
			if (textView != null) {
				textView.setText(p.getCampo());
			}


			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setText(p.getValor());
			
		}
		return v;
	}
}

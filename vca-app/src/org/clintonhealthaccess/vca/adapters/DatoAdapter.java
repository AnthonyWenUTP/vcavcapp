package org.clintonhealthaccess.vca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import org.clintonhealthaccess.vca.R;

public class DatoAdapter extends ArrayAdapter<Object[]> {
	
	
	public DatoAdapter(Context context, int textViewResourceId,
                          List<Object[]> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.dato_list_item, null);
		}
		Object[] p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			
			textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setText(p[0].toString());
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setText(p[1].toString());
		}
		return v;
	}
}

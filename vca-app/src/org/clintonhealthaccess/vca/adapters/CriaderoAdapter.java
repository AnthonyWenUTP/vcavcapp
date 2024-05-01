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
import org.clintonhealthaccess.vca.domain.Criadero;

public class CriaderoAdapter extends ArrayAdapter<Criadero> {
	
	//private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public CriaderoAdapter(Context context, int textViewResourceId,
                          List<Criadero> items) {
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
		Criadero p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				if (p.getTipo().equals("PR")) {
					textView.setText("Productivos");
				} else if (p.getTipo().equals("PT")) {
					textView.setText("Potenciales");
				}
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {	
				textView.setText(String.valueOf(p.getSize()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.BLACK);
			
			textView.setText(p.getInfo());
			
			textView = (TextView) v.findViewById(R.id.infoc_text);
			
			
			textView.setText(p.getLocal().getName());
			
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (String.valueOf(p.getEstado()).equals("1")) {
				imageView.setImageResource(R.drawable.red);
			}
			else if (String.valueOf(p.getEstado()).equals("2")) {
				imageView.setImageResource(R.drawable.green);
			}
			
			
		}
		return v;
	}
}

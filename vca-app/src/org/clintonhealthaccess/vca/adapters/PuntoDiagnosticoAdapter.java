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
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;

public class PuntoDiagnosticoAdapter extends ArrayAdapter<PuntoDiagnostico> {
	
	//private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public PuntoDiagnosticoAdapter(Context context, int textViewResourceId,
                          List<PuntoDiagnostico> items) {
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
		PuntoDiagnostico p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				if (p.getTipo().equals("CM")) {
					textView.setText("Centros de Microscopía");
				} else if (p.getTipo().equals("CS")) {
					textView.setText("CS/CAP");
				} else if (p.getTipo().equals("CV")) {
					textView.setText("ColVol");
				} else if (p.getTipo().equals("HOSP")) {
					textView.setText("Hospital");
				} else if (p.getTipo().equals("OTRO")) {
					textView.setText("Otro");
				} else if (p.getTipo().equals("POC")) {
					textView.setText("POC agroindustria");
				} else if (p.getTipo().equals("PS")) {
					textView.setText("Puesto de Salud");
				}
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {	
				textView.setText(p.getStatus());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.BLACK);
			
			textView.setText(p.getClave());
			
			textView = (TextView) v.findViewById(R.id.infoc_text);
			
			
			
			textView.setText(p.getLocal().getName());
			
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

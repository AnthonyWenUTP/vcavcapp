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

import java.text.SimpleDateFormat;
import java.util.List;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.domain.PtoDxVisit;

public class VisPtoDxVisitAdapter extends ArrayAdapter<PtoDxVisit> {
	
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public VisPtoDxVisitAdapter(Context context, int textViewResourceId,
                          List<PtoDxVisit> items) {
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
		PtoDxVisit p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				if (p.getVisitType().equals("MON")) {
					textView.setText("Monitoreo");
				} else if (p.getVisitType().equals("CAP")) {
					textView.setText("Capacitación");
				}
				
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {	
				textView.setText(mDateFormat.format(p.getVisitDate()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.BLACK);
			
			textView.setText(p.getObs());
			
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

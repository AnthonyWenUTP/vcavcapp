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
import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionPersona;

public class EvaluacionPersonaAdapter extends ArrayAdapter<EvaluacionPersona> {
	
		
	public EvaluacionPersonaAdapter(Context context, int textViewResourceId,
                          List<EvaluacionPersona> items) {
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
		EvaluacionPersona p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				textView.setText(p.getPersona().getCode());
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {
				textView.setText(p.getPersona().getAge().toString());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			if(p.getPasive()=='1') {
				textView.setTextColor(Color.RED);
			}
			else {
				textView.setTextColor(Color.BLACK);
			}
			
			textView.setText(this.getContext().getString(R.string.name) + ": " +p.getPersona().getName());
			
			textView = (TextView) v.findViewById(R.id.infoc_text);
			
			if(p.getUsoNocheAnterior()!=null) {
				textView.setText("Evaluación:\nDurmió bajo mosquitero la noche anterior: "+p.getUsoNocheAnterior()+
					"\nFrecuencia de uso última semana: "+ p.getFrecuenciaSemana());
			}
			else {
				textView.setText("Sin evaluar");
			}
				
			
			
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (String.valueOf(p.getPersona().getSex()).equals("M")) {
					imageView.setImageResource(R.drawable.male);
				} else if (String.valueOf(p.getPersona().getSex()).equals("F")) {
					imageView.setImageResource(R.drawable.female);
				} else {
					imageView.setImageResource(R.drawable.gray);
				}
			}
		}
		return v;
	}
}

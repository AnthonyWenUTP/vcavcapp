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
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.utils.Constants;

public class EntregaTargetAdapter extends ArrayAdapter<EntregaTarget> {
	
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public EntregaTargetAdapter(Context context, int textViewResourceId,
                          List<EntregaTarget> items) {
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
		EntregaTarget p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				textView.setText(p.getHousehold().getCode());
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getHousehold().getCensusDate()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.BLACK);
			if(p.getHousehold().getInhabited().equals(Constants.CERRADA_ID)){
				textView.setTextColor(Color.RED);
				textView.setText("Casa Cerrada");
			} else {
				textView.setText(this.getContext().getString(R.string.ownerName) + ": " +p.getHousehold().getOwnerName());
			}	
			
			textView = (TextView) v.findViewById(R.id.infoc_text);
				Integer espaciosDormir = p.getSitiosDormirCama() + p.getSitiosDormirHamaca() + p.getSitiosDormirOtro() + p.getSitiosDormirSuelo();
				textView.setText("Total espacios de dormir: " + espaciosDormir + "\nTotal habitantes: " + p.getHousehold().getHabitants() + "\nEstado vivienda: "+
											p.getStatus());
				if(p.getStatus().equals("Asignada")) {
					textView.setText(textView.getText()+" a "+p.getAssignedTo().getName());
				}
				textView.setText(textView.getText()+"\nObs:"+p.getHousehold().getObs());
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				imageView.setImageResource(R.drawable.red);
			}
			
			
		}
		return v;
	}
}

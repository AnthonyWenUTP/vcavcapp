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
import org.clintonhealthaccess.vca.domain.Caso;

public class CasoAdapter extends ArrayAdapter<Caso> {
	
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public CasoAdapter(Context context, int textViewResourceId,
                          List<Caso> items) {
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
		Caso p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				textView.setText(p.getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getMxDate()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.BLACK);
			
			String identificadores="";
			if(p.getNombre()!=null) {
				identificadores = "Nombre: " + p.getNombre();
			}
			else if(p.getCui()!=null) {
				identificadores = "Cui: " + p.getCui();
			}
			else if(p.getCasa()!=null) {
				identificadores = "Casa: " + p.getCasa();
			}
			else if(p.getCodE1()!=null) {
				identificadores = "Código E1: " + p.getCodE1();
			}
			textView.setText(identificadores);
			
			textView = (TextView) v.findViewById(R.id.infoc_text);
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			
			
			
			if (p.getEstadocaso().equals("CONF")) {
				textView.setText("Confirmado");
				imageView.setImageResource(R.drawable.red);
			} else if (p.getEstadocaso().equals("TRAT")) {
				textView.setText("En tratamiento");
				imageView.setImageResource(R.drawable.orange);
			} else if (p.getEstadocaso().equals("TRATC")) {
				textView.setText("Tratamiento completo");
				imageView.setImageResource(R.drawable.blue);
			} else if (p.getEstadocaso().equals("SEG2")) {
				textView.setText("Primer control negativo");
				imageView.setImageResource(R.drawable.yellow);
			} else if (p.getEstadocaso().equals("SEG4")) {
				textView.setText("Segundo control negativo");
				imageView.setImageResource(R.drawable.green);
			} else if (p.getEstadocaso().equals("SEGPOS")) {
				textView.setText("No curado");
				imageView.setImageResource(R.drawable.purple);
			} else if (p.getEstadocaso().equals("SEGINC")) {
				textView.setText("Pérdida de seguimiento");
				imageView.setImageResource(R.drawable.gray);
			}
			
			if (String.valueOf(p.getEstado()).equals("0")) {
				imageView.setImageResource(R.drawable.ic_pending);
			}
			
			if(p.getLocal()!=null) {
				textView.setText(textView.getText()+"\n"+p.getLocal().getName());
			}
			
			
		}
		return v;
	}
}

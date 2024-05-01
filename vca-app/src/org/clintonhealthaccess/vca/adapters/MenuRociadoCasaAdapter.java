package org.clintonhealthaccess.vca.adapters;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.domain.irs.Target;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuRociadoCasaAdapter extends ArrayAdapter<String> {

	private final String[] values;
	private final Target mTarget;
	
	public MenuRociadoCasaAdapter(Context context, int textViewResourceId,
			String[] values, Target meta) {
		super(context, textViewResourceId, values);
		this.values = values;
		this.mTarget=meta;
	}
	
	@Override
    public boolean isEnabled(int position) {
        // Disable the first item of GridView
		boolean habilitado = true;
		switch (position){
		
		default:
			habilitado = true;
			break;
        }        	
		return habilitado;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_item_2, null);
		}
		TextView textView = (TextView) v.findViewById(R.id.label);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setTextColor(Color.BLACK);
		textView.setText(values[position]);
		
		// Change icon based on position
		Drawable img = null;
		switch (position){
		case 0: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_mark);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 1: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_goto);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 2: 
			if(mTarget.getHousehold().getLatitude()==null) {
				textView.setTextColor(Color.RED);
				textView.setText("No tiene ubicación registrada ");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_myplaces);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 3: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_find);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;	
		case 4: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_revert);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		
		default:
			img=getContext().getResources().getDrawable( R.drawable.ic_launcher);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		}

		return v;
	}
}

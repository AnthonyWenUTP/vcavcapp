package org.clintonhealthaccess.vca.adapters;



import org.clintonhealthaccess.vca.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ViewDataActivityAdapter extends ArrayAdapter<String> {

	private final String[] values;
	public ViewDataActivityAdapter(Context context, int textViewResourceId,
			String[] values) {
		super(context, textViewResourceId, values);
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_item, null);
		}
		TextView textView = (TextView) v.findViewById(R.id.label);
		textView.setText(values[position]);

		// Change icon based on position
		Drawable img = null;
		switch (position){
		case 0: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_month);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		case 1: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_myplaces);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;	
		case 2: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_mark);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		case 3: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_today);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		default:
			img=getContext().getResources().getDrawable( R.drawable.ic_launcher);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		}
		return v;
	}
}

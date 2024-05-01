package org.clintonhealthaccess.vca.adapters;



import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.clintonhealthaccess.vca.R;

public class MainActivityAdapter extends ArrayAdapter<String> {

	private final String[] values;
	private Integer numCatalogos;
	public MainActivityAdapter(Context context, int textViewResourceId,
			String[] values, Integer numCatalogos) {
		super(context, textViewResourceId, values);
		this.values = values;
		this.numCatalogos = numCatalogos;
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
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_home);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		case 1: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_goto);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		case 2: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_selectall_holo_light);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		case 3: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_mylocation);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		case 4: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_set_as);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			break;
		case 5: 
			textView.setText(textView.getText() + " (" + numCatalogos +")");
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_settings_holo_light);
			textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			if (numCatalogos < 1){
                textView.setTextColor(Color.RED);
                textView.setTypeface(null, Typeface.BOLD);
            }
			break;
		case 6: 
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_download);
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

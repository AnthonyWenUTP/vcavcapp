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
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.utils.Constants;

public class HouseholdAdapter extends ArrayAdapter<Household> {
	
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public HouseholdAdapter(Context context, int textViewResourceId,
                          List<Household> items) {
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
		Household p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				textView.setText(p.getCode());
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getCensusDate()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.BLACK);
			if(p.getInhabited().equals(Constants.CERRADA_ID)){
				textView.setTextColor(Color.RED);
				textView.setText("Casa Cerrada");
			} else {
				textView.setText(this.getContext().getString(R.string.ownerName) + ": " +p.getOwnerName());
			}	
			
			textView = (TextView) v.findViewById(R.id.infoc_text);
			if(!(p.getInhabited().equals(Constants.CERRADA_ID))){
				textView.setText(this.getContext().getString(R.string.habitants) + ": " +p.getHabitants() + ", " +this.getContext().getString(R.string.rooms) + ": " +p.getRooms());
			}
			else {
				textView.setText("");
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (String.valueOf(p.getEstado()).equals("0")) {
					imageView.setImageResource(R.drawable.ic_pending);
				} else if (String.valueOf(p.getEstado()).equals("1")) {
					imageView.setImageResource(R.drawable.ic_notsent);
				} else if (String.valueOf(p.getEstado()).equals("2")) {
					imageView.setImageResource(R.drawable.ic_done);
				}
			}
		}
		return v;
	}
}

package org.clintonhealthaccess.vca.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.domain.Caso;
import org.joda.time.DateMidnight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuCasoTxSupAdapter extends ArrayAdapter<String> {

	private final String[] values;
	private final Caso mCaso;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public MenuCasoTxSupAdapter(Context context, int textViewResourceId,
			String[] values, Caso caso) {
		super(context, textViewResourceId, values);
		this.values = values;
		this.mCaso=caso;
	}
	
	@Override
    public boolean isEnabled(int position) {
        // Disable the first item of GridView
		boolean habilitado = true;
		switch (position){
		case 0:
			if(mCaso.getDayTx02()!=null){
				habilitado = false;
			}else {
				habilitado = true;
			}
			break;
		case 1:
			if(mCaso.getDayTx01()==null || mCaso.getDayTx03()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx01().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;	
		case 2:
			if(mCaso.getDayTx02()==null || mCaso.getDayTx04()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx02().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;			
		case 3:
			if(mCaso.getDayTx03()==null || mCaso.getDayTx05()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx03().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 4:
			if(mCaso.getDayTx04()==null || mCaso.getDayTx06()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx04().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 5:
			if(mCaso.getDayTx05()==null || mCaso.getDayTx07()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx05().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 6:
			if(mCaso.getDayTx06()==null || mCaso.getDayTx08()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx06().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 7:
			if(mCaso.getDayTx07()==null || mCaso.getDayTx09()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx07().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 8:
			if(mCaso.getDayTx08()==null || mCaso.getDayTx10()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx08().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 9:
			if(mCaso.getDayTx09()==null || mCaso.getDayTx11()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx09().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 10:
			if(mCaso.getDayTx10()==null || mCaso.getDayTx12()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx10().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 11:
			if(mCaso.getDayTx11()==null || mCaso.getDayTx13()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx11().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 12:
			if(mCaso.getDayTx12()==null || mCaso.getDayTx14()!=null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx12().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
		case 13:
			if(mCaso.getDayTx13()==null){
				habilitado = false;
			}else {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx13().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}
			break;
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
			v = vi.inflate(R.layout.menu_item_3, null);
		}
		TextView textView = (TextView) v.findViewById(R.id.label);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setTextColor(Color.BLACK);
		textView.setText(values[position]);
		
		// Change icon based on position
		Drawable img = null;
		switch (position){		
		case 0: 
			if(mCaso.getDayTx01()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx01()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx02()!=null){
				textView.setTextColor(Color.GRAY);
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 1: 
			if(mCaso.getDayTx02()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx02()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx01()==null || mCaso.getDayTx03()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx01()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx01().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 2: 
			if(mCaso.getDayTx03()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx03()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx02()==null || mCaso.getDayTx04()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx02()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx02().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 3: 
			if(mCaso.getDayTx04()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx04()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx03()==null || mCaso.getDayTx05()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx03()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx03().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 4: 
			if(mCaso.getDayTx05()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx05()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx04()==null || mCaso.getDayTx06()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx04()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx04().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 5: 
			if(mCaso.getDayTx06()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx06()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx05()==null || mCaso.getDayTx07()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx05()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx05().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
			
		case 6: 
			if(mCaso.getDayTx07()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx07()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx06()==null || mCaso.getDayTx08()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx06()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx06().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
			
		case 7: 
			if(mCaso.getDayTx08()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx08()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx07()==null || mCaso.getDayTx09()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx07()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx07().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 8: 
			if(mCaso.getDayTx09()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx09()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx08()==null || mCaso.getDayTx10()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx08()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx08().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 9: 
			if(mCaso.getDayTx10()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx10()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx09()==null || mCaso.getDayTx11()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx09()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx09().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 10: 
			if(mCaso.getDayTx11()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx11()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx10()==null || mCaso.getDayTx12()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx10()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx10().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 11: 
			if(mCaso.getDayTx12()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx12()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx11()==null || mCaso.getDayTx13()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx11()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx11().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 12: 
			if(mCaso.getDayTx13()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx13()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx12()==null || mCaso.getDayTx14()!=null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx12()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx12().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
			
		case 13: 
			if(mCaso.getDayTx14()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getDayTx14()));
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getDayTx13()==null){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getDayTx13()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getDayTx13().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
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

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

public class MenuCasoAdapter extends ArrayAdapter<String> {

	private final String[] values;
	private final Caso mCaso;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public MenuCasoAdapter(Context context, int textViewResourceId,
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
			if(mCaso.getLostFollowUp().matches("0")){
				habilitado = true;
			}else {
				habilitado = false;
			}
			break;  
        	
		case 1:
			
			if(mCaso.getLostFollowUp().matches("0")){
				habilitado = true;
			}else {
				habilitado = false;
			}
			break;        	
        	
		case 2:
			
			if(mCaso.getLostFollowUp().matches("0")){
				habilitado = true;
			}else {
				habilitado = false;
			}
			break;  
			
		case 3:
			
			if(mCaso.getLostFollowUp().matches("0")&& mCaso.getInvCompDate()==null){
				habilitado = true;
			}else {
				habilitado = false;
			}
			break;
			
		case 4:
			
			if(mCaso.getLostFollowUp().matches("0") && mCaso.getInvDate()!=null){
				habilitado = true;
			}else {
				habilitado = false;
			}
			break;
			
		case 5:
			
			if(mCaso.getTxComp().matches("1") || !mCaso.getTxSup().matches("No") || mCaso.getTxSusp().matches("1") || mCaso.getLostFollowUp().matches("1")){
				habilitado = false;
			}else {
				habilitado = true;
			}
			break;

		case 6:
			
			if(mCaso.getTx().matches("1") && mCaso.getLostFollowUp().matches("0")&& mCaso.getTxSusp().matches("0")){
				habilitado = true;
			}else {
				habilitado = false;
			}
			break;
			
		case 7:
			
			if(mCaso.getTx().matches("1") && mCaso.getLostFollowUp().matches("0")&&mCaso.getTxComp().matches("0")){
				habilitado = true;
			}else {
				habilitado = false;
			}
			break;	
			
			
		case 8:
			
			if(mCaso.getTx().matches("1") && mCaso.getTxSusp().matches("0") && mCaso.getSx().matches("0") && mCaso.getLostFollowUp().matches("0")){
				DateMidnight minDate = new DateMidnight(mCaso.getTxDate().getTime());
				minDate = minDate.plusDays(13);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}else {
				habilitado = false;
			}
			break;
			
			
		case 9:
			
			if(mCaso.getTxComp().matches("1") && mCaso.getSxComp().matches("0") && mCaso.getLostFollowUp().matches("0")){
				DateMidnight minDate = new DateMidnight(mCaso.getTxCompDate().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}else {
				habilitado = false;
			}
			break;
			
		case 10:
			
			if(mCaso.getSx().matches("1") && mCaso.getLostFollowUp().matches("0")){
				DateMidnight minDate = new DateMidnight(mCaso.getTxCompDate().getTime());
				minDate = minDate.plusDays(10);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					habilitado = false;
				}
				else {
					habilitado = true;
				}
			}else {
				habilitado = false;
			}
			break;	
			
		case 11:
			
			if(mCaso.getSxComp().matches("1")){
				habilitado = false;
			}else {
				habilitado = true;
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
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_edit);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 1: 
			if(mCaso.getLatitude()==null) {
					textView.setTextColor(Color.RED);
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_myplaces);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 2: 
			if(mCaso.getLatitudeOrigin()==null) {
					textView.setTextColor(Color.RED);
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_mylocation);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;			
		case 3: 
			if(mCaso.getInvDate()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				if(mCaso.getInvDate()!=null) {
					textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getInvDate()));
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getInvCompDate()!=null || mCaso.getLostFollowUp().matches("1")){
				textView.setTextColor(Color.GRAY);
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 4: 
			if(mCaso.getInvCompDate()!=null) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				if(mCaso.getInvCompDate()!=null) {
					textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getInvCompDate()));
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getInvDate()==null || mCaso.getLostFollowUp().matches("1")){
				textView.setTextColor(Color.GRAY);
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;		
		case 5: 
			if(mCaso.getTx().matches("1")) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				if(mCaso.getTxDate()!=null) {
					textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getTxDate()));
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(mCaso.getTxComp().matches("1") || mCaso.getLostFollowUp().matches("1") || !mCaso.getTxSup().matches("No")|| mCaso.getTxSusp().matches("1")){
				textView.setTextColor(Color.GRAY);
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 6: 
			if(!mCaso.getTxSup().matches("No")) {
				img=getContext().getResources().getDrawable( R.drawable.green);
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(!(mCaso.getTx().matches("1") && mCaso.getLostFollowUp().matches("0")&& mCaso.getTxSusp().matches("0"))){
				textView.setTextColor(Color.GRAY);
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 7: 
			if(mCaso.getTxSusp().matches("1")) {
				img=getContext().getResources().getDrawable( R.drawable.orange);
				if(mCaso.getTxSuspDate()!=null) {
					textView.setText("Tratamiento suspendido"+ "\n"+mDateFormat.format(mCaso.getTxSuspDate()));
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.yellow);
			}
			if(!(mCaso.getTx().matches("1") && mCaso.getLostFollowUp().matches("0")&&mCaso.getTxComp().matches("0"))){
				textView.setTextColor(Color.GRAY);
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;			
		case 8: 
			if(mCaso.getTxComp().matches("1")) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				if(mCaso.getTxCompDate()!=null) {
					textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getTxCompDate()));
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(!(mCaso.getTx().matches("1") && mCaso.getTxSusp().matches("0") && mCaso.getSx().matches("0") && mCaso.getLostFollowUp().matches("0"))){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getTxDate()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getTxDate().getTime());
				minDate = minDate.plusDays(13);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 9: 
			if(mCaso.getSx().matches("1")) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				if(mCaso.getSxDate()!=null) {
					textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getSxDate()));
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(!(mCaso.getTxComp().matches("1") && mCaso.getSxComp().matches("0") && mCaso.getLostFollowUp().matches("0"))){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getTxCompDate()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getTxCompDate().getTime());
				minDate = minDate.plusDays(1);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 10: 
			if(mCaso.getSxComp().matches("1")) {
				img=getContext().getResources().getDrawable( R.drawable.green);
				if(mCaso.getSxCompDate()!=null) {
					textView.setText(textView.getText()+"\n"+mDateFormat.format(mCaso.getSxCompDate()));
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.red);
			}
			if(!(mCaso.getSx().matches("1") && mCaso.getLostFollowUp().matches("0"))){
				textView.setTextColor(Color.GRAY);
			}
			if(mCaso.getTxCompDate()!=null) {
				DateMidnight minDate = new DateMidnight(mCaso.getTxCompDate().getTime());
				minDate = minDate.plusDays(10);
				DateMidnight todayDate = new DateMidnight(new Date().getTime());
				if(todayDate.isBefore(minDate)) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;			
		case 11: 
			if(mCaso.getLostFollowUp().matches("1")) {
				img=getContext().getResources().getDrawable( R.drawable.orange);
				if (mCaso.getLostFollowUpReason().equals("CD")) {
					textView.setText(textView.getText()+"\n"+ "Cambio de domicilio");
				}
				else if (mCaso.getLostFollowUpReason().equals("WORK")) {
					textView.setText(textView.getText()+"\n"+ "Trabaja fuera de la localidad");
				}
				else if (mCaso.getLostFollowUpReason().equals("REL")) {
					textView.setText(textView.getText()+"\n"+ "Renuencia al tratamiento");
				}
				else if (mCaso.getLostFollowUpReason().equals("ADV")) {
					textView.setText(textView.getText()+"\n"+ "Reacción adversa al tratamiento");
				}
				else if (mCaso.getLostFollowUpReason().equals("OTHER")) {
					textView.setText(textView.getText()+"\n"+ "Otro motivo: "+mCaso.getLostFollowUpOtherReason());
				}
			}
			else {
				img=getContext().getResources().getDrawable( R.drawable.yellow);
				if(mCaso.getSxComp().matches("1")) {
					textView.setTextColor(Color.GRAY);
				}
			}
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 12:
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_delete);
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

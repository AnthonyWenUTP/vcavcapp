package org.clintonhealthaccess.vca.wizard.model;

import java.util.ArrayList;

import org.clintonhealthaccess.vca.wizard.ui.LabelFragment;

import android.support.v4.app.Fragment;

public class LabelPage extends Page {
	
	protected boolean mValPattern = false;
	protected String mPattern="";

	public LabelPage(ModelCallbacks callbacks, String title, String hintText, String textColor,boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible, true);
	}

	@Override
	public Fragment createFragment() {
		return LabelFragment.create(getKey());
	}

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {

	}
}

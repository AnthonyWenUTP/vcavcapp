package org.clintonhealthaccess.vca.wizard.model;

import java.util.ArrayList;

import org.clintonhealthaccess.vca.wizard.ui.BarcodeFragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

public class BarcodePage extends Page {
	
	protected boolean mValPattern = false;
	protected String mPattern="";
	protected int mGreaterOrEqualsThan = 0;
	protected int mLowerOrEqualsThan = 0;
	protected boolean mValRange = false;

	public BarcodePage(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible, true);
	}

	@Override
	public Fragment createFragment() {
		return BarcodeFragment.create(getKey());
	}
	
	public Page setPatternValidation(boolean valPattern, String pattern) {
		mPattern = pattern;
		mValPattern = valPattern;
        return this;
    }

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {
		dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY),
				getKey()));

	}

	public boolean ismValPattern() {
		return mValPattern;
	}

	public void setmValPattern(boolean mValPattern) {
		this.mValPattern = mValPattern;
	}

	public String getmPattern() {
		return mPattern;
	}

	public void setmPattern(String mPattern) {
		this.mPattern = mPattern;
	}
	
	public Page setRangeValidation(boolean valRange, int minimo, int maximo) {
		mGreaterOrEqualsThan = minimo;
		mLowerOrEqualsThan = maximo;
		mValRange = valRange;
        return this;
    }

	public int getmGreaterOrEqualsThan() {
		return mGreaterOrEqualsThan;
	}

	public void setmGreaterOrEqualsThan(int mGreaterOrEqualsThan) {
		this.mGreaterOrEqualsThan = mGreaterOrEqualsThan;
	}

	public int getmLowerOrEqualsThan() {
		return mLowerOrEqualsThan;
	}

	public void setmLowerOrEqualsThan(int mLowerOrEqualsThan) {
		this.mLowerOrEqualsThan = mLowerOrEqualsThan;
	}

	public boolean ismValRange() {
		return mValRange;
	}

	public void setmValRange(boolean mValRange) {
		this.mValRange = mValRange;
	}

	@Override
	public boolean isCompleted() {
		return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
	}

	public BarcodePage setValue(String value) {
		mData.putString(SIMPLE_DATA_KEY, value);
		return this;
	}
}

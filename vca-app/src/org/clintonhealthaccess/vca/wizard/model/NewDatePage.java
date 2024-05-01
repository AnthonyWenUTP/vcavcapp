package org.clintonhealthaccess.vca.wizard.model;

import java.util.ArrayList;

import org.clintonhealthaccess.vca.wizard.ui.NewDateFragment;
import org.joda.time.DateMidnight;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

public class NewDatePage extends Page {
	
	protected DateMidnight mLaterThan = new DateMidnight();
	protected DateMidnight mSoonerThan = new DateMidnight();
	protected boolean mValRange = false;

	public NewDatePage(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible, true);
	}

	@Override
	public Fragment createFragment() {
		return NewDateFragment.create(getKey(),mValRange,mLaterThan,mSoonerThan);
	}
	
	public Page setRangeValidation(boolean valRange, DateMidnight minimo, DateMidnight maximo) {
		mLaterThan = minimo;
		mSoonerThan = maximo;
		mValRange = valRange;
        return this;
    }

	public DateMidnight getmLaterThan() {
		return mLaterThan;
	}

	public void setmLaterThan(DateMidnight mLaterThan) {
		this.mLaterThan = mLaterThan;
	}

	public DateMidnight getmSoonerThan() {
		return mSoonerThan;
	}

	public void setmSoonerThan(DateMidnight mSoonerThan) {
		this.mSoonerThan = mSoonerThan;
	}

	public boolean ismValRange() {
		return mValRange;
	}

	public void setmValRange(boolean mValRange) {
		this.mValRange = mValRange;
	}

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {
		dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY),
				getKey()));

	}

	@Override
	public boolean isCompleted() {
		return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
	}

	public NewDatePage setValue(String value) {
		mData.putString(SIMPLE_DATA_KEY, value);
		return this;
	}
}

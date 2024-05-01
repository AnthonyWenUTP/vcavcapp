package org.clintonhealthaccess.vca.wizard.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;

public class IntegerFragment extends TextFragment {
	public static IntegerFragment create(String key) {
		Bundle args = new Bundle();
		args.putString(ARG_KEY, key);

		IntegerFragment f = new IntegerFragment();
		f.setArguments(args);
		return f;
	}

	@SuppressLint("InlinedApi")
	@Override
	protected void setInputType() {
		mEditTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

}

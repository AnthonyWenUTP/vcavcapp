package org.clintonhealthaccess.vca.wizard.ui;

import java.util.Calendar;

import org.clintonhealthaccess.vca.wizard.model.Page;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.clintonhealthaccess.vca.R;


public class NewDateFragment extends Fragment {
	protected static final String ARG_KEY = "key";
	protected static final int DATE_DIALOG_ID = 99;

	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private Page mPage;
	
	private static DateMidnight mMinDate;
	private static DateMidnight mMaxDate;
	private static boolean mValRange;

	protected TextView mTitleTextInput;
	protected TextView mHintTextInput;
	protected EditText mEditTextInput;
	protected ImageButton mButtonBarcode;
	
	private int year;
	private int month;
	private int day;

	public static NewDateFragment create(String key, boolean valRange, DateMidnight minimo, DateMidnight maximo) {
		Bundle args = new Bundle();
		args.putString(ARG_KEY, key);
		mMinDate=minimo;
		mMaxDate=maximo;
		mValRange=valRange;
		NewDateFragment f = new NewDateFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		mKey = args.getString(ARG_KEY);
		mPage = mCallbacks.onGetPage(mKey);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_page_newdate,
				container, false);
		
		mTitleTextInput = (TextView) rootView.findViewById(android.R.id.title);
		mTitleTextInput.setText(mPage.getTitle());
		mTitleTextInput.setTextColor(Color.parseColor(mPage.getTextColor()));
		
		mHintTextInput = (TextView) rootView.findViewById(R.id.label_hint);
		mHintTextInput.setText(mPage.getHint());
		mHintTextInput.setTextColor(Color.parseColor(mPage.getmHintTextColor()));
		
		mEditTextInput = (EditText) rootView.findViewById(R.id.editTextInput);
		mEditTextInput.setText(mPage.getData().getString(Page.SIMPLE_DATA_KEY));
		mEditTextInput.setEnabled(false);
		mButtonBarcode = (ImageButton) rootView.findViewById(R.id.changedate_button);
		mButtonBarcode.setEnabled(mPage.ismEnabled());
		mButtonBarcode.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_DIALOG_ID);
			}
		});
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mEditTextInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() >= 0 && isResumed()) {
                    mPage.getData().putString(Page.SIMPLE_DATA_KEY, editable.toString());
                    mPage.notifyDataChanged();
                }
            }

		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof PageFragmentCallbacks)) {
			throw new ClassCastException(
					"Activity must implement PageFragmentCallbacks");
		}

		mCallbacks = (PageFragmentCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	private void createDialog(int dialog) {
		switch(dialog){
		case DATE_DIALOG_ID:
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			final DatePickerDialog dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, datePickerListener, year, month,
					day);
			if(mValRange){
				dpD.getDatePicker().setMinDate(mMinDate.getMillis());
				dpD.getDatePicker().setMaxDate(mMaxDate.getMillis());
				year = mMaxDate.getYear();
				month = mMaxDate.getMonthOfYear();
				day = mMaxDate.getDayOfMonth();
			}
			dpD.getDatePicker().init(year, month, day,new DatePicker.OnDateChangedListener() {

                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                	DateMidnight valueSelected = DateMidnight.parse(dayOfMonth+"/"+(monthOfYear+1)+"/"+year, DateTimeFormat.forPattern("dd/MM/yyyy"));
                	if(mValRange && (valueSelected.isBefore(mMinDate.getMillis())||valueSelected.isAfter(mMaxDate.getMillis()))){
            			Toast.makeText(getActivity(),getActivity().getString(R.string.outofrange), Toast.LENGTH_SHORT).show();
                	}
                	else{
                		dpD.getButton(DatePickerDialog.BUTTON_POSITIVE).setEnabled(true);
                	}
                }
            });
			dpD.show();
			
		default:
			break;
		}
	}
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			DateMidnight valueSelected = DateMidnight.parse(day+"/"+(month+1)+"/"+year, DateTimeFormat.forPattern("dd/MM/yyyy"));
			
        	if(mValRange && (valueSelected.isBefore(mMinDate.getMillis())||valueSelected.isAfter(mMaxDate.getMillis()))){
        			Toast.makeText(getActivity(),getActivity().getString(R.string.outofrange), Toast.LENGTH_SHORT).show();
        	}
        	else{
				// set selected date into textview
				mEditTextInput.setText(new StringBuilder().append(String.valueOf(year))
						.append("-").append(month<=8?"0".concat(String.valueOf(month+1)):String.valueOf(month+1)).append("-").append(day<=9?"0".concat(String.valueOf(day)):String.valueOf(day))
						.append(""));
				mPage.getData().putString(Page.SIMPLE_DATA_KEY,mEditTextInput.getText().toString());
				mPage.notifyDataChanged();
        	}
		}
	};
}

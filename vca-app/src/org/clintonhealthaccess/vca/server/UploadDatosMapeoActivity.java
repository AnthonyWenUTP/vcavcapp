
package org.clintonhealthaccess.vca.server;


import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.listeners.UploadListener;
import org.clintonhealthaccess.vca.preferences.PreferencesActivity;
import org.clintonhealthaccess.vca.server.tasks.UploadDatosMapeoTask;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.VcaApplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class UploadDatosMapeoActivity extends Activity implements UploadListener{

	protected static final String TAG = UploadDatosMapeoActivity.class.getSimpleName();

	private String username;
	private String password;
	private String url;
	private SharedPreferences settings;
	private UploadDatosMapeoTask uploadDatosMapeoTask;

	private final static int PROGRESS_DIALOG = 1;
	private ProgressDialog mProgressDialog;

	// ***************************************
	// Metodos de la actividad
	// ***************************************

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(getString(R.string.app_name) + " > "
				+ getString(R.string.census_upload));

		if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			setResult(RESULT_CANCELED);
			finish();
		}
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		url =
				settings.getString(PreferencesActivity.KEY_SERVER_URL, this.getString(R.string.default_server_url));
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);

		password =
				((VcaApplication) this.getApplication()).getPassApp();

		// get the task if we've changed orientations. If it's null it's a new upload.
		uploadDatosMapeoTask = (UploadDatosMapeoTask) getLastNonConfigurationInstance();
		if (uploadDatosMapeoTask == null) {
			uploadAll();
		}
	}


	@Override
	public void uploadComplete(String result) {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
		if(result!=null){
			if (result.matches("Datos recibidos!")) {
				setResult(RESULT_OK);
			} else if (result.matches("no hay datos")) {
				Intent intent = new Intent();
				intent.putExtra("resultado", "no hay datos");
				setResult(RESULT_CANCELED, intent);
			}
			else {
				Intent intent = new Intent();
				intent.putExtra("resultado", result);
				setResult(RESULT_CANCELED, intent);
			}
		}
		else{
			Intent intent = new Intent();
			intent.putExtra("resultado", getString(R.string.error));
			setResult(RESULT_CANCELED, intent);
		}
		uploadDatosMapeoTask = null;
		finish();

	}

	@Override
	public void progressUpdate(String message, int progress, int max) {

		mProgressDialog.setMax(max);
		mProgressDialog.setProgress(progress);
		mProgressDialog.setTitle(message);

	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		if (uploadDatosMapeoTask != null && uploadDatosMapeoTask.getStatus() != AsyncTask.Status.FINISHED)
			return uploadDatosMapeoTask;

		return null;
	}

	@Override
	protected void onDestroy() {
		if (uploadDatosMapeoTask != null) {
			uploadDatosMapeoTask.setUploadListener(null);
			if (uploadDatosMapeoTask.getStatus() == AsyncTask.Status.FINISHED) {
				uploadDatosMapeoTask.cancel(true);
			}
		}

		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (uploadDatosMapeoTask != null) {
			uploadDatosMapeoTask.setUploadListener(this);
		}
	}

	@Override
	protected void onPause() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		super.onPause();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == PROGRESS_DIALOG) {
			mProgressDialog = createUploadDialog();
			return mProgressDialog;
		}
		return null;
	}

	private ProgressDialog createUploadDialog() {

		ProgressDialog dialog = new ProgressDialog(this);
		DialogInterface.OnClickListener loadingButtonListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				uploadDatosMapeoTask.setUploadListener(null);
				uploadDatosMapeoTask.cancel(true);
				Intent intent = new Intent();
				intent.putExtra("resultado", getString(R.string.error));
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		};
		dialog.setTitle(getString(R.string.loading));
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setIndeterminate(false);
		dialog.setCancelable(false);
		dialog.setButton(getString(R.string.cancel),
				loadingButtonListener);
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		if (id == PROGRESS_DIALOG) {
			ProgressDialog progress = (ProgressDialog) dialog;
			progress.setTitle(getString(R.string.loading));
			progress.setProgress(0);
		}
	}

	private void uploadAll() {
		uploadDatosMapeoTask =  new UploadDatosMapeoTask(this.getApplicationContext());
		uploadDatosMapeoTask.setUploadListener(UploadDatosMapeoActivity.this);
		uploadDatosMapeoTask.execute(url,username,password);
		showDialog(PROGRESS_DIALOG);
	} 

}

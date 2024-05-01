
package org.clintonhealthaccess.vca.server;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.listeners.DownloadListener;
import org.clintonhealthaccess.vca.preferences.PreferencesActivity;
import org.clintonhealthaccess.vca.server.tasks.DownloadCatalogosTask;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.VcaApplication;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class DownloadCatalogosActivity extends Activity implements DownloadListener{

	protected static final String TAG = DownloadCatalogosActivity.class.getSimpleName();

	private String username;
	private String password;
	private String url;
	private SharedPreferences settings;
	private DownloadCatalogosTask downloadCatalogosTask;
	private final static int PROGRESS_DIALOG = 1;
	private ProgressDialog mProgressDialog;
	

	// ***************************************
	// Metodos de la actividad
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(getString(R.string.app_name) + " > "
				+ getString(R.string.main_download));

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
		
		downloadCatalogosTask = (DownloadCatalogosTask) getLastNonConfigurationInstance();
		if (downloadCatalogosTask == null) {
			downloadCatalogoData();
		}
	}

	private void downloadCatalogoData(){   
		if (downloadCatalogosTask != null)
			return;
		showDialog(PROGRESS_DIALOG);
		downloadCatalogosTask = new DownloadCatalogosTask(this.getApplicationContext());
		downloadCatalogosTask.setDownloadListener(DownloadCatalogosActivity.this);
		downloadCatalogosTask.execute(url, username, password);
	}

	@Override
	public void downloadComplete(String result) {

		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}

		if (result != null) {
			Intent intent = new Intent();
			intent.putExtra("resultado", result);
			setResult(RESULT_CANCELED, intent);
		} else {
			setResult(RESULT_OK);
		}

		downloadCatalogosTask = null;
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
		return downloadCatalogosTask;
	}

	@Override
	protected void onDestroy() {
		if (downloadCatalogosTask != null) {
			downloadCatalogosTask.setDownloadListener(null);
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (downloadCatalogosTask != null) {
			downloadCatalogosTask.setDownloadListener(this);
		}

		if (mProgressDialog != null && !mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == PROGRESS_DIALOG) {
			mProgressDialog = createDownloadDialog();
			return mProgressDialog;
		}
		return null;
	}

	private ProgressDialog createDownloadDialog() {

		ProgressDialog dialog = new ProgressDialog(this);
		DialogInterface.OnClickListener loadingButtonListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				downloadCatalogosTask.setDownloadListener(null);
				Intent intent = new Intent();
				intent.putExtra("resultado", getString(R.string.err_cancel));
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

}

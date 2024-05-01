/*
 * Copyright (C) 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.clintonhealthaccess.vca.preferences;


import org.clintonhealthaccess.vca.utils.UrlUtils;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.Toast;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.activities.ListaBrigadasActivity;
import org.clintonhealthaccess.vca.activities.ListaCensadoresActivity;
import org.clintonhealthaccess.vca.activities.ListaLocalidadesActivity;
import org.clintonhealthaccess.vca.activities.ListaRociadoresActivity;
import org.clintonhealthaccess.vca.activities.ListaSupervisoresActivity;
import org.clintonhealthaccess.vca.activities.ListaTemporadasActivity;

/**
 * @author william aviles
 */
@SuppressWarnings("deprecation")
public class PreferencesActivity extends PreferenceActivity implements
        OnSharedPreferenceChangeListener {
	

    public static String KEY_SERVER_URL = "server_url";
    public static String KEY_USERNAME = "username";
    public static String KEY_BARCODE = "barcode";
    public static String KEY_LOCALIDAD = "localidad";
    public static String KEY_CODE_LOCALIDAD = "localidadSeleccionada";
    public static String KEY_TEMPORADA = "temporada";
    public static String KEY_CODE_TEMPORADA = "temporadaSeleccionada";
    public static String KEY_CENSADOR = "censador";
    public static String KEY_CODE_CENSADOR = "censadorSeleccionado";
    public static String KEY_SUPERVISOR = "supervisor";
    public static String KEY_CODE_SUPERVISOR = "supervisorSeleccionado";
    public static String KEY_ROCIADOR = "rociador";
    public static String KEY_CODE_ROCIADOR = "rociadorSeleccionado";
    public static String KEY_BRIGADA = "brigada";
    public static String KEY_CODE_BRIGADA = "brigadaSeleccionada";
    public static String KEY_ULTSYNCCAT = "ultSyncCatalogos";

    private EditTextPreference mServerUrlPreference;
    private EditTextPreference mUsernamePreference;
    private PreferenceScreen mLocalidadPreference;
    private PreferenceScreen mCensadorPreference;
    private PreferenceScreen mSupervisorPreference;
    private PreferenceScreen mRociadorPreference;
    private PreferenceScreen mBrigadaPreference;
    private PreferenceScreen mTemporadaPreference;
    
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	ActionBar actionBar = getActionBar();
        	actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        
        setTitle(getString(R.string.app_name) + " > " + getString(R.string.preferences));
        buscarLocalidad();
        buscarTemporada();
        buscarCensador();
        buscarSupervisor();
        buscarRociador();
        buscarBrigada();
        updateServerUrl();
        updateUsername();
        updateLocalidad();
        updateTemporada();
        updateCensador();
        updateSupervisor();
        updateRociador();
        updateBrigada();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
            this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        updateServerUrl();
        updateUsername();
        updateLocalidad();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    	if (key.equals(KEY_SERVER_URL)) {
            updateServerUrl();
        } else if (key.equals(KEY_USERNAME)) {
            updateUsername();
        } else if (key.equals(KEY_LOCALIDAD)) {
        	updateLocalidad();
        } else if (key.equals(KEY_TEMPORADA)) {
        	updateTemporada();
        }
    }

    private void validateUrl(EditTextPreference preference) {
        if (preference != null) {
            String url = preference.getText();
            if (UrlUtils.isValidUrl(url)) {
                preference.setText(url);
                preference.setSummary(url);
            } else {
                // preference.setText((String) preference.getSummary());
                Toast.makeText(getApplicationContext(), getString(R.string.url_error),
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateServerUrl() {
        mServerUrlPreference = (EditTextPreference) findPreference(KEY_SERVER_URL);

        // remove all trailing "/"s
        while (mServerUrlPreference.getText().endsWith("/")) {
            mServerUrlPreference.setText(mServerUrlPreference.getText().substring(0,
                mServerUrlPreference.getText().length() - 1));
        }
        validateUrl(mServerUrlPreference);
        mServerUrlPreference.setSummary(mServerUrlPreference.getText());

        mServerUrlPreference.getEditText().setFilters(new InputFilter[] {
            getReturnFilter()
        });
    }


    private void updateUsername() {
        mUsernamePreference = (EditTextPreference) findPreference(KEY_USERNAME);
        mUsernamePreference.setSummary(mUsernamePreference.getText());

        mUsernamePreference.getEditText().setFilters(new InputFilter[] {
            getWhitespaceFilter()
        });

    }
    
    private void updateLocalidad() {
    	mLocalidadPreference = (PreferenceScreen) findPreference(KEY_LOCALIDAD);
    	mLocalidadPreference.setSummary(mLocalidadPreference.getSharedPreferences().getString(KEY_LOCALIDAD, null));
    }
    
    private void updateTemporada() {
    	mTemporadaPreference = (PreferenceScreen) findPreference(KEY_TEMPORADA);
    	mTemporadaPreference.setSummary(mTemporadaPreference.getSharedPreferences().getString(KEY_TEMPORADA, null));
    }
    
    private void buscarLocalidad() {
    	mLocalidadPreference = (PreferenceScreen) findPreference(KEY_LOCALIDAD);
    	mLocalidadPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// Presenta Localidad
				Intent myIntent = new Intent(PreferencesActivity.this, ListaLocalidadesActivity.class);
				PreferencesActivity.this.startActivity(myIntent);
			    return true;
			}
    		
    	}); 	
    }
    
    private void buscarTemporada() {
    	mTemporadaPreference = (PreferenceScreen) findPreference(KEY_TEMPORADA);
    	mTemporadaPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// Presenta Localidad
				Intent myIntent = new Intent(PreferencesActivity.this, ListaTemporadasActivity.class);
				PreferencesActivity.this.startActivity(myIntent);
			    return true;
			}
    		
    	}); 	
    }

    
    
    private void updateCensador() {
    	mCensadorPreference = (PreferenceScreen) findPreference(KEY_CENSADOR);
    	mCensadorPreference.setSummary(mCensadorPreference.getSharedPreferences().getString(KEY_CENSADOR, null));
    }
    
    private void buscarCensador() {
    	mCensadorPreference = (PreferenceScreen) findPreference(KEY_CENSADOR);
    	mCensadorPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// Presenta Censador
				Intent myIntent = new Intent(PreferencesActivity.this, ListaCensadoresActivity.class);
				PreferencesActivity.this.startActivity(myIntent);
			    return true;
			}
    		
    	}); 	
    }
    
    private void updateSupervisor() {
    	mSupervisorPreference = (PreferenceScreen) findPreference(KEY_SUPERVISOR);
    	mSupervisorPreference.setSummary(mSupervisorPreference.getSharedPreferences().getString(KEY_SUPERVISOR, null));
    }
    
    private void buscarSupervisor() {
    	mSupervisorPreference = (PreferenceScreen) findPreference(KEY_SUPERVISOR);
    	mSupervisorPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// Presenta Censador
				Intent myIntent = new Intent(PreferencesActivity.this, ListaSupervisoresActivity.class);
				PreferencesActivity.this.startActivity(myIntent);
			    return true;
			}
    		
    	}); 	
    }
    
    private void updateRociador() {
    	mRociadorPreference = (PreferenceScreen) findPreference(KEY_ROCIADOR);
    	mRociadorPreference.setSummary(mRociadorPreference.getSharedPreferences().getString(KEY_ROCIADOR, null));
    }
    
    private void buscarRociador() {
    	mRociadorPreference = (PreferenceScreen) findPreference(KEY_ROCIADOR);
    	mRociadorPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// Presenta Rociador
				Intent myIntent = new Intent(PreferencesActivity.this, ListaRociadoresActivity.class);
				PreferencesActivity.this.startActivity(myIntent);
			    return true;
			}
    		
    	}); 	
    }
    
    private void updateBrigada() {
    	mBrigadaPreference = (PreferenceScreen) findPreference(KEY_BRIGADA);
    	mBrigadaPreference.setSummary(mBrigadaPreference.getSharedPreferences().getString(KEY_BRIGADA, null));
    }
    
    private void buscarBrigada() {
    	mBrigadaPreference = (PreferenceScreen) findPreference(KEY_BRIGADA);
    	mBrigadaPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// Presenta Brigada
				Intent myIntent = new Intent(PreferencesActivity.this, ListaBrigadasActivity.class);
				PreferencesActivity.this.startActivity(myIntent);
			    return true;
			}
    		
    	}); 	
    }

    private InputFilter getWhitespaceFilter() {
        InputFilter whitespaceFilter = new InputFilter() {
            @Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                    int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        return whitespaceFilter;
    }


    private InputFilter getReturnFilter() {
        InputFilter returnFilter = new InputFilter() {
            @Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                    int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.getType((source.charAt(i))) == Character.CONTROL) {
                        return "";
                    }
                }
                return null;
            }
        };
        return returnFilter;
    }
    
    /**
     * Let's the user tap the activity icon to go 'home'.
     * Requires setHomeButtonEnabled() in onCreate().
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
    	switch (menuItem.getItemId()) {
        case android.R.id.home:
          finish();
          return true;
    	}
      return (super.onOptionsItemSelected(menuItem));
    }
}

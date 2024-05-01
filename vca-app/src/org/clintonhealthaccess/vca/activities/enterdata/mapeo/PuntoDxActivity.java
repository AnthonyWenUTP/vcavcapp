package org.clintonhealthaccess.vca.activities.enterdata.mapeo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import net.sqlcipher.database.SQLiteException;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;

import org.clintonhealthaccess.vca.activities.mapeo.MenuPuntoDxActivity;
import org.clintonhealthaccess.vca.activities.mapeo.PuntosDxActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.forms.mapeo.PuntoDxForm;
import org.clintonhealthaccess.vca.forms.mapeo.PuntoDxFormLabels;
import org.clintonhealthaccess.vca.preferences.PreferencesActivity;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.DeviceInfo;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.BarcodePage;
import org.clintonhealthaccess.vca.wizard.model.DatePage;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.ModelCallbacks;
import org.clintonhealthaccess.vca.wizard.model.MultipleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.NewDatePage;
import org.clintonhealthaccess.vca.wizard.model.IntegerPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

import org.clintonhealthaccess.vca.wizard.ui.PageFragmentCallbacks;
import org.clintonhealthaccess.vca.wizard.ui.ReviewFragment;
import org.clintonhealthaccess.vca.wizard.ui.StepPagerStrip;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class PuntoDxActivity extends FragmentActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
	private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private boolean mEditingAfterReview;
    private AbstractWizardModel mWizardModel;
    private boolean mConsumePageSelectedEvent;
    private Button mNextButton;
    private Button mPrevButton;
    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;
    private VcaAdapter vcaAdapter;
    private DeviceInfo infoMovil;
    private static Localidad localidad = new Localidad();
    private static PuntoDiagnostico puntodx = new PuntoDiagnostico();
    private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private PuntoDxFormLabels labels;
	private String roles;
	private String mPass;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
        setContentView(R.layout.activity_data_enter);
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		infoMovil = new DeviceInfo(PuntoDxActivity.this);
		localidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
        puntodx = (PuntoDiagnostico) getIntent().getExtras().getSerializable(Constants.PUNTO);
        roles = getIntent().getExtras().getString(Constants.ROLES);
		
        mPass = ((VcaApplication) this.getApplication()).getPassApp();
        mWizardModel = new PuntoDxForm(this,mPass,localidad.getIdent());
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        labels = new PuntoDxFormLabels(); 
        
        try {
            //Abre la base de datos
    		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
    		vcaAdapter.open();
    		Bundle dato = null;
        	Page modifPage;

            if (puntodx != null) {
            	
            	if(puntodx.getLocal()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLocalidad());
                    dato = new Bundle();
                    dato.putString(SIMPLE_DATA_KEY, puntodx.getLocal().getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        
    	        if(tieneValor(puntodx.getClave())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getClave());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, puntodx.getClave());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        
    	        
    	        if(tieneValor(puntodx.getTipo())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTipo());
                    MessageResource catTipo = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + puntodx.getTipo() + "' and " + MainDBConstants.catRoot + "='CAT_TIPOPDX'", null);
                    dato = new Bundle();
                    if(catTipo!=null) dato.putString(SIMPLE_DATA_KEY, catTipo.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        
    	        if(tieneValor(puntodx.getStatus())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getStatus());
                    MessageResource catStatus = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + puntodx.getStatus() + "' and " + MainDBConstants.catRoot + "='CAT_ESTADOPDX'", null);
                    dato = new Bundle();
                    if(catStatus!=null) dato.putString(SIMPLE_DATA_KEY, catStatus.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        if(tieneValor(puntodx.getInfo())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getInfo());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, puntodx.getInfo());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
            }
        }
        catch(Exception e) {
        	Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        	if (vcaAdapter != null)
                vcaAdapter.close();
        	finish();
        }
        
        mWizardModel.registerListener(this);
        
        
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return; 
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.submit_confirm_message)
                                    .setPositiveButton(R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                    	@Override
										public void onClick(DialogInterface arg0, int arg1) {
                                    		saveData();
										}
                                    })
                                    .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                                    	@Override
										public void onClick(DialogInterface arg0, int arg1) {
                                    		createDialog(EXIT);
										}
                                    })
                                    .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "guardar_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });
        
        onPageTreeChangedInitial(); 
    }
    
	@Override
	public void onBackPressed (){
		createDialog(EXIT);
	}

    private void createDialog(int dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(dialog){
            case EXIT:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.exiting));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                    	if (vcaAdapter != null)
                            vcaAdapter.close();
                    	Bundle arguments = new Bundle();
                        Intent i;
                        if(puntodx.getIdent()==null) {
                        	i = new Intent(getApplicationContext(),
                					PuntosDxActivity.class);
                        }
                        else {
                        	i = new Intent(getApplicationContext(),
                        			MenuPuntoDxActivity.class);
                        }
                    	if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
                		if (puntodx!=null) arguments.putSerializable(Constants.PUNTO , puntodx);
                		arguments.putString(Constants.ROLES, roles);
                		i.putExtras(arguments);
            			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            			startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.err_cancel),Toast.LENGTH_LONG);
                        toast.show();
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }
    
    public void onPageTreeChangedInitial() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        if (recalculateCutOffPage()) {
            updateBottomBar();
        }
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }
        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
    	try{
	    	updateModel(page);
	    	updateConstrains();
	        if (recalculateCutOffPage()) {
	        	if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
	            updateBottomBar();
	        }
	        notificarCambios = true;
	    }catch (Exception ex){
	        ex.printStackTrace();
	    }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            String clase = page.getClass().toString();
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }     
            if (!page.getData().isEmpty() && clase.equals("class org.clintonhealthaccess.vca.wizard.model.IntegerPage")) {
            	IntegerPage np = (IntegerPage) page;
            	String valor = np.getData().getString(IntegerPage.SIMPLE_DATA_KEY);
        		if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
        				|| (np.ismValPattern() && !valor.matches(np.getmPattern()))){
        			cutOffPage = i;
        			break;
        		}
            }
            if (!page.getData().isEmpty() && clase.equals("class org.clintonhealthaccess.vca.wizard.model.TextPage")) {
            	TextPage tp = (TextPage) page;
            	if (tp.ismValPattern()) {
            		String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
            		if(!valor.matches(tp.getmPattern())){
            			cutOffPage = i;
            			break;
            		}
            	}
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }
    
    
    public void updateConstrains(){
    	
        
    }
    
    public void updateModel(Page page){
        try{
        	
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void changeStatus(Page page, boolean visible, String hint){
    	String clase = page.getClass().toString();
    	if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage")){
    		SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.BarcodePage")){
    		BarcodePage modifPage = (BarcodePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.LabelPage")){
    		LabelPage modifPage = (LabelPage) page;
            if (hint!=null)
                modifPage.setHint(hint);
            modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.TextPage")){
    		TextPage modifPage = (TextPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.IntegerPage")){
    		IntegerPage modifPage = (IntegerPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.MultipleFixedChoicePage")){
    		MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.DatePage")){
    		DatePage modifPage = (DatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.NewDatePage")){
    		NewDatePage modifPage = (NewDatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    }
    
    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }
    
    public void saveData(){
        try {
        	vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
        	vcaAdapter.open();
        	
            Map<String, String> mapa = mWizardModel.getAnswers();
            //Guarda las respuestas en un bundle
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            
            String ident = infoMovil.getId();
            String idLocalidad = datos.getString(this.getString(R.string.localidadF));
            String clave = datos.getString(this.getString(R.string.clave));
            String tipo = datos.getString(this.getString(R.string.tipo));
            String status = datos.getString(this.getString(R.string.status));
            String info = datos.getString(this.getString(R.string.info));
            
            
            
            if (tieneValor(clave)) {
                puntodx.setClave(clave);
            } else {
            	puntodx.setClave(null);
            }
            
            if (tieneValor(tipo)) {
            	MessageResource catTipo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + tipo + "' and " + MainDBConstants.catRoot + "='CAT_TIPOPDX'", null);
            	if (catTipo!=null) puntodx.setTipo(catTipo.getCatKey());
            } else {
            	puntodx.setTipo(null);
            }
            
            if (tieneValor(status)) {
            	MessageResource catStatus = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + status + "' and " + MainDBConstants.catRoot + "='CAT_ESTADOPDX'", null);
            	if (catStatus!=null) puntodx.setStatus(catStatus.getCatKey());
            } else {
            	puntodx.setStatus(null);
            }
            if (tieneValor(info)) {
                puntodx.setInfo(info);
            } else {
            	puntodx.setInfo(null);
            }

           
            if (puntodx.getRecordDate()==null) puntodx.setRecordDate(new Date());
            puntodx.setRecordUser(username);
            puntodx.setDeviceid(infoMovil.getDeviceId());
            puntodx.setPasive('0');
            if (puntodx.getEstado()==Constants.STATUS_SUBMITTED) {
            	puntodx.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else if (puntodx.getEstado()==Constants.STATUS_NOT_SUBMITTED) {
            	puntodx.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else {
            	puntodx.setEstado(Constants.STATUS_NOT_FINALIZED);
            }
            puntodx.setLocal(vcaAdapter.getLocalidad(MainDBConstants.name + "= '" + idLocalidad + "'", null));
            if(puntodx.getIdent()==null) {
            	puntodx.setIdent(ident);
            	try {
            		vcaAdapter.crearPuntoDiagnostico(puntodx);
            	}catch (SQLiteException exception) {
            		puntodx.setIdent(null);
            	    Toast toast = Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG);
            	    toast.show();
            	    exception.printStackTrace();
            	    return;
            	} 
            }else {
            	vcaAdapter.editarPuntoDiagnostico(puntodx);
            }
            vcaAdapter.close();
            Bundle arguments = new Bundle();
    		if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
    		if (puntodx!=null) arguments.putSerializable(Constants.PUNTO , puntodx);
    		if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
            Intent i;
            i = new Intent(getApplicationContext(),
            		MenuPuntoDxActivity.class);
            i.putExtras(arguments);
    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }catch (Exception ex){
        	Toast toast = Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG);
    	    toast.show();
            ex.printStackTrace();
        }finally {
            if (vcaAdapter != null)
                vcaAdapter.close();
        }
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}

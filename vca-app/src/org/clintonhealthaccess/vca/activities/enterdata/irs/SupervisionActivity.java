package org.clintonhealthaccess.vca.activities.enterdata.irs;

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

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.irs.MenuRociadoActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.forms.irs.SupervisionForm;
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
import org.clintonhealthaccess.vca.wizard.model.NumberPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;
import org.clintonhealthaccess.vca.wizard.ui.PageFragmentCallbacks;
import org.clintonhealthaccess.vca.wizard.ui.ReviewFragment;
import org.clintonhealthaccess.vca.wizard.ui.StepPagerStrip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class SupervisionActivity extends FragmentActivity implements
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
    private static Target meta = new Target();
    private static Supervision mSupervision;
    private DeviceInfo infoMovil;
    
    private String roles;
    private String defaultSupervisor;
    
    private String username;
	private SharedPreferences settings;
    
	
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
        setContentView(R.layout.activity_data_enter);

		
        infoMovil = new DeviceInfo(SupervisionActivity.this);
        
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        
        
        defaultSupervisor =
				settings.getString(PreferencesActivity.KEY_CODE_SUPERVISOR,
						null);
        //Aca se recupera los datos de la meta
        meta = (Target) getIntent().getExtras().getSerializable(Constants.META);
        roles = getIntent().getExtras().getString(Constants.ROLES);
        mSupervision = new Supervision();
        
        String mPass = ((VcaApplication) this.getApplication()).getPassApp();
        mWizardModel = new SupervisionForm(this,mPass,meta.getIdent());
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        
        try {
            //Abre la base de datos
    		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
    		vcaAdapter.open();
    		
    		Bundle dato = null;
        	Page modifPage;
        	
        	if (mSupervision != null) {
        		if(mSupervision.getSupervisor()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Supervisor");
                    Personal supervisor = vcaAdapter.getPersonal(MainDBConstants.ident + "='"+ mSupervision.getSupervisor().getIdent() + "'", null);
                    dato = new Bundle();
                    if(supervisor!=null) dato.putString(SIMPLE_DATA_KEY, supervisor.getCode()+"-"+supervisor.getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        else {
    	        	modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Supervisor");
    	        	Personal supervisor = vcaAdapter.getPersonal(MainDBConstants.ident + "='"+ defaultSupervisor + "'", null);
    	        	dato = new Bundle();
                    if(supervisor!=null) dato.putString(SIMPLE_DATA_KEY, supervisor.getCode()+"-"+supervisor.getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
    	        }
        		if(mSupervision.getSupervisionDate()!=null){
    		        modifPage = (NewDatePage) mWizardModel.findByKey("Fecha de la supervisión");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(mSupervision.getSupervisionDate()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        else {
    	        	modifPage = (NewDatePage) mWizardModel.findByKey("Fecha de la supervisión");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(new Date()));
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
                    	Bundle arguments = new Bundle();
                    	arguments.putSerializable(Constants.LOCALIDAD , meta.getHousehold().getLocal());
                		arguments.putSerializable(Constants.TEMPORADA , meta.getIrsSeason());
                		arguments.putString(Constants.ROLES, roles);
                        Intent i;
                        i = new Intent(getApplicationContext(),
                        		MenuRociadoActivity.class);
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
            if (!page.getData().isEmpty() && clase.equals("class org.clintonhealthaccess.vca.wizard.model.NumberPage")) {
            	NumberPage np = (NumberPage) page;
            	String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
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
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.NumberPage")){
    		NumberPage modifPage = (NumberPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
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
            Map<String, String> mapa = mWizardModel.getAnswers();
            //Guarda las respuestas en un bundle
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            
            String ident = infoMovil.getId();
            mSupervision.setTarget(meta);
            
            String fechaSupervision = datos.getString("Fecha de la supervisión");
            Date supDate = null;
            try {
            	supDate = mDateFormat.parse(fechaSupervision);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
    			toast.show();
    			finish();
    		}          
            mSupervision.setSupervisionDate(supDate);
            
            String[] strRociador = datos.getString("Rociador").split("-");
            Personal rociador = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strRociador[0] +"' and "+ MainDBConstants.name + "='"+ strRociador[1] +"'", null);
            if (tieneValor(rociador.getIdent())) {
                mSupervision.setRociador(rociador);
            } else {
            	mSupervision.setRociador(null);
            }

            
            String[] strSupervisor = datos.getString("Supervisor").split("-");
            Personal supervisor = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strSupervisor[0] +"' and "+ MainDBConstants.name + "='"+ strSupervisor[1] +"'", null);
            if (tieneValor(supervisor.getIdent())) {
                mSupervision.setSupervisor(supervisor);
            } else {
            	mSupervision.setSupervisor(null);
            }
            
            String usoEqProt = datos.getString("El equpo de protección está bien llevado");
            
            if (tieneValor(usoEqProt)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + usoEqProt + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setUsoEqProt(catSiNo.getCatKey());
            } else {
            	mSupervision.setUsoEqProt(null);
            }
            
            String eqProtBien = datos.getString("El equipo de protección está en buen estado");
            
            if (tieneValor(eqProtBien)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + eqProtBien + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setEqProtBien(catSiNo.getCatKey());
            } else {
            	mSupervision.setEqProtBien(null);
            }
            
            String numIden = datos.getString("Pone su número de identificación y lo hace correctamente");
            
            if (tieneValor(numIden)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + numIden + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setNumIden(catSiNo.getCatKey());
            } else {
            	mSupervision.setNumIden(null);
            }
            
            
            String aguaOp = datos.getString("Solicita agua oportunamente");
            
            if (tieneValor(aguaOp)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + aguaOp + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setAguaOp(catSiNo.getCatKey());
            } else {
            	mSupervision.setAguaOp(null);
            }            
            
            String prepViv = datos.getString("Comprueba la preparación de la vivienda");
            
            if (tieneValor(prepViv)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + prepViv + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setPrepViv(catSiNo.getCatKey());
            } else {
            	mSupervision.setPrepViv(null);
            }
            
            String coopPrepViv = datos.getString("Coopera en la preparación de la vivienda");
            
            if (tieneValor(coopPrepViv)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + coopPrepViv + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setCoopPrepViv(catSiNo.getCatKey());
            } else {
            	mSupervision.setCoopPrepViv(null);
            }
            
            String mezcla = datos.getString("Mezcla");
            
            if (tieneValor(mezcla)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + mezcla + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setMezcla(catSiNo.getCatKey());
            } else {
            	mSupervision.setMezcla(null);
            }

            String aguaAdec = datos.getString("Aplica la cantidad adecuada de agua");
            
            if (tieneValor(aguaAdec)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + aguaAdec + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setAguaAdec(catSiNo.getCatKey());
            } else {
            	mSupervision.setAguaAdec(null);
            }
            String mezclaPrep = datos.getString("Prepara bien la mezcla");
            
            if (tieneValor(mezclaPrep)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + mezclaPrep + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setMezclaPrep(catSiNo.getCatKey());
            } else {
            	mSupervision.setMezclaPrep(null);
            }
            String agitaBomba = datos.getString("Agita correctamente la bomba");
            
            if (tieneValor(agitaBomba)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + agitaBomba + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setAgitaBomba(catSiNo.getCatKey());
            } else {
            	mSupervision.setAgitaBomba(null);
            }
            String bombaCerrada = datos.getString("La bomba está cerrada correctamente");
            if (tieneValor(bombaCerrada)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + bombaCerrada + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setBombaCerrada(catSiNo.getCatKey());
            } else {
            	mSupervision.setBombaCerrada(null);
            }
            
            String bombaPresion = datos.getString("La bomba tiene la presión correcta (55psi)");
            if (tieneValor(bombaPresion)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + bombaPresion + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setBombaPresion(catSiNo.getCatKey());
            } else {
            	mSupervision.setBombaPresion(null);
            }
            String compruebaBomba = datos.getString("Comprueba su funcionamiento antes de rociar");
            if (tieneValor(compruebaBomba)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + compruebaBomba + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setCompruebaBomba(catSiNo.getCatKey());
            } else {
            	mSupervision.setCompruebaBomba(null);
            }
            String colocApropiada = datos.getString("Se coloca adecuadamente");
            if (tieneValor(colocApropiada)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + colocApropiada + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setColocApropiada(catSiNo.getCatKey());
            } else {
            	mSupervision.setColocApropiada(null);
            }
            String distApropiada = datos.getString("Calcula la distancia adecuada");
            if (tieneValor(distApropiada)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + distApropiada + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setDistApropiada(catSiNo.getCatKey());
            } else {
            	mSupervision.setDistApropiada(null);
            }
            String distBoquilla = datos.getString("Mantiene la distancia de la boquilla al momento de rociar");
            if (tieneValor(distBoquilla)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + distBoquilla + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setDistBoquilla(catSiNo.getCatKey());
            } else {
            	mSupervision.setDistBoquilla(null);
            }

            String pasoFrente = datos.getString("Da un paso al frente en las superficies altas");
            if (tieneValor(pasoFrente)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + pasoFrente + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setPasoFrente(catSiNo.getCatKey());
            } else {
            	mSupervision.setPasoFrente(null);
            }
            String mantRitmo = datos.getString("Mantiene el ritmo al rociar");
            if (tieneValor(mantRitmo)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + mantRitmo + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setMantRitmo(catSiNo.getCatKey());
            } else {
            	mSupervision.setMantRitmo(null);
            }
            String metConteo = datos.getString("Utiliza el método del conteo");
            if (tieneValor(metConteo)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + metConteo + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setMetConteo(catSiNo.getCatKey());
            } else {
            	mSupervision.setMetConteo(null);
            }
            String velocSuperficies = datos.getString("Mantiene volocidad adecuada en las superficies");
            if (tieneValor(velocSuperficies)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + velocSuperficies + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setVelocSuperficies(catSiNo.getCatKey());
            } else {
            	mSupervision.setVelocSuperficies(null);
            }
            String supFajas = datos.getString("Superpone las fajas correctamente");
            if (tieneValor(supFajas)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + supFajas + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setSupFajas(catSiNo.getCatKey());
            } else {
            	mSupervision.setSupFajas(null);
            }
            String pasosLaterales = datos.getString("Da los pasos laterales de 70 cm aproximadamente");
            if (tieneValor(pasosLaterales)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + pasosLaterales + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setPasosLaterales(catSiNo.getCatKey());
            } else {
            	mSupervision.setPasosLaterales(null);
            }
            String salvarObstaculos = datos.getString("Tiene habilidad para salvar obstáculos");
            if (tieneValor(salvarObstaculos)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + salvarObstaculos + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setSalvarObstaculos(catSiNo.getCatKey());
            } else {
            	mSupervision.setSalvarObstaculos(null);
            }
            String bienRociado = datos.getString("Rocía bien las superficies");
            if (tieneValor(bienRociado)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + bienRociado + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setBienRociado(catSiNo.getCatKey());
            } else {
            	mSupervision.setBienRociado(null);
            }
            String supInvertidas = datos.getString("Rocía las superficies invertidas");
            if (tieneValor(supInvertidas)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + supInvertidas + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setSupInvertidas(catSiNo.getCatKey());
            } else {
            	mSupervision.setSupInvertidas(null);
            }
            String objPiso = datos.getString("Rocía objetos en el piso");
            if (tieneValor(objPiso)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + objPiso + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setObjPiso(catSiNo.getCatKey());
            } else {
            	mSupervision.setObjPiso(null);
            }
            String reportaConsumoAprop = datos.getString("La cantidad de cargas reportadas corresponde a la cantidad de producto utilizado");
            if (tieneValor(reportaConsumoAprop)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + reportaConsumoAprop + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setReportaConsumoAprop(catSiNo.getCatKey());
            } else {
            	mSupervision.setReportaConsumoAprop(null);
            }
            String transEqAprop = datos.getString("Transporta el equipo correctamente");
            if (tieneValor(transEqAprop)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + transEqAprop + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setTransEqAprop(catSiNo.getCatKey());
            } else {
            	mSupervision.setTransEqAprop(null);
            }
            String eqCompleto = datos.getString("Lleva equipo completo");
            if (tieneValor(eqCompleto)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + eqCompleto + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setEqCompleto(catSiNo.getCatKey());
            } else {
            	mSupervision.setEqCompleto(null);
            }
            String cuidaMatEq = datos.getString("Cuida sus materiales y equipos");
            if (tieneValor(cuidaMatEq)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + cuidaMatEq + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setCuidaMatEq(catSiNo.getCatKey());
            } else {
            	mSupervision.setCuidaMatEq(null);
            }
            String buenAspPersonal = datos.getString("Su aspecto personal es bueno");
            if (tieneValor(buenAspPersonal)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + buenAspPersonal + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setBuenAspPersonal(catSiNo.getCatKey());
            } else {
            	mSupervision.setBuenAspPersonal(null);
            }

            String cumpleInstrucciones = datos.getString("Cumple con las instrucciones que recibe");
            if (tieneValor(cumpleInstrucciones)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + cumpleInstrucciones + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setCumpleInstrucciones(catSiNo.getCatKey());
            } else {
            	mSupervision.setCumpleInstrucciones(null);
            }
            String aceptaSuperv = datos.getString("Acepta la supervisión");
            if (tieneValor(aceptaSuperv)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + aceptaSuperv + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setAceptaSuperv(catSiNo.getCatKey());
            } else {
            	mSupervision.setAceptaSuperv(null);
            }
            String respetuoso = datos.getString("Es respetuoso con sus compañeros, superiores y habitantes de las viviendas");
            if (tieneValor(respetuoso)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + respetuoso + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setRespetuoso(catSiNo.getCatKey());
            } else {
            	mSupervision.setRespetuoso(null);
            }
            String camp = datos.getString("Realizó charlas educativas");
            if (tieneValor(camp)) {
            	MessageResource catSiNo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + camp + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSiNo!=null) mSupervision.setCamp(catSiNo.getCatKey());
            } else {
            	mSupervision.setCamp(null);
            }
            
            String obs = datos.getString("Observaciones");
            
            if (tieneValor(obs)) {
            	mSupervision.setObs(obs);
            } else {
            	mSupervision.setObs(null);
            }
            
            if (mSupervision.getRecordDate()==null) mSupervision.setRecordDate(new Date());
            
            if (mSupervision.getRecordUser()==null) mSupervision.setRecordUser(username);
            mSupervision.setDeviceid(infoMovil.getDeviceId());
            mSupervision.setPasive('0');
            mSupervision.setEstado(Constants.STATUS_NOT_SUBMITTED);
            
            
            if(mSupervision.getIdent()==null) {
            	mSupervision.setIdent(ident);
            	vcaAdapter.crearSupervision(mSupervision);
            }else {
            	vcaAdapter.editarSupervision(mSupervision);
            }
            
            
            vcaAdapter.close();
            Bundle arguments = new Bundle();
    		
    		
    		arguments.putSerializable(Constants.LOCALIDAD , meta.getHousehold().getLocal());
    		arguments.putSerializable(Constants.TEMPORADA , meta.getIrsSeason());
    		arguments.putString(Constants.ROLES, roles);
            Intent i;
            i = new Intent(getApplicationContext(),
            		MenuRociadoActivity.class);
            i.putExtras(arguments);
    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }catch (Exception ex){
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

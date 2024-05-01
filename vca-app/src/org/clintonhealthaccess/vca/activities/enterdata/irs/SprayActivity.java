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
import org.clintonhealthaccess.vca.activities.irs.MenuRociadoCasaActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.forms.HouseholdFormLabels;
import org.clintonhealthaccess.vca.forms.irs.SprayForm;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class SprayActivity extends FragmentActivity implements
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
    private static Visit mVisit;
    private static Household vivienda = new Household();
    int totalVisitas = 0;
    private DeviceInfo infoMovil;
    private String roles;
    
    private String defaultBrigada;
    private String defaultSupervisor;
    private String username;
	private SharedPreferences settings;
    
	
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private MessageResource catMotivoRenuenteNV;
	private MessageResource catMotivoOtroNV;
	private MessageResource catMotivoOtroRenuente;
	
	private HouseholdFormLabels labels;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
        setContentView(R.layout.activity_data_enter);

		
        infoMovil = new DeviceInfo(SprayActivity.this);
        
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        
        
        defaultBrigada =
				settings.getString(PreferencesActivity.KEY_CODE_BRIGADA,
						null);
        defaultSupervisor =
				settings.getString(PreferencesActivity.KEY_CODE_SUPERVISOR,
						null);
        //Aca se recupera los datos de la meta
        meta = (Target) getIntent().getExtras().getSerializable(Constants.META);
        roles = getIntent().getExtras().getString(Constants.ROLES);
        mVisit = new Visit();
        vivienda = meta.getHousehold();
        
        String mPass = ((VcaApplication) this.getApplication()).getPassApp();
        mWizardModel = new SprayForm(this,mPass,meta.getIdent());
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        
        labels = new HouseholdFormLabels(); 
        
        try {
            //Abre la base de datos
    		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
    		vcaAdapter.open();
    		catMotivoRenuenteNV = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='RELUCT' and " + MainDBConstants.catRoot + "='CAT_NO_VISIT'", null);
    		catMotivoOtroNV = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='OTHER' and " + MainDBConstants.catRoot + "='CAT_NO_VISIT'", null);
    		catMotivoOtroRenuente = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='OTHER' and " + MainDBConstants.catRoot + "='CAT_RELUCTANT'", null);
    		totalVisitas = vcaAdapter.getNumeroRegistros(MainDBConstants.VISITAS_TABLE, 
    									MainDBConstants.target + " = '"+meta.getIdent()+"' and "+MainDBConstants.activity+"='NOTICE'");
    		Bundle dato = null;
        	Page modifPage;
        	
        	if (mVisit != null) {
        		if(mVisit.getVisitDate()!=null){
    		        modifPage = (NewDatePage) mWizardModel.findByKey("Fecha de la visita");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(mVisit.getVisitDate()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        else {
    	        	modifPage = (NewDatePage) mWizardModel.findByKey("Fecha de la visita");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(new Date()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
        		if(mVisit.getVisitor()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Persona que visita");
                    Personal rociador = vcaAdapter.getPersonal(MainDBConstants.ident + "='"+ mVisit.getVisitor().getIdent() + "'", null);
                    dato = new Bundle();
                    if(rociador!=null) dato.putString(SIMPLE_DATA_KEY, rociador.getCode()+"-"+rociador.getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        
        		if(mVisit.getBrigada()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Brigada");
                    Brigada brigada = vcaAdapter.getBrigada(MainDBConstants.ident + "='"+ mVisit.getBrigada().getIdent() + "'", null);
                    dato = new Bundle();
                    if(brigada!=null) dato.putString(SIMPLE_DATA_KEY, brigada.getCode()+"-"+brigada.getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        else {
    	        	modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Brigada");
    	        	Brigada brigada = vcaAdapter.getBrigada(MainDBConstants.ident + "='"+ defaultBrigada + "'", null);
    	        	dato = new Bundle();
                    if(brigada!=null) dato.putString(SIMPLE_DATA_KEY, brigada.getCode()+"-"+brigada.getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
    	        }
        		if(mVisit.getSupervisor()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Supervisor");
                    Personal supervisor = vcaAdapter.getPersonal(MainDBConstants.ident + "='"+ mVisit.getSupervisor().getIdent() + "'", null);
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
        		if(tieneValor(mVisit.getCompVisit())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Se logró completar la visita");
                    MessageResource catSino = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + mVisit.getCompVisit() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    dato = new Bundle();
                    if(catSino!=null) dato.putString(SIMPLE_DATA_KEY, catSino.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
        		if(tieneValor(mVisit.getReasonNoVisit())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Porqué no se logró completar la visita");
                    MessageResource catRNV = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + mVisit.getReasonNoVisit() + "' and " + MainDBConstants.catRoot + "='CAT_NO_VISIT'", null);
                    dato = new Bundle();
                    if(catRNV!=null) dato.putString(SIMPLE_DATA_KEY, catRNV.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
        		if(tieneValor(mVisit.getReasonNoVisitOther())){
    	        	modifPage = (TextPage) mWizardModel.findByKey("Motivo no se logro completar, Otro");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mVisit.getReasonNoVisitOther());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
        		if(tieneValor(mVisit.getReasonReluctant())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Motivo renuente");
                    MessageResource catRen = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + mVisit.getReasonReluctant() + "' and " + MainDBConstants.catRoot + "='CAT_RELUCTANT'", null);
                    dato = new Bundle();
                    if(catRen!=null) dato.putString(SIMPLE_DATA_KEY, catRen.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
        		if(tieneValor(mVisit.getReasonReluctantOther())){
    	        	modifPage = (TextPage) mWizardModel.findByKey("Motivo renuente otros");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mVisit.getReasonReluctantOther());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
        		if(tieneValor(mVisit.getModCasa())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Desea modificar los datos de la vivienda?");
                    MessageResource catSino = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + mVisit.getModCasa() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    dato = new Bundle();
                    if(catSino!=null) dato.putString(SIMPLE_DATA_KEY, catSino.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
        		if(mVisit.getPersonasCharlas()!=null){
    	        	modifPage = (NumberPage) mWizardModel.findByKey("Personas charla");
    	            dato = new Bundle();
    	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(mVisit.getPersonasCharlas()));
    	            modifPage.resetData(dato);
    	            modifPage.setmVisible(true);
    	        }
        		if(tieneValor(mVisit.getObs())){
    	        	modifPage = (TextPage) mWizardModel.findByKey("Observaciones");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, vivienda.getCode());
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
        	if (page.getTitle().equals("Se logró completar la visita")) {
                Boolean completa = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.SI);
                if (!completa) {
                	changeStatus(mWizardModel.findByKey("Porqué no se logró completar la visita"), true, null);
                	changeStatus(mWizardModel.findByKey("Asignar rociado a"), false, null);
                	changeStatus(mWizardModel.findByKey("Personas charla"), false, null);
                	changeStatus(mWizardModel.findByKey("Desea modificar los datos de la vivienda?"), false, null);
                }
                else {
                	changeStatus(mWizardModel.findByKey("Porqué no se logró completar la visita"), false, null);
                	changeStatus(mWizardModel.findByKey("Motivo no se logro completar, Otro"), false, null);
                	changeStatus(mWizardModel.findByKey("Motivo renuente"), false, null);
                	changeStatus(mWizardModel.findByKey("Motivo renuente otros"), false, null);
                	changeStatus(mWizardModel.findByKey("Asignar rociado a"), true, null);
                	changeStatus(mWizardModel.findByKey("Desea modificar los datos de la vivienda?"), true, null);
                	changeStatus(mWizardModel.findByKey("Personas charla"), true, null);
                }
                	
                notificarCambios = false;
                onPageTreeChanged();
            }
        	
        	if (page.getTitle().equals("Porqué no se logró completar la visita")) {
        		changeStatus(mWizardModel.findByKey("Motivo no se logro completar, Otro"), false, null);
            	changeStatus(mWizardModel.findByKey("Motivo renuente"), false, null);
            	changeStatus(mWizardModel.findByKey("Motivo renuente otros"), false, null);
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(catMotivoOtroNV.getSpanish())) {
                	changeStatus(mWizardModel.findByKey("Motivo no se logro completar, Otro"), true, null);
                }
                else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(catMotivoRenuenteNV.getSpanish())) {
                	changeStatus(mWizardModel.findByKey("Motivo renuente"), true, null);
                }
                	
                notificarCambios = false;
                onPageTreeChanged();
            }
        	
        	
        	if (page.getTitle().equals("Desea modificar los datos de la vivienda?")) {
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.SI)) {
                	Bundle dato;
                	Page modifPage;
                	if (vivienda != null) {
        	        	if(tieneValor(vivienda.getCode())){
        	        		dato = new Bundle();
        		        	dato.putString(SIMPLE_DATA_KEY, vivienda.getCode());
        		        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCode());
        		        	modifPage.resetData(dato);
        		        	modifPage.setmVisible(true);
        		        }
        	        	if(tieneValor(vivienda.getInhabited())){
                            modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getInhabited());
                            MessageResource catHab = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + vivienda.getInhabited() + "' and " + MainDBConstants.catRoot + "='CAT_HAB'", null);
                            dato = new Bundle();
                            if(catHab!=null) dato.putString(SIMPLE_DATA_KEY, catHab.getSpanish());
                            modifPage.resetData(dato);
                            modifPage.setmVisible(true);
                        }
        	        	if(tieneValor(vivienda.getOwnerName())){
        	        		dato = new Bundle();
        		        	dato.putString(SIMPLE_DATA_KEY, vivienda.getOwnerName());
        		        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOwnerName());
        		        	modifPage.resetData(dato);
        		        	modifPage.setmVisible(true);
        		        }
        	        	if(vivienda.getHabitants()!=null){
        			        if(vivienda.getHabitants()>0){
        			        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getHabitants());
        			            dato = new Bundle();
        			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getHabitants()));
        			            modifPage.resetData(dato);
        			            modifPage.setmVisible(true);
        			        }
            	        }
        	        	if(tieneValor(vivienda.getMaterial())){
                            modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMaterial());
                            MessageResource catMat = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + vivienda.getMaterial() + "' and " + MainDBConstants.catRoot + "='CAT_MAT'", null);
                            dato = new Bundle();
                            if(catMat!=null) dato.putString(SIMPLE_DATA_KEY, catMat.getSpanish());
                            modifPage.resetData(dato);
                            modifPage.setmVisible(true);
                        }
        	        	if(vivienda.getSprRooms()!=null){
            	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getSprRooms());
            	            dato = new Bundle();
            	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getSprRooms()));
            	            modifPage.resetData(dato);
            	            modifPage.setmVisible(true);
            	        }
            	        if(vivienda.getNoSprooms()!=null){
            	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getNoSprooms());
            	            dato = new Bundle();
            	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getNoSprooms()));
            	            modifPage.resetData(dato);
            	            modifPage.setmVisible(true);
            	        }
            	        if(vivienda.getRooms()!=null){
            	        	modifPage = (LabelPage) mWizardModel.findByKey(labels.getRooms());
            	        	modifPage.setHint(vivienda.getRooms().toString());
            	            modifPage.setmVisible(true);
            	        }
            	        if(vivienda.getSleep()!=null){
            	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getSleep());
            	            dato = new Bundle();
            	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getSleep()));
            	            modifPage.resetData(dato);
            	            modifPage.setmVisible(true);
            	        }
            	        if(vivienda.getNumNets()!=null){
            	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumNets());
            	            dato = new Bundle();
            	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getNumNets()));
            	            modifPage.resetData(dato);
            	            modifPage.setmVisible(true);
            	        }
            	        if(tieneValor(vivienda.getNoSproomsReasons())){
                            modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getNoSproomsReasons());
                            String codRazones = vivienda.getNoSproomsReasons().replaceAll("," , "','");
                            List<String> descRazones = new ArrayList<String>();
                            List<MessageResource> msArticulos = vcaAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codRazones + "') and " + MainDBConstants.catRoot + "='CAT_RNR'", null);
                            for(MessageResource ms : msArticulos){
                                descRazones.add(ms.getSpanish());
                            }
                            dato = new Bundle();
                            dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descRazones);
                            modifPage.resetData(dato);
                            modifPage.setmVisible(true);
                        }
                	}
                }
                else {
                	changeStatus(mWizardModel.findByKey(labels.getCode()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getInhabited()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSleep()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNumNets()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSproomsReasons()), false, null);
                }
                	
                notificarCambios = false;
                onPageTreeChanged();
            }
        	if (page.getTitle().equals(labels.getInhabited())) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.SI)) {
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSleep()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getNumNets()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), true, null);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO)) {
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSleep()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNumNets()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), true, null);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.CERRADA)) {
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSleep()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNumNets()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSproomsReasons()), false, null);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.RENUENTE)) {
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSleep()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNumNets()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSproomsReasons()), false, null);
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
        	
        	if (page.getTitle().equals(labels.getSprRooms())) {
            	Integer cuartos = 0;
            	Page noSprooms = mWizardModel.findByKey(labels.getNoSprooms()); 
            	String valorNoSpr = noSprooms.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorSpr = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorSpr != null && !valorSpr.isEmpty() && valorNoSpr != null && !valorNoSpr.isEmpty()) {
                	cuartos = Integer.valueOf(valorSpr)+Integer.valueOf(valorNoSpr);
                }
                changeStatus(mWizardModel.findByKey(labels.getRooms()), true, String.valueOf(cuartos));
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getNoSprooms())) {
            	Integer cuartos = 0;
            	Page sprRooms = mWizardModel.findByKey(labels.getSprRooms()); 
            	String valorSpr = sprRooms.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorNoSpr = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorSpr != null && !valorSpr.isEmpty() && valorNoSpr != null && !valorNoSpr.isEmpty()) {
                	cuartos = Integer.valueOf(valorSpr)+Integer.valueOf(valorNoSpr);
                	if(Integer.valueOf(valorNoSpr)>0) {
                    	changeStatus(mWizardModel.findByKey(labels.getNoSproomsReasons()), true, null);
                    	notificarCambios = false;
                    }
                	else {
                		changeStatus(mWizardModel.findByKey(labels.getNoSproomsReasons()), false, null);
                		notificarCambios = false;
                	}
                }
                changeStatus(mWizardModel.findByKey(labels.getRooms()), true, String.valueOf(cuartos));
                notificarCambios = false;
                onPageTreeChanged();
            }
        	
        	if (page.getTitle().equals("Motivo renuente")) {
            	changeStatus(mWizardModel.findByKey("Motivo renuente otros"), false, null);
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(catMotivoOtroRenuente.getSpanish())) {
                	changeStatus(mWizardModel.findByKey("Motivo renuente otros"), true, null);
                }
                	
                notificarCambios = false;
                onPageTreeChanged();
            }
        	
            
            
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
            
            String code = datos.getString(this.getString(R.string.codeHouse));
            String inhabited = datos.getString(this.getString(R.string.inhabited));
            String ownerName = datos.getString(this.getString(R.string.ownerName));
            String habitants = datos.getString(this.getString(R.string.habitants));
            String material = datos.getString(this.getString(R.string.material));
            String sprRooms = datos.getString(this.getString(R.string.sprRooms));
            String noSprooms = datos.getString(this.getString(R.string.noSprooms));
            String noSproomsReasons = datos.getString(this.getString(R.string.noSproomsReasons));
            String sleep = datos.getString(this.getString(R.string.sleep));
            String numNets = datos.getString(this.getString(R.string.numNets));
            
            String ident = infoMovil.getId();
            mVisit.setTarget(meta);
            if(totalVisitas==0) {
            	mVisit.setVisit("1");
            }else {
            	mVisit.setVisit("2");
            }
            mVisit.setActivity("NOTICE");
            String fechaVisita = datos.getString("Fecha de la visita");
            Date visitDate = null;
            try {
            	visitDate = mDateFormat.parse(fechaVisita);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
    			toast.show();
    			finish();
    		}          
            mVisit.setVisitDate(visitDate);
            
            String[] strSentinel = datos.getString("Persona que visita").split("-");
            Personal centinela = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strSentinel[0] +"' and "+ MainDBConstants.name + "='"+ strSentinel[1] +"'", null);
            if (tieneValor(centinela.getIdent())) {
                mVisit.setVisitor(centinela);
            } else {
            	mVisit.setVisitor(null);
            }
            
            String[] strBrigada = datos.getString("Brigada").split("-");
            Brigada brigada = vcaAdapter.getBrigada(MainDBConstants.code + "='"+ strBrigada[0] +"' and "+ MainDBConstants.name + "='"+ strBrigada[1] +"'", null);
            if (tieneValor(brigada.getIdent())) {
                mVisit.setBrigada(brigada);
            } else {
            	mVisit.setBrigada(null);
            }
            
            String[] strSupervisor = datos.getString("Supervisor").split("-");
            Personal supervisor = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strSupervisor[0] +"' and "+ MainDBConstants.name + "='"+ strSupervisor[1] +"'", null);
            if (tieneValor(supervisor.getIdent())) {
                mVisit.setSupervisor(supervisor);
            } else {
            	mVisit.setSupervisor(null);
            }
            
            String compVisit = datos.getString("Se logró completar la visita");
            
            if (tieneValor(compVisit)) {
            	MessageResource catCompVisit = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + compVisit + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catCompVisit!=null) mVisit.setCompVisit(catCompVisit.getCatKey());
            } else {
            	mVisit.setCompVisit(null);
            }
            
            String cambiaCasa = datos.getString("Desea modificar los datos de la vivienda?");
            
            if (tieneValor(cambiaCasa)) {
            	MessageResource catCambiaCasa = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + cambiaCasa + "' and " + MainDBConstants.catRoot + "='CAT_HAB'", null);
            	if (catCambiaCasa!=null) mVisit.setModCasa(catCambiaCasa.getCatKey());
            } else {
            	mVisit.setModCasa(null);
            }
            
            String reasonNoVisit = datos.getString("Porqué no se logró completar la visita");
            
            if (tieneValor(reasonNoVisit)) {
            	MessageResource catreasonNoVisit = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + reasonNoVisit + "' and " + MainDBConstants.catRoot + "='CAT_NO_VISIT'", null);
            	if (catreasonNoVisit!=null) mVisit.setReasonNoVisit(catreasonNoVisit.getCatKey());
            } else {
            	mVisit.setReasonNoVisit(null);
            }
            
            String reasonNoVisitOther = datos.getString("Motivo no se logro completar, Otro");
            
            if (tieneValor(reasonNoVisitOther)) {
            	mVisit.setReasonNoVisitOther(reasonNoVisitOther);
            } else {
            	mVisit.setReasonNoVisitOther(null);
            }
            
            String reasonReluctant = datos.getString("Motivo renuente");
            
            if (tieneValor(reasonReluctant)) {
            	MessageResource catreasonReluctant = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + reasonReluctant + "' and " + MainDBConstants.catRoot + "='CAT_RELUCTANT'", null);
            	if (catreasonReluctant!=null) mVisit.setReasonReluctant(catreasonReluctant.getCatKey());
            } else {
            	mVisit.setReasonReluctant(null);
            }
            
            String reasonReluctantOther = datos.getString("Motivo renuente otros");
            
            if (tieneValor(reasonReluctantOther)) {
            	mVisit.setReasonReluctantOther(reasonReluctantOther);
            } else {
            	mVisit.setReasonReluctantOther(null);
            }
            
            
            String personasCharlas = datos.getString("Personas charla");
            
            if (tieneValor(personasCharlas)) {
            	mVisit.setPersonasCharlas(Integer.valueOf(personasCharlas));
            } else {
            	mVisit.setPersonasCharlas(null);
            }
            
            String obs = datos.getString("Observaciones");
            
            if (tieneValor(obs)) {
            	mVisit.setObs(obs);
            } else {
            	mVisit.setObs(null);
            }
            
            if (mVisit.getRecordDate()==null) mVisit.setRecordDate(new Date());
            
            if (mVisit.getRecordUser()==null) mVisit.setRecordUser(username);
            mVisit.setDeviceid(infoMovil.getDeviceId());
            mVisit.setPasive('0');
            mVisit.setEstado(Constants.STATUS_NOT_SUBMITTED);
            
            
            if(mVisit.getIdent()==null) {
            	mVisit.setIdent(ident);
            	vcaAdapter.crearVisit(mVisit);
            }else {
            	vcaAdapter.editarVisit(mVisit);
            }
            
            if(mVisit.getModCasa()!= null && mVisit.getModCasa().equals("1")) {
            	if (tieneValor(code)) {
                    vivienda.setCode(code);
                } else {
                	vivienda.setCode(null);
                }
            	if (tieneValor(inhabited)) {
                	MessageResource catHab = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + inhabited + "' and " + MainDBConstants.catRoot + "='CAT_HAB'", null);
                	if (catHab!=null) vivienda.setInhabited(catHab.getCatKey());
                } else {
                	vivienda.setInhabited(null);
                }
            	if (tieneValor(ownerName)) {
                    vivienda.setOwnerName(ownerName);
                } else {
                	vivienda.setOwnerName(null);
                }
                
                if (tieneValor(habitants)) {
                    vivienda.setHabitants(Integer.valueOf(habitants));
                } else {
                	vivienda.setHabitants(null);
                }
                if (tieneValor(material)) {
                	MessageResource catMaterial = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + material + "' and " + MainDBConstants.catRoot + "='CAT_MAT'", null);
                	if (catMaterial!=null) vivienda.setMaterial(catMaterial.getCatKey());
                } else {
                	vivienda.setMaterial(null);
                }
                
                if (tieneValor(sprRooms)) {
                    vivienda.setSprRooms(Integer.valueOf(sprRooms));
                } else {
                	vivienda.setSprRooms(null);
                }
                
                if (tieneValor(sleep)) {
                    vivienda.setSleep(Integer.valueOf(sleep));
                } else {
                	vivienda.setSleep(null);
                }
                
                if (tieneValor(numNets)) {
                    vivienda.setNumNets(Integer.valueOf(numNets));
                } else {
                	vivienda.setNumNets(null);
                }
                
                if (tieneValor(noSprooms)) {
                    vivienda.setNoSprooms(Integer.valueOf(noSprooms));
                } else {
                	vivienda.setNoSprooms(null);
                }
                
                if (tieneValor(sprRooms) & tieneValor(noSprooms)) {
                    vivienda.setRooms(Integer.valueOf(sprRooms)+Integer.valueOf(noSprooms));
                }
                else {
                	vivienda.setRooms(null);
                }
                
                if (tieneValor(noSproomsReasons)) {
                    String keysRazones = "";
                    noSproomsReasons = noSproomsReasons.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                    List<MessageResource> msArticulos = vcaAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + noSproomsReasons + "') and "
                            + MainDBConstants.catRoot + "='CAT_RNR'", null);
                    for(MessageResource ms : msArticulos) {
                        keysRazones += ms.getCatKey() + ",";
                    }
                    if (!keysRazones.isEmpty())
                        keysRazones = keysRazones.substring(0, keysRazones.length() - 1);
                    vivienda.setNoSproomsReasons(keysRazones);
                } else {
                	vivienda.setNoSproomsReasons(null);
                }
                vivienda.setEstado(Constants.STATUS_NOT_SUBMITTED);
                vcaAdapter.editarHousehold(vivienda);
            }
            
            if (vivienda.getSprRooms()>0) {
	            if(mVisit.getCompVisit().equals("1")) {
	            	meta.setSprayStatus("PENDING");
	            	String[] strRociador = datos.getString("Asignar rociado a").split("-");
	                Personal rociador = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strRociador[0] +"' and "+ MainDBConstants.name + "='"+ strRociador[1] +"'", null);
	                if (tieneValor(rociador.getIdent())) {
	                    meta.setAssignedTo(rociador);
	                } else {
	                	meta.setAssignedTo(null);
	                }
	            }
	            else if(mVisit.getReasonNoVisit().equals("RELUCT")) {
	            	meta.setSprayStatus("RELUCT");
	            }
	            else if(mVisit.getReasonNoVisit().equals("CLOSED")) {
	            	meta.setSprayStatus("CLOSED");
	            }
	            else if(mVisit.getReasonNoVisit().equals("DESTRO")) {
	            	meta.setSprayStatus("DROPPED");
	            }
	            else {
	            	meta.setSprayStatus("NOTVIS");
	            }
            }
            else {
            	meta.setSprayStatus("DROPPED");
            }
            meta.setDeviceid(infoMovil.getDeviceId());
            meta.setPasive('0');
            meta.setEstado(Constants.STATUS_NOT_SUBMITTED);
            vcaAdapter.editarTarget(meta);
            MessageResource catEstado = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + meta.getSprayStatus() + "' and " + MainDBConstants.catRoot + "='CAT_STATUS'", null);
            if(catEstado!= null) meta.setSprayStatus(catEstado.getSpanish());
            vcaAdapter.close();
            Bundle arguments = new Bundle();
    		
    		
    		arguments.putSerializable(Constants.LOCALIDAD , meta.getHousehold().getLocal());
    		arguments.putSerializable(Constants.TEMPORADA , meta.getIrsSeason());
    		arguments.putString(Constants.ROLES, roles);
    		if (meta!=null) arguments.putSerializable(Constants.META , meta);
            Intent i;
            i = new Intent(getApplicationContext(),
            		MenuRociadoCasaActivity.class);
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

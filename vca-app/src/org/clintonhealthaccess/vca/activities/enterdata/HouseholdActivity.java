package org.clintonhealthaccess.vca.activities.enterdata;

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
import org.clintonhealthaccess.vca.activities.CensoActivity;
import org.clintonhealthaccess.vca.activities.MenuCensoCasaActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.forms.HouseholdForm;
import org.clintonhealthaccess.vca.forms.HouseholdFormLabels;
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
import org.clintonhealthaccess.vca.wizard.model.IntegerPage;
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


public class HouseholdActivity extends FragmentActivity implements
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
    private static Household vivienda = new Household();
    private String username;
    private String defaultCensador;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private HouseholdFormLabels labels;
	
	public String constraintMessage="";
	private Integer masculinos;
	private Integer femeninos;

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
		defaultCensador =
				settings.getString(PreferencesActivity.KEY_CODE_CENSADOR,
						null);
		infoMovil = new DeviceInfo(HouseholdActivity.this);
		localidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
        vivienda = (Household) getIntent().getExtras().getSerializable(Constants.VIVIENDA);
		
        String mPass = ((VcaApplication) this.getApplication()).getPassApp();
        mWizardModel = new HouseholdForm(this,mPass,localidad.getIdent());
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        labels = new HouseholdFormLabels(); 
        
        try {
            //Abre la base de datos
    		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
    		vcaAdapter.open();
    		Bundle dato = null;
        	Page modifPage;

            if (vivienda != null) {
    	        if(tieneValor(vivienda.getCode())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCode());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, vivienda.getCode());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(vivienda.getCensusTaker()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCensusTaker());
                    Censador censador = vcaAdapter.getCensador(MainDBConstants.ident + "='"+ vivienda.getCensusTaker().getIdent() + "'", null);
                    dato = new Bundle();
                    if(censador!=null) dato.putString(SIMPLE_DATA_KEY, censador.getCode()+"-"+censador.getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        else {
    	        	modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCensusTaker());
    	        	Censador censador = vcaAdapter.getCensador(MainDBConstants.ident + "='"+ defaultCensador + "'", null);
    	        	dato = new Bundle();
                    if(censador!=null) dato.putString(SIMPLE_DATA_KEY, censador.getCode()+"-"+censador.getName());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(vivienda.getOwnerName())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOwnerName());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, vivienda.getOwnerName());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(vivienda.getCensusDate()!=null){
    		        modifPage = (NewDatePage) mWizardModel.findByKey(labels.getCensusDate());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(vivienda.getCensusDate()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        else {
    	        	modifPage = (NewDatePage) mWizardModel.findByKey(labels.getCensusDate());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(new Date()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(vivienda.getMaterial())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMaterial());
                    MessageResource catMat = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + vivienda.getMaterial() + "' and " + MainDBConstants.catRoot + "='CAT_MAT'", null);
                    dato = new Bundle();
                    if(catMat!=null) dato.putString(SIMPLE_DATA_KEY, catMat.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        if(tieneValor(vivienda.getInhabited())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getInhabited());
                    MessageResource catSino = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + vivienda.getInhabited() + "' and " + MainDBConstants.catRoot + "='CAT_HAB'", null);
                    dato = new Bundle();
                    if(catSino!=null) dato.putString(SIMPLE_DATA_KEY, catSino.getSpanish());
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
    	        /*if(vivienda.getSleep()!=null){
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
    	        }*/
    	        
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
    	        if(vivienda.getPersonasCharlas()!=null){
    	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getPersonasCharlas());
    	            dato = new Bundle();
    	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getPersonasCharlas()));
    	            modifPage.resetData(dato);
    	            modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(vivienda.getObs())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getObs());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, vivienda.getObs());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getMasculinos()!=null){
			        if(vivienda.getMasculinos()>0){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getMasculinos());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getMasculinos()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
			        }
    	        }
    	        if(vivienda.getFemeninos()!=null){
			        if(vivienda.getFemeninos()>0){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getFemeninos());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getFemeninos()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
			        }
    	        }
    	        if(vivienda.getHabitants()!=null){
    	        	modifPage = (LabelPage) mWizardModel.findByKey(labels.getHabitants());
    	        	modifPage.setHint(vivienda.getHabitants().toString());
    	            modifPage.setmVisible(true);
    	        }

    	        
    	        if(vivienda.getMenores5fem()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getMenores5fem());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getMenores5fem()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getMenores5masc()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getMenores5masc());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getMenores5masc()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        if(vivienda.getMenores5()!=null){
    	        	modifPage = (LabelPage) mWizardModel.findByKey(labels.getMenores5());
    	        	modifPage.setHint(vivienda.getMenores5().toString());
    	            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getEmbarazadas()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getEmbarazadas());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getEmbarazadas()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getSitiosDormirCama()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getSitiosDormirCama());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getSitiosDormirCama()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getSitiosDormirHamaca()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getSitiosDormirHamaca());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getSitiosDormirHamaca()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getSitiosDormirSuelo()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getSitiosDormirSuelo());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getSitiosDormirSuelo()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getSitiosDormirOtro()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getSitiosDormirOtro());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getSitiosDormirOtro()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getMtildExistentes()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getMtildExistentes());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getMtildExistentes()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
    	        }
    	        
    	        if(vivienda.getMosqSinInsecticida()!=null){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getMosqSinInsecticida());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(vivienda.getMosqSinInsecticida()));
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
                        if(vivienda.getIdent()==null) {
                        	i = new Intent(getApplicationContext(),
                					CensoActivity.class);
                        }
                        else {
                        	i = new Intent(getApplicationContext(),
                					MenuCensoCasaActivity.class);
                        }
                    	if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
                		if (vivienda!=null) arguments.putSerializable(Constants.VIVIENDA , vivienda);
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
            if(position == mPagerAdapter.getCutOffPage()) {
            	if (!constraintMessage.equals("")) {
	            	//Toast.makeText(getApplicationContext(), constraintMessage, Toast.LENGTH_SHORT).show();
	            	constraintMessage = "";
            	}
            }
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
            	String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        		if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
        				|| (np.ismValPattern() && !valor.matches(np.getmPattern()))){
        			constraintMessage = "El valor que ingresó no cumple con el rango especificado. Favor ingresar un número entre "
        					+ np.getmGreaterOrEqualsThan() + " y " + np.getmLowerOrEqualsThan() +"!";
        			cutOffPage = i;
        			break;
        		}
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
    	for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.getTitle().equals(labels.getFemeninos())) {
            	String valor = page.getData().getString(IntegerPage.SIMPLE_DATA_KEY);
                if (valor != null && !valor.isEmpty()) {
                	Page pageCambiar2;
                	IntegerPage np2;
                	femeninos = Integer.parseInt(valor);
                	pageCambiar2 = mWizardModel.findByKey(labels.getEmbarazadas());
                	np2 = (IntegerPage) pageCambiar2;
                	np2.setmLowerOrEqualsThan(femeninos);
                	np2.setmValRange(true);
                	pageCambiar2 = mWizardModel.findByKey(labels.getMenores5fem());
                	np2 = (IntegerPage) pageCambiar2;
                	np2.setmLowerOrEqualsThan(femeninos);
                	np2.setmValRange(true);
                }
            }
            if (page.getTitle().equals(labels.getMasculinos())) {
            	String valor = page.getData().getString(IntegerPage.SIMPLE_DATA_KEY);
                if (valor != null && !valor.isEmpty()) {
                	Page pageCambiar2;
                	IntegerPage np2;
                	masculinos = Integer.parseInt(valor);
                	pageCambiar2 = mWizardModel.findByKey(labels.getMenores5masc());
                	np2 = (IntegerPage) pageCambiar2;
                	np2.setmLowerOrEqualsThan(masculinos);
                	np2.setmValRange(true);
                }
            }
        }
    }
    
    public void updateModel(Page page){
        try{
        	if (page.getTitle().equals(labels.getInhabited())) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.SI)) {
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), true, null);
                	//changeStatus(mWizardModel.findByKey(labels.getSleep()), true, null);
                	//changeStatus(mWizardModel.findByKey(labels.getNumNets()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getPersonasCharlas()), true, null);
                	
                	changeStatus(mWizardModel.findByKey(labels.getMasculinos()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getFemeninos()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5masc()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5fem()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getEmbarazadas()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirCama()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirHamaca()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirSuelo()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirOtro()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMtildExistentes()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMosqSinInsecticida()), true, null);
                	
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO)) {
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), true, null);
                	//changeStatus(mWizardModel.findByKey(labels.getSleep()), false, null);
                	//changeStatus(mWizardModel.findByKey(labels.getNumNets()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), true, null);
                	changeStatus(mWizardModel.findByKey(labels.getPersonasCharlas()), true, null);
                	
                	changeStatus(mWizardModel.findByKey(labels.getMasculinos()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getFemeninos()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5masc()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5fem()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getEmbarazadas()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirCama()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirHamaca()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirSuelo()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirOtro()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMtildExistentes()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMosqSinInsecticida()), false, null);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && (page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.CERRADA)||page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.RENUENTE))) {
                	changeStatus(mWizardModel.findByKey(labels.getHabitants()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getOwnerName()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMaterial()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSprRooms()), false, null);
                	//changeStatus(mWizardModel.findByKey(labels.getSleep()), false, null);
                	//changeStatus(mWizardModel.findByKey(labels.getNumNets()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSprooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getRooms()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getNoSproomsReasons()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getPersonasCharlas()), false, null);
                	
                	changeStatus(mWizardModel.findByKey(labels.getMasculinos()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getFemeninos()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5masc()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMenores5fem()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getEmbarazadas()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirCama()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirHamaca()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirSuelo()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getSitiosDormirOtro()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMtildExistentes()), false, null);
                	changeStatus(mWizardModel.findByKey(labels.getMosqSinInsecticida()), false, null);
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
            
            if (page.getTitle().equals(labels.getMasculinos())) {
            	Integer personas = 0;
            	Page femPage = mWizardModel.findByKey(labels.getFemeninos()); 
            	String valorFem = femPage.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorMasc = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorMasc != null && !valorMasc.isEmpty() && valorFem != null && !valorFem.isEmpty()) {
                	personas = Integer.valueOf(valorMasc)+Integer.valueOf(valorFem);
                	if(Integer.valueOf(valorMasc)>0) {
                    	changeStatus(mWizardModel.findByKey(labels.getMenores5masc()), true, null);
                    	notificarCambios = false;
                    }
                	else {
                		changeStatus(mWizardModel.findByKey(labels.getMenores5masc()), false, null);
                		notificarCambios = false;
                	}
                }
                changeStatus(mWizardModel.findByKey(labels.getHabitants()), true, String.valueOf(personas));
                notificarCambios = false;
                onPageTreeChanged();
            }
            
            if (page.getTitle().equals(labels.getFemeninos())) {
            	Integer personas = 0;
            	Page mascPage = mWizardModel.findByKey(labels.getMasculinos()); 
            	String valorMascu = mascPage.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorFeme = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorFeme != null && !valorFeme.isEmpty() && valorMascu != null && !valorMascu.isEmpty()) {
                	personas = Integer.valueOf(valorFeme)+Integer.valueOf(valorMascu);
                	if(Integer.valueOf(valorFeme)>0) {
                    	changeStatus(mWizardModel.findByKey(labels.getEmbarazadas()), true, null);
                    	changeStatus(mWizardModel.findByKey(labels.getMenores5fem()), true, null);
                    	notificarCambios = false;
                    }
                	else {
                		changeStatus(mWizardModel.findByKey(labels.getEmbarazadas()), false, null);
                		changeStatus(mWizardModel.findByKey(labels.getMenores5fem()), false, null);
                		notificarCambios = false;
                	}
                }
                changeStatus(mWizardModel.findByKey(labels.getHabitants()), true, String.valueOf(personas));
                notificarCambios = false;
                onPageTreeChanged();
            }
            
            if (page.getTitle().equals(labels.getMenores5masc())) {
            	Integer personas = 0;
            	Page femPage = mWizardModel.findByKey(labels.getMenores5fem()); 
            	String valorFem = femPage.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorMasc = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorMasc != null && !valorMasc.isEmpty() && valorFem != null && !valorFem.isEmpty()) {
                	personas = Integer.valueOf(valorMasc)+Integer.valueOf(valorFem);
                }
                changeStatus(mWizardModel.findByKey(labels.getMenores5()), true, String.valueOf(personas));
                notificarCambios = false;
                onPageTreeChanged();
            }
            
            if (page.getTitle().equals(labels.getMenores5fem())) {
            	Integer personas = 0;
            	Page mascPage = mWizardModel.findByKey(labels.getMenores5masc()); 
            	String valorMascu = mascPage.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorFeme = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorFeme != null && !valorFeme.isEmpty() && valorMascu != null && !valorMascu.isEmpty()) {
                	personas = Integer.valueOf(valorFeme)+Integer.valueOf(valorMascu);
                }
                changeStatus(mWizardModel.findByKey(labels.getMenores5()), true, String.valueOf(personas));
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
            Map<String, String> mapa = mWizardModel.getAnswers();
            //Guarda las respuestas en un bundle
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            
            String ident = infoMovil.getId();
            String code = datos.getString(this.getString(R.string.codeHouse));
            String[] censusTaker = datos.getString(this.getString(R.string.censusTaker)).split("-");
            Censador censador = vcaAdapter.getCensador(MainDBConstants.code + "='"+ censusTaker[0] +"' and "+ MainDBConstants.name + "='"+ censusTaker[1] +"'", null);
            String inhabited = datos.getString(this.getString(R.string.inhabited));
            String ownerName = datos.getString(this.getString(R.string.ownerName));
            //String habitants = datos.getString(this.getString(R.string.habitants));
            String material = datos.getString(this.getString(R.string.material));
            String sprRooms = datos.getString(this.getString(R.string.sprRooms));
            String noSprooms = datos.getString(this.getString(R.string.noSprooms));
            String noSproomsReasons = datos.getString(this.getString(R.string.noSproomsReasons));
            String personasCharlas = datos.getString(this.getString(R.string.personasCharlas));
            //String sleep = datos.getString(this.getString(R.string.sleep));
            //String numNets = datos.getString(this.getString(R.string.numNets));
            String obs = datos.getString(this.getString(R.string.obsHouse));
            String masculinos = datos.getString(this.getString(R.string.masculinos));
            String femeninos = datos.getString(this.getString(R.string.femeninos));
            //String menores5 = datos.getString(this.getString(R.string.menores5));
            String menores5masc = datos.getString(this.getString(R.string.menores5masc));
            String menores5fem = datos.getString(this.getString(R.string.menores5fem));
            String embarazadas = datos.getString(this.getString(R.string.embarazadas));
            String sitiosDormirCama = datos.getString(this.getString(R.string.sitiosDormirCama));
            String sitiosDormirHamaca = datos.getString(this.getString(R.string.sitiosDormirHamaca));
            String sitiosDormirSuelo = datos.getString(this.getString(R.string.sitiosDormirSuelo));
            String sitiosDormirOtro = datos.getString(this.getString(R.string.sitiosDormirOtro));
            String mtildExistentes = datos.getString(this.getString(R.string.mtildExistentes));
            String mosqSinInsecticida = datos.getString(this.getString(R.string.mosqSinInsecticida));
            Integer sitios = 0;
            Integer mosquiteros = 0;
            Integer totalPersonas = 0;
            Integer men5 = 0;
            
            Date censusDate = null;
            try {
            	censusDate = mDateFormat.parse(datos.getString(this.getString(R.string.censusDate)));
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
    			toast.show();
    			finish();
    		}          
            vivienda.setCensusDate(censusDate);

            if (tieneValor(code)) {
                vivienda.setCode(code);
            } else {
            	vivienda.setCode(null);
            }
                  
            if (tieneValor(censador.getIdent())) {
                vivienda.setCensusTaker(censador);
            } else {
            	vivienda.setCensusTaker(null);
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
            
            if (tieneValor(masculinos)) {
                vivienda.setMasculinos(Integer.valueOf(masculinos));
                totalPersonas = totalPersonas + Integer.valueOf(masculinos);
            } else {
            	vivienda.setMasculinos(null);
            }
            
            if (tieneValor(femeninos)) {
                vivienda.setFemeninos(Integer.valueOf(femeninos));
                totalPersonas = totalPersonas + Integer.valueOf(femeninos);
            } else {
            	vivienda.setFemeninos(null);
            }
            
            vivienda.setHabitants(totalPersonas);
            
            if (tieneValor(menores5masc)) {
                vivienda.setMenores5masc(Integer.valueOf(menores5masc));
                men5 = men5 + Integer.valueOf(menores5masc);
            } else {
            	vivienda.setMenores5masc(null);
            }
            
            if (tieneValor(menores5fem)) {
                vivienda.setMenores5fem(Integer.valueOf(menores5fem));
                men5 = men5 + Integer.valueOf(menores5fem);
            } else {
            	vivienda.setMenores5fem(null);
            }
            
            vivienda.setMenores5(men5);
            
            if (tieneValor(embarazadas)) {
                vivienda.setEmbarazadas(Integer.valueOf(embarazadas));
            } else {
            	vivienda.setEmbarazadas(null);
            }
            
            if (tieneValor(sitiosDormirCama)) {
                vivienda.setSitiosDormirCama(Integer.valueOf(sitiosDormirCama));
                sitios=sitios+Integer.valueOf(sitiosDormirCama);
            } else {
            	vivienda.setSitiosDormirCama(null);
            }
            
            if (tieneValor(sitiosDormirHamaca)) {
                vivienda.setSitiosDormirHamaca(Integer.valueOf(sitiosDormirHamaca));
                sitios=sitios+Integer.valueOf(sitiosDormirHamaca);
            } else {
            	vivienda.setSitiosDormirHamaca(null);
            }
            
            if (tieneValor(sitiosDormirSuelo)) {
                vivienda.setSitiosDormirSuelo(Integer.valueOf(sitiosDormirSuelo));
                sitios=sitios+Integer.valueOf(sitiosDormirSuelo);
            } else {
            	vivienda.setSitiosDormirSuelo(null);
            }
            
            if (tieneValor(sitiosDormirOtro)) {
                vivienda.setSitiosDormirOtro(Integer.valueOf(sitiosDormirOtro));
                sitios=sitios+Integer.valueOf(sitiosDormirOtro);
            } else {
            	vivienda.setSitiosDormirOtro(null);
            }
            
            if (tieneValor(mtildExistentes)) {
                vivienda.setMtildExistentes(Integer.valueOf(mtildExistentes));
                mosquiteros=mosquiteros+Integer.valueOf(mtildExistentes);
            } else {
            	vivienda.setMtildExistentes(null);
            }
            
            if (tieneValor(mosqSinInsecticida)) {
                vivienda.setMosqSinInsecticida(Integer.valueOf(mosqSinInsecticida));
                mosquiteros=mosquiteros+Integer.valueOf(mosqSinInsecticida);
            } else {
            	vivienda.setMosqSinInsecticida(null);
            }
            
            
            vivienda.setSleep(sitios);
            vivienda.setNumNets(mosquiteros);
            
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
            
            if (tieneValor(personasCharlas)) {
                vivienda.setPersonasCharlas(Integer.valueOf(personasCharlas));
            }
            else {
            	vivienda.setPersonasCharlas(null);
            }
            
            if (tieneValor(obs)) {
                vivienda.setObs(obs);
            } else {
            	vivienda.setObs(null);
            }
            
            if (vivienda.getRecordDate()==null) vivienda.setRecordDate(new Date());
            vivienda.setRecordUser(username);
            vivienda.setDeviceid(infoMovil.getDeviceId());
            vivienda.setPasive('0');
            if (vivienda.getEstado()==Constants.STATUS_SUBMITTED) {
            	vivienda.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else if (vivienda.getEstado()==Constants.STATUS_NOT_SUBMITTED) {
            	vivienda.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else {
            	vivienda.setEstado(Constants.STATUS_NOT_FINALIZED);
            }
            
            vivienda.setVerified("Si");
            
            vivienda.setLocal(localidad);
            if(vivienda.getIdent()==null) {
            	vivienda.setIdent(ident);
            	vcaAdapter.crearHousehold(vivienda);
            }else {
            	vcaAdapter.editarHousehold(vivienda);
            }
            vcaAdapter.close();
            Bundle arguments = new Bundle();
    		if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
    		if (vivienda!=null) arguments.putSerializable(Constants.VIVIENDA , vivienda);
            Intent i;
            i = new Intent(getApplicationContext(),
                    MenuCensoCasaActivity.class);
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

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
import org.clintonhealthaccess.vca.activities.mapeo.CasosActivity;
import org.clintonhealthaccess.vca.activities.mapeo.MenuCasoActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.forms.mapeo.CaseForm;
import org.clintonhealthaccess.vca.forms.mapeo.CaseFormLabels;
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
import org.clintonhealthaccess.vca.wizard.model.IntegerPage;
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


public class CaseActivity extends FragmentActivity implements
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
    private static Caso caso = new Caso();
    private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private CaseFormLabels labels;
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
		infoMovil = new DeviceInfo(CaseActivity.this);
		localidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
        caso = (Caso) getIntent().getExtras().getSerializable(Constants.CASO);
        roles = getIntent().getExtras().getString(Constants.ROLES);
		
        mPass = ((VcaApplication) this.getApplication()).getPassApp();
        mWizardModel = new CaseForm(this,mPass,localidad.getIdent());
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        labels = new CaseFormLabels(); 
        
        try {
            //Abre la base de datos
    		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
    		vcaAdapter.open();
    		Bundle dato = null;
        	Page modifPage;

            if (caso != null) {
    	        if(tieneValor(caso.getCodigo())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCodeCase());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, caso.getCodigo());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(caso.getCui())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCuiCase());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, caso.getCui());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(caso.getCodE1())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCodE1());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, caso.getCodE1());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(caso.getCasa())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCodCasa());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, caso.getCasa());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(caso.getNombre())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getNameCase());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, caso.getNombre());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(caso.getSint())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSintCase());
                    MessageResource catSN = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + caso.getSint() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    dato = new Bundle();
                    if(catSN!=null) dato.putString(SIMPLE_DATA_KEY, catSN.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        if(caso.getFisDate()!=null){
    		        modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFisDate());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(caso.getFisDate()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(caso.getMxDate()!=null){
    		        modifPage = (NewDatePage) mWizardModel.findByKey(labels.getMxDate());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(caso.getMxDate()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        else {
    	        	modifPage = (NewDatePage) mWizardModel.findByKey(labels.getMxDate());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(new Date()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(caso.getMxType())){
                    modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getMxType());
                    String codMxTypes = caso.getMxType().replaceAll("," , "','");
                    List<String> descMxTypes = new ArrayList<String>();
                    List<MessageResource> msMxTypes = vcaAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codMxTypes + "') and " + MainDBConstants.catRoot + "='CAT_TIPOPRUEBA'", null);
                    for(MessageResource ms : msMxTypes){
                        descMxTypes.add(ms.getSpanish());
                    }
                    dato = new Bundle();
                    dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descMxTypes);
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        if(tieneValor(caso.getInfo())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getInfoCase());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, caso.getInfo());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(caso.getSexo())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSexo());
                    MessageResource catSexo = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + caso.getSexo() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
                    dato = new Bundle();
                    if(catSexo!=null) dato.putString(SIMPLE_DATA_KEY, catSexo.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        if(tieneValor(caso.getEmbarazada())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEmbarazada());
                    MessageResource catSN = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + caso.getEmbarazada() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    dato = new Bundle();
                    if(catSN!=null) dato.putString(SIMPLE_DATA_KEY, catSN.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        if(tieneValor(caso.getMenor6meses())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMenor6meses());
                    MessageResource catSN = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + caso.getMenor6meses() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    dato = new Bundle();
                    if(catSN!=null) dato.putString(SIMPLE_DATA_KEY, catSN.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        if(caso.getEdad()!=null){
    	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getEdad());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, String.valueOf(caso.getEdad()));
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        
    	        if(caso.getLocal()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLocalidad());
                    dato = new Bundle();
                    dato.putString(SIMPLE_DATA_KEY, caso.getLocal().getName());
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
                        if(caso.getIdent()==null) {
                        	i = new Intent(getApplicationContext(),
                					CasosActivity.class);
                        }
                        else {
                        	i = new Intent(getApplicationContext(),
                        			MenuCasoActivity.class);
                        }
                    	if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
                		if (caso!=null) arguments.putSerializable(Constants.CASO , caso);
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
        	if (page.getTitle().equals(labels.getSintCase())) {
        		if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.SI)) {
        			changeStatus(mWizardModel.findByKey(labels.getFisDate()), true, null);
        		}
        		else {
        			changeStatus(mWizardModel.findByKey(labels.getFisDate()), false, null);
        		}
        		notificarCambios = false;
                onPageTreeChanged();
        	}
        	if (page.getTitle().equals(labels.getSexo())) {
        		Page page1 = mWizardModel.findByKey(labels.getSexo());
        		Page page2 = mWizardModel.findByKey(labels.getEdad());
        		if(page1.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null) {
        			Integer edad = Integer.parseInt(page2.getData().getString(IntegerPage.SIMPLE_DATA_KEY));
	                if(page1.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.MUJER) && edad > 12 && edad < 50) {
	                	changeStatus(mWizardModel.findByKey(labels.getEmbarazada()), true, null);
	                }
	                else {
	                	changeStatus(mWizardModel.findByKey(labels.getEmbarazada()), false, null);
	                }
        		}
        		notificarCambios = false;
                onPageTreeChanged();
        	}
        	if (page.getTitle().equals(labels.getEdad())) {
        		Page page1 = mWizardModel.findByKey(labels.getSexo());
        		Page page2 = mWizardModel.findByKey(labels.getEdad());
        		if(page1.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null) {
        			Integer edad = Integer.parseInt(page2.getData().getString(IntegerPage.SIMPLE_DATA_KEY));
	                if(page1.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.MUJER) && edad > 12 && edad < 50) {
	                	changeStatus(mWizardModel.findByKey(labels.getEmbarazada()), true, null);
	                }
	                else {
	                	changeStatus(mWizardModel.findByKey(labels.getEmbarazada()), false, null);
	                }
        		}
        		if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("0")) {
        			changeStatus(mWizardModel.findByKey(labels.getMenor6meses()), true, null);
        		}
        		else {
        			changeStatus(mWizardModel.findByKey(labels.getMenor6meses()), false, null);
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
        	vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
        	vcaAdapter.open();
        	
            Map<String, String> mapa = mWizardModel.getAnswers();
            //Guarda las respuestas en un bundle
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            
            String ident = infoMovil.getId();
            String codigo = datos.getString(this.getString(R.string.codeCase));
            String cui = datos.getString(this.getString(R.string.cuiCase));
            String casa = datos.getString(this.getString(R.string.codCasa));
            String codE1 = datos.getString(this.getString(R.string.codE1));
            String nombre = datos.getString(this.getString(R.string.nameCase));
            String mxType = datos.getString(this.getString(R.string.mxType));
            String sint = datos.getString(this.getString(R.string.sintCase));
            String idLocalidad = datos.getString(this.getString(R.string.localidadF));
            String info = datos.getString(this.getString(R.string.infoCase));
            
            String sexo = datos.getString(this.getString(R.string.sexo));
            String edad = datos.getString(this.getString(R.string.edad));
            String embarazada = datos.getString(this.getString(R.string.embarazada));
            String menor6meses = datos.getString(this.getString(R.string.menor6meses));
            
            
            Date fisDate = null;
            if(datos.getString(this.getString(R.string.fisDate))!= null) {
	            try {
	            	fisDate = mDateFormat.parse(datos.getString(this.getString(R.string.fisDate)));
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}     
            }
            caso.setFisDate(fisDate);
            
            Date mxDate = null;
            try {
            	mxDate = mDateFormat.parse(datos.getString(this.getString(R.string.mxDate)));
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
    			toast.show();
    			finish();
    		}          
            caso.setMxDate(mxDate);
            
            if(fisDate!=null && mxDate!= null) {
            	if(!(mxDate.compareTo(fisDate)>0 || mxDate.compareTo(fisDate)==0)) {
            		Toast toast = Toast.makeText(getApplicationContext(),"Fechas incorrectas!",Toast.LENGTH_LONG);
                    toast.show();
                    return;
            	}
            }

            if (tieneValor(codigo)) {
                caso.setCodigo(codigo);
            } else {
            	caso.setCodigo(null);
            }
            
            if (tieneValor(cui)) {
                caso.setCui(cui);
            } else {
            	caso.setCui(null);
            }
            
            if (tieneValor(casa)) {
                caso.setCasa(casa);
            } else {
            	caso.setCasa(null);
            }
            
            if (tieneValor(codE1)) {
                caso.setCodE1(codE1);
            } else {
            	caso.setCodE1(null);
            }
            
            if (tieneValor(nombre)) {
                caso.setNombre(nombre);
            } else {
            	caso.setNombre(null);
            }
            
            if (tieneValor(edad)) {
                caso.setEdad(Integer.valueOf(edad));
            } else {
            	caso.setEdad(null);
            }
                  
            if (tieneValor(mxType)) {
                String keysRazones = "";
                mxType = mxType.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msArticulos = vcaAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + mxType + "') and "
                        + MainDBConstants.catRoot + "='CAT_TIPOPRUEBA'", null);
                for(MessageResource ms : msArticulos) {
                    keysRazones += ms.getCatKey() + ",";
                }
                if (!keysRazones.isEmpty())
                    keysRazones = keysRazones.substring(0, keysRazones.length() - 1);
                caso.setMxType(keysRazones);
            } else {
            	caso.setMxType(null);
            }
            if (tieneValor(sint)) {
            	MessageResource catSN = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + sint + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSN!=null) caso.setSint(catSN.getCatKey());
            } else {
            	caso.setSint(null);
            }
            
            if (tieneValor(info)) {
                caso.setInfo(info);
            } else {
            	caso.setInfo(null);
            }
            
            if (tieneValor(sexo)) {
            	MessageResource catSexo = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + sexo + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
            	if (catSexo!=null) caso.setSexo(catSexo.getCatKey());
            } else {
            	caso.setSexo(null);
            }
            
            if (tieneValor(embarazada)) {
            	MessageResource catSN = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + embarazada + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSN!=null) caso.setEmbarazada(catSN.getCatKey());
            } else {
            	caso.setEmbarazada(null);
            }
            
            if (tieneValor(menor6meses)) {
            	MessageResource catSN = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + menor6meses + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSN!=null) caso.setMenor6meses(catSN.getCatKey());
            } else {
            	caso.setMenor6meses(null);
            }
            
            if (caso.getRecordDate()==null) caso.setRecordDate(new Date());
            caso.setRecordUser(username);
            caso.setDeviceid(infoMovil.getDeviceId());
            caso.setPasive('0');
            if (caso.getEstado()==Constants.STATUS_SUBMITTED) {
            	caso.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else if (caso.getEstado()==Constants.STATUS_NOT_SUBMITTED) {
            	caso.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else {
            	caso.setEstado(Constants.STATUS_NOT_FINALIZED);
            }
            caso.setLocal(vcaAdapter.getLocalidad(MainDBConstants.name + "= '" + idLocalidad + "'", null));
            if(caso.getIdent()==null) {
            	caso.setIdent(ident);
            	try {
            		vcaAdapter.crearCaso(caso);
            	}catch (SQLiteException exception) {
            		caso.setIdent(null);
            	    Toast toast = Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG);
            	    toast.show();
            	    exception.printStackTrace();
            	    return;
            	} 
            }else {
            	vcaAdapter.editarCaso(caso);
            }
            vcaAdapter.close();
            Bundle arguments = new Bundle();
    		if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
    		if (caso!=null) arguments.putSerializable(Constants.CASO , caso);
    		if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
            Intent i;
            i = new Intent(getApplicationContext(),
            		MenuCasoActivity.class);
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

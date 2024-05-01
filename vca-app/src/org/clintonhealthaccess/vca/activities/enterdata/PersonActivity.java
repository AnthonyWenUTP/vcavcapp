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
import org.clintonhealthaccess.vca.activities.VerFamiliaActivity;
import org.clintonhealthaccess.vca.activities.mtilds.EvaluacionMosquiterosPersonaActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.forms.PersonForm;
import org.clintonhealthaccess.vca.forms.PersonFormLabels;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class PersonActivity extends FragmentActivity implements
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
    private static EntregaTarget meta = new EntregaTarget();
    private static Person persona = new Person();
    private String username;
    
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private PersonFormLabels labels;
	private String formulario;
	public String constraintMessage="";

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

		infoMovil = new DeviceInfo(PersonActivity.this);
		localidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
        vivienda = (Household) getIntent().getExtras().getSerializable(Constants.VIVIENDA);
        meta = (EntregaTarget) getIntent().getExtras().getSerializable(Constants.META);
        persona = (Person) getIntent().getExtras().getSerializable(Constants.PERSONA);
        formulario = getIntent().getExtras().getString(Constants.FORM_NAME);
		
        String mPass = ((VcaApplication) this.getApplication()).getPassApp();
        mWizardModel = new PersonForm(this,mPass,localidad.getIdent());
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        labels = new PersonFormLabels(); 
        
        try {
            //Abre la base de datos
    		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
    		vcaAdapter.open();
    		Bundle dato = null;
        	Page modifPage;

            if (persona != null) {
    	        if(tieneValor(persona.getCode())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCodePerson());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, persona.getCode());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        if(tieneValor(persona.getName())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getNamePerson());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, persona.getName());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }

    	        
    	        if(persona.getAge()!=null){
			        if(persona.getAge()>=0){
			        	modifPage = (IntegerPage) mWizardModel.findByKey(labels.getAgePerson());
			            dato = new Bundle();
			            dato.putString(SIMPLE_DATA_KEY, String.valueOf(persona.getAge()));
			            modifPage.resetData(dato);
			            modifPage.setmVisible(true);
			        }
    	        }
    	        if(tieneValor(persona.getSex())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSexPerson());
                    MessageResource catSex = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + persona.getSex() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
                    dato = new Bundle();
                    if(catSex!=null) dato.putString(SIMPLE_DATA_KEY, catSex.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        
    	        if(tieneValor(persona.getPreg())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPregPerson());
                    MessageResource catEmb = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + persona.getPreg() + "' and " + MainDBConstants.catRoot + "='CAT_EMB'", null);
                    dato = new Bundle();
                    if(catEmb!=null) dato.putString(SIMPLE_DATA_KEY, catEmb.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
    	        
    	        if(tieneValor(persona.getObs())){
    	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getObsPerson());
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, persona.getObs());
    	        	modifPage.resetData(dato);
    	        	modifPage.setmVisible(true);
    	        }
    	        
    	        if(persona.getIdent()!= null) {
    	        	modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Esta persona está viviendo en esta casa?");
    	        	dato = new Bundle();
    	        	if(persona.getPasive()=='0') {
    	        		dato.putString(SIMPLE_DATA_KEY, Constants.SI);
    	        	}else {
    	        		dato.putString(SIMPLE_DATA_KEY, Constants.NO);
    	        	}
    	        	
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
                    	salirFormulario(2);
                    	dialog.dismiss();
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
            	Toast.makeText(getApplicationContext(), constraintMessage, Toast.LENGTH_SHORT).show();
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
                constraintMessage = "Este valor es requerido, favor ingrese la información para poder continuar";
                break;
            }     
            if (!page.getData().isEmpty() && clase.equals("class org.clintonhealthaccess.vca.wizard.model.IntegerPage")) {
            	IntegerPage np = (IntegerPage) page;
            	String valor = np.getData().getString(IntegerPage.SIMPLE_DATA_KEY);
        		if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
        				|| (np.ismValPattern() && !valor.matches(np.getmPattern()))){
        			constraintMessage = "El valor que ingresó no cumple con el rango especificado. Favor ingresar un número entre "
        					+ np.getmGreaterOrEqualsThan() + " y " + np.getmLowerOrEqualsThan() +"!";
        			cutOffPage = i;
        			break;
        		}
            }
            if (!page.getData().isEmpty() && clase.equals("class org.clintonhealthaccess.vca.wizard.model.TextPage")) {
            	TextPage tp = (TextPage) page;
            	if (tp.ismValPattern()) {
            		String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
            		if(!valor.matches(tp.getmPattern())){
            			constraintMessage = "El valor que ingresó no cumple con el formato especificado. " + tp.getmPattern();
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
        	if (page.getTitle().equals(labels.getSexPerson())||page.getTitle().equals(labels.getAgePerson())) {
        		Page page1 = mWizardModel.findByKey(labels.getSexPerson());
        		Page page2 = mWizardModel.findByKey(labels.getAgePerson());
        		if(page1.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null) {
        			Integer edad = Integer.parseInt(page2.getData().getString(IntegerPage.SIMPLE_DATA_KEY));
	                if(page1.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.MUJER) && edad > 12 && edad < 50) {
	                	changeStatus(mWizardModel.findByKey(labels.getPregPerson()), true, null);
	                }
	                else {
	                	changeStatus(mWizardModel.findByKey(labels.getPregPerson()), false, null);
	                }
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
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.IntegerPage")){
    		IntegerPage modifPage = (IntegerPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class org.clintonhealthaccess.vca.wizard.model.IntegerPage")){
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
            String code = datos.getString(this.getString(R.string.codePerson));
            String name = datos.getString(this.getString(R.string.namePerson));
            String sex = datos.getString(this.getString(R.string.sexPerson));
            String age = datos.getString(this.getString(R.string.agePerson));
            String preg = datos.getString(this.getString(R.string.pregPerson));
            String activo = datos.getString("Esta persona está viviendo en esta casa?");
            String obs = datos.getString(this.getString(R.string.obsPerson));
            
            if (tieneValor(code)) {
                persona.setCode(code);
            } else {
            	persona.setCode(null);
            }
            if (tieneValor(name)) {
                persona.setName(name);
            } else {
            	persona.setName(null);
            }
                  
            if (tieneValor(age)) {
                persona.setAge(Integer.valueOf(age));
            } else {
            	persona.setAge(null);
            }
            if (tieneValor(sex)) {
            	MessageResource catSex = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + sex + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
            	if (catSex!=null) persona.setSex(catSex.getCatKey());
            } else {
            	persona.setSex(null);
            }
            if (tieneValor(preg)) {
            	MessageResource catEmb = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + preg + "' and " + MainDBConstants.catRoot + "='CAT_EMB'", null);
            	if (catEmb!=null) persona.setPreg(catEmb.getCatKey());
            } else {
            	persona.setPreg(null);
            }
            
            if (tieneValor(activo)) {
            	MessageResource catSN = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + activo + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catSN!=null) {
            		if(catSN.getSpanish().equals(Constants.SI)) {
            			persona.setPasive('0');
            		}else {
            			persona.setPasive('1');
            		}
            	}
            } else {
            	persona.setPasive('0');
            }
            
            
            
            if (tieneValor(obs)) {
            	persona.setObs(obs);
            } else {
            	persona.setObs(null);
            }
            
            if (persona.getRecordDate()==null) persona.setRecordDate(new Date());
            persona.setRecordUser(username);
            persona.setDeviceid(infoMovil.getDeviceId());
            if (persona.getEstado()==Constants.STATUS_SUBMITTED) {
            	persona.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else if (persona.getEstado()==Constants.STATUS_NOT_SUBMITTED) {
            	persona.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else {
            	persona.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }

            
            persona.setCasa(vivienda);
            if(persona.getIdent()==null) {
            	persona.setIdent(ident);
            	vcaAdapter.crearPersona(persona);
            }else {
            	vcaAdapter.editarPersona(persona);
            }
            vcaAdapter.close();
            salirFormulario(1);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (vcaAdapter != null)
                vcaAdapter.close();
        }
    }
    
	private void salirFormulario(int resultado) {
        
		Bundle arguments = new Bundle();
		Intent i = null;
		if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
		
		
		if(formulario.equals(Constants.VIVIENDA)) {
			if (vivienda!=null) arguments.putSerializable(Constants.VIVIENDA , vivienda);
			i = new Intent(getApplicationContext(),
					VerFamiliaActivity.class);
		}
		else if(formulario.equals(Constants.EVMOSQPERSONA)) {
			if (meta!=null) arguments.putSerializable(Constants.META , meta);
        	i = new Intent(getApplicationContext(),
        			EvaluacionMosquiterosPersonaActivity.class);
		}
		i.putExtras(arguments);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		if(resultado==1) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
	        toast.show();
		}
		else {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.err_cancel),Toast.LENGTH_LONG);
	        toast.show();
		}
		
		finish();
		
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

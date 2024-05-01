package org.clintonhealthaccess.vca.activities.enterdata.mtilds;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.mtilds.EvaluacionMosquiteroIndividualActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionMosquitero;
import org.clintonhealthaccess.vca.forms.mtilds.EvaluacionMosquiteroForm;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.FileUtils;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public class EvaluacionMosquiteroActivity extends FragmentActivity implements
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
	private static Localidad mLocalidad = new Localidad();
	private static EntregaTarget mTarget = new EntregaTarget();
    private static EvaluacionMosquitero meta = new EvaluacionMosquitero();
    
    //private DeviceInfo infoMovil;
    private String roles;

    //private String username;
	//private SharedPreferences settings;
    
	
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

		
        //infoMovil = new DeviceInfo(EntregaMosquiteroActivity.this);
        
        //settings =
		//		PreferenceManager.getDefaultSharedPreferences(this);
		//username =
		//		settings.getString(PreferencesActivity.KEY_USERNAME,
		//				null);

        //Aca se recupera los datos de la meta
        meta = (EvaluacionMosquitero) getIntent().getExtras().getSerializable(Constants.EVMOSQIND);
        roles = getIntent().getExtras().getString(Constants.ROLES);
        
      //Aca se recupera los datos de la localidad
  		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
  		//Aca se recupera los datos de la vivienda
  		mTarget = (EntregaTarget) getIntent().getExtras().getSerializable(Constants.META);
        
        String mPass = ((VcaApplication) this.getApplication()).getPassApp();
        mWizardModel = new EvaluacionMosquiteroForm(this,mPass,meta.getIdent());
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        
        try {
            //Abre la base de datos
    		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
    		vcaAdapter.open();
    		
    		Bundle dato = null;
        	Page modifPage;
        	
        	if (meta != null) {
        		
        		if(tieneValor(meta.getEstadoEvaluacion())){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey("Cual es el estado de este mosquitero durante la evaluación?");
                    dato = new Bundle();
                    dato.putString(SIMPLE_DATA_KEY, meta.getEstadoEvaluacion());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }

        		
        		
        		if(tieneValor(meta.getObs())){
    	        	modifPage = (TextPage) mWizardModel.findByKey("Observaciones");
    	        	dato = new Bundle();
    	        	dato.putString(SIMPLE_DATA_KEY, meta.getObs());
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
                        // Finish form
                    	if (vcaAdapter != null)
                            vcaAdapter.close();
                    	Intent i;
                    	Bundle arguments = new Bundle();
                    	if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
    					if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
    					arguments.putString(Constants.ROLES, roles);
                        i = new Intent(getApplicationContext(), EvaluacionMosquiteroIndividualActivity.class);
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
            if (!page.getData().isEmpty() && clase.equals("class org.clintonhealthaccess.vca.wizard.model.NumberPage")) {
            	NumberPage np = (NumberPage) page;
            	String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        		if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
        				|| (np.ismValPattern() && !valor.matches(np.getmPattern()))){
        			cutOffPage = i;
        			constraintMessage = "El valor que ingresó no cumple con el rango especificado. Favor ingresar un número entre "
        					+ np.getmGreaterOrEqualsThan() + " y " + np.getmLowerOrEqualsThan() +"!";
        			break;
        		}
            }
            if (!page.getData().isEmpty() && clase.equals("class org.clintonhealthaccess.vca.wizard.model.TextPage")) {
            	TextPage tp = (TextPage) page;
            	if (tp.ismValPattern()) {
            		String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
            		if(!valor.matches(tp.getmPattern())){
            			cutOffPage = i;
            			constraintMessage = "El valor que ingresó no cumple con el formato especificado. " + tp.getmPattern();
            			break;
            		}
            	}
            }
        }
        
        
        boolean prueba = mPagerAdapter.getCutOffPage() != cutOffPage;

        if (prueba) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }
        else {
        	return false;
        }	
    }
    
    
    public void updateConstrains(){
    }
    
    public void updateModel(Page page){
        try{
        	if (page.getTitle().equals("Cual es el estado de este mosquitero durante la evaluación?")) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Instalado")) {
                	changeStatus(mWizardModel.findByKey("Porque el MITID está guardado?"), false, null);
                	changeStatus(mWizardModel.findByKey("Razón por la que el MTILD es faltante"), false, null);
                	changeStatus(mWizardModel.findByKey("Durmieron anoche bajo este MTILD?"), true, null);
                	changeStatus(mWizardModel.findByKey("Fue lavado este mosquitero en los ultimos 6 meses?"), true, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el lavado del MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el secado del MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como se maneja el MTILD cuando no esta en uso?"), true, null);
                	changeStatus(mWizardModel.findByKey("Reaccion secundaria con este MTILD"), true, null);
                	changeStatus(mWizardModel.findByKey("El MTILD esta roto?"), true, null);
                	changeStatus(mWizardModel.findByKey("Porque se rompió el MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Número de agujeros"), false, null);
                	changeStatus(mWizardModel.findByKey("Evaluacion de integridad física"), false, null);
                	changeStatus(mWizardModel.findByKey("Los MTILDs tienen agujeros reparados?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como estan reparados?"), false, null);
                	changeStatus(mWizardModel.findByKey("MTILD colectado"), true, null);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Guardado")) {
                	changeStatus(mWizardModel.findByKey("Porque el MITID está guardado?"), true, null);
                	changeStatus(mWizardModel.findByKey("Razón por la que el MTILD es faltante"), false, null);
                	changeStatus(mWizardModel.findByKey("Durmieron anoche bajo este MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Fue lavado este mosquitero en los ultimos 6 meses?"), true, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el lavado del MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el secado del MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como se maneja el MTILD cuando no esta en uso?"), true, null);
                	changeStatus(mWizardModel.findByKey("Reaccion secundaria con este MTILD"), true, null);
                	changeStatus(mWizardModel.findByKey("El MTILD esta roto?"), true, null);
                	changeStatus(mWizardModel.findByKey("Porque se rompió el MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Número de agujeros"), false, null);
                	changeStatus(mWizardModel.findByKey("Evaluacion de integridad física"), false, null);
                	changeStatus(mWizardModel.findByKey("Los MTILDs tienen agujeros reparados?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como estan reparados?"), false, null);
                	changeStatus(mWizardModel.findByKey("MTILD colectado"), true, null);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Faltante")) {
                	changeStatus(mWizardModel.findByKey("Porque el MITID está guardado?"), false, null);
                	changeStatus(mWizardModel.findByKey("Razón por la que el MTILD es faltante"), true, null);
                	changeStatus(mWizardModel.findByKey("Durmieron anoche bajo este MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Fue lavado este mosquitero en los ultimos 6 meses?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el lavado del MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el secado del MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como se maneja el MTILD cuando no esta en uso?"), false, null);
                	changeStatus(mWizardModel.findByKey("Reaccion secundaria con este MTILD"), false, null);
                	changeStatus(mWizardModel.findByKey("El MTILD esta roto?"), false, null);
                	changeStatus(mWizardModel.findByKey("Porque se rompió el MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Número de agujeros"), false, null);
                	changeStatus(mWizardModel.findByKey("Evaluacion de integridad física"), false, null);
                	changeStatus(mWizardModel.findByKey("Los MTILDs tienen agujeros reparados?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como estan reparados?"), false, null);
                	changeStatus(mWizardModel.findByKey("MTILD colectado"), false, null);
                }
                notificarCambios = false;
                onPageTreeChanged();
        	}
        	if (page.getTitle().equals("Fue lavado este mosquitero en los ultimos 6 meses?")) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Si")) {
                	changeStatus(mWizardModel.findByKey("Como hizo el lavado del MTILD?"), true, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el secado del MTILD?"), true, null);
                }
                else {
                	changeStatus(mWizardModel.findByKey("Como hizo el lavado del MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Como hizo el secado del MTILD?"), false, null);
                }
                notificarCambios = false;
                onPageTreeChanged();
        	}
        	if (page.getTitle().equals("El MTILD esta roto?")) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Si")) {
                	changeStatus(mWizardModel.findByKey("Porque se rompió el MTILD?"), true, null);
                	changeStatus(mWizardModel.findByKey("Número de agujeros"), true, null);
                	changeStatus(mWizardModel.findByKey("Evaluacion de integridad física"), true, null);
                	changeStatus(mWizardModel.findByKey("Los MTILDs tienen agujeros reparados?"), true, null);
                }
                else {
                	changeStatus(mWizardModel.findByKey("Porque se rompió el MTILD?"), false, null);
                	changeStatus(mWizardModel.findByKey("Número de agujeros"), false, null);
                	changeStatus(mWizardModel.findByKey("Evaluacion de integridad física"), false, null);
                	changeStatus(mWizardModel.findByKey("Los MTILDs tienen agujeros reparados?"), false, null);
                }
                notificarCambios = false;
                onPageTreeChanged();
        	}
        	if (page.getTitle().equals("Los MTILDs tienen agujeros reparados?")) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Si")) {
                	changeStatus(mWizardModel.findByKey("Como estan reparados?"), true, null);
                }
                else {
                	changeStatus(mWizardModel.findByKey("Como estan reparados?"), true, null);
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
    		TextPage modifPage = (TextPage) page; modifPage.setValue(""); 
    		modifPage.setmVisible(visible);
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
            
            /*String ident = infoMovil.getId();
            mEntrega.setTarget(meta);
            if(totalVisitas==0) {
            	mEntrega.setVisit("1");
            }else {
            	mEntrega.setVisit("2");
            }
            mEntrega.setActivity("NOTICE");
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
            mEntrega.setVisitDate(visitDate);
            
            String[] strSentinel = datos.getString("Persona que visita").split("-");
            Personal centinela = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strSentinel[0] +"' and "+ MainDBConstants.name + "='"+ strSentinel[1] +"'", null);
            if (tieneValor(centinela.getIdent())) {
                mEntrega.setVisitor(centinela);
            } else {
            	mEntrega.setVisitor(null);
            }
            
            String[] strBrigada = datos.getString("Brigada").split("-");
            Brigada brigada = vcaAdapter.getBrigada(MainDBConstants.code + "='"+ strBrigada[0] +"' and "+ MainDBConstants.name + "='"+ strBrigada[1] +"'", null);
            if (tieneValor(brigada.getIdent())) {
                mEntrega.setBrigada(brigada);
            } else {
            	mEntrega.setBrigada(null);
            }
            
            String[] strSupervisor = datos.getString("Supervisor").split("-");
            Personal supervisor = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strSupervisor[0] +"' and "+ MainDBConstants.name + "='"+ strSupervisor[1] +"'", null);
            if (tieneValor(supervisor.getIdent())) {
                mEntrega.setSupervisor(supervisor);
            } else {
            	mEntrega.setSupervisor(null);
            }
            
            String compVisit = datos.getString("Se logró completar la visita");
            
            if (tieneValor(compVisit)) {
            	MessageResource catCompVisit = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + compVisit + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
            	if (catCompVisit!=null) mEntrega.setCompVisit(catCompVisit.getCatKey());
            } else {
            	mEntrega.setCompVisit(null);
            }
            
            String reasonNoVisit = datos.getString("Porqué no se logró completar la visita");
            
            if (tieneValor(reasonNoVisit)) {
            	MessageResource catreasonNoVisit = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + reasonNoVisit + "' and " + MainDBConstants.catRoot + "='CAT_NO_VISIT'", null);
            	if (catreasonNoVisit!=null) mEntrega.setReasonNoVisit(catreasonNoVisit.getCatKey());
            } else {
            	mEntrega.setReasonNoVisit(null);
            }
            
            String reasonNoVisitOther = datos.getString("Motivo no se logro completar, Otro");
            
            if (tieneValor(reasonNoVisitOther)) {
            	mEntrega.setReasonNoVisitOther(reasonNoVisitOther);
            } else {
            	mEntrega.setReasonNoVisitOther(null);
            }
            
            String reasonReluctant = datos.getString("Motivo renuente");
            
            if (tieneValor(reasonReluctant)) {
            	MessageResource catreasonReluctant = vcaAdapter.getMessageResource(MainDBConstants.spanish + "='" + reasonReluctant + "' and " + MainDBConstants.catRoot + "='CAT_RELUCTANT'", null);
            	if (catreasonReluctant!=null) mEntrega.setReasonReluctant(catreasonReluctant.getCatKey());
            } else {
            	mEntrega.setReasonReluctant(null);
            }
            
            String reasonReluctantOther = datos.getString("Motivo renuente otros");
            
            if (tieneValor(reasonReluctantOther)) {
            	mEntrega.setReasonReluctantOther(reasonReluctantOther);
            } else {
            	mEntrega.setReasonReluctantOther(null);
            }
            
            
            String personasCharlas = datos.getString("Personas charla");
            
            if (tieneValor(personasCharlas)) {
            	mEntrega.setPersonasCharlas(Integer.valueOf(personasCharlas));
            } else {
            	mEntrega.setPersonasCharlas(null);
            }
            
            String obs = datos.getString("Observaciones");
            
            if (tieneValor(obs)) {
            	mEntrega.setObs(obs);
            } else {
            	mEntrega.setObs(null);
            }
            
            if (mEntrega.getRecordDate()==null) mEntrega.setRecordDate(new Date());
            
            if (mEntrega.getRecordUser()==null) mEntrega.setRecordUser(username);
            mEntrega.setDeviceid(infoMovil.getDeviceId());
            mEntrega.setPasive('0');
            mEntrega.setEstado(Constants.STATUS_NOT_SUBMITTED);
            
            
            if(mEntrega.getIdent()==null) {
            	mEntrega.setIdent(ident);
            	vcaAdapter.crearVisit(mEntrega);
            }else {
            	vcaAdapter.editarVisit(mEntrega);
            }
            
            if(mEntrega.getCompVisit().equals("1")) {
            	meta.setSprayStatus("PENDING");
            	String[] strRociador = datos.getString("Asignar rociado a").split("-");
                Personal rociador = vcaAdapter.getPersonal(MainDBConstants.code + "='"+ strRociador[0] +"' and "+ MainDBConstants.name + "='"+ strRociador[1] +"'", null);
                if (tieneValor(rociador.getIdent())) {
                    meta.setAssignedTo(rociador);
                } else {
                	meta.setAssignedTo(null);
                }
            }
            else if(mEntrega.getReasonNoVisit().equals("RELUCT")) {
            	meta.setSprayStatus("RELUCT");
            }
            else if(mEntrega.getReasonNoVisit().equals("CLOSED")) {
            	meta.setSprayStatus("CLOSED");
            }
            else if(mEntrega.getReasonNoVisit().equals("DESTRO")) {
            	meta.setSprayStatus("DROPPED");
            	meta.setPasive('0');
            }
            else {
            	meta.setSprayStatus("NOTVIS");
            }
            meta.setDeviceid(infoMovil.getDeviceId());
            meta.setPasive('0');
            meta.setEstado(Constants.STATUS_NOT_SUBMITTED);
            vcaAdapter.editarTarget(meta);
            MessageResource catEstado = vcaAdapter.getMessageResource(MainDBConstants.catKey + "='" + meta.getSprayStatus() + "' and " + MainDBConstants.catRoot + "='CAT_STATUS'", null);
            if(catEstado!= null) meta.setSprayStatus(catEstado.getSpanish());
            vcaAdapter.close();*/
            Bundle arguments = new Bundle();
    		
    		
            if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
			if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
			arguments.putString(Constants.ROLES, roles);
    		
            Intent i;
            i = new Intent(getApplicationContext(),
            		EvaluacionMosquiteroIndividualActivity.class);
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

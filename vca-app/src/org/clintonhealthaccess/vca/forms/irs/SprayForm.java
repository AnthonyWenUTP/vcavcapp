package org.clintonhealthaccess.vca.forms.irs;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.forms.HouseholdFormLabels;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.MultipleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.NewDatePage;
import org.clintonhealthaccess.vca.wizard.model.NumberPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class SprayForm extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private Target meta = null;
	private HouseholdFormLabels labels;
	

	private String[] fillCatalog(String codigoCatalogo){
        String[] catalogo;
        List<MessageResource> mCatalogo = vcaAdapter.getMessageResources(MainDBConstants.catRoot + "='"+codigoCatalogo+"'", MainDBConstants.order);
        catalogo = new String[mCatalogo.size()];
        index = 0;
        for (MessageResource message: mCatalogo){
            catalogo[index] = message.getSpanish();
            index++;
        }
        return catalogo;
    }
	
    public SprayForm(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new HouseholdFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	meta = vcaAdapter.getTarget(MainDBConstants.ident + " = '"+ mIdent + "'", null);
    	
    	String patron = meta.getHousehold().getLocal().getPattern();
    	
    	int totalVisitas = vcaAdapter.getNumeroRegistros(MainDBConstants.VISITAS_TABLE, 
				MainDBConstants.target + " = '"+ mIdent +"' and "+MainDBConstants.activity+"='NOTICE'");
    	
    	String[] catCompVisit = fillCatalog("CAT_SINO");
    	String[] catHab = fillCatalog("CAT_HAB");
    	List<Personal> mRociador = vcaAdapter.getPersonals(MainDBConstants.sprayer + "=1", MainDBConstants.code);
    	String[] catRociadores = new String[mRociador.size()];
    	index = 0;
        for (Personal rociador: mRociador){
        	catRociadores[index] = rociador.getCode()+"-"+rociador.getName();
            index++;
        }
        List<Personal> mSentinels = vcaAdapter.getPersonals(MainDBConstants.sentinel + "=1", MainDBConstants.code);
    	String[] catCentinelas = new String[mSentinels.size()];
    	index = 0;
        for (Personal centinela: mSentinels){
        	catCentinelas[index] = centinela.getCode()+"-"+centinela.getName();
            index++;
        }
        List<Brigada> mBrigada = vcaAdapter.getBrigadas(null, MainDBConstants.code);
    	String[] catBrigadas = new String[mBrigada.size()];
    	index = 0;
        for (Brigada brigada: mBrigada){
        	catBrigadas[index] = brigada.getCode()+"-"+brigada.getName();
            index++;
        }
        
        List<Personal> mSupervisor = vcaAdapter.getPersonals(MainDBConstants.supervisor + "=1", MainDBConstants.code);
    	String[] catSupervisores = new String[mSupervisor.size()];
    	index = 0;
        for (Personal sup: mSupervisor){
        	catSupervisores[index] = sup.getCode()+"-"+sup.getName();
            index++;
        }
        String[] catRNV = fillCatalog("CAT_NO_VISIT");
        String[] catRR = fillCatalog("CAT_RELUCTANT");
        String[] catMaterial = fillCatalog("CAT_MAT");
        String[] catRazones = fillCatalog("CAT_RNR");

        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Agregar visita en la vivienda" , meta.getHousehold().getCode() + "\n" + meta.getHousehold().getOwnerName()
    													+ "\n Cuartos total > " + meta.getHousehold().getRooms()
    													+ "\n Cuartos rociables > " + meta.getHousehold().getSprRooms()
    													+ "\n Cuartos no rociables > " + meta.getHousehold().getNoSprooms()
    													+ "\n Total habitantes > " + meta.getHousehold().getHabitants()
    													+ "\n Total visitas de preaviso > " + totalVisitas, Constants.WIZARD, true).setRequired(false);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(3);
    	Page sprayDate = new NewDatePage(this,"Fecha de la visita", "Ingrese la fecha en que se visita la vivienda", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page centinela = new SingleFixedChoicePage(this,"Persona que visita", "Seleccione la persona que realiza esta visita de preaviso, es un valor requerido", Constants.WIZARD, true).setChoices(catCentinelas).setRequired(true);
    	Page brigada = new SingleFixedChoicePage(this,"Brigada", "Seleccione la brigada, es un valor requerido", Constants.WIZARD, true).setChoices(catBrigadas).setRequired(true);
    	Page supervisor = new SingleFixedChoicePage(this,"Supervisor", "Seleccione el supervisor, es un valor requerido", Constants.WIZARD, true).setChoices(catSupervisores).setRequired(true);
    	Page compVisit = new SingleFixedChoicePage(this,"Se logró completar la visita", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catCompVisit).setRequired(true);
    	Page reasonNoVisit = new SingleFixedChoicePage(this,"Porqué no se logró completar la visita", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices(catRNV).setRequired(true);
    	Page reasonNoVisitOther = new TextPage(this,"Motivo no se logro completar, Otro","Pregunte las razones por las que no permite la actividad",Constants.WIZARD,false).setPatternValidation(true, ".{0,250}").setRequired(true);
    	Page reasonReluctant = new SingleFixedChoicePage(this,"Motivo renuente","Pregunte las razones por las que no permite la actividad", Constants.WIZARD, false).setChoices(catRR).setRequired(true);
    	Page reasonReluctantOther = new TextPage(this,"Motivo renuente otros","Pregunte las razones por las que no permite la actividad",Constants.WIZARD,false).setPatternValidation(true, ".{0,250}").setRequired(true);
    	Page modCasa = new SingleFixedChoicePage(this,"Desea modificar los datos de la vivienda?", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices(catCompVisit).setRequired(true);
    	Page code = new TextPage(this,labels.getCode(),labels.getCodeHint()+" \n" +patron,Constants.WIZARD,false).setPatternValidation(true, patron).setRequired(true);
    	Page inhabited = new SingleFixedChoicePage(this,labels.getInhabited(), labels.getInhabitedHint(), Constants.WIZARD, false).setChoices(catHab).setRequired(true);
    	Page ownerName = new TextPage(this,labels.getOwnerName(),labels.getOwnerNameHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page habitants = new NumberPage(this,labels.getHabitants(),labels.getHabitantsHint(),Constants.WIZARD,false).setRangeValidation(true, 1, 20).setRequired(true);
    	Page material = new SingleFixedChoicePage(this,labels.getMaterial(), labels.getMaterialHint(), Constants.WIZARD, false).setChoices(catMaterial).setRequired(true);	
    	Page sprRooms = new NumberPage(this,labels.getSprRooms(),labels.getSprRoomsHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page noSprooms = new NumberPage(this,labels.getNoSprooms(),labels.getNoSproomsHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page rooms = new LabelPage(this,labels.getRooms(),labels.getRoomsHint(),Constants.WIZARD,false).setRequired(false);
    	Page noSproomsReasons = new MultipleFixedChoicePage(this,labels.getNoSproomsReasons(), labels.getNoSproomsReasonsHint(), Constants.WIZARD, false).setChoices(catRazones).setRequired(true);
    	Page sleep = new NumberPage(this,labels.getSleep(),labels.getSleepHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 30).setRequired(true);
    	Page numNets = new NumberPage(this,labels.getNumNets(),labels.getNumNetsHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 30).setRequired(true);
    	
    	Page rociador = new SingleFixedChoicePage(this,"Asignar rociado a", "Seleccione la persona que realizará el rociado de esta vivienda, es un valor requerido", Constants.WIZARD, false).setChoices(catRociadores).setRequired(true);
    	Page personasCharlas = new NumberPage(this,"Personas charla","Numero de personas que se les brida charlas. Requerido de 0 a 30",Constants.WIZARD,false).setRangeValidation(true, 0, 30).setRequired(true);
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta visita",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,sprayDate,centinela,brigada,supervisor,compVisit,reasonNoVisit,reasonNoVisitOther,reasonReluctant,reasonReluctantOther,modCasa,
        		code,inhabited,ownerName,habitants,material,sprRooms,noSprooms,rooms,noSproomsReasons,sleep,numNets,
        		rociador,personasCharlas,obs);
    }
    
}

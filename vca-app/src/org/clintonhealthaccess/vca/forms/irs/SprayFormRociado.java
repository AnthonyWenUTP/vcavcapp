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
import org.clintonhealthaccess.vca.domain.irs.Visit;
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

public class SprayFormRociado extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private Target meta = null;
	

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
	
    public SprayFormRociado(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	meta = vcaAdapter.getTarget(MainDBConstants.ident + " = '"+ mIdent + "'", null);
    	List<Visit> totalVisitas = vcaAdapter.getVisits(MainDBConstants.target + " = '"+mIdent+"' and "+MainDBConstants.activity+"='SPRAY'", MainDBConstants.visitDate);
    	int totalRociados = 0;
    	for (Visit visit:totalVisitas) {
    		if(visit.getSprayedRooms()!=null) {
    			totalRociados = totalRociados + visit.getSprayedRooms();
    		}
    	}
    	
    	String[] catCompVisit = fillCatalog("CAT_SINO");
    	List<Personal> mRociador = vcaAdapter.getPersonals(null, MainDBConstants.code);
    	String[] catRociadores = new String[mRociador.size()];
    	index = 0;
        for (Personal rociador: mRociador){
        	catRociadores[index] = rociador.getCode()+"-"+rociador.getName();
            index++;
        }
        
        List<Brigada> mBrigada = vcaAdapter.getBrigadas(null, MainDBConstants.code);
    	String[] catBrigadas = new String[mBrigada.size()];
    	index = 0;
        for (Brigada brigada: mBrigada){
        	catBrigadas[index] = brigada.getCode()+"-"+brigada.getName();
            index++;
        }
        
        List<Personal> mSupervisor = vcaAdapter.getPersonals(null, MainDBConstants.code);
    	String[] catSupervisores = new String[mSupervisor.size()];
    	index = 0;
        for (Personal sup: mSupervisor){
        	catSupervisores[index] = sup.getCode()+"-"+sup.getName();
            index++;
        }
        String[] catRNV = fillCatalog("CAT_NO_VISIT");
        String[] catRR = fillCatalog("CAT_RELUCTANT");
        
        int aRociar = meta.getHousehold().getSprRooms() - totalRociados;

        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Agregar visita en la vivienda" , meta.getHousehold().getCode() + "\n" + meta.getHousehold().getOwnerName()
    													+ "\n Cuartos total > " + meta.getHousehold().getRooms()
    													+ "\n Cuartos rociables > " + meta.getHousehold().getSprRooms()
    													+ "\n Cuartos no rociables > " + meta.getHousehold().getNoSprooms()
    													+ "\n Total habitantes > " + meta.getHousehold().getHabitants()
    													+ "\n Total visitas de rociado > " + totalVisitas.size()
    													+ "\n Total cuartos ya rociados > " + totalRociados, Constants.WIZARD, true).setRequired(false);
    	String hintSprRooms = "Numero de cuartos que se rociaron en esta visita. Total de cuartos rociables > " + meta.getHousehold().getSprRooms()+ ". Total de cuartos rociados > "+totalRociados;
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(3);
    	Page sprayDate = new NewDatePage(this,"Fecha de la visita", "Ingrese la fecha en que se visita la vivienda", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page rociador = new SingleFixedChoicePage(this,"Rociador", "Seleccione la persona que realiza esta visita, es un valor requerido", Constants.WIZARD, true).setChoices(catRociadores).setRequired(true);
    	Page brigada = new SingleFixedChoicePage(this,"Brigada", "Seleccione la brigada, es un valor requerido", Constants.WIZARD, true).setChoices(catBrigadas).setRequired(true);
    	Page supervisor = new SingleFixedChoicePage(this,"Supervisor", "Seleccione el supervisor, es un valor requerido", Constants.WIZARD, true).setChoices(catSupervisores).setRequired(true);
    	Page compVisit = new SingleFixedChoicePage(this,"Se logró completar la visita", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catCompVisit).setRequired(true);
    	Page reasonNoVisit = new SingleFixedChoicePage(this,"Porqué no se logró completar la visita", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices(catRNV).setRequired(true);
    	Page reasonNoVisitOther = new TextPage(this,"Motivo no se logro completar, Otro","Pregunte las razones por las que no permite la actividad",Constants.WIZARD,false).setPatternValidation(true, ".{0,250}").setRequired(true);
    	Page reasonReluctant = new SingleFixedChoicePage(this,"Motivo renuente","Pregunte las razones por las que no permite la actividad", Constants.WIZARD, false).setChoices(catRR).setRequired(true);
    	Page reasonReluctantOther = new TextPage(this,"Motivo renuente otros","Pregunte las razones por las que no permite la actividad",Constants.WIZARD,false).setPatternValidation(true, ".{0,250}").setRequired(true);
    	Page sprayedRooms = new NumberPage(this,"Cuartos rociados",hintSprRooms,Constants.WIZARD,false).setRangeValidation(true, 1, aRociar).setRequired(true);
    	Page numCharges = new NumberPage(this,"Numero de cargas","Numero de cargas que se utilizan en la vivienda. se considera cargas utilizadas las que se preparan para rociar en la misma vivienda. Requerido, de 0 a 15",Constants.WIZARD,false).setRangeValidation(true, 0, 15).setRequired(true);
    	Page reasonIncomplete = new MultipleFixedChoicePage(this,"Motivo incompleta", "Porque no se pudo rociar todos los cuartos", Constants.WIZARD, false).setChoices(catRR).setRequired(true);
    	Page supervised = new SingleFixedChoicePage(this,"Supervision", "El rociado fue supervisado en esta vivienda", Constants.WIZARD, false).setChoices("Si", "No").setRequired(true);
    	Page personasCharlas = new NumberPage(this,"Personas charla","Numero de personas que se les brida charlas. Requerido de 0 a 30",Constants.WIZARD,false).setRangeValidation(true, 0, 30).setRequired(true);
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta visita",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,sprayDate,rociador,brigada,supervisor,compVisit,reasonNoVisit,reasonNoVisitOther,reasonReluctant,reasonReluctantOther,
        		sprayedRooms,numCharges,reasonIncomplete,supervised,personasCharlas,obs);
    }
    
}

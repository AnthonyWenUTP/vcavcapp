package org.clintonhealthaccess.vca.forms;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import android.content.Context;


import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.IntegerPage;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.MultipleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.NewDatePage;
import org.clintonhealthaccess.vca.wizard.model.NumberPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class HouseholdForm extends AbstractWizardModel {
	
	int index = 0;
	private HouseholdFormLabels labels;
	private VcaAdapter vcaAdapter;
	//private Localidad localidad = null;

	
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
	
    public HouseholdForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new HouseholdFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	//localidad = vcaAdapter.getLocalidad(MainDBConstants.ident + " = '"+ mIdent + "'", null);
    	String patron = "^\\S{1,100}$";
    	String[] catSino = fillCatalog("CAT_HAB");
    	List<Censador> mCensador = vcaAdapter.getCensadores(null, MainDBConstants.code);
    	String[] catEncuestadores = new String[mCensador.size()];
    	index = 0;
        for (Censador censador: mCensador){
        	catEncuestadores[index] = censador.getCode()+"-"+censador.getName();
            index++;
        }
        String[] catMaterial = fillCatalog("CAT_MAT");
        String[] catRazones = fillCatalog("CAT_RNR");
        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, labels.getIntroMessage() , labels.getIntroMessageHint(), Constants.WIZARD, true).setRequired(false);
    	Page code = new TextPage(this,labels.getCode(),labels.getCodeHint()+" \n" +patron,Constants.WIZARD,true).setPatternValidation(true, patron).setRequired(true);
    	Page censusTaker = new SingleFixedChoicePage(this,labels.getCensusTaker(), labels.getCensusTakerHint(), Constants.WIZARD, true).setChoices(catEncuestadores).setRequired(true);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(3);
    	Page censusDate = new NewDatePage(this,labels.getCensusDate(), labels.getCensusDateHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page inhabited = new SingleFixedChoicePage(this,labels.getInhabited(), labels.getInhabitedHint(), Constants.WIZARD, true).setChoices(catSino).setRequired(true);
    	Page ownerName = new TextPage(this,labels.getOwnerName(),labels.getOwnerNameHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page habitants = new LabelPage(this,labels.getHabitants(),labels.getHabitantsHint(),Constants.WIZARD,false).setRequired(true);
    	Page masculinos = new IntegerPage(this,labels.getMasculinos(),labels.getMasculinosHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 25).setRequired(true);
    	Page femeninos = new IntegerPage(this,labels.getFemeninos(),labels.getFemeninosHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 25).setRequired(true);
    	Page menores5 = new LabelPage(this,labels.getMenores5(),labels.getMenores5Hint(),Constants.WIZARD,false).setRequired(true);
    	Page menores5masc = new IntegerPage(this,labels.getMenores5masc(),labels.getMenores5mascHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page menores5fem = new IntegerPage(this,labels.getMenores5fem(),labels.getMenores5femHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page embarazadas = new IntegerPage(this,labels.getEmbarazadas(),labels.getEmbarazadasHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page material = new SingleFixedChoicePage(this,labels.getMaterial(), labels.getMaterialHint(), Constants.WIZARD, false).setChoices(catMaterial).setRequired(true);	
    	Page sprRooms = new NumberPage(this,labels.getSprRooms(),labels.getSprRoomsHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page noSprooms = new NumberPage(this,labels.getNoSprooms(),labels.getNoSproomsHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page rooms = new LabelPage(this,labels.getRooms(),labels.getRoomsHint(),Constants.WIZARD,false).setRequired(false);
    	Page noSproomsReasons = new MultipleFixedChoicePage(this,labels.getNoSproomsReasons(), labels.getNoSproomsReasonsHint(), Constants.WIZARD, false).setChoices(catRazones).setRequired(true);
    	//Page sleep = new NumberPage(this,labels.getSleep(),labels.getSleepHint(),Constants.WIZARD,true).setRangeValidation(true, 0, 30).setRequired(true);
    	//Page numNets = new NumberPage(this,labels.getNumNets(),labels.getNumNetsHint(),Constants.WIZARD,true).setRangeValidation(true, 0, 30).setRequired(true);
    	Page sitiosDormirCama = new IntegerPage(this,labels.getSitiosDormirCama(),labels.getSitiosDormirCamaHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 10).setRequired(true);
    	Page sitiosDormirHamaca = new IntegerPage(this,labels.getSitiosDormirHamaca(),labels.getSitiosDormirHamacaHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 10).setRequired(true);
    	Page sitiosDormirSuelo = new IntegerPage(this,labels.getSitiosDormirSuelo(),labels.getSitiosDormirSueloHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 10).setRequired(true);
    	Page sitiosDormirOtro = new IntegerPage(this,labels.getSitiosDormirOtro(),labels.getSitiosDormirOtroHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 10).setRequired(true);
    	Page mtildExistentes = new IntegerPage(this,labels.getMtildExistentes(),labels.getMtildExistentesHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	Page mosqSinInsecticida = new IntegerPage(this,labels.getMosqSinInsecticida(),labels.getMosqSinInsecticidaHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 20).setRequired(true);
    	
    	Page personasCharlas = new NumberPage(this,labels.getPersonasCharlas(),labels.getPersonasCharlasHint(),Constants.WIZARD,false).setRangeValidation(true, 0, 30).setRequired(true);
    	Page obs = new TextPage(this,labels.getObs(),labels.getObsHint(),Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,code,censusTaker,censusDate,inhabited,ownerName,masculinos,femeninos,habitants,menores5masc,menores5fem,menores5,embarazadas,material,sprRooms,noSprooms,rooms,noSproomsReasons,
        		sitiosDormirCama,sitiosDormirHamaca,sitiosDormirSuelo,sitiosDormirOtro,mtildExistentes,mosqSinInsecticida,personasCharlas,obs);
    }

	public HouseholdFormLabels getLabels() {
		return labels;
	}

	public void setLabels(HouseholdFormLabels labels) {
		this.labels = labels;
	}
    
}

package org.clintonhealthaccess.vca.forms.mapeo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
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

public class CaseForm extends AbstractWizardModel {
	
	int index = 0;
	private CaseFormLabels labels;
	private VcaAdapter vcaAdapter;

	
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
	
	private String[] fillLocalidades(){
        String[] localidades;
        List<Localidad> mLocalidades = vcaAdapter.getLocalidades("", MainDBConstants.name);
        localidades = new String[mLocalidades.size()];
        index = 0;
        for (Localidad loc: mLocalidades){
            localidades[index] = loc.getName();
            index++;
        }
        return localidades;
    }
	
    public CaseForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new CaseFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String[] catTipo = fillCatalog("CAT_TIPOPRUEBA");
    	String[] catSN = fillCatalog("CAT_SINO");
    	String[] catSexo = fillCatalog("CAT_SEXO");
    	String[] localidades = fillLocalidades();
        vcaAdapter.close();
    	Page introCaseMessage = new LabelPage(this, labels.getIntroCaseMessage() , labels.getIntroCaseMessageHint(), Constants.WIZARD, true).setRequired(false);
    	Page codeCase = new TextPage(this,labels.getCodeCase(),labels.getCodeCaseHint(),Constants.WIZARD,true).setPatternValidation(true, "^\\S{1,100}$").setRequired(true);
    	Page localidad = new SingleFixedChoicePage(this,labels.getLocalidad(), labels.getLocalidadHint(), Constants.WIZARD, true).setChoices(localidades).setRequired(true);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String input = "2020-01-01";
		Date t = null;
		try {
			t = ft.parse(input);  
		} catch (ParseException e) { 
			System.out.println("Unparseable using " + ft); 
		}
		DateMidnight dmDesde = new DateMidnight(t.getTime());
		Page sint = new SingleFixedChoicePage(this,labels.getSintCase(), labels.getSintCaseHint(), Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page fisDate = new NewDatePage(this,labels.getFisDate(), labels.getFisDateHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page mxDate = new NewDatePage(this,labels.getMxDate(), labels.getMxDateHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page mxType = new MultipleFixedChoicePage(this,labels.getMxType(), labels.getMxTypeHint(), Constants.WIZARD, true).setChoices(catTipo).setRequired(true);
    	Page cuiCase = new TextPage(this,labels.getCuiCase(),labels.getCuiCaseHint(),Constants.WIZARD,true).setPatternValidation(true, "^\\S{0,250}$").setRequired(false);
    	Page codE1 = new TextPage(this,labels.getCodE1(),labels.getCodE1Hint(),Constants.WIZARD,true).setPatternValidation(true, "^\\S{0,250}$").setRequired(false);
    	Page codCasa = new TextPage(this,labels.getCodCasa(),labels.getCodCasaHint(),Constants.WIZARD,true).setPatternValidation(true, "^\\S{0,250}$").setRequired(true);
    	Page nameCase = new TextPage(this,labels.getNameCase(),labels.getNameCaseHint(),Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
    	Page sexo = new SingleFixedChoicePage(this,labels.getSexo(), labels.getSexoHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
    	Page edad = new NumberPage(this,labels.getEdad(),labels.getEdadHint(),Constants.WIZARD,true).setRangeValidation(true, 0, 100).setRequired(true);
    	Page embarazada = new SingleFixedChoicePage(this,labels.getEmbarazada(), labels.getEmbarazadaHint(), Constants.WIZARD, false).setChoices(catSN).setRequired(false);
    	Page menor6meses = new SingleFixedChoicePage(this,labels.getMenor6meses(), labels.getMenor6mesesHint(), Constants.WIZARD, false).setChoices(catSN).setRequired(false);
    	Page infoCase = new TextPage(this,labels.getInfoCase(),labels.getInfoCaseHint(),Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
    	
    	return new PageList(introCaseMessage,codeCase,localidad,sint,fisDate,mxDate,mxType,cuiCase,codE1,codCasa,nameCase,sexo,edad,embarazada,menor6meses,infoCase);
    }

	public CaseFormLabels getLabels() {
		return labels;
	}

	public void setLabels(CaseFormLabels labels) {
		this.labels = labels;
	}
    
}

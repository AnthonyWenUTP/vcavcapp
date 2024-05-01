package org.clintonhealthaccess.vca.forms;

import java.util.List;

import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.IntegerPage;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class PersonForm extends AbstractWizardModel {
	
	int index = 0;
	private PersonFormLabels labels;
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
	
    public PersonForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new PersonFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	

    	String[] catSex = fillCatalog("CAT_SEXO");
    	String[] catEmb = fillCatalog("CAT_EMB");
    	String[] catSN = fillCatalog("CAT_SINO");
        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, labels.getIntroMessagePerson() , labels.getIntroMessagePersonHint(), Constants.WIZARD, true).setRequired(false);
    	Page code = new TextPage(this,labels.getCodePerson(),labels.getCodePersonHint(),Constants.WIZARD,true).setRequired(true);
    	Page name = new TextPage(this,labels.getNamePerson(),labels.getNamePersonHint(),Constants.WIZARD,true).setPatternValidation(true, ".{1,255}").setRequired(true);
    	
    	Page sex = new SingleFixedChoicePage(this,labels.getSexPerson(), labels.getSexPersonHint(), Constants.WIZARD, true).setChoices(catSex).setRequired(true);
    	
    	Page age = new IntegerPage(this,labels.getAgePerson(),labels.getAgePersonHint(),Constants.WIZARD,true).setRangeValidation(true, 1, 110).setRequired(true);
    	
    	Page preg = new SingleFixedChoicePage(this,labels.getPregPerson(), labels.getPregPersonHint(), Constants.WIZARD, false).setChoices(catEmb).setRequired(true);
    	Page activo = new SingleFixedChoicePage(this,"Esta persona está viviendo en esta casa?", "Seleccionar no si la persona ya no está viviendo en esta casa.", Constants.WIZARD, false).setChoices(catSN).setRequired(true);
    	
    	Page obs = new TextPage(this,labels.getObsPerson(),labels.getObsPersonHint(),Constants.WIZARD,true).setPatternValidation(true, ".{0,255}").setRequired(false);
        return new PageList(introMessage,code,name,sex,age,preg,activo,obs);
    }

	public PersonFormLabels getLabels() {
		return labels;
	}

	public void setLabels(PersonFormLabels labels) {
		this.labels = labels;
	}
    
}

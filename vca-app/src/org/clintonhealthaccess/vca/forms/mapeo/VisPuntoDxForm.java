package org.clintonhealthaccess.vca.forms.mapeo;

import java.util.Date;
import java.util.List;

import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.NewDatePage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;
import org.joda.time.DateMidnight;

public class VisPuntoDxForm extends AbstractWizardModel {
	
	int index = 0;
	private VisPuntoDxFormLabels labels;
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
	
    public VisPuntoDxForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new VisPuntoDxFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String[] catVisitType = fillCatalog("CAT_TIPOVISPDX");

        vcaAdapter.close();
    	Page introVisPointMessage = new LabelPage(this, labels.getIntroVisPointMessage() , labels.getIntroVisPointMessageHint(), Constants.WIZARD, true).setRequired(false);
		Page visitType = new SingleFixedChoicePage(this,labels.getVisitType(), labels.getVisitTypeHint(), Constants.WIZARD, true).setChoices(catVisitType).setRequired(true);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(180);
		Page visitDate = new NewDatePage(this,labels.getVisitDate(), labels.getVisitDateHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page obs = new TextPage(this,labels.getObs(),labels.getObsHint(),Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(true);
    	return new PageList(introVisPointMessage,visitType,visitDate,obs);
    }

	public VisPuntoDxFormLabels getLabels() {
		return labels;
	}

	public void setLabels(VisPuntoDxFormLabels labels) {
		this.labels = labels;
	}


    
}

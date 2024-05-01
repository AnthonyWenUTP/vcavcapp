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

public class CriaderoTxForm extends AbstractWizardModel {
	
	int index = 0;
	private CriaderoTxFormLabels labels;
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
	
    public CriaderoTxForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new CriaderoTxFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String[] catTratType = fillCatalog("CAT_TIPOTRAT");
        vcaAdapter.close();
    	Page introTxCriaderoMessage = new LabelPage(this, labels.getIntroTxCriaderoMessage() , labels.getIntroTxCriaderoMessageHint(), Constants.WIZARD, true).setRequired(false);
		Page txType = new SingleFixedChoicePage(this,labels.getTxType(), labels.getTxTypeHint(), Constants.WIZARD, true).setChoices(catTratType).setRequired(true);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(180);
		Page txDate = new NewDatePage(this,labels.getTxDate(), labels.getTxDateHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page obsT = new TextPage(this,labels.getObsT(),labels.getObsTHint(),Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(true);
    	return new PageList(introTxCriaderoMessage,txType,txDate,obsT);
    }

	public CriaderoTxFormLabels getLabels() {
		return labels;
	}

	public void setLabels(CriaderoTxFormLabels labels) {
		this.labels = labels;
	}


    
}

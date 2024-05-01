package org.clintonhealthaccess.vca.forms.mapeo;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.IntegerPage;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.NewDatePage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class MuestraForm extends AbstractWizardModel {
	
	int index = 0;
	private MuestraFormLabels labels;
	private VcaAdapter vcaAdapter;

	
    public MuestraForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
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

    @Override
    protected PageList onNewRootPageList() {
    	labels = new MuestraFormLabels();
    	
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String[] localidades = fillLocalidades();
        vcaAdapter.close();
        
    	Page introTestMessage = new LabelPage(this, labels.getIntroTestMessage() , labels.getIntroTestMessageHint(), Constants.WIZARD, true).setRequired(false);
    	Page localidad = new SingleFixedChoicePage(this,labels.getLocalidad(), labels.getLocalidadHint(), Constants.WIZARD, true).setChoices(localidades).setRequired(true);
    	DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(180);
		Page mxDate = new NewDatePage(this,labels.getMxDate(), labels.getMxDateHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page casa = new TextPage(this,labels.getCasa(),labels.getCasaHint(),Constants.WIZARD,true).setPatternValidation(true, "^\\S{0,250}$").setRequired(true);
    	Page mxProactiva = new IntegerPage(this,labels.getMxProactiva(), labels.getMxProactivaHint(), Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
    	Page mxReactiva = new IntegerPage(this,labels.getMxReactiva(), labels.getMxReactivaHint(), Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
    	
    	return new PageList(introTestMessage,localidad,mxDate,casa, mxProactiva,mxReactiva);
    }

	public MuestraFormLabels getLabels() {
		return labels;
	}

	public void setLabels(MuestraFormLabels labels) {
		this.labels = labels;
	}
    
}

package org.clintonhealthaccess.vca.forms.mapeo;

import java.util.List;

import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class PuntoDxForm extends AbstractWizardModel {
	
	int index = 0;
	private PuntoDxFormLabels labels;
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
	
    public PuntoDxForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new PuntoDxFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String[] catTipo = fillCatalog("CAT_TIPOPDX");
    	String[] catEstado = fillCatalog("CAT_ESTADOPDX");
    	String[] localidades = fillLocalidades();
        vcaAdapter.close();
    	Page introPointMessage = new LabelPage(this, labels.getIntroPointMessage() , labels.getIntroPointMessageHint(), Constants.WIZARD, true).setRequired(false);
    	Page localidad = new SingleFixedChoicePage(this,labels.getLocalidad(), labels.getLocalidadHint(), Constants.WIZARD, true).setChoices(localidades).setRequired(true);
    	Page clave = new TextPage(this,labels.getClave(),labels.getClaveHint(),Constants.WIZARD,true).setPatternValidation(true, "^\\S{1,100}$").setRequired(true);
		Page tipo = new SingleFixedChoicePage(this,labels.getTipo(), labels.getTipoHint(), Constants.WIZARD, true).setChoices(catTipo).setRequired(true);
    	Page status = new SingleFixedChoicePage(this,labels.getStatus(), labels.getStatusHint(), Constants.WIZARD, true).setChoices(catEstado).setRequired(true);
    	Page info = new TextPage(this,labels.getInfo(),labels.getInfoHint(),Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
    	return new PageList(introPointMessage,localidad,clave,tipo,status,info);
    }

	public PuntoDxFormLabels getLabels() {
		return labels;
	}

	public void setLabels(PuntoDxFormLabels labels) {
		this.labels = labels;
	}


    
}

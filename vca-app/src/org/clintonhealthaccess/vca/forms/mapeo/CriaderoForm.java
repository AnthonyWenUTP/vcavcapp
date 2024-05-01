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
import org.clintonhealthaccess.vca.wizard.model.MultipleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.NumberPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class CriaderoForm extends AbstractWizardModel {
	
	int index = 0;
	private CriaderoFormLabels labels;
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

	
    public CriaderoForm(Context context, String pass, String mLocalidad) {    	
        super(context,pass,mLocalidad);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new CriaderoFormLabels();
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String[] catTipo = fillCatalog("CAT_TIPOPCR");
    	String[] catEspecie = fillCatalog("CAT_ESPCR");
    	String[] localidades = fillLocalidades();    	
        vcaAdapter.close();
    	
    	
    	Page introCriaderoMessage = new LabelPage(this, labels.getIntroCriaderoMessage() , labels.getIntroCriaderoMessageHint(), Constants.WIZARD, true).setRequired(false);
    	Page localidad = new SingleFixedChoicePage(this,labels.getLocalidad(), labels.getLocalidadHint(), Constants.WIZARD, true).setChoices(localidades).setRequired(true);
    	Page infoC = new TextPage(this,labels.getInfoC(),labels.getInfoCHint(),Constants.WIZARD,true).setPatternValidation(true, "^\\S{0,250}$").setRequired(true);
    	
    	
    	Page tipoC = new SingleFixedChoicePage(this,labels.getTipoC(), labels.getTipoCHint(), Constants.WIZARD, true).setChoices(catTipo).setRequired(true);
    	
    	Page especie = new MultipleFixedChoicePage(this,labels.getEspecie(), labels.getEspecieHint(), Constants.WIZARD, true).setChoices(catEspecie).setRequired(false);
    	
    	Page size = new NumberPage(this,labels.getSize(), labels.getSizeHint(), Constants.WIZARD, true).setRangeValidation(true, 0, 2000).setRequired(false);
    	
		return new PageList(introCriaderoMessage,localidad,infoC,tipoC, especie,size);
    }


	public CriaderoFormLabels getLabels() {
		return labels;
	}


	public void setLabels(CriaderoFormLabels labels) {
		this.labels = labels;
	}

    
}

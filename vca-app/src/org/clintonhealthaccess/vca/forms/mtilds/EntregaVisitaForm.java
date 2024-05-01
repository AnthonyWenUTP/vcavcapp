package org.clintonhealthaccess.vca.forms.mtilds;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.NewDatePage;
import org.clintonhealthaccess.vca.wizard.model.NumberPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class EntregaVisitaForm extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private EntregaTarget meta = null;
	

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
	
    public EntregaVisitaForm(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String filtro=MainDBConstants.ident +"='00000000-70a7-23f2-0000-00003b1c24b6'";
		Household casa = vcaAdapter.getHousehold(filtro, MainDBConstants.ownerName);
		meta =new EntregaTarget("1",new Ciclo("1","01","Ciclo 2019", new Date(), new Date(),4,"Obs"),casa,new Date(),6,5,4,3,2,1,"Pendiente","Obs",15);
    	
        String[] catNV = fillCatalog("CAT_NO_VISIT");
        String[] catSN = fillCatalog("CAT_SINO");

        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Agregar entrega de mosquiteros en la vivienda" , meta.getHousehold().getCode() + "\n" + meta.getHousehold().getOwnerName()
    													, Constants.WIZARD, true).setRequired(false);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(3);
    	Page visitDate = new NewDatePage(this,"Fecha de la visita", "Ingrese la fecha en que se visita la vivienda", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page entrega = new SingleFixedChoicePage(this,"Se pudo instalar mosquiteros?", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page mtildEntregadosCama = new NumberPage(this,"Número de MTILDs entregados/instalados tipo cama","Número de MTILDs entregados/instalados en camas. Requerido, de 0 a " + meta.getSitiosDormirCama(),Constants.WIZARD,false)
    			.setRangeValidation(true, 0, meta.getSitiosDormirCama()).setRequired(true);
    	Page mtildEntregadosHamaca = new NumberPage(this,"Número de MTILDs entregados/instalados tipo hamaca","Número de MTILDs entregados/instalados en hamacas. Requerido, de 0 a  " + meta.getSitiosDormirHamaca(),Constants.WIZARD,false)
    			.setRangeValidation(true, 0, meta.getSitiosDormirHamaca()).setRequired(true);
    	Page mtildEntregadosSuelo = new NumberPage(this,"Número de MTILDs entregados/instalados suelo","Número de MTILDs entregados/instalados en el suelo. Requerido, de 0 a " + meta.getSitiosDormirSuelo(),Constants.WIZARD,false)
    			.setRangeValidation(true, 0, meta.getSitiosDormirSuelo()).setRequired(true);
    	Page mtildEntregadosOtro = new NumberPage(this,"Número de MTILDs entregados/instalados otro","Número de MTILDs entregados/instalados en otro tipo de espacio par dormir. Requerido, de 0 a " + meta.getSitiosDormirOtro(),Constants.WIZARD,false)
    			.setRangeValidation(true, 0, meta.getSitiosDormirOtro()).setRequired(true);
    	Page razonNoEntrega = new SingleFixedChoicePage(this,"Si no pudo instalar todos los mosquiteros, porque no?", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices(catNV).setRequired(true);
    	Page visitaRecuperacion = new SingleFixedChoicePage(this,"Requiere una visita de recuperación?", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta visita",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,visitDate,entrega,mtildEntregadosCama,mtildEntregadosHamaca,
        		mtildEntregadosSuelo,mtildEntregadosOtro,razonNoEntrega,visitaRecuperacion,obs);
    }
    
}

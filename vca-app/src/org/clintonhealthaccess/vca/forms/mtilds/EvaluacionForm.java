package org.clintonhealthaccess.vca.forms.mtilds;

import java.util.Date;
import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
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
import org.joda.time.DateMidnight;

public class EvaluacionForm extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private EntregaTarget meta = null;
	
    public EvaluacionForm(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String filtro=MainDBConstants.ident +"='00000000-70a7-23f2-0000-00003b1c24b6'";
		Household casa = vcaAdapter.getHousehold(filtro, MainDBConstants.ownerName);
		meta =new EntregaTarget("1",new Ciclo("1","01","Ciclo 2019", new Date(), new Date(),4,"Obs"),casa,new Date(),6,5,4,3,2,1,"Pendiente","Obs",15);
		Integer totalMosq = meta.getSitiosDormirCama()+meta.getSitiosDormirHamaca()+meta.getSitiosDormirSuelo()+meta.getSitiosDormirOtro();

        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Evaluación de uso de mosquiteros en la vivienda" , meta.getHousehold().getCode() + "\n" + meta.getHousehold().getOwnerName()
    													, Constants.WIZARD, true).setRequired(false);
    	DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(3);
    	Page visitDate = new NewDatePage(this,"Fecha de la evaluación", "Ingrese la fecha en que se visita la vivienda para la evaluación", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	
    	Page mtildEnUso = new NumberPage(this,"Número de mosquiteros MTILDs en uso para dormir (instalados)","Requerido, de 0 a " + totalMosq,Constants.WIZARD,true).setRangeValidation(true, 0, totalMosq).setRequired(true);
    	
    	Page mtildGuardados = new SingleFixedChoicePage(this,"Existen mosquiteros o MTILDs guardados?", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices("Si","No").setRequired(true);
    	Page numMtildGuardados = new NumberPage(this,"Número de MTILDs guardados","Requerido, de 0 a " + totalMosq, Constants.WIZARD,false).setRangeValidation(false, 0, totalMosq).setRequired(true);
    	Page razonesMtildGuardados = new MultipleFixedChoicePage(this,"Porque los MITIDs estan guardados?", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices("Viejos / Rotos","No hay malaria","No hay mosquitos").setRequired(true);
    	
    	Page mtildFaltantes = new SingleFixedChoicePage(this,"Existen MTILDs faltantes?", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices("Si","No").setRequired(true);
    	Page numMtildFaltantes = new NumberPage(this,"Número de MTILDs faltantes","Requerido, de 0 a " + totalMosq, Constants.WIZARD,false).setRangeValidation(true, 0, totalMosq).setRequired(true);
    	Page razonesMtildFaltantes = new MultipleFixedChoicePage(this,"Razones por las que hay  MTILDs faltantes", "Seleccione, es un valor requerido", Constants.WIZARD, false).
    			setChoices("Desechado","Robado","Regalado","En otro lugar", "Con otro uso").setRequired(true);
    	
    	Page tempUsoMtild = new SingleFixedChoicePage(this,"En que momento del año utiliza los MTILDs?", "Seleccione, es un valor requerido", Constants.WIZARD, true).
    			setChoices("Todo el año","Solo en época seca","Solo en época de lluvia","No sabe").setRequired(true);
    	
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta visita",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,visitDate,mtildEnUso,mtildGuardados,numMtildGuardados,razonesMtildGuardados,mtildFaltantes,numMtildFaltantes,razonesMtildFaltantes,tempUsoMtild,obs);
    }
    
}

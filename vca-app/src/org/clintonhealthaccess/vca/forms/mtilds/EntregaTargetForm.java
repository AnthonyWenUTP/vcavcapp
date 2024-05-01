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
import org.clintonhealthaccess.vca.wizard.model.NumberPage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class EntregaTargetForm extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private EntregaTarget meta = null;
	
    public EntregaTargetForm(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String filtro=MainDBConstants.ident +"='00000000-70a7-23f2-0000-00003b1c24b6'";
		Household casa = vcaAdapter.getHousehold(filtro, MainDBConstants.ownerName);
		meta =new EntregaTarget("1",new Ciclo("1","01","Ciclo 2019", new Date(), new Date(),4,"Obs"),casa,new Date(),4,3,2,1,5,6,"Pendiente","Obs",casa.getHabitants());


        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Editar la meta de entrega en la vivienda" , meta.getHousehold().getCode() + "\n" + meta.getHousehold().getOwnerName()
    													, Constants.WIZARD, true).setRequired(false);

    	Page hab = new NumberPage(this,"Número de habitantes","Número de habitantes en la vivienda. Requerido, de 0 a 20",Constants.WIZARD,true).setRangeValidation(true, 0, 20).setRequired(true);
    	Page sitiosDormirCama = new NumberPage(this,"Número de sitios para dormir tipo cama","Número de espacios para dormir que son camas. Requerido, de 0 a 10",Constants.WIZARD,true).setRangeValidation(true, 0, 10).setRequired(true);
    	Page sitiosDormirHamaca = new NumberPage(this,"Número de sitios para dormir tipo hamaca","Número de espacios para dormir que son hamacas. Requerido, de 0 a 10",Constants.WIZARD,true).setRangeValidation(true, 0, 10).setRequired(true);
    	Page sitiosDormirSuelo = new NumberPage(this,"Número de sitios para dormir tipo suelo","Número de espacios para dormir en el suelo. Requerido, de 0 a 10",Constants.WIZARD,true).setRangeValidation(true, 0, 10).setRequired(true);
    	Page sitiosDormirOtro = new NumberPage(this,"Número de sitios para dormir de otro tipo","Número de espacios para dormir que son de otro tipo. Requerido, de 0 a 10",Constants.WIZARD,true).setRangeValidation(true, 0, 10).setRequired(true);
    	Page mtildExistentes = new NumberPage(this,"Número de MTILDs existentes","Número de MTILDs existentes en la vivienda antes de esta visita. Requerido, de 0 a 20",Constants.WIZARD,true).setRangeValidation(true, 0, 10).setRequired(true);
    	Page mosqSinInsecticida = new NumberPage(this,"Mosquiteros sin insecticida","Número de mosquiteros sin insecticida existentes en la vivienda antes de esta visita. Requerido, de 0 a 20",Constants.WIZARD,true).setRangeValidation(true, 0, 10).setRequired(true);
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta visita",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,hab,sitiosDormirCama,sitiosDormirHamaca,sitiosDormirSuelo,sitiosDormirOtro,mtildExistentes,mosqSinInsecticida,obs);
    }
    
}

package org.clintonhealthaccess.vca.forms.mtilds;

import android.content.Context;

import java.util.Date;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionPersona;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.MultipleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class EvaluacionPersonaForm extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private EvaluacionPersona meta = null;
	
    public EvaluacionPersonaForm(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String filtro=MainDBConstants.ident +"='00000000-70a7-23f2-0000-00003b1c24b6'";
		Household casa = vcaAdapter.getHousehold(filtro, MainDBConstants.ownerName);
		
    	if(mIdent.equals("1")) {
    		meta = new EvaluacionPersona("1",new EntregaTarget("1",new Ciclo("1","01","Ciclo 2019", new Date(), new Date(),4,"Obs"),casa,new Date(),6,5,4,3,2,1,"Nueva","Observaciones en la vivienda",15),new Person("1", casa, "1", "PEDRO", "M", 23, null, ""),"Si",null,"Todas las noches",null);
    	}
    	else if(mIdent.equals("2")) {
    		meta = new EvaluacionPersona("2",new EntregaTarget("1",new Ciclo("1","01","Ciclo 2019", new Date(), new Date(),4,"Obs"),casa,new Date(),6,5,4,3,2,1,"Nueva","Observaciones en la vivienda",15),new Person("2", casa, "2", "JUAN", "M", 29, null, ""),"No","Mal olor","No lo usa del todo",null);
    	}
    	else if(mIdent.equals("3")) {
    		meta = new EvaluacionPersona("3",new EntregaTarget("1",new Ciclo("1","01","Ciclo 2019", new Date(), new Date(),4,"Obs"),casa,new Date(),6,5,4,3,2,1,"Nueva","Observaciones en la vivienda",15),new Person("3", casa, "3", "MARIA", "F", 43, null, ""));
    	}
    	

        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Agregar evaluación de mosquiteros en la persona" , meta.getPersona().getCode() + "\n" + meta.getPersona().getName()
    													, Constants.WIZARD, true).setRequired(false);

    	Page usoNocheAnterior = new SingleFixedChoicePage(this,"Durmió bajo un mosquitero la noche anterior?", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices("Si","No").setRequired(true);
    	Page razoneNoUso = new MultipleFixedChoicePage(this,"Indique las razones para no hacerlo", "Seleccione, es un valor requerido. (Indicar todas las razones que apliquen)", Constants.WIZARD, false)
    			.setChoices("Muy caliente","No me gusta","Siento encerrado","Mal olor","No hay malaria","No hay mosquitos","Muy viejo / roto","No estaba en casa","Me da alergia")
    			.setRequired(true);
    	Page frecuenciaSemana = new SingleFixedChoicePage(this," Durante la ultima semana, con que frequencia ha dormido bajo los MTILDs?", "Seleccione, es un valor requerido", Constants.WIZARD, true)
    			.setChoices("Todas las noches","Mayoría de las noches (5-6)", "Algunas noches (1-4)","No lo usa del todo").setRequired(true);
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta visita",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,usoNocheAnterior,razoneNoUso,frecuenciaSemana,obs);
    }
    
}

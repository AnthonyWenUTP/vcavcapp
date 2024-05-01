package org.clintonhealthaccess.vca.forms.mtilds;

import android.content.Context;

import java.util.Date;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionMosquitero;
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

public class EvaluacionMosquiteroForm extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private EvaluacionMosquitero meta = null;
	
    public EvaluacionMosquiteroForm(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	String filtro=MainDBConstants.ident +"='00000000-70a7-23f2-0000-00003b1c24b6'";
		Household casa = vcaAdapter.getHousehold(filtro, MainDBConstants.ownerName);
		EntregaTarget et = new EntregaTarget("1",new Ciclo("1","01","Ciclo 2019", new Date(), new Date(),4,"Obs"),casa,new Date(),6,5,4,3,2,1,"Nueva","Observaciones en la vivienda",15);
		
    	if(mIdent.equals("1")) {
    		meta = new EvaluacionMosquitero("1",et,"Cama","No");
    	}
    	else if(mIdent.equals("2")) {
    		meta = new EvaluacionMosquitero("2",et,"Hamaca","No");
    	}
    	else if(mIdent.equals("3")) {
    		meta = new EvaluacionMosquitero("3",et,"Cama","No");
    	}
    	

        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Agregar evaluación individual para cada MTILD" , meta.getIdent() + "\n" + meta.getTipo()
    													, Constants.WIZARD, true).setRequired(false);

    	Page estadoEvaluacion = new SingleFixedChoicePage(this,"Cual es el estado de este mosquitero durante la evaluación?", "Seleccione, es un valor requerido", Constants.WIZARD, true).
    			setChoices("Instalado","Guardado","Faltante").setRequired(true);
    	Page razonMtildGuardado = new SingleFixedChoicePage(this,"Porque el MITID está guardado?", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices("Viejos / Rotos","No hay malaria","No hay mosquitos").setRequired(true);
    	
    	Page razonMtildFaltante = new SingleFixedChoicePage(this,"Razón por la que el MTILD es faltante", "Seleccione, es un valor requerido", Constants.WIZARD, false).
    			setChoices("Desechado","Robado","Regalado","En otro lugar", "Con otro uso").setRequired(true);
    	Page usadoAnoche = new SingleFixedChoicePage(this,"Durmieron anoche bajo este MTILD?", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices("Si","No").setRequired(true);
    	Page lavado6Meses = new SingleFixedChoicePage(this,"Fue lavado este mosquitero en los ultimos 6 meses?", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices("Si","No").setRequired(true);
    	
    	Page formaLavado = new MultipleFixedChoicePage(this,"Como hizo el lavado del MTILD?", "Forma de lavado del MTILD. Lavado correcto es con agua y jabon; lavado incorrecto es con agua solamente, con cloro, o con detergente. Seleccione, es un valor requerido", Constants.WIZARD, false)
    			.setChoices("Solo agua","Agua y jabon","Con cloro","Con detergente","Restregado en lavadero").setRequired(true);
    	Page formaSecado = new MultipleFixedChoicePage(this,"Como hizo el secado del MTILD?", "Forma de secado del MTILD. La forma de secado correcta es en la sombra y no en el sol.. Seleccione, es un valor requerido", Constants.WIZARD, false)
    			.setChoices("Extendido en sombra","Extendido al sol","Doblado en sombra","Doblado en sol").setRequired(true);
    	
    	Page manejoNoUso = new SingleFixedChoicePage(this,"Como se maneja el MTILD cuando no esta en uso?", "Seleccione, es un valor requerido", Constants.WIZARD, false)
    			.setChoices("Colgado","Recogido","Guardado").setRequired(true);
    	Page reaccionSecundaria = new MultipleFixedChoicePage(this,"Reaccion secundaria con este MTILD", "Seleccione, es un valor requerido", Constants.WIZARD, false)
    			.setChoices("Irritacion piel","Irritacion ojos","Dificultad al respirar","Nauseas/vomito","Otros").setRequired(true);
    	
    	Page estaRoto = new SingleFixedChoicePage(this,"El MTILD esta roto?", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices("Si","No").setRequired(true);
    	
    	Page razonRoto = new SingleFixedChoicePage(this,"Porque se rompió el MTILD?", "Seleccione, es un valor requerido", Constants.WIZARD, true)
    			.setChoices("Se trabo con objeto","Se quemó","Roto por animales","Roto por ninos","No sabe","Otros").setRequired(true);
    	
    	Page numAgujeros = new NumberPage(this,"Número de agujeros","Requerido, de 0 a 10",Constants.WIZARD,false).setRangeValidation(true, 0, 10).setRequired(true);
    	
    	Page integridadFisica = new MultipleFixedChoicePage(this,"Evaluacion de integridad física", "Observacion y clasificacion de huecos y rotos. Seleccione, es un valor requerido", Constants.WIZARD, false)
    			.setChoices("Tipo 1","Tipo 2","Tipo 3","Tipo 4").setRequired(true);
    	
    	Page agujerosReparados = new SingleFixedChoicePage(this,"Los MTILDs tienen agujeros reparados?", "Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices("Si","No").setRequired(true);
    	Page agujerosReparadosComo = new SingleFixedChoicePage(this,"Como estan reparados?", "Seleccione, es un valor requerido", Constants.WIZARD, false)
    			.setChoices("Cosido","Parchado","Nudo").setRequired(true);
    	
    	Page colectado = new SingleFixedChoicePage(this,"MTILD colectado", "Colecta del MTILD para biodisponibilidad. Seleccione, es un valor requerido", Constants.WIZARD, false).setChoices("Si","No").setRequired(true);
    	
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta visita",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,estadoEvaluacion,razonMtildGuardado,razonMtildFaltante,usadoAnoche,
        		lavado6Meses,formaLavado,formaSecado,manejoNoUso,reaccionSecundaria,estaRoto,razonRoto,numAgujeros,integridadFisica,agujerosReparados,agujerosReparadosComo,colectado,obs);
    }
    
}

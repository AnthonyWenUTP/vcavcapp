package org.clintonhealthaccess.vca.forms.irs;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import android.content.Context;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.wizard.model.AbstractWizardModel;
import org.clintonhealthaccess.vca.wizard.model.LabelPage;
import org.clintonhealthaccess.vca.wizard.model.NewDatePage;
import org.clintonhealthaccess.vca.wizard.model.Page;
import org.clintonhealthaccess.vca.wizard.model.PageList;
import org.clintonhealthaccess.vca.wizard.model.SingleFixedChoicePage;
import org.clintonhealthaccess.vca.wizard.model.TextPage;

public class SupervisionForm extends AbstractWizardModel {
	
	int index = 0;
	private VcaAdapter vcaAdapter;
	private Target meta = null;
	

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
	
    public SupervisionForm(Context context, String pass, String ident) {    	
        super(context,pass,ident);
    }

    @Override
    protected PageList onNewRootPageList() {
    	this.vcaAdapter = new VcaAdapter(mContext, mPass, false, false);
    	vcaAdapter.open();
    	meta = vcaAdapter.getTarget(MainDBConstants.ident + " = '"+ mIdent + "'", null);
    	
    	
    	String[] catSiNo = fillCatalog("CAT_SINO");
    	List<Personal> mRociador = vcaAdapter.getPersonals(MainDBConstants.sprayer + "=1", MainDBConstants.code);
    	String[] catRociadores = new String[mRociador.size()];
    	index = 0;
        for (Personal rociador: mRociador){
        	catRociadores[index] = rociador.getCode()+"-"+rociador.getName();
            index++;
        }
        
        List<Personal> mSupervisor = vcaAdapter.getPersonals(MainDBConstants.supervisor + "=1", MainDBConstants.code);
    	String[] catSupervisores = new String[mSupervisor.size()];
    	index = 0;
        for (Personal sup: mSupervisor){
        	catSupervisores[index] = sup.getCode()+"-"+sup.getName();
            index++;
        }

        vcaAdapter.close();
    	Page introMessage = new LabelPage(this, "Agregar visita en la vivienda" , meta.getHousehold().getCode() + "\n" + meta.getHousehold().getOwnerName()
    													+ "\n Cuartos total > " + meta.getHousehold().getRooms()
    													+ "\n Cuartos rociables > " + meta.getHousehold().getSprRooms()
    													+ "\n Cuartos no rociables > " + meta.getHousehold().getNoSprooms()
    													+ "\n Total habitantes > " + meta.getHousehold().getHabitants()
    													, Constants.WIZARD, true).setRequired(false);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(3);
    	Page supervisionDate = new NewDatePage(this,"Fecha de la supervisión", "Ingrese la fecha en que se supervisa el rociado", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page supervisor = new SingleFixedChoicePage(this,"Supervisor", "Seleccione el supervisor, es un valor requerido", Constants.WIZARD, true).setChoices(catSupervisores).setRequired(true);
    	Page rociador = new SingleFixedChoicePage(this,"Rociador", "Seleccione el rociador, es un valor requerido", Constants.WIZARD, true).setChoices(catRociadores).setRequired(true);
    	Page usoEqProt = new SingleFixedChoicePage(this,"El equpo de protección está bien llevado", "Seguridad - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page eqProtBien = new SingleFixedChoicePage(this,"El equipo de protección está en buen estado", "Seguridad - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page numIden = new SingleFixedChoicePage(this,"Pone su número de identificación y lo hace correctamente", "Organización - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page aguaOp = new SingleFixedChoicePage(this,"Solicita agua oportunamente", "Organización -Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page prepViv = new SingleFixedChoicePage(this,"Comprueba la preparación de la vivienda", "Organización -Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page coopPrepViv = new SingleFixedChoicePage(this,"Coopera en la preparación de la vivienda", "Organización -Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page mezcla = new SingleFixedChoicePage(this,"Mezcla", "Técnica de rociado - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page aguaAdec = new SingleFixedChoicePage(this,"Aplica la cantidad adecuada de agua", "Técnica de rociado - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page mezclaPrep = new SingleFixedChoicePage(this,"Prepara bien la mezcla", "Técnica de rociado - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page agitaBomba = new SingleFixedChoicePage(this,"Agita correctamente la bomba", "Técnica de rociado - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page bombaCerrada = new SingleFixedChoicePage(this,"La bomba está cerrada correctamente", "Bomba - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page bombaPresion = new SingleFixedChoicePage(this,"La bomba tiene la presión correcta (55psi)", "Bomba - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page compruebaBomba = new SingleFixedChoicePage(this,"Comprueba su funcionamiento antes de rociar", "Bomba - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page colocApropiada = new SingleFixedChoicePage(this,"Se coloca adecuadamente", "Distancia - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page distApropiada = new SingleFixedChoicePage(this,"Calcula la distancia adecuada", "Distancia - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page distBoquilla = new SingleFixedChoicePage(this,"Mantiene la distancia de la boquilla al momento de rociar", "Distancia - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page pasoFrente = new SingleFixedChoicePage(this,"Da un paso al frente en las superficies altas", "Distancia - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page mantRitmo = new SingleFixedChoicePage(this,"Mantiene el ritmo al rociar", "Velocidad - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page metConteo = new SingleFixedChoicePage(this,"Utiliza el método del conteo", "Velocidad - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page velocSuperficies = new SingleFixedChoicePage(this,"Mantiene volocidad adecuada en las superficies", "Velocidad - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page supFajas = new SingleFixedChoicePage(this,"Superpone las fajas correctamente", "Fajas - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page pasosLaterales = new SingleFixedChoicePage(this,"Da los pasos laterales de 70 cm aproximadamente", "Fajas - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page salvarObstaculos = new SingleFixedChoicePage(this,"Tiene habilidad para salvar obstáculos", "Fajas - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page bienRociado = new SingleFixedChoicePage(this,"Rocía bien las superficies", "Calidad de trabajo - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page supInvertidas = new SingleFixedChoicePage(this,"Rocía las superficies invertidas", "Calidad de trabajo - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page objPiso = new SingleFixedChoicePage(this,"Rocía objetos en el piso", "Calidad de trabajo - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page reportaConsumoAprop = new SingleFixedChoicePage(this,"La cantidad de cargas reportadas corresponde a la cantidad de producto utilizado", "Consumo de insecticidas - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page transEqAprop = new SingleFixedChoicePage(this,"Transporta el equipo correctamente", "Otros aspectos - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page eqCompleto = new SingleFixedChoicePage(this,"Lleva equipo completo", "Otros aspectos - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page cuidaMatEq = new SingleFixedChoicePage(this,"Cuida sus materiales y equipos", "Otros aspectos - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page buenAspPersonal = new SingleFixedChoicePage(this,"Su aspecto personal es bueno", "Conducta personal - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page cumpleInstrucciones = new SingleFixedChoicePage(this,"Cumple con las instrucciones que recibe", "Conducta personal - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page aceptaSuperv = new SingleFixedChoicePage(this,"Acepta la supervisión", "Conducta personal - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page respetuoso = new SingleFixedChoicePage(this,"Es respetuoso con sus compañeros, superiores y habitantes de las viviendas", "Conducta personal - Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	Page camp = new SingleFixedChoicePage(this,"Realizó charlas educativas", "Seleccione, es un valor requerido", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
    	
    	Page obs = new TextPage(this,"Observaciones","Cualquier observacion en esta supervisión",Constants.WIZARD,true).setPatternValidation(true, ".{0,250}").setRequired(false);
        return new PageList(introMessage,supervisionDate,supervisor,rociador,usoEqProt,eqProtBien,numIden,aguaOp,prepViv,coopPrepViv,
        		mezcla,aguaAdec,mezclaPrep,agitaBomba,bombaCerrada,bombaPresion,compruebaBomba,colocApropiada,distApropiada,distBoquilla,
        		pasoFrente,mantRitmo,metConteo,velocSuperficies,supFajas,pasosLaterales,salvarObstaculos,bienRociado,supInvertidas,objPiso,
        		reportaConsumoAprop,transEqAprop,eqCompleto,cuidaMatEq,buenAspPersonal,cumpleInstrucciones,aceptaSuperv,respetuoso,camp,obs);
    }
    
}

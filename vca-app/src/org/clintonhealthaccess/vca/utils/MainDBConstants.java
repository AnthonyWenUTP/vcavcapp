package org.clintonhealthaccess.vca.utils;

/**
 * Constantes usadas en la base de datos de la aplicacion
 * 
 * @author William Aviles
 * 
 */
public class MainDBConstants {
	
	//Base de datos y tablas
	public static final String DATABASE_NAME = "vcacryp.sqlite3";
	public static final int DATABASE_VERSION = 6;
	
	//Campos entidades
	public static final String ident = "ident";
	public static final String code = "code";
	public static final String name = "name";
	
	
	//Campos metadata
	public static final String recordDate = "recordDate";
	public static final String recordUser = "recordUser";
	public static final String pasive = "pasive";
	public static final String deviceId = "identificador_equipo";
	public static final String estado = "estado";
	
	//Tabla usuarios
	public static final String USER_TABLE = "users";
	//Campos usuarios
	public static final String username = "username";
	public static final String created = "created";
	public static final String modified = "modified";
	public static final String lastAccess = "lastaccess";
	public static final String password = "password";
	public static final String completeName = "completename";
	public static final String email = "email";
	public static final String enabled = "enabled";
	public static final String accountNonExpired = "accountnonexpired";
	public static final String credentialsNonExpired = "credentialsnonexpired";
	public static final String lastCredentialChange = "lastcredentialchange";
	public static final String accountNonLocked = "accountnonlocked";
	public static final String changePasswordNextLogin = "changePasswordNextLogin";
	public static final String createdBy = "createdby";
	public static final String modifiedBy = "modifiedby";
	//Crear tabla usuarios
	public static final String CREATE_USER_TABLE = "create table if not exists "
			+ USER_TABLE + " ("
			+ username + " text not null, "  
			+ created + " date, " 
			+ modified + " date, "
			+ lastAccess + " date, "
			+ password + " text not null, "
			+ completeName + " text, "
			+ email + " text, "
			+ enabled  + " boolean, " 
			+ accountNonExpired  + " boolean, "
			+ credentialsNonExpired  + " boolean, "
			+ lastCredentialChange + " date, "
			+ accountNonLocked  + " boolean, "
			+ changePasswordNextLogin  + " boolean, "
			+ createdBy + " text, "
			+ modifiedBy + " text, "
			+ "primary key (" + username + "));";
	
	//Tabla usuarios roles
	public static final String ROLE_TABLE = "roles";
	//Campos roles
	public static final String role = "role";
	//Crear tabla roles
	public static final String CREATE_ROLE_TABLE = "create table if not exists "
			+ ROLE_TABLE + " ("
			+ username + " text not null, "  
			+ role + " text not null, "
			+ "primary key (" + username + "," + role + "));";	
	
	//Tabla mensajes
	public static final String MESSAGES_TABLE = "mensajes";
	//Campos mensajes
	public static final String messageKey = "messageKey";
	public static final String catRoot = "catRoot";
	public static final String catKey = "catKey";
	public static final String isCat = "isCat";
	public static final String order = "orden";
	public static final String spanish = "spanish";
	public static final String english = "english";
	
	//Crear tabla mensajes
	public static final String CREATE_MESSAGES_TABLE = "create table if not exists "
			+ MESSAGES_TABLE + " ("
			+ messageKey + " text not null, "
			+ catRoot + " text , "
			+ catKey + " text, "
			+ isCat + " text , "
			+ order + " integer , "
			+ spanish + " text not null, "
			+ english + " text , "
			+ MainDBConstants.pasive + " text, "
			+ "primary key (" + messageKey + "));";	
	
	//Tabla areas
	public static final String AREAS_TABLE = "areas";
	
	//Crear tabla areas
	public static final String CREATE_AREAS_TABLE = "create table if not exists "
			+ AREAS_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text , "
			+ name + " text, "
			+ "primary key (" + ident + "));";	
	
	//Tabla distritos
	public static final String DISTRITOS_TABLE = "distritos";
	
	//Campos distritos
	public static final String area = "area";
	
	//Crear tabla distritos
	public static final String CREATE_DISTRITOS_TABLE = "create table if not exists "
			+ DISTRITOS_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text , "
			+ name + " text, "
			+ area + " text, "
			+ "primary key (" + ident + "));";	
	
	//Tabla localidades
	public static final String LOCALIDADES_TABLE = "localidades";
	
	//Campos localidades
	public static final String distrito = "distrito";
	public static final String latitude = "latitude";
	public static final String longitude = "longitude";
	public static final String zoom = "zoom";
	public static final String population = "population";
	public static final String pattern = "pattern";
	public static final String obs = "obs";
	public static final String acceso = "acceso";
	
	//Crear tabla localidades
	public static final String CREATE_LOCALIDADES_TABLE = "create table if not exists "
			+ LOCALIDADES_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text , "
			+ name + " text, "
			+ distrito + " text, "
			+ latitude + " real, "
			+ longitude + " real, "
			+ zoom + " integer, "
			+ population + " integer, "
			+ pattern + " text, "
			+ obs + " text, "
			+ acceso + " boolean,"
			+ "primary key (" + ident + "));";	
	
	//Tabla censadores
	public static final String CENSADORES_TABLE = "censadores";
	
	//Crear tabla censadores
	public static final String CREATE_CENSADORES_TABLE = "create table if not exists "
			+ CENSADORES_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text , "
			+ name + " text, "
			+ "primary key (" + ident + "));";	
	
	//Tabla personal
	public static final String PERSONAL_TABLE = "personal";
	
	//Campos persona
	public static final String sprayer = "sprayer";
	public static final String sentinel = "sentinel";
	public static final String supervisortip = "supervisor";
	
	//Crear tabla censadores
	public static final String CREATE_PERSONAL_TABLE = "create table if not exists "
			+ PERSONAL_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text , "
			+ name + " text, "
			+ sprayer + " boolean,"
			+ sentinel + " boolean,"
			+ supervisortip + " boolean,"
			+ "primary key (" + ident + "));";

	//Tabla brigadas
	public static final String BRIGADAS_TABLE = "brigadas";
	
	//Crear tabla brigadas
	public static final String CREATE_BRIGADAS_TABLE = "create table if not exists "
			+ BRIGADAS_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text , "
			+ name + " text, "
			+ "primary key (" + ident + "));";	
	
	//Tabla viviendas
	public static final String VIVIENDAS_TABLE = "viviendas";
	
	//Tabla viviendas anteriores
	public static final String VIVIENDAS2_TABLE = "viviendas2";
	
	//Campos viviendas
	public static final String local = "local";
	public static final String censusTaker = "censusTaker";
	public static final String censusDate = "censusDate";
	public static final String inhabited = "inhabited";
	public static final String ownerName = "ownerName";
	public static final String habitants = "habitants";
	public static final String material = "material";
	public static final String rooms = "rooms";
	public static final String sprRooms = "sprRooms";
	public static final String noSprooms = "noSprooms";
	public static final String noSproomsReasons = "noSproomsReasons";
	public static final String sleep = "sleep";
	public static final String numNets = "numNets";
	public static final String personasCharlas = "personasCharlas";
	public static final String altitud = "altitud";
	public static final String exactitud = "exactitud";
	public static final String verif = "verified";
	public static final String masculinos = "masculinos";
	public static final String femeninos = "femeninos";
	public static final String menores5 = "menores5";
	public static final String menores5masc = "menores5masc";
	public static final String menores5fem = "menores5fem";
	public static final String embarazadas = "embarazadas";
	public static final String sitiosDormirCama = "sitiosDormirCama";
	public static final String sitiosDormirHamaca = "sitiosDormirHamaca";
	public static final String sitiosDormirSuelo = "sitiosDormirSuelo";
	public static final String sitiosDormirOtro = "sitiosDormirOtro";
	public static final String mtildExistentes = "mtildExistentes";
	public static final String mosqSinInsecticida = "mosqSinInsecticida";

	
	//Crear tabla viviendas
	public static final String CREATE_VIVIENDAS_TABLE = "create table if not exists "
			+ VIVIENDAS_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text not null, "
			+ local + " text not null, "
			+ censusTaker + " text not null, "
			+ censusDate + " date not null, "
			+ inhabited + " text not null, "
			+ ownerName + " text, "
			+ habitants + " integer, "
			+ material + " text, "
			+ rooms + " text, "
			+ sprRooms + " text, "
			+ noSprooms + " text, "
			+ noSproomsReasons + " text, "
			+ sleep + " text, "
			+ numNets + " text, "
			+ personasCharlas + " text, "
			+ latitude + " real, "
			+ longitude + " real, "
			+ altitud + " real, "
			+ exactitud + " real, "
			+ obs + " text, "
			+ verif + " text, "
			+ masculinos + " integer, "
			+ femeninos + " integer, "
			+ menores5 + " integer, "
			+ menores5masc + " integer, "
			+ menores5fem + " integer, "
			+ embarazadas + " integer, "
			+ sitiosDormirCama + " integer, "
			+ sitiosDormirHamaca + " integer, "
			+ sitiosDormirSuelo + " integer, "
			+ sitiosDormirOtro + " integer, "
			+ mtildExistentes + " integer, "
			+ mosqSinInsecticida + " integer, "			
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
			+ "primary key (" + ident + "));";		
	
	
	//Crear tabla viviendas
	public static final String CREATE_VIVIENDAS2_TABLE = "create table if not exists "
			+ VIVIENDAS2_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text not null, "
			+ local + " text not null, "
			+ censusTaker + " text not null, "
			+ censusDate + " date not null, "
			+ inhabited + " text not null, "
			+ ownerName + " text, "
			+ habitants + " integer, "
			+ material + " text, "
			+ rooms + " text, "
			+ sprRooms + " text, "
			+ noSprooms + " text, "
			+ noSproomsReasons + " text, "
			+ sleep + " text, "
			+ numNets + " text, "
			+ personasCharlas + " text, "
			+ latitude + " real, "
			+ longitude + " real, "
			+ altitud + " real, "
			+ exactitud + " real, "
			+ obs + " text, "
			+ verif + " text, "
			+ masculinos + " integer, "
			+ femeninos + " integer, "
			+ menores5 + " integer, "
			+ menores5masc + " integer, "
			+ menores5fem + " integer, "
			+ embarazadas + " integer, "
			+ sitiosDormirCama + " integer, "
			+ sitiosDormirHamaca + " integer, "
			+ sitiosDormirSuelo + " integer, "
			+ sitiosDormirOtro + " integer, "
			+ mtildExistentes + " integer, "
			+ mosqSinInsecticida + " integer, "			
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
			+ "primary key (" + ident + "));";		

	
	//Tabla temporadas
	public static final String TEMPORADAS_TABLE = "temporadas";
	
	//Campos temporadas
	public static final String startDate = "startDate";
	public static final String endDate = "endDate";
	public static final String numberDays = "numberDays";

	//Crear tabla temporadas
	public static final String CREATE_TEMPORADAS_TABLE = "create table if not exists "
			+ TEMPORADAS_TABLE + " ("
			+ ident + " text not null, "
			+ code + " text , "
			+ name + " text, "
			+ startDate + " date, " 
			+ endDate + " date, " 
			+ numberDays + " integer, " 
			+ obs + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
			+ "primary key (" + ident + "));";
	
	//Tabla metas
	public static final String METAS_TABLE = "metas";
	
	//Campos metas
	public static final String irsSeason = "irsSeason";
	public static final String household = "household";
	public static final String sprayStatus = "sprayStatus";
	public static final String lastModified = "lastModified";
	public static final String assignedTo = "assignedTo";

	//Crear tabla metas
	public static final String CREATE_METAS_TABLE = "create table if not exists "
			+ METAS_TABLE + " ("
			+ ident + " text not null, "
			+ irsSeason + " text , "
			+ household + " text, "
			+ lastModified + " date, " 
			+ sprayStatus + " text, "
			+ assignedTo + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
			+ "primary key (" + ident + "));";	
	
	//Tabla visitas
	public static final String VISITAS_TABLE = "visitas";
	
	//Campos visitas
	public static final String target = "target";
	public static final String visitDate = "visitDate";
	public static final String visitor = "visitor";
	public static final String supervisor = "supervisor";
	public static final String brigada = "brigada";
	public static final String visit = "visit";
	public static final String activity = "activity";
	public static final String compVisit = "compVisit";
	public static final String reasonNoVisit = "reasonNoVisit";
	public static final String reasonNoVisitOther = "reasonNoVisitOther";
	public static final String reasonReluctant = "reasonReluctant";
	public static final String reasonReluctantOther = "reasonReluctantOther";
	public static final String sprayedRooms = "sprayedRooms";
	public static final String numCharges = "numCharges";
	public static final String reasonIncomplete = "reasonIncomplete";
	public static final String supervised = "supervised";
	

	//Crear tabla visitas
	public static final String CREATE_VISITAS_TABLE = "create table if not exists "
			+ VISITAS_TABLE + " ("
			+ ident + " text not null, "
			+ target + " text , "
			+ visitDate + " date, " 
			+ visitor + " text, "
			+ supervisor + " text, "
			+ brigada + " text, "
			+ visit + " text, "
			+ activity + " text, "
			+ compVisit + " text, "
			+ reasonNoVisit + " text, "
			+ reasonNoVisitOther + " text, "
			+ reasonReluctant + " text, "
			+ reasonReluctantOther + " text, "
			+ sprayedRooms + " integer, "
			+ numCharges + " integer, "
			+ reasonIncomplete + " text, "
			+ supervised + " text, "
			+ personasCharlas + " integer, "
			+ obs + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
			+ "primary key (" + ident + "));";
	
	//Tabla supervision
	public static final String SUPERVISION_TABLE = "supervisiones";
	
	//Campos supervision
	public static final String supervisionDate = "supervisionDate";
	public static final String rociador = "rociador";
	public static final String usoEqProt = "usoEqProt";
	
	public static final String eqProtBien = "eqProtBien";
	public static final String numIden = "numIden";
	public static final String aguaOp = "aguaOp";
	public static final String prepViv = "prepViv";
	public static final String coopPrepViv = "coopPrepViv";
	
	public static final String mezcla = "mezcla";
	public static final String aguaAdec = "aguaAdec";
	public static final String mezclaPrep = "mezclaPrep";
	public static final String agitaBomba = "agitaBomba";
	public static final String bombaCerrada = "bombaCerrada";
	
	public static final String bombaPresion = "bombaPresion";
	public static final String compruebaBomba = "compruebaBomba";
	public static final String colocApropiada = "colocApropiada";
	public static final String distApropiada = "distApropiada";
	public static final String distBoquilla = "distBoquilla";
	
	public static final String pasoFrente = "pasoFrente";
	public static final String mantRitmo = "mantRitmo";
	public static final String metConteo = "metConteo";
	public static final String velocSuperficies = "velocSuperficies";
	public static final String supFajas = "supFajas";
	
	public static final String pasosLaterales = "pasosLaterales";
	public static final String salvarObstaculos = "salvarObstaculos";
	public static final String bienRociado = "bienRociado";
	public static final String supInvertidas = "supInvertidas";
	public static final String objPiso = "objPiso";

	public static final String reportaConsumoAprop = "reportaConsumoAprop";
	public static final String transEqAprop = "transEqAprop";
	public static final String eqCompleto = "eqCompleto";
	public static final String cuidaMatEq = "cuidaMatEq";
	public static final String buenAspPersonal = "buenAspPersonal";
	
	public static final String cumpleInstrucciones = "cumpleInstrucciones";
	public static final String aceptaSuperv = "aceptaSuperv";
	public static final String respetuoso = "respetuoso";
	public static final String camp = "camp";

	
	//Crear tabla supervision
	public static final String CREATE_SUPERVISION_TABLE = "create table if not exists "
			+ SUPERVISION_TABLE + " ("
			+ ident + " text not null, "
			+ target + " text , "
			+ supervisionDate + " date, " 
			+ rociador + " text, "
			+ supervisor + " text, "
			+ usoEqProt + " text, "
			+ eqProtBien + " text, "
			+ numIden + " text, "
			+ aguaOp + " text, "
			+ prepViv + " text, "
			+ coopPrepViv + " text, "
			+ mezcla + " text, "
			+ aguaAdec + " text, "
			+ mezclaPrep + " text, "
			+ agitaBomba + " text, "
			+ bombaCerrada + " text, "
			+ bombaPresion + " text, "
			+ compruebaBomba + " text, "
			+ colocApropiada + " text, "
			+ distApropiada + " text, "
			+ distBoquilla + " text, "
			+ pasoFrente + " text, "
			+ mantRitmo + " text, "
			+ metConteo + " text, "
			+ velocSuperficies + " text, "
			+ supFajas + " text, "
			+ pasosLaterales + " text, "
			+ salvarObstaculos + " text, "
			+ bienRociado + " text, "
			+ supInvertidas + " text, "
			+ objPiso + " text, "
			+ reportaConsumoAprop + " text, "
			+ transEqAprop + " text, "
			+ eqCompleto + " text, "
			+ cuidaMatEq + " text, "
			+ buenAspPersonal + " text, "
			+ cumpleInstrucciones + " text, "
			+ aceptaSuperv + " text, "
			+ respetuoso + " text, "
			+ camp + " text, "
			+ obs + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
			+ "primary key (" + ident + "));";
	
	//Tabla personas
	public static final String PERSONS_TABLE = "persons";
	//Campos personas
	public static final String codePerson = "codePerson";
	public static final String namePerson = "namePerson";
	public static final String agePerson = "agePerson";
	public static final String sexPerson = "sexPerson";
	public static final String pregPerson = "pregPerson";
	
	//Crear tabla persona
	public static final String CREATE_PERSON_TABLE = "create table if not exists "
			+ PERSONS_TABLE + " ("
			+ ident + " text not null, "
			+ household + " text not null, "
			+ codePerson + " text not null, "
			+ namePerson + " text not null, "
			+ sexPerson + " text not null, "
			+ agePerson + " integer, "
			+ pregPerson + " text , "
			+ obs + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
			+ "primary key (" + ident + "));";


	//Tabla casos
	public static final String CASOS_TABLE = "cases";
	//Campos casos
	public static final String codigo = "codigo";
	public static final String cui = "cui";
	public static final String codE1 = "codE1";
	public static final String casa = "casa";
	public static final String nombre = "nombre";
	public static final String sexo = "sexo";
	public static final String edad = "edad";
	public static final String embarazada = "embarazada";
	public static final String menor6meses = "menor6meses";
	public static final String sint = "sint";
	public static final String fisDate = "fisDate";
	public static final String mxDate = "mxDate";
	public static final String mxType = "mxType";
	public static final String inv = "inv";
	public static final String invDate = "invDate";
	public static final String invCompDate = "invCompDate";
	public static final String tx = "tx";
	public static final String txResultType = "txResultType";
	public static final String txSup = "txSup";
	public static final String txSusp = "txSusp";
	public static final String txSuspDate = "txSuspDate";
	public static final String txSuspReason = "txSuspReason";
	public static final String txSuspOtherReason = "txSuspOtherReason";
	public static final String txDate = "txDate";
	public static final String txComp = "txComp";
	public static final String txCompDate = "txCompDate";
	public static final String sx = "sx";
	public static final String sxDate = "sxDate";
	public static final String sxResult = "sxResult";
	public static final String sxComp = "sxComp";
	public static final String sxCompDate = "sxCompDate";
	public static final String sxCompResult = "sxCompResult";
	public static final String lostFollowUp = "lostFollowUp";
	public static final String lostFollowUpReason = "lostFollowUpReason";
	public static final String lostFollowUpOtherReason = "lostFollowUpOtherReason";
	public static final String estadocaso = "estadocaso";
	public static final String info = "info";
	
	public static final String dayTx01 = "dayTx01";
	public static final String dayTx02 = "dayTx02";
	public static final String dayTx03 = "dayTx03";
	public static final String dayTx04 = "dayTx04";
	public static final String dayTx05 = "dayTx05";
	public static final String dayTx06 = "dayTx06";
	public static final String dayTx07 = "dayTx07";
	public static final String dayTx08 = "dayTx08";
	public static final String dayTx09 = "dayTx09";
	public static final String dayTx10 = "dayTx10";
	public static final String dayTx11 = "dayTx11";
	public static final String dayTx12 = "dayTx12";
	public static final String dayTx13 = "dayTx13";
	public static final String dayTx14 = "dayTx14";
	
	public static final String latitudeOrigin = "latitudeOrigin";
	public static final String longitudeOrigin = "longitudeOrigin";
	public static final String zoomOrigin = "zoomOrigin";
	
	
	//Crear tabla persona
	public static final String CREATE_CASOS_TABLE = "create table if not exists "
			+ CASOS_TABLE + " ("
			+ ident + " text not null, "
			+ local + " text not null, "
			+ codigo + " text not null, "
			+ cui + " text, "
			+ codE1 + " text, "
			+ casa + " text , "
			+ nombre + " text, "
			+ edad + " integer, "
			+ sexo + " text, "
			+ embarazada + " text, "
			+ menor6meses + " text, "
			+ sint + " text , "
			+ fisDate + " date, "
			+ mxDate + " date, "
			+ mxType + " text, "
			+ inv + " text , "
			+ invDate + " date, "
			+ invCompDate + " date, "
			+ tx + " text , "
			+ txResultType + " text , "
			+ txSup + " text , "
			+ txDate + " date, "
			+ txSusp + " text , "
			+ txSuspDate + " date, "
			+ txSuspReason + " text , "
			+ txSuspOtherReason + " text , "
			+ txComp + " text , "
			+ txCompDate + " date, "
			+ sx + " text , "
			+ sxResult + " text , "
			+ sxDate + " date, "
			+ sxComp + " text , "
			+ sxCompResult + " text , "
			+ sxCompDate + " date, " 
			+ lostFollowUp + " text , "
			+ lostFollowUpReason + " text , "
			+ lostFollowUpOtherReason + " text , "
			+ estadocaso + " text , "
			+ info + " text , "
			+ latitude + " real, "
			+ longitude + " real, "
			+ altitud + " real, "
			+ exactitud + " real, "
			+ zoom + " integer, "
			+ dayTx01 + " date, " 
			+ dayTx02 + " date, "
			+ dayTx03 + " date, "
			+ dayTx04 + " date, "
			+ dayTx05 + " date, "
			+ dayTx06 + " date, "
			+ dayTx07 + " date, "
			+ dayTx08 + " date, "
			+ dayTx09 + " date, "
			+ dayTx10 + " date, "
			+ dayTx11 + " date, "
			+ dayTx12 + " date, "
			+ dayTx13 + " date, "
			+ dayTx14 + " date, "
			+ latitudeOrigin + " real, "
			+ longitudeOrigin + " real, "
			+ zoomOrigin + " integer, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text, "
            + " UNIQUE(codigo)"
			+ " primary key (" + ident + "));";	
	
	//Tabla muestras
	public static final String TESTS_TABLE = "tests";	
	//Campos muestras
	public static final String mxProactiva = "mxProactiva";
	public static final String mxReactiva = "mxReactiva";
	
	//Crear tabla muestra
	public static final String CREATE_TESTS_TABLE = "create table if not exists "
				+ TESTS_TABLE + " ("
				+ ident + " text not null, "
				+ local + " text not null, "
				+ casa + " text, "
				+ mxProactiva + " integer , "
				+ mxReactiva + " integer , "
				+ mxDate + " date, "
				+ latitude + " real, "
				+ longitude + " real, "
				+ altitud + " real, "
				+ exactitud + " real, "
				+ zoom + " integer, "
				+ recordDate + " date, " 
				+ recordUser + " text, "
				+ pasive + " text, "
				+ deviceId + " text, "
	            + estado + " text, "
	            + " UNIQUE(local,casa,mxDate)"
				+ " primary key (" + ident + "));";	
	
	//Tabla puntos
	public static final String PUNTOS_TABLE = "puntosdx";	
	//Campos puntos
	public static final String status = "status";
	public static final String clave = "clave";
	public static final String tipo = "tipo";
	
	//Crear tabla puntos
	public static final String CREATE_PUNTOS_TABLE = "create table if not exists "
				+ PUNTOS_TABLE + " ("
				+ ident + " text not null, "
				+ local + " text not null, "
				+ clave + " text, "
				+ tipo + " text , "
				+ status + " text , "
				+ info + " text , "
				+ latitude + " real, "
				+ longitude + " real, "
				+ zoom + " integer, "
				+ recordDate + " date, " 
				+ recordUser + " text, "
				+ pasive + " text, "
				+ deviceId + " text, "
	            + estado + " text, "
	            + " UNIQUE(clave)"
				+ " primary key (" + ident + "));";		
	
	//Tabla criaderos
	public static final String CRIAD_TABLE = "criaderos";	
	//Campos criaderos
	public static final String size = "size";
	public static final String especie = "especie";
	
	//Crear tabla criaderos
	public static final String CREATE_CRIAD_TABLE = "create table if not exists "
				+ CRIAD_TABLE + " ("
				+ ident + " text not null, "
				+ local + " text not null, "
				+ tipo + " text , "
				+ info + " text , "
				+ size + " real, "
				+ especie + " text, "
				+ recordDate + " date, " 
				+ recordUser + " text, "
				+ pasive + " text, "
				+ deviceId + " text, "
	            + estado + " text, "
				+ " primary key (" + ident + "));";		
	
	//Tabla visitas puntos
	public static final String VIS_PUNTOS_TABLE = "visitspdxs";	
	//Campos visitas puntos
	public static final String visitType = "visitType";
	public static final String punto = "punto";
	
	//Crear tabla puntos
	public static final String CREATE_VIS_PUNTOS_TABLE = "create table if not exists "
				+ VIS_PUNTOS_TABLE + " ("
				+ ident + " text not null, "
				+ punto + " text not null, "
				+ visitType + " text, "
				+ visitDate + " date , "
				+ obs + " text , "
				+ recordDate + " date, " 
				+ recordUser + " text, "
				+ pasive + " text, "
				+ deviceId + " text, "
	            + estado + " text, "
				+ " primary key (" + ident + "));";		
	
	//Tabla tratamientos criaderos
	public static final String TRAT_CRIAD_TABLE = "criaderotxs";	
	//Campos tratamientos criaderos
	public static final String txType = "txType";
	public static final String criadero = "criadero";
	
	//Crear tabla puntos
	public static final String CREATE_TRAT_CRIAD_TABLE = "create table if not exists "
				+ TRAT_CRIAD_TABLE + " ("
				+ ident + " text not null, "
				+ criadero + " text not null, "
				+ txType + " text, "
				+ txDate + " date , "
				+ obs + " text , "
				+ recordDate + " date, " 
				+ recordUser + " text, "
				+ pasive + " text, "
				+ deviceId + " text, "
	            + estado + " text, "
				+ " primary key (" + ident + "));";		
	
	//Tabla puntos criaderos
	public static final String PUNTOS_CRIAD_TABLE = "puntoscriaderos";	
	//Campos tratamientos criaderos
	
	
	//Crear tabla puntoscriaderos
	public static final String CREATE_PUNTOS_CRIAD_TABLE = "create table if not exists "
				+ PUNTOS_CRIAD_TABLE + " ("
				+ ident + " text not null, "
				+ criadero + " text not null, "
				+ latitude + " real, "
				+ longitude + " real, "
				+ order + " integer, "
				+ recordDate + " date, " 
				+ recordUser + " text, "
				+ pasive + " text, "
				+ deviceId + " text, "
	            + estado + " text, "
				+ " primary key (" + ident + "));";		

}

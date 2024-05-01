package org.clintonhealthaccess.vca.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.clintonhealthaccess.vca.domain.Area;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.clintonhealthaccess.vca.domain.Distrito;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.domain.OldHousehold;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;
import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.domain.users.Authority;
import org.clintonhealthaccess.vca.domain.users.UserSistema;
import org.clintonhealthaccess.vca.helpers.AreaHelper;
import org.clintonhealthaccess.vca.helpers.BrigadaHelper;
import org.clintonhealthaccess.vca.helpers.CasoHelper;
import org.clintonhealthaccess.vca.helpers.CensadorHelper;
import org.clintonhealthaccess.vca.helpers.CriaderoHelper;
import org.clintonhealthaccess.vca.helpers.CriaderoTxHelper;
import org.clintonhealthaccess.vca.helpers.DistritoHelper;
import org.clintonhealthaccess.vca.helpers.HouseholdHelper;
import org.clintonhealthaccess.vca.helpers.LocalidadHelper;
import org.clintonhealthaccess.vca.helpers.MessageResourceHelper;
import org.clintonhealthaccess.vca.helpers.MuestraHelper;
import org.clintonhealthaccess.vca.helpers.OldHouseholdHelper;
import org.clintonhealthaccess.vca.helpers.PersonHelper;
import org.clintonhealthaccess.vca.helpers.PersonalHelper;
import org.clintonhealthaccess.vca.helpers.PtoDxVisitHelper;
import org.clintonhealthaccess.vca.helpers.PuntoDiagnosticoHelper;
import org.clintonhealthaccess.vca.helpers.PuntosCriaderoHelper;
import org.clintonhealthaccess.vca.helpers.SupervisionHelper;
import org.clintonhealthaccess.vca.helpers.TargetHelper;
import org.clintonhealthaccess.vca.helpers.TemporadaHelper;
import org.clintonhealthaccess.vca.helpers.UserSistemaHelper;
import org.clintonhealthaccess.vca.helpers.VisitHelper;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteQueryBuilder;


/**
 * Adaptador de la base de datos
 * 
 * @author William Aviles
 */

public class VcaAdapter {
	
	public static final String TAG = "WILL";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;
	private final String mPassword;
	private final boolean mFromServer;
	private final boolean mCleanDb;
	

	public VcaAdapter(Context context, String password, boolean fromServer, boolean cleanDb) {
		mContext = context;
		mPassword = password;
		mFromServer = fromServer;
		mCleanDb = cleanDb;
	}
	
	private static class DatabaseHelper extends VcaSQLiteOpenHelper {
		DatabaseHelper(Context context, String password, boolean fromServer, boolean cleanDb) {
			super(FileUtils.DATABASE_PATH, MainDBConstants.DATABASE_NAME, MainDBConstants.DATABASE_VERSION, context,
					password, fromServer, cleanDb);
			createStorage();
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MainDBConstants.CREATE_USER_TABLE);
			db.execSQL(MainDBConstants.CREATE_ROLE_TABLE);
			db.execSQL(MainDBConstants.CREATE_MESSAGES_TABLE);
			db.execSQL(MainDBConstants.CREATE_AREAS_TABLE);
			db.execSQL(MainDBConstants.CREATE_DISTRITOS_TABLE);
			db.execSQL(MainDBConstants.CREATE_LOCALIDADES_TABLE);
			db.execSQL(MainDBConstants.CREATE_CENSADORES_TABLE);
			db.execSQL(MainDBConstants.CREATE_PERSONAL_TABLE);
			db.execSQL(MainDBConstants.CREATE_BRIGADAS_TABLE);
			db.execSQL(MainDBConstants.CREATE_VIVIENDAS_TABLE);
			db.execSQL(MainDBConstants.CREATE_VIVIENDAS2_TABLE);
			db.execSQL(MainDBConstants.CREATE_TEMPORADAS_TABLE);
			db.execSQL(MainDBConstants.CREATE_METAS_TABLE);
			db.execSQL(MainDBConstants.CREATE_VISITAS_TABLE);
			db.execSQL(MainDBConstants.CREATE_SUPERVISION_TABLE);
			db.execSQL(MainDBConstants.CREATE_PERSON_TABLE);
			db.execSQL(MainDBConstants.CREATE_CASOS_TABLE);
			db.execSQL(MainDBConstants.CREATE_TESTS_TABLE);
			db.execSQL(MainDBConstants.CREATE_PUNTOS_TABLE);
			db.execSQL(MainDBConstants.CREATE_VIS_PUNTOS_TABLE);
			db.execSQL(MainDBConstants.CREATE_CRIAD_TABLE);
			db.execSQL(MainDBConstants.CREATE_TRAT_CRIAD_TABLE);
			db.execSQL(MainDBConstants.CREATE_PUNTOS_CRIAD_TABLE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			if(oldVersion<5) {
				onCreate(db);
			}
			else if(oldVersion==5 && newVersion==6) {
				try {
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.masculinos + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.femeninos + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.menores5 + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.menores5masc + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.menores5fem + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.embarazadas + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.sitiosDormirCama + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.sitiosDormirHamaca + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.sitiosDormirSuelo + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.sitiosDormirOtro + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.mtildExistentes + " integer");
				   db.execSQL("ALTER TABLE " + MainDBConstants.VIVIENDAS_TABLE + " ADD COLUMN " + MainDBConstants.mosqSinInsecticida + " integer");
				} catch (SQLiteException ex) {
				   Log.w(TAG, "Altering " + MainDBConstants.VIVIENDAS_TABLE + ": " + ex.getMessage());
				}
			}
		}
	}
	
	public VcaAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext,mPassword,mFromServer,mCleanDb);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * Crea un cursor desde la base de datos
	 * 
	 * @return cursor
	 */
	public Cursor crearCursor(String tabla, String whereString, String projection[], String ordenString) throws SQLException {
		Cursor c = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(tabla);
		c = qb.query(mDb,projection,whereString,null,null,null,ordenString);
		return c;
	}

	public static boolean createStorage() {
		return FileUtils.createFolder(FileUtils.DATABASE_PATH);
	}
	
	/**
	 * Metodos para usuarios en la base de datos
	 * 
	 * @param user
	 *            Objeto Usuario que contiene la informacion
	 *
	 */
	//Crear nuevo usuario en la base de datos
	public void crearUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		mDb.insert(MainDBConstants.USER_TABLE, null, cv);
	}
	//Editar usuario existente en la base de datos
	public boolean editarUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		return mDb.update(MainDBConstants.USER_TABLE, cv, MainDBConstants.username + "='" 
				+ user.getUsername()+"'", null) > 0;
	}
	//Limpiar la tabla de usuarios de la base de datos
	public boolean borrarUsuarios() {
		return mDb.delete(MainDBConstants.USER_TABLE, null, null) > 0;
	}
	//Obtener un usuario de la base de datos
	public UserSistema getUsuario(String filtro, String orden) throws SQLException {
		UserSistema mUser = null;
		Cursor cursorUser = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUser != null && cursorUser.getCount() > 0) {
			cursorUser.moveToFirst();
			mUser=UserSistemaHelper.crearUserSistema(cursorUser);
		}
		if (!cursorUser.isClosed()) cursorUser.close();
		return mUser;
	}
	//Obtener una lista de usuarios de la base de datos
	public List<UserSistema> getUsuarios(String filtro, String orden) throws SQLException {
		List<UserSistema> mUsuarios = new ArrayList<UserSistema>();
		Cursor cursorUsuarios = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUsuarios != null && cursorUsuarios.getCount() > 0) {
			cursorUsuarios.moveToFirst();
			mUsuarios.clear();
			do{
				UserSistema mUser = null;
				mUser = UserSistemaHelper.crearUserSistema(cursorUsuarios);
				mUsuarios.add(mUser);
			} while (cursorUsuarios.moveToNext());
		}
		if (!cursorUsuarios.isClosed()) cursorUsuarios.close();
		return mUsuarios;
	}
	
	/**
	 * Metodos para roles en la base de datos
	 * 
	 * @param rol
	 *            Objeto Authority que contiene la informacion
	 *
	 */
	//Crear nuevo rol en la base de datos
	public void crearRol(Authority rol) {
		ContentValues cv = UserSistemaHelper.crearRolValues(rol);
		mDb.insert(MainDBConstants.ROLE_TABLE, null, cv);
	}
	//Limpiar la tabla de roles de la base de datos
	public boolean borrarRoles() {
		return mDb.delete(MainDBConstants.ROLE_TABLE, null, null) > 0;
	}
	//Verificar un rol de usuario
	public Boolean buscarRol(String username, String Rol) throws SQLException {
		Cursor c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
				MainDBConstants.username + "='" + username + "' and " + MainDBConstants.role + "='" + Rol + "'" , null, null, null, null, null);
		boolean result = c != null && c.getCount()>0; 
		c.close();
		return result;
	}
	//Obtener roles del usuario
	public List<String> obtenerRoles(String username) throws SQLException {
		List<String> uroles = new ArrayList<String>();
		Cursor c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
				MainDBConstants.username + "='" + username + "'" , null, null, null, null, null);
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			uroles.clear();
			do{
				Authority mRol = null;
				mRol = UserSistemaHelper.crearRol(c);
				uroles.add(mRol.getAuthId().getAuthority());
			} while (c.moveToNext());
		}
		if (!c.isClosed()) c.close();
		return uroles;
	}
	
	
	
	/**
	 * Metodos para mensajes en la base de datos
	 * 
	 *
	 */
	//Crear nuevo MessageResource en la base de datos
	public void crearMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		mDb.insert(MainDBConstants.MESSAGES_TABLE, null, cv);
	}
	//Editar MessageResource existente en la base de datos
	public boolean editarMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		return mDb.update(MainDBConstants.MESSAGES_TABLE , cv, MainDBConstants.messageKey + "='" 
				+ mensaje.getMessageKey() + "'", null) > 0;
	}
	//Limpiar la tabla de MessageResource de la base de datos
	public boolean borrarMessageResource() {
		return mDb.delete(MainDBConstants.MESSAGES_TABLE, null, null) > 0;
	}
	//Obtener un MessageResource de la base de datos
	public MessageResource getMessageResource(String filtro, String orden) throws SQLException {
		MessageResource mMessageResource = null;
		Cursor cursorMessageResource = crearCursor(MainDBConstants.MESSAGES_TABLE , filtro, null, orden);
		if (cursorMessageResource != null && cursorMessageResource.getCount() > 0) {
			cursorMessageResource.moveToFirst();
			mMessageResource=MessageResourceHelper.crearMessageResource(cursorMessageResource);
		}
		if (!cursorMessageResource.isClosed()) cursorMessageResource.close();
		return mMessageResource;
	}
	//Obtener una lista de MessageResource de la base de datos
	public List<MessageResource> getMessageResources(String filtro, String orden) throws SQLException {
		List<MessageResource> mMessageResources = new ArrayList<MessageResource>();
		Cursor cursorMessageResources = crearCursor(MainDBConstants.MESSAGES_TABLE, filtro, null, orden);
		if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
			cursorMessageResources.moveToFirst();
			mMessageResources.clear();
			do{
				MessageResource mMessageResource = null;
				mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
				mMessageResources.add(mMessageResource);
			} while (cursorMessageResources.moveToNext());
		}
		if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
		return mMessageResources;
	}
	
	/**
	 * Metodos para areas en la base de datos
	 * 
	 *
	 */
	public void crearArea(Area area) {
		ContentValues cv = AreaHelper.crearAreaValues(area);
		mDb.insert(MainDBConstants.AREAS_TABLE, null, cv);
	}
	//Limpiar la tabla de areas de la base de datos
		public boolean borrarAreas() {
			return mDb.delete(MainDBConstants.AREAS_TABLE, null, null) > 0;
	}
		
	//Obtener un Area de la base de datos
	public Area getArea(String filtro, String orden) throws SQLException {
		Area mArea = null;
		Cursor cursorArea = crearCursor(MainDBConstants.AREAS_TABLE , filtro, null, orden);
		if (cursorArea != null && cursorArea.getCount() > 0) {
			cursorArea.moveToFirst();
			mArea=AreaHelper.crearArea(cursorArea);
		}
		if (!cursorArea.isClosed()) cursorArea.close();
		return mArea;
	}
	
	/**
	 * Metodos para distritos en la base de datos
	 * 
	 *
	 */
	public void crearDistrito(Distrito distrito) {
		ContentValues cv = DistritoHelper.crearDistritoValues(distrito);
		mDb.insert(MainDBConstants.DISTRITOS_TABLE, null, cv);
	}
	//Limpiar la tabla de distritos de la base de datos
		public boolean borrarDistritos() {
			return mDb.delete(MainDBConstants.DISTRITOS_TABLE, null, null) > 0;
	}
		
	//Obtener un Distrito de la base de datos
	public Distrito getDistrito(String filtro, String orden) throws SQLException {
		Distrito mDistrito = null;
		Cursor cursorDistrito = crearCursor(MainDBConstants.DISTRITOS_TABLE , filtro, null, orden);
		if (cursorDistrito != null && cursorDistrito.getCount() > 0) {
			cursorDistrito.moveToFirst();
			mDistrito=DistritoHelper.crearDistrito(cursorDistrito);
			Area area = this.getArea(MainDBConstants.ident + "='" +cursorDistrito.getString(cursorDistrito.getColumnIndex(MainDBConstants.area))+"'", null);
			mDistrito.setArea(area);
		}
		if (!cursorDistrito.isClosed()) cursorDistrito.close();
		return mDistrito;
	}
	
	/**
	 * Metodos para localidades en la base de datos
	 * 
	 *
	 */
	public void crearLocalidad(Localidad localidad) {
		ContentValues cv = LocalidadHelper.crearLocalidadValues(localidad);
		mDb.insert(MainDBConstants.LOCALIDADES_TABLE, null, cv);
	}
	//Limpiar la tabla de localidads de la base de datos
		public boolean borrarLocalidads() {
			return mDb.delete(MainDBConstants.LOCALIDADES_TABLE, null, null) > 0;
	}
		
	//Obtener un Localidad de la base de datos
	public Localidad getLocalidad(String filtro, String orden) throws SQLException {
		Localidad mLocalidad = null;
		Cursor cursorLocalidad = crearCursor(MainDBConstants.LOCALIDADES_TABLE , filtro, null, orden);
		if (cursorLocalidad != null && cursorLocalidad.getCount() > 0) {
			cursorLocalidad.moveToFirst();
			mLocalidad=LocalidadHelper.crearLocalidad(cursorLocalidad);
			Distrito distrito = this.getDistrito(MainDBConstants.ident + "='" +cursorLocalidad.getString(cursorLocalidad.getColumnIndex(MainDBConstants.distrito))+"'", null);
			mLocalidad.setDistrict(distrito);
		}
		if (!cursorLocalidad.isClosed()) cursorLocalidad.close();
		return mLocalidad;
	}
	//Obtener una lista de localidades de la base de datos
	public List<Localidad> getLocalidades(String filtro, String orden) throws SQLException {
		List<Localidad> mLocalidades = new ArrayList<Localidad>();
		Cursor cursorLocalidades = crearCursor(MainDBConstants.LOCALIDADES_TABLE, filtro, null, orden);
		if (cursorLocalidades != null && cursorLocalidades.getCount() > 0) {
			cursorLocalidades.moveToFirst();
			mLocalidades.clear();
			do{
				Localidad mLocalidad = null;
				mLocalidad = LocalidadHelper.crearLocalidad(cursorLocalidades);
				Distrito distrito = this.getDistrito(MainDBConstants.ident + "='" +cursorLocalidades.getString(cursorLocalidades.getColumnIndex(MainDBConstants.distrito))+"'", null);
				mLocalidad.setDistrict(distrito);
				mLocalidades.add(mLocalidad);
			} while (cursorLocalidades.moveToNext());
		}
		if (!cursorLocalidades.isClosed()) cursorLocalidades.close();
		return mLocalidades;
	}
	
	/**
	 * Metodos para censadores en la base de datos
	 * 
	 *
	 */
	public void crearCensador(Censador censador) {
		ContentValues cv = CensadorHelper.crearCensadorValues(censador);
		mDb.insert(MainDBConstants.CENSADORES_TABLE, null, cv);
	}
	//Limpiar la tabla de censadors de la base de datos
		public boolean borrarCensadores() {
			return mDb.delete(MainDBConstants.CENSADORES_TABLE, null, null) > 0;
	}
		
	//Obtener un Censador de la base de datos
	public Censador getCensador(String filtro, String orden) throws SQLException {
		Censador mCensador = null;
		Cursor cursorCensador = crearCursor(MainDBConstants.CENSADORES_TABLE , filtro, null, orden);
		if (cursorCensador != null && cursorCensador.getCount() > 0) {
			cursorCensador.moveToFirst();
			mCensador=CensadorHelper.crearCensador(cursorCensador);
		}
		if (!cursorCensador.isClosed()) cursorCensador.close();
		return mCensador;
	}
	
	//Obtener una lista de censadores de la base de datos
	public List<Censador> getCensadores(String filtro, String orden) throws SQLException {
		List<Censador> mCensadores = new ArrayList<Censador>();
		Cursor cursorCensadores = crearCursor(MainDBConstants.CENSADORES_TABLE, filtro, null, orden);
		if (cursorCensadores != null && cursorCensadores.getCount() > 0) {
			cursorCensadores.moveToFirst();
			mCensadores.clear();
			do{
				Censador mCensador = null;
				mCensador = CensadorHelper.crearCensador(cursorCensadores);
				mCensadores.add(mCensador);
			} while (cursorCensadores.moveToNext());
		}
		if (!cursorCensadores.isClosed()) cursorCensadores.close();
		return mCensadores;
	}
	
	
	/**
	 * Metodos para personal en la base de datos
	 * 
	 *
	 */
	public void crearPersonal(Personal rociador) {
		ContentValues cv = PersonalHelper.crearPersonalValues(rociador);
		mDb.insert(MainDBConstants.PERSONAL_TABLE, null, cv);
	}
	//Limpiar la tabla de personal de la base de datos
		public boolean borrarPersonal() {
			return mDb.delete(MainDBConstants.PERSONAL_TABLE, null, null) > 0;
	}
		
	//Obtener un Personal de la base de datos
	public Personal getPersonal(String filtro, String orden) throws SQLException {
		Personal mPersonal = null;
		Cursor cursorPersonal = crearCursor(MainDBConstants.PERSONAL_TABLE , filtro, null, orden);
		if (cursorPersonal != null && cursorPersonal.getCount() > 0) {
			cursorPersonal.moveToFirst();
			mPersonal=PersonalHelper.crearPersonal(cursorPersonal);
		}
		if (!cursorPersonal.isClosed()) cursorPersonal.close();
		return mPersonal;
	}
	
	//Obtener una lista de personal de la base de datos
	public List<Personal> getPersonals(String filtro, String orden) throws SQLException {
		List<Personal> mPersonals = new ArrayList<Personal>();
		Cursor cursorPersonals = crearCursor(MainDBConstants.PERSONAL_TABLE, filtro, null, orden);
		if (cursorPersonals != null && cursorPersonals.getCount() > 0) {
			cursorPersonals.moveToFirst();
			mPersonals.clear();
			do{
				Personal mPersonal = null;
				mPersonal = PersonalHelper.crearPersonal(cursorPersonals);
				mPersonals.add(mPersonal);
			} while (cursorPersonals.moveToNext());
		}
		if (!cursorPersonals.isClosed()) cursorPersonals.close();
		return mPersonals;
	}
	
	
	/**
	 * Metodos para supervisores en la base de datos
	 * 
	 *
	 *
	public void crearSupervisor(Supervisor supervisor) {
		ContentValues cv = SupervisorHelper.crearSupervisorValues(supervisor);
		mDb.insert(MainDBConstants.SUPERVISORES_TABLE, null, cv);
	}
	//Limpiar la tabla de supervisors de la base de datos
		public boolean borrarSupervisores() {
			return mDb.delete(MainDBConstants.SUPERVISORES_TABLE, null, null) > 0;
	}
		
	//Obtener un Supervisor de la base de datos
	public Supervisor getSupervisor(String filtro, String orden) throws SQLException {
		Supervisor mSupervisor = null;
		Cursor cursorSupervisor = crearCursor(MainDBConstants.SUPERVISORES_TABLE , filtro, null, orden);
		if (cursorSupervisor != null && cursorSupervisor.getCount() > 0) {
			cursorSupervisor.moveToFirst();
			mSupervisor=SupervisorHelper.crearSupervisor(cursorSupervisor);
		}
		if (!cursorSupervisor.isClosed()) cursorSupervisor.close();
		return mSupervisor;
	}
	
	//Obtener una lista de supervisores de la base de datos
	public List<Supervisor> getSupervisores(String filtro, String orden) throws SQLException {
		List<Supervisor> mSupervisores = new ArrayList<Supervisor>();
		Cursor cursorSupervisores = crearCursor(MainDBConstants.SUPERVISORES_TABLE, filtro, null, orden);
		if (cursorSupervisores != null && cursorSupervisores.getCount() > 0) {
			cursorSupervisores.moveToFirst();
			mSupervisores.clear();
			do{
				Supervisor mSupervisor = null;
				mSupervisor = SupervisorHelper.crearSupervisor(cursorSupervisores);
				mSupervisores.add(mSupervisor);
			} while (cursorSupervisores.moveToNext());
		}
		if (!cursorSupervisores.isClosed()) cursorSupervisores.close();
		return mSupervisores;
	}*/
	
	
	/**
	 * Metodos para brigadas en la base de datos
	 * 
	 *
	 */
	public void crearBrigada(Brigada brigada) {
		ContentValues cv = BrigadaHelper.crearBrigadaValues(brigada);
		mDb.insert(MainDBConstants.BRIGADAS_TABLE, null, cv);
	}
	//Limpiar la tabla de brigadas de la base de datos
		public boolean borrarBrigadaes() {
			return mDb.delete(MainDBConstants.BRIGADAS_TABLE, null, null) > 0;
	}
		
	//Obtener un Brigada de la base de datos
	public Brigada getBrigada(String filtro, String orden) throws SQLException {
		Brigada mBrigada = null;
		Cursor cursorBrigada = crearCursor(MainDBConstants.BRIGADAS_TABLE , filtro, null, orden);
		if (cursorBrigada != null && cursorBrigada.getCount() > 0) {
			cursorBrigada.moveToFirst();
			mBrigada=BrigadaHelper.crearBrigada(cursorBrigada);
		}
		if (!cursorBrigada.isClosed()) cursorBrigada.close();
		return mBrigada;
	}
	
	//Obtener una lista de brigadas de la base de datos
	public List<Brigada> getBrigadas(String filtro, String orden) throws SQLException {
		List<Brigada> mBrigadas = new ArrayList<Brigada>();
		Cursor cursorBrigadas = crearCursor(MainDBConstants.BRIGADAS_TABLE, filtro, null, orden);
		if (cursorBrigadas != null && cursorBrigadas.getCount() > 0) {
			cursorBrigadas.moveToFirst();
			mBrigadas.clear();
			do{
				Brigada mBrigada = null;
				mBrigada = BrigadaHelper.crearBrigada(cursorBrigadas);
				mBrigadas.add(mBrigada);
			} while (cursorBrigadas.moveToNext());
		}
		if (!cursorBrigadas.isClosed()) cursorBrigadas.close();
		return mBrigadas;
	}
    
    
    /**
	 * Metodos para Household en la base de datos
	 * 
	 *
	 */
	public void crearHousehold(Household vivienda) {
		ContentValues cv = HouseholdHelper.crearHouseholdValues(vivienda);
		mDb.insert(MainDBConstants.VIVIENDAS_TABLE, null, cv);
	}
	public boolean editarHousehold(Household vivienda) {
		ContentValues cv = HouseholdHelper.crearHouseholdValues(vivienda);
		return mDb.update(MainDBConstants.VIVIENDAS_TABLE, cv, MainDBConstants.ident + "='" 
				+ vivienda.getIdent()+"'", null) > 0;
	}
	//Limpiar la tabla de localidads de la base de datos
		public boolean borrarHouseholds() {
			return mDb.delete(MainDBConstants.VIVIENDAS_TABLE, null, null) > 0;
	}
		
	//Obtener un Household de la base de datos
	public Household getHousehold(String filtro, String orden) throws SQLException {
		Household mHousehold = null;
		Cursor cursorHousehold = crearCursor(MainDBConstants.VIVIENDAS_TABLE , filtro, null, orden);
		if (cursorHousehold != null && cursorHousehold.getCount() > 0) {
			cursorHousehold.moveToFirst();
			mHousehold=HouseholdHelper.crearHousehold(cursorHousehold);
			Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.local))+"'", null);
			mHousehold.setLocal(localidad);
			Censador censador = this.getCensador(MainDBConstants.ident + "='" +cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.censusTaker))+"'", null);
			mHousehold.setCensusTaker(censador);
		}
		if (!cursorHousehold.isClosed()) cursorHousehold.close();
		return mHousehold;
	}
	//Obtener una lista de viviendas de la base de datos
	public List<Household> getHouseholds(String filtro, String orden) throws SQLException {
		List<Household> mHouseholdes = new ArrayList<Household>();
		Cursor cursorHouseholdes = crearCursor(MainDBConstants.VIVIENDAS_TABLE, filtro, null, orden);
		if (cursorHouseholdes != null && cursorHouseholdes.getCount() > 0) {
			cursorHouseholdes.moveToFirst();
			mHouseholdes.clear();
			do{
				Household mHousehold = null;
				mHousehold = HouseholdHelper.crearHousehold(cursorHouseholdes);
				Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorHouseholdes.getString(cursorHouseholdes.getColumnIndex(MainDBConstants.local))+"'", null);
				mHousehold.setLocal(localidad);
				Censador censador = this.getCensador(MainDBConstants.ident + "='" +cursorHouseholdes.getString(cursorHouseholdes.getColumnIndex(MainDBConstants.censusTaker))+"'", null);
				mHousehold.setCensusTaker(censador);
				mHouseholdes.add(mHousehold);
			} while (cursorHouseholdes.moveToNext());
		}
		if (!cursorHouseholdes.isClosed()) cursorHouseholdes.close();
		return mHouseholdes;
	}
	
	/**
	 * Metodos para Household2 en la base de datos
	 * 
	 *
	 */
	public void crearHousehold2(OldHousehold vivienda) {
		ContentValues cv = OldHouseholdHelper.crearOldHouseholdValues(vivienda);
		mDb.insert(MainDBConstants.VIVIENDAS2_TABLE, null, cv);
	}
	//Limpiar la tabla de localidads de la base de datos
	public boolean borrarOldHouseholds() {
		return mDb.delete(MainDBConstants.VIVIENDAS2_TABLE, null, null) > 0;
	}
		
	//Obtener un OldHousehold de la base de datos
	public OldHousehold getOldHousehold(String filtro, String orden) throws SQLException {
		OldHousehold mHousehold = null;
		Cursor cursorHousehold = crearCursor(MainDBConstants.VIVIENDAS2_TABLE , filtro, null, orden);
		if (cursorHousehold != null && cursorHousehold.getCount() > 0) {
			cursorHousehold.moveToFirst();
			mHousehold=OldHouseholdHelper.crearOldHousehold(cursorHousehold);
			Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.local))+"'", null);
			mHousehold.setLocal(localidad);
			Censador censador = this.getCensador(MainDBConstants.ident + "='" +cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.censusTaker))+"'", null);
			mHousehold.setCensusTaker(censador);
		}
		if (!cursorHousehold.isClosed()) cursorHousehold.close();
		return mHousehold;
	}	
	
	/**
	 * Metodos para temporadas en la base de datos
	 * 
	 *
	 */
	public void crearTemporada(IrsSeason temporada) {
		ContentValues cv = TemporadaHelper.crearTemporadaValues(temporada);
		mDb.insert(MainDBConstants.TEMPORADAS_TABLE, null, cv);
	}
	//Limpiar la tabla de temporadas de la base de datos
		public boolean borrarTemporadas() {
			return mDb.delete(MainDBConstants.TEMPORADAS_TABLE, null, null) > 0;
	}
		
	//Obtener un IrsSeason de la base de datos
	public IrsSeason getTemporada(String filtro, String orden) throws SQLException {
		IrsSeason mTemporada = null;
		Cursor cursorTemporada = crearCursor(MainDBConstants.TEMPORADAS_TABLE , filtro, null, orden);
		if (cursorTemporada != null && cursorTemporada.getCount() > 0) {
			cursorTemporada.moveToFirst();
			mTemporada=TemporadaHelper.crearTemporada(cursorTemporada);
		}
		if (!cursorTemporada.isClosed()) cursorTemporada.close();
		return mTemporada;
	}
	
	//Obtener una lista de temporadas de la base de datos
	public List<IrsSeason> getTemporadas(String filtro, String orden) throws SQLException {
		List<IrsSeason> mTemporadas = new ArrayList<IrsSeason>();
		Cursor cursorTemporadas = crearCursor(MainDBConstants.TEMPORADAS_TABLE, filtro, null, orden);
		if (cursorTemporadas != null && cursorTemporadas.getCount() > 0) {
			cursorTemporadas.moveToFirst();
			mTemporadas.clear();
			do{
				IrsSeason mTemporada = null;
				mTemporada = TemporadaHelper.crearTemporada(cursorTemporadas);
				mTemporadas.add(mTemporada);
			} while (cursorTemporadas.moveToNext());
		}
		if (!cursorTemporadas.isClosed()) cursorTemporadas.close();
		return mTemporadas;
	}
	
	
 	/**
	 * Metodos para Target en la base de datos
	 * 
	 *
	 */
	public void crearTarget(Target meta) {
		ContentValues cv = TargetHelper.crearTargetValues(meta);
		mDb.insert(MainDBConstants.METAS_TABLE, null, cv);
	}
	public boolean editarTarget(Target meta) {
		ContentValues cv = TargetHelper.crearTargetValues(meta);
		return mDb.update(MainDBConstants.METAS_TABLE, cv, MainDBConstants.ident + "='" 
				+ meta.getIdent()+"'", null) > 0;
	}
	//Limpiar la tabla de metas de la base de datos
		public boolean borrarTargets() {
			return mDb.delete(MainDBConstants.METAS_TABLE, null, null) > 0;
	}
		
	//Obtener un Target de la base de datos
	public Target getTarget(String filtro, String orden) throws SQLException {
		Target mTarget = null;
		Cursor cursorTarget = crearCursor(MainDBConstants.METAS_TABLE , filtro, null, orden);
		if (cursorTarget != null && cursorTarget.getCount() > 0) {
			cursorTarget.moveToFirst();
			mTarget=TargetHelper.crearTarget(cursorTarget);
			IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
			mTarget.setIrsSeason(temporada);
			Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.household))+"'", null);
			mTarget.setHousehold(vivienda);
			Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTarget.getString(cursorTarget.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
			mTarget.setAssignedTo(asignado);
		}
		if (!cursorTarget.isClosed()) cursorTarget.close();
		return mTarget;
	}
	//Obtener una lista de metas de la base de datos
	public List<Target> getTargets(String filtro, String orden) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = crearCursor(MainDBConstants.METAS_TABLE, filtro, null, orden);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	
	//Obtener una lista de metas de la base de datos
	public List<Target> getTargets2(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	
	
	//Obtener una lista de metas de la base de datos para preaviso
	public List<Target> getTargetsPreaviso(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'NOTVIS' or sprayStatus = 'CLOSED' or sprayStatus = 'RELUCT' or sprayStatus = 'SPRPAR' or sprayStatus = 'DROPPED') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	
	//Obtener una lista de metas de la base de datos para preaviso
	public List<Target> getTargetsRociar(String mTemporada, String mLocalidad, String rociador, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'PENDING' or sprayStatus = 'SPRPAR') " 
				+ "and assignedTo = '" + rociador +"'"
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	
	//Obtener una lista de metas de la base de datos para preaviso
	public List<Target> getTargetsRociar2(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'PENDING' or sprayStatus = 'SPRPAR') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	
	
	//Obtener una lista de metas de la base de datos para no visitadas
	public List<Target> getNovisitadas(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'NOTVIS') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	
	//Obtener una lista de metas de la base de datos para no visitadas
	public List<Target> getAsignadas(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'PENDING') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	
	//Obtener una lista de metas de la base de datos para no visitadas
	public List<Target> getCerradas(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'CLOSED') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	//Obtener una lista de metas de la base de datos para no visitadas
	public List<Target> getRenuentes(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'RELUCT') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
	//Obtener una lista de metas de la base de datos para no visitadas
	public List<Target> getRociadas(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'SPRTOT') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}	
	//Obtener una lista de metas de la base de datos para no visitadas
	public List<Target> getParciales(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'SPRPAR') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}		
	
	
	//Obtener una lista de metas de la base de datos para no visitadas
	public List<Target> getDestruidas(String mTemporada, String mLocalidad, String parametro) throws SQLException {
		List<Target> mTargets = new ArrayList<Target>();
		Cursor cursorTargets = mDb.rawQuery("select * from metas where irsSeason = '"+ mTemporada +"' "
				+ "and (sprayStatus = 'DROPPED') " 
				+ "and household in (select ident from viviendas where local = '"+ mLocalidad +"') "
						+ "and household in (select ident from viviendas where code like '%" + parametro + "%' or ownerName like '%" + parametro + "%')", null);
		if (cursorTargets != null && cursorTargets.getCount() > 0) {
			cursorTargets.moveToFirst();
			mTargets.clear();
			do{
				Target mTarget = null;
				mTarget = TargetHelper.crearTarget(cursorTargets);
				IrsSeason temporada = this.getTemporada(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.irsSeason))+"'", null);
				mTarget.setIrsSeason(temporada);
				Household vivienda = this.getHousehold(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.household))+"'", null);
				mTarget.setHousehold(vivienda);
				Personal asignado = this.getPersonal(MainDBConstants.ident + "='" +cursorTargets.getString(cursorTargets.getColumnIndex(MainDBConstants.assignedTo))+"'", null);
				mTarget.setAssignedTo(asignado);
				mTargets.add(mTarget);
			} while (cursorTargets.moveToNext());
		}
		if (!cursorTargets.isClosed()) cursorTargets.close();
		return mTargets;
	}
			
	
	/**
	 * Metodos para Visit en la base de datos
	 * 
	 *
	 */
	public void crearVisit(Visit visita) {
		ContentValues cv = VisitHelper.crearVisitValues(visita);
		mDb.insert(MainDBConstants.VISITAS_TABLE, null, cv);
	}
	public boolean editarVisit(Visit visita) {
		ContentValues cv = VisitHelper.crearVisitValues(visita);
		return mDb.update(MainDBConstants.VISITAS_TABLE, cv, MainDBConstants.ident + "='" 
				+ visita.getIdent()+"'", null) > 0;
	}
	//Limpiar la tabla de visitas de la base de datos
		public boolean borrarVisits() {
			return mDb.delete(MainDBConstants.VISITAS_TABLE, null, null) > 0;
	}
		
	//Obtener un Visit de la base de datos
	public Visit getVisit(String filtro, String orden) throws SQLException {
		Visit mVisit = null;
		Cursor cursorVisit = crearCursor(MainDBConstants.VISITAS_TABLE , filtro, null, orden);
		if (cursorVisit != null && cursorVisit.getCount() > 0) {
			cursorVisit.moveToFirst();
			mVisit=VisitHelper.crearVisit(cursorVisit);
			Target target = this.getTarget(MainDBConstants.ident + "='" +cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.target))+"'", null);
			mVisit.setTarget(target);
			Personal visitante = this.getPersonal(MainDBConstants.ident + "='" +cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.visitor))+"'", null);
			mVisit.setVisitor(visitante);
			Personal supervisor = this.getPersonal(MainDBConstants.ident + "='" +cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.supervisor))+"'", null);
			mVisit.setSupervisor(supervisor);
			Brigada brigada = this.getBrigada(MainDBConstants.ident + "='" +cursorVisit.getString(cursorVisit.getColumnIndex(MainDBConstants.brigada))+"'", null);
			mVisit.setBrigada(brigada);
		}
		if (!cursorVisit.isClosed()) cursorVisit.close();
		return mVisit;
	}
	//Obtener una lista de visitas de la base de datos
	public List<Visit> getVisits(String filtro, String orden) throws SQLException {
		List<Visit> mVisits = new ArrayList<Visit>();
		Cursor cursorVisits = crearCursor(MainDBConstants.VISITAS_TABLE, filtro, null, orden);
		if (cursorVisits != null && cursorVisits.getCount() > 0) {
			cursorVisits.moveToFirst();
			mVisits.clear();
			do{
				Visit mVisit = null;
				mVisit = VisitHelper.crearVisit(cursorVisits);
				Target target = this.getTarget(MainDBConstants.ident + "='" +cursorVisits.getString(cursorVisits.getColumnIndex(MainDBConstants.target))+"'", null);
				mVisit.setTarget(target);
				Personal visitante = this.getPersonal(MainDBConstants.ident + "='" +cursorVisits.getString(cursorVisits.getColumnIndex(MainDBConstants.visitor))+"'", null);
				mVisit.setVisitor(visitante);
				Personal supervisor = this.getPersonal(MainDBConstants.ident + "='" +cursorVisits.getString(cursorVisits.getColumnIndex(MainDBConstants.supervisor))+"'", null);
				mVisit.setSupervisor(supervisor);
				Brigada brigada = this.getBrigada(MainDBConstants.ident + "='" +cursorVisits.getString(cursorVisits.getColumnIndex(MainDBConstants.brigada))+"'", null);
				mVisit.setBrigada(brigada);
				mVisits.add(mVisit);
			} while (cursorVisits.moveToNext());
		}
		if (!cursorVisits.isClosed()) cursorVisits.close();
		return mVisits;
	}
	
	
	/**
	 * Metodos para Supervision en la base de datos
	 * 
	 *
	 */
	public void crearSupervision(Supervision supervision) {
		ContentValues cv = SupervisionHelper.crearSupervisionValues(supervision);
		mDb.insert(MainDBConstants.SUPERVISION_TABLE, null, cv);
	}
	public boolean editarSupervision(Supervision supervision) {
		ContentValues cv = SupervisionHelper.crearSupervisionValues(supervision);
		return mDb.update(MainDBConstants.SUPERVISION_TABLE, cv, MainDBConstants.ident + "='" 
				+ supervision.getIdent()+"'", null) > 0;
	}
	//Limpiar la tabla de Supervision de la base de datos
		public boolean borrarSupervisiones() {
			return mDb.delete(MainDBConstants.SUPERVISION_TABLE, null, null) > 0;
	}
		
	//Obtener un Supervision de la base de datos
	public Supervision getSupervision(String filtro, String orden) throws SQLException {
		Supervision mSupervision = null;
		Cursor cursorSupervision = crearCursor(MainDBConstants.SUPERVISION_TABLE , filtro, null, orden);
		if (cursorSupervision != null && cursorSupervision.getCount() > 0) {
			cursorSupervision.moveToFirst();
			mSupervision=SupervisionHelper.crearSupervision(cursorSupervision);
			Target target = this.getTarget(MainDBConstants.ident + "='" +cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.target))+"'", null);
			mSupervision.setTarget(target);
			Personal rociador = this.getPersonal(MainDBConstants.ident + "='" +cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.rociador))+"'", null);
			mSupervision.setRociador(rociador);
			Personal supervisor = this.getPersonal(MainDBConstants.ident + "='" +cursorSupervision.getString(cursorSupervision.getColumnIndex(MainDBConstants.supervisor))+"'", null);
			mSupervision.setSupervisor(supervisor);
		}
		if (!cursorSupervision.isClosed()) cursorSupervision.close();
		return mSupervision;
	}
	//Obtener una lista de Supervisiones de la base de datos
	public List<Supervision> getSupervisiones(String filtro, String orden) throws SQLException {
		List<Supervision> mSupervisiones = new ArrayList<Supervision>();
		Cursor cursorSupervisiones = crearCursor(MainDBConstants.SUPERVISION_TABLE, filtro, null, orden);
		if (cursorSupervisiones != null && cursorSupervisiones.getCount() > 0) {
			cursorSupervisiones.moveToFirst();
			mSupervisiones.clear();
			do{
				Supervision mSupervision = null;
				mSupervision = SupervisionHelper.crearSupervision(cursorSupervisiones);
				Target target = this.getTarget(MainDBConstants.ident + "='" +cursorSupervisiones.getString(cursorSupervisiones.getColumnIndex(MainDBConstants.target))+"'", null);
				mSupervision.setTarget(target);
				Personal rociador = this.getPersonal(MainDBConstants.ident + "='" +cursorSupervisiones.getString(cursorSupervisiones.getColumnIndex(MainDBConstants.rociador))+"'", null);
				mSupervision.setRociador(rociador);
				Personal supervisor = this.getPersonal(MainDBConstants.ident + "='" +cursorSupervisiones.getString(cursorSupervisiones.getColumnIndex(MainDBConstants.supervisor))+"'", null);
				mSupervision.setSupervisor(supervisor);
				mSupervisiones.add(mSupervision);
			} while (cursorSupervisiones.moveToNext());
		}
		if (!cursorSupervisiones.isClosed()) cursorSupervisiones.close();
		return mSupervisiones;
	}
	
	/**
	 * Metodos para personas en la base de datos
	 * 
	 *
	 */
	public void crearPersona(Person persona) {
		ContentValues cv = PersonHelper.crearPersonValues(persona);
		mDb.insert(MainDBConstants.PERSONS_TABLE, null, cv);
	}
	//Limpiar la tabla de personas de la base de datos
		public boolean borrarPersonas() {
			return mDb.delete(MainDBConstants.PERSONS_TABLE, null, null) > 0;
	}
	public boolean editarPersona(Person persona) {
		ContentValues cv = PersonHelper.crearPersonValues(persona);
		return mDb.update(MainDBConstants.PERSONS_TABLE, cv, MainDBConstants.ident + "='" 
				+ persona.getIdent()+"'", null) > 0;
	}
		
	//Obtener una persona de la base de datos
	public Person getPersona(String filtro, String orden) throws SQLException {
		Person mPersona = null;
		Cursor cursorPersona = crearCursor(MainDBConstants.PERSONS_TABLE , filtro, null, orden);
		if (cursorPersona != null && cursorPersona.getCount() > 0) {
			cursorPersona.moveToFirst();
			mPersona=PersonHelper.crearPerson(cursorPersona);
			Household casa = this.getHousehold(MainDBConstants.ident + "='" +cursorPersona.getString(cursorPersona.getColumnIndex(MainDBConstants.household))+"'", null);
			mPersona.setCasa(casa);
		}
		if (!cursorPersona.isClosed()) cursorPersona.close();
		return mPersona;
	}
	//Obtener una lista de personas de la base de datos
	public List<Person> getPersonas(String filtro, String orden) throws SQLException {
		List<Person> mPersonas = new ArrayList<Person>();
		Cursor cursorPersonas = crearCursor(MainDBConstants.PERSONS_TABLE, filtro, null, orden);
		if (cursorPersonas != null && cursorPersonas.getCount() > 0) {
			cursorPersonas.moveToFirst();
			mPersonas.clear();
			do{
				Person mPersona = null;
				mPersona = PersonHelper.crearPerson(cursorPersonas);
				Household casa = this.getHousehold(MainDBConstants.ident + "='" +cursorPersonas.getString(cursorPersonas.getColumnIndex(MainDBConstants.household))+"'", null);
				mPersona.setCasa(casa);
				mPersonas.add(mPersona);
			} while (cursorPersonas.moveToNext());
		}
		if (!cursorPersonas.isClosed()) cursorPersonas.close();
		return mPersonas;
	}
	
    /**
	 * Metodos para Caso en la base de datos
	 * 
	 *
	 */
	public void crearCaso(Caso caso) {
		ContentValues cv = CasoHelper.crearCasoValues(caso);
		mDb.insertOrThrow(MainDBConstants.CASOS_TABLE, null, cv);
	}
	//Limpiar la tabla de casos de la base de datos
	public boolean borrarCasos() {
		return mDb.delete(MainDBConstants.CASOS_TABLE, null, null) > 0;
	}
	public boolean editarCaso(Caso caso) {
		ContentValues cv = CasoHelper.crearCasoValues(caso);
		return mDb.update(MainDBConstants.CASOS_TABLE, cv, MainDBConstants.ident + "='" 
				+ caso.getIdent()+"'", null) > 0;
	}	
	public boolean eliminarCaso(Caso caso) {
		return mDb.delete(MainDBConstants.CASOS_TABLE, MainDBConstants.ident + "='" 
				+ caso.getIdent()+"'", null) > 0;
	}
	//Obtener un caso de la base de datos
	public Caso getCaso(String filtro, String orden) throws SQLException {
		Caso mCaso = null;
		Cursor cursorCaso = crearCursor(MainDBConstants.CASOS_TABLE , filtro, null, orden);
		if (cursorCaso != null && cursorCaso.getCount() > 0) {
			cursorCaso.moveToFirst();
			mCaso=CasoHelper.crearCaso(cursorCaso);
			Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorCaso.getString(cursorCaso.getColumnIndex(MainDBConstants.local))+"'", null);
			mCaso.setLocal(localidad);
		}
		if (!cursorCaso.isClosed()) cursorCaso.close();
		return mCaso;
	}
	
	//Obtener una lista de casos de la base de datos
	public List<Caso> getCasos(String filtro, String orden) throws SQLException {
		List<Caso> mCasos = new ArrayList<Caso>();
		Cursor cursorCasos = crearCursor(MainDBConstants.CASOS_TABLE, filtro, null, orden);
		if (cursorCasos != null && cursorCasos.getCount() > 0) {
			cursorCasos.moveToFirst();
			mCasos.clear();
			do{
				Caso mCaso = null;
				mCaso = CasoHelper.crearCaso(cursorCasos);
				Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorCasos.getString(cursorCasos.getColumnIndex(MainDBConstants.local))+"'", null);
				mCaso.setLocal(localidad);
				mCasos.add(mCaso);
			} while (cursorCasos.moveToNext());
		}
		if (!cursorCasos.isClosed()) cursorCasos.close();
		return mCasos;
	}
	
	
    /**
	 * Metodos para Muestra en la base de datos
	 * 
	 *
	 */
	public void crearMuestra(Muestra muestra) {
		ContentValues cv = MuestraHelper.crearMuestraValues(muestra);
		mDb.insertOrThrow(MainDBConstants.TESTS_TABLE, null, cv);
	}
	//Limpiar la tabla de muestras de la base de datos
	public boolean borrarMuestras() {
		return mDb.delete(MainDBConstants.TESTS_TABLE, null, null) > 0;
	}
	public boolean editarMuestra(Muestra muestra) {
		ContentValues cv = MuestraHelper.crearMuestraValues(muestra);
		return mDb.update(MainDBConstants.TESTS_TABLE, cv, MainDBConstants.ident + "='" 
				+ muestra.getIdent()+"'", null) > 0;
	}	
	public boolean eliminarMuestra(Muestra muestra) {
		return mDb.delete(MainDBConstants.TESTS_TABLE, MainDBConstants.ident + "='" 
				+ muestra.getIdent()+"'", null) > 0;
	}
	//Obtener una muestra de la base de datos
	public Muestra getMuestra(String filtro, String orden) throws SQLException {
		Muestra mMuestra = null;
		Cursor cursorMuestra = crearCursor(MainDBConstants.TESTS_TABLE , filtro, null, orden);
		if (cursorMuestra != null && cursorMuestra.getCount() > 0) {
			cursorMuestra.moveToFirst();
			mMuestra=MuestraHelper.crearMuestra(cursorMuestra);
			Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorMuestra.getString(cursorMuestra.getColumnIndex(MainDBConstants.local))+"'", null);
			mMuestra.setLocal(localidad);
		}
		if (!cursorMuestra.isClosed()) cursorMuestra.close();
		return mMuestra;
	}
	
	//Obtener una lista de muestras de la base de datos
	public List<Muestra> getMuestras(String filtro, String orden) throws SQLException {
		List<Muestra> mMuestras = new ArrayList<Muestra>();
		Cursor cursorMuestras = crearCursor(MainDBConstants.TESTS_TABLE, filtro, null, orden);
		if (cursorMuestras != null && cursorMuestras.getCount() > 0) {
			cursorMuestras.moveToFirst();
			mMuestras.clear();
			do{
				Muestra mMuestra = null;
				mMuestra = MuestraHelper.crearMuestra(cursorMuestras);
				Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorMuestras.getString(cursorMuestras.getColumnIndex(MainDBConstants.local))+"'", null);
				mMuestra.setLocal(localidad);
				mMuestras.add(mMuestra);
			} while (cursorMuestras.moveToNext());
		}
		if (!cursorMuestras.isClosed()) cursorMuestras.close();
		return mMuestras;
	}	
	
	   /**
		 * Metodos para Punto Diagnóstico en la base de datos
		 * 
		 *
		 */
		public void crearPuntoDiagnostico(PuntoDiagnostico punto) {
			ContentValues cv = PuntoDiagnosticoHelper.crearPuntoDiagnosticoValues(punto);
			mDb.insertOrThrow(MainDBConstants.PUNTOS_TABLE, null, cv);
		}
		//Limpiar la tabla de puntos de diagnostico de la base de datos
		public boolean borrarPuntoDiagnosticos() {
			return mDb.delete(MainDBConstants.PUNTOS_TABLE, null, null) > 0;
		}
		public boolean editarPuntoDiagnostico(PuntoDiagnostico punto) {
			ContentValues cv = PuntoDiagnosticoHelper.crearPuntoDiagnosticoValues(punto);
			return mDb.update(MainDBConstants.PUNTOS_TABLE, cv, MainDBConstants.ident + "='" 
					+ punto.getIdent()+"'", null) > 0;
		}
		public boolean eliminarPuntoDiagnostico(PuntoDiagnostico punto) {
			return mDb.delete(MainDBConstants.PUNTOS_TABLE, MainDBConstants.ident + "='" 
					+ punto.getIdent()+"'", null) > 0;
		}
		//Obtener un punto de la base de datos
		public PuntoDiagnostico getPuntoDiagnostico(String filtro, String orden) throws SQLException {
			PuntoDiagnostico mPuntoDiagnostico = null;
			Cursor cursorPuntoDiagnostico = crearCursor(MainDBConstants.PUNTOS_TABLE , filtro, null, orden);
			if (cursorPuntoDiagnostico != null && cursorPuntoDiagnostico.getCount() > 0) {
				cursorPuntoDiagnostico.moveToFirst();
				mPuntoDiagnostico=PuntoDiagnosticoHelper.crearPuntoDiagnostico(cursorPuntoDiagnostico);
				Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorPuntoDiagnostico.getString(cursorPuntoDiagnostico.getColumnIndex(MainDBConstants.local))+"'", null);
				mPuntoDiagnostico.setLocal(localidad);
			}
			if (!cursorPuntoDiagnostico.isClosed()) cursorPuntoDiagnostico.close();
			return mPuntoDiagnostico;
		}
		
		//Obtener una lista de puntos de la base de datos
		public List<PuntoDiagnostico> getPuntosDiagnosticos(String filtro, String orden) throws SQLException {
			List<PuntoDiagnostico> mPuntoDiagnosticos = new ArrayList<PuntoDiagnostico>();
			Cursor cursorPuntoDiagnosticos = crearCursor(MainDBConstants.PUNTOS_TABLE, filtro, null, orden);
			if (cursorPuntoDiagnosticos != null && cursorPuntoDiagnosticos.getCount() > 0) {
				cursorPuntoDiagnosticos.moveToFirst();
				mPuntoDiagnosticos.clear();
				do{
					PuntoDiagnostico mPuntoDiagnostico = null;
					mPuntoDiagnostico = PuntoDiagnosticoHelper.crearPuntoDiagnostico(cursorPuntoDiagnosticos);
					Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorPuntoDiagnosticos.getString(cursorPuntoDiagnosticos.getColumnIndex(MainDBConstants.local))+"'", null);
					mPuntoDiagnostico.setLocal(localidad);
					mPuntoDiagnosticos.add(mPuntoDiagnostico);
				} while (cursorPuntoDiagnosticos.moveToNext());
			}
			if (!cursorPuntoDiagnosticos.isClosed()) cursorPuntoDiagnosticos.close();
			return mPuntoDiagnosticos;
		}		

		
	   /**
		 * Metodos para criaderos en la base de datos
		 * 
		 *
		 */
		public void crearCriadero(Criadero criadero) {
			ContentValues cv = CriaderoHelper.crearCriaderoValues(criadero);
			mDb.insertOrThrow(MainDBConstants.CRIAD_TABLE, null, cv);
		}
		//Limpiar la tabla de Criaderos de la base de datos
		public boolean borrarCriaderos() {
			return mDb.delete(MainDBConstants.CRIAD_TABLE, null, null) > 0;
		}
		public boolean editarCriadero(Criadero criadero) {
			ContentValues cv = CriaderoHelper.crearCriaderoValues(criadero);
			return mDb.update(MainDBConstants.CRIAD_TABLE, cv, MainDBConstants.ident + "='" 
					+ criadero.getIdent()+"'", null) > 0;
		}
		public boolean eliminarCriadero(Criadero punto) {
			return mDb.delete(MainDBConstants.CRIAD_TABLE, MainDBConstants.ident + "='" 
					+ punto.getIdent()+"'", null) > 0;
		}
		//Obtener un Criadero de la base de datos
		public Criadero getCriadero(String filtro, String orden) throws SQLException {
			Criadero mCriadero = null;
			Cursor cursorCriadero = crearCursor(MainDBConstants.CRIAD_TABLE , filtro, null, orden);
			if (cursorCriadero != null && cursorCriadero.getCount() > 0) {
				cursorCriadero.moveToFirst();
				mCriadero=CriaderoHelper.crearCriadero(cursorCriadero);
				Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorCriadero.getString(cursorCriadero.getColumnIndex(MainDBConstants.local))+"'", null);
				mCriadero.setLocal(localidad);
			}
			if (!cursorCriadero.isClosed()) cursorCriadero.close();
			return mCriadero;
		}
		
		//Obtener una lista de Criaderos de la base de datos
		public List<Criadero> getCriaderos(String filtro, String orden) throws SQLException {
			List<Criadero> mCriaderos = new ArrayList<Criadero>();
			Cursor cursorCriaderos = crearCursor(MainDBConstants.CRIAD_TABLE, filtro, null, orden);
			if (cursorCriaderos != null && cursorCriaderos.getCount() > 0) {
				cursorCriaderos.moveToFirst();
				mCriaderos.clear();
				do{
					Criadero mCriadero = null;
					mCriadero = CriaderoHelper.crearCriadero(cursorCriaderos);
					Localidad localidad = this.getLocalidad(MainDBConstants.ident + "='" +cursorCriaderos.getString(cursorCriaderos.getColumnIndex(MainDBConstants.local))+"'", null);
					mCriadero.setLocal(localidad);
					mCriaderos.add(mCriadero);
				} while (cursorCriaderos.moveToNext());
			}
			if (!cursorCriaderos.isClosed()) cursorCriaderos.close();
			return mCriaderos;
		}		


   /**
	 * Metodos para Visita de Punto Diagnóstico en la base de datos
	 * 
	 *
	 */
	public void crearVisPuntoDiagnostico(PtoDxVisit vispunto) {
		ContentValues cv = PtoDxVisitHelper.crearPtoDxVisitValues(vispunto);
		mDb.insertOrThrow(MainDBConstants.VIS_PUNTOS_TABLE, null, cv);
	}
	//Limpiar la tabla de puntos de diagnostico de la base de datos
	public boolean borrarVisPuntoDiagnosticos() {
		return mDb.delete(MainDBConstants.VIS_PUNTOS_TABLE, null, null) > 0;
	}
	public boolean editarVisPuntoDiagnostico(PtoDxVisit vispunto) {
		ContentValues cv = PtoDxVisitHelper.crearPtoDxVisitValues(vispunto);
		return mDb.update(MainDBConstants.VIS_PUNTOS_TABLE, cv, MainDBConstants.ident + "='" 
				+ vispunto.getIdent()+"'", null) > 0;
	}
	public boolean eliminarVisPuntoDiagnostico(PtoDxVisit vispunto) {
		return mDb.delete(MainDBConstants.VIS_PUNTOS_TABLE, MainDBConstants.ident + "='" 
				+ vispunto.getIdent()+"'", null) > 0;
	}
	//Obtener un punto de la base de datos
	public PtoDxVisit getVisPuntoDiagnostico(String filtro, String orden) throws SQLException {
		PtoDxVisit mVisitaPuntoDiagnostico = null;
		Cursor cursorVisitaPuntoDiagnostico = crearCursor(MainDBConstants.VIS_PUNTOS_TABLE , filtro, null, orden);
		if (cursorVisitaPuntoDiagnostico != null && cursorVisitaPuntoDiagnostico.getCount() > 0) {
			cursorVisitaPuntoDiagnostico.moveToFirst();
			mVisitaPuntoDiagnostico=PtoDxVisitHelper.crearPtoDxVisit(cursorVisitaPuntoDiagnostico);
			PuntoDiagnostico punto = this.getPuntoDiagnostico(
					MainDBConstants.ident + "='" +cursorVisitaPuntoDiagnostico.getString(cursorVisitaPuntoDiagnostico.getColumnIndex(MainDBConstants.punto))+"'", null);
			mVisitaPuntoDiagnostico.setPunto(punto);
		}
		if (!cursorVisitaPuntoDiagnostico.isClosed()) cursorVisitaPuntoDiagnostico.close();
		return mVisitaPuntoDiagnostico;
	}
	
	//Obtener una lista de puntos de la base de datos
	public List<PtoDxVisit> getVisPuntosDiagnosticos(String filtro, String orden) throws SQLException {
		List<PtoDxVisit> mVisitasPuntoDiagnosticos = new ArrayList<PtoDxVisit>();
		Cursor cursorVisitasPuntoDiagnosticos = crearCursor(MainDBConstants.VIS_PUNTOS_TABLE, filtro, null, orden);
		if (cursorVisitasPuntoDiagnosticos != null && cursorVisitasPuntoDiagnosticos.getCount() > 0) {
			cursorVisitasPuntoDiagnosticos.moveToFirst();
			mVisitasPuntoDiagnosticos.clear();
			do{
				PtoDxVisit mVisitaPuntoDiagnostico = null;
				mVisitaPuntoDiagnostico = PtoDxVisitHelper.crearPtoDxVisit(cursorVisitasPuntoDiagnosticos);
				PuntoDiagnostico punto = this.getPuntoDiagnostico(
						MainDBConstants.ident + "='" +cursorVisitasPuntoDiagnosticos.getString(cursorVisitasPuntoDiagnosticos.getColumnIndex(MainDBConstants.punto))+"'", null);
				mVisitaPuntoDiagnostico.setPunto(punto);
				mVisitasPuntoDiagnosticos.add(mVisitaPuntoDiagnostico);
			} while (cursorVisitasPuntoDiagnosticos.moveToNext());
		}
		if (!cursorVisitasPuntoDiagnosticos.isClosed()) cursorVisitasPuntoDiagnosticos.close();
		return mVisitasPuntoDiagnosticos;
	}	

	
   /**
	 * Metodos para Tratamiento de Criaderos en la base de datos
	 * 
	 *
	 */
	public void crearCriaderoTx(CriaderoTx txcriadero) {
		ContentValues cv = CriaderoTxHelper.crearCriaderoTxValues(txcriadero);
		mDb.insertOrThrow(MainDBConstants.TRAT_CRIAD_TABLE, null, cv);
	}
	//Limpiar la tabla de tx de criaderos de la base de datos
	public boolean borrarCriaderoTxs() {
		return mDb.delete(MainDBConstants.TRAT_CRIAD_TABLE, null, null) > 0;
	}
	public boolean editarCriaderoTx(CriaderoTx txcriadero) {
		ContentValues cv = CriaderoTxHelper.crearCriaderoTxValues(txcriadero);
		return mDb.update(MainDBConstants.TRAT_CRIAD_TABLE, cv, MainDBConstants.ident + "='" 
				+ txcriadero.getIdent()+"'", null) > 0;
	}
	public boolean eliminarCriaderoTx(CriaderoTx txcriadero) {
		return mDb.delete(MainDBConstants.TRAT_CRIAD_TABLE, MainDBConstants.ident + "='" 
				+ txcriadero.getIdent()+"'", null) > 0;
	}
	//Obtener un tx de criadero de la base de datos
	public CriaderoTx getCriaderoTx(String filtro, String orden) throws SQLException {
		CriaderoTx mCriaderoTx = null;
		Cursor cursorCriaderoTx = crearCursor(MainDBConstants.TRAT_CRIAD_TABLE , filtro, null, orden);
		if (cursorCriaderoTx != null && cursorCriaderoTx.getCount() > 0) {
			cursorCriaderoTx.moveToFirst();
			mCriaderoTx=CriaderoTxHelper.crearCriaderoTx(cursorCriaderoTx);
			Criadero criadero = this.getCriadero(
					MainDBConstants.ident + "='" +cursorCriaderoTx.getString(cursorCriaderoTx.getColumnIndex(MainDBConstants.criadero))+"'", null);
			mCriaderoTx.setCriadero(criadero);
		}
		if (!cursorCriaderoTx.isClosed()) cursorCriaderoTx.close();
		return mCriaderoTx;
	}
	
	//Obtener una lista de puntos de la base de datos
	public List<CriaderoTx> getCriaderoTxs(String filtro, String orden) throws SQLException {
		List<CriaderoTx> mCriaderoTxs = new ArrayList<CriaderoTx>();
		Cursor cursorCriaderoTxs = crearCursor(MainDBConstants.TRAT_CRIAD_TABLE, filtro, null, orden);
		if (cursorCriaderoTxs != null && cursorCriaderoTxs.getCount() > 0) {
			cursorCriaderoTxs.moveToFirst();
			mCriaderoTxs.clear();
			do{
				CriaderoTx mCriaderoTx = null;
				mCriaderoTx = CriaderoTxHelper.crearCriaderoTx(cursorCriaderoTxs);
				Criadero criadero = this.getCriadero(
						MainDBConstants.ident + "='" +cursorCriaderoTxs.getString(cursorCriaderoTxs.getColumnIndex(MainDBConstants.criadero))+"'", null);
				mCriaderoTx.setCriadero(criadero);
				mCriaderoTxs.add(mCriaderoTx);
			} while (cursorCriaderoTxs.moveToNext());
		}
		if (!cursorCriaderoTxs.isClosed()) cursorCriaderoTxs.close();
		return mCriaderoTxs;
	}	
	
	/**
	 * Metodos para Puntos de Criaderos en la base de datos
	 * 
	 *
	 */
	public void crearPuntosCriadero(PuntosCriadero txcriadero) {
		ContentValues cv = PuntosCriaderoHelper.crearPuntosCriaderoValues(txcriadero);
		mDb.insertOrThrow(MainDBConstants.PUNTOS_CRIAD_TABLE, null, cv);
	}
	//Limpiar la tabla de puntos de criaderos de la base de datos
	public boolean borrarPuntosCriaderos() {
		return mDb.delete(MainDBConstants.PUNTOS_CRIAD_TABLE, null, null) > 0;
	}
	public boolean editarPuntosCriadero(PuntosCriadero txcriadero) {
		ContentValues cv = PuntosCriaderoHelper.crearPuntosCriaderoValues(txcriadero);
		return mDb.update(MainDBConstants.PUNTOS_CRIAD_TABLE, cv, MainDBConstants.ident + "='" 
				+ txcriadero.getIdent()+"'", null) > 0;
	}
	public boolean eliminarPuntosCriadero(PuntosCriadero txcriadero) {
		return mDb.delete(MainDBConstants.PUNTOS_CRIAD_TABLE, MainDBConstants.ident + "='" 
				+ txcriadero.getIdent()+"'", null) > 0;
	}
	//Obtener un tx de criadero de la base de datos
	public PuntosCriadero getPuntosCriadero(String filtro, String orden) throws SQLException {
		PuntosCriadero mPuntosCriadero = null;
		Cursor cursorPuntosCriadero = crearCursor(MainDBConstants.PUNTOS_CRIAD_TABLE , filtro, null, orden);
		if (cursorPuntosCriadero != null && cursorPuntosCriadero.getCount() > 0) {
			cursorPuntosCriadero.moveToFirst();
			mPuntosCriadero=PuntosCriaderoHelper.crearPuntosCriadero(cursorPuntosCriadero);
			Criadero criadero = this.getCriadero(
					MainDBConstants.ident + "='" +cursorPuntosCriadero.getString(cursorPuntosCriadero.getColumnIndex(MainDBConstants.criadero))+"'", null);
			mPuntosCriadero.setCriadero(criadero);
		}
		if (!cursorPuntosCriadero.isClosed()) cursorPuntosCriadero.close();
		return mPuntosCriadero;
	}
	
	//Obtener una lista de puntos de la base de datos
	public List<PuntosCriadero> getPuntosCriaderos(String filtro, String orden) throws SQLException {
		List<PuntosCriadero> mPuntosCriaderos = new ArrayList<PuntosCriadero>();
		Cursor cursorPuntosCriaderos = crearCursor(MainDBConstants.PUNTOS_CRIAD_TABLE, filtro, null, orden);
		if (cursorPuntosCriaderos != null && cursorPuntosCriaderos.getCount() > 0) {
			cursorPuntosCriaderos.moveToFirst();
			mPuntosCriaderos.clear();
			do{
				PuntosCriadero mPuntosCriadero = null;
				mPuntosCriadero = PuntosCriaderoHelper.crearPuntosCriadero(cursorPuntosCriaderos);
				Criadero criadero = this.getCriadero(
						MainDBConstants.ident + "='" +cursorPuntosCriaderos.getString(cursorPuntosCriaderos.getColumnIndex(MainDBConstants.criadero))+"'", null);
				mPuntosCriadero.setCriadero(criadero);
				mPuntosCriaderos.add(mPuntosCriadero);
			} while (cursorPuntosCriaderos.moveToNext());
		}
		if (!cursorPuntosCriaderos.isClosed()) cursorPuntosCriaderos.close();
		return mPuntosCriaderos;
	}	
	
	//Obtener el numero de registros de una tabla
    public Integer getUltimoPuntoCriadero(String filtro) {
    	Integer ultimo = 0;
    	if(filtro==null) filtro = "1=1";
    	Cursor cursor = mDb.rawQuery("select max(orden) from " + MainDBConstants.PUNTOS_CRIAD_TABLE + " where " + filtro, null);
    	if (cursor != null && cursor.getCount() > 0) {
    		cursor.moveToFirst();
    		ultimo = cursor.getInt(0);
    	}
    	if (!cursor.isClosed()) cursor.close();
        return ultimo;
    }	
		
	//Obtener el numero de registros de una tabla
    public Integer getNumeroRegistros(String tabla, String filtro) {
    	Integer total = 0;
    	if(filtro==null) filtro = "1=1";
    	Cursor cursor = mDb.rawQuery("select * from " + tabla + " where " + filtro, null);
    	total = cursor.getCount();
    	if (!cursor.isClosed()) cursor.close();
        return total;
    }
    
    //Obtener el numero de registros por fecha
    public List<Object[]> getNumeroRegistrosFecha() {
    	List<Object[]> resultados = new ArrayList<Object[]>();
    	SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
    	Cursor cursor = mDb.rawQuery("select censusDate, count(*) from viviendas where pasive = '0' group by censusDate order by censusDate", null);
    	if (cursor != null && cursor.getCount() > 0) {
    	cursor.moveToFirst();
    	resultados.clear();
		do{
			Object[] resultado = new Object[2];
			resultado[0] = mDateFormat.format(new Date(cursor.getLong(0)));
			resultado[1] = cursor.getString(1);
			resultados.add(resultado);
		} while (cursor.moveToNext());
    	}
    	if (!cursor.isClosed()) cursor.close();
        return resultados;
    }
    
    //Obtener el numero de registros por fecha
    public List<Object[]> getNumeroRegistrosFechaVisitas() {
    	List<Object[]> resultados = new ArrayList<Object[]>();
    	SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
    	Cursor cursor = mDb.rawQuery("select visitDate, count(*) from visitas where pasive = '0' group by visitDate order by visitDate", null);
    	if (cursor != null && cursor.getCount() > 0) {
    	cursor.moveToFirst();
    	resultados.clear();
		do{
			Object[] resultado = new Object[2];
			resultado[0] = mDateFormat.format(new Date(cursor.getLong(0)));
			resultado[1] = cursor.getString(1);
			resultados.add(resultado);
		} while (cursor.moveToNext());
    	}
    	if (!cursor.isClosed()) cursor.close();
        return resultados;
    }
    
    
    //Obtener el numero de registros por localidad
    public List<Object[]> getNumeroRegistrosLocalidad() {
    	List<Object[]> resultados = new ArrayList<Object[]>();
    	Cursor cursor = mDb.rawQuery("select local, count(*) from viviendas group by local order by local", null);
    	if (cursor != null && cursor.getCount() > 0) {
    	cursor.moveToFirst();
    	resultados.clear();
		do{
			Object[] resultado = new Object[2];
			Localidad localidad  = this.getLocalidad("ident  = '" + cursor.getString(0) +"'", null);
			resultado[0] = localidad.getName();
			resultado[1] = cursor.getString(1);
			resultados.add(resultado);
		} while (cursor.moveToNext());
    	}
    	if (!cursor.isClosed()) cursor.close();
        return resultados;
    }
    
    
  //Obtener el numero de visitas por localidad
    public List<Object[]> getNumeroRegistrosEstadoMetas() {
    	List<Object[]> resultados = new ArrayList<Object[]>();
    	Cursor cursor = mDb.rawQuery("select sprayStatus, count(*) from metas group by sprayStatus order by sprayStatus", null);
    	if (cursor != null && cursor.getCount() > 0) {
    	cursor.moveToFirst();
    	resultados.clear();
		do{
			Object[] resultado = new Object[2];
			MessageResource mr  = this.getMessageResource("catKey  = '" + cursor.getString(0) +"' and catRoot  = 'CAT_STATUS'", null);
			resultado[0] = mr.getSpanish();
			resultado[1] = cursor.getString(1);
			resultados.add(resultado);
		} while (cursor.moveToNext());
    	}
    	if (!cursor.isClosed()) cursor.close();
        return resultados;
    }
    
  //Obtener el numero de registros por estado
    public List<Object[]> getNumeroRegistrosEstado() {
    	List<Object[]> resultados = new ArrayList<Object[]>();
    	Cursor cursor = mDb.rawQuery("select estado, count(*) from viviendas where pasive = '0' group by estado order by estado", null);
    	if (cursor != null && cursor.getCount() > 0) {
    	cursor.moveToFirst();
    	resultados.clear();
		do{
			Object[] resultado = new Object[2];
			if(cursor.getString(0).charAt(0)==Constants.STATUS_NOT_FINALIZED) {
				resultado[0] = Constants.STATUS_NOT_FINALIZED_DESC;
			}
			else if(cursor.getString(0).charAt(0)==Constants.STATUS_NOT_SUBMITTED) {
				resultado[0] = Constants.STATUS_NOT_SUBMITTED_DESC;
			}
			else if(cursor.getString(0).charAt(0)==Constants.STATUS_SUBMITTED) {
				resultado[0] = Constants.STATUS_SUBMITTED_DESC;
			}
			else {
				resultado[0] = Constants.STATUS_UNKNOWN;
			}
			resultado[1] = cursor.getString(1);
			resultados.add(resultado);
		} while (cursor.moveToNext());
    	}
    	if (!cursor.isClosed()) cursor.close();
        return resultados;
    }
    
  //Obtener el numero de registros por estado
    public List<Object[]> getNumeroRegistrosVisitasEstado() {
    	List<Object[]> resultados = new ArrayList<Object[]>();
    	Cursor cursor = mDb.rawQuery("select estado, count(*) from visitas where pasive = '0' group by estado order by estado", null);
    	if (cursor != null && cursor.getCount() > 0) {
    	cursor.moveToFirst();
    	resultados.clear();
		do{
			Object[] resultado = new Object[2];
			if(cursor.getString(0).charAt(0)==Constants.STATUS_NOT_FINALIZED) {
				resultado[0] = Constants.STATUS_NOT_FINALIZED_DESC;
			}
			else if(cursor.getString(0).charAt(0)==Constants.STATUS_NOT_SUBMITTED) {
				resultado[0] = Constants.STATUS_NOT_SUBMITTED_DESC;
			}
			else if(cursor.getString(0).charAt(0)==Constants.STATUS_SUBMITTED) {
				resultado[0] = Constants.STATUS_SUBMITTED_DESC;
			}
			else {
				resultado[0] = Constants.STATUS_UNKNOWN;
			}
			resultado[1] = cursor.getString(1);
			resultados.add(resultado);
		} while (cursor.moveToNext());
    	}
    	if (!cursor.isClosed()) cursor.close();
        return resultados;
    }
    
    
    public Boolean verificarData() throws SQLException{
		Cursor c = null;
		c = crearCursor(MainDBConstants.VIVIENDAS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "' or " + MainDBConstants.estado + "='"  + Constants.STATUS_NOT_FINALIZED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.METAS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+"'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.VISITAS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+"'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.SUPERVISION_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+"'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.PERSONS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+"'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		return false;
	}
    
    
    public Boolean verificarDataMapeo() throws SQLException{
		Cursor c = null;
		c = crearCursor(MainDBConstants.CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.TESTS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.PUNTOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.CRIAD_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.VIS_PUNTOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.TRAT_CRIAD_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.PUNTOS_CRIAD_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		return false;
	}
	
}
package is2011.app.preferencias;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

public class Preferencias {
	
	private static Preferencias preferencias = null;
	private String pathPreferenciasSistema;
	private String pathBiblioteca;
	private String pathListaReproduccion;
	private String pathListaReproduccionDefecto;
	private ModoReproduccionEnum modoReproduccion;
	private Double volumen;
	private boolean abrirUltimaLista; 
	private String ultimoDirectorioAbierto;
	private boolean hayCambios;

	private final String NOMBRE_DIR = "ISPlayer";
	private final String NOMBRE_PREFERENCIAS = "ISPlayerPreferences.xml";
	private final String NOMBRE_BIBLIOTECA = "Biblioteca.xml";
	private final String NOMBRE_LISTA = "listaDefecto.xml";
	
	
	/** Flujo de lectura/escritura para ficheros XML */
	private XStream stream;
	
	private Preferencias(){
		
		String home = System.getProperty("user.home");
		home = home+File.separator+NOMBRE_DIR;
		
		File f = new File(home);
		f.mkdir();
		
		home = home+File.separator;
		
		pathPreferenciasSistema = home+NOMBRE_PREFERENCIAS;
		
		File pref = new File(pathPreferenciasSistema);
		
		stream = new XStream(new DomDriver());
		stream.alias("Preferencias", Preferencias.class);
		stream.omitField(Preferencias.class, "stream");
		
		//Si no existen las preferencias,creamos unas por defecto.
		if(!pref.exists()){
			pathBiblioteca = home + NOMBRE_BIBLIOTECA;
			pathListaReproduccion = "";
			pathListaReproduccionDefecto = home + NOMBRE_LISTA;
			ultimoDirectorioAbierto = home;
			volumen = 1.0;
			hayCambios = true;
			modoReproduccion = ModoReproduccionEnum.NORMAL;
			abrirUltimaLista = true;
			
		}else { 
			pathBiblioteca = null;
			pathListaReproduccion = null;
			modoReproduccion = null;
			volumen = 1.0;
			abrirUltimaLista = false;
			ultimoDirectorioAbierto = null;
			cargarXML(pathPreferenciasSistema);
			hayCambios = false;
		}
		
	}
	
	public synchronized static Preferencias getInstance() {
		if(preferencias == null) {
			preferencias = new Preferencias();
		}
		return preferencias;
	}
	
	
	private void cargarXML(String path) {
		Preferencias aux;
		try {
			aux = (Preferencias) stream.fromXML(new FileInputStream(path));
			setPathBiblioteca(aux.getPathBiblioteca());
			setPathListaReproduccion(aux.getPathListaReproduccion());
			setModoReproduccion(aux.getModoReproduccion());
			setPathListaReproduccionDefecto(aux.getPathListaReproduccionDefecto());
			setAbrirUltimaLista(aux.isAbrirUltimaLista());
			setUltimoDirectorioAbierto(aux.getUltimoDirectorioAbierto());
			setVolumen(aux.getVolumen());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void guardarXML() {
		try {
			stream.toXML(preferencias, new FileOutputStream(this.pathPreferenciasSistema));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @return the hayCambios
	 */
	public boolean isHayCambios() {
		return hayCambios;
	}

	/**
	 * @return the abrirUltimaLista
	 */
	public boolean isAbrirUltimaLista() {
		return abrirUltimaLista;
	}

	/**
	 * @param abrirUltimaLista the abrirUltimaLista to set
	 */
	public void setAbrirUltimaLista(boolean abrirUltimaLista) {
		this.abrirUltimaLista = abrirUltimaLista;
		this.hayCambios = true;
	}

	
	public String getPathPreferenciasSistema(){
		return pathPreferenciasSistema;
	}
	public String getPathBiblioteca(){
		return pathBiblioteca;
	}
	
	public String getPathListaReproduccion(){
		return pathListaReproduccion;
	}
	
	public ModoReproduccionEnum getModoReproduccion(){
		return modoReproduccion;
	}
	
	public void setPathPreferenciasSistema(String input){
		pathPreferenciasSistema = input;
		this.hayCambios = true;
	}
	public void setPathBiblioteca(String input){
		pathBiblioteca = input;
		this.hayCambios = true;
	}
	
	public void setPathListaReproduccion(String input){
		pathListaReproduccion = input;
		this.hayCambios = true;
	}
	
	public void setModoReproduccion(ModoReproduccionEnum input){
		modoReproduccion = input;
		this.hayCambios = true;
	}
	/**
	 * @return the volumen
	 */
	public float getVolumen() {
		if(volumen == null)
			return (float) 1.0;
		else
			return volumen.floatValue();
	}

	/**
	 * @param vol the volumen to set
	 */
	public void setVolumen(float vol) {
		this.volumen =  new Double(vol);
		this.hayCambios = true;
	}
	
	public String getUltimoDirectorioAbierto() {
		return ultimoDirectorioAbierto;
	}

	public void setUltimoDirectorioAbierto(String ultimoDirectorioAbierto) {
		this.ultimoDirectorioAbierto = ultimoDirectorioAbierto;
		this.hayCambios = true;
	}
	
	/**
	 * @return the pathListaReproduccionDefecto
	 */
	public String getPathListaReproduccionDefecto() {
		return pathListaReproduccionDefecto;
	}

	/**
	 * @param pathListaReproduccionDefecto the pathListaReproduccionDefecto to set
	 */

	public void setPathListaReproduccionDefecto(String pathListaReproduccionDefecto) {
		this.pathListaReproduccionDefecto = pathListaReproduccionDefecto;
	}
	
	
}

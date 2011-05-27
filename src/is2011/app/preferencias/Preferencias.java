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
	
	/** Path de la carpeta donde estan todos los archivos del sistema*/
	private String pathPreferencias;
	
	/** Path del archivo de preferencias */
	private String pathArchivoPreferenciasSistema;
	
	/** Path de la biblioteca*/
	private String pathBiblioteca;
	
	/** Path de la carpeta que contiene las listas de reproduccion*/
	private String pahtListasDeReproduccion;
	
	/** Path de la lista de reproduccion por defecto*/
	private String pathListaReproduccionDefecto;
	
	/** Aqui guardamos el path de la ultima lista abierta*/
	private String pathUltimaLista;
	
	
	private int posX;
	private int posY;
	private int ancho;
	private int alto;
	private boolean compacta;
	
	
	private ModoReproduccionEnum modoReproduccion;
	private Double volumen;
	private boolean abrirUltimaLista; 
	private String ultimoDirectorioAbierto;
	private String nombreLook;
	private boolean hayCambios;

	private final String NOMBRE_DIR = "ISPlayer";
	private final String DIR_LISTAS = "Listas";
	private final String NOMBRE_PREFERENCIAS = "ISPlayerPreferences.xml";
	private final String NOMBRE_BIBLIOTECA = "Biblioteca.xml";
	private final String NOMBRE_LISTA = "listaDefecto.xml";
	
	
	/** Flujo de lectura/escritura para ficheros XML */
	private XStream stream;
	
	
	private Preferencias(){
		
		String home = System.getProperty("user.home");
		//Creamos el la carpeta donde estaran todas las preferencias.
		this.pathPreferencias = home+File.separator+NOMBRE_DIR ;
		
		File f = new File(pathPreferencias);
		f.mkdir();
		
		this.pathArchivoPreferenciasSistema = this.pathPreferencias
						+File.separator + NOMBRE_PREFERENCIAS;
		
		File pref = new File(pathArchivoPreferenciasSistema);
		
		stream = new XStream(new DomDriver());
		stream.alias("Preferencias", Preferencias.class);
		stream.omitField(Preferencias.class, "stream");
		
		
		this.pahtListasDeReproduccion = this.pathPreferencias + 
				File.separator + DIR_LISTAS;
		
		this.pathUltimaLista = this.pathPreferencias + File.separator + 
								NOMBRE_LISTA; 
		//Si no existen las preferencias,creamos unas por defecto.
		if(!pref.exists()){
			//Posicion y tamaño por defecto;
			posX = 200;
			posY = 200;
			
			ancho = 800;
			alto = 700;
			
			compacta = false;
			pathBiblioteca = pathPreferencias + File.separator 
							+  NOMBRE_BIBLIOTECA;
			pathListaReproduccionDefecto = this.pathUltimaLista;
			ultimoDirectorioAbierto = this.pathPreferencias;
			volumen = 1.0;
			hayCambios = true;
			modoReproduccion = ModoReproduccionEnum.NORMAL;
			abrirUltimaLista = true;
			nombreLook = "SyntheticaSimple2DLookAndFeel";
			
		}else { 
			
			pathBiblioteca = null;
			modoReproduccion = null;
			volumen = 1.0;
			abrirUltimaLista = false;
			ultimoDirectorioAbierto = null;
			cargarXML(pathArchivoPreferenciasSistema);
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
			setModoReproduccion(aux.getModoReproduccion());
			setPathListaReproduccionDefecto(aux.getPathListaReproduccionDefecto());
			setAbrirUltimaLista(aux.isAbrirUltimaLista());
			setUltimoDirectorioAbierto(aux.getUltimoDirectorioAbierto());
			setVolumen(aux.getVolumen());
			setLookAndFeel(aux.getNombreLook());
			setPathListaReproduccionDefecto(aux.getPathListaReproduccionDefecto());
			posX = aux.getPosX();
			posY = aux.getPosY();
			
			ancho = aux.getAncho();
			alto = aux.getAlto();
			compacta = aux.isVistaCompacta();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		hayCambios = true;
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		hayCambios = true;
		this.posY = posY;
	}

	/**
	 * @return the ancho
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * @param ancho the ancho to set
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * @return the alto
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * @param alto the alto to set
	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}

	public String getDirecctorioListasDeReproduccion() {
		return  this.pahtListasDeReproduccion;
		
	}
	public void guardarXML() {
		try {
			stream.toXML(preferencias, new FileOutputStream(this.pathArchivoPreferenciasSistema));
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
		return pathArchivoPreferenciasSistema;
	}
	public String getPathBiblioteca(){
		return pathBiblioteca;
	}
	

	
	public ModoReproduccionEnum getModoReproduccion(){
		return modoReproduccion;
	}
	
	public void setPathPreferenciasSistema(String input){
		pathArchivoPreferenciasSistema = input;
		this.hayCambios = true;
	}
	
	public void setPathBiblioteca(String input){
		pathBiblioteca = input;
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

	public void setLookAndFeel(String nombreLook) {
		this.nombreLook = nombreLook;
		hayCambios = true;
	}
	
	public String getNombreLook() {
		return this.nombreLook;
	}
	
	public String getPathUltimaLista() {
		return this.pathUltimaLista;
	}

	/**
	 * @return
	 */
	public boolean isVistaCompacta() {
		return compacta;
	}
	
	public void setCompacta(boolean c) {
		this.compacta = c;
		hayCambios = true;
	}
	
}

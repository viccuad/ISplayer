package is2011.biblioteca.contenedores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamOmitField;




public class BibliotecaContainer {
	
	/** Indica si la biblioteca ha sido modificada */
	@XStreamOmitField
	private boolean modificado;
	
	/**  */

	private HashMap<String, DirectorioContainer> directorios;
	
	
	/**
	 * 
	 */
	public BibliotecaContainer(){
		this.modificado = false;
		this.directorios = new HashMap<String, DirectorioContainer>();
	}
	
	/**
	 * Añade una canción a un directorio ya existente
	 * @param song
	 */
	public void addCancion(CancionContainer song){
		this.modificado = true;
		this.directorios.get(song.getTotalPath().substring(0, song.getTotalPath().indexOf(song.getTrackPath())-1)).addCancion(song);
	}
	
	
	/**
	 * Añade una canción a un directorio ya existente a partir del directorio del padre
	 * @param song
	 */
	public void addCancion(CancionContainer song, String parentPath){
		this.modificado = true;
		this.directorios.get(parentPath).addCancion(song);
	}
	

	/**
	 * Indica si se ha habido alguna modificado en la estrucura de contenedores
	 * @return
	 */
	public boolean isModificado() {
		return modificado;
	}
	

	/**
	 * Añade un directorio a la biblioteca. Si este ya existia lo reemplaza
	 * @param dir
	 */
	public void addDir(DirectorioContainer dir){
		// si el directorio existia, se reemplaza
		this.directorios.put(dir.getPath(), dir);
		this.modificado = true;
	}
	
	/**
	 * Añade un nuevo directorio vario a partir de su ruta
	 * @param path
	 */
	public void addDir(String path){
		this.directorios.put(path, new DirectorioContainer(path));
		this.modificado = true;
	}
	
	
	/**
	 * Comprueba que exista creado un directorio correpondiente con el path absoluto que recibe como parámetro
	 * @param path ruta absoluta del directorio que se quiere comprobar
	 * @return true si el directorio existe en el container, false en otro caso
	 */
	public boolean existeDirectorio(String path){
		return this.directorios.containsKey(path);
	}

	
	/** 
	 * Devuelve todas las canciones de la biblioteca musical
	 * @return un ArrayList con todas las canciones de la biblioteca musical
	 */
	public ArrayList<CancionContainer> getArrayListCanciones(){
		ArrayList<CancionContainer> canciones = new ArrayList<CancionContainer>();
		
		Iterator<Entry<String, DirectorioContainer>> it =  this.directorios.entrySet().iterator();
		while(it.hasNext())
			canciones.addAll(it.next().getValue().getListaCanciones());
		
		return canciones;
	}
	
	
	/**
	 * Genera las rutas absolutas de las canciones. Se debe hacer siempre que se lea la biblioteca musical
	 * desde el fichero XML
	 */
	public void generarRutasAbsolutas(){
		Iterator<Entry<String, DirectorioContainer>> it =  this.directorios.entrySet().iterator();
		while(it.hasNext())
			it.next().getValue().actualizarPathCanciones();
	}
	
}

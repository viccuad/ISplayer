package is2011.biblioteca.contenedores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamOmitField;




public class BibliotecaContainer {
	
	@XStreamOmitField
	private boolean modificado;
	
	private HashMap<String, DirectorioContainer> directorios;
	//private ArrayList<CancionContainer> tracks;
	
	
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
	
	
	
	
	/*
	  
	 public BibliotecaContainer(){
		this.modificado = false;
		this.tracks = new ArrayList<CancionContainer>();
	}
	
	
	public void addCancion(CancionContainer song){
		// si el directorio existia, se reemplaza
		this.tracks.add(song);
		this.modificado = true;
	}


	public boolean isModificado() {
		return modificado;
	}
	
	public void ordenar(Comparator<CancionContainer> criterio){
		Collections.sort(this.tracks, criterio);
		this.modificado = true;
	}
	
	public ArrayList<CancionContainer> getCanciones(){
		return this.tracks;
	}
	
	public String getPathCancion(int posicionEnLaLista){
		//TODO mirar si es getTotalPath() o getTackPath()
		return this.tracks.get(posicionEnLaLista).getTotalPath();
	}


	public CancionContainer getCancion(int posicionEnLaLista) {
		return this.tracks.get(posicionEnLaLista);
	} 
	 
	 */
	
	

	
	
	
}

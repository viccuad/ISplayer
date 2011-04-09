package is2011.biblioteca.contenedores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamOmitField;




public class BibliotecaContainer {
	
	@XStreamOmitField
	private boolean modificado;
	
	//private HashMap<String, DirectorioContainer> directorios;
	private ArrayList<CancionContainer> tracks;
	
	public BibliotecaContainer(){
		this.modificado = false;
		this.tracks = new ArrayList<CancionContainer>();
	}
	
	
	public void addCancion(CancionContainer song){
		// si el directorio existia, se reemplaza
		this.tracks.add(song);
		this.modificado = true;
	}

	/**
	 * Indica si se ha habido alguna modificado en la estrucura de contenedores
	 * @return
	 */
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
	
	
	
	
	/*
	public BibliotecaContainer(){
		this.modificado = false;
		this.directorios = new HashMap<String, DirectorioContainer>();
	}
	
	
	public void addDir(DirectorioContainer dir){
		// si el directorio existia, se reemplaza
		this.directorios.put(dir.getPath(), dir);
		this.modificado = true;
	}

	
	public boolean isModificado() {
		return modificado;
	}
	*/
	
	
	
	
	

	
	
	
}

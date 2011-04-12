package is2011.biblioteca.contenedores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DirectorioContainer {
	
	private String dirPath;
	//private HashMap<String,CancionContainer> listaCanciones;
	private ArrayList<CancionContainer> listaCanciones;
	
	
	public DirectorioContainer(String path, ArrayList<CancionContainer> listaCanciones) {
		this.dirPath = path;
		this.listaCanciones = listaCanciones;
	}
	
	public DirectorioContainer(String path) {
		this.dirPath = path;
		this.listaCanciones = new ArrayList<CancionContainer>();
	}

	public void addCancion(CancionContainer cancion){
		// no pueden existir dos canciones con el mismo trackPath (nombre de fichero .mp3)
		// se sobreescribe de todas formas
		this.listaCanciones.add(cancion);
	}
	
	public String getPath() {
		return dirPath;
	}

	public void setPath(String path) {
		this.dirPath = path;
	}

	public ArrayList<CancionContainer> getListaCanciones() {
		return listaCanciones;
	}

	public void setListaCanciones(ArrayList<CancionContainer> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}
	
	/**
	 * Actualiza el path completo de las canciones: 
	 * /home/luis/miMusica + waKawaKa.mp3
	 */
	public void actualizarPathCanciones(){
		Iterator<CancionContainer> it = this.listaCanciones.iterator();
		while(it.hasNext()){
			CancionContainer aux = it.next();
			aux.setTotalPath(this.dirPath + aux.getTrackPath());
		}
	}

	
	
}

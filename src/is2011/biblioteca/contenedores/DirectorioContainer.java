package is2011.biblioteca.contenedores;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class DirectorioContainer {
	/** Ruta absoluta del directorio */
	private String dirPath;
	
	//private HashMap<String,CancionContainer> listaCanciones;
	
	/** Lista de canciones que contiene el directorio */
	private ArrayList<CancionContainer> listaCanciones;
	
	
	/**
	 * 
	 * @param path
	 * @param listaCanciones
	 */
	public DirectorioContainer(String path, ArrayList<CancionContainer> listaCanciones) {
		this.dirPath = path;
		this.listaCanciones = listaCanciones;
	}
	
	
	/**
	 * 
	 * @param path
	 */
	public DirectorioContainer(String path) {
		this.dirPath = path;
		this.listaCanciones = new ArrayList<CancionContainer>();
	}

	
	/**
	 * 
	 * @param cancion
	 */
	public void addCancion(CancionContainer cancion){
		// no pueden existir dos canciones con el mismo trackPath (nombre de fichero .mp3)
		// se sobreescribe de todas formas
		this.listaCanciones.add(cancion);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getPath() {
		return dirPath;
	}

	
	/**
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.dirPath = path;
	}

	
	/**
	 * 
	 * @return
	 */
	public ArrayList<CancionContainer> getListaCanciones() {
		return listaCanciones;
	}

	
	/**
	 * 
	 * @param listaCanciones
	 */
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
			//TODO: quitar esta linea si funciona la de abajo, que usa File.separator 
			//aux.setTotalPath(this.dirPath + "//" + aux.getTrackPath());
			aux.setTotalPath(this.dirPath + File.separator + aux.getTrackPath());
		}
	}
}

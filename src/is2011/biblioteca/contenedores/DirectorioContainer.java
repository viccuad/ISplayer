package is2011.biblioteca.contenedores;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class DirectorioContainer {
	
	/** Ruta absoluta del directorio */
	private String dirPath;
	
	/** Lista de canciones que contiene el directorio */
	private ArrayList<CancionContainer> listaCanciones;
	
	
	
	/**
	 * Crea un objeto a partir de un path correspondiente a un directorio y una lista de canciones
	 * @param path ruta de un directorio
	 * @param listaCanciones lista de canciones que se encuentrar en el directorio 'path'
	 */
	public DirectorioContainer(String path, ArrayList<CancionContainer> listaCanciones) {
		this.dirPath = path;
		this.listaCanciones = listaCanciones;
	}
	
	
	/**
	 * Crea un objeto directorio vacio (sin canciones) a partir de un path de directorio
	 * @param path ruta de un directorio
	 */
	public DirectorioContainer(String path) {
		this.dirPath = path;
		this.listaCanciones = new ArrayList<CancionContainer>();
	}

	
	/**
	 * Añade una canción al directorio. No comprueba que la canción exista previamente
	 * @param cancion
	 */
	public void addCancion(CancionContainer cancion){
		// no pueden existir dos canciones con el mismo trackPath (nombre_de_fichero.mp3)
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
			aux.setTotalPath(this.dirPath + File.separator + aux.getTrackPath());
		}
	}
	
	
	/**
	 * Comprueba si existe alguna canción en el directorio que se corresponda con un nombre de canción
	 * o trackPath pasado como parámetro en forma de string.
	 * @param nombreCancion nombre del fichero de la canción que se quiere comprobar
	 * @return true si existe una canción con un nombre o trackPath igual al que recibe la funcion como
	 * parámetro, false en otro caso
	 */
	public boolean existeCancion(String nombreCancion){
		Iterator<CancionContainer> it = this.listaCanciones.iterator();
		
		while(it.hasNext())
			if(it.next().getTrackPath().equalsIgnoreCase(nombreCancion))
				return true;
				
		return false;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean equals(DirectorioContainer s) {
		if (s==null) return false;
		if (this.getClass()!= s.getClass()) return false;
		if (this.getPath() != s.getPath()) return false;
		for ( int i = 0; i == s.getListaCanciones().size(); i++){
			String nombrecancion = s.getListaCanciones().get(i).getTitulo();
			if (this.existeCancion(nombrecancion) != true) return false;				
		}

		return true;
	}
}

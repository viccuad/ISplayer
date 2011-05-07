package is2011.biblioteca.contenedores;

import is2011.biblioteca.search.CriterioBusqueda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamOmitField;




public class BibliotecaContainer {
	
	/** Indica si la biblioteca ha sido modificada para escribir el XML*/
	@XStreamOmitField
	private boolean modificadoEscritura;
	
	/** Indica si la biblioteca ha sido modificada para escribir el XML*/
	@XStreamOmitField
	private boolean modificadoParaMostrar;
	
	/** Listado de todas las canciones de la biblioteca */
	@XStreamOmitField
	private ArrayList<CancionContainer> listaCanciones;
	
	/**  Contenedor de las canciones*/
	private HashMap<String, DirectorioContainer> directorios;
	
	

	
	/**
	 * Crea un objeto vacío preparado para añadir canciones
	 */
	public BibliotecaContainer(){
		this.modificadoEscritura = false;
		this.modificadoParaMostrar = false;
		this.listaCanciones = null;
		this.directorios = new HashMap<String, DirectorioContainer>();
	}
	
	
	/**
	 * Añade una canción a un directorio ya existente
	 * @param song
	 */
	public void addCancion(CancionContainer song){
		this.listaCanciones = null;
		this.directorios.get(song.getTotalPath().substring(0, song.getTotalPath().indexOf(song.getTrackPath())-1)).addCancion(song);
		this.modificadoEscritura = false;
		this.modificadoParaMostrar = false;
	}
	
	
	/**
	 * Añade una canción a un directorio ya existente a partir del directorio del padre
	 * @param song
	 */
	public void addCancion(CancionContainer song, String parentPath){
		this.directorios.get(parentPath).addCancion(song);
		this.modificadoEscritura = true;
		this.modificadoParaMostrar = true;
	}
	
	
	/**
	 * Añade un directorio a la biblioteca. Si este ya existia lo reemplaza
	 * @param dir
	 */
	public void addDir(DirectorioContainer dir){
		// si el directorio existia, se reemplaza
		this.directorios.put(dir.getPath(), dir);
		this.modificadoEscritura = true;
		this.modificadoParaMostrar = true;
	}
	
	
	/**
	 * Añade un nuevo directorio vario a partir de su ruta
	 * @param path
	 */
	public void addDir(String path){
		this.directorios.put(path, new DirectorioContainer(path));
		this.modificadoEscritura = true;
		this.modificadoParaMostrar = true;
	}
	
	
	/**
	 * Elimina un directorio de la biblioteca. Si el directorio no existe entonces no ocurre nada.
	 * @param path
	 */
	public void removeDir(String path){
		this.directorios.remove(path);
		this.modificadoEscritura = true;
		this.modificadoParaMostrar = true;
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
	 * Indica si se ha habido alguna modificado en la estrucura de contenedores con
	 * el fin de saber si se debe escribir en XML o no
	 * @return
	 */
	public boolean isModificado() {
		return modificadoEscritura;
	}
	
	public void setModificado(boolean b){
		modificadoEscritura = b;
	}
	
	
	/**
	 * A partir del nombre de una cancion y el directorio indica si existe en la biblioteca
	 * @param parentPath
	 * @param trackName
	 * @return
	 */
	public boolean existeCancion(String parentPath, String trackName){
		boolean existe = false;
		
		if(this.directorios.containsKey(parentPath))
			existe = this.directorios.get(parentPath).existeCancion(trackName);
		
		return existe;
	}

	
	/** 
	 * Devuelve todas las canciones de la biblioteca musical
	 * @return un ArrayList con todas las canciones de la biblioteca musical
	 */
	public ArrayList<CancionContainer> getArrayListCanciones(){
		if(this.modificadoParaMostrar || this.listaCanciones == null)
			generarArrayListCanciones();
		
		return listaCanciones;
	}
	
	
	/**
	 * Realiza una búsqueda en la lista de canciones según un criterio que recibe como parámetro
	 * @param busqueda es el criterio por el cuál se desea buscar en la lista de reproducción
	 * @return la nueva colección con los elementos que satisface el criterio de búsqueda
	 */
	public ArrayList<CancionContainer> getListaBusqueda(CriterioBusqueda busqueda){
		// si no se ha generado la lista de canciones la crea
		if(this.modificadoParaMostrar || this.listaCanciones == null)
			generarArrayListCanciones();
		
		return busqueda.buscar(this.listaCanciones);
	}
	
	
	/**
	 * Realiza una búsqueda avanzada en la lista de canciones según un criterio que recibe como parámetro
	 * @param busqueda es el criterio por el cuál se desea buscar en la lista de reproducción
	 * @return la nueva colección con los elementos que satisface el criterio de búsqueda
	 */
	public ArrayList<CancionContainer> getListaBusquedaAvanzada(CriterioBusqueda busqueda){
		// si no se ha generado la lista de canciones la crea
		if(this.modificadoParaMostrar || this.listaCanciones == null)
			generarArrayListCanciones();
		
		return busqueda.buscarAvanzado(this.listaCanciones);
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
	
	
	/**
	 * Genera una nueva lista con las canciones existentes en la biblioteca
	 * y fija ese valor al atributo listaCanciones
	 */
	private void generarArrayListCanciones(){
		ArrayList<CancionContainer> canciones = new ArrayList<CancionContainer>();
		
		Iterator<Entry<String, DirectorioContainer>> it =  this.directorios.entrySet().iterator();
		while(it.hasNext())
			canciones.addAll(it.next().getValue().getListaCanciones());
		
		listaCanciones = canciones;
	}
	

	/**
	 * Cuando se escribe la biblioteca a un XML se fija su flag de escritura.
	 * Cuando se invoca a este método se indica que ha sido guardado y por lo tanto está
	 * actualizado
	 */
	public void guardado() {
		this.modificadoEscritura = false;
	}


	public ArrayList<String> getDirectorios() {
		return new ArrayList<String>(this.directorios.keySet());
	}
	
	
	
}

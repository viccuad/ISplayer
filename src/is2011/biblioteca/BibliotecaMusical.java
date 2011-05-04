package is2011.biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;
import is2011.biblioteca.search.CriterioBusqueda;
import is2011.biblioteca.util.ActualizarBiblioteca;
import is2011.biblioteca.util.AniadirCanciones;
import is2011.biblioteca.util.RecorreFicheros;
import is2011.reproductor.modelo.listeners.BibliotecaListener;



public class BibliotecaMusical {

	/** Contenedor de canciones para la biblioteca musical */
	private BibliotecaContainer canciones;
	
	/** Única instancia de la biblioteca musical. Sigue el patrón Singleton */
	private static BibliotecaMusical singleBibliotecaMusical = null;
	
	/** Flujo de lectura/escritura para ficheros XML */
	private XStream stream;
	
	/** Indica si se añaden canciones de los subdirectorios */
	private boolean busquedaRecursiva;
	
	/** Lista de listeners de la biblioteca */
	private ArrayList<BibliotecaListener> listeners;
	
	
	
	
	/**
	 * Constructor privado para implementar en patrón Singleton
	 */
	private BibliotecaMusical(){
		busquedaRecursiva = false;
		canciones = new BibliotecaContainer();
		stream = new XStream(new DomDriver());
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("dir", DirectorioContainer.class);
		stream.alias("track", CancionContainer.class);
		
		//atributos que no se incluyen en el formato XML de la biblioteca
		stream.omitField(BibliotecaContainer.class, "modificadoEscritura");
		stream.omitField(BibliotecaContainer.class, "modificadoParaMostrar");
		stream.omitField(BibliotecaContainer.class, "listaCanciones");
		stream.omitField(CancionContainer.class, "totalPath");
		listeners = new ArrayList<BibliotecaListener>();
	}
	
	
	/**
	 * Sigue el patrón Singleton y devuelve la instancia única de la biblioteca musical.
	 * @return singleBibliotecaMusical
	 */
	public static BibliotecaMusical getInstacia(){
		if(singleBibliotecaMusical == null)
			singleBibliotecaMusical = new BibliotecaMusical();
		
		return singleBibliotecaMusical;
	}
	
	
	/**
	 * Carga la biblioteca XML recibiendo la ruta en la que se ubica.
	 * @param pathYfichero ruta absoluta al fichero XML de la biblioteca musical
	 * @throws FileNotFoundException
	 */
	public void cargarXML(String pathYfichero) throws FileNotFoundException{
		
		File aux = new File(pathYfichero);
		if (aux.canRead()){
			canciones = (BibliotecaContainer) stream.fromXML(new FileInputStream(pathYfichero));
			this.canciones.generarRutasAbsolutas();
			notificaCancionesModificadas();
		}else System.out.println("El fichero no existe");
	}
	
	
	/**
	 * Solo guarda el XML si ha habido cambios en la biblioteca
	 * @param pathYfichero
	 * @throws FileNotFoundException
	 */
	public void guardarXML(String pathYfichero) throws FileNotFoundException{
		if(canciones.isModificado()){
			stream.toXML(canciones, new FileOutputStream(pathYfichero));
			this.canciones.guardado();
		}
	}
	
	
	/**
	 * Fija si se quiere hacer búsqueda en los subdirectorios
	 * @param busquedaRecursiva
	 */
	public void setBusquedaRecursiva(boolean busquedaRecursiva) {
		this.busquedaRecursiva = busquedaRecursiva;
	}
	
	
	/**
	 * Devuelve una lista con todas las canciones de la biblioteca musical
	 * @return
	 */
	public ArrayList<CancionContainer> getCanciones(){
		return this.canciones.getArrayListCanciones();
	}
	
	
	/**
	 * Devuelve los elementos de la biblioteca musical que coincidan con el criterio de búsqueda
	 * que recibe como parámetro
	 * @param busqueda es el criterio de búsqueda
	 * @return colección con los elementos que satisfacen el criterio de búsqueda
	 */
	public ArrayList<CancionContainer> getBusqueda(CriterioBusqueda busqueda){
		return this.canciones.getListaBusqueda(busqueda);
	}
	
	
	/**
	 * Dice si la biblioteca musical ha sido modificada en algún momento
	 * @return true si la biblioteca ha sido modificada, false en otro caso
	 */
	public boolean isModificado(){
		return this.canciones.isModificado();
	}
	
	
	/**
	 * Añade y actualiza (en caso de que ya exista) directorios y canciones. Si los directorios
	 * ya existían los borra y añade su información de nuevo. Las canciones sólo se añaden
	 * si no existen previamente.
	 * @param ficheros lista de ficheros y canciones para actualizar la biblioteca
	 */
	public void actualizarDirectorios(ArrayList<String> ficheros){
		RecorreFicheros recorre = new RecorreFicheros(ficheros);
		recorre.setEstrategia(new ActualizarBiblioteca(this.canciones));
		recorre.recorre();
		notificaCancionesModificadas();
	}
	
	
	/**
	 * Añade canciones a la biblioteca en caso de que no existan previamente, de ser así no hace nada al
	 * igual que si recibiese algún directorio en vez de canciones.
	 * @param canciones lista de canciones a insertar
	 */
	public void aniadirCanciones(ArrayList<String> canciones){
		RecorreFicheros recorre = new RecorreFicheros(canciones);
		recorre.setEstrategia(new AniadirCanciones(this.canciones));
		recorre.recorre();
		notificaCancionesModificadas();

	}
	
	
	/**
	 * Lista canciones modificada
	 * @param .
	 */
	private void notificaCancionesModificadas() {
		for (BibliotecaListener l : listeners) {
			l.mostrarTodas();
		}
		
	}
	
	
	
	//TODO hacer la notificación al listener
	/*
	private void notificaAutoresBuscados( ArrayList<String> autores) {
		for (BibliotecaListener l : listeners) {
			l.autoresBuscados(autores);
		}
		
	}	
	*/
	
	
	/**
	 * Añade oyentes a la biblioteca
	 * @param listener
	 */
	public void addBibliotecaListeners(BibliotecaListener listener)
	{
		listeners.add(listener);
	}
	
}

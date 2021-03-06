package is2011.biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;
import is2011.biblioteca.search.CriterioBusqueda;
import is2011.biblioteca.util.Aniadir;
import is2011.biblioteca.util.CrearBiblioteca;
import is2011.biblioteca.util.AniadirCanciones;
import is2011.biblioteca.util.RecorreFicheros;
import is2011.reproductor.modelo.listeners.BibliotecaListener;



public class BibliotecaMusical {

	/** Contenedor de canciones para la biblioteca musical */
	private BibliotecaContainer canciones;
	
	/** Subcojunto de las canciones buscadas */
	private ArrayList<CancionContainer> buscadas;
	
	/** Atributo que indica si la biblioteca se encuentra en fase de busqueda */
	boolean busquedaRealizada;
	
	/** Única instancia de la biblioteca musical. Sigue el patrón Singleton */
	private static BibliotecaMusical singleBibliotecaMusical = null;
	
	/** Flujo de lectura/escritura para ficheros XML */
	private XStream stream;
	
	/** Indica si se añaden canciones de los subdirectorios */
	//private boolean busquedaRecursiva;
	
	/** Lista de listeners de la biblioteca */
	private ArrayList<BibliotecaListener> listeners;
	
	
	
	
	/**
	 * Constructor privado para implementar el patrón Singleton.
	 * Define también que atributos no son incluidos en el formato XML de 
	 * la biblioteca. 
	 */
	private BibliotecaMusical(){
		//busquedaRecursiva = false;
		buscadas = null;
		busquedaRealizada = false;
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
	
	
	public void reset(){
		buscadas = null;
		busquedaRealizada = false;
		buscadas = new ArrayList<CancionContainer>();
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
	 * Sigue el patrón Singleton y devuelve la instancia única de la biblioteca
	 *  musical.
	 *  
	 * @return singleBibliotecaMusical
	 */
	public static BibliotecaMusical getInstacia(){
		if(singleBibliotecaMusical == null)
			singleBibliotecaMusical = new BibliotecaMusical();
		
		return singleBibliotecaMusical;
	}
	
	
	/**
	 * Carga la biblioteca XML recibiendo la ruta en la que se ubica.
	 * 
	 * @param pathYfichero ruta absoluta al fichero XML de la biblioteca musical
	 * 
	 * @throws FileNotFoundException
	 */
	public void cargarXML(String pathYfichero) throws FileNotFoundException{
		try {
			File aux = new File(pathYfichero);
			if (aux.canRead()){
				canciones = 
					(BibliotecaContainer) 
					stream.fromXML(new FileInputStream(pathYfichero));
				this.canciones.generarRutasAbsolutas();
				canciones.setModificado(true);
				notificaCancionesModificadas();
			}else{
				System.out.println("El fichero no existe");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Solo guarda el XML si ha habido cambios en la biblioteca
	 * 
	 * @param pathYfichero
	 * 
	 * @throws FileNotFoundException
	 */
	public void guardarXML(String pathYfichero) throws FileNotFoundException{
		if(canciones.isModificado()){
			
			try {
				OutputStreamWriter w = new OutputStreamWriter(
						new FileOutputStream(pathYfichero),"UTF-8");
				stream.toXML(canciones, w);
				this.canciones.guardado();
			} catch (UnsupportedEncodingException e) {
				System.out.println("No se pudo guardar la biblioteca");
			}
			
		}
	}
	
	
	/**
	 * Fija si se quiere hacer búsqueda en los subdirectorios
	 * 
	 * @param busquedaRecursiva
	 */
	public void setBusquedaRecursiva(boolean busquedaRecursiva) {
		//this.busquedaRecursiva = busquedaRecursiva;
	}
	
	
	/**
	 * Devuelve una lista con todas las canciones de la biblioteca musical
	 * 
	 * @return ArrayList<CancionContainer>
	 */
	public ArrayList<CancionContainer> getCanciones(){
		return this.canciones.getArrayListCanciones();
	}
	
	/**
	 * Devuelve una lista con todas las canciones dbuscadas
	 * 
	 * @return ArrayList<CancionContainer>
	 */
	public ArrayList<CancionContainer> getCancionesBuscadas(){
		return this.buscadas;
	}
	
	/*
	 * Devuelve los elementos de la biblioteca musical que coincidan con el 
	 * criterio de búsqueda que recibe como parámetro.
	 * 
	 * @param busqueda es el criterio de búsqueda
	 * @return colección con los elementos que satisfacen el criterio de búsqueda
	 */
	/*
	public void realizaBusqueda(CriterioBusqueda busqueda){
		 this.buscadas = this.canciones.getListaBusqueda(busqueda);
		 		 
		 if (busqueda.getCadena().equals("")) busquedaRealizada = false;
		 else busquedaRealizada = true;
	}*/
	
	
	
	/**
	 * Devuelve si hay o no busqueda realizada
	 * @return
	 */
	public boolean getBusquedaRealizada(){
		return busquedaRealizada;
	}
	
	/**
	 * Devuelve los elementos de la biblioteca musical que coincidan con el 
	 * criterio de búsqueda	que recibe como parámetro. En vez de por 
	 * coincidencia total se hace la búsqueda por cualquier subcadena que este 
	 * contenida en otra.
	 * 
	 * @param busqueda es el criterio de búsqueda
	 * 
	 * @return colección con elementos que satisfacen el criterio de búsqueda
	 */
	public void realizaBusquedaAvanzada(CriterioBusqueda busqueda){
		this.buscadas =  this.canciones.getListaBusquedaAvanzada(busqueda);
		 
		 if (busqueda.getCadena().equals("")){
			 busquedaRealizada = false;
			 notificaNuevaBiblioteca(getCanciones());
		 }
		 else{
			 busquedaRealizada = true;
			 notificaNuevaBiblioteca(getCancionesBuscadas());
		 }
	}
	
	
	/**
	 * Dice si la biblioteca musical ha sido modificada en algún momento
	 * 
	 * @return true si la biblioteca ha sido modificada, false en otro caso
	 */
	public boolean isModificado(){
		return this.canciones.isModificado();
	}
	
	
	/**
	 * Actualiza todos los directorios existentes en la biblioteca
	 */
	public void actualizar(){
		ArrayList<String> dir = this.canciones.getDirectorios();
		this.canciones = new BibliotecaContainer();
		this.buscadas = new ArrayList<CancionContainer>();
		this.busquedaRealizada = false;
		this.canciones.setModificado(true);
		RecorreFicheros recorre = new RecorreFicheros(dir);
		recorre.setEstrategia(new CrearBiblioteca(this.canciones));
		recorre.recorre();
		notificaCancionesModificadas();
	}
	
	
	/**
	 * Añade y actualiza (en caso de que ya exista) directorios y canciones. 
	 * Si los directorios ya existían los borra y añade su información de nuevo. 
	 * Las canciones sólo se añaden si no existen previamente.
	 * 
	 * @param ficheros lista de ficheros y canciones para actualizar la biblio.
	 */
	@Deprecated
	public void actualizarDirectorios(ArrayList<String> ficheros){
		//Añadimos los directorios.
		RecorreFicheros recorre = new RecorreFicheros(ficheros);
		recorre.setEstrategia(new CrearBiblioteca(this.canciones));
		recorre.recorre();
		
		
		ArrayList<String> dir = this.canciones.getDirectorios();
		this.canciones = new BibliotecaContainer();
		this.canciones.setModificado(true);
		
		recorre = new RecorreFicheros(dir);
		recorre.setEstrategia(new CrearBiblioteca(this.canciones));
		recorre.recorre();
		notificaCancionesModificadas();
	}
	
	
	public void creaDirectorios(ArrayList<String> ficheros){
		this.canciones = new BibliotecaContainer();
		this.buscadas = new ArrayList<CancionContainer>();
		this.busquedaRealizada = false;
		this.canciones.setModificado(true);
		RecorreFicheros recorre = new RecorreFicheros(ficheros);
		recorre.setEstrategia(new CrearBiblioteca(this.canciones));
		recorre.recorre();
		notificaCancionesModificadas();
	}
	

	
	/**
	 * Añade canciones a la biblioteca en caso de que no existan previamente, 
	 * de ser así no hace nada al igual que si recibiese algún directorio en vez 
	 * de canciones. 
	 * Las canciones y directorios que ya existían no se ven modificadas
	 * 
	 * @param canciones lista de canciones a insertar
	 */
	public void aniadirCanciones(ArrayList<String> canciones){
		RecorreFicheros recorre = new RecorreFicheros(canciones);
		recorre.setEstrategia(new AniadirCanciones(this.canciones));
		recorre.recorre();
		notificaCancionesModificadas();

	}
	
	/**
	 * Añade canciones y directorios a la biblioteca en caso de que no existan 
	 * previamente, de ser así no hace nada. Las canciones y directorios que ya 
	 * existían no se ven modificadas.
	 * 
	 * @param canciones lista de canciones a insertar
	 */
	public void aniadir(ArrayList<String> canciones){
		RecorreFicheros recorre = new RecorreFicheros(canciones);
		recorre.setEstrategia(new Aniadir(this.canciones));
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
	
	
	
	/**
	 * Añade oyentes a la biblioteca
	 * 
	 * @param listener
	 */
	public void addBibliotecaListeners(BibliotecaListener listener){
		listeners.add(listener);
	}
	
	/**
	 * Ordena la lista biblioteca siguiendo un criterio de ordenacion
	 * que recibe como parametro
	 * 
	 * @param orden: criterio por el cual se desea ordenar la lista de reproduc.
	 */
	public void ordenar(Comparator<CancionContainer> orden){
		
		if (busquedaRealizada){
			Collections.sort(this.buscadas, orden);
			this.notificaNuevaBiblioteca(this.buscadas);
		}else{
			this.buscadas = this.getCanciones();
			Collections.sort(this.buscadas, orden);
			this.notificaNuevaBiblioteca(this.buscadas);
		}
	}
	
	/**
	 * Notifica a los oyentes que se ha cargado una nueva lista de canciones.
	 * Manda borrar la anteriory notifica la nueva
	 * 
	 * @param las canciones nuevas de la biblioteca
	 */
	private void notificaNuevaBiblioteca(ArrayList<CancionContainer> canciones){
		for (BibliotecaListener l : listeners) {
			l.reinicia();
			l.nuevaListaCanciones(canciones);
		}
	}
	
	/**
	 * Devuelve true si la biblioteca es vacia
	 * 
	 * @return boolean
	 */
	public boolean isVacia() {
		return (this.getCanciones().size() == 0);
	}
	
}

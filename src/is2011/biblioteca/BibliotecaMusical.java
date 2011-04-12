package is2011.biblioteca;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;
import is2011.biblioteca.util.ActualizarBiblioteca;
import is2011.biblioteca.util.AniadirCanciones;
import is2011.biblioteca.util.RecorreFicheros;


public class BibliotecaMusical {

	/** Contenedor de canciones para la biblioteca musical */
	private BibliotecaContainer canciones;
	
	/** Única instancia de la biblioteca musical. Sigue el patrón Singleton */
	private static BibliotecaMusical singleBibliotecaMusical = null;
	
	/** Flujo de lectura/escritura para ficheros XML */
	private XStream stream;
	
	/** Indica si se añaden canciones de los subdirectorios */
	private boolean busquedaRecursiva;
	
	
	
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
		stream.omitField(BibliotecaContainer.class, "modificado");
		stream.omitField(CancionContainer.class, "totalPath");
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
		canciones = (BibliotecaContainer) stream.fromXML(new FileInputStream(pathYfichero));
		this.canciones.generarRutasAbsolutas();
	}
	
	/**
	 * Solo guarda el XML si ha habido cambios en la biblioteca
	 * @param pathYfichero
	 * @throws FileNotFoundException
	 */
	public void guardarXML(String pathYfichero) throws FileNotFoundException{
		if(canciones.isModificado())
			stream.toXML(canciones, new FileOutputStream(pathYfichero));
	}
	
	
	
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
	
	
	// vale tanto para añadir como para actualizar directorios completos y sus subdirectorios
	public void actualizarDirectorios(ArrayList<String> ficheros){
		RecorreFicheros recorre = new RecorreFicheros(ficheros);
		recorre.setEstrategia(new ActualizarBiblioteca(this.canciones));
		recorre.recorre();
	}
	
	//TODO esta por implementar
	// añade canciones sueltas
	public void aniadirCanciones(ArrayList<String> canciones){
		RecorreFicheros recorre = new RecorreFicheros(canciones);
		recorre.setEstrategia(new AniadirCanciones(this.canciones));
		recorre.recorre();
	}
	

	
	
	
	
}

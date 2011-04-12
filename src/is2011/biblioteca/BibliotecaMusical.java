package is2011.biblioteca;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.thoughtworks.xstream.XStream;

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
		stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("track", CancionContainer.class);
		//TODO quitar esta linea o descomentar si se sigue usando DirectorioContainer.java
		//stream.alias("dir", DirectorioContainer.class);
		
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
	
	
	//TODO: quizás debería ser un método privado y llamarse siempre en el constructor, puesto que
	// la biblioteca debe estar siempre disponible
	public void cargarXML(String pathYfichero) throws FileNotFoundException{
		canciones = (BibliotecaContainer) stream.fromXML(new FileInputStream(pathYfichero));
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
	
	
	public void ordenar(Comparator<CancionContainer> criterioOrden){
		this.canciones.ordenar(criterioOrden);
	}
	
	
	public ArrayList<CancionContainer> getCanciones(){
		return this.canciones.getCanciones();
	}
	
	/**
	 * Accede al path o ruta de una canción mediante el orden que ocupa en la lista de canciones
	 * @param posicionEnLaLista
	 * @return ruta de la canción que ocupa el lugar 'posicionEnLaLista-ésimo' en la lista de canciones
	 */
	public String getPathCancion(int posicionEnLaLista){
		return this.canciones.getPathCancion(posicionEnLaLista);
	}
	
	/**
	 * Accede a una canción mediante el orden que ocupa en la lista de canciones
	 * @param posicionEnLaLista
	 * @return canción que ocupa el lugar 'posicionEnLaLista-ésimo' en la lista de canciones
	 */
	public CancionContainer getCancion(int posicionEnLaLista){
		return this.canciones.getCancion(posicionEnLaLista);
	}

	public void setBusquedaRecursiva(boolean busquedaRecursiva) {
		this.busquedaRecursiva = busquedaRecursiva;
	}
	
	
	
	// vale tanto para añadir como para actualizar directorios
	public void actualizarDirectorio(ArrayList<String> ficheros){
		RecorreFicheros recorre = new RecorreFicheros(ficheros);
		recorre.setEstrategia(new ActualizarBiblioteca(this.canciones));
		recorre.recorre();
	}
	
	
	public void aniadirCanciones(ArrayList<String> canciones){
		RecorreFicheros recorre = new RecorreFicheros(canciones);
		recorre.setEstrategia(new AniadirCanciones(this.canciones));
		recorre.recorre();
	}
	
	
}

package is2011.biblioteca;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;

import com.thoughtworks.xstream.XStream;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.Cancion;
import is2011.biblioteca.contenedores.Directorio;

public class BibliotecaMusical {

	private BibliotecaContainer canciones;
	
	/** Única instancia de la biblioteca musical. Sigue el patrón Singleton */
	private static BibliotecaMusical singleBibliotecaMusical = null;
	
	/** */
	private XStream stream;
	
	
	private BibliotecaMusical(){
		canciones = new BibliotecaContainer();
		stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", Cancion.class);
		stream.alias("dir", Directorio.class);
		//no introducimos el atributo modificado en la estructura del fichero XML
		stream.omitField(BibliotecaContainer.class, "modificado");
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
	
	// Solo guarda el XML si ha habido cambios en la biblioteca
	public void guardarXML(String pathYfichero) throws FileNotFoundException{
		if(canciones.isModificado())
			stream.toXML(canciones, new FileOutputStream(pathYfichero));
	}
	
	
	public void ordenar(Comparator criterioOrden){
		
	}
	
	
	
	
	
	
	
}

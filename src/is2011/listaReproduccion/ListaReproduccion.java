package is2011.listaReproduccion;

import is2011.biblioteca.contenedores.CancionContainer;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ListaReproduccion {

	/** Lista de canciones */
	private ArrayList<CancionContainer> listaCanciones;
	
	/** Flujo de lectura/escritura para ficheros XML */
	private XStream stream;
	
	/** Indica si la lista de reproducción sufrió algún cambio */
	private boolean modificado;
	
	
	
	/**
	 * Crea una lista de reproducción vacía
	 */
	public ListaReproduccion(){
		this.modificado = false;
		stream = new XStream(new DomDriver());
		stream.alias("track", CancionContainer.class);
		this.listaCanciones = new ArrayList<CancionContainer>();
	}
	
	
	/**
	 * Crea una lista de reproducción a partir de la ruta de un fichero. Este fichero
	 * se debe corresponder con un XML.
	 * @param path: ruta del fichero XML
	 */
	public ListaReproduccion(String path){
		this.modificado = false;
		stream = new XStream(new DomDriver());
		stream.alias("track", CancionContainer.class);
		try {
			this.cargarXML(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Añade una nueva canción a la lista de reproducción
	 * @param cancion: nueva canción que se añade
	 */
	public void addCancion(CancionContainer cancion){
		this.listaCanciones.add(cancion);
		modificado = true;
	}
	
	
	/**
	 * Añade las caciones que recibe como parámetro a la lista de reproducción
	 * @param canciones: canciones que se desea añadir a la lista de reproducción
	 */
	public void addCanciones(ArrayList<CancionContainer> canciones){
		this.listaCanciones.addAll(canciones);
		modificado = true;
	}
	
	
	/**
	 * Borra la canción que ocupa la posición que recibe como parámetro
	 * @param posicion: índice de la canción a borrar
	 */
	public void deleteCancion(int posicion){
		this.listaCanciones.remove(posicion);
		modificado = true;
	}
	
	
	/**
	 * Elimina la primra aparición del elemento canción que recibe como parámetro
	 * @param cancion
	 */
	public void deleteCancion(CancionContainer cancion){
		this.listaCanciones.remove(cancion);
		modificado = true;
	}
	
	
	/**
	 * Borra todas las canciones de la lista de reproducción que coinciden
	 * con las las canciones que recibe como parámetro
	 * @param canciones: lista de canciones que se desea borrar
	 */
	public void deleteCanciones(ArrayList<CancionContainer> canciones){
		this.listaCanciones.removeAll(canciones);
		modificado = true;
	}
	
	
	/**
	 * Carga la lista de reproducción XML recibiendo la ruta en la que se ubica.
	 * @param pathYfichero ruta absoluta al fichero XML de la lista de reproducción
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void cargarXML(String pathYfichero) throws FileNotFoundException{
		this.listaCanciones = (ArrayList<CancionContainer>) stream.fromXML(new FileInputStream(pathYfichero));
		modificado = true;
	}
	
	
	/**
	 * Solo guarda el XML si ha habido cambios en la lista de reproducción
	 * @param pathYfichero
	 * @throws FileNotFoundException
	 */
	public void guardarXML(String pathYfichero) throws FileNotFoundException{
		if(modificado)
			stream.toXML(listaCanciones, new FileOutputStream(pathYfichero));
	}
	
	
	/**
	 * Ordena la lista de reproducción siguiendo un criterio de ordenación
	 * que recibe como parámetro
	 * @param orden: es el criterio por el cuál se desea ordenar la lista de reproducción
	 */
	public void ordenar(Comparator<CancionContainer> orden){
		Collections.sort(this.listaCanciones, orden);
		modificado = true;
	}
	
	
	/**
	 * Fija una nueva lista de repoducción con las canciones que recibe como parámetro
	 * @param nuevaLista: Lista de canciones 
	 */
	public void setLista(ArrayList<CancionContainer> nuevaLista){
		this.listaCanciones = nuevaLista;
		modificado = true;
	}
	
}

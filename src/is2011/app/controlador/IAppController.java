package is2011.app.controlador;

import java.util.ArrayList;

import is2011.app.preferencias.PreferenciasSistema;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

/**
 * Controlador de la aplicacion , que recoje las ordenes de la vista principal
 * y ordena al reproductor lo que debe hacer.
 * 
 * Las funciones que podra realizar, en el campo de la reproduccion, seran.
 * 
 *  -> Cargar archivos en la lista de reproduccion.
 *  -> Encolar archivos en la lista de reproduccion.
 *  -> Reproducir.
 *  -> Pausar.
 *  -> Stop.
 *  -> FF y rewind.
 *  -> Siguiente cancion y anterior.
 *  -> Borrar canciones seleccionadas.
 *  -> Reproducir la cancion seleccionada.
 * 
 * @author Administrator
 *
 */
public interface IAppController {

	/**
	 * Indica al reproductor que haga sonar la cancion actual.
	 * @param cancionSeleccionada El numero de cancion que queremos reproducir.
	 * (desde el 0).
	 * @return false si no hay canciones o se ha llegado al final de la lista
	 * de reproduccion.
	 */
	public boolean play(int cancionSeleccionada);

	/**
	 * Para la cancion que esta sonando actualmente.
	 */
	public void stop();

	/**
	 * Adelante
	 */
	public void fastForward();

	/**
	 * Atras
	 */
	public void rewind();


	/**
	 * Pausa la cancion actual 
	 */
	public void pause();
	
	/**
	 * Indica al reproductor que vaya a un punto determinado de la cancion, 
	 * mediante un porcentaje.
	 * @param porcentaje Indica el lugar donde nos debemos situar en la cancion
	 */
	public void irA(float porcentaje);

	/**
	 * Borra la lista de reproduccion actual y añade los archivos que se abren.
	 */
	public void abrirArchivos();

	
	/**
	 * Añade al final de la lista de reproduccion actual los archivos que 
	 * seleccionamos
	 */
	public void aniadir();

	/**
	 * Actualiza la biblioteca musical desde una serie de ficheros que
	 * recibe como parametro 
	 * @param dir
	 */
	public void actualizarBiblioteca();
	
	/**
	 * Carga la biblioteca mediante un archivo XML
	 * * @param path
	 */
	public void cargarBiblioteca();
	
	/**
	 * Guarda la biblioteca en un archivo XML
	 * @param path
	 */
	public void guardarBiblioteca();
	
	/**
	 * Permite añadir canciones a la biblioteca.
	 * @param dir
	 */
	public void aniadirCancionesBiblioteca();
	
	/**
	 * Devuelve las canciones de la lista de reproduccion
	 * @return
	 */
	public ArrayList<CancionContainer> getCanciones();
	/**
	 * Reproduce la siguienete cancion.
	 */
	public void siguienteCancion();
	
	
	/**
	 * Reproduce la cancion anterior.
	 */
	public void cancionAnterior();
	
	
	/**
	 * Establece el modo de reproduccion
	 * @param modo El modo de reproduccion.
	 */
	public void setModoReproduccion(ModoReproduccionEnum modo);
	

	public void borrarCanciones();
	
	public void reproducirSeleccionada();
	
	public boolean listaReproduccionVacia();

	/**
	 * @param el volumen
	 * 
	 */
	public void setVolumen(float porcentaje);
	
	/**
	 * Cargar una cancion en laLista de Reproduccion desde la biblioteca
	 */
	public void fromBibliotecaToListaReproduccion(String path);
	
	/**
	 * Actualiza las preferencias del sistema con los parametros recogidos a traves de la interfaz
	 * @param pathBib
	 * @param pathListaRep
	 * @param modo
	 */
	public void actualizaPreferencias(String pathBib, String pathListaRep, ModoReproduccionEnum modo);
	
	
	/**
	 * Devuevle las preferencias del sistema
	 * @return
	 */
	public PreferenciasSistema getPreferencias();

	/**
	 * Borra una cancion determinada por su numero de orden en la lista de repro
	 * duccion
	 * @param La cancion
	 */
	public void borrarCancion(int numCancion);

	/**
	 * Manda ordenar la lista de reproduccion por artista
	 */
	public void ordenarPorArtista();

	/**
	 * Manda ordenar la lista de reproduccion por genero
	 */
	public void ordenarPorGenero();

	/**
	 * Manda ordenar la lista de reproduccion por album
	 */
	public void ordenarPorAlbum();

	/**
	 * Manda ordenar la lista de reproduccion por duracion
	 */
	public void ordenarPorDuracion();

	/**
	 * Manda ordenar la lista de reproduccion por titulo
	 */
	public void ordenarPorTitulo();
	
	/**
	 * Guarda la lista de reproduccion actual en formato XML
	 */
	public void guardarListaReproduccion();
	
	/**
	 * Carga una lista de reproduccion XML como Lista de Reproduccion actual
	 */
	public void cargarListaReproduccion();
	
	/**
	 * Carga la biblioteca y la lista de reproduccion por defecto, si las hubiera.
	 */
	public void cargarArchivosPreferencias();
}

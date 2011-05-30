package is2011.app.controlador;

import java.util.ArrayList;

import is2011.app.preferencias.Preferencias;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.CriterioBusqueda;
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
	 * Crea la biblioteca musical desde una serie de ficheros
	 */
	public void crearBiblioteca();
	
	/**
	 * Actualiza la biblioteca musical 
	 */
	public void actualizarBiblioteca();
	
	/**
	 * Carga la biblioteca mediante un archivo XML
	 */
	public void cargarBiblioteca();
	
	/**
	 * Guarda la biblioteca en un archivo XML
	 */
	public void guardarBiblioteca();
	
	/**
	 * Permite añadir canciones a la biblioteca.
	 */
	public void aniadirCancionesBiblioteca();
	
	/**
	 * Permite añadir canciones y carpetas con canciones a la biblioteca.
	 */
	public void aniadirCancionesYCarpetasBiblioteca();
	
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
	

	public boolean listaReproduccionVacia();

	/**
	 * @param el volumen
	 * 
	 */
	public void setVolumen(float porcentaje);
	
	/**
	 * Cargar una cancion en laLista de Reproduccion desde la biblioteca
	 */
	public void fromBibliotecaToListaReproduccion(CancionContainer c);
	
	
	/**
	 * Devuevle las preferencias del sistema
	 * @return
	 */
	public Preferencias getPreferencias();

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
	
	/**
	 * Realiza una busqueda sobre la biblioteca y devuelve lo buscado
	 * @param criterio
	 */ 
	
	//public ArrayList<CancionContainer> buscaBiblioteca(CriterioBusqueda criterio);
	
	/**
	 * Realiza una busqueda sobre la lista de reproduccion
	 * @param criterio
	 * @return
	 */
//	public ArrayList<CancionContainer> buscaListaReproduccion(CriterioBusqueda criterio);
	
	/**
	 * Realiza una busqueda avanzada sobre una biblioteca
	 * @param criterio
	 */
	public void buscaBibliotecaAvanzada(CriterioBusqueda criterio);
	
	/**
	 * Realiza una busqueda avanzada sobre una lista de reproduccion
	 * @param criterio
	 */
	public void buscaListaReproduccionAvanzada(CriterioBusqueda criterio);
	
	/**
	 * Devuelve las canciones que componen la lista de reproduccion actual
	 * @return
	 */
	public ArrayList<CancionContainer> getCancionesListaReproduccion();
	
	/**
	 * Le pasamos la posicion que la cancion ocupa en la biblioteca y esta 
	 * se pasa a la lista de reproduccion actual
	 * @param La posicion de la cancion en la bib
	 */
	public void fromBibliotecaToListaReproduccion(int posicion);
	
	/**
	 * Le pasamos un array de posiciones, que indican la sposiciones de las 
	 * canciones en la biblioteca que queremso pasar a la lista de reproduccion
	 * actual.
	 * @param El array de posiciones
	 */
	public void fromBibliotecaToListaReproduccion(int[] posiciones);

	/**
	 * Ordena las canciones de la biblioteca por album
	 */
	public void ordenarBibliotecaPorAlbum();

	/**
	 * Ordena las canciones de la biblioteca por genero
	 */
	public void ordenarBibliotecaPorGenero();

	/**
	 * Ordena las canciones de la biblioteca por artista
	 */
	public void ordenarBibliotecaPorArtista();

	/**
	 * Ordena las canciones de la biblioteca por duracion
	 */
	public void ordenarBibliotecaPorDuracion();

	/**
	 * Ordena las canciones de la biblioteca por titulo
	 */
	public void ordenarBibliotecaPorTitulo();

	/**
	 * Realizas las operaciones oportunas antes de cerrar la apliacion...
	 * Guarda la las preferencias, lista de reproduccion....
	 */
	public void requestSalir();

	/**
	 * Deja sin sonido el reproductor.
	 */
	public void mute();

	/**
	 * @param string
	 */
	public void openLR(String string);

	/**
	 * 
	 */
	public void muestraListaReproduccion();

	/**
	 * 
	 */
	public void borrarListaReproduccion();

	public void lanzarAyuda();

}

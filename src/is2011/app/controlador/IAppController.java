package is2011.app.controlador;

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
	 * @return false si no hay canciones o se ha llegado al final de la lista
	 * de reproduccion.
	 */
	public boolean play();

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
	public void añadir();

	/**
	 * Reproduce la siguienete cancion.
	 */
	public void siguienteCancion();
	
	
	/**
	 * Reproduce la cancion anterior.
	 */
	public void cancionAnterior();
	

	public void borrarCanciones();
	
	public void reproducirSeleccionada();
}

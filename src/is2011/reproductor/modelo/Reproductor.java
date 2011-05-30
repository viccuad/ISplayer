package is2011.reproductor.modelo;

import is2011.biblioteca.contenedores.CancionContainer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * 
 * Interfaz que deberan implementar todas las clases que funcionen como un 
 * reproductor.
 * @author Administrator
 *
 */
public interface Reproductor {

	/**
	 * Empieza a reproducir la cancion en caso de estar esta cargada con open
	 * @throws BasicPlayerException
	 */
	public void play() throws BasicPlayerException;
	
	/**
	 * Para al cancion
	 * @throws BasicPlayerException
	 */
	public void stop() throws BasicPlayerException;
	
	/**
	 * Pausa
	 * @throws BasicPlayerException
	 */
	public void pause() throws BasicPlayerException;
	
	/**
	 * Despausa
	 * @throws BasicPlayerException
	 */
	public void resume() throws BasicPlayerException;
	
	/**
	 * Pasa rapido hacia delante
	 * @param velocidad Parametro de 1 a 4 que indica al velocidad de avance
	 * @throws BasicPlayerException
	 */
	public void fastForward(int velocidad) throws BasicPlayerException;
	
	/**
	 * Rebobinado
	 * @param velocidad Parametro de 1 a 4 que indica la velocidad de retroceso
	 * @throws BasicPlayerException
	 */
	public void rewind(int velocidad) throws BasicPlayerException;
	
	/**
	 * Va a un punto de la cancion , determinado por el porcentaje de 0 a 1
	 * @param porcentaje
	 * @throws BasicPlayerException
	 */
	public void irA(float porcentaje) throws BasicPlayerException;
	
	/**
	 * Abre el fichero que le pasamos como parametro para su reproduccion
	 * @param c La cancion que queremos abrir
	 * @throws BasicPlayerException
	 * @throws Exception 
	 */
	public void open(CancionContainer c) throws BasicPlayerException, Exception;
	
	/**
	 * Añade un oyente al reproductor
	 * @param bpl El oyente que queremos añadir
	 */
	public void addBasicPlayerListener(BasicPlayerListener bpl);

	/**
	 * @param borrarOyentes 
	 * 
	 */
	public void reiniciar(boolean borrarOyentes);

	/**
	 * Devuelve si la cancion actual esta pausada o no.
	 * @return Si esta pausada
	 */
	public boolean isPaused();
	/**
	 * Devuelve si la lista de reproduccion esta vacia.
	 * @return Si esta vacia
	 */

	/**
	 * Establece el volumen
	 * @param porcentaje
	 */
	public void setVolumen(float porcentaje);

	/**
	 * Indica si el reproductor esta en silencio.
	 * @return True si esta en silencio
	 */
	boolean isMute();

	/**
	 * Pone el reproductor en silencio
	 * @param mute True si queremos ponerlo en silencio
	 */
	void setMute(boolean mute);

	
}

/**
 * 
 */
package is2011.reproductor.modelo.listeners;

import java.util.ArrayList;

import is2011.biblioteca.contenedores.CancionContainer;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

/**
 * 
 * Listener de la lista de reproduccion.
 * Estos metodos seran llamados por el modelo de la lista de reproduccion
 * cuando se produzcan las acciones que ellos mismos indican.
 * @author Administrator
 *
 */
public interface ListaReproduccionListener {
	
	/**
	 * Se llama cuando se añade una nueva cancion a la lista de reproduccion
	 * @param e El evento con la informacion de la nueva cancion
	 */
	public void nuevaCancion(NuevaCancionEvent e);
	
	/**
	 * Se llema cuando se borra una cancion.
	 * @param e El evento con la informacion de la cancion que se borra
	 */
	public void borrarCancion(BorrarCancionEvent e);
	
	/**
	 * Se llama cuando se cambia el tipo de reproduccion.(Normal, aleatorio...)
	 * @param modo El modo de reproduccion.
	 */
	public void cambioTipoReproduccion(ModoReproduccionEnum modo);
	
	/**
	 * Se llama cuando cambia la cancion que se reproduce actualmente.
	 * @param actualNuevo La cancion actual nueva
	 * @param actualViejo La cancion actual vieja
	 */
	public void setActual(int actualNuevo, int actualViejo);
	
	/**
	 * Se llama cuando se han de borrar todas las canciones.
	 */
	public void reinicia();

	/**
	 * Se llama cuanso se crea una nueva lista de reproduccion.
	 */
	public void nuevaListaReproduccion(ArrayList<CancionContainer> c);
}

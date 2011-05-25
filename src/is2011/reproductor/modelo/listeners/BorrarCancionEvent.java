/**
 * 
 */
package is2011.reproductor.modelo.listeners;

/**
 * Evento con toda la informacion necesaria para borrar una cancion.
 * 
 * @author Administrator
 *
 */
public class BorrarCancionEvent {

	/** La posicion que ocupa esta cancion*/
	private int posicion;

	public BorrarCancionEvent(int posicion) {
		super();
		this.posicion = posicion;
	}

	/**
	 * @return the posicion
	 */
	public int getPosicion() {
		return posicion;
	}
	
	
}

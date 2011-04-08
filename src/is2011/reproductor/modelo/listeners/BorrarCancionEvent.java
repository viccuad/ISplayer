/**
 * 
 */
package is2011.reproductor.modelo.listeners;

/**
 * @author Administrator
 *
 */
public class BorrarCancionEvent {

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

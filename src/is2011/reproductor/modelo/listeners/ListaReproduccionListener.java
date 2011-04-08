/**
 * 
 */
package is2011.reproductor.modelo.listeners;

/**
 * @author Administrator
 *
 */
public interface ListaReproduccionListener {

	
	public void nuevaCancion(NuevaCancionEvent e);
	
	public void borrarCancion(BorrarCancionEvent e);
	
	public void setAleatorio(boolean aleatorio);
	
	public void setActual(int actualNuevo, int actualViejo);
	
	public void reinicia();
}

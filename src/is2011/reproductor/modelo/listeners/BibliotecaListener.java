/**
 * 
 */
package is2011.reproductor.modelo.listeners;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.ArrayList;


/**
 * @author Administrator
 *
 */
public interface BibliotecaListener {
	
	public void nuevaCancion(NuevaCancionEvent e);
	
	public void borrarCancion(BorrarCancionEvent e);
			
	public void reinicia();
	
	public void mostrarTodas();

	/**
	 * @param canciones
	 */
	public void nuevaListaCanciones(ArrayList<CancionContainer> canciones);
}
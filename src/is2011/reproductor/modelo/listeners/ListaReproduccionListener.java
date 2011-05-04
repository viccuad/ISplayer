/**
 * 
 */
package is2011.reproductor.modelo.listeners;

import java.util.ArrayList;

import is2011.biblioteca.contenedores.CancionContainer;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

/**
 * @author Administrator
 *
 */
public interface ListaReproduccionListener {
	
	public void nuevaCancion(NuevaCancionEvent e);
	
	public void borrarCancion(BorrarCancionEvent e);
	
	public void cambioTipoReproduccion(ModoReproduccionEnum modo);
	
	public void setActual(int actualNuevo, int actualViejo);
	
	public void reinicia();

	public void nuevaListaReproduccion(ArrayList<CancionContainer> c);
}

/**
 * 
 */
package is2011.reproductor.modelo.listeners;

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
}

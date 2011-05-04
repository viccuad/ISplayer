/**
 * 
 */
package is2011.reproductor.modelo.listeners;


/**
 * @author Administrator
 *
 */
public interface BibliotecaListener {
	
	public void nuevaCancion(NuevaCancionEvent e);
	
	public void borrarCancion(BorrarCancionEvent e);
			
	public void reinicia();
	
	public void mostrarTodas();
}
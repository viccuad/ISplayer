/**
 * 
 */
package is2011.app.controlador;

import java.awt.FileDialog;
import java.io.File;

/**
 * @author Administrator
 *
 */
public interface IAppController {

	/**
	 * 
	 */
	public void play();

	/**
	 * 
	 */
	public void stop();

	/**
	 * 
	 */
	public void fastForward();

	/**
	 * 
	 */
	public void rewind();

	/**
	 * @param file
	 */
	public void open(File file);

	/**
	 * @param i
	 */
	public void irA(float i);

	/**
	 * @return
	 */
	public void abrirArchivos();

	/**
	 * 
	 */
	public void pause();

	public void aniadir();

	/**
	 * 
	 */
	public void siguienteCancion();
	
	
	public void cancionAnterior();
	

}

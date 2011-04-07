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
	void play();

	/**
	 * 
	 */
	void stop();

	/**
	 * 
	 */
	void fastForward();

	/**
	 * 
	 */
	void rewind();

	/**
	 * @param file
	 */
	void open(File file);

	/**
	 * @param i
	 */
	void irA(float i);

	/**
	 * @return
	 */
	File abrirArchivo();

	/**
	 * 
	 */
	void pause();

	/**
	 * 
	 */
	

}

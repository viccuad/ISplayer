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
public interface iAppController {

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
	 * 
	 */
	

}

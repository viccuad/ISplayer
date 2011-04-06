/**
 * 
 */
package is2011.reproductor.modelo;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * @author Administrator
 *
 */
public interface Reproductor {

	public void play() throws BasicPlayerException;
	
	public void stop() throws BasicPlayerException;
	
	public void pause() throws BasicPlayerException;
	
	public void resume() throws BasicPlayerException;
	
	public void fastForward(int velocidad) throws BasicPlayerException;
	
	public void rewind(int velocidad) throws BasicPlayerException;
	
	public void irA(float porcentaje) throws BasicPlayerException;
	
	public void open(File f) throws BasicPlayerException;
	
}

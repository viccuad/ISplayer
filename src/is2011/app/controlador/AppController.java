/**
 * 
 */
package is2011.app.controlador;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import is2011.reproductor.controlador.Controlador;
import is2011.reproductor.modelo.*;
import is2011.reproductor.vista.VistaReproduccion;

/**
 * 
 * Controlador que recoje las ordenes de la vista
 * 
 * @author Administrator
 *
 */
public class AppController implements IAppController {

	private Controlador reproductor;
	
	public AppController() {
		//TODO poner esto donde debe estar.
		this.reproductor = new Controlador();
		
		BasicPlayerListener bpl = new VistaReproduccion();
		
		this.reproductor.getReproductor().addBasicPlayerListener(bpl);
		
	}
	
	
	@Override
	public void fastForward() {
		reproductor.fastForward();
		
	}

	
	@Override
	public void play() {
		reproductor.play();
		
	}

	@Override
	public void rewind() {
		reproductor.rewind();
	}
	
	@Override
	public void stop() {
		reproductor.stop();
		
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.iAppController#open(java.io.File)
	 */
	@Override
	public void open(File file) {
		reproductor.open(file);
		
	}


	/* (non-Javadoc)
	 * @see is2011.app.controlador.iAppController#irA(int)
	 */
	@Override
	public void irA(float i) {
		reproductor.irA(i);
		
	}

}

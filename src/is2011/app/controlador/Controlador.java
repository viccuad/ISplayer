/**
 * 
 */
package is2011.app.controlador;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import is2011.reproductor.modelo.*;
import is2011.reproductor.vista.VistaReproduccion;

/**
 * @author Administrator
 *
 */
public class Controlador implements iAppController {

	private Reproductor reproductor;
	
	public Controlador() {
		//TODO poner esto donde debe estar.
		this.reproductor = new ReproductorIS();
		
		BasicPlayerListener bpl = new VistaReproduccion();
		
		this.reproductor.addBasicPlayerListener(bpl);
		
	}
	
	
	@Override
	public void fastForward() {
		try {
			this.reproductor.fastForward(4);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	@Override
	public void play() {
		try {
			this.reproductor.play();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void rewind() {
		try {
			this.reproductor.rewind(4);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void stop() {
		try {
			this.reproductor.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.iAppController#open(java.io.File)
	 */
	@Override
	public void open(File file) {
		try {
			this.reproductor.open(file);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

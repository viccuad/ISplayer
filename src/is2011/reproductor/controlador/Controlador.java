/**
 * Clase encargada de manejar el reproductor y el playList.
 */
package is2011.reproductor.controlador;

import is2011.reproductor.modelo.Reproductor;
import is2011.reproductor.modelo.ReproductorIS;
import is2011.reproductor.vista.VistaReproduccion;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * Clase con una instancia del reproductor y otra del playList. Se encarga de
 * reproducir el playList segun las operaciones del usuario.
 * 
 * @author Administrator
 *
 */
public class Controlador {

	private Reproductor reproductor;
	
	public Controlador() {
		
	}
	
	public void setReproductor(Reproductor rep) {
		this.reproductor = rep;
	}
	
	
	public void fastForward() {
		try {
			this.reproductor.fastForward(4);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	public void play() {
		try {
			this.reproductor.play();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void rewind() {
		try {
			this.reproductor.rewind(4);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void stop() {
		try {
			this.reproductor.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void open(File file) {
		try {
			this.reproductor.open(file);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void irA(float i) {
		try {
			reproductor.irA(i);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public Reproductor getReproductor() {
		return reproductor;
	}

}

/**
 * Clase encargada de manejar el reproductor y el playList.
 */
package is2011.reproductor.controlador;

import is2011.reproductor.modelo.Cancion;
import is2011.reproductor.modelo.CancionMP3;
import is2011.reproductor.modelo.ListaReproduccion;
import is2011.reproductor.modelo.Reproductor;


import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerException;


/**
 * Clase con una instancia del reproductor y otra del playList. Se encarga de
 * reproducir el playList segun las operaciones del usuario.
 * 
 * @author Administrator
 *
 */
public class Controlador {

	private Reproductor reproductor;
	private ListaReproduccion listaReproduccion;
	private int contadorSaturado;
	
	public Controlador() {
		contadorSaturado = 0;
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

	
	/**
	 * Reproduce la cancion actual de la lista de reproduccion.
	 */
	public void play() {
		try {
			if(!listaReproduccion.isVacia()) {
				Cancion cancion = listaReproduccion.getCancionAt(contadorSaturado++);
				this.reproductor.open(new File(cancion.getPath()));
				this.reproductor.setBytesMusica(cancion.getBytesMusica());
				this.reproductor.play();
				contadorSaturado %= listaReproduccion.getNumeroCanciones();
			}
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

	/**
	 * 
	 */
	public void pausar() {
		
		try {
			reproductor.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void aniadir(String absolutePath) {
		
		listaReproduccion.addCancion(new CancionMP3(absolutePath));
		System.out.println(listaReproduccion);
		
	}

	/**
	 * @param lr
	 */
	public void setListaReproduccion(ListaReproduccion lr) {
		this.listaReproduccion = lr;
		
	}

}

/**
 * Clase encargada de manejar el reproductor y el playList.
 */
package is2011.reproductor.controlador;

import is2011.reproductor.modelo.Cancion;
import is2011.reproductor.modelo.CancionMP3;
import is2011.reproductor.modelo.ListaReproduccion;
import is2011.reproductor.modelo.Reproductor;


import javazoom.jlgui.basicplayer.BasicPlayerException;


/**
 * Clase con una instancia del reproductor y otra del playList. Se encarga de
 * reproducir el playList segun las operaciones del usuario.
 * 
 * @author Administrator
 *
 */
public class ControladorReproductor {
	
	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	/** Instancia del reproductor*/
	private Reproductor reproductor;
	
	/** Instancia de la lista de reproduccion*/
	private ListaReproduccion listaReproduccion;
	
	// ********************************************************************** //
	// *************                CONSTRUCTOR                 ************* //
	// ********************************************************************** //
	
	/**
	 * Constructor por defecto.
	 */
	public ControladorReproductor() {
		
	}
	
	/**
	 * Reinicia el modelo
	 * @param borrarOyentes Indica si al reiniciar al modelo hay que borrar
	 * a los oyentes.
	 */
	public void reiniciar(boolean borrarOyentes) {
		reproductor.reiniciar(borrarOyentes);
		listaReproduccion.reiniciar(borrarOyentes);
	}
	
	// ********************************************************************** //
	// *************              METODOS PUBLICOS              ************* //
	// ********************************************************************** //
	/**
	 * Reproduce la cancion actual de la lista de reproduccion.
	 */
	public void play() {
		int cancionActual = listaReproduccion.getActual();
		
		//Si es la priemra cancion y hay canciones...
		if (cancionActual == 0 && listaReproduccion.getNumeroCanciones() > 0) {
			listaReproduccion.setActual(++cancionActual);
		}
		
		if(listaReproduccion.getNumeroCanciones() >= cancionActual 
				&& cancionActual > 0) {
			Cancion cancion = this.listaReproduccion.getCancionAt(cancionActual -1 );
			try {
				this.reproductor.open(cancion);
				this.reproductor.play();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Adelanta la cancion mas rapido.
	 */
	public void fastForward() {
		try {
			this.reproductor.fastForward(4);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Retrasa la cancion.
	 */
	public void rewind() {
		try {
			this.reproductor.rewind(4);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Para la cancion que esta sonando actualmente.
	 */
	public void stop() {
		try {
			this.reproductor.stop();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Va a un putno determinado de la cancion.
	 * @param i El punto donde queremos ir. Es un porcentaje de 0 a 1.
	 */
	public void irA(float i) {
		try {
			reproductor.irA(i);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Pausa la cancion, o la retoma, dependiendo de si esta ya pausada o no.
	 */
	public void pausar() {
		try {
			if(reproductor.isPaused()) {
				reproductor.resume();
			}else {
				reproductor.pause();
			}
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		
	}

	public void añadirCancion(String absolutePath) {
		if(absolutePath.endsWith(".mp3")) {
			listaReproduccion.addCancion(new CancionMP3(absolutePath));
		}
	}


	/**
	 * Reinicia la lista de reproduccion.
	 * @param borrarOyentes Indica si tambien tiene que reiniciar los oyentes.
	 */
	public void reiniciaListaReproduccion(boolean borrarOyentes) {
		this.listaReproduccion.reiniciar(borrarOyentes);
		
	}

	/**
	 * Pasa a la siguiente cancion. Dependiendo del modo de reproduccion (
	 * aleatorio, repeteir todos, repetir ultima etc etc, su funcionamiento 
	 * sera diferente.
	 */
	public void siguiente() {
		int actual = this.listaReproduccion.getActual();
		
		if (actual == this.listaReproduccion.getNumeroCanciones()) {
			this.stop();
		} else {
			actual = actual + 1;
			this.listaReproduccion.setActual(actual);
			this.play();
		}
	}

	/**
	 * Coloca actual en la cancion anterior. Dependiendo de aleatorio, repetir
	 * etc etc.
	 * 
	 * Si la posicion es la primera cancion, no hacemos nada.
	 */
	public void anterior() {
		int actual = this.listaReproduccion.getActual();
		
		if ( actual == 1) {
			this.stop();
			//Estamos en la ultima cancion.
			//No hacemos nada
		} else {
			actual = actual - 1;
			this.listaReproduccion.setActual(actual);
			this.play();
		}
	}
	
	
	/**
	 * Establece la lista de reproduccion a la que pasamos como parametro.
	 * @param listaReproduccion La lista de reproduccion que esablecemos.
	 */
	public void setListaReproduccion(ListaReproduccion listaReproduccion) {
		this.listaReproduccion = listaReproduccion;
	}
	
	/**
	 * Establece el reproductor a rep
	 * @param rep El reproductor de las canciones
	 */
	public void setReproductor(Reproductor rep) {
		this.reproductor = rep;
	}

	/**
	 * Devuelve el reproductor actual.
	 * @return El reproductor actual.
	 */
	public Reproductor getReproductor() {
		return reproductor;
	}

}

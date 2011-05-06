/**
 * Clase encargada de manejar el reproductor y el playList.
 */
package is2011.reproductor.controlador;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import is2011.app.preferencias.Preferencias;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.CriterioBusqueda;
import is2011.reproductor.modelo.ListaReproduccion;
import is2011.reproductor.modelo.Reproductor;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;


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
	
	/** Objeto utilizado para generar números aleatorios. */
	private transient Random rnd;
	
	// ********************************************************************** //
	// *************                CONSTRUCTOR                 ************* //
	// ********************************************************************** //
	
	/**
	 * Constructor por defecto.
	 */
	public ControladorReproductor() {
		this.rnd = new Random();
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
	// *************              METODOS 	PRIVADOS            ************* //
	// ********************************************************************** //
	
	/**
	 * Reproduce la cancion actual de la lista de reproduccion.
	 */
	private void play() {
		
		int cancionActual = listaReproduccion.getActual();
		
		//Si es la priemra cancion y hay canciones...
		if (cancionActual == 0 && listaReproduccion.getNumeroCanciones() > 0) {
			listaReproduccion.setActual(++cancionActual);
		}
		
		if(listaReproduccion.getNumeroCanciones() >= cancionActual 
				&& cancionActual > 0) {
			CancionContainer cancion = this.listaReproduccion.getCancionAt(cancionActual -1 );
			try {
				this.reproductor.open(cancion);
				this.reproductor.play();
				this.reproductor.setVolumen(Preferencias.getInstancia().getVolumen());
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	// ********************************************************************** //
	// *************              METODOS PUBLICOS              ************* //
	// ********************************************************************** //
	public void play(int cancionSeleccionada) {
		if(cancionSeleccionada >= 0 && cancionSeleccionada < this.listaReproduccion.getNumeroCanciones()){
			listaReproduccion.setActual(cancionSeleccionada + 1);
		}
		this.play();
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

	
	public void aniadirCancion(String absolutePath) {
		if(absolutePath.toLowerCase().endsWith(".mp3")) {
			listaReproduccion.addCancion(new CancionContainer(absolutePath));
		} 
		//TODO quitar esto si al final no se hace ogg!
		/*
		else if(absolutePath.toLowerCase().endsWith(".ogg")) {
			listaReproduccion.addCancion(new CancionOGG(absolutePath));
		}
		*/
	}

	/**
	 * Añade una canción a la lista de reproducción
	 * @param cancion
	 */
	public void aniadirCancion(CancionContainer cancion){
		listaReproduccion.addCancion(cancion);
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
		int actual = 0;
		ModoReproduccionEnum modo = this.listaReproduccion.getModoReproduccion();
		
		
		//Si repetimos 1
		if(modo == ModoReproduccionEnum.REPETIR_UNO) {
			actual = this.listaReproduccion.getActual();
			
		}else if(modo == ModoReproduccionEnum.REPETIR_TODOS) {
			actual = (this.listaReproduccion.getActual() % this.listaReproduccion.getNumeroCanciones()) +1;
			
		}else if (modo == ModoReproduccionEnum.ALEATORIO){
			actual = rnd.nextInt(this.listaReproduccion.getNumeroCanciones()) +1;
		
		}else if(modo == ModoReproduccionEnum.NORMAL) {
			actual = this.listaReproduccion.getActual() + 1;
		}
		
		if (modo == ModoReproduccionEnum.NORMAL  && 
				actual-1 == this.listaReproduccion.getNumeroCanciones()) {
			actual = this.listaReproduccion.getNumeroCanciones() ;
			this.stop();
		} else {
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
		int actual = 0;
		ModoReproduccionEnum modo = this.listaReproduccion.getModoReproduccion();
		
		
		//Si repetimos 1
		if(modo == ModoReproduccionEnum.REPETIR_UNO) {
			actual = this.listaReproduccion.getActual();
			
		}else if(modo == ModoReproduccionEnum.REPETIR_TODOS) {
			actual = this.listaReproduccion.getActual()- 1;
			if(actual == 0) {
				actual = this.listaReproduccion.getNumeroCanciones();
			}
			
		}else if (modo == ModoReproduccionEnum.ALEATORIO){
			actual = rnd.nextInt(this.listaReproduccion.getNumeroCanciones()) +1;
		
		}else if(modo == ModoReproduccionEnum.NORMAL ) {
			actual = this.listaReproduccion.getActual()-1;
			
		}
		
		if ( modo == ModoReproduccionEnum.NORMAL && (actual + 1) == 1) {
			actual = 1;
			this.stop();
			//Estamos en la ultima cancion.
			//No hacemos nada
		} else {
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

	/**
	 * @param modo
	 */
	public void setModoRepdroduccion(ModoReproduccionEnum modo) {
		this.listaReproduccion.setModoReproduccion(modo);
		
	}
	
	public boolean listaReproduccionVacia(){
		return listaReproduccion.isVacia();
	}

	/**
	 * @param porcentaje
	 */
	public void setVolumen(float porcentaje) {
		this.reproductor.setVolumen(porcentaje);
		
	}

	/**
	 * Borra de la lista actual la cancion correspondiente.
	 * @param numCancion Es recibido desde 0.
	 */
	public void borrarCancion(int numCancion) {
		int actual = this.listaReproduccion.getActual();
		
		//Si estamos borrando la cancion actual...
		if(numCancion == actual-1) {
			try {
				reproductor.stop();
				//Si la cancion actual era la ultima...
				if(listaReproduccion.getNumeroCanciones() == actual) {
					listaReproduccion.setActual(actual-1);
				}else {
					//Si no era la ultima, la incrementamos en uno.
					listaReproduccion.setActual(actual + 1);
				}
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
		this.listaReproduccion.removeCancion(numCancion);
		
	}

	/**
	 * Manda ordenar la lista de reproduccion segun el sort que se le 
	 * pasa como parametro.
	 * @param sortAlbum
	 */
	public void sort(Comparator<CancionContainer> sort) {
		this.listaReproduccion.ordenar(sort);
	}
	
	

	/**
	 * Llama a la lista de reproduccion para decirle que se guarde en un lugar concreto.
	 * @param path
	 */
	public void guardarListaReproduccion(String path){
		try {
			listaReproduccion.guardarXML(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Llama a la lista de repoduccion para que cargue el contenido de una lista guardada
	 * @param path
	 */
	public void cargarListaReproduccion(String path){
		try {
			listaReproduccion.cargarXML(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Devuelve las canciones de la lista de reproduccion
	 * @return
	 */
	public ArrayList<CancionContainer> getCancionesListaReproduccion(){
		return listaReproduccion.getCancionesListaReproduccion();
	}
	
	/**
	 * Metodo que devuelve la lista de reproduccion filtrada con los parametros de busqueda
	 * @param c
	 * @return
	 */
	public ArrayList<CancionContainer> getBusqueda(CriterioBusqueda c){
		return listaReproduccion.getBusqueda(c);
	}
	
	
	/**
	 * Metodo que devuelve la lista de reproduccion filtrada con los parametros de busqueda avanzada
	 * @param c
	 * @return
	 */
	public ArrayList<CancionContainer> getBusquedaAvanzada(CriterioBusqueda c){
		return listaReproduccion.getBusquedaAvanzada(c);
	}

}

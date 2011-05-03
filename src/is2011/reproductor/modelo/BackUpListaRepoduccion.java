package is2011.reproductor.modelo;


import is2011.reproductor.modelo.listeners.*;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Clase que almacena toda la informacion referente a la lista de reproduccion
 * del reproductor.
 * @author Administrator
 *
 */


public class BackUpListaRepoduccion {
	
	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	
	/** Tipo de reproduccion */
	public static enum ModoReproduccionEnum {
		NORMAL,
		ALEATORIO,
		REPETIR_UNO,
		REPETIR_TODOS
	}
	
	/** Lista de canciones */
	private ArrayList<Cancion> listaReproduccion;
	
	/** Cancion actual. va de 0 a numero de canciones*/
	private int actual;
	
	/** Indica si la reproduccion es en modo aleatorio*/
	private ModoReproduccionEnum modoReproduccion;

	/** Lista de listeners del reproductor */
	private ArrayList<ListaReproduccionListener> listeners;
	
	// ********************************************************************** //
	// *************                CONSTRUCTOR                 ************* //
	// ********************************************************************** //
	
	/**
	 * Constructor por defecto.
	 * Crea lista de reproduccion y oyentes.
	 */
	public BackUpListaRepoduccion(){
		listaReproduccion = new ArrayList<Cancion>();
		actual = 0;
		modoReproduccion = ModoReproduccionEnum.NORMAL;
		listeners = new ArrayList<ListaReproduccionListener>();
	}
	
	/**
	 * Reinicia la lista de reproduccion. (Quita todas las canciones, actual = 0
	 * aleatorio lo pone a false...
	 * 
	 * @param borrarOyentes booleano que indica si queremos borrar todos los 
	 * oyentes de la lista de reproduccion.
	 */
	public void reiniciar(boolean borrarOyentes) {
		listaReproduccion = new ArrayList<Cancion>();
		actual = 0;
		modoReproduccion = ModoReproduccionEnum.NORMAL;
		if(borrarOyentes) {
			listeners = new ArrayList<ListaReproduccionListener>();
		}
		notificaReinicio();
	}
	
	
	// ********************************************************************** //
	// *************              MÉTODOS PRIVADOS              ************* //
	// ********************************************************************** //
	/**
	 * Añade una cancion a la lista de reproduccion y notifica los cambios a los
	 * oyentes.
	 * Siempre que se añada alguna cancion, habremos de usar este metodo.
	 * @param cancion La cancion a añadir.
	 * @param pos La posicion donde añadimos la cancion.0 es la primera posicion
	 */
	private void addCancion(Cancion cancion, int pos) {
		listaReproduccion.add(pos, cancion);
		notificaNuevaCancionAniadida(cancion, pos);
	}
	
	/**
	 * Añade una cancion al final.
	 * @param cancion La cancion que queremos añadir.
	 */
	private void addCancionAlFinal(Cancion cancion) {
		addCancion(cancion,listaReproduccion.size());
	}
	
	/**
	 * Borra una cancion y notifica a los oyentes.
	 * @param pos La posicion de la cancion que queremos borrar.0 es la primera
	 * posicion
	 */
	private void borrarCancion(int pos) {
		if(pos < listaReproduccion.size() && pos >= 0) {
			listaReproduccion.remove(pos);
			notificaCancionBorrada(pos);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	// ********************************************************************** //
	// *************              MÉTODOS PUBLICOS              ************* //
	// ********************************************************************** //
	
	/**
	 * Añade una cancion al final y notifica a los oyentes.
	 */
	public void addCancion(Cancion c){
		this.addCancionAlFinal(c);
	}
	
	/**
	 * Indica si la lista esta vacia.
	 * @return
	 */
	public boolean isVacia(){
		return listaReproduccion.isEmpty();
	}
	
	/**
	 * Devuelve el numero de canciones.
	 * @return
	 */
	public int getNumeroCanciones(){
		return listaReproduccion.size();
	}
	
	/**
	 * 
	 * @param pos La primera posicion es la 0
	 */
	public void removeCancion(int pos){
		this.borrarCancion(pos);
		
		//Si la cancion que borramos esta por debajo de actual
		//O actual apuntaba a la ultima cancion
		if (pos < (actual-1) || actual > this.listaReproduccion.size()) {
			setActual(actual -1);
		}
		
	}
	
	/**
	 * 
	 * @param pos La primera posicion es la 0
	 * @return
	 */
	public Cancion getCancionAt(int pos){
		return listaReproduccion.get(pos);
		
	}
	
	/**
	 * 
	 * @param pos La primera posicion es la 0.
	 * @return
	 */
	public String getInfoCancionAt(int pos){
		return listaReproduccion.get(pos).getInfo();
	}
	
	@Override
	public String toString(){
		
		Iterator<Cancion> itr = listaReproduccion.iterator();
		String s = "";
		s = "LISTA DE REPRODUCCION";
		int i =0;
		while (itr.hasNext()){
			s += ("- CANCION EN POS "+i) + "\n";
			s += (itr.next().getInfo()) + "\n";
			s += ("") + "\n";

			i++;
		}
		return s;
	}
	
	// ********************************************************************** //
	// *************           GETTERS Y SETTERS                ************* //
	// ********************************************************************** //

	/**
	 * Devuelve el numero de la cancion actual. (De 1 hasta size).
	 * @return La cancion actual.
	 */
	public int getActual() {
		return actual;
	}

	/**
	 * El numero de cancion actual.
	 * @param La cancion actual. 1 es la primera cancion.
	 */
	public void setActual(int actual) {
		int viejo = this.actual;
		this.actual = actual;
		notificaCambioNumeroCancionActual(actual,viejo);
	}
	

	/**
	 * Devuelve el modo de reproduccion actual.
	 * @return el modo de reproduccion actual
	 */
	public ModoReproduccionEnum getModoReproduccion() {
		return modoReproduccion;
	}

	/**
	 * Establece el modo de reproduccion actual y notifica a los oyentes.
	 * @param modoReproduccion the modoReproduccion to set
	 */
	public void setModoReproduccion(ModoReproduccionEnum modoReproduccion) {
		this.modoReproduccion = modoReproduccion;
		notificaCambioTipoReproduccion(modoReproduccion);
	}

	// ********************************************************************** //
	// *************              GESTION LISTENERS             ************* //
	// ********************************************************************** //
	/**
	 * Añade un listener al modelo.
	 * @param listener El listener.
	 */
	public void addListaReproduccionListener(ListaReproduccionListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Elimina un listener del modelo.
	 * @param listener El listener.
	 */
	public void removeListaReproduccionListener(ListaReproduccionListener 
			listener) {
		listeners.remove(listener);
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Notifica una nueva lista de reproduccion, es decir, que se borran todas
	 * las canciones.
	 */
	private void notificaReinicio() {
		for (ListaReproduccionListener l : listeners) {
			l.reinicia();
		}
	}
	
	/**
	 * Notifica que se añade una nueva cancion.
	 * @param c La cancion que se añade.
	 * @param pos La posicion donde se añade. 0 es la primera posicion.
	 */
	private void notificaNuevaCancionAniadida(Cancion c, int pos) {
		for (ListaReproduccionListener l : listeners) {
			l.nuevaCancion(new NuevaCancionEvent(c.getName(),c.getAlbum(),
					c.getPista(),c.getCompositor(), c.getGenero(),c.getTrackLength(),pos));
		}
		
	}
	
	/**
	 * Notifica que se ha cambiado de cancion actual.
	 * @param actual La nueva cancion actual.
	 */
	private void notificaCambioNumeroCancionActual(int actualNuevo, int actualViejo) {
		for (ListaReproduccionListener l : listeners) {
			l.setActual(actualNuevo, actualViejo);
		}
	}
	
	/**
	 * Indica que se encuentra en reproduccion aleatoria.
	 * @param aleatorio. Indica si aleatorio es true o false.
	 */
	private void notificaCambioTipoReproduccion(ModoReproduccionEnum modo) {
		for (ListaReproduccionListener l : listeners) {
			l.cambioTipoReproduccion(modo);
		}
	}
	
	/**
	 * @param pos 0 es la primera posicion.
	 */
	private void notificaCancionBorrada(int pos) {
		for (ListaReproduccionListener l : listeners) {
			l.borrarCancion(new BorrarCancionEvent(pos));
		}
		
	}
}

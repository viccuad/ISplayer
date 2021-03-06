package is2011.reproductor.modelo;


import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.CriterioBusqueda;
import is2011.reproductor.modelo.listeners.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * Clase que almacena toda la informacion referente a la lista de reproduccion
 * del reproductor.
 * 
 * Puede ordenarse por algun sort definido en biliboteca.sorts
 * (Es decir, por album, artista, ....)
 * 
 * Puede desordenarse para reproducir al azar.
 * 
 * Puede iniciar una reproduccion aleatoria.
 *
 * Puede añadir y borrar canciones.
 * @author Administrator
 */
public class ListaReproduccion {
	
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
	private ArrayList<CancionContainer> listaReproduccion;
	
	/** Indices de las canciones en orden aleatorio */
	private ArrayList<Integer> listaAleatoria;
	
	/** Subcojunto de las canciones buscadas */
	private ArrayList<CancionContainer> buscadas;
	
	/** Atributo que indica si la biblioteca se encuentra en fase de busqueda */
	boolean busquedaRealizada;
	
	/** Cancion actual. va de 0 a numero de canciones*/
	private int actual;
	
	/** Indica si la reproduccion es en modo aleatorio*/
	private ModoReproduccionEnum modoReproduccion;

	/** Lista de listeners del reproductor */
	private ArrayList<ListaReproduccionListener> listeners;
	
	/** Flujo de lectura/escritura para ficheros XML */
	private XStream stream;
	
	/** Indica si la lista de reproducción sufrió algún cambio */
	private boolean modificado;
	
	/** Objeto utilizado para generar números aleatorios. */
	private transient Random rnd;

	/** Cancion actual Aleatoria*/
	private int actualAleatoria;
	
	// ********************************************************************** //
	// *************                CONSTRUCTOR                 ************* //
	// ********************************************************************** //
	
	/**
	 * Constructor por defecto.
	 * Crea lista de reproduccion y oyentes.
	 */
	public ListaReproduccion(){
		buscadas = null;
		busquedaRealizada = false;
		listaReproduccion = new ArrayList<CancionContainer>();
		listaAleatoria = new ArrayList<Integer>();
		actualAleatoria = 0;
		actual = 0;
		modoReproduccion = ModoReproduccionEnum.NORMAL;
		listeners = new ArrayList<ListaReproduccionListener>();
		
		this.modificado = false;
		stream = new XStream(new DomDriver());
		stream.alias("track", CancionContainer.class);
		
		rnd = new Random();
	}
	
	/**
	 * Reinicia la lista de reproduccion. (Quita todas las canciones, actual = 0
	 * aleatorio lo pone a false...
	 * 
	 * @param borrarOyentes booleano que indica si queremos borrar todos los 
	 * oyentes de la lista de reproduccion.
	 */
	public void reiniciar(boolean borrarOyentes) {
		listaReproduccion = new ArrayList<CancionContainer>();
		listaAleatoria = new ArrayList<Integer>();
		actual = 0;
		modificado = true;
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
	private void addCancion(CancionContainer cancion, int pos) {
		listaReproduccion.add(pos, cancion);
		modificado = true;
		notificaNuevaCancionAniadida(cancion, pos);
		
		crearListaAleatoria();
	}
	
	

	/**
	 * Añade una cancion al final.
	 * @param cancion La cancion que queremos añadir.
	 */
	private void addCancionAlFinal(CancionContainer cancion) {
		addCancion(cancion,listaReproduccion.size());
		modificado = true;
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
			modificado = true;
		} else {
			throw new IndexOutOfBoundsException();
		}
		crearListaAleatoria();
	}
	
	
	/**
	 * Toma el arrayList de canciones actual y crea del mismo tamaño con indices
	 * los indices de las canciones.
	 */
	private void crearListaAleatoria() {
		//Creamos un arrayList con todas las posiciones posibles.
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for(int i = 0; i < this.listaReproduccion.size();  i++) {
			indices.add(i);
		}
		
		//Creamos una nueva lista aleatoria.
		this.listaAleatoria = new ArrayList<Integer>();
		
		int aleatorio = 0;
		for(int i = 0; i < this.listaReproduccion.size();  i++) {
			aleatorio = indices.remove(rnd.nextInt(indices.size()));
			
			this.listaAleatoria.add(aleatorio+1);
		}
		
		if(this.actualAleatoria < this.listaReproduccion.size()) {
			this.actualAleatoria = 0;
		} 
		
	}
	// ********************************************************************** //
	// *************              MÉTODOS PUBLICOS              ************* //
	// ********************************************************************** //
	
	/**
	 * Añade una cancion al final y notifica a los oyentes.
	 */
	public void addCancion(CancionContainer c){
		this.addCancionAlFinal(c);
		modificado = true;
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
		
		if (busquedaRealizada){
			if(pos < buscadas.size() && pos >= 0) {
				
				int pos2  = getIndexOf(buscadas.get(pos), listaReproduccion);
				buscadas.remove(pos);
				listaReproduccion.remove(pos2);
				notificaCancionBorrada(pos);
				if (pos2 < (actual-1) || actual > this.listaReproduccion.size()) {
					setActual(actual -1);
				}				
				modificado = true;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}else{
			this.borrarCancion(pos);
			
			//Si la cancion que borramos esta por debajo de actual
			//O actual apuntaba a la ultima cancion
			if (pos < (actual-1) || actual > this.listaReproduccion.size()) {
				setActual(actual -1);
			}
			
			modificado = true;
		}

	}
	
	/**
	 * 
	 * @param pos La primera posicion es la 0
	 * @return
	 */
	public CancionContainer getCancionAt(int pos){
		return listaReproduccion.get(pos);
		
	}
	
	
	
	
	/**
	 * Carga la lista de reproducción XML recibiendo la ruta en la que se ubica.
	 * @param pathYfichero ruta absoluta al fichero XML de la lista de reproducción
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void cargarXML(String pathYfichero) throws FileNotFoundException{
		try {
			File aux = new File(pathYfichero);
			if (aux.canRead()){
				this.listaReproduccion = 
					(ArrayList<CancionContainer>)stream.fromXML
				(new FileInputStream(pathYfichero));
				modificado = true;
				notificaNuevaListaReproduccion(listaReproduccion,0);
				this.crearListaAleatoria();
				this.setActual(1);
				this.actualAleatoria = 0;
			}else {
				System.out.println("El fichero no existe");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Solo guarda el XML si ha habido cambios en la lista de reproducción
	 * @param pathYfichero
	 * @throws FileNotFoundException
	 */
	public void guardarXML(String pathYfichero) throws FileNotFoundException{
		File f = new File (pathYfichero);
		File f2 = f.getParentFile();
		f2.mkdirs();
		
		if(modificado) {
			OutputStreamWriter ww;
			try {
				ww = new OutputStreamWriter(
						new FileOutputStream(pathYfichero),"UTF-8");
				stream.toXML(listaReproduccion, ww);
			} catch (UnsupportedEncodingException e) {
				System.out.println("no se pudo guardar la lista en xml");
				e.printStackTrace();
			}
			
		}
			
			
	}
	
	
	/**
	 * Ordena la lista de reproducción siguiendo un criterio de ordenación
	 * que recibe como parámetro
	 * @param orden: es el criterio por el cuál se desea ordenar la lista de 
	 * reproducción
	 */
	public void ordenar(Comparator<CancionContainer> orden){
		
		if (busquedaRealizada){
			Collections.sort(this.buscadas, orden);
			this.notificaNuevaListaReproduccion(this.buscadas, 0);
		}else{
			
			if(actual == 0) {
				
				Collections.sort(this.listaReproduccion, orden);
				modificado = true;
				
				
				this.notificaNuevaListaReproduccion(this.listaReproduccion, 0);
			} else {
				CancionContainer cancionActual = this.listaReproduccion.get(
						actual-1);
				Collections.sort(this.listaReproduccion, orden);
				modificado = true;
				
				int indiceActual = this.listaReproduccion.indexOf(
						cancionActual) +1;
				this.notificaNuevaListaReproduccion(
						this.listaReproduccion, indiceActual);
			}
		}

		
		
	}

	
	/**
	 * Realiza una búsqueda en la lista de canciones según un criterio que 
	 * recibe como parámetro
	 * 
	 * @param busqueda es el criterio por el cuál se desea buscar en la lista de
	 *  reproducción
	 * @return la nueva colección con los elementos que satisface el criterio de
	 *  búsqueda
	 */
	/*
	public ArrayList<CancionContainer> getBusqueda(CriterioBusqueda busqueda){		
		return busqueda.buscar(this.listaReproduccion);
	}
	*/
	
	/**
	 * Realiza una búsqueda avanzada en la lista de canciones según un criterio 
	 * que recibe como parámetro
	 * @param busqueda es el criterio por el cuál se desea buscar en la lista de
	 *  reproducción
	 * @return la nueva colección con los elementos que satisface el criterio de
	 *  búsqueda
	 */
	public void realizarBusquedaAvanzada(CriterioBusqueda busqueda){		
		this.buscadas = busqueda.buscarAvanzado(this.listaReproduccion);
		
		 if (busqueda.getCadena().equals("")){
			 busquedaRealizada = false;
			 notificaNuevaListaReproduccion(listaReproduccion, actual);
			 
		 }
		 else{
			 busquedaRealizada = true;
			 notificaNuevaListaReproduccion(buscadas, actual);
		 }
	}
	
	
	// ********************************************************************** //
	// *************           GETTERS Y SETTERS                ************* //
	// ********************************************************************** //
	
	/**
	 * Devuelve si hay o no busqueda realizada
	 * @return
	 */
	public boolean getBusquedaRealizada(){
		return busquedaRealizada;
	}
	
	public int getIndexOf(CancionContainer c, ArrayList<CancionContainer> lr){
	
		Iterator<CancionContainer> itr = lr.iterator();
		int i=0;
		boolean encontrada = false;
		while (itr.hasNext() && !encontrada){
			if (itr.next().equals(c)) encontrada = true;
			else i++;
		}
		
		return i;
	}
	
	/**
	 * Devuelve una lista con todas las canciones dbuscadas
	 * @return
	 */
	public ArrayList<CancionContainer> getCancionesBuscadas(){
		return this.buscadas;
	}

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
		
		if (!busquedaRealizada)		
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
		if(this.modoReproduccion.equals(modoReproduccion)){
			modoReproduccion = ModoReproduccionEnum.NORMAL;
		}
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
	private void notificaNuevaCancionAniadida(CancionContainer c, int pos) {
		for (ListaReproduccionListener l : listeners) {
			
			if (!busquedaRealizada){
			l.nuevaCancion(new NuevaCancionEvent(c.getTitulo(),c.getAlbum(),
					c.getPista(),c.getArtista(), c.getGenero(),c.getDuracion(),
					pos));
			}
		}
		
	}
	
	/**
	 * Notifica que se ha cambiado de cancion actual.
	 * @param actual La nueva cancion actual.
	 */
	private void notificaCambioNumeroCancionActual(int actualNuevo, 
			int actualViejo) {
		for (ListaReproduccionListener l : listeners) {
			l.setActual(actualNuevo, actualViejo);
		}
		
	}
	
	private void notificaCancioNoValida(int indice) { 
		for (ListaReproduccionListener l : listeners) {
			l.desactivaCancion(indice);
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
	
	/**
	 * Le dice a la vista que borre todas las canciones cargadas
	 * y que cargue la nueva.
	 * @param 
	 */
	private void notificaNuevaListaReproduccion(ArrayList<CancionContainer> c
			,int indiceActual) {
		for (ListaReproduccionListener l : listeners) {
			l.reinicia();
			l.nuevaListaReproduccion(c);
		}
		
		this.setActual(indiceActual);
	}
	
	/**
	 * Devuelve las canciones de la lista de reproduccion
	 * @return
	 */
	public ArrayList<CancionContainer> getCancionesListaReproduccion(){
		return listaReproduccion;
	}

	/**
	 * Devulve el indice de la cancion actual pero en orden aleatorio.
	 * @return El indice de la siguietne cancion aleatoria.
	 */ 
	public int getSiguienteAleatoria() {
		if(this.actualAleatoria >= this.listaReproduccion.size()){
			return this.actualAleatoria+1;
		}else {
			if(actualAleatoria <=0) {
				this.actualAleatoria = 0;
			}
			if(this.listaAleatoria.size() > 0) {
				int aux = this.listaAleatoria.get(this.actualAleatoria);
				this.actualAleatoria++;
				return aux;
			}
			return 0;
		}
		
	}
	
	public int getAnteriorAleatoria() {
		
		if(this.actualAleatoria <= 1){
			return 0;
		}/*if(this.actualAleatoria == 1) {
			return this.listaAleatoria.get(--this.actualAleatoria );
		}*/
		
		else {
			if (actualAleatoria > this.listaReproduccion.size()) {
				actualAleatoria = this.listaReproduccion.size() +1;
			}else {
				
			}
			
			return this.listaAleatoria.get(--this.actualAleatoria -1);
		}
	}
	

	public void setActualAleatoria(int cancionSeleccionada) {
		this.actualAleatoria = cancionSeleccionada +1;
		
	}
	
	/**
	 * Incrementa la cancion Actual en 1. Solo se debe utilizar en modo de
	 * reproduccion aleatorio, ya que este metodo no notificara 
	 * el numero de cancion actual, sino su posicion real.
	 *
	 */
	public void incrementaActual() {
		
	}

	/**
	 * 
	 */
	public void desactivaActual() {
		CancionContainer c = this.listaReproduccion.get(this.actual-1);
		int indexReal ;
		if (busquedaRealizada) {
			indexReal = this.getIndexOf(c, this.buscadas);
		}else {
			indexReal = actual-1;
		}
		
		c.setValida(false);
		
		this.notificaCancioNoValida(indexReal);
		
		
	}

	

}

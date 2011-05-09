package is2011.biblioteca.search;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.ArrayList;

public interface CriterioBusqueda {

	/**
	 * Realiza una búsqueda en una colección de canciones. El elemento o elementos
	 * a buscar se deberá instanciar en el constructor de la clase que implemente
	 * esta interfaz. Esto se hace así para dar más flexibilidad, como por ejemplo
	 * realizar búsquedas por varios criterios
	 * 
	 * @param lista es la colección de elemenentos dónde se desea realizar la búsqueda
	 * @return una nueva colección con los elements que coinciden con el criterio de búsqueda
	 */
	public ArrayList<CancionContainer> buscar(ArrayList<CancionContainer> lista);
	
	
	/**
	 * Realiza una búsqueda avanzada en una colección de canciones. El elemento o elementos
	 * a buscar se deberá instanciar en el constructor de la clase que implemente
	 * esta interfaz. Esto se hace así para dar más flexibilidad, como por ejemplo
	 * realizar búsquedas por varios criterios
	 * 
	 * @param lista es la colección de elemenentos dónde se desea realizar la búsqueda
	 * @return una nueva colección con los elements que coinciden con el criterio de búsqueda
	 */
	public ArrayList<CancionContainer> buscarAvanzado(ArrayList<CancionContainer> lista); 
	
	/**
	 * Devuelve la candena sobre la que se realiza las comparaciones
	 * @return
	 */
	public String getCadena();
	
}

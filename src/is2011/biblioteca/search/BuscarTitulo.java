package is2011.biblioteca.search;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.ArrayList;
import java.util.Iterator;

public class BuscarTitulo implements CriterioBusqueda {

	/** Es el elemento que se desea buscar */
	private String titulo;
	
	
	/**
	 * Crea el criterio de búsqueda con el elemento que se desea buscar
	 * @param elemento es lo que se desea buscar
	 */
	public BuscarTitulo(String elemento){
		this.titulo = elemento;
	}
	
	/**
	 * Devuelve la cadena sobre la que se busca
	 */
	public String getCadena(){
		return titulo;
	}
	/**
	 * Realiza la búsqueda soble la colección que recible como parámetro
	 * @param lista es la colección de elemenentos dónde se desea realizar la búsqueda
	 * @return una nueva colección con los elements que coinciden con el criterio de búsqueda
	 */
	@Override
	public ArrayList<CancionContainer> buscar(ArrayList<CancionContainer> lista) {
		ArrayList<CancionContainer> encontrados = new ArrayList<CancionContainer>();
		Iterator<CancionContainer> it = lista.iterator();
		
		while(it.hasNext()){
			CancionContainer actual = it.next();
			if(actual.getTitulo().equalsIgnoreCase(titulo))
				encontrados.add(actual);
		}
		
		return encontrados;
	}


	/**
	 * Realiza la búsqueda avanzada (coincidencias de cadenas) soble la colección que recible como parámetro.
	 * @param lista es la colección de elemenentos dónde se desea realizar la búsqueda
	 * @return una nueva colección con los elements que coinciden con el criterio de búsqueda
	 */
	@Override
	public ArrayList<CancionContainer> buscarAvanzado(ArrayList<CancionContainer> lista) {
		ArrayList<CancionContainer> encontrados = new ArrayList<CancionContainer>();
		Iterator<CancionContainer> it = lista.iterator();
		
		while(it.hasNext()){
			CancionContainer actual = it.next();
			if(actual.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
				encontrados.add(actual);
		}
		
		return encontrados;
	}

}
